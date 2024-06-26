package br.edu.infnet.cartaocredito.service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AssinaturaDto {
    private UUID id;
    private boolean ativo;
    private LocalDateTime dtAssinatura;
    private UUID usuarioId;
    private UUID planoId;
}
