package br.com.miguelf03kai.gerenciamento.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrioridadeItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long itemPrioridadeId;
    public String descricao;
    public String cor;
}