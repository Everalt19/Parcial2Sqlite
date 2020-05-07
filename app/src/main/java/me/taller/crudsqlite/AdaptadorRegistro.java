package me.taller.crudsqlite;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.taller.crudsqlite.modelos.Registro;

public class AdaptadorRegistro extends RecyclerView.Adapter<AdaptadorRegistro.MyViewHolder> {

    private List<Registro> listaDeRegistros;

    public void setListaDeRegistros(List<Registro> listaDeRegistros) {
        this.listaDeRegistros = listaDeRegistros;
    }

    public AdaptadorRegistro(List<Registro> registros) {
        this.listaDeRegistros = registros;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaRegistro = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fil_registros, viewGroup, false);
        return new MyViewHolder(filaRegistro);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Obtener el registro de nuestra lista gracias al índice i
        Registro registro = listaDeRegistros.get(i);

        // Obtener los datos de la lista
        String nombre = registro.getNombre();
        int cedula = registro.getCedula();
        String estrato = registro.getEstrato();
        String educacion = registro.getEducacion();
        float salario =  registro.getSalariop();

        // Y poner a los TextView los datos con setText
        myViewHolder.nombre.setText(nombre);
        myViewHolder.cedula.setText(""+cedula);
        myViewHolder.estrato.setText(estrato);
        myViewHolder.educacion.setText(educacion);
        myViewHolder.salario.setText(""+salario);



    }



    @Override
    public int getItemCount() {
        return listaDeRegistros.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, cedula,estrato, educacion, salario;

        MyViewHolder(View itemView) {
            super(itemView);
            this.nombre = itemView.findViewById(R.id.tvNombre);
            this.cedula = itemView.findViewById(R.id.tvCedula);
            this.estrato = itemView.findViewById(R.id.tvEstrato);
            this.educacion = itemView.findViewById(R.id.tvEducacion);
            this.salario = itemView.findViewById(R.id.tvSalario);

        }
    }
}
