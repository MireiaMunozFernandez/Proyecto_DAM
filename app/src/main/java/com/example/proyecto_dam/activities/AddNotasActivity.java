package com.example.proyecto_dam.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_dam.R;
import com.example.proyecto_dam.dal.UnitOfWork;
import com.example.proyecto_dam.poco.NotaPoco;
import com.example.proyecto_dam.poco.TareaPoco;
import com.example.proyecto_dam.poco.UsuarioPoco;

public class AddNotasActivity extends AppCompatActivity {


    EditText Nota, Descripcion;
    CheckBox Selected;
    Button btn_guardar;
    Button btn_salir;
    Button btn_eliminar;

    UnitOfWork uow;
    UsuarioPoco usuario;
    NotaPoco nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_notas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_notas), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Bundle parametros = this.getIntent().getExtras();
        String usuarioStr = parametros.getString("usuario");
        usuario = UsuarioPoco.CreateByJson(usuarioStr);
        this.nota = new NotaPoco(0, usuario.getId(), "", "", false);
        String notaStr = parametros.getString("nota");

        this.uow = new UnitOfWork(this, false);

        Nota = findViewById(R.id.campo_nota);
        Descripcion = findViewById(R.id.campo_descripcion_nota);
        Selected = findViewById(R.id.check_selected_nota);
        btn_guardar = findViewById(R.id.btn_guardar_nota);
        btn_salir = findViewById(R.id.btn_salir_nota);
        btn_eliminar = findViewById(R.id.btn_eliminar_nota);

        if(notaStr != null) {
            this.nota = NotaPoco.CreateByJson(notaStr);
            this.Set(this.nota);
        }

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearNota();
            }
        });
        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EliminarNota();
            }
        });



    }
    @Override
    protected void onDestroy() {
        this.uow.Close();
        super.onDestroy();
    }

     private void Set(NotaPoco nota) {
        Nota.setText(nota.getName());
        Descripcion.setText(nota.getDescripcion());
        Selected.setChecked(nota.getSelected());
    }
    private void CrearNota(){
        String nota = Nota.getText().toString();
        if (TextUtils.isEmpty(nota)){
            Toast.makeText(this, "Ingrese campo nota", Toast.LENGTH_SHORT).show();
        } else {
            this.nota.setName(nota);
            this.nota.setDescripcion(Descripcion.getText().toString());
            this.nota.setSelected(Selected.isChecked());
            this.uow.getNotaRepository().InsertOrUpdate(this.nota);
            Toast.makeText(this, "La Nota se ha guardado correctamante", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }
    }

    private void EliminarNota(){
        if (this.nota.getId() == 0){
            Toast.makeText(this, "La nota no existe", Toast.LENGTH_SHORT).show();
        } else {
            this.uow.getNotaRepository().DeleteById(this.nota.getId());
            Toast.makeText(this, "La Nota se ha eliminado correctamante", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }

    }
}