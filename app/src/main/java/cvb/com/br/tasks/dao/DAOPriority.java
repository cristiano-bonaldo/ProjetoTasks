package cvb.com.br.tasks.dao;

import android.content.Context;

import java.util.List;

import cvb.com.br.tasks.db.table.TabPriority;
import cvb.com.br.tasks.model.Priority;

public class DAOPriority {

    private TabPriority tabPriority;

    public DAOPriority(Context ctx) {
        tabPriority = new TabPriority(DAOUtil.getInstance(ctx).getDB());
    }

    public boolean insert(Priority priority) {
        long id = tabPriority.inserir(priority);

        return (id >= 0);
    }

    public void load(List<Priority> lista) {
        tabPriority.load(lista);
    }

    public void listaDados() {
        tabPriority.listaDados();
    }

    public void clear() {
        tabPriority.clear();
    }

    public List<Priority> getList() {
        return tabPriority.getList();
    }

}
