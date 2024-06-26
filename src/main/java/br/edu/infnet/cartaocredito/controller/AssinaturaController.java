package br.edu.infnet.cartaocredito.controller;

import br.edu.infnet.cartaocredito.service.AssinaturaService;
import br.edu.infnet.cartaocredito.service.dto.AssinaturaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/assinaturas")
public class AssinaturaController {

    @Autowired
    private AssinaturaService assinaturaService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarAssinatura(@RequestBody AssinaturaDto dto) {
        try {
            AssinaturaDto assinaturaDto = assinaturaService.criar(dto);
            return ResponseEntity.ok(assinaturaDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarAssinaturaPorId(@PathVariable UUID id) {
        try {
            AssinaturaDto assinaturaDto = assinaturaService.buscarPorId(id);
            return ResponseEntity.ok(assinaturaDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarAssinatura(@PathVariable UUID id, @RequestBody AssinaturaDto dto) {
        try {
            AssinaturaDto assinaturaDto = assinaturaService.atualizar(id, dto);
            return ResponseEntity.ok(assinaturaDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarAssinatura(@PathVariable UUID id) {
        try {
            assinaturaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
