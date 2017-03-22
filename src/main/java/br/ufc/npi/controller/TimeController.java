package br.ufc.npi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.ufc.npi.model.Jogador;
import br.ufc.npi.model.Time;
import br.ufc.npi.service.JogadorService;
import br.ufc.npi.service.TimeService;

@Controller
@RequestMapping(path = "/times")
public class TimeController {

	@Autowired
	private TimeService service;
	@Autowired
	private JogadorService jogadorService;

	@RequestMapping(path = "/")
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("times");
		List<Time> times = service.listar();
		model.addObject("times", times);
		return model;
	}

	@RequestMapping(path = "/{id}")
	public ModelAndView detalhes(@PathVariable int id, @RequestParam(required=false) String erro) {
		ModelAndView modelAndView = new ModelAndView("detalhes-time");
		Time time = service.buscarPorId(id);
		List<Jogador> jogadoresSemTime = jogadorService.jogadoresSemTime();

		modelAndView.addObject("jogadoresSemTime", jogadoresSemTime);
		modelAndView.addObject("time", time);
		modelAndView.addObject("erro", erro);
		return modelAndView;
	}

	@RequestMapping(path = "/salvar", method = RequestMethod.POST)
	public String salvar(@RequestParam String nome) {
		service.salvar(nome);
		return "redirect:/times/";
	}

	@RequestMapping(path = "/excluir/{id}")
	public String excluir(@PathVariable("id") int id) {
		service.excluir(id);
		return "redirect:/times/";
	}

	@RequestMapping(path = "/{idTime}/adicionarJogador", method = RequestMethod.POST)
	public ModelAndView adicionarJogadorAoTime(@PathVariable("idTime") int idTime, @RequestParam int idJogadorSemTime) {
		boolean itsOk = service.adicionarJogadorAoTime(idTime, idJogadorSemTime);
		ModelAndView model = new ModelAndView("redirect:/times/".concat(String.valueOf(idTime)));
		if (itsOk == false) {
			String erro = "O time já está completo.";
			model.addObject("erro", erro);
		}
		return model;
	}

	@RequestMapping(path = "/{idTime}/removerJogador/{idJogadorSemTime}")
	public String removerJogadorDoTime(@PathVariable int idTime, @PathVariable int idJogadorSemTime) {
		service.removerJogadorDoTime(idTime, idJogadorSemTime);
		return "redirect:/times/".concat(String.valueOf(idTime));
	}

}
