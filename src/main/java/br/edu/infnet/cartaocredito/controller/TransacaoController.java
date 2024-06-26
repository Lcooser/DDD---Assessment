package br.edu.infnet.cartaocredito.controller;

import br.edu.infnet.cartaocredito.service.TransacaoService;
import br.edu.infnet.cartaocredito.service.dto.TransacaoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping("/autorizar")
    public ResponseEntity<String> autorizarTransacao(@RequestBody TransacaoDto dto) {
        try {
            transacaoService.autorizarTransacao(dto);
            return ResponseEntity.ok("Transação autorizada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
