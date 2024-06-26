package br.edu.infnet.cartaocredito.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PlanoDto {
    private UUID id;
    private String nome;
    private boolean ativo;
    private String descricao;
    private double valor;
}
