package br.edu.infnet.cartaocredito.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private Boolean ativo;

    @Column
    private LocalDate validade;

    @Column(nullable = false)
    private double limite;

    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL)
    private List<Transacao> transacoes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private static final int TRANSACAO_INTERVALO_TEMPO = 2;
    private static final int TRANSACAO_QUANTIDADE_ALTA_FREQUENCIA = 3;
    private static final int TRANSACAO_MERCHANT_DUPLICADAS = 2;

    public void criarTransacao(String merchant, double valor, String descricao) throws Exception {
        // Validar Cartao
        if (!this.ativo) {
            throw new Exception("Cartão não está ativo");
        }

        Transacao transacao = new Transacao();
        transacao.setMerchant(merchant);
        transacao.setDescricao(descricao);
        transacao.setValor(valor);
        transacao.setDtTransacao(LocalDateTime.now());
        transacao.setCartao(this);

        // Verificar Limite
        if (!validarLimite(transacao)) {
            throw new Exception("Cartão não possui limite para esta transação");
        }

        // Validar Transacao
        if (!validarTransacao(transacao)) {
            throw new Exception("Transação inválida");
        }

        // Diminuir o limite do cartao
        this.setLimite(this.getLimite() - transacao.getValor());

        // Adicionar uma nova transacao e criar o codigo de autorização
        transacao.setCodigoAutorizacao(UUID.randomUUID());
        this.transacoes.add(transacao);
    }

    private boolean validarLimite(Transacao transacao) {
        return this.limite >= transacao.getValor();
    }

    private boolean validarTransacao(Transacao transacao) {
        List<Transacao> ultimasTransacoes = this.transacoes.stream()
                .filter((x) -> x.getDtTransacao().isAfter(LocalDateTime.now().minusMinutes(TRANSACAO_INTERVALO_TEMPO)))
                .collect(Collectors.toList());

        if (ultimasTransacoes.size() >= TRANSACAO_QUANTIDADE_ALTA_FREQUENCIA) {
            return false;
        }

        List<Transacao> transacoesMerchantRepetidas = ultimasTransacoes.stream()
                .filter((x) -> x.getMerchant().equals(transacao.getMerchant()) && x.getValor() == transacao.getValor())
                .collect(Collectors.toList());

        return transacoesMerchantRepetidas.size() < TRANSACAO_MERCHANT_DUPLICADAS;
    }
}
