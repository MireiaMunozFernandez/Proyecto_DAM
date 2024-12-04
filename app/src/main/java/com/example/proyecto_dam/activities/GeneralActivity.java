package com.example.proyecto_dam.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_dam.R;
import com.example.proyecto_dam.dal.Repositories.TareaRepository;
import com.example.proyecto_dam.dal.Repositories.UsuarioRepository;
import com.example.proyecto_dam.dal.UnitOfWork;
import com.example.proyecto_dam.poco.TareaPoco;
import com.example.proyecto_dam.poco.UsuarioPoco;
import com.example.proyecto_dam.fragments.Calendario;
import com.example.proyecto_dam.fragments.Notas;
import com.example.proyecto_dam.fragments.Tareas;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.color.DynamicColors;

import org.json.JSONObject;

public class GeneralActivity extends AppCompatActivity {

    BottomNavigationView BottomNavigationView;
    UsuarioPoco usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_general);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.general), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle parametros = this.getIntent().getExtras();
        String usuarioStr = parametros.getString("usuario");
        usuario = UsuarioPoco.CreateByJson(usuarioStr);
        BottomNavigationView = findViewById(R.id.bottomNavigationBar);
        BottomNavigationView.setOnItemSelectedListener(selectedListener);

        Calendario fragment = new Calendario();
        Bundle parmetros = new Bundle();
        parmetros.putString("usuario", usuario.toJSON());
        fragment.setArguments(parmetros);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, "");
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //Abre fragmento en función del botón seleccionado
    private BottomNavigationView.OnItemSelectedListener selectedListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    if (menuItem.getItemId() == R.id.btn_calendario) {
                        Calendario fragment = new Calendario();
                        Bundle parmetros = new Bundle();
                        parmetros.putString("usuario", usuario.toJSON());
                        fragment.setArguments(parmetros);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment, "");
                        fragmentTransaction.commit();
                        return true;
                    } else if (menuItem.getItemId() == R.id.btn_tareas){
                        Tareas fragment = new Tareas();
                        Bundle parmetros = new Bundle();
                        parmetros.putString("usuario", usuario.toJSON());
                        fragment.setArguments(parmetros);
                        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.container, fragment);
                        fragmentTransaction1.commit();
                        return true;
                    }else if(menuItem.getItemId() == R.id.btn_notas) {
                        Notas fragment = new Notas();
                        Bundle parmetros = new Bundle();
                        parmetros.putString("usuario", usuario.toJSON());
                        fragment.setArguments(parmetros);
                        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction2.replace(R.id.container, fragment, "");
                        fragmentTransaction2.commit();
                        return true;
                    }
                    return false;
                }

            };
}