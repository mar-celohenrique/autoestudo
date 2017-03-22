package br.ufc.npi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.ufc.npi.model.Jogador;

public interface JogadorRepository extends JpaRepository<Jogador, Integer>{
	Jogador findByNome(String nome);
	@Query("from Jogador j where j.time is null")
	List<Jogador> jogadoresSemTime();	
}
