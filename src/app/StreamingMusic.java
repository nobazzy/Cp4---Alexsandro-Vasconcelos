package app;

import model.Musicas;
import model.Playlist;
import usuario.Usuario;
import usuario.UsuarioFree;
import usuario.UsuarioPremium;

import java.util.ArrayList;
import java.util.Scanner;

public class StreamingMusic {

    private static final ArrayList<Playlist> playlists = new ArrayList<>();
    private static final ArrayList<Usuario> usuarios = new ArrayList<>();

    private static final Musicas[] colecao = new Musicas[1000];
    private static int totalMusicas = 0;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int opcao;

        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);

        System.out.println("\nEncerrando o sistema.");
        scanner.close();
    }

    public static void exibirMenu() {
        System.out.println("\n=== SISTEMA DE STREAMING ===");
        System.out.println("1. Cadastrar música");
        System.out.println("2. Listar Musica/Apagar Musica");
        System.out.println("3. Buscar por título");
        System.out.println("4. Listar músicas por gênero");
        System.out.println("5. Cadastrar usuario");
        System.out.println("6. Buscar usuario");
        System.out.println("0. Sair");
        System.out.print("Escolha: ");
    }

    public static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static Playlist BuscarPorGenero(String nome) {
        for (Playlist p : playlists) {
            if (p.getPlayNome().equalsIgnoreCase(nome)) {
                return p;
            }
        }
        return null;
    }

    public static void processarOpcao(int opcao) {

        switch (opcao) {

            case 1 -> cadastrarMusica();

            case 2 -> {
                listarMusicas();

                System.out.println("Deseja deletar alguma musica? S = 1, N = 0");
                int opc = Integer.parseInt(scanner.nextLine());

                if (opc == 1) {
                    System.out.println("Escolha um indice: ");
                    int indice = Integer.parseInt(scanner.nextLine());
                    deletarMusica(indice);
                }
            }

            case 3 -> buscarPorTitulo();

            case 4 -> {
                System.out.println("Digite um gênero: ");
                String genero = scanner.nextLine();

                Playlist p = BuscarPorGenero(genero);

                if (p != null) {
                    p.listarMusicas();
                } else {
                    System.out.println("Nenhuma música desse gênero.");
                }
            }

            case 5 -> cadastrarUsuarios();

            case 6 -> {
                System.out.println("Digite o nome do usuario: ");
                String nome = scanner.nextLine();

                Usuario u = buscarUsuario(nome);

                if (u == null) {
                    System.out.println("usuario.Usuario nao encontrado.");
                    break;
                }

                u.listarMusicas();
            }

            case 0 -> {}

            default -> System.out.println("Opção inválida.");
        }
    }


    public static void cadastrarUsuarios() {

        System.out.println("Digite o nome do novo usuario: ");
        String nome = scanner.nextLine();

        if (buscarUsuario(nome) != null) {
            System.out.println("usuario.Usuario ja existe.");
            return;
        }

        System.out.println("Digite o email: ");
        String email = scanner.nextLine();

        System.out.println("Tipo de conta:");
        System.out.println("1 - Free");
        System.out.println("2 - Premium");
        int tipo = Integer.parseInt(scanner.nextLine());

        Usuario usuario;

        if (tipo == 1) {
            usuario = new UsuarioFree(nome, email);
        } else {
            System.out.println("Digite o plano (Mensal/Anual/Familiar): ");
            String plano = scanner.nextLine();

            usuario = new UsuarioPremium(nome, email, plano);
        }

        usuarios.add(usuario);
        System.out.println("usuario.Usuario cadastrado com sucesso!");
    }

    public static Usuario buscarUsuario(String nome) {
        for (Usuario u : usuarios) {
            if (u.getNome().equalsIgnoreCase(nome)) {
                return u;
            }
        }
        return null;
    }

    public static void cadastrarMusica() {
        System.out.println("\n--- CADASTRAR MÚSICA ---");

        System.out.println("Digite o nome do usuário:");
        String nomeUsuario = scanner.nextLine();

        Usuario usuario = buscarUsuario(nomeUsuario);

        if (usuario == null) {
            System.out.println("usuario.Usuario nao encontrado. Cadastre primeiro!");
            return;
        }

        System.out.println("Título: ");
        String titulo = scanner.nextLine();

        System.out.println("Artista: ");
        String artista = scanner.nextLine();

        System.out.println("Duração (segundos): ");
        int duracao = Integer.parseInt(scanner.nextLine());

        System.out.println("Gênero: ");
        String genero = scanner.nextLine();

        Musicas musica;

        try {
            musica = new Musicas(titulo, artista, duracao, genero);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (totalMusicas < colecao.length) {
            colecao[totalMusicas] = musica;
            totalMusicas++;
            System.out.println("Música cadastrada com sucesso.");
        }

        Playlist play = BuscarPorGenero(genero);

        if (play == null) {
            play = new Playlist(genero);
            playlists.add(play);
        }

        play.adicionarMusicas(musica);

        usuario.adicionarMusica(musica);
    }

    public static void deletarMusica(int indice) {
        if (indice < 0 || indice >= totalMusicas) {
            System.out.println("Indice invalido.");
            return;
        }

        for (int i = indice; i < totalMusicas - 1; i++) {
            colecao[i] = colecao[i + 1];
        }

        colecao[totalMusicas - 1] = null;
        totalMusicas--;

        System.out.println("Musica deletada com sucesso.");
    }

    public static void listarMusicas() {
        System.out.println("\n--- MÚSICAS CADASTRADAS ---");

        if (totalMusicas == 0) {
            System.out.println("Nenhuma música cadastrada.");
            return;
        }

        for (int i = 0; i < totalMusicas; i++) {
            System.out.println("------------------------");
            System.out.println("Indice: " + i);
            System.out.println("Título: " + colecao[i].getTitulo());
            System.out.println("Artista: " + colecao[i].getArtista());
            System.out.println("Duração: " + formatarDuracao(colecao[i].getDuracao()));
            System.out.println("Gênero: " + colecao[i].getGenero());
        }
    }

    public static void buscarPorTitulo() {
        System.out.println("\n--- BUSCAR POR TÍTULO ---");

        System.out.print("Digite o título: ");
        String busca = scanner.nextLine();

        boolean encontrado = false;

        for (int i = 0; i < totalMusicas; i++) {
            if (colecao[i].getTitulo().equalsIgnoreCase(busca)) {
                System.out.println(colecao[i]);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Música não encontrada.");
        }
    }

    public static String formatarDuracao(int segundos) {
        int min = segundos / 60;
        int seg = segundos % 60;
        return String.format("%d:%02d", min, seg);
    }
}