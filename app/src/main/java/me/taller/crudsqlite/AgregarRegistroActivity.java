package me.taller.crudsqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import me.taller.crudsqlite.controllers.RegistroController;
import me.taller.crudsqlite.modelos.Registro;

public class AgregarRegistroActivity extends AppCompatActivity {
    private Button btnAgregarRegistro, btnCancelarRegistro;
    private EditText etNombre, etCedula,etSalario ;
    private RegistroController registroController;
    private Spinner spinEstratos, spinEducacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        // Instanciar vistas
        etNombre = findViewById(R.id.etNombre);
        etCedula = findViewById(R.id.etCedula);
        etSalario = findViewById(R.id.etSalario);
        spinEstratos = findViewById(R.id.spin_estrato);
        spinEducacion = findViewById(R.id.spin_educacion);

        btnAgregarRegistro = findViewById(R.id.btnAgregarRegistro);
        btnCancelarRegistro = findViewById(R.id.btnCancelarRegistro);

        // Crear el controlador
        registroController = new RegistroController(AgregarRegistroActivity.this);

        // Agregar listener del botón de guardar
        btnAgregarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // Resetear errores/validaciones
                String randomID = "USER"+ UUID.randomUUID().toString();

               // etNombre.setError(null);
                //etCedula.setError(null);
                //etSalario.setError(null);

                String nombre = etNombre.getText().toString(),
                cedulaCadena =  etCedula.getText().toString(),
                salarioCadena = etSalario.getText().toString();



                String estrato = spinEstratos.getItemAtPosition(spinEstratos.getSelectedItemPosition()).toString();
                String educacion = spinEducacion.getItemAtPosition(spinEducacion.getSelectedItemPosition()).toString();


                // validaciones
                if ("".equals(nombre)) {
                    etNombre.setError("Digite un nombre");
                    etNombre.requestFocus();
                    return;
                }

                if ("".equals(cedulaCadena)) {
                    etCedula.setError("Digite una cedula");
                    etCedula.requestFocus();
                    return;
                }
                if ("".equals(salarioCadena)) {
                    etSalario.setError("Digite un Salario");
                    etSalario.requestFocus();
                    return;
                }

                int cedula = Integer.parseInt(cedulaCadena);
                float salario = Float.parseFloat(salarioCadena);


                // Ya pasó la validación

                Registro nuevoRegistro = new Registro(randomID, nombre, cedula, estrato, educacion,  salario);

                long id = registroController.nuevaRegistro(nuevoRegistro);
                if (id == -1) {
                    // De alguna manera ocurrió un error
                    Toast.makeText(AgregarRegistroActivity.this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                } else {
                    // Terminar
                    finish();
                }


            }
        });

        // El de cancelar simplemente cierra la actividad
        btnCancelarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}


