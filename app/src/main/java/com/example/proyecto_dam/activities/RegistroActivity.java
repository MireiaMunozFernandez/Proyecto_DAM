package com.example.proyecto_dam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_dam.R;
import com.example.proyecto_dam.dal.Repositories.UsuarioRepository;
import com.example.proyecto_dam.dal.UnitOfWork;
import com.example.proyecto_dam.poco.UsuarioPoco;

public class RegistroActivity extends AppCompatActivity {

    Button RegistrarUsuario;
    Button VolverInicioSesion;
    EditText Usuario;
    EditText Password;
    EditText Email;

    UsuarioRepository usuarioRepository;

    String nombre = " ", email = " ", password = " ", confirmarPassword = " ";
    UnitOfWork uow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registro), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       this.uow = new UnitOfWork(this, false);

        RegistrarUsuario = findViewById(R.id.registro_btn_registrar);
        VolverInicioSesion = findViewById(R.id.registro_volver_inicio_sesion);


        Usuario = findViewById(R.id.registro_campo_usuario);
        Password = findViewById(R.id.registro_campo_contrasenya);
        Email = findViewById(R.id.registro_campo_email);

        RegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //uow.getUsuarioRepository().InsertOrUpdate(new UsuarioPoco(0, registro_campo_usuario.getText().toString(), registro_campo_contrasenya.getText().toString(), registro_campo_email.getText().toString()));
                //
                ValidarDatos();

               // startActivity(new Intent(RegistroActivity.this, MainActivity.class));
            }
        });
        VolverInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistroActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        this.uow.Close();
        super.onDestroy();
    }

    private void ValidarDatos(){
        nombre = Usuario.getText().toString();
        email = Email.getText().toString();
        password = Password.getText().toString();
        confirmarPassword = Password.getText().toString();
        //UsuarioPoco usuarioExistente =  uow.getUsuarioRepository().GetByName(nombre);

        if (TextUtils.isEmpty(nombre)){
            Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_SHORT).show();
        }
        //else if (usuarioExistente.equals(nombre)){
          //Toast.makeText(this, "Este usuario ya existe", Toast.LENGTH_SHORT).show();
        //}
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Ingrese correo", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();

        }
        else if (!password.equals(confirmarPassword)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }

        else {
            CrearUsuario();
        }
    }

    private void CrearUsuario(){
        this.uow.getUsuarioRepository().InsertOrUpdate(new UsuarioPoco( 0, nombre, password, email ));
        Toast.makeText(this, "El Usuario se ha guardado correctamante", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegistroActivity.this, MainActivity.class));
    }

}