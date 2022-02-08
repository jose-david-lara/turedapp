package com.sb.tured.model;

public class ReporteVentasBusqueda {

    private String estado;
    private String fecha;
    private String referencia;
    private String operador;
    private String saldo_antes;
    private String valor_recarga;
    private String nuevo_saldo;
    private String numero_autorizacion;


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getSaldo_antes() {
        return saldo_antes;
    }

    public void setSaldo_antes(String saldo_antes) {
        this.saldo_antes = saldo_antes;
    }

    public String getValor_recarga() {
        return valor_recarga;
    }

    public void setValor_recarga(String valor_recarga) {
        this.valor_recarga = valor_recarga;
    }

    public String getNuevo_saldo() {
        return nuevo_saldo;
    }

    public void setNuevo_saldo(String nuevo_saldo) {
        this.nuevo_saldo = nuevo_saldo;
    }

    public String getNumero_autorizacion() {
        return numero_autorizacion;
    }

    public void setNumero_autorizacion(String numero_autorizacion) {
        this.numero_autorizacion = numero_autorizacion;
    }
}
