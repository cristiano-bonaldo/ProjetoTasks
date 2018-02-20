package cvb.com.br.tasks.model;

public class Task {

    private String descricao;
    private String data;
    private int completa;
    private int prioridade;

    public Task(String descricao, String data, int completa, int prioridade) {
        this.descricao = descricao;
        this.data = data;
        this.completa = completa;
        this.prioridade = prioridade;
    }
}
