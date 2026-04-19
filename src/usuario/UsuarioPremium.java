package usuario;

import model.Musicas;

import java.util.ArrayList;

public class UsuarioPremium extends Usuario {

    private String plano;
    private ArrayList<Musicas> musicasBaixadas;

    public UsuarioPremium(String nome, String email, String plano) {
        super(nome, email);
        this.plano = plano;
        this.musicasBaixadas = new ArrayList<>();
    }

    @Override
    public void reproduzirMusica(Musicas musica) {
        System.out.println("🎵 ALTA QUALIDADE: " + musica.getTitulo());
        historicoReproducao.add(musica);
    }

    public void baixarMusica(Musicas musica) {
        if (!musicasBaixadas.contains(musica)) {
            musicasBaixadas.add(musica);
            System.out.println("⬇ Baixada!");
        }
    }

    public void listarMusicasBaixadas() {
        for (Musicas m : musicasBaixadas) {
            System.out.println(m);
        }
    }
}