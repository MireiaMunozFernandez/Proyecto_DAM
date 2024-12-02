package com.example.proyecto_dam.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.proyecto_dam.dal.TablesHelper.NotaTablesHelper;
import com.example.proyecto_dam.dal.TablesHelper.TareaTablesHelper;
import com.example.proyecto_dam.dal.TablesHelper.UsuarioTablesHelper;

public class SqliteHelper extends SQLiteOpenHelper {

    //Nombre y versión de la base de datos
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "ProyectoDAM.db";

    //Helpers
    private UsuarioTablesHelper Usuario;
    private TareaTablesHelper Tarea;
    private NotaTablesHelper Nota;

    //Constructor
    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.Usuario = new UsuarioTablesHelper();
        this.Tarea = new TareaTablesHelper();
        this.Nota = new NotaTablesHelper();
    }
    public void onCreate(SQLiteDatabase db) {
        this.Usuario.Create(db);
        this.Tarea.Create(db);
        this.Nota.Create(db);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.Usuario.Delete(db);
        this.Tarea.Delete(db);
        this.Nota.Delete(db);

        onCreate(db);
    }

}
