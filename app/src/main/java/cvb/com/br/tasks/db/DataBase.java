package cvb.com.br.tasks.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cvb.com.br.tasks.db.table.TabPriority;

public class DataBase extends SQLiteOpenHelper {

    private static final String C_DB_NAME = "DB_TASKS";
    private static final int C_DB_VERSION = 1;

    public DataBase(Context context) {
        super(context, C_DB_NAME, null, C_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TabPriority.C_SCRIPT_V1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
