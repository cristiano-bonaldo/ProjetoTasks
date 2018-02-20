package cvb.com.br.tasks.db.table;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cvb.com.br.tasks.model.Priority;

public class TabPriority {

    private static String C_TABLE_NAME = "PRIORITY";

    private static String C_FIELD_ID   = "ID";
    private static String C_FIELD_DESC = "DESCRICAO";

    public static String C_SCRIPT_V1 =
            "CREATE TABLE " + C_TABLE_NAME + " (" +
            C_FIELD_ID     + " INTEGER(1), " +
            C_FIELD_DESC   + " TEXT(5)  " +
            ")";

    private SQLiteDatabase db;

    public TabPriority(SQLiteDatabase db) {
        this.db = db;
    }

    public long inserir(Priority priority) {
        return db.insert(C_TABLE_NAME, null, getContentValues(priority));
    }

    public void load(List<Priority> list) {
        /*
        PARDRAO SISMA
        ---
        db.beginTransaction();
        try {
            for (Priority priority : list) {
                if (db.insert(C_TABLE_NAME, null, getContentValues(priority)) < 0) {
                    Log.i("CVB", "ERRO = " + priority.toString());
                    return;
                }
            }
            db.setTransactionSuccessful();
        }
        finally {
            db.endTransaction();
        }
        */

        //---------------------

        String sql = "insert into " + C_TABLE_NAME + " (" + C_FIELD_ID + "," + C_FIELD_DESC + ") values (?, ?)";
        db.beginTransaction();

        SQLiteStatement stm = db.compileStatement(sql);
        for (Priority priority : list) {
            stm.bindLong(1, priority.getId());
            stm.bindString(2, priority.getDescription());

            stm.executeInsert();
            stm.clearBindings();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void clear() {
        db.delete(C_TABLE_NAME,null, null);
    }

    public void listaDados() {
        Cursor c = null;
        try {
            c = db.query(C_TABLE_NAME, null, null, null, null, null, null);
            while (c.moveToNext()) {
                Log.i("CVB", "info: " +
                        c.getString(c.getColumnIndex(C_FIELD_ID)) + " - " +
                        c.getString(c.getColumnIndex(C_FIELD_DESC)));
            }
        }
        catch (Exception E) {
            Log.i("CVB", "Erro: " + E.getMessage());
        }
        finally {
            if (c != null)
                c.close();
        }
    }

    public List<Priority> getList() {
        List<Priority> lista = new ArrayList<>();

        Cursor c = null;
        try {
            c = db.query(C_TABLE_NAME, null, null, null, null, null, null);
            while (c.moveToNext())
                lista.add(new Priority(c.getInt(c.getColumnIndex(C_FIELD_ID)), c.getString(c.getColumnIndex(C_FIELD_DESC))));
        }
        catch (Exception E) {
            Log.i("CVB", "Erro: " + E.getMessage());
        }
        finally {
            if (c != null)
                c.close();
        }

        return lista;
    }

    private ContentValues getContentValues(Priority priority) {
        ContentValues cv = new ContentValues();
        cv.put(C_FIELD_ID, priority.getId());
        cv.put(C_FIELD_DESC, priority.getDescription());

        return cv;
    }
}
