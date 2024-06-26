package br.edu.infnet.cartaocredito.service;

import br.edu.infnet.cartaocredito.domain.Banda;
import br.edu.infnet.cartaocredito.domain.Musica;
import br.edu.infnet.cartaocredito.repository.BandaRepository;
import br.edu.infnet.cartaocredito.service.dto.BandaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BandaService {

    @Autowired
    private BandaRepository bandaRepository;

    public BandaDto criar(BandaDto dto) {
        Banda banda = new Banda();
        banda.setNome(dto.getNome());
        banda.setDescricao(dto.getDescricao());

   
        List<Musica> musicas = new ArrayList<>();
        for (UUID musicaId : dto.getMusicasIds()) {
            Musica musica = new Musica();
            musica.setId(musicaId);
            musicas.add(musica);
        }
        banda.setMusicas(musicas);

        bandaRepository.save(banda);

        return convertToDto(banda);
    }

    public BandaDto buscarPorId(UUID id) {
        Banda banda = bandaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banda não encontrada"));

        return convertToDto(banda);
    }

    public BandaDto atualizar(UUID id, BandaDto dto) {
        Banda banda = bandaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banda não encontrada"));

        banda.setNome(dto.getNome());
        banda.setDescricao(dto.getDescricao());

        // Atualizar as músicas associadas à banda, caso necessário
        List<Musica> musicas = new ArrayList<>();
        for (UUID musicaId : dto.getMusicasIds()) {
            Musica musica = new Musica();
            musica.setId(musicaId);
            musicas.add(musica);
        }
        banda.setMusicas(musicas);

        bandaRepository.save(banda);

        return convertToDto(banda);
    }

    public void deletar(UUID id) {
        Banda banda = bandaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banda não encontrada"));

        bandaRepository.delete(banda);
    }


    private BandaDto convertToDto(Banda banda) {
        BandaDto dto = new BandaDto();
        dto.setId(banda.getId());
        dto.setNome(banda.getNome());
        dto.setDescricao(banda.getDescricao());

        return dto;
    }
}
