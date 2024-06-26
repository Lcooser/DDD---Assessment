package br.edu.infnet.cartaocredito.service;

import br.edu.infnet.cartaocredito.domain.Assinatura;
import br.edu.infnet.cartaocredito.domain.Plano;
import br.edu.infnet.cartaocredito.domain.Usuario;
import br.edu.infnet.cartaocredito.repository.AssinaturaRepository;
import br.edu.infnet.cartaocredito.repository.PlanoRepository;
import br.edu.infnet.cartaocredito.repository.UsuarioRepository;
import br.edu.infnet.cartaocredito.service.dto.AssinaturaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AssinaturaService {

    @Autowired
    private AssinaturaRepository assinaturaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanoRepository planoRepository;

    public AssinaturaDto criar(AssinaturaDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Plano plano = planoRepository.findById(dto.getPlanoId())
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        Assinatura assinatura = new Assinatura();
        assinatura.setAtivo(dto.isAtivo());
        assinatura.setDtAssinatura(LocalDateTime.now());
        assinatura.setUsuario(usuario);
        assinatura.setPlano(plano);

        assinaturaRepository.save(assinatura);

        return convertToDto(assinatura);
    }

    public AssinaturaDto buscarPorId(UUID id) {
        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

        return convertToDto(assinatura);
    }

    public AssinaturaDto atualizar(UUID id, AssinaturaDto dto) {
        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Plano plano = planoRepository.findById(dto.getPlanoId())
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        assinatura.setAtivo(dto.isAtivo());
        assinatura.setUsuario(usuario);
        assinatura.setPlano(plano);

        assinaturaRepository.save(assinatura);

        return convertToDto(assinatura);
    }

    public void deletar(UUID id) {
        Assinatura assinatura = assinaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

        assinaturaRepository.delete(assinatura);
    }


    private AssinaturaDto convertToDto(Assinatura assinatura) {
        AssinaturaDto dto = new AssinaturaDto();
        dto.setId(assinatura.getId());
        dto.setAtivo(assinatura.isAtivo());
        dto.setDtAssinatura(assinatura.getDtAssinatura());
        dto.setUsuarioId(assinatura.getUsuario().getId());
        dto.setPlanoId(assinatura.getPlano().getId());
        return dto;
    }
}
