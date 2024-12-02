package com.example.proyecto_dam.dal.TablesHelper;

import android.database.sqlite.SQLiteDatabase;

public class NotaTablesHelper {
    public static final String TABLE_NOTAS = "Notas";
    public static final String COLUMN_NOTAS_ID = "Id";
    public static final String COLUMN_NOTAS_USERID = "UserId";
    public static final String COLUMN_NOTAS_NAME = "Name";
    public static final String COLUMN_NOTAS_DESCRIPCION = "Descripcion";
    public static final String COLUMN_NOTAS_SELECTED = "Selected";

    //SQL para crear la tabla
    private static final String SQL_CREATE_TABLE_NOTAS  =
            "create table " + TABLE_NOTAS + "(" +
                    COLUMN_NOTAS_ID + " integer primary key autoincrement, " +
                    COLUMN_NOTAS_USERID + " integer NOT NULL, " +
                    COLUMN_NOTAS_NAME + " text not null," +
                    COLUMN_NOTAS_DESCRIPCION + " text, " +
                    COLUMN_NOTAS_SELECTED + " text, " +
                    "CONSTRAINT FK_NOTAS_USERID_USER_ID FOREIGN KEY (\""+COLUMN_NOTAS_USERID+"\") REFERENCES \""+UsuarioTablesHelper.TABLE_USUARIOS+"\" (\""+UsuarioTablesHelper.COLUMN_USUARIO_ID+"\") ON DELETE CASCADE" +
                    ");"
            ;

    //SQL para borrar la tabla
    private static final String SQL_DELETE_NOTAS =
            "DROP TABLE IF EXISTS " + TABLE_NOTAS;

    public void Create(SQLiteDatabase db) {
        String sql = SQL_CREATE_TABLE_NOTAS;
        db.execSQL(sql);
    }
    public void Delete(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_NOTAS);
    }
}
