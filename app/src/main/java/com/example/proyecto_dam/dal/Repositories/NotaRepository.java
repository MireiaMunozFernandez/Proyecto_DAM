package com.example.proyecto_dam.dal.Repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto_dam.dal.Repositories.BaseRepository.BaseRepository;
import com.example.proyecto_dam.dal.TablesHelper.NotaTablesHelper;
import com.example.proyecto_dam.dal.TablesHelper.TareaTablesHelper;
import com.example.proyecto_dam.poco.NotaPoco;
import com.example.proyecto_dam.poco.TareaPoco;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NotaRepository extends BaseRepository {

    public NotaRepository(SQLiteDatabase db) {
        super(db);
    }

    public NotaPoco InsertOrUpdate(NotaPoco nota) {
        ContentValues values = new ContentValues();
        values.put(NotaTablesHelper.COLUMN_NOTAS_USERID, nota.getUserId());
        values.put(NotaTablesHelper.COLUMN_NOTAS_NAME, nota.getName());
        values.put(NotaTablesHelper.COLUMN_NOTAS_DESCRIPCION, nota.getDescripcion());
        values.put(NotaTablesHelper.COLUMN_NOTAS_SELECTED, nota.getSelected() == true ? 1 : 0);

        if(nota.HasBeenPersistence()) {
            this.Update(NotaTablesHelper.TABLE_NOTAS, nota.getId(), values);
        } else {
            nota.setId(this.Insert(NotaTablesHelper.TABLE_NOTAS, values));
        }
        return nota;
    }

    public List<NotaPoco> GetList() {
        Cursor cursor = this.Db.rawQuery("select * from " + NotaTablesHelper.TABLE_NOTAS, null);
        List<NotaPoco> result = new ArrayList<NotaPoco>();
        if(cursor.moveToFirst()) {
            while(cursor.isAfterLast() == false){
                long id = cursor.getLong(getColumnIndex(cursor, NotaTablesHelper.COLUMN_NOTAS_ID));
                long userId = cursor.getLong(getColumnIndex(cursor, NotaTablesHelper.COLUMN_NOTAS_USERID));
                String name = cursor.getString(getColumnIndex(cursor, NotaTablesHelper.COLUMN_NOTAS_NAME));
                String descripcion = cursor.getString(getColumnIndex(cursor, NotaTablesHelper.COLUMN_NOTAS_DESCRIPCION));
                boolean selected = cursor.getInt(getColumnIndex(cursor, NotaTablesHelper.COLUMN_NOTAS_SELECTED)) == 1;

                result.add(new NotaPoco(id, userId, name, descripcion, selected));
                cursor.moveToNext();
            }
        }
        return result;
    }
    public NotaPoco GetByName(String name) {
        List<NotaPoco> list = this.GetList();
        NotaPoco result = list.stream().filter(t -> t.getName().toLowerCase().equals(name.toLowerCase())).findFirst().orElse(null);
        return result;
    }

    public List<NotaPoco> GetByUserId(long userId) {
        List<NotaPoco> list = this.GetList();
        List<NotaPoco> result = list.stream().filter(t -> t.getUserId() == userId).collect(Collectors.toList());
        return result;
    }

    public NotaPoco GetById(int id) {
        List<NotaPoco> list = this.GetList();
        NotaPoco result = list.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
        return result;
    }
}
