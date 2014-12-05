package com.example.campitos.springandroid4;

/**
 * Created by campitos on 4/12/14.
 */
public class Estacion implements Comparable<Estacion> {


    private String id;
    Integer hora;
    private String temperatura;

    public Estacion(int hora, String temperatura) {
        this.hora = hora;
        this.temperatura = temperatura;
    }

    public Estacion(String id) {
        this.id = id;
    }

    public Estacion() {
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public String getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(String temperatura) {
        this.temperatura = temperatura;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int compareTo(Estacion e) {
        return hora.compareTo(e.getHora());
    }
}

