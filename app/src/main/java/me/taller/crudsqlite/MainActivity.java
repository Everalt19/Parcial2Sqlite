package me.taller.crudsqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.taller.crudsqlite.controllers.RegistroController;
import me.taller.crudsqlite.modelos.Registro;

public class MainActivity extends AppCompatActivity {
    private List<Registro> listaDeRegistros;
    private RecyclerView recyclerView;
    private AdaptadorRegistro adaptadorRegistros;
    private RegistroController registroController;
    private FloatingActionButton fabAgregarRegistro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Ojo: este código es generado automáticamente, pone la vista y ya, pero
        // no tiene nada que ver con el código que vamos a escribir
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Lo siguiente sí es nuestro ;)
        // Definir nuestro controlador
        registroController = new RegistroController(MainActivity.this);

        // Instanciar vistas
        recyclerView = findViewById(R.id.recyclerViewRegistros);
        fabAgregarRegistro = findViewById(R.id.fabAgregarRegistro);


        // Por defecto es una lista vacía,
        // se la ponemos al adaptador y configuramos el recyclerView
        listaDeRegistros= new ArrayList<>();

        adaptadorRegistros = new AdaptadorRegistro(listaDeRegistros);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptadorRegistros);

        // Una vez que ya configuramos el RecyclerView le ponemos los datos de la BD
        refrescarListaDeRegistros();

        // Listener de los clicks en la lista, o sea el RecyclerView
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override // Un toque sencillo
            public void onClick(View view, int position) {
                // Pasar a la actividad EditarDonacionActivity.java
                Registro registroSeleccionada =  listaDeRegistros.get(position);
                Intent intent = new Intent(MainActivity.this, EditarRegistroActivity.class);
                intent.putExtra("registros", registroSeleccionada);
                startActivity(intent);
            }

            @Override // Un toque largo
            public void onLongClick(View view, int position) {
                final Registro registroParaEliminar = listaDeRegistros.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(MainActivity.this)
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                registroController.eliminarRegistro(registroParaEliminar);
                                refrescarListaDeRegistros();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Eliminar ! ")
                        .setMessage("¿Eliminar registro -->  " + registroParaEliminar.getNombre() + " ?")
                        .create();
                dialog.show();

            }
        }));

        // Listener del FAB
        fabAgregarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simplemente cambiamos de actividad
                Intent intent = new Intent(MainActivity.this, AgregarRegistroActivity.class);
                startActivity(intent);
            }
        });

        // Créditos
        /*
        fabAgregarDonacion.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Info")
                        .setMessage("Desarrollo de nuevas tecnologias")
                        .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogo, int which) {
                                dialogo.dismiss();
                            }
                        })
                        .setPositiveButton("Sitio web", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intentNavegador = new Intent(Intent.ACTION_VIEW, Uri.parse("https://parzibyte.me"));
                                startActivity(intentNavegador);
                            }
                        })
                        .create()
                        .show();
                return false;
            }
        });
        */


    }

    @Override
    protected void onResume() {
        super.onResume();
        refrescarListaDeRegistros();
    }

    public void refrescarListaDeRegistros() {
        /*
         * ==========
         * Justo aquí obtenemos la lista de la BD
         * y se la ponemos al RecyclerView
         * ============
         *
         * */
        if (adaptadorRegistros == null) return;
        listaDeRegistros = registroController.obtenerRegistro();
        adaptadorRegistros.setListaDeRegistros(listaDeRegistros);
        adaptadorRegistros.notifyDataSetChanged();

    }
}
