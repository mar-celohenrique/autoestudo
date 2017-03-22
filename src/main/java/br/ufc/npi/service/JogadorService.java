package br.ufc.npi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.model.Jogador;
import br.ufc.npi.model.Time;
import br.ufc.npi.repository.JogadorRepository;
import br.ufc.npi.repository.TimeRepository;

@Service
public class JogadorService {

	@Autowired
	private JogadorRepository repository;
	@Autowired
	private TimeRepository timeRepository;

	public Jogador salvar(String nome, int idade) {
		Jogador jogador = new Jogador();
		jogador.setNome(nome);
		jogador.setIdade(idade);
		repository.save(jogador);
		return jogador;
	}

	public List<Jogador> jogadores() {
		return repository.findAll();
	}

	public List<Jogador> jogadoresSemTime() {
		return repository.jogadoresSemTime();
	}

	public void excluir(int id) {
		Jogador jogador = repository.findOne(id);
		if (jogador.getTime() != null) {
			Time time = timeRepository.findOne(jogador.getTime().getId());
			time.getJogadores().remove(jogador);
			timeRepository.save(time);
		}
		repository.delete(jogador);
	}

}
