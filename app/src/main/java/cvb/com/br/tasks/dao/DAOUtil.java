package cvb.com.br.tasks.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cvb.com.br.tasks.db.DataBase;

public class DAOUtil {

    private static DAOUtil INSTANCE = null;

    private DAOUtil() {}

    private DataBase db;

    private DAOUtil(Context ctx) {
        db = new DataBase(ctx);
    }

    public static synchronized DAOUtil getInstance(Context ctx) {
        if (INSTANCE == null)
            INSTANCE = new DAOUtil(ctx);

        return INSTANCE;
    }

    public SQLiteDatabase getDB() {
        return db.getWritableDatabase();
    }
}
