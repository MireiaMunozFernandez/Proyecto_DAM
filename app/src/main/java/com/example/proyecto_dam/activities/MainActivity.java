package com.example.proyecto_dam.activities;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import com.example.proyecto_dam.services.NotificacionReceiver;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "mi_canal";
    Button btn_registrarse;
    Button btn_iniciar_sesion;

    EditText Usuario;
    EditText Contrasenya;

    UsuarioRepository usuarioRepository;
    UnitOfWork uow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        this.uow = new UnitOfWork(this, false);

        btn_registrarse = findViewById(R.id.btn_registrarse);
        btn_iniciar_sesion = findViewById(R.id.btn_iniciar_sesion);

        Usuario = findViewById(R.id.campo_usuario);
        Contrasenya = findViewById(R.id.campo_contrasenya);


        btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistroActivity.class));
            }
        });

        btn_iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarUsuario();
                //startActivity(new Intent(MainActivity.this, GeneralActivity.class));
            }
        });
        crearCanalDeNotificacion();

        // Programar la notificación
        programarNotificacion(System.currentTimeMillis() + 5000);
    }
    @Override
    protected void onDestroy() {
        this.uow.Close();
        super.onDestroy();
    }

    private void ValidarUsuario(){
        String usuario = Usuario.getText().toString();
        String contrasenya = Contrasenya.getText().toString();
        if(TextUtils.isEmpty(usuario)){
            Toast.makeText(this, "Ingrese usuario", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(contrasenya)){
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();
        } else{
            Login(usuario, contrasenya);
        }
    }
    private void Login(String usuario, String contrasenya){
        UsuarioPoco usuarioExistente =  uow.getUsuarioRepository().GetByName(usuario);
        if (usuarioExistente != null && usuarioExistente.getPassword().equals(contrasenya)){
            Bundle parmetros = new Bundle();
            parmetros.putString("usuario", usuarioExistente.toJSON());
            Intent i = new Intent(MainActivity.this, GeneralActivity.class);
            i.putExtras(parmetros);
            startActivity(i);
        }else{
            Toast.makeText(this, "Usuario o contraseña inválidos.", Toast.LENGTH_SHORT).show();
        }

    }

    private void crearCanalDeNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence nombre = "Canal de Prueba";
            String descripcion = "Descripción del canal de prueba";
            int importancia = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel canal = new NotificationChannel(CHANNEL_ID, nombre, importancia);
            canal.setDescription(descripcion);

            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(canal);
            }
        }
    }

    private void programarNotificacion(long tiempoEnMilisegundos) {
        // Crear un Intent que llamará al BroadcastReceiver
        Intent intent = new Intent(this, NotificacionReceiver.class);
        intent.putExtra("titulo", "Hola!");
        intent.putExtra("mensaje", "¡Este es un mensaje programado!");

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Configurar el AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP, // Despierta el dispositivo si está en reposo
                    tiempoEnMilisegundos,    // Tiempo para ejecutar la alarma
                    pendingIntent
            );
        }
    }
}