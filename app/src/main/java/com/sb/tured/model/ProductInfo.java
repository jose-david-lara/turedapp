package com.sb.tured.model;

public class ProductInfo {

    private String id;
    private String comision_producto;
    private String valor_producto;
    private String nombre;
    private String descripcion;
    private String tipo_comision;
    private String url_img;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComision_producto() {
        return comision_producto;
    }

    public void setComision_producto(String comision_producto) {
        this.comision_producto = comision_producto;
    }

    public String getValor_producto() {
        return valor_producto;
    }

    public void setValor_producto(String valor_producto) {
        this.valor_producto = valor_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo_comision() {
        return tipo_comision;
    }

    public void setTipo_comision(String tipo_comision) {
        this.tipo_comision = tipo_comision;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }
}
