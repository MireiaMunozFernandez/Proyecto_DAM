package com.example.proyecto_dam.dal.Repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto_dam.dal.Repositories.BaseRepository.BaseRepository;
import com.example.proyecto_dam.dal.TablesHelper.UsuarioTablesHelper;
import com.example.proyecto_dam.poco.UsuarioPoco;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository extends BaseRepository {

    public UsuarioRepository(SQLiteDatabase db) {
        super(db);
    }

    public UsuarioPoco InsertOrUpdate(UsuarioPoco usuario) {
        ContentValues values = new ContentValues();
        values.put(UsuarioTablesHelper.COLUMN_USUARIO_NAME, usuario.getName());
        values.put(UsuarioTablesHelper.COLUMN_USUARIO_EMAIL, usuario.getEmail());
        values.put(UsuarioTablesHelper.COLUMN_USUARIO_PASSWORD, usuario.getPassword());

        if(usuario.HasBeenPersistence()) {
            this.Update(UsuarioTablesHelper.TABLE_USUARIOS, usuario.getId(), values);
        } else {
            usuario.setId(this.Insert(UsuarioTablesHelper.TABLE_USUARIOS, values));
        }
        return usuario;
    }

    public List<UsuarioPoco> GetList() {
        Cursor cursor = this.Db.rawQuery("select * from " + UsuarioTablesHelper.TABLE_USUARIOS, null);
        List<UsuarioPoco> result = new ArrayList<UsuarioPoco>();
        if(cursor.moveToFirst()) {
            while(cursor.isAfterLast() == false){
                long id = cursor.getLong(getColumnIndex(cursor, UsuarioTablesHelper.COLUMN_USUARIO_ID));
                String name = cursor.getString(getColumnIndex(cursor, UsuarioTablesHelper.COLUMN_USUARIO_NAME));
                String email = cursor.getString(getColumnIndex(cursor, UsuarioTablesHelper.COLUMN_USUARIO_EMAIL));
                String pwd = cursor.getString(getColumnIndex(cursor, UsuarioTablesHelper.COLUMN_USUARIO_PASSWORD));

                result.add(new UsuarioPoco(id, name, pwd, email));
                cursor.moveToNext();
            }
        }
        return result;
    }
    public UsuarioPoco GetByName(String name) {
        List<UsuarioPoco> list = this.GetList();
        UsuarioPoco result = list.stream().filter(t -> t.getName().toLowerCase().equals(name.toLowerCase())).findFirst().orElse(null);
        return result;
    }
    public UsuarioPoco GetById(int id) {
        List<UsuarioPoco> list = this.GetList();
        UsuarioPoco result = list.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
        return result;
    }

}
