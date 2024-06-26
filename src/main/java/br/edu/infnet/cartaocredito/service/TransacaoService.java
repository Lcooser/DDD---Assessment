package br.edu.infnet.cartaocredito.service;

import br.edu.infnet.cartaocredito.domain.Cartao;
import br.edu.infnet.cartaocredito.domain.Transacao;
import br.edu.infnet.cartaocredito.repository.TransacaoRepository;
import br.edu.infnet.cartaocredito.service.dto.TransacaoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private CartaoService cartaoService;

    @Transactional
    public void autorizarTransacao(TransacaoDto dto) throws Exception {
        Cartao cartao = cartaoService.findById(dto.getIdCartao());
        if (cartao == null) {
            throw new Exception("Cartão não encontrado");
        }

        Transacao transacao = new Transacao();
        transacao.setValor(dto.getValor());
        transacao.setDtTransacao(LocalDateTime.now());
        transacao.setMerchant(dto.getMerchant());
        transacao.setDescricao(dto.getDescricao());
        transacao.setCartao(cartao);

        transacaoRepository.save(transacao);
    }
}
