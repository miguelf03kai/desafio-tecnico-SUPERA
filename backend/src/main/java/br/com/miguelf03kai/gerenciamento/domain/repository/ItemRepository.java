package br.com.miguelf03kai.gerenciamento.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.miguelf03kai.gerenciamento.domain.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{}
