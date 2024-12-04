package com.example.proyecto_dam.activities.viewHolder;

import static androidx.core.content.ContextCompat.startActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_dam.R;
import com.example.proyecto_dam.activities.AddNotasActivity;
import com.example.proyecto_dam.activities.MainActivity;
import com.example.proyecto_dam.activities.RegistroActivity;
import com.example.proyecto_dam.fragments.Notas;
import com.example.proyecto_dam.poco.NotaPoco;
import com.example.proyecto_dam.poco.TareaPoco;

import java.util.List;


public class NotaCustomAdapter extends RecyclerView.Adapter<NotaCustomAdapter.NotaViewHolder> {

    private List<NotaPoco> localDataSet;
    int selected_position = -1;
    private NotaOnAdapterItemClickListener adapterItemClickListener = null;

    public static class NotaViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView item_name;
        private TextView item_descripcion;
        ImageButton btn_selecionar;

        public NotaViewHolder(View view) {
            super(view);
            checkBox = itemView.findViewById(R.id.checkBox);
            item_name = itemView.findViewById(R.id.item_name);
            item_descripcion = itemView.findViewById(R.id.item_descripcion);
            btn_selecionar = itemView.findViewById(R.id.btn_editar_notas);

        }

        public CheckBox getCheckBox() {
            return checkBox;
        }
        public TextView getTextName() {
            return item_name;
        }
        public TextView getTextDescripcion() {
            return item_descripcion;
        }
    }

    public NotaCustomAdapter(NotaOnAdapterItemClickListener listener, List<NotaPoco> dataSet) {
        this.adapterItemClickListener = listener;
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NotaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_notas, viewGroup, false);

        return new NotaViewHolder(view);
    }


    @Override
    public void onBindViewHolder(NotaViewHolder viewHolder,
                                 @SuppressLint("RecyclerView") final int position) {
        viewHolder.getCheckBox().setChecked(localDataSet.get(position).getSelected());
        viewHolder.getTextName().setText(localDataSet.get(position).getName());
        viewHolder.getTextDescripcion().setText(localDataSet.get(position).getDescripcion());
        viewHolder.itemView.setBackgroundColor(selected_position == position ? Color.GREEN : Color.TRANSPARENT);
        //NotaPoco item = localDataSet.get(position);

        viewHolder.btn_selecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               NotaPoco item = localDataSet.get(position);
                adapterItemClickListener.onAdapterItemClickListener(position, item);
            }
        }
        );
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet == null ? 0 : localDataSet.size();
    }
}
