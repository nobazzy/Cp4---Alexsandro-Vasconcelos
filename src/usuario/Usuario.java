package usuario;

import model.Musicas;
import model.Playlist;

import java.util.ArrayList;

public class Usuario {

    protected String nome;
    protected String email;
    protected ArrayList<Playlist> playlists;
    protected ArrayList<Musicas> historicoReproducao;

    public Usuario(String nome, String email) {
        setNome(nome);
        setEmail(email);
        this.playlists = new ArrayList<>();
        this.historicoReproducao = new ArrayList<>();
    }

    // REPRODUZIR MÚSICA (pode ser sobrescrito)
    public void reproduzirMusica(Musicas musica) {
        System.out.println("🎵 Reproduzindo: " + musica.getTitulo());
        historicoReproducao.add(musica);
    }

    //  HISTÓRICO
    public void exibirHistorico() {
        System.out.println("\n--- HISTÓRICO ---");

        if (historicoReproducao.isEmpty()) {
            System.out.println("Nenhuma música reproduzida.");
            return;
        }

        for (Musicas m : historicoReproducao) {
            System.out.println(m);
        }
    }

    //  ADICIONAR MÚSICA (USADO NA MAIN)
    public void adicionarMusica(Musicas musica) {

        if (musica == null) {
            System.out.println("Música inválida");
            return;
        }

        Playlist playlist = null;

        // procura playlist pelo gênero
        for (Playlist p : playlists) {
            if (p.getPlayNome().equalsIgnoreCase(musica.getGenero())) {
                playlist = p;
                break;
            }
        }

        // se não existir, cria
        if (playlist == null) {
            playlist = new Playlist(musica.getGenero());
            playlists.add(playlist);
        }

        playlist.adicionarMusicas(musica);
    }

    //  LISTAR MÚSICAS DO USUÁRIO (USADO NA MAIN)
    public void listarMusicas() {

        if (playlists.isEmpty()) {
            System.out.println("Usuário não possui músicas.");
            return;
        }

        for (Playlist p : playlists) {
            System.out.println("\nGênero: " + p.getPlayNome());
            p.listarMusicas();
        }
    }

    // getters/setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.nome = nome.trim();
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email.trim();
    }
}