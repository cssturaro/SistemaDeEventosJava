import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Evento {
    public static int numEventos;

    public String id;
    final String nome;
    final Cidade cidade;
    final String endereco;
    final CategoriaEvento categoria;
    final LocalDateTime dataHora;
    final String descricao;
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
