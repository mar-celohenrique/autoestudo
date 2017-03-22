package br.ufc.npi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufc.npi.model.Time;

public interface TimeRepository extends JpaRepository<Time, Integer>{
	Time findByNome(String nome);
}
