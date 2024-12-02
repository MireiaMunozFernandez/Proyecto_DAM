package com.example.proyecto_dam.dal.TablesHelper;

import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto_dam.dal.SqliteHelper;

public class UsuarioTablesHelper {
    //Tabla y columnas
    public static final String TABLE_USUARIOS = "Usuarios";
    public static final String COLUMN_USUARIO_ID = "Id";
    public static final String COLUMN_USUARIO_NAME = "Name";
    public static final String COLUMN_USUARIO_EMAIL = "Email";
    public static final String COLUMN_USUARIO_PASSWORD = "Pwd";

    //SQL para crear la tabla
    private static final String SQL_CREATE_TABLE_USUARIOS  =
            "create table " + TABLE_USUARIOS + "(" +
            COLUMN_USUARIO_ID + " integer primary key autoincrement, " +
            COLUMN_USUARIO_NAME + " text not null," +
            COLUMN_USUARIO_EMAIL + " text not null," +
            COLUMN_USUARIO_PASSWORD + " text not null" +
            ");"
            ;

    //SQL para borrar la tabla
    private static final String SQL_DELETE_USUARIOS =
            "DROP TABLE IF EXISTS " + TABLE_USUARIOS;

    public void Create(SQLiteDatabase db) {
        String sql = SQL_CREATE_TABLE_USUARIOS;
        db.execSQL(sql);
    }
    public void Delete(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_USUARIOS);
    }
}
