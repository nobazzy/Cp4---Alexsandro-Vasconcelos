package model;

import java.util.ArrayList;

public class Playlist {

    private ArrayList<Musicas> musicas = new ArrayList<>();
    private String nome;

    public Playlist(){
        this("Sem nome");
    }

    public Playlist(String nome){
        setNome(nome);
    }

    public String getPlayNome(){
        return nome;
    }

    public void setNome(String nome){
        if(nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("Nome da model.Playlist invalido.");
        }
        this.nome = nome.trim();
    }

    public void adicionarMusicas(Musicas m) {
        if (m == null){
            System.out.println("Música invalida.");
            return;
        }

        for (Musicas musica: musicas){
            if(musica.getTitulo().equalsIgnoreCase(m.getTitulo())
                    && musica.getArtista().equalsIgnoreCase(m.getArtista())){
                System.out.println("Musica ja disponivel na playlist.");
                return;
            }
        }

        musicas.add(m);
    }


    public void removerMusica(String titulo) {
        boolean removido = musicas.removeIf(m ->
                m.getTitulo().equalsIgnoreCase(titulo));

        if (!removido) {
            System.out.println("Música não encontrada.");
        }
    }


    public void removerMusica(int indice) {
        if (indice < 0 || indice >= musicas.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        musicas.remove(indice);
    }

    public void listarMusicas() {

        if (musicas.isEmpty()) {
            System.out.println("model.Playlist vazia.");
            return;
        }

        for (Musicas m : musicas) {
            System.out.println("------------------------");
            System.out.println(m);
        }
    }
}