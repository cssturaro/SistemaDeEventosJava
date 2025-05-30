import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GerenciadorDados {

    public static String CAMINHO_EVENTS_DATA = "./events_data.txt";

    private static void salvarEventos(List<Evento> eventos){
        // id;nome;cidade;endereço;categoria;dataHora;descrição
        // lista participantes

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_EVENTS_DATA, true))){
            bw.write("+eventos");
            bw.newLine();
            for (Evento evento : eventos) {
                String linhaEvento = evento.id + ";" + evento.nome + ";"
                        + AppController.formatarCidade(evento.cidade)
                        + ";" +  evento.endereco + ";"
                        + AppController.formatarCategoria(evento.categoria) + ";" +
                        evento.dataHora.format(formatter) + ";" + evento.descricao;

                StringBuilder listaParticipantes = new StringBuilder();
                for (Usuario participante : evento.participantes){
                    listaParticipantes.append(participante.id).append(";");
                }

                bw.write(linhaEvento);
                bw.newLine();
                bw.write(listaParticipantes.toString());
                bw.newLine();

            }
            bw.write("-eventos");
            bw.newLine();
        } catch (IOException e) {
            System.out.println("[ERRO] Não foi possível salvar eventos!");
        }

    }

    private static void salvarUsuarios(List<Usuario> usuarios){
        // id, nome, cidade, gênero, idade

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_EVENTS_DATA))){
            bw.write("+usuarios");
            bw.newLine();
            for (Usuario usuario : usuarios) {
                String linhaUsuario = usuario.id + ";" + usuario.nome + ";"
                        + AppController.formatarCidade(usuario.cidade) + ";" + usuario.genero + ";"
                        + usuario.idade;
                bw.write(linhaUsuario);
                bw.newLine();
            }
            bw.write("-usuarios");
            bw.newLine();
        } catch (IOException e) {
            System.out.println("[ERRO] Não foi possível salvar usuários!");
        }
    }


    public static void salvarDados(List<Usuario> usuarios, List<Evento> eventos){
        AppController.atualizarID();
        GerenciadorDados.salvarUsuarios(usuarios);
        GerenciadorDados.salvarEventos(eventos);
    }

    // só posso carregar eventos depois de carregar usuários!
    private static void carregarEventos(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_EVENTS_DATA))){
            String linha;
            linha = br.readLine();

            while (!linha.equals("+eventos")){
                linha = br.readLine();
            }

            while (!linha.equals("-eventos")) {

                int idEvento = 0;
                for (int i = 1; i <= 2; i++) {
                    linha = br.readLine();

                    if (linha.equals("-eventos")) break;
                    if (linha.isEmpty()) break;

                    if (i == 1) {
                        String[] atributosEvento = linha.split(";");

                        String id = atributosEvento[0];
                        String nome = atributosEvento[1];

                        Cidade cidade = switch (atributosEvento[2]) {
                            case "Brasilia" -> Cidade.BRASILIA;
                            case "Belo Horizonte" -> Cidade.BELO_HORIZONTE;
                            case "Feira De Santana" -> Cidade.FEIRA_DE_SANTANA;
                            case "Florianopolis" -> Cidade.FLORIANOPOLIS;
                            case "Rio De Janeiro" -> Cidade.RIO_DE_JANEIRO;
                            case "Salvador" -> Cidade.SALVADOR;
                            case "Sao Paulo" -> Cidade.SAO_PAULO;
                            default -> Cidade.NULO;
                        };

                        String endereco = atributosEvento[3];

                        CategoriaEvento categoria = switch (atributosEvento[4]) {
                            case "Cultural" -> CategoriaEvento.CULTURAL;
                            case "Educacional" -> CategoriaEvento.EDUCACIONAL;
                            case "Esportivo" -> CategoriaEvento.ESPORTIVO;
                            case "Show" -> CategoriaEvento.SHOW;
                            case "Tecnologia" -> CategoriaEvento.TECNOLOGIA;
                            case "Outro" -> CategoriaEvento.OUTRO;
                            default -> CategoriaEvento.NULO;
                        };

                        LocalDateTime dataHora = LocalDateTime.parse(atributosEvento[5], formatter);

                        String descricao = atributosEvento[6];

                        SistemaEventos.cadastrarEvento(nome, cidade, endereco, categoria, dataHora, descricao);
                        idEvento = Integer.parseInt(id);
                    } else {
                        String[] listaIDsParticipantes = linha.split(";");
                        for (String id : listaIDsParticipantes) {
                            try {
                                int idUsuario = Integer.parseInt(id);
                                SistemaEventos.confirmarPresenca(SistemaEventos.usuarios.get(idUsuario),
                                        SistemaEventos.eventos.get(idEvento));
                            } catch (Exception e) {
                                System.out.printf(("Não foi possível adicionar %s a lista de participantes"), id);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("[ERRO] Não foi possível carregar eventos;");
        }
    }

    private static void carregarUsuarios(){
        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_EVENTS_DATA))){
            String linha;
            while ((linha = br.readLine()) != null){
                if (linha.equals("+usuarios")) {
                    while (!linha.equals("-usuarios")) {
                        linha = br.readLine();
                        if (linha.isEmpty() || linha.equals("-usuarios")) break;
                        String[] atributosUsuario = linha.split(";");

                        String nome = atributosUsuario[1];

                        Cidade cidade = switch (atributosUsuario[2]) {
                            case "Brasilia" -> Cidade.BRASILIA;
                            case "Belo Horizonte" -> Cidade.BELO_HORIZONTE;
                            case "Feira De Santana" -> Cidade.FEIRA_DE_SANTANA;
                            case "Florianopolis" -> Cidade.FLORIANOPOLIS;
                            case "Rio De Janeiro" -> Cidade.RIO_DE_JANEIRO;
                            case "Salvador" -> Cidade.SALVADOR;
                            case "Sao Paulo" -> Cidade.SAO_PAULO;
                            default -> Cidade.NULO;
                        };

                        char genero = atributosUsuario[3].charAt(0);

                        int idade = Integer.parseInt(atributosUsuario[4]);

                        SistemaEventos.cadastrarUsuario(nome, cidade, genero, idade);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("[ERRO] Não foi possível carregar usuários!");
        }
    }

    public static void carregarDados(){
        carregarUsuarios();
        carregarEventos();
    }
}
