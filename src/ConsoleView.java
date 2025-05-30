import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    protected static Scanner scanner = new Scanner(System.in);

    public static void limparTela() {
        final String os = System.getProperty("os.name").toLowerCase();

        try {

            if (os.contains("win"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("clear").inheritIO().start().waitFor();

        } catch (Exception e) {
            System.out.println("[ERRO] Não foi possível limpar a tela!");
        }
    }

    // FINALIZAR PROGRAMA -------------
    public static void finalizarPrograma() {
        System.out.println("\n================================================\n");
        System.out.println("                  Até a próxima!\n");
        System.out.println("================================================\n");

        System.exit(0);
    }

    // MENU PRINCIPAL -------------
    public static void menuPrincipal() {
        System.out.println("================================================\n");
        System.out.println("        [1] - Menu de Usuário");
        System.out.println("        [2] - Menu de Eventos");
        System.out.println("        [0] - Sair \n");
        System.out.println("            Digite a opção desejada!\n");
        System.out.println("================================================\n");

        int opcao = AppController.verificarOpcao(2);

        switch (opcao) {
            case 0: AppController.selecionarMenu(99); break;
            case 1: AppController.selecionarMenu(1); break;
            case 2: AppController.selecionarMenu(2); break;
        }
    }

    // MENU USUÁRIO -------------
    public static void menuUsuario() {
        System.out.println("================================================\n");
        System.out.println("        [1] - Adicionar Usuário");
        System.out.println("        [2] - Remover Usuário");
        System.out.println("        [3] - Buscar Usuário");
        System.out.println("        [0] - Voltar \n");
        System.out.println("            Digite a opção desejada!\n");
        System.out.println("================================================\n");

        int opcao = AppController.verificarOpcao(3);

        switch (opcao) {
            case 0: AppController.selecionarMenu(0); break;
            case 1: AppController.selecionarMenu(11); break;
            case 2: AppController.selecionarMenu(12); break;
            case 3: AppController.selecionarMenu(13); break;
        }
    }

    // MENU ADICIONAR USUÁRIO -------------
    public static void adicionarUsuario() {

        // Definindo nome
        System.out.print("Nome do Usuário: ");
        String nome = scanner.nextLine();
        ConsoleView.limparTela();

        // Definindo Cidade
        System.out.println("================================================\n");
        System.out.println("        Escolha a cidade do usuário:\n");
        System.out.println("        [0] - Brasília");
        System.out.println("        [1] - Belo Horizonte");
        System.out.println("        [2] - Feira de Santana");
        System.out.println("        [3] - Florianópolis");
        System.out.println("        [4] - Rio de Janeiro");
        System.out.println("        [5] - Salvador");
        System.out.println("        [6] - São Paulo\n");
        System.out.println("================================================\n");

        int opcao = AppController.verificarOpcao(6);

        Cidade cidade = Cidade.BRASILIA; // como default para poder callar metodo de SistemaEventos
        cidade = switch (opcao) {
            case 0 -> Cidade.BRASILIA;
            case 1 -> Cidade.BELO_HORIZONTE;
            case 2 -> Cidade.FEIRA_DE_SANTANA;
            case 3 -> Cidade.FLORIANOPOLIS;
            case 4 -> Cidade.RIO_DE_JANEIRO;
            case 5 -> Cidade.SALVADOR;
            case 6 -> Cidade.SAO_PAULO;
            default -> cidade;
        };
        ConsoleView.limparTela();

        // Definindo gênero
        System.out.println("================================================\n");
        System.out.println("        Gênero do usuário\n");
        System.out.println("        [0] - Masculino");
        System.out.println("        [1] - Feminino");
        System.out.println("        [2] - Outro\n");
        System.out.println("================================================\n");

        opcao = AppController.verificarOpcao(2);
        char genero = switch (opcao) {
            case 0 -> 'M';
            case 1 -> 'F';
            case 2 -> 'O';
            default -> 'D'; // como default para poder callar metodo de SistemaEventos
        };
        ConsoleView.limparTela();

        // Definindo idade
        int idade = -1; // como default para poder callar metodo de SistemaEventos
        boolean valida;

        do {
            System.out.print("Idade do usuário: ");
            try {
                idade = scanner.nextInt();
                scanner.nextLine(); // para limpar o buffer
                valida = true;
            } catch (Exception e) {
                scanner.nextLine(); // para limpar o buffer
                valida = false;
                System.out.println("[ERRO] Não foi possível ler a opção digitada! Tente novamente!");
            }
        } while (!valida || !(idade > 0));
        ConsoleView.limparTela();

        SistemaEventos.cadastrarUsuario(nome, cidade, genero, idade);
        AppController.selecionarMenu(1);
    }

    // REMOVER USUÁRIO -------------
    public static void removerUsuario() {
        System.out.println("================================================\n");
        System.out.println("        [1] - Remover por ID");
        System.out.println("        [2] - Remover por nome");
        System.out.println("        [0] - Voltar \n");
        System.out.println("            Digite a opção desejada!\n");
        System.out.println("================================================\n");

        int opcao = AppController.verificarOpcao(2);
        ConsoleView.limparTela();

        String id = "-Default";
        String nome = "-Default";

        switch (opcao){
            case 0: AppController.selecionarMenu(1); break;
            case 1: System.out.print("ID: "); id = scanner.nextLine(); break;
            case 2: System.out.print("Nome: "); nome = scanner.nextLine(); break;
        }


        if (opcao != 0){
            ConsoleView.limparTela();
            List<Usuario> listaUsuarios = AppController.buscarUsuario(opcao, id, nome);
            System.out.println("=========================================================\n");
            System.out.println("                   Usuários Encontrados");
            System.out.println("         [ID] - Nome | Cidade | Gênero | idade\n");
            AppController.listarUsuarios(listaUsuarios);
            System.out.println("\n=========================================================\n");
            System.out.println("        Digite o número de ID para remover o usuário!\n");
            System.out.println("        [0] - Para cancelar");
            System.out.println("\n=========================================================\n");

            if (Usuario.numUsuarios != 0)
                opcao = AppController.verificarOpcao(Usuario.numUsuarios);
            else
                mensagem("Não há usuários para remover!", 12);

            if (opcao == 0) AppController.selecionarMenu(12);
            else{
                SistemaEventos.removerUsuario(opcao - 1);
                AppController.selecionarMenu(12);
            }
        }
    }

    // BUSCAR USUÁRIO -------------
    public static void buscarUsuario() {
        System.out.println("================================================\n");
        System.out.println("        [1] - Buscar por ID");
        System.out.println("        [2] - Buscar por nome");
        System.out.println("        [0] - Voltar \n");
        System.out.println("            Digite a opção desejada!\n");
        System.out.println("================================================\n");

        int opcao = AppController.verificarOpcao(2);
        ConsoleView.limparTela();

        String id = "-Default";
        String nome = "-Default";

        switch (opcao){
            case 0: AppController.selecionarMenu(1); break;
            case 1: System.out.print("ID: "); id = scanner.nextLine(); break;
            case 2: System.out.print("Nome: "); nome = scanner.nextLine(); break;
        }


        if (opcao != 0){
            ConsoleView.limparTela();
            List<Usuario> listaUsuarios = AppController.buscarUsuario(opcao, id, nome);
            System.out.println("=========================================================\n");
            System.out.println("                   Usuários Encontrados");
            System.out.println("         [ID] - Nome | Cidade | Gênero | idade\n");
            AppController.listarUsuarios(listaUsuarios);
            System.out.println("\n=========================================================\n");
            System.out.println(" [0] - Voltar [1] - Remover Participação de Evento");
            System.out.println("\n=========================================================\n");

            opcao = AppController.verificarOpcao(1);

            if (opcao == 1) {
                System.out.println("Digite o ID do usuário que você deseja remover a participacao.");
                int idUsuario = AppController.verificarOpcao(Usuario.numUsuarios);
                ConsoleView.limparTela();
                System.out.println("=========================================================\n");
                System.out.println("               Lista de Eventos Inscrito:");
                System.out.println("         [ID] - Nome | Cidade | Gênero | idade\n");
                Usuario usuario = SistemaEventos.usuarios.get(idUsuario -1);
                AppController.listarEventos(usuario.eventosInscritos);
                System.out.println("=========================================================\n");
                System.out.println("Digite o seu ID para cancelar participacao no Evento:");
                int idEvento = AppController.verificarOpcao(usuario.eventosInscritos.size());
                Evento evento = SistemaEventos.eventos.get(idEvento);
                if (!(usuario.eventosInscritos.contains(evento))){
                    mensagem("Você não está inscrito nesse evento", 13);
                } else {
                    SistemaEventos.cancelarPresenca(usuario, evento);
                    AppController.selecionarMenu(13);
                }
            }
            else AppController.selecionarMenu(13);
        }
    }

    public static void mensagem(String mensagem, int redirecionar) {
        limparTela();
        System.out.println("================================================\n");
        System.out.println(mensagem);
        System.out.println("\n        [ENTER] - para retornar");
        System.out.println("================================================\n");
        scanner.nextLine();
        AppController.selecionarMenu(redirecionar);
    }

    // MENU EVENTOS -------------
    public static void menuEventos() {
        System.out.println("================================================\n");
        System.out.println("        [1] - Adicionar Evento");
        System.out.println("        [2] - Remover Evento");
        System.out.println("        [3] - Buscar Evento");
        System.out.println("        [0] - Voltar \n");
        System.out.println("            Digite a opção desejada!\n");
        System.out.println("================================================\n");

        int opcao = AppController.verificarOpcao(3);

        switch (opcao) {
            case 0: AppController.selecionarMenu(0); break;
            case 1: AppController.selecionarMenu(21); break;
            case 2: AppController.selecionarMenu(22); break;
            case 3: AppController.selecionarMenu(23); break;
        }
    }

    // ADICIONAR EVENTO -------------
    public static void adicionarEvento() {

        // Definindo nome
        System.out.print("Nome do Evento: ");
        String nome = scanner.nextLine();
        ConsoleView.limparTela();

        // Definindo Cidade
        System.out.println("================================================\n");
        System.out.println("        Escolha a cidade do evento:\n");
        System.out.println("        [0] - Brasília");
        System.out.println("        [1] - Belo Horizonte");
        System.out.println("        [2] - Feira de Santana");
        System.out.println("        [3] - Florianópolis");
        System.out.println("        [4] - Rio de Janeiro");
        System.out.println("        [5] - Salvador");
        System.out.println("        [6] - São Paulo\n");
        System.out.println("================================================\n");

        int opcao = AppController.verificarOpcao(6);

        Cidade cidade = Cidade.BRASILIA; // como default para poder callar metodo de SistemaEventos
        cidade = switch (opcao) {
            case 0 -> Cidade.BRASILIA;
            case 1 -> Cidade.BELO_HORIZONTE;
            case 2 -> Cidade.FEIRA_DE_SANTANA;
            case 3 -> Cidade.FLORIANOPOLIS;
            case 4 -> Cidade.RIO_DE_JANEIRO;
            case 5 -> Cidade.SALVADOR;
            case 6 -> Cidade.SAO_PAULO;
            default -> cidade;
        };
        ConsoleView.limparTela();

        // Definindo endereço
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        ConsoleView.limparTela();

        // Definindo Categoria
        System.out.println("================================================\n");
        System.out.println("        Escolha a categoria do evento:\n");
        System.out.println("        [0] - Cultural");
        System.out.println("        [1] - Educacional");
        System.out.println("        [2] - Esportivo");
        System.out.println("        [3] - Show");
        System.out.println("        [4] - Tecnologia");
        System.out.println("        [5] - Outro\n");
        System.out.println("================================================\n");

        opcao = AppController.verificarOpcao(5);

        CategoriaEvento categoria = CategoriaEvento.CULTURAL; // como default para poder callar metodo de SistemaEventos
        categoria = switch (opcao) {
            case 0 -> CategoriaEvento.CULTURAL;
            case 1 -> CategoriaEvento.EDUCACIONAL;
            case 2 -> CategoriaEvento.ESPORTIVO;
            case 3 -> CategoriaEvento.SHOW;
            case 4 -> CategoriaEvento.TECNOLOGIA;
            case 5 -> CategoriaEvento.OUTRO;
            default -> categoria;
        };
        ConsoleView.limparTela();

        // Definindo Horário
        LocalDateTime dataHora = AppController.pedirDateTime();
        ConsoleView.limparTela();

        // Definindo endereço
        System.out.print("Descrição do evento: ");
        String descricao = scanner.nextLine();
        ConsoleView.limparTela();

        SistemaEventos.cadastrarEvento(nome, cidade, endereco, categoria, dataHora, descricao);
        AppController.selecionarMenu(2);
    }

    // REMOVER EVENTO -------------
    public static void removerEvento() {
        System.out.println("================================================\n");
        System.out.println("        [1] - Remover por ID");
        System.out.println("        [2] - Remover por nome");
        System.out.println("        [0] - Voltar \n");
        System.out.println("            Digite a opção desejada!\n");
        System.out.println("================================================\n");

        int opcao = AppController.verificarOpcao(2);
        ConsoleView.limparTela();

        String id = "-Default";
        String nome = "-Default";
        Cidade cidade = Cidade.NULO;
        CategoriaEvento categoria = CategoriaEvento.NULO;

        switch (opcao){
            case 0: AppController.selecionarMenu(2); break;
            case 1: System.out.print("ID: "); id = scanner.nextLine(); break;
            case 2: System.out.print("Nome: "); nome = scanner.nextLine(); break;
        }


        if (opcao != 0){
            ConsoleView.limparTela();
            List<Evento> listaEventos = AppController.buscarEvento(opcao, id, nome, cidade, categoria);
            System.out.println("=========================================================\n");
            System.out.println("                   Eventos Encontrados");
            System.out.println("      [ID] - Nome | Cidade | Categoria | Data e Hora \n");
            AppController.listarEventos(listaEventos);
            System.out.println("\n=========================================================\n");
            System.out.println("        Digite o número de ID para remover o evento!\n");
            System.out.println("        [0] - Para cancelar");
            System.out.println("\n=========================================================\n");

            if (Evento.numEventos != 0)
                opcao = AppController.verificarOpcao(Evento.numEventos);
            else
                mensagem("Não há eventos para remover!", 22);

            if (opcao == 0) AppController.selecionarMenu(22);
            else{
                SistemaEventos.removerEvento(opcao - 1);
                AppController.selecionarMenu(22);
            }
        }
    }

    // BUSCAR EVENTO -------------
    public static void buscarEvento() {
        System.out.println("================================================\n");
        System.out.println("        [1] - Buscar por ID");
        System.out.println("        [2] - Buscar por nome");
        System.out.println("        [3] - Buscar por cidade");
        System.out.println("        [4] - Buscar por categoria");
        System.out.println("        [0] - Voltar \n");
        System.out.println("            Digite a opção desejada!\n");
        System.out.println("================================================\n");

        int opcao = AppController.verificarOpcao(4);
        ConsoleView.limparTela();

        String id = "-Default";
        String nome = "-Default";
        Cidade cidade = Cidade.NULO;
        CategoriaEvento categoria = CategoriaEvento.NULO;

        switch (opcao){
            case 0: AppController.selecionarMenu(2); break;
            case 1: System.out.print("ID: "); id = scanner.nextLine(); break;
            case 2: System.out.print("Nome: "); nome = scanner.nextLine(); break;
            case 3:
                System.out.println("================================================\n");
                System.out.println("        Escolha a cidade do evento:\n");
                System.out.println("        [0] - Brasília");
                System.out.println("        [1] - Belo Horizonte");
                System.out.println("        [2] - Feira de Santana");
                System.out.println("        [3] - Florianópolis");
                System.out.println("        [4] - Rio de Janeiro");
                System.out.println("        [5] - Salvador");
                System.out.println("        [6] - São Paulo\n");
                System.out.println("================================================\n");

                int opcaoCidade = AppController.verificarOpcao(6);

                cidade = Cidade.BRASILIA; // como default para poder callar metodo de SistemaEventos
                cidade = switch (opcaoCidade) {
                    case 0 -> Cidade.BRASILIA;
                    case 1 -> Cidade.BELO_HORIZONTE;
                    case 2 -> Cidade.FEIRA_DE_SANTANA;
                    case 3 -> Cidade.FLORIANOPOLIS;
                    case 4 -> Cidade.RIO_DE_JANEIRO;
                    case 5 -> Cidade.SALVADOR;
                    case 6 -> Cidade.SAO_PAULO;
                    default -> cidade;
                };
                break;
            case 4:
                System.out.println("================================================\n");
                System.out.println("        Escolha a categoria do evento:\n");
                System.out.println("        [0] - Cultural");
                System.out.println("        [1] - Educacional");
                System.out.println("        [2] - Esportivo");
                System.out.println("        [3] - Show");
                System.out.println("        [4] - Tecnologia");
                System.out.println("        [5] - Outro\n");
                System.out.println("================================================\n");

                int opcaoCategoria= AppController.verificarOpcao(5);

                categoria = CategoriaEvento.CULTURAL; // como default para poder callar metodo de SistemaEventos
                categoria = switch (opcaoCategoria) {
                    case 0 -> CategoriaEvento.CULTURAL;
                    case 1 -> CategoriaEvento.EDUCACIONAL;
                    case 2 -> CategoriaEvento.ESPORTIVO;
                    case 3 -> CategoriaEvento.SHOW;
                    case 4 -> CategoriaEvento.TECNOLOGIA;
                    case 5 -> CategoriaEvento.OUTRO;
                    default -> categoria;
                };
                break;
        }


        if (opcao != 0){
            ConsoleView.limparTela();
            List<Evento> listaEventos = AppController.buscarEvento(opcao, id, nome, cidade, categoria);
            System.out.println("=========================================================\n");
            System.out.println("                   Eventos Encontrados");
            System.out.println("      [ID] - Nome | Cidade | Categoria | Data e Hora \n");
            AppController.listarEventos(listaEventos);
            System.out.println("\n=========================================================\n");
            System.out.println(" [0] - Voltar [1] - Participar de Evento");
            System.out.println("\n=========================================================\n");

            opcao = AppController.verificarOpcao(1);

            if (opcao == 1) {
                System.out.println("Digite o ID do evento que você deseja participar.");
                int idEvento = AppController.verificarOpcao(Evento.numEventos);
                ConsoleView.limparTela();
                System.out.println("=========================================================\n");
                System.out.println("                     Lista de Usuários:");
                System.out.println("         [ID] - Nome | Cidade | Gênero | idade\n");
                AppController.listarUsuarios(SistemaEventos.usuarios);
                System.out.println("=========================================================\n");
                System.out.println("Digite o seu ID para se inscrever no Evento:");
                int idUsuario = AppController.verificarOpcao(Usuario.numUsuarios);
                Usuario usuario = SistemaEventos.usuarios.get(idUsuario - 1);
                Evento evento = SistemaEventos.eventos.get(idEvento -1 );
                if (usuario.eventosInscritos.contains(evento))
                    mensagem("Você já está inscrito nesse evento", 23);
                else{
                    SistemaEventos.confirmarPresenca(usuario, evento);
                    AppController.selecionarMenu(23);
                }
            }
            else AppController.selecionarMenu(23);
        }
    }
}
