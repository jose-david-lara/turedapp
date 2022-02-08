package com.sb.tured.model;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import com.sb.tured.activity.SolicSaldoActivity;

public class Soat {

    private String id;
    private String placa;
    private String operador;
    private String bolsa_venta;


    public String getBolsa_venta() {
        return bolsa_venta;
    }

    public void setBolsa_venta(String bolsa_venta) {
        this.bolsa_venta = bolsa_venta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public static class formulario{

        private int o;
        private String n;
        private String t;
        private int lmn;
        private int lmx;
        private String valor;
        private int est;


        public int getO() {
            return o;
        }

        public void setO(int o) {
            this.o = o;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getT() {
            return t;
        }

        public void setT(String t) {
            this.t = t;
        }

        public int getLmn() {
            return lmn;
        }

        public void setLmn(int lmn) {
            this.lmn = lmn;
        }

        public int getLmx() {
            return lmx;
        }

        public void setLmx(int lmx) {
            this.lmx = lmx;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }

        public int getEst() {
            return est;
        }

        public void setEst(int est) {
            this.est = est;
        }




    }

    public static class publico{
        private String alerta;
        private String opcion_1;
        private String opcion_2;
        private String publico;

        public String getPublico() {
            return publico;
        }

        public void setPublico(String publico) {
            this.publico = publico;
        }

        public String getAlerta() {
            return alerta;
        }

        public void setAlerta(String alerta) {
            this.alerta = alerta;
        }

        public String getOpcion_1() {
            return opcion_1;
        }

        public void setOpcion_1(String opcion_1) {
            this.opcion_1 = opcion_1;
        }

        public String getOpcion_2() {
            return opcion_2;
        }

        public void setOpcion_2(String opcion_2) {
            this.opcion_2 = opcion_2;
        }



    }

    public static class comsumo_servicio_soat {

        private String tipo_documento;
        private String documento;
        private String nombre;
        private String apellido;
        private String id_ciudad;
        private String direccion;
        private String celular;
        private String email;
        private String tipo_publico;

        public String getTipo_documento() {
            return tipo_documento;
        }

        public void setTipo_documento(String tipo_documento) {
            this.tipo_documento = tipo_documento;
        }

        public String getDocumento() {
            return documento;
        }

        public void setDocumento(String documento) {
            this.documento = documento;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

        public String getId_ciudad() {
            return id_ciudad;
        }

        public void setId_ciudad(String id_ciudad) {
            this.id_ciudad = id_ciudad;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public String getCelular() {
            return celular;
        }

        public void setCelular(String celular) {
            this.celular = celular;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTipo_publico() {
            return tipo_publico;
        }

        public void setTipo_publico(String tipo_publico) {
            this.tipo_publico = tipo_publico;
        }

    }



    }
