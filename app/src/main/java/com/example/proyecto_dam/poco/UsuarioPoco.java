package com.example.proyecto_dam.poco;
import com.example.proyecto_dam.poco.Base.BasePoco;

import org.json.JSONObject;
import org.json.JSONException;

public class UsuarioPoco extends BasePoco {
    private String Name;
    private String Email;
    private String Password;

    public UsuarioPoco(long id, String name, String password, String email) {
        super(id);
        this.Name = name;
        this.Email = email;
        this.Password = password;
    }

    public String getName() {return this.Name;}
    public void setName(String name) {this.Name = name;}

    public String getEmail() {return this.Email;}
    public void setEmail(String email) {this.Email = email;}

    public String getPassword() {return this.Password;}
    public void setPassword(String password) {this.Password = password;}

    public String toJSON(){

        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("name", getName());
            jsonObject.put("email", getEmail());
            jsonObject.put("password", getPassword());
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static UsuarioPoco CreateByJson(String str) {
        try {
        JSONObject json = new JSONObject(str);
        int id = json.getInt("id");
            String name = json.getString("name");
            String email  = json.getString("email");
            String password = json.getString("password");

        UsuarioPoco data = new UsuarioPoco(id, name, password, email);
        return data;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
