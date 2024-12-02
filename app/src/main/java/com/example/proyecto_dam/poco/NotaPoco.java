package com.example.proyecto_dam.poco;

import com.example.proyecto_dam.poco.Base.BaseUserPoco;

import org.json.JSONException;
import org.json.JSONObject;

public class NotaPoco extends BaseUserPoco {

    private String Name;
    private String Descripcion;
    private boolean Selected;

    public NotaPoco(long id, long userId, String name, String descripcion, boolean selected) {
        super(id, userId);
        this.Name = name;
        this.Descripcion = descripcion;
        this.Selected = selected;
    }


    public String getName() {return this.Name;}
    public void setName(String name) {this.Name = name;}

    public String getDescripcion() {return this.Descripcion;}
    public void setDescripcion(String descripcion) {this.Descripcion = descripcion;}

    public boolean getSelected() {return this.Selected;}
    public void setSelected(boolean selected) {this.Selected = selected;}

    public String toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("userId", getUserId());
            jsonObject.put("name", getName());
            jsonObject.put("descripcion", getDescripcion());
            jsonObject.put("selected", getSelected());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static NotaPoco CreateByJson(String str) {
        try {
            JSONObject json = new JSONObject(str);
            int id = json.getInt("id");
            int userId = json.getInt("userId");
            String name = json.getString("name");
            String descripcion  = json.getString("descripcion");
            boolean selected = json.getBoolean("selected");

            NotaPoco data = new NotaPoco(id, userId, name, descripcion, selected);
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
