package com.example.proyecto_dam.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyecto_dam.activities.viewHolder.TareaOnAdapterItemClickListener;
import com.example.proyecto_dam.R;
import com.example.proyecto_dam.activities.AddTareasActivity;
import com.example.proyecto_dam.activities.viewHolder.TareaCustomAdapter;
import com.example.proyecto_dam.dal.UnitOfWork;
import com.example.proyecto_dam.poco.TareaPoco;
import com.example.proyecto_dam.poco.UsuarioPoco;

import java.util.List;



public class Tareas extends Fragment implements TareaOnAdapterItemClickListener {

    Context context;
    UnitOfWork uow;
    UsuarioPoco usuario;
    RecyclerView recyclerView;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String usuarioStr  = getArguments().getString("usuario");
            usuario = UsuarioPoco.CreateByJson(usuarioStr);
        }
    }
    @Override
    public void onDestroy() {
        this.uow.Close();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tareas, container, false);
        context = container.getContext();
        this.uow = new UnitOfWork(context, false);
        initUI(view);
       // abrirConfiguracion(view);
        return view;
    }

    @Override
    public void onAdapterItemClickListener(int position, TareaPoco tarea) {
        Bundle parmetros = new Bundle();
        parmetros.putString("usuario", usuario.toJSON());
        parmetros.putString("tarea", tarea.toJSON());
        Intent i = new Intent(getActivity(), AddTareasActivity.class);
        i.putExtras(parmetros);
        launcher.launch(i);
    }

    private void initUI(View v){
        Button btn_add_tareas =(Button)v.findViewById(R.id.btn_add_tareas);
        recyclerView = v.findViewById(R.id.listado_tareas);

        List<TareaPoco> list = uow.getTareaRepository().GetByUserId(usuario.getId());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new TareaCustomAdapter(this, list));

        this.launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        List<TareaPoco> list2 = uow.getTareaRepository().GetByUserId(usuario.getId());
                        recyclerView.setAdapter(new TareaCustomAdapter(this, list2));
                    }
                }
        );

        btn_add_tareas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //startActivity( new Intent(getActivity(), AddTareasActivity.class));
                Bundle parmetros = new Bundle();
                parmetros.putString("usuario", usuario.toJSON());
                Intent i = new Intent(getActivity(), AddTareasActivity.class);
                i.putExtras(parmetros);
                launcher.launch(i);
            }
        });
    }
    private class RecyclerViewFragment extends Fragment {
        private RecyclerView recyclerView;


        public RecyclerViewFragment(){
            super(R.layout.list_item_tareas);
        }

        @Override
        public void onViewCreated(View view,Bundle savedInstanceState){
            recyclerView = view.findViewById(R.id.listado_tareas);
            //this.recyclerView.setAdapter(new ListaTareasAdapter());
            this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        }


    }
//    private void abrirConfiguracion(View v){
//        ImageButton btn_configuracion = (ImageButton)v.findViewById(R.id.btn_configuracion);
//
//        btn_configuracion.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), ConfiguracionActivity.class));
//            }
//        });
//    }
}
