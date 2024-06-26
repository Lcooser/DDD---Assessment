package br.edu.infnet.cartaocredito.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransacaoDto {

    private UUID codigoAutorizacao;
    private double valor;
    private LocalDateTime dtTransacao;
    private String merchant;
    private String descricao;
    private UUID idCartao;

}
