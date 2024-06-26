package br.edu.infnet.cartaocredito.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MusicaDto {
    private UUID id;
    private String nome;
    private int duracao;

}
