package com.example.proyecto_dam.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_dam.Mapper.DateMapper;
import com.example.proyecto_dam.R;
import com.example.proyecto_dam.dal.Repositories.UsuarioRepository;
import com.example.proyecto_dam.dal.UnitOfWork;
import com.example.proyecto_dam.poco.NotaPoco;
import com.example.proyecto_dam.poco.TareaPoco;
import com.example.proyecto_dam.poco.UsuarioPoco;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;
import java.util.Date;

public class AddTareasActivity extends AppCompatActivity {

    TextView Fecha;
    EditText Tarea, Descripcion;
    ImageButton btn_calendario;
    CheckBox Selected;
    Button btn_guardar;
    Button btn_salir;
    Button btn_eliminar;
    UnitOfWork uow;
    UsuarioPoco usuario;
    TareaPoco tarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_tareas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.add_tareas), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle parametros = this.getIntent().getExtras();
        String usuarioStr = parametros.getString("usuario");
        long dateInMili = parametros.getLong("date", 0);
        if(dateInMili == 0) {
            dateInMili =DateMapper.Now().getTimeInMillis();
        }

        Calendar calendar = DateMapper.LongToDate(dateInMili);
        usuario = UsuarioPoco.CreateByJson(usuarioStr);
        this.tarea = new TareaPoco(0, usuario.getId(), "", "", calendar, false);
        String tareaStr = parametros.getString("tarea");
        this.uow = new UnitOfWork(this, false);

        Fecha = findViewById(R.id.txt_fecha);
        Tarea = findViewById(R.id.campo_tarea);
        Descripcion = findViewById(R.id.campo_descripcion);
        btn_calendario = findViewById(R.id.btn_calendario_tarea);
        btn_guardar = findViewById(R.id.btn_guardar_tarea);
        btn_salir = findViewById(R.id.btn_salir_tarea);
        btn_eliminar = findViewById(R.id.btn_eliminar_tarea);
        Selected = findViewById(R.id.check_selected_tarea);
        Fecha.setText(DateMapper.DateToString(calendar));

        if(tareaStr != null) {
            this.tarea = TareaPoco.CreateByJson(tareaStr);
            this.Set(this.tarea);
        }

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearTarea();
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
                EliminarTarea();
            }
        });

        btn_calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddTareasActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Calendar date = DateMapper.IntToDate(year, monthOfYear + 1, dayOfMonth);
                                String dateStr = DateMapper.DateToString(date);
                                Fecha.setText(dateStr);
                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

    }
    @Override
    protected void onDestroy() {
        this.uow.Close();
        super.onDestroy();
    }

    private void Set(TareaPoco tarea) {
        Tarea.setText(tarea.getName());
        Descripcion.setText(tarea.getDescripcion());
        Fecha.setText(DateMapper.DateToString(tarea.getFecha()));
        Selected.setChecked(tarea.getSelected());
    }
    private void CrearTarea(){
        String tarea = Tarea.getText().toString();
        if (TextUtils.isEmpty(tarea)){
            Toast.makeText(this, "Ingrese tarea", Toast.LENGTH_SHORT).show();
        } else {
            this.tarea.setName(tarea);
            this.tarea.setDescripcion(Descripcion.getText().toString());
            String fecha = Fecha.getText().toString();
            Calendar date = DateMapper.StringToDate(fecha);
            this.tarea.setFecha(date);
            this.tarea.setSelected(Selected.isChecked());
            this.uow.getTareaRepository().InsertOrUpdate(this.tarea);
            Toast.makeText(this, "La Tarea se ha guardado correctamante", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }

    }
    private void EliminarTarea(){
        if (this.tarea.getId() == 0){
            Toast.makeText(this, "La tarea no existe", Toast.LENGTH_SHORT).show();
        } else {
            this.uow.getTareaRepository().DeleteById(this.tarea.getId());
            Toast.makeText(this, "La tarea se ha eliminado correctamante", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }

    }


}