package cvb.com.br.tasks.model;

import java.util.Date;

public class Task {

    private String descricao;
    private Date data;
    private int completa;
    private int idPrioridade;

    public Task(String descricao, Date data, int completa, int idPrioridade) {
        this.descricao = descricao;
        this.data = data;
        this.completa = completa;
        this.idPrioridade = idPrioridade;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getData() {
        return data;
    }

    public int getCompleta() {
        return completa;
    }

    public int getIdPrioridade() {
        return idPrioridade;
    }
}
