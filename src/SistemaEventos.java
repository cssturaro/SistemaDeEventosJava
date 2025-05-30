import jdk.jfr.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SistemaEventos {

    public static List<Usuario> usuarios = new ArrayList<>();
    public static List<Evento> eventos = new ArrayList<>();

    public static void cadastrarUsuario(String nome, Cidade cidade, char genero, int idade){
        usuarios.add(new Usuario(nome, cidade, genero, idade));
    }

    public static void removerUsuario(int id){
        usuarios.remove(usuarios.get(id));
        AppController.atualizarID();
        Usuario.numUsuarios--;
    }

    public static void editarUsuario(Usuario usuario, String nome, Cidade cidade, char genero, int idade){
        usuario.nome = nome;
        usuario.cidade = cidade;
        usuario.genero = genero;
        usuario.idade = idade;
    }

    public static void cadastrarEvento(String nome, Cidade cidade, String endereco, CategoriaEvento catagoria, LocalDateTime dataHora, String descricao){
        eventos.add(new Evento(nome, cidade, endereco, catagoria, dataHora, descricao));
    }

    public static void removerEvento(int id){
        eventos.remove(eventos.get(id));
        AppController.atualizarID();
        Evento.numEventos--;
    }

    public static void editarEvento(Evento evento, String nome, Cidade cidade, String endereco, CategoriaEvento categoria, LocalDateTime dataHora, String descricao){
        evento.nome = nome;
        evento.cidade = cidade;
        evento.endereco = endereco;
        evento.categoria = categoria;
        evento.dataHora = dataHora;
        evento.descricao = descricao;
    }

    public static void confirmarPresenca(Usuario usuario, Evento evento){
        evento.participantes.add(usuario);
        usuario.eventosInscritos.add(evento);
    }

    public static void cancelarPresenca(Usuario usuario, Evento evento){
        evento.participantes.remove(usuario);
        usuario.eventosInscritos.remove(evento);
    }

}
