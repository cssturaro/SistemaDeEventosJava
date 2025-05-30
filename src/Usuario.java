import java.util.ArrayList;
import java.util.List;

public class Usuario {
    public static int numUsuarios;

    public String id;
    public String nome;
    public Cidade cidade;
    public char genero;
    public int idade;
    public List<Evento> eventosInscritos = new ArrayList<>();

    public Usuario(String nome, Cidade cidade, char genero, int idade){
        numUsuarios++;

        this.id = String.valueOf(numUsuarios);
        this.nome = nome;
        this.cidade = cidade;
        this.genero = genero;
        this.idade = idade;
    }

}
