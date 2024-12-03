package com.example.proyecto_dam.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import android.app.NotificationManager;

import com.example.proyecto_dam.Mapper.DateMapper;
import com.example.proyecto_dam.R;
import com.example.proyecto_dam.dal.UnitOfWork;

import java.util.Calendar;
import java.util.GregorianCalendar;

//Genera la notificación

public class NotificacionReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "mi_canal";

    @Override
    public void onReceive(Context context, Intent intent) {

        // Obtener los datos de la notificación
        int tareasPendientes;
        UnitOfWork uow = new UnitOfWork(context, false);
        tareasPendientes = uow.getTareaRepository().GetByDateWithoutUser(DateMapper.Now()).size();
        uow.Close();
        String titulo = "Tareas Pendientes";//intent.getStringExtra("Tareas Pendientes");
        String mensaje = "Tienes " + tareasPendientes + " Tareas Pendientes";

        // Crear la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_btn_calendario) // Reemplaza con un ícono válido
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        // Mostrar la notificación
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            try {
                manager.notify(1, builder.build());
            }
            catch (Exception ex) {
                Exception e = ex;
            }
        }
    }
}