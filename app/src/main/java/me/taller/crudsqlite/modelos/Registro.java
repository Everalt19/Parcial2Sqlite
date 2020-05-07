package me.taller.crudsqlite.modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Registro implements Parcelable {

    private String nombre;
    private int cedula;
    private String estrato,educacion;
    private float salariop;

    private String id; // El ID de la BD

    public Registro(String id, String nombre, int cedula, String estrato, String educacion, float salariop){
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.estrato = estrato;
        this.educacion = educacion;
        this.salariop = salariop;
    }

    protected Registro(Parcel in) {

        nombre = in.readString();
        cedula = in.readInt();
        estrato = in.readString();
        educacion = in.readString();
        salariop = in.readFloat();
        id = in.readString();

    }


    public static final Creator<Registro> CREATOR = new Creator<Registro>() {
        @Override
        public Registro createFromParcel(Parcel in) {
            return new Registro(in);
        }

        @Override
        public Registro[] newArray(int size) {
            return new Registro[size];
        }
    };


    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }


    public String getEstrato() {
        return estrato;
    }

    public void setEstrato(String estrato) {
        this.estrato = estrato;
    }


    public String getEducacion() {
        return educacion;
    }

    public void setEducacion(String educacion) {
        this.educacion = educacion;
    }


    public float getSalariop() {
        return salariop;
    }

    public void setSalariop(float salariop) { this.salariop = salariop; }

    public String getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public String toString() {
        return "Registro{" +
                "nombre='" + nombre + '\'' +
                ", cedula=" + cedula +
                ", estrato='" + estrato + '\'' +
                ", educacion='" + educacion + '\'' +
                ", salario=" + salariop +
                ", id='" + id + '\'' +
                '}';
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeInt(cedula);
        parcel.writeString(estrato);
        parcel.writeString(educacion);
        parcel.writeFloat(salariop);
        parcel.writeString(id);
    }
}
