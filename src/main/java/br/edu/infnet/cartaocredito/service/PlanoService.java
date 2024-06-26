package br.edu.infnet.cartaocredito.service;

import br.edu.infnet.cartaocredito.domain.Plano;
import br.edu.infnet.cartaocredito.repository.PlanoRepository;
import br.edu.infnet.cartaocredito.service.dto.PlanoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class PlanoService {

    @Autowired
    private PlanoRepository planoRepository;

    public PlanoDto criar(PlanoDto dto) {
        Plano plano = new Plano();
        plano.setNome(dto.getNome());
        plano.setAtivo(dto.isAtivo());
        plano.setDescricao(dto.getDescricao());
        plano.setValor(dto.getValor());

        Plano planoSalvo = planoRepository.save(plano);

        return convertToDto(planoSalvo);
    }

    public PlanoDto buscarPorId(UUID id) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        return convertToDto(plano);
    }

    public PlanoDto atualizar(UUID id, PlanoDto dto) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        plano.setNome(dto.getNome());
        plano.setAtivo(dto.isAtivo());
        plano.setDescricao(dto.getDescricao());
        plano.setValor(dto.getValor());

        Plano planoAtualizado = planoRepository.save(plano);

        return convertToDto(planoAtualizado);
    }

    public void deletar(UUID id) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        planoRepository.delete(plano);
    }

    private PlanoDto convertToDto(Plano plano) {
        PlanoDto dto = new PlanoDto();
        dto.setId(plano.getId());
        dto.setNome(plano.getNome());
        dto.setAtivo(plano.isAtivo());
        dto.setDescricao(plano.getDescricao());
        dto.setValor(plano.getValor());
        return dto;
    }
}
