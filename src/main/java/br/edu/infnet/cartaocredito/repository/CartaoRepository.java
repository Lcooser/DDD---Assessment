package br.edu.infnet.cartaocredito.repository;

import br.edu.infnet.cartaocredito.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    Optional<Cartao> findById(UUID id);
}
