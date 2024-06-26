package br.edu.infnet.cartaocredito.service;

import br.edu.infnet.cartaocredito.domain.*;
import br.edu.infnet.cartaocredito.repository.PlanoRepository;
import br.edu.infnet.cartaocredito.repository.UsuarioRepository;
import br.edu.infnet.cartaocredito.service.dto.PlaylistDto;
import br.edu.infnet.cartaocredito.service.dto.UsuarioDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private CartaoService cartaoService;

    @Transactional
    public UsuarioDto criar(UsuarioDto dto) throws Exception {
        // Verificar se temos um plano válido
        Optional<Plano> plano = planoRepository.findById(dto.getIdPlano());
        if (plano.isEmpty()) {
            throw new Exception("Plano não encontrado");
        }

        // Verificar se o CPF já está cadastrado
        if (usuarioRepository.findUsuarioByCpf(dto.getCpf()).isPresent()) {
            throw new Exception("CPF do usuário já cadastrado");
        }

        // Criar um novo cartão
        Cartao cartao = new Cartao();
        cartao.setNumero(dto.getNumeroCartao());
        cartao.setAtivo(dto.getCartaoAtivo());
        cartao.setLimite(dto.getLimite());

        // Criar o usuário
        Usuario usuario = new Usuario();
        usuario.criar(dto.getNome(), dto.getEmail(), dto.getSenha(), dto.getCpf(), cartao, plano.get());

        // Salvar o usuário e o cartão
        usuarioRepository.save(usuario);
        cartaoService.salvarCartao(cartao);

        // Preparar resposta para o controller
        UsuarioDto response = new UsuarioDto();
        response.setId(usuario.getId());
        response.setEmail(usuario.getEmail());
        response.setNome(usuario.getNome());
        response.setSenha("************");
        response.setNumeroCartao("***** ***** ***** *****");
        response.setCartaoAtivo(cartao.getAtivo());
        response.setLimite(cartao.getLimite());
        response.setIdPlano(plano.get().getId());
        response.setCpf(usuario.getCpf());

        return response;
    }

    @Transactional
    public UsuarioDto atualizarUsuario(UUID id, UsuarioDto dto) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            throw new Exception("Usuário não encontrado");
        }

        Usuario usuario = optionalUsuario.get();
        usuario.atualizar(dto.getNome(), dto.getEmail(), dto.getSenha());

        // Atualizar o usuário
        usuarioRepository.save(usuario);

        // Preparar resposta para o controller
        UsuarioDto response = new UsuarioDto();
        response.setId(usuario.getId());
        response.setEmail(usuario.getEmail());
        response.setNome(usuario.getNome());
        response.setSenha("************");
        response.setCpf(usuario.getCpf());

        return response;
    }

    @Transactional
    public void deletarUsuario(UUID id) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            throw new Exception("Usuário não encontrado");
        }

        Usuario usuario = optionalUsuario.get();
        usuario.deletar();

        // Deletar o usuário
        usuarioRepository.delete(usuario);
    }

    @Transactional
    public UsuarioDto buscarUsuarioPorId(UUID id) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            throw new Exception("Usuário não encontrado");
        }

        Usuario usuario = optionalUsuario.get();

        // Preparar resposta para o controller
        UsuarioDto response = new UsuarioDto();
        response.setId(usuario.getId());
        response.setEmail(usuario.getEmail());
        response.setNome(usuario.getNome());
        response.setSenha("************");
        response.setCpf(usuario.getCpf());

        return response;
    }

    @Transactional
    public List<PlaylistDto> listarPlaylistsUsuario(UUID id) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            throw new Exception("Usuário não encontrado");
        }

        Usuario usuario = optionalUsuario.get();
        List<PlaylistDto> playlists = new ArrayList<>();

        // Converter playlists para DTOs
        for (Playlist playlist : usuario.getPlaylists()) {
            PlaylistDto playlistDto = new PlaylistDto();
            playlistDto.setId(playlist.getId());
            playlistDto.setNome(playlist.getNome());
            playlists.add(playlistDto);
        }

        return playlists;
    }
}
