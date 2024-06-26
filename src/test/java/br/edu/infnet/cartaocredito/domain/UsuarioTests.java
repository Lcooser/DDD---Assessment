package br.edu.infnet.cartaocredito.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class UsuarioTests {

    @Test
    public void deve_criar_um_usuario_corretamente() throws Exception {
        Usuario usuario = new Usuario();
        Cartao cartao = new Cartao();
        cartao.setNumero("1234567890123456");
        cartao.setAtivo(true);
        cartao.setValidade(LocalDate.now().plusYears(1));
        cartao.setLimite(1000.0);

        Plano plano = new Plano();
        plano.setNome("Plano Teste");
        plano.setDescricao("Descrição");
        plano.setValor(100.0);
        plano.setAtivo(true);

        usuario.criar("Xpto", "teste@teste.com", "123456", "39048007011", cartao, plano);

        Assertions.assertTrue(usuario.getCpf().equals("39048007011"));
        Assertions.assertTrue(usuario.getNome().equals("Xpto"));
        Assertions.assertTrue(usuario.getEmail().equals("teste@teste.com"));
        Assertions.assertTrue(usuario.getSenha().equals("MTIzNDU2"));
        Assertions.assertFalse(usuario.getCartoes().isEmpty());
        Assertions.assertFalse(usuario.getAssinaturas().isEmpty());
        Assertions.assertEquals(1, usuario.getPlaylists().size());
        Assertions.assertEquals("Musicas Favoritas", usuario.getPlaylists().get(0).getNome());
    }

    @Test
    public void nao_deve_criar_um_usuario_com_cpf_invalido() {
        Usuario usuario = new Usuario();
        Cartao cartao = new Cartao();
        cartao.setNumero("1234567890123456");
        cartao.setAtivo(true);
        cartao.setValidade(LocalDate.now().plusYears(1));
        cartao.setLimite(1000.0);

        Plano plano = new Plano();
        plano.setNome("Plano Teste");
        plano.setDescricao("Descrição");
        plano.setValor(100.0);
        plano.setAtivo(true);

        Assertions.assertThrows(Exception.class, () -> {
            usuario.criar("Xpto", "teste@teste.com", "123456", "12347896311", cartao, plano);
        });
    }
}
