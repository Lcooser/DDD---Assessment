package br.edu.infnet.cartaocredito.service;

import br.edu.infnet.cartaocredito.domain.Cartao;
import br.edu.infnet.cartaocredito.repository.CartaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartaoService {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Transactional
    public void autorizarTransacao(UUID idCartao, String merchant, double valor, String descricao) throws Exception {

        Cartao cartao = cartaoRepository.findById(idCartao)
                .orElseThrow(() -> new Exception("Cartão não encontrado"));

        cartao.criarTransacao(merchant, valor, descricao);

        cartaoRepository.save(cartao);
    }

    public Cartao findById(UUID id) {
        return cartaoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void salvarCartao(Cartao cartao) {
        cartaoRepository.save(cartao);
    }

}
