package br.edu.infnet.cartaocredito.controller;

import br.edu.infnet.cartaocredito.service.UsuarioService;
import br.edu.infnet.cartaocredito.service.dto.PlaylistDto;
import br.edu.infnet.cartaocredito.service.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarUsuario(@RequestBody UsuarioDto dto) {
        try {
            UsuarioDto response = usuarioService.criar(dto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable UUID id) {
        try {
            UsuarioDto usuarioDto = usuarioService.buscarUsuarioPorId(id);
            return ResponseEntity.ok(usuarioDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable UUID id, @RequestBody UsuarioDto dto) {
        try {
            UsuarioDto usuarioDto = usuarioService.atualizarUsuario(id, dto);
            return ResponseEntity.ok(usuarioDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable UUID id) {
        try {
            usuarioService.deletarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/playlists")
    public ResponseEntity<?> listarPlaylistsUsuario(@PathVariable UUID id) {
        try {
            List<PlaylistDto> playlists = usuarioService.listarPlaylistsUsuario(id);
            return ResponseEntity.ok(playlists);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
