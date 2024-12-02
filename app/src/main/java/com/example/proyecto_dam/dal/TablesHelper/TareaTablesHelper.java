package com.example.proyecto_dam.dal.TablesHelper;

import android.database.sqlite.SQLiteDatabase;

public class TareaTablesHelper {
    public static final String TABLE_TAREAS = "Tareas";
    public static final String COLUMN_TAREAS_ID = "Id";
    public static final String COLUMN_TAREAS_USERID = "UserId";
    public static final String COLUMN_TAREAS_NAME = "Name";
    public static final String COLUMN_TAREAS_DESCRIPCION = "Descripcion";
    public static final String COLUMN_TAREAS_FECHA = "Fecha";
    public static final String COLUMN_TAREAS_SELECTED = "Selected";

    //SQL para crear la tabla
    private static final String SQL_CREATE_TABLE_TAREAS  =
            "create table " + TABLE_TAREAS + "(" +
                    COLUMN_TAREAS_ID + " integer primary key autoincrement, " +
                    COLUMN_TAREAS_USERID + " integer NOT NULL, " +
                    COLUMN_TAREAS_NAME + " text not null, " +
                    COLUMN_TAREAS_DESCRIPCION + " text, " +
                    COLUMN_TAREAS_FECHA + " integer not null, " +
                    COLUMN_TAREAS_SELECTED + " integer not null, " +
                    "CONSTRAINT FK_TAREAS_USERID_USER_ID FOREIGN KEY (\""+COLUMN_TAREAS_USERID+"\") REFERENCES \""+UsuarioTablesHelper.TABLE_USUARIOS+"\" (\""+UsuarioTablesHelper.COLUMN_USUARIO_ID+"\") ON DELETE CASCADE" +
                    ");"
            ;

    //SQL para borrar la tabla
    private static final String SQL_DELETE_TAREAS =
            "DROP TABLE IF EXISTS " + TABLE_TAREAS;

    public void Create(SQLiteDatabase db) {
        String sql = SQL_CREATE_TABLE_TAREAS;
        db.execSQL(sql);
    }
    public void Delete(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_TAREAS);
    }
}
