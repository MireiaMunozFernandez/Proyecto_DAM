package com.example.proyecto_dam.dal;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto_dam.dal.Repositories.NotaRepository;
import com.example.proyecto_dam.dal.Repositories.TareaRepository;
import com.example.proyecto_dam.dal.Repositories.UsuarioRepository;

public class UnitOfWork {
    private SQLiteDatabase db;
    private SqliteHelper sqliteHelper;
    private boolean IsReadOnly;

    private UsuarioRepository usuarioRepository;
    private TareaRepository tareaRepository;
    private NotaRepository notaRepository;

    public UnitOfWork(Context context, boolean isReadOnly) {
        this.IsReadOnly =isReadOnly;
        this.sqliteHelper = new SqliteHelper(context);
        //try {
        if(this.IsReadOnly) {
            this.db = this.sqliteHelper.getReadableDatabase();
        }else {
            this.db = this.sqliteHelper.getWritableDatabase();
        }
    }

    public void Close() {
        if(this.db != null && this.db.isOpen()) {
            this.db.close();
            this.db = null;
        }
    }

    public UsuarioRepository getUsuarioRepository() {
        if(this.usuarioRepository == null) {
            this.usuarioRepository = new UsuarioRepository(this.db);
        }
        return this.usuarioRepository;
    }

    public TareaRepository getTareaRepository() {
        if(this.tareaRepository == null) {
            this.tareaRepository = new TareaRepository(this.db);
        }
        return this.tareaRepository;
    }
    public NotaRepository getNotaRepository() {
        if(this.notaRepository == null) {
            this.notaRepository = new NotaRepository(this.db);
        }
        return this.notaRepository;
    }
}
