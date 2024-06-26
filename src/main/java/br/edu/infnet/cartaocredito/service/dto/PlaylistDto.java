package br.edu.infnet.cartaocredito.service.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PlaylistDto {
    private UUID id;
    private String nome;
    private List<MusicaDto> musicas;
    private String descricao;

}
