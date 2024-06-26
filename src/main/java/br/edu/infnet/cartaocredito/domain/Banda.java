package br.edu.infnet.cartaocredito.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Banda {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String nome;
    @Column
    private String descricao;

    @OneToMany
    private List<Musica> musicas;
}
