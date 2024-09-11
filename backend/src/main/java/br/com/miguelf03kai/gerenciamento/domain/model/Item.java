package br.com.miguelf03kai.gerenciamento.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long itemId;
    public String descricao;
    public Long itemPrioridadeId;
    public Long itemEstadoId;
	public Long listaId;

    /*@ManyToOne
    @JoinColumn(name = "lista_id")
    @JsonIgnore
    public Lista lista;*/
}
