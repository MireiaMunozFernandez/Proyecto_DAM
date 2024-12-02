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
import android.widget.ImageButton;

import com.example.proyecto_dam.R;
import com.example.proyecto_dam.activities.AddNotasActivity;
import com.example.proyecto_dam.activities.AddTareasActivity;
import com.example.proyecto_dam.activities.ConfiguracionActivity;
import com.example.proyecto_dam.activities.viewHolder.NotaCustomAdapter;
import com.example.proyecto_dam.activities.viewHolder.NotaOnAdapterItemClickListener;
import com.example.proyecto_dam.activities.viewHolder.TareaCustomAdapter;
import com.example.proyecto_dam.activities.viewHolder.TareaOnAdapterItemClickListener;
import com.example.proyecto_dam.dal.UnitOfWork;
import com.example.proyecto_dam.poco.NotaPoco;
import com.example.proyecto_dam.poco.TareaPoco;
import com.example.proyecto_dam.poco.UsuarioPoco;

import java.util.List;

public class Notas extends Fragment implements NotaOnAdapterItemClickListener {

    Context context;
    UnitOfWork uow;
    UsuarioPoco usuario;
    RecyclerView recyclerView;
    private ActivityResultLauncher<Intent> launcher;
    public Notas() {
        // Required empty public constructor
    }

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
        View view = inflater.inflate(R.layout.fragment_notas, container, false);
        context = container.getContext();
        this.uow = new UnitOfWork(context, false);
        initUI(view);
        //abrirConfiguracion(view);

        return view;
    }

    @Override
    public void onAdapterItemClickListener(int position, NotaPoco nota) {
        Bundle parmetros = new Bundle();
        parmetros.putString("usuario", usuario.toJSON());
        parmetros.putString("nota", nota.toJSON());
        Intent i = new Intent(getActivity(), AddNotasActivity.class);
        i.putExtras(parmetros);
        launcher.launch(i);
    }

    private void initUI(View v){
        Button btn_add_notas =(Button)v.findViewById(R.id.btn_add_notas);


        recyclerView = v.findViewById(R.id.listado_notas);
        List<NotaPoco> list = uow.getNotaRepository().GetByUserId(usuario.getId());
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new NotaCustomAdapter(this, list));

        this.launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        List<NotaPoco> list2 = uow.getNotaRepository().GetByUserId(usuario.getId());
                        recyclerView.setAdapter(new NotaCustomAdapter(this, list2));
                    }
                }
        );

        btn_add_notas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //startActivity( new Intent(getActivity(), AddNotasActivity.class));
                Bundle parmetros = new Bundle();
                parmetros.putString("usuario", usuario.toJSON());
                Intent i = new Intent(getActivity(), AddNotasActivity.class);
                i.putExtras(parmetros);
                launcher.launch(i);

            }
        });
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