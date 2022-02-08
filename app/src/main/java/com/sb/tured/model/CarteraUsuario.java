package com.sb.tured.model;

import java.util.ArrayList;

public class CarteraUsuario {


    private String id;
    private String name;
    private String last_name;
    private String fecha;
    private String nombre;
    private String valor;
    private String tipo;
    private String recargas="";
    private String wplay="";
    private String soat="";
    private String certificados;
    private String abono;
    private String deuda_inicial;
    private String deuda_total;
    private String deuda_final;
    public ArrayList<Producto>  productos_info = new ArrayList<Producto>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRecargas() {
        return recargas;
    }

    public void setRecargas(String recargas) {
        this.recargas = recargas;
    }

    public String getWplay() {
        return wplay;
    }

    public void setWplay(String wplay) {
        this.wplay = wplay;
    }

    public String getSoat() {
        return soat;
    }

    public void setSoat(String soat) {
        this.soat = soat;
    }

    public String getCertificados() {
        return certificados;
    }

    public void setCertificados(String certificados) {
        this.certificados = certificados;
    }

    public String getAbono() {
        return abono;
    }

    public void setAbono(String abono) {
        this.abono = abono;
    }

    public String getDeuda_inicial() {
        return deuda_inicial;
    }

    public void setDeuda_inicial(String deuda_inicial) {
        this.deuda_inicial = deuda_inicial;
    }

    public String getDeuda_total() {
        return deuda_total;
    }

    public void setDeuda_total(String deuda_total) {
        this.deuda_total = deuda_total;
    }

    public String getDeuda_final() {
        return deuda_final;
    }

    public void setDeuda_final(String deuda_final) {
        this.deuda_final = deuda_final;
    }


}
