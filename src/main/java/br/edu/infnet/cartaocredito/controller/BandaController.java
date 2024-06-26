package br.edu.infnet.cartaocredito.controller;

import br.edu.infnet.cartaocredito.service.BandaService;
import br.edu.infnet.cartaocredito.service.dto.BandaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/bandas")
public class BandaController {

    @Autowired
    private BandaService bandaService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarBanda(@RequestBody BandaDto dto) {
        try {
            BandaDto bandaDto = bandaService.criar(dto);
            return ResponseEntity.ok(bandaDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarBandaPorId(@PathVariable UUID id) {
        try {
            BandaDto bandaDto = bandaService.buscarPorId(id);
            return ResponseEntity.ok(bandaDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarBanda(@PathVariable UUID id, @RequestBody BandaDto dto) {
        try {
            BandaDto bandaDto = bandaService.atualizar(id, dto);
            return ResponseEntity.ok(bandaDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarBanda(@PathVariable UUID id) {
        try {
            bandaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
