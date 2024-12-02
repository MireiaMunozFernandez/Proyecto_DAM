package com.example.proyecto_dam.activities.viewHolder;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto_dam.Mapper.DateMapper;
import com.example.proyecto_dam.R;
import com.example.proyecto_dam.poco.TareaPoco;

import java.util.List;


public class TareaCustomAdapter extends RecyclerView.Adapter<TareaCustomAdapter.TareaViewHolder> {

    private List<TareaPoco> localDataSet;
    private TareaOnAdapterItemClickListener adapterItemClickListener = null;

    public static class TareaViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView item_fecha;
        private TextView item_tarea;
        private TextView item_descripcion;
        ImageButton btn_selecionar;

        public TareaViewHolder(View view) {
            super(view);

            checkBox = itemView.findViewById(R.id.checkBox);
            item_fecha = itemView.findViewById(R.id.item_fecha);
            item_tarea = itemView.findViewById(R.id.item_tarea);
            item_descripcion = itemView.findViewById(R.id.item_descripcion);
            btn_selecionar = itemView.findViewById(R.id.btn_editar_tareas);
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }
        public TextView getTextFecha() {
            return item_fecha;
        }
        public TextView getTextTarea() {
            return item_tarea;
        }
        public TextView getTextDescripcion() {
            return item_descripcion;
        }
    }

    public TareaCustomAdapter(TareaOnAdapterItemClickListener listener, List<TareaPoco> dataSet) {
        this.adapterItemClickListener = listener;
        localDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TareaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_tareas, viewGroup, false);

        return new TareaViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(TareaViewHolder viewHolder,
                                 @SuppressLint("RecyclerView") final int position) {
        viewHolder.getCheckBox().setChecked(localDataSet.get(position).getSelected());
        viewHolder.getTextFecha().setText(DateMapper.DateToString(localDataSet.get(position).getFecha()));
        viewHolder.getTextTarea().setText(localDataSet.get(position).getName());
        viewHolder.getTextDescripcion().setText(localDataSet.get(position).getDescripcion());

        viewHolder.btn_selecionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TareaPoco item = localDataSet.get(position);
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
