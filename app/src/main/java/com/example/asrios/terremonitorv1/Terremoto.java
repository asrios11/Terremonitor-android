package com.example.asrios.terremonitorv1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <pre>
 * Clase Terremoto
 *
 * Clase que permite instanciar objetos de tipo Terremoto con todos sus atributos y métodos
 * Implementa la interfaz Parcelable
 * @author Alexandro Sánchez Rios
 * @version 1.0
 * </pre>
 */
public class Terremoto implements Parcelable {
    private Long dateTime;
    private Double magnitud;
    private String lugar;
    private String longitud;
    private String latitud;

    public Terremoto(Long dateTime, Double magnitud, String lugar, String longitud, String latitud) {
        this.dateTime = dateTime;
        this.magnitud = magnitud;
        this.lugar = lugar;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public Terremoto(Double magnitud, String lugar) {
        this.magnitud = magnitud;
        this.lugar = lugar;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public Double getMagnitud() {
        return magnitud;
    }

    public String getLugar() {
        return lugar;
    }

    public String getLongitud() {
        return longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    protected Terremoto(Parcel in) {
        dateTime = in.readLong();
        magnitud = in.readByte() == 0x00 ? null : in.readDouble();
        lugar = in.readString();
        longitud = in.readString();
        latitud = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(dateTime);
        if (magnitud == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(magnitud);
        }
        dest.writeString(lugar);
        dest.writeString(longitud);
        dest.writeString(latitud);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Terremoto> CREATOR = new Parcelable.Creator<Terremoto>() {
        @Override
        public Terremoto createFromParcel(Parcel in) {
            return new Terremoto(in);
        }

        @Override
        public Terremoto[] newArray(int size) {
            return new Terremoto[size];
        }
    };
}
