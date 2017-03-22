package br.ufc.npi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufc.npi.model.Jogador;
import br.ufc.npi.model.Time;
import br.ufc.npi.repository.JogadorRepository;
import br.ufc.npi.repository.TimeRepository;

@Service
public class TimeService {

	@Autowired
	private TimeRepository repository;
	@Autowired
	private JogadorRepository jogadorRepository;

	public Time salvar(String nome) {
		Time time = new Time();
		time.setNome(nome);
		repository.save(time);
		return time;
	}

	public List<Time> listar() {
		return repository.findAll();
	}

	public void excluir(int id) {
		repository.delete(id);
	}

	public Time buscarPorId(int id) {
		return repository.findOne(id);
	}

	public boolean adicionarJogadorAoTime(int idTime, int idJogadorSemTime) {
		Time time = this.buscarPorId(idTime);
		if (time.getJogadores().size() == 7) {
			return false;
		} else {
			Jogador jogador = jogadorRepository.findOne(idJogadorSemTime);

			time.getJogadores().add(jogador);
			jogador.setTime(time);

			repository.save(time);
			jogadorRepository.save(jogador);
			return true;
		}
	}

	public void removerJogadorDoTime(int idTime, int idJogadorSemTime) {
		Time time = this.buscarPorId(idTime);
		Jogador jogador = jogadorRepository.findOne(idJogadorSemTime);

		time.getJogadores().remove(jogador);
		jogador.setTime(null);

		repository.save(time);
		jogadorRepository.save(jogador);
	}
}
