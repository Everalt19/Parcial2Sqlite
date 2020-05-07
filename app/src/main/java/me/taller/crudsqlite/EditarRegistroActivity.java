package me.taller.crudsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import me.taller.crudsqlite.controllers.RegistroController;
import me.taller.crudsqlite.modelos.Registro;

public class EditarRegistroActivity extends AppCompatActivity {

    private EditText etEditarNombre, etEditarCedula, etEditarSalario;
    private Button btnGuardarCambios, btnCancelarEdicion;
    private Registro registro;//El registro que se va a estar editando
    private RegistroController registroController;
    private Spinner spinEstratos, spinEducacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        // Recuperar datos que enviaron
        Bundle extras = getIntent().getExtras();
        // Si no hay datos (cosa rara) salimos
        if (extras == null) {
            finish();
            return;
        }
        // Instanciar el controlador de las mascotas
        registroController = new RegistroController(EditarRegistroActivity.this);

        // Rearmar la donacion
        // Nota: igualmente solamente podríamos mandar el id y recuperar la donacion de la BD

        registro = getIntent().getExtras().getParcelable("registros");

        // Ahora declaramos las vistas

        etEditarNombre = findViewById(R.id.etEditarNombre);
        etEditarCedula = findViewById(R.id.etEditarCedula);
        etEditarSalario = findViewById(R.id.etEditarSalario);
        spinEstratos = findViewById(R.id.spin_estrato);
        spinEducacion = findViewById(R.id.spin_educacion);

        btnCancelarEdicion = findViewById(R.id.btnCancelarEdicionDonacion);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambiosDonacion);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.estratos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEstratos.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.educacion, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEducacion.setAdapter(adapter2);


        // Rellenar los EditText con los datos del registro
        etEditarNombre.setText(registro.getNombre());
        etEditarCedula.setText(""+registro.getCedula());
        etEditarSalario.setText(""+registro.getSalariop());


        setDataSpinEstrato(adapter);
        setDataSpinEducacion(adapter2);

        // Listener del click del botón para salir, simplemente cierra la actividad
        btnCancelarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        // Listener del click del botón que guarda cambios
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remover previos errores si existen

                etEditarNombre.setError(null);
                etEditarCedula.setError(null);
                etEditarSalario.setError(null);

                // Crear el registro con los nuevos cambios pero ponerle
                // el id de la anterior

                String nuevoNombre = etEditarNombre.getText().toString();
                String nuevaCedulaCadena = etEditarCedula.getText().toString();
                String nuevoSalarioCadena = etEditarSalario.getText().toString();



                String nuevoEstrato = spinEstratos.getItemAtPosition(spinEstratos.getSelectedItemPosition()).toString();
                String nuevoEducacion = spinEducacion.getItemAtPosition(spinEducacion.getSelectedItemPosition()).toString();

                if (nuevoNombre.isEmpty()) {
                    etEditarNombre.setError("Digite el Nombre");
                    etEditarNombre.requestFocus();
                    return;
                }
                if ("".equals(nuevaCedulaCadena)) {
                    etEditarCedula.setError("Digite una cedula");
                    etEditarCedula.requestFocus();
                    return;
                }
                if ("".equals(nuevoSalarioCadena)) {
                    etEditarSalario.setError("Digite un Salario");
                    etEditarSalario.requestFocus();
                    return;
                }

                int nuevaCedula = Integer.parseInt(nuevaCedulaCadena);
                float nuevoSalario = Float.parseFloat(nuevoSalarioCadena);


                // Si llegamos hasta aquí es porque los datos ya están validados
                registro.setNombre(nuevoNombre);
                registro.setCedula(nuevaCedula);
                registro.setEstrato(nuevoEstrato);
                registro.setEducacion(nuevoEducacion);
                registro.setSalariop(nuevoSalario);

                int filasModificadas = registroController.guardarCambios(registro);
                if (filasModificadas != 1) {
                    // De alguna forma ocurrió un error porque se debió modificar únicamente una fila
                    Toast.makeText(EditarRegistroActivity.this, "Error guardando cambios. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                } else {
                    // Si las cosas van bien, volvemos a la principal
                    // cerrando esta actividad
                    finish();
                }
            }
        });
    }

    private void setDataSpinEstrato(ArrayAdapter<CharSequence> adapter) {
        int spinnerPosition = adapter.getPosition(registro.getEstrato());
        spinEstratos.setSelection(spinnerPosition);

    }


    private void setDataSpinEducacion(ArrayAdapter<CharSequence> adapter2) {
        int spinnerPosition = adapter2.getPosition(registro.getEducacion());
        spinEducacion.setSelection(spinnerPosition);
    }


}
