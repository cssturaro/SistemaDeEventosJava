import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Evento {
    public static int numEventos;

    public String id;
    public String nome;
    public Cidade cidade;
    public String endereco;
    public CategoriaEvento categoria;
    public LocalDateTime dataHora;
    public String descricao;
    public List<Usuario> participantes = new ArrayList<>();
    public int[] loadingParticipantes;

    public Evento(String nome, Cidade cidade, String endereco, CategoriaEvento categoria, LocalDateTime dataHora, String descricao){
        numEventos++;

        this.id = String.valueOf(numEventos);
        this.nome = nome;
        this.cidade = cidade;
        this.endereco = endereco;
        this.categoria = categoria;
        this.dataHora = dataHora;
        this.descricao = descricao;
    }
}
