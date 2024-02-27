package gr313.ladvinskiy.lab08;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper{

    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE notes (id INT not null unique primary key, txt TEXT);";
        db.execSQL(sql);
    }



    public int getMaxId() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT MAX(id) FROM notes;";

        Cursor cur = db.rawQuery(sql, null);

        if (cur.moveToFirst()) {
            int ind = cur.getInt(0);
            cur.close();
            return ind;
        }

        return 0;
    }
    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> noteList = new ArrayList<Note>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT id, txt FROM notes;";
        Cursor cur = db.rawQuery(sql, null);
        if (cur.moveToFirst()) {
            do {
                Note note = new Note();
                note.id = cur.getInt(0);
                note.txt = cur.getString(1);
                noteList.add(note);
            } while (cur.moveToNext());

        }
        cur.close();
        return noteList;
    }

    public void insert(String txt) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO notes VALUES (" + (getMaxId()+1) + ",'" + txt + "');";
        db.execSQL(sql);
    }

    public void update(int id, String txt) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE notes SET txt = '" + txt + "' where id = " + id + ";";
        db.execSQL(query);
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE from notes WHERE id = " + id + ";";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
