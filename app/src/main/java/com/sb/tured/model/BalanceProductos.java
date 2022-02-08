package com.sb.tured.model;

import java.util.ArrayList;

public class BalanceProductos {

    private String id;
    private String user_id;
    private String productos_id;
    private String saldo;
    private String nombre;
    private String descripion;
    private String estatus;
    private String tipo_producto;
    private String orden_presentacion;
    private String url_img;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProductos_id() {
        return productos_id;
    }

    public void setProductos_id(String productos_id) {
        this.productos_id = productos_id;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripion() {
        return descripion;
    }

    public void setDescripion(String descripion) {
        this.descripion = descripion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }


    public String getTipo_producto() {
        return tipo_producto;
    }

    public void setTipo_producto(String tipo_producto) {
        this.tipo_producto = tipo_producto;
    }

    public String getOrden_presentacion() {
        return orden_presentacion;
    }

    public void setOrden_presentacion(String orden_presentacion) {
        this.orden_presentacion = orden_presentacion;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }
}
