package br.edu.infnet.cartaocredito.controller;

import br.edu.infnet.cartaocredito.service.PlanoService;
import br.edu.infnet.cartaocredito.service.dto.PlanoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/planos")
public class PlanoController {

    @Autowired
    private PlanoService planoService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarPlano(@RequestBody PlanoDto dto) {
        try {
            PlanoDto planoDto = planoService.criar(dto);
            return ResponseEntity.ok(planoDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPlanoPorId(@PathVariable UUID id) {
        try {
            PlanoDto planoDto = planoService.buscarPorId(id);
            return ResponseEntity.ok(planoDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPlano(@PathVariable UUID id, @RequestBody PlanoDto dto) {
        try {
            PlanoDto planoDto = planoService.atualizar(id, dto);
            return ResponseEntity.ok(planoDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPlano(@PathVariable UUID id) {
        try {
            planoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
