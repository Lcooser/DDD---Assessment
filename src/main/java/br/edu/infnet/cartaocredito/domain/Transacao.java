package br.edu.infnet.cartaocredito.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID codigoAutorizacao;

    @Column(nullable = false)
    private double valor;

    @Column(nullable = false)
    private LocalDateTime dtTransacao;

    @Column(nullable = false)
    private String merchant;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    private Cartao cartao;
}
