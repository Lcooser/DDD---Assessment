package br.edu.infnet.cartaocredito.repository;

import br.edu.infnet.cartaocredito.domain.Assinatura;
import br.edu.infnet.cartaocredito.domain.Banda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AssinaturaRepository extends JpaRepository<Assinatura, Integer> {
    Optional<Assinatura> findById(UUID id);
}
