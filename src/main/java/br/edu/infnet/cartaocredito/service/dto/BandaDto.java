package br.edu.infnet.cartaocredito.service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class BandaDto {
    private UUID id;
    private String nome;
    private String descricao;
    private List<UUID> musicasIds = new ArrayList<>();
}
