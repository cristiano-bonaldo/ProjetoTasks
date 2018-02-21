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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Priority priority = (Priority) o;

        return Id == priority.Id;
    }

    @Override
    public int hashCode() {
        return Id;
    }
}
