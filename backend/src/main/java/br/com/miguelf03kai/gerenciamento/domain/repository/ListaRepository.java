package br.com.miguelf03kai.gerenciamento.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.miguelf03kai.gerenciamento.domain.model.Lista;

public interface ListaRepository extends JpaRepository<Lista, Long>{
}
