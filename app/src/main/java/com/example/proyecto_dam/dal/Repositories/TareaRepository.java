package com.example.proyecto_dam.dal.Repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyecto_dam.Mapper.DateMapper;
import com.example.proyecto_dam.dal.Repositories.BaseRepository.BaseRepository;
import com.example.proyecto_dam.dal.TablesHelper.NotaTablesHelper;
import com.example.proyecto_dam.dal.TablesHelper.TareaTablesHelper;
import com.example.proyecto_dam.dal.TablesHelper.UsuarioTablesHelper;
import com.example.proyecto_dam.poco.TareaPoco;
import com.example.proyecto_dam.poco.UsuarioPoco;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TareaRepository extends BaseRepository {

    public TareaRepository(SQLiteDatabase db) {
        super(db);
    }

    public TareaPoco InsertOrUpdate(TareaPoco tarea) {
        ContentValues values = new ContentValues();
        values.put(NotaTablesHelper.COLUMN_NOTAS_USERID, tarea.getUserId());
        values.put(TareaTablesHelper.COLUMN_TAREAS_NAME, tarea.getName());
        values.put(TareaTablesHelper.COLUMN_TAREAS_DESCRIPCION, tarea.getDescripcion());
        values.put(TareaTablesHelper.COLUMN_TAREAS_FECHA, DateMapper.DateToLong(tarea.getFecha()));
        values.put(TareaTablesHelper.COLUMN_TAREAS_SELECTED, tarea.getSelected() == true ? 1 : 0);

        if(tarea.HasBeenPersistence()) {
            this.Update(TareaTablesHelper.TABLE_TAREAS, tarea.getId(), values);
        } else {
            tarea.setId(this.Insert(TareaTablesHelper.TABLE_TAREAS, values));
        }
        return tarea;
    }

    public List<TareaPoco> GetList() {
        Cursor cursor = this.Db.rawQuery("select * from " + TareaTablesHelper.TABLE_TAREAS, null);
        List<TareaPoco> result = new ArrayList<TareaPoco>();
        if(cursor.moveToFirst()) {
            while(cursor.isAfterLast() == false){
                long id = cursor.getLong(getColumnIndex(cursor, TareaTablesHelper.COLUMN_TAREAS_ID));
                long userId = cursor.getLong(getColumnIndex(cursor, TareaTablesHelper.COLUMN_TAREAS_USERID));
                String name = cursor.getString(getColumnIndex(cursor, TareaTablesHelper.COLUMN_TAREAS_NAME));
                String descripcion = cursor.getString(getColumnIndex(cursor, TareaTablesHelper.COLUMN_TAREAS_DESCRIPCION));
                long fecha = cursor.getLong(getColumnIndex(cursor, TareaTablesHelper.COLUMN_TAREAS_FECHA));
                boolean selected = cursor.getInt(getColumnIndex(cursor, TareaTablesHelper.COLUMN_TAREAS_SELECTED)) == 1;

                result.add(new TareaPoco(id, userId, name, descripcion, DateMapper.LongToDate(fecha), selected));
                cursor.moveToNext();
            }
        }
        return result;
    }
    public TareaPoco GetByName(String name) {
        List<TareaPoco> list = this.GetList();
        TareaPoco result = list.stream().filter(t -> t.getName().toLowerCase().equals(name.toLowerCase())).findFirst().orElse(null);
        return result;
    }

    public List<TareaPoco> GetByUserId(long userId) {
        List<TareaPoco> list = this.GetList();
        List<TareaPoco> result = list.stream().filter(t -> t.getUserId() == userId).collect(Collectors.toList());
        return result;
    }

    public List<TareaPoco> GetByDate(Calendar date, long userId) {
        List<TareaPoco> list = this.GetList();
        List<TareaPoco> result = list.stream().filter(t -> t.getUserId() == userId && DateMapper.Compare(t.getFecha(), date) == 0).collect(Collectors.toList());
        return result;
    }

    public List<TareaPoco> GetByDateWithoutUser(Calendar date) {
        List<TareaPoco> list = this.GetList();
        List<TareaPoco> result = list.stream().filter(t -> DateMapper.Compare(t.getFecha(), date) == 0).collect(Collectors.toList());
        return result;
    }

    public TareaPoco GetById(int id) {
        List<TareaPoco> list = this.GetList();
        TareaPoco result = list.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
        return result;
    }

    public int DeleteById(long id) {
        ContentValues values = new ContentValues();
        int i = this.Db.delete(TareaTablesHelper.TABLE_TAREAS, TareaTablesHelper.COLUMN_TAREAS_ID + " =" + id, null);
        return i;
    }
}
