package cvb.com.br.tasks.model;

public class Priority {

    public int Id;
    public String Description;

    public Priority(int id, String descricao) {
        this.Id = id;
        this.Description = descricao;
    }

    public int getId() {
        return Id;
    }

    public String getDescription() {
        return Description;
    }

    @Override
    public String toString() {
        return "Priority[ " + "id:" + Id + " | descricao:" + Description + "]";
    }
}
