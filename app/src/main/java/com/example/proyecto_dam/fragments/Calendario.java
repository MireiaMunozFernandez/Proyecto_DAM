package com.example.proyecto_dam.fragments;

import static android.app.Activity.RESULT_OK;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.proyecto_dam.Mapper.DateMapper;
import com.example.proyecto_dam.activities.AddNotasActivity;
import com.example.proyecto_dam.activities.MainActivity;
import com.example.proyecto_dam.activities.viewHolder.NotaCustomAdapter;
import com.example.proyecto_dam.activities.viewHolder.TareaOnAdapterItemClickListener;
import com.example.proyecto_dam.R;
import com.example.proyecto_dam.activities.AddTareasActivity;
import com.example.proyecto_dam.activities.viewHolder.TareaCustomAdapter;
import com.example.proyecto_dam.dal.UnitOfWork;
import com.example.proyecto_dam.poco.NotaPoco;
import com.example.proyecto_dam.poco.TareaPoco;
import com.example.proyecto_dam.poco.UsuarioPoco;
import com.example.proyecto_dam.services.NotificacionReceiver;

import java.util.Calendar;
import java.util.List;

public class Calendario extends Fragment implements TareaOnAdapterItemClickListener {
    UnitOfWork uow;
    UsuarioPoco usuario;
    TareaPoco tarea;
    Context context;
    RecyclerView recyclerView;
    CalendarView calendarView;
    Calendar currentDate;
    Calendario main;
    private ActivityResultLauncher<Intent> launcher;
    public Calendario() {
        main = this;
    }

    //Recuperar usuario registrado y fecha actual
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDate = DateMapper.Now();
        if (getArguments() != null) {
            String usuarioStr  = getArguments().getString("usuario");
            usuario = UsuarioPoco.CreateByJson(usuarioStr);
        }
    }

    //Cerrar UnitOfWork
    @Override
    public void onDestroy() {
        this.uow.Close();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendario, container, false);
        context = container.getContext();
        this.uow = new UnitOfWork(context, false);
        initUI(view);
        return view;
    }

    //Recuperar listado de notas registradas en la fecha seleccionada
    private void initUI(View v){
        Button btn_add_tareas =(Button)v.findViewById(R.id.btn_add_tareas);
        calendarView = (CalendarView) v.findViewById(R.id.calendarView);
        calendarView.setDate(currentDate.getTimeInMillis());
        recyclerView = v.findViewById(R.id.listado_calendario);
        List<TareaPoco> list = uow.getTareaRepository().GetByDate(currentDate, usuario.getId());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new TareaCustomAdapter(this, list));
        this.launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        List<TareaPoco> list2 = uow.getTareaRepository().GetByDate(currentDate, usuario.getId() );
                        recyclerView.setAdapter(new TareaCustomAdapter(this, list2));
                    }
                }
        );

        //Recuperar listado de notas registradas tras seleccionar otra fecha en el calendario
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                currentDate = DateMapper.IntToDate(year, month + 1, dayOfMonth);
                List<TareaPoco> list2 = uow.getTareaRepository().GetByDate(currentDate, usuario.getId() );
                recyclerView.setAdapter(new TareaCustomAdapter(main, list2));
            }
        });

        //Abrir activity 'AddTareas'
        btn_add_tareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //startActivity( new Intent(getActivity(), AddTareasActivity.class));
                long date = DateMapper.DateToLong(currentDate);
                Bundle parmetros = new Bundle();
                parmetros.putString("usuario", usuario.toJSON());
                parmetros.putLong("date", date);
                Intent i = new Intent(getActivity(), AddTareasActivity.class);
                i.putExtras(parmetros);
                main.launcher.launch(i);
                //startActivity(i);

            }
        });

    }

    //Abrir activity 'AddTareas' con los datos de la tarea seleccionada
     @Override
    public void onAdapterItemClickListener(int position, TareaPoco tarea) {
        ActivityResultContracts.StartActivityForResult resultContract =  new ActivityResultContracts.StartActivityForResult();
        try {
            Bundle parmetros = new Bundle();
            parmetros.putString("usuario", usuario.toJSON());
            parmetros.putString("tarea", tarea.toJSON());
            Intent i = new Intent(getActivity(), AddTareasActivity.class);
            i.putExtras(parmetros);
            main.launcher.launch(i);
        }
        catch (Exception ex) {
            Exception e = ex;
        }
    }

    private class RecyclerViewFragment extends Fragment {
        private RecyclerView recyclerView;


        public RecyclerViewFragment(){
            super(R.layout.list_item_tareas);
        }

        @Override
        public void onViewCreated(View view,Bundle savedInstanceState){
            recyclerView = view.findViewById(R.id.listado_calendario);
            //this.recyclerView.setAdapter(new ListaTareasAdapter());
            this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }
}