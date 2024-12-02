package com.example.proyecto_dam.dal.Repositories.BaseRepository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.proyecto_dam.dal.TablesHelper.UsuarioTablesHelper;
import com.example.proyecto_dam.poco.UsuarioPoco;

public class BaseRepository {

    protected SQLiteDatabase Db;
    public BaseRepository(SQLiteDatabase db) {
        this.Db = db;
    }

    protected long Insert(String tableName, ContentValues content) {
        if(this.Db != null) {
            return this.Db.insert(tableName, null, content);
        }
        return 0;
    }
    protected int Update(String tableName, long id, ContentValues content) {
        if(this.Db != null) {
            return this.Db.update(tableName, content, "Id = ?", new String[]{Long.toString(id)});
        }
        return 0;
    }

    protected int getColumnIndex(Cursor cursor, String campo) {
        int index = cursor.getColumnIndex(campo);
        if(index < 0) {
            return 0;//throw new NoSuchFieldException("El campo no existe");
        }
        return index;
    }
}
