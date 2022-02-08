package com.sb.tured.model;



public class InformacionMobil {

    private String tipo_red;
    private String nombre_tipo_red;
    public boolean red_conectada;


    public String getTipo_red() {
        return tipo_red;
    }

    public void setTipo_red(String tipo_red) {
        this.tipo_red = tipo_red;
    }

    public String getNombre_tipo_red() {
        return nombre_tipo_red;
    }

    public void setNombre_tipo_red(String nombre_tipo_red) {
        this.nombre_tipo_red = nombre_tipo_red;
    }

    public boolean isRed_conectada() {
        return red_conectada;
    }

    public void setRed_conectada(boolean red_conectada) {
        this.red_conectada = red_conectada;
    }
}
