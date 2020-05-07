package me.taller.crudsqlite.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import me.taller.crudsqlite.AuxBaseDatos;
import me.taller.crudsqlite.modelos.Registro;


public class RegistroController {
    private AuxBaseDatos auxBaseDatos;
    private String NOMBRE_TABLA = "registros";

    public RegistroController(Context contexto) {
        auxBaseDatos = new AuxBaseDatos(contexto);
    }


    public int eliminarRegistro(Registro registro) {

        SQLiteDatabase baseDeDatos = auxBaseDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(registro.getId())};
        return baseDeDatos.delete(NOMBRE_TABLA, "id = ?", argumentos);
    }

    public long nuevaRegistro(Registro registro) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = auxBaseDatos.getWritableDatabase();
        ContentValues valoresInsertar = new ContentValues();
        valoresInsertar.put("id", registro.getId());
        valoresInsertar.put("nombre", registro.getNombre());
        valoresInsertar.put("cedula", registro.getCedula());
        valoresInsertar.put("estrato", registro.getEstrato());
        valoresInsertar.put("educacion", registro.getEducacion());
        valoresInsertar.put("salario", registro.getSalariop());


        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresInsertar);


    }

    public int guardarCambios(Registro registroEditada) {
        SQLiteDatabase baseDeDatos = auxBaseDatos.getWritableDatabase();
        ContentValues valoresActualizar = new ContentValues();
        valoresActualizar.put("nombre", registroEditada.getNombre());
        valoresActualizar.put("cedula", registroEditada.getCedula());
        valoresActualizar.put("estrato", registroEditada.getEstrato());
        valoresActualizar.put("educacion", registroEditada.getEducacion());
        valoresActualizar.put("salario", registroEditada.getSalariop());
        // where id...
        String campoActualizar = "id = ?";
        // ... = idDonacion
        String[] argumentosParaActualizar = {String.valueOf(registroEditada.getId())};
        return baseDeDatos.update(NOMBRE_TABLA, valoresActualizar, campoActualizar, argumentosParaActualizar);
    }

    public ArrayList<Registro> obtenerRegistro() {
        ArrayList<Registro> registros = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = auxBaseDatos.getReadableDatabase();
        // SELECT datos
        String[] columnasAConsultar = {"id", "nombre", "cedula","estrato" ,"educacion","salario"};
        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,//from registros
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null


        );

        if (cursor == null) {
            /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return registros;


        }
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return registros;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista de donaciones
        do {
            // El 0 es el número de la columna, como seleccionamos
            // datos
            String idObtenidoDeBD = cursor.getString(0);
            String nombreObtenidoDeBD = cursor.getString(1);
            int cedulaObtenidaDeBD = cursor.getInt(2);
            String estratoObtenidaDeBD = cursor.getString(3);
            String educacionObtenidadeBD = cursor.getString(4);
            float salarioObtenidadeBD = cursor.getFloat(4);


            Registro registroObtenidaDeBD = new Registro(idObtenidoDeBD,nombreObtenidoDeBD, cedulaObtenidaDeBD, estratoObtenidaDeBD, educacionObtenidadeBD, salarioObtenidadeBD);
            registros.add(registroObtenidaDeBD);



        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista
        cursor.close();
        return registros;
    }


}