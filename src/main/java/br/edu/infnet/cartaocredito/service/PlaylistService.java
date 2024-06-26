package br.edu.infnet.cartaocredito.service;

import br.edu.infnet.cartaocredito.domain.Playlist;
import br.edu.infnet.cartaocredito.domain.Usuario;
import br.edu.infnet.cartaocredito.repository.PlaylistRepository;
import br.edu.infnet.cartaocredito.repository.UsuarioRepository;
import br.edu.infnet.cartaocredito.service.dto.PlaylistDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public PlaylistDto criarPlaylist(UUID idUsuario, PlaylistDto dto) throws Exception {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new Exception("Usuário não encontrado"));


        Playlist playlist = new Playlist();
        playlist.setNome(dto.getNome());
        playlist.setDescricao(dto.getDescricao());
        playlist.setUsuario(usuario);

        // Salvar a playlist no repositório
        playlist = playlistRepository.save(playlist);

        // Preparar a resposta
        PlaylistDto playlistDto = new PlaylistDto();
        playlistDto.setId(playlist.getId());
        playlistDto.setNome(playlist.getNome());
        playlistDto.setDescricao(playlist.getDescricao());

        return playlistDto;
    }

    public PlaylistDto buscarPlaylistPorId(UUID id) throws Exception {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new Exception("Playlist não encontrada"));

        PlaylistDto playlistDto = new PlaylistDto();
        playlistDto.setId(playlist.getId());
        playlistDto.setNome(playlist.getNome());
        playlistDto.setDescricao(playlist.getDescricao());

        return playlistDto;
    }

    public void deletarPlaylist(UUID id) throws Exception {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new Exception("Playlist não encontrada"));

        playlistRepository.delete(playlist);
    }
}
