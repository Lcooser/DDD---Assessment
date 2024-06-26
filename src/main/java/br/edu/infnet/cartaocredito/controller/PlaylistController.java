package br.edu.infnet.cartaocredito.controller;

import br.edu.infnet.cartaocredito.service.PlaylistService;
import br.edu.infnet.cartaocredito.service.dto.PlaylistDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarPlaylist(@RequestParam UUID idUsuario, @RequestBody PlaylistDto dto) {
        try {
            PlaylistDto playlistDto = playlistService.criarPlaylist(idUsuario, dto);
            return ResponseEntity.ok(playlistDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistDto> buscarPlaylistPorId(@PathVariable UUID id) {
        try {
            PlaylistDto playlistDto = playlistService.buscarPlaylistPorId(id);
            return ResponseEntity.ok(playlistDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPlaylist(@PathVariable UUID id) {
        try {
            playlistService.deletarPlaylist(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
