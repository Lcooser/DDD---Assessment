package br.edu.infnet.cartaocredito.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Cartao> cartoes = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Playlist> playlists = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Assinatura> assinaturas = new ArrayList<>();

    private static final String DEFAULT_PLAYLIST_NAME = "Musicas Favoritas";

    public void criar(String nome, String email, String senha, String cpf, Cartao cartao, Plano plano) throws Exception {
        validarPlanoAtivo();
        validarCartaoAtivo(cartao);

        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = encodeSenha(senha);

        // Adicionar cartão ao usuário
        cartoes.add(cartao);
        cartao.setUsuario(this);

        // Criar uma assinatura se não houver uma ativa
        if (!possuiAssinaturaAtiva()) {
            Assinatura assinatura = new Assinatura();
            assinatura.setPlano(plano);
            assinatura.setDtAssinatura(LocalDateTime.now());
            assinatura.setAtivo(true);
            assinatura.setUsuario(this);
            assinaturas.add(assinatura);
        }

        // Criar uma playlist default
        Playlist playlist = new Playlist();
        playlist.setNome(DEFAULT_PLAYLIST_NAME);
        playlist.setUsuario(this);
        playlists.add(playlist);
    }

    private void validarPlanoAtivo() throws Exception {
        if (assinaturas.stream().anyMatch(Assinatura::isAtivo)) {
            throw new Exception("Usuário já possui uma assinatura ativa.");
        }
    }

    private void validarCartaoAtivo(Cartao cartao) throws Exception {
        if (!cartao.getAtivo()) {
            throw new Exception("O cartão não está ativo.");
        }
    }
    public void atualizar(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = encodeSenha(senha);
    }

    public void adicionarPlaylist(Playlist playlist) {
        playlists.add(playlist);
    }

    public void removerPlaylist(Playlist playlist) {
        playlists.remove(playlist);
    }

    public void deletar() {
        // Desativar todos os cartões do usuário
        for (Cartao cartao : cartoes) {
            cartao.setAtivo(false);
        }

        // Cancelar todas as assinaturas ativas do usuário
        for (Assinatura assinatura : assinaturas) {
            if (assinatura.isAtivo()) {
                assinatura.setAtivo(false);
            }
        }

        // Remover associação de playlists e usuário
        for (Playlist playlist : playlists) {
            playlist.setUsuario(null);
        }

        // Limpar listas
        cartoes.clear();
        assinaturas.clear();
        playlists.clear();
    }


    private boolean possuiAssinaturaAtiva() {
        return assinaturas.stream().anyMatch(Assinatura::isAtivo);
    }

    private String encodeSenha(String senha) {
        return Base64.getEncoder().encodeToString(senha.getBytes());
    }

    private boolean isValidoCpf(String CPF) {
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return false;

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);

            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return true;
            return false;
        } catch (InputMismatchException erro) {
            return false;
        }
    }
}
