package com.example.proyecto_dam.poco;

import com.example.proyecto_dam.Mapper.DateMapper;
import com.example.proyecto_dam.poco.Base.BaseUserPoco;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class TareaPoco extends BaseUserPoco {
    private String Name;
    private String Descripcion;
    private Calendar Fecha;
    private boolean Selected;

    public TareaPoco(long id, long userId, String name, String descripcion, Calendar fecha, boolean selected) {
        super(id, userId);
        this.Name = name;
        this.Descripcion = descripcion;
        this.Fecha = fecha;
        this.Selected = selected;
    }


    public String getName() {return this.Name;}
    public void setName(String name) {this.Name = name;}

    public String getDescripcion() {return this.Descripcion;}
    public void setDescripcion(String descripcion) {this.Descripcion = descripcion;}

    public Calendar getFecha() {return this.Fecha;}
    public void setFecha(Calendar fecha) {this.Fecha = fecha;}

    public boolean getSelected() {return this.Selected;}
    public void setSelected(boolean selected) {this.Selected = selected;}

    public String toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("userId", getUserId());
            jsonObject.put("name", getName());
            jsonObject.put("descripcion", getDescripcion());
            jsonObject.put("fecha", DateMapper.DateToLong(getFecha()));
            jsonObject.put("selected", getSelected());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static TareaPoco CreateByJson(String str) {
        try {
            JSONObject json = new JSONObject(str);
            int id = json.getInt("id");
            int userId = json.getInt("userId");
            String name = json.getString("name");
            String descripcion  = json.getString("descripcion");
            long fecha = json.getLong("fecha");
            boolean selected = json.getBoolean("selected");

            TareaPoco data = new TareaPoco(id, userId, name, descripcion, DateMapper.LongToDate(fecha), selected);
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
