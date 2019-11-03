package akansh.megazord.com.databaseconnection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "inventory";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "product";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "BRAND";
    public static final String COL_4 = "COST";
    public static final String COL_5 = "QTY";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "  + TABLE_NAME + " " +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME VARCHAR(255), " +
                "BRAND VARCHAR(255), " +
                "COST NUMBER," +
                "QTY NUMBER);";
        db.execSQL(sql);
    }

    public boolean addProducts(String name, String brand, Double cost, int qty) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("brand", brand);
        cv.put("cost", cost);
        cv.put("qty", qty);
        db.insert("product", null, cv);
        db.close();

        return true;
    }

    public boolean updateProducts(String id, String name, String brand, Double cost, int qty) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_1, id);
        cv.put(COL_2, name);
        cv.put(COL_3, brand);
        cv.put(COL_4, cost);
        cv.put(COL_5, qty);
        db.update("product", cv, "ID = ?", new String[] {id});
        db.close();

        return true;
    }

    public int deleteProduct(String id) {
        SQLiteDatabase db = getWritableDatabase();
        int delete = db.delete(TABLE_NAME, "ID = ?", new String[] {id});
        db.close();

        return delete;
    }

    public Cursor viewAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        return c;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS PRODUCT";
        db.execSQL(sql);

        onCreate(db);
    }
}
