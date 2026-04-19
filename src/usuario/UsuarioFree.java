package usuario;

import model.Musicas;
import model.Playlist;

public class UsuarioFree extends Usuario {

    private int contadorReproducoes;
    private static final int MAX_PLAYLISTS = 3;

    public UsuarioFree(String nome, String email) {
        super(nome, email);
        this.contadorReproducoes = 0;
    }

    @Override
    public void reproduzirMusica(Musicas musica) {
        contadorReproducoes++;

        if (contadorReproducoes % 3 == 0) {
            exibirAnuncio();
        }

        super.reproduzirMusica(musica);
    }

    public void criarPlaylist(String nome) {
        if (playlists.size() >= MAX_PLAYLISTS) {
            System.out.println(" Limite de playlists atingido!");
            return;
        }

        Playlist p = new Playlist(nome);
        playlists.add(p);
        System.out.println(" model.Playlist criada!");
    }

    private void exibirAnuncio() {
        System.out.println(" ANÚNCIO: Assine Premium!");
    }
}