import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class AppController {
    protected static Scanner scanner = new Scanner(System.in);

    public static void iniciar() {
        GerenciadorDados.carregarDados();
        ConsoleView.limparTela();
        ConsoleView.menuPrincipal();
    }

    public static int verificarOpcao(int opcoes) {

        do {
            try {
                System.out.print(">  ");
                int opcao = scanner.nextInt();
                scanner.nextLine(); // para limpar o buffer
                if (opcao >= 0 && opcao <= opcoes)
                    return opcao;
                else
                    System.out.println("[ERRO] Digite uma opção válida!");
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("[ERRO] Não foi possível ler a opção digitada! Tente novamente!");
            }
        } while (true);

    }

    public static void selecionarMenu(int menuID) {
        ConsoleView.limparTela();

        switch (menuID) {
            case 0: ConsoleView.menuPrincipal(); break;
            case 1: ConsoleView.menuUsuario(); break;
            case 11: ConsoleView.adicionarUsuario(); break;
            case 12: ConsoleView.removerUsuario(); break;
            case 13: ConsoleView.buscarUsuario(); break;
            case 14: ConsoleView.editarUsuario(); break;
            case 2: ConsoleView.menuEventos(); break;
            case 21: ConsoleView.adicionarEvento(); break;
            case 22: ConsoleView.removerEvento(); break;
            case 23: ConsoleView.buscarEvento(); break;
            case 24: ConsoleView.editarEvento(); break;
            case 99:
                GerenciadorDados.salvarDados(SistemaEventos.usuarios, SistemaEventos.eventos);
                ConsoleView.finalizarPrograma();
                break;
        }
    }

    public static String formatarCidade(Cidade cidade) {
        String nomeFormatado = cidade.name().toLowerCase().replace("_", " ");
        String[] palavras = nomeFormatado.split(" ");
        StringBuilder resultado = new StringBuilder();

        for (String palavra : palavras) {
            if (!palavra.isEmpty()) {
                resultado.append(Character.toUpperCase(palavra.charAt(0)))
                        .append(palavra.substring(1))
                        .append(" ");
            }
        }

        return resultado.toString().trim();
    }

    public static String formatarCategoria(CategoriaEvento categoria) {
        String nomeFormatado = categoria.name().toLowerCase().replace("_", " ");
        String[] palavras = nomeFormatado.split(" ");
        StringBuilder resultado = new StringBuilder();

        for (String palavra : palavras) {
            if (!palavra.isEmpty()) {
                resultado.append(Character.toUpperCase(palavra.charAt(0)))
                        .append(palavra.substring(1))
                        .append(" ");
            }
        }

        return resultado.toString().trim();
    }

    public static List<Usuario> buscarUsuario(int opcao, String id, String nome) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();

        switch (opcao) {
            case 1:
                for (Usuario usuario : SistemaEventos.usuarios) {
                    if ((usuario.id).contains(id)) {
                        usuariosEncontrados.add(usuario);
                    }
                }
                break;
            case 2:
                for (Usuario usuario : SistemaEventos.usuarios) {
                    if ((usuario.nome).contains(nome)) {
                        usuariosEncontrados.add(usuario);
                    }
                }
                break;
        }

        return usuariosEncontrados;
    }

    public static void listarUsuarios(List<Usuario> usuarios) {
        //[ID] - Nome | Cidade | gênero | idade

        AppController.atualizarID();

        for (Usuario usuario : usuarios) {
            System.out.printf((" [%s] - %s | %s | %c | %d %n"), Integer.parseInt(usuario.id) + 1, usuario.nome, formatarCidade(usuario.cidade), usuario.genero, usuario.idade);
            System.out.print(" Eventos Inscrito: ");
            for (Evento evento : usuario.eventosInscritos) {
                System.out.printf(("[%s] - %s, "), evento.id, evento.nome);
            }
            System.out.println();
        }

    }

    public static List<Evento> buscarEvento(int opcao, String id, String nome, Cidade cidade, CategoriaEvento categoria) {
        List<Evento> eventosEncontrados = new ArrayList<>();

        switch (opcao) {
            case 1:
                for (Evento evento : SistemaEventos.eventos) {
                    if ((evento.id).contains(id)) {
                        eventosEncontrados.add(evento);
                    }
                }
                break;
            case 2:
                for (Evento evento : SistemaEventos.eventos) {
                    if ((evento.nome).contains(nome)) {
                        eventosEncontrados.add(evento);
                    }
                }
                break;
            case 3:
                for (Evento evento : SistemaEventos.eventos) {
                    if (evento.cidade == cidade) {
                        eventosEncontrados.add(evento);
                    }
                }
                break;
            case 4:
                for (Evento evento : SistemaEventos.eventos) {
                    if (evento.categoria == categoria) {
                        eventosEncontrados.add(evento);
                    }
                }
                break;
        }

        eventosEncontrados.sort(Comparator.comparing((Evento evento) -> evento.dataHora));
        return eventosEncontrados;
    }

    public static void listarEventos(List<Evento> eventos) {
        //[ID] - Nome | Cidade | Categoria | dataHora | Endereco | Descricao

        AppController.atualizarID();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime horarioAtual = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);

        for (Evento evento : eventos) {
            if (horarioAtual.isEqual(evento.dataHora.withMinute(0).withSecond(0).withNano(0)))
                System.out.println("(OCORRENDO AGORA!)");
            else if (horarioAtual.isAfter(evento.dataHora.withMinute(0).withSecond(0).withNano(0)))
                System.out.println("(JÁ OCORREU!)");
            System.out.printf((" [%s] - %s | %s | %s | %s \n"), Integer.parseInt(evento.id) + 1, evento.nome, formatarCidade(evento.cidade), formatarCategoria(evento.categoria), evento.dataHora.format(formatter));
            System.out.println(" [Endereço] " + evento.endereco);
            System.out.println(" [Descricao] " + evento.descricao);
            System.out.println();
        }

    }

    public static LocalDateTime pedirDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.print("Data e hora do evento (aaaa-MM-dd HH:mm:ss): ");

        while (true) {
            try {
                String entrada = scanner.nextLine().trim(); // Apenas aqui!
                return LocalDateTime.parse(entrada, formatter);
            } catch (Exception e) {
                System.out.println("Digite de modo válido! (aaaa-MM-dd HH:mm:ss)");
            }
        }
    }


    public static void atualizarID(){
        for (Usuario usuario : SistemaEventos.usuarios){
            usuario.id = Integer.toString(SistemaEventos.usuarios.indexOf(usuario));
        }

        for (Evento evento : SistemaEventos.eventos){
            evento.id = Integer.toString(SistemaEventos.eventos.indexOf(evento));
        }
    }
}


