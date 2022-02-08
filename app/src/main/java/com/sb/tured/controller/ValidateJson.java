package com.sb.tured.controller;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.model.BalanceProductos;
import com.sb.tured.model.CarteraUsuario;
import com.sb.tured.model.HomeProductosParametros;
import com.sb.tured.model.Producto;
import com.sb.tured.model.ProductosSaldo;
import com.sb.tured.model.ReporteVentasBusqueda;
import com.sb.tured.model.Soat;
import com.sb.tured.utilities.turedUserBD;
import com.sb.tured.utilities.utilitiesTuRedBD;
import java.util.ArrayList;
import java.util.List;


public class ValidateJson implements interfacesModelo {


    private utilitiesTuRedBD bd_data;
    private Producto producto_local;
    private ProductosSaldo producto_saldos_local;
    private JsonObject json_aux = new JsonObject();
    private JsonObject json_productos_aux = new JsonObject();
    private JsonArray jsonArray = new JsonArray();
    private JsonArray jsonArrayProductos = new JsonArray();
    private turedUserBD bd_datos = new turedUserBD();


    public boolean data_user_token(Context contexto,boolean borrarInfo) {

        bd_data = new utilitiesTuRedBD(contexto);
        if(borrarInfo)
            bd_data.deleteBD(66, 67);

        if (json_object_response.getJson_object_response().get("response").getAsString().equals("success")) {
            json_aux = (JsonObject) json_object_response.getJson_object_response().get("result");
            data_user.setResponse(json_object_response.getJson_object_response().get("response").getAsString());
            data_user.setToken(json_aux.get("token").getAsString());

            return true;
        } else {
            return false;
        }
    }

    public boolean data_gethome(Context contexto) {


        if (json_object_response.getJson_object_response().get("error").getAsString().equals("false")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));


            bd_data = new utilitiesTuRedBD(contexto);


            //Nombre del usuario
            if (json_object_response.getJson_response_data().get("usuario").toString().length() > 0) {
                data_user.setNombre_usuario(json_object_response.getJson_response_data().get("usuario").getAsString());
            } else {
                data_user.setNombre_usuario(data_user.getEmail());
            }



            //Prestamo maximo dia
            if (json_object_response.getJson_response_data().get("prestamo_maximo_dia").toString().length() > 0) {
                data_user.setPrestamo_maximo_dia(json_object_response.getJson_response_data().get("prestamo_maximo_dia").getAsString());
            }

            //Tu deuda
            if (!json_object_response.getJson_response_data().get("tudeuda").isJsonNull()) {
                json_aux = (JsonObject) json_object_response.getJson_response_data().get("tudeuda");
                if (!json_aux.get("deuda").isJsonNull()) {
                    data_user.setDeuda_negativa(json_aux.get("deuda").getAsString());
                } else {
                    data_user.setDeuda_negativa("0");
                }
            } else {
                data_user.setDeuda_negativa("0");
            }


            //Categorias productos
            if (!json_object_response.getJson_response_data().get("categorias").isJsonNull()) {
                jsonArray = (JsonArray) json_object_response.getJson_response_data().get("categorias");
                try {
                    if (bd_data.isTableExists(turedUserBD.CATEGORIAS_PRODUCTOS.TABLE_NAME)) {
                        bd_data.deleteTable(turedUserBD.CATEGORIAS_PRODUCTOS.TABLE_NAME, false);
                    }
                    CargarArray(jsonArray, 13);
                } catch (Exception e) {
                }
            }

            //Pregunta usuario
            if (!json_object_response.getJson_response_data().get("pregunta").isJsonNull()) {
                data_user.setPregunta(json_object_response.getJson_response_data().get("pregunta").toString());
            }

            //Lo que te deben deuda positiva
            if (!json_object_response.getJson_response_data().get("lo_que_te_deben").isJsonNull()) {
                json_aux = (JsonObject) json_object_response.getJson_response_data().get("lo_que_te_deben");
                if (!json_aux.get("deuda").isJsonNull()) {
                    data_user.setDeuda_positiva(json_aux.get("deuda").getAsString());
                } else {
                    data_user.setDeuda_positiva("0");
                }
            }


            //Prestamo maximo
            if (!json_object_response.getJson_response_data().get("prestamo_maximo").isJsonNull()) {
                data_user.setPrestamo_maximo(json_object_response.getJson_response_data().get("prestamo_maximo").getAsString());
            }


            //Guardar datos de usuario
            if (bd_data.isTableExists(turedUserBD.USUARIO_INFO.TABLE_NAME)) {
                bd_data.deleteTable(turedUserBD.USUARIO_INFO.TABLE_NAME, false);
            }
            bd_data.insertUsuarioInfo(data_user);


            //Productos Asignados
            if (!json_object_response.getJson_response_data().get("productos_asignados").isJsonNull()) {
                jsonArray = (JsonArray) json_object_response.getJson_response_data().get("productos_asignados");
                try {
                    if (bd_data.isTableExists(turedUserBD.USUARIO_INFO_PROD.TABLE_NAME)) {
                        bd_data.deleteTable(turedUserBD.USUARIO_INFO_PROD.TABLE_NAME, false);
                    }
                    CargarArray(jsonArray, 1);
                } catch (Exception e) {
                }
            }

            //Saldos Productos
            if (!json_object_response.getJson_response_data().get("saldo_productos").isJsonNull()) {
                jsonArray = (JsonArray) json_object_response.getJson_response_data().get("saldo_productos");
                try {
                    if (bd_data.isTableExists(turedUserBD.USUARIO_BALANCE_PRODUCTOS.TABLE_NAME)) {
                        bd_data.deleteTable(turedUserBD.USUARIO_BALANCE_PRODUCTOS.TABLE_NAME, false);
                    }
                    CargarArray(jsonArray, 2);
                } catch (Exception e) {
                }

            }

            //Productos Home
            if (!json_object_response.getJson_response_data().get("servicioshome").isJsonNull()) {
                jsonArray = (JsonArray) json_object_response.getJson_response_data().get("servicioshome");
                try {
                    if (bd_data.isTableExists(turedUserBD.USUARIO_HOME_PRODUCTOS.TABLE_NAME)) {
                        bd_data.deleteTable(turedUserBD.USUARIO_HOME_PRODUCTOS.TABLE_NAME, false);
                    }
                    if (bd_data.isTableExists(turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.TABLE_NAME)) {
                        bd_data.deleteTable(turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.TABLE_NAME, false);
                    }
                    CargarArray(jsonArray, 3);
                } catch (Exception e) {
                }

            }

            //Oficinas Certificados
            if (!json_object_response.getJson_response_data().get("oficinas_certificados").isJsonNull()) {
                jsonArray = (JsonArray) json_object_response.getJson_response_data().get("oficinas_certificados");
                try {
                    CargarArray(jsonArray, 12);
                } catch (Exception e) {
                }

            }


            if (bd_data.isTableExists(turedUserBD.PACK_OPER_RECARGA.TABLE_NAME)) {
                bd_data.deleteTable(turedUserBD.PACK_OPER_RECARGA.TABLE_NAME, false);
            }

            if (bd_data.isTableExists(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.TABLE_NAME)) {
                bd_data.deleteTable(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.TABLE_NAME, false);
            }

            return true;
        } else {
            return false;
        }
    }

    public void CargarArray(JsonArray jsonArray, int opc) {
        ArrayList<String> Lista = new ArrayList<>();
        if (opc == 1) {
            if (bd_data.isTableExists(turedUserBD.USUARIO_INFO_PROD.TABLE_NAME)) {
                bd_data.deleteTable(turedUserBD.USUARIO_INFO_PROD.TABLE_NAME, false);
            }
            for (int i = 0; i < jsonArray.size(); i++) {
                json_aux = (JsonObject) jsonArray.get(i);
                //Aquí se obtiene el dato y es guardado en una lista
                product_info.setId(json_aux.get("id").getAsString());
                product_info.setNombre(json_aux.get("nombre").getAsString());
                product_info.setDescripcion(json_aux.get("descripcion").getAsString());
                product_info.setComision_producto(json_aux.get("comision_producto").getAsString());
                product_info.setTipo_comision(json_aux.get("tipo_comision").getAsString());
                if (!json_aux.get("valor_producto").isJsonNull()) {
                    product_info.setValor_producto(json_aux.get("valor_producto").getAsString());
                } else {
                    product_info.setValor_producto("0");
                }
                product_info.setUrl_img(json_aux.get("url_img").getAsString());

                //Guardar datos de Productos
                bd_data.insertProducto(product_info);

            }
        } else if (opc == 2) {
            BalanceProductos saldo_local_objeto = new BalanceProductos();
            interfacesModelo.producto_saldo_list.removeAll(interfacesModelo.producto_saldo_list);
            Gson gson = new Gson();
            if (bd_data.isTableExists(turedUserBD.USUARIO_BALANCE_PRODUCTOS.TABLE_NAME)) {
                bd_data.deleteTable(turedUserBD.USUARIO_BALANCE_PRODUCTOS.TABLE_NAME, false);
            }

            try {
                for (int i = 0; i < jsonArray.size(); i++) {
                    try {
                        saldo_local_objeto = gson.fromJson(jsonArray.get(i), BalanceProductos.class);
                        interfacesModelo.producto_saldo_list.add(saldo_local_objeto);

                        //Guardar datos de Producto Balance
                        bd_data.insertProductoBalance(saldo_local_objeto);
                    } catch (Exception ex) {
                    }

                }
            } catch (Exception ex) {
            }


        } else if (opc == 3) {
            if (bd_data.isTableExists(turedUserBD.USUARIO_HOME_PRODUCTOS.TABLE_NAME)) {
                bd_data.deleteTable(turedUserBD.USUARIO_HOME_PRODUCTOS.TABLE_NAME, false);
            }
            for (int i = 0; i < jsonArray.size(); i++) {
                //inicio
                json_aux = (JsonObject) jsonArray.get(i);



                //inicio
                json_aux = (JsonObject) jsonArray.get(i);



                //Aquí se obtiene el dato y es guardado en una lista
                home_productos.setCategoria(json_aux.get("categoria").getAsString());
                home_productos.setId(json_aux.get("id").getAsString());
                home_productos.setProveedor(json_aux.get("proveedor").getAsString());
                home_productos.setOperador(json_aux.get("operador").getAsString());
                home_productos.setServicio(json_aux.get("servicio").getAsString());
                home_productos.setEstatus(json_aux.get("estatus").getAsString());
                home_productos.setId_producto(json_aux.get("id_producto").getAsString());
                if (json_aux.get("orden_presentacion").isJsonNull()) {
                    home_productos.setOrden_presentacion("0");
                } else {
                    home_productos.setOrden_presentacion(json_aux.get("orden_presentacion").getAsString());
                }
                home_productos.setTabla(json_aux.get("tabla").getAsString());

                //Home Productos Parametros
                homeProductosParametros(json_aux);

                home_productos.setProductos_id(json_aux.get("productos_id").getAsString());
                home_productos.setUrl_img(json_aux.get("url_img").getAsString());

                //Guardar datos de Productos Home
                bd_data.insertProductoHome(home_productos);
            }
        } else if (opc == 4) {//Paquetes de operador recargas
            for (int i = 0; i < jsonArray.size(); i++) {
                json_aux = (JsonObject) jsonArray.get(i);
                //Aquí se obtiene el dato y es guardado en una lista

                if (!json_aux.get("valor").isJsonNull()) {
                    pack_oper_recarga.setId(json_aux.get("id").getAsString());
                    pack_oper_recarga.setValor(json_aux.get("valor").getAsString());
                    pack_oper_recarga.setDescripcion(json_aux.get("descripcion").getAsString());
                    pack_oper_recarga.setCodigo(json_aux.get("codigo").getAsString());
                    pack_oper_recarga.setEstatus(json_aux.get("estatus").getAsString());
                    pack_oper_recarga.setId_producto_recargas(json_aux.get("id_producto_recargas").getAsString());
                    pack_oper_recarga.setCategoria(json_aux.get("categoria").getAsString());
                    pack_oper_recarga.setOrden_categoria(json_aux.get("orden_categoria").getAsString());

                    //Guardar datos de paquetes de operador
                    bd_data.insertPackOperRecarga(pack_oper_recarga);
                }
            }
        } else if (opc == 5) {//Valores predeterminados de operador recargas
            /*if(bd_data.isTableExists(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.TABLE_NAME)){
                bd_data.deleteTable(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.TABLE_NAME,false);
            }*/
            for (int i = 0; i < jsonArray.size(); i++) {
                json_aux = (JsonObject) jsonArray.get(i);
                //Aquí se obtiene el dato y es guardado en una lista

                valor_default_oper_recarga.setId(json_aux.get("id").getAsString());
                if (json_aux.get("valor").isJsonNull()) {
                    valor_default_oper_recarga.setValor("0");
                } else {
                    valor_default_oper_recarga.setValor(json_aux.get("valor").getAsString());
                }
                valor_default_oper_recarga.setDescripcion(json_aux.get("descripcion").getAsString());
                valor_default_oper_recarga.setCodigo(json_aux.get("codigo").getAsString());
                valor_default_oper_recarga.setEstatus(json_aux.get("estatus").getAsString());
                valor_default_oper_recarga.setId_producto_recargas(json_aux.get("id_producto_recargas").getAsString());
                valor_default_oper_recarga.setCategoria(json_aux.get("categoria").getAsString());
                valor_default_oper_recarga.setOrden_categoria(json_aux.get("orden_categoria").getAsString());
                valor_default_oper_recarga.setTabla(json_aux.get("tabla").getAsString());

                //Guardar datos de paquetes de operador
                bd_data.insertValueDefaultOperRecarga(valor_default_oper_recarga);


            }
        } else if (opc == 6) {//Actualizar saldo de productos
            BalanceProductos saldo_local_objeto = new BalanceProductos();
            interfacesModelo.producto_saldo_list.removeAll(interfacesModelo.producto_saldo_list);
            Gson gson = new Gson();

            try {
                for (int i = 0; i < jsonArray.size(); i++) {
                    try {
                        saldo_local_objeto = gson.fromJson(jsonArray.get(i), BalanceProductos.class);
                        interfacesModelo.producto_saldo_list.add(saldo_local_objeto);


                        if (i == 0) {
                            if (bd_data.isTableExists(turedUserBD.USUARIO_BALANCE_PRODUCTOS.TABLE_NAME)) {
                                bd_data.deleteTable(turedUserBD.USUARIO_BALANCE_PRODUCTOS.TABLE_NAME, false);
                            }
                        }
                        //Guardar datos de Producto Balance
                        bd_data.insertProductoBalance(saldo_local_objeto);
                    } catch (Exception ex) {
                    }

                }
            } catch (Exception ex) {
            }

        } else if (opc == 7) {//Reporte de transacciones actuales

            //Guardar datos de transacciones historial reporte
            if (bd_data.isTableExists(turedUserBD.REPORT_TRANS_PRODUCTOS.TABLE_NAME)) {
                bd_data.deleteTable(turedUserBD.REPORT_TRANS_PRODUCTOS.TABLE_NAME, false);
            }
            for (int i = 0; i < jsonArray.size(); i++) {
                json_aux = (JsonObject) jsonArray.get(i);
                //Aquí se obtiene el dato y es guardado en una lista


                trans_report.setMin_recarga(json_aux.get("referencia").getAsString());
                trans_report.setValor_recarga(json_aux.get("valor_recarga").getAsString());
                trans_report.setFecha_solicitud(json_aux.get("fecha").getAsString());
                trans_report.setCodigo_respuesta(json_aux.get("Estado").getAsString());
                trans_report.setNumero_autorizacion(json_aux.get("numero_autorizacion").getAsString());
                trans_report.setSaldo_antes(json_aux.get("saldo_antes").getAsString());
                trans_report.setSaldo_antes(json_aux.get("nuevo_saldo").getAsString());
                trans_report.setOperador(json_aux.get("operador").getAsString());
                bd_data.insertReportTransProductos(trans_report);
            }
        } else if (opc == 8) {//Reporte de cartera discriminado

            cartera_usuario_list.removeAll(cartera_usuario_list);
            try {
                for (int i = 0; i < jsonArray.size(); i++) {
                    CarteraUsuario cartera_usuario_local = new CarteraUsuario();
                    json_aux = (JsonObject) jsonArray.get(i);
                    //Aquí se obtiene el dato y es guardado en una lista

                    cartera_usuario_local.setName(json_aux.get("name").getAsString());
                    cartera_usuario_local.setLast_name(json_aux.get("last_name").getAsString());
                    cartera_usuario_local.setNombre(json_aux.get("nombre").getAsString());
                    cartera_usuario_local.setFecha(json_aux.get("fecha").getAsString());
                    cartera_usuario_local.setValor(json_aux.get("compras").getAsString());

                    cartera_usuario_list.add(cartera_usuario_local);

                    //Guardar datos de transacciones historial reporte
                    //
                }
            } catch (Exception e) {
                CarteraUsuario cartera_usuario_local = new CarteraUsuario();
                cartera_usuario_local.setNombre("");
                cartera_usuario_list.add(cartera_usuario_local);
            }
        } else if (opc == 9) {//Reporte de cartera total
            CarteraUsuario cartera_usuario_local = new CarteraUsuario();
            producto_local = new Producto();
            Gson gson = new Gson();
            cartera_usuario_local.productos_info.removeAll(cartera_usuario_local.productos_info);

            //for (int i = 0; i < jsonArray.size(); i++) {
            int i = 0;
            json_aux = (JsonObject) jsonArray.get(i);
            //Aquí se obtiene el dato y es guardado en una lista
            interfacesModelo.cartera_usuario_list.removeAll(interfacesModelo.cartera_usuario_list);

            try {
                cartera_usuario_local.setId(json_aux.get("usuario").getAsString());
                cartera_usuario_local.setName(json_aux.get("name").getAsString());
                cartera_usuario_local.setLast_name(json_aux.get("last_name").getAsString());
                cartera_usuario_local.setFecha(json_aux.get("fecha").getAsString());
                cartera_usuario_local.productos_info.removeAll(cartera_usuario_local.productos_info);
                if (!json_aux.get("productos").isJsonNull()) {
                    jsonArrayProductos = (JsonArray) json_aux.get("productos");


                    for (int x = 0; x < jsonArrayProductos.size(); x++) {
                        json_aux = (JsonObject) jsonArrayProductos.get(x);
                        if (json_aux.get("nombre").getAsString().equals("RECARGAS")) {
                            cartera_usuario_local.setRecargas(json_aux.get("valor").getAsString());
                        } else if (json_aux.get("nombre").getAsString().equals("WPLAY")) {
                            cartera_usuario_local.setWplay(json_aux.get("valor").getAsString());
                        } else if (json_aux.get("nombre").getAsString().equals("SOAT")) {
                            cartera_usuario_local.setSoat(json_aux.get("valor").getAsString());
                        }
                        producto_local = gson.fromJson(jsonArrayProductos.get(x), Producto.class);
                        cartera_usuario_local.productos_info.add(producto_local);
                    }
                }
                json_aux = (JsonObject) jsonArray.get(i);
                cartera_usuario_local.setAbono(json_aux.get("abono").getAsString());
                cartera_usuario_local.setDeuda_inicial(json_aux.get("deuda_inicial").getAsString());
                cartera_usuario_local.setDeuda_total(json_aux.get("deuda_total").getAsString());
                cartera_usuario_local.setDeuda_final(json_aux.get("deuda_final").getAsString());
                interfacesModelo.cartera_usuario_list.add(cartera_usuario_local);
            } catch (Exception e) {
                cartera_usuario_local.setId("0");
            }

            //Guardar datos de transacciones historial reporte
            //
            //}
        } else if (opc == 10) {//Reporte de busqueda recarga
            ReporteVentasBusqueda reporte_busqueda_recarga_local = new ReporteVentasBusqueda();
            interfacesModelo.reporte_venta_busqueda.removeAll(interfacesModelo.reporte_venta_busqueda);
            Gson gson = new Gson();
            try {

                for (int i = 0; i < jsonArray.size(); i++) {
                    try {
                        reporte_busqueda_recarga_local = gson.fromJson(jsonArray.get(i), ReporteVentasBusqueda.class);
                        interfacesModelo.reporte_venta_busqueda.add(reporte_busqueda_recarga_local);


                    } catch (Exception ex) {
                    }
                }
            } catch (Exception e) {
            }

        } else if (opc == 11) { //Ultimas 5 transacciones

            //Guardar datos de ultimas transacciones
            if (bd_data.isTableExists(turedUserBD.USUARIO_TRANS_LAST.TABLE_NAME)) {
                bd_data.deleteTable(turedUserBD.USUARIO_TRANS_LAST.TABLE_NAME, false);
            }
            bd_data.deteleAllLastTrans();

            try {
                if (jsonArray.size() > 0) {
                    for (int x = 0; x < jsonArray.size(); x++) {
                        json_aux = (JsonObject) jsonArray.get(x);
                        trans_last.setId(json_aux.get("id").getAsString());
                        trans_last.setOperador(json_aux.get("operador").getAsString());
                        trans_last.setNumero(json_aux.get("numero").getAsString());
                        trans_last.setFecha_solicitud(json_aux.get("fecha_solicitud").getAsString());
                        if (!json_aux.get("fecha_respuesta").isJsonNull()) {
                            trans_last.setFecha_respuesta(json_aux.get("fecha_respuesta").getAsString());
                        } else {
                            trans_last.setFecha_respuesta("");
                        }
                        if (!json_aux.get("mensaje").isJsonNull()) {
                            trans_last.setMensaje(json_aux.get("mensaje").getAsString());
                        } else {
                            trans_last.setMensaje("");
                        }
                        trans_last.setValor_Recarga(json_aux.get("valor_recarga").getAsString());
                        if (!json_aux.get("autorizacion").isJsonNull()) {
                            trans_last.setAutorizacion(json_aux.get("autorizacion").getAsString());
                        } else {
                            trans_last.setAutorizacion("");
                        }

                        trans_last.setEstado(json_aux.get("codigo").getAsString());
                        bd_data.insertTransactionLast(trans_last);
                    }
                } else {
                    trans_last.setId("");

                    bd_data.insertTransactionLast(trans_last);
                }
            } catch (Exception e) {
                trans_last.setId("");
                bd_data.insertTransactionLast(trans_last);
            }
        } else if (opc == 12) {//Oficinas Certificados SNR

            //Guardar datos de ultimas transacciones
            bd_data.deleteOficinasCertificadosSNR();

            try {
                if (jsonArray.size() > 0) {
                    for (int x = 0; x < jsonArray.size(); x++) {
                        json_aux = (JsonObject) jsonArray.get(x);
                        oficinas_certificados_snr.setId(json_aux.get("id").getAsString());
                        oficinas_certificados_snr.setCiudad(json_aux.get("ciudad").getAsString());
                        oficinas_certificados_snr.setCodigo(json_aux.get("codigo").getAsString());
                        oficinas_certificados_snr.setEstatus(json_aux.get("estatus").getAsString());
                        bd_data.insertOficinasCertificadosSNR(oficinas_certificados_snr);
                    }
                } else {
                    oficinas_certificados_snr.setId("");
                    bd_data.insertOficinasCertificadosSNR(oficinas_certificados_snr);
                }
            } catch (Exception e) {
                trans_last.setId("");
                bd_data.insertOficinasCertificadosSNR(oficinas_certificados_snr);
            }
        }else if (opc == 13){

            try {
                for (int i = 0; i < jsonArray.size(); i++) {
                    json_aux = (JsonObject) jsonArray.get(i);

                    categoria_producto_info.setCategoria(json_aux.get("categoria").getAsString());
                    categoria_producto_info.setImg(json_aux.get("img").getAsString());

                    bd_data.insertCategoriasProductos(categoria_producto_info);

                }
            }catch (Exception ex){
                categoria_producto_info.setCategoria("");
                categoria_producto_info.setImg("");
                categoria_producto_info.setImg("");

                bd_data.insertCategoriasProductos(categoria_producto_info);

            }
        }
    }


    private void homeProductosParametros(JsonObject json_aux){

        HomeProductosParametros home_productos_parametros = new HomeProductosParametros();

        //temporal
        try {


            JsonObject json_aux_temporal = new JsonObject();
            JsonObject json_aux_temporal_two = new JsonObject();
            JsonArray jsonArray_temp = new JsonArray();
            json_aux_temporal = (JsonObject) json_aux.get("parametros");
            jsonArray_temp = (JsonArray) json_aux_temporal.get("campos");
            json_aux_temporal_two = (JsonObject)  jsonArray_temp.get(0);

            home_productos_parametros.setId(json_aux.get("id").getAsString());

            home_productos_parametros.setN(json_aux_temporal_two.get("n").getAsString());
            home_productos_parametros.setT(json_aux_temporal_two.get("t").getAsString());
            home_productos_parametros.setLmn(json_aux_temporal_two.get("lmn").getAsString());
            home_productos_parametros.setLmx(json_aux_temporal_two.get("lmx").getAsString());
            home_productos_parametros.setTabla(json_aux.get("tabla").getAsString());

            bd_data.insertProductoHomeParametros(home_productos_parametros);

        }catch (Exception ex){
            home_productos_parametros.setId(json_aux.get("id").getAsString());

            home_productos_parametros.setN("...");
            home_productos_parametros.setT("...");
            home_productos_parametros.setLmn("...");
            home_productos_parametros.setLmx("...");
            home_productos_parametros.setTabla("...");
            home_productos_parametros.setTabla("...");
            bd_data.insertProductoHomeParametros(home_productos_parametros);

        }

    }

    public boolean data_getproductosmontos(Context contexto) {

        bd_data = new utilitiesTuRedBD(contexto);

        if (json_object_response.getJson_object_response().get("error").getAsString().equals("false") && json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));


            if (!json_object_response.getJson_response_data().get("servicios_parametros").isJsonNull()) {
                jsonArray = (JsonArray) json_object_response.getJson_response_data().get("servicios_parametros");
                json_aux = (JsonObject) json_object_response.getJson_response_data().get("servicios");
                if (json_aux.get("servicio").getAsString().equals("PAQUETES") || json_aux.get("servicio").getAsString().equals("PINES")) {
                    try {
                        CargarArray(jsonArray, 4);
                    } catch (Exception e) {
                    }
                } else {
                    try {
                        CargarArray(jsonArray, 5);
                    } catch (Exception e) {
                    }
                }
            }

            //servicios_parametros
            if (!json_object_response.getJson_response_data().get("servicios").isJsonNull()) {

                json_aux = (JsonObject) json_object_response.getJson_response_data().get("servicios");

                serv_oper_pack_recarga.setId(json_aux.get("id").getAsString());
                serv_oper_pack_recarga.setProveedor(json_aux.get("proveedor").getAsString());
                serv_oper_pack_recarga.setOperador(json_aux.get("operador").getAsString());
                serv_oper_pack_recarga.setServicio(json_aux.get("servicio").getAsString());
                serv_oper_pack_recarga.setEstatus(json_aux.get("estatus").getAsString());
                serv_oper_pack_recarga.setId_producto(json_aux.get("id_producto").getAsString());
                serv_oper_pack_recarga.setId_recargas(json_aux.get("id_recargas").getAsString());
                serv_oper_pack_recarga.setTabla(json_aux.get("tabla").getAsString());

                //Guardar datos de paquetes de operador
                bd_data.insertServPackOperRecarga(serv_oper_pack_recarga);

            }

        }
        return true;
    }


    public String data_getresprecarga() {

        if (json_object_response.getJson_object_response().get("error").getAsString().equals("false") && json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            return json_object_response.getJson_response_data().get("message").getAsString();
        } else if (json_object_response.getJson_object_response().get("error").getAsString().equals("true")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            if (json_object_response.getJson_response_data().get("message").getAsString().length() == 0) {
                return "No es posible realizar la recarga, error desconocido " + json_object_response.getJson_object_response().get("code").toString();
            }
            return json_object_response.getJson_response_data().get("message").getAsString();
        } else {
            return "No es posible realizar la recarga, error desconocido 500";
        }
    }

    public String data_getsaldo(Context contexto) {

        bd_data = new utilitiesTuRedBD(contexto);

        if (json_object_response.getJson_object_response().get("error").getAsString().equals("false") && json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));

            if (!json_object_response.getJson_response_data().get("saldo_productos").isJsonNull()) {
                jsonArray = (JsonArray) json_object_response.getJson_response_data().get("saldo_productos");
                try {
                    CargarArray(jsonArray, 6);
                } catch (Exception e) {
                    return "Error interno SALDO";
                }

            }
            return "Saldo actualizado";
        } else {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            return json_object_response.getJson_response_data().get("message").getAsString();
        }
    }

    public String data_reporte_transacciones(Context contexto) {

        bd_data = new utilitiesTuRedBD(contexto);

        if (json_object_response.getJson_object_response().get("error").getAsString().equals("false") && json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));

            if (!json_object_response.getJson_response_data().get("reportes").isJsonNull()) {
                jsonArray = (JsonArray) json_object_response.getJson_response_data().get("reportes");
                if (jsonArray.size() != 0) {
                    try {
                        CargarArray(jsonArray, 7);
                    } catch (Exception e) {
                        return "404";
                    }
                    return "Exitoso";
                } else {
                    return "404";
                }
            } else {
                return "404";
            }

        } else {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            return json_object_response.getJson_response_data().get("message").getAsString();
        }
    }

    public String data_reporte_cartera_discriminado(Context contexto) {

        bd_data = new utilitiesTuRedBD(contexto);

        if (json_object_response.getJson_object_response().get("error").getAsString().equals("false") && json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));

            if (!json_object_response.getJson_response_data().get("reporte").isJsonNull()) {
                jsonArray = (JsonArray) json_object_response.getJson_response_data().get("reporte");
                if (jsonArray.size() != 0) {
                    try {
                        CargarArray(jsonArray, 8);
                    } catch (Exception e) {
                        return "404";
                    }
                    return "Exitoso";
                } else {
                    return "404";
                }
            } else {
                return "404";
            }

        } else {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            return json_object_response.getJson_response_data().get("message").getAsString();
        }
    }

    public String data_reporte_cartera(Context contexto) {

        bd_data = new utilitiesTuRedBD(contexto);

        if (json_object_response.getJson_object_response().get("error").getAsString().equals("false") && json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));

            if (!json_object_response.getJson_response_data().get("reporte").isJsonNull()) {
                jsonArray = (JsonArray) json_object_response.getJson_response_data().get("reporte");
                if (jsonArray.size() != 0) {
                    try {
                        CargarArray(jsonArray, 9);
                    } catch (Exception e) {
                        return "404";
                    }
                    return "Exitoso";
                } else {
                    return "404";
                }
            } else {
                return "404";
            }

        } else {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            if (json_object_response.getJson_response_data().get("message").isJsonNull()) {
                return "Problemas con el servidor";
            } else
                return json_object_response.getJson_response_data().get("message").getAsString();
        }
    }

    public String data_reporte_busqueda_recarga(Context contexto) {

        bd_data = new utilitiesTuRedBD(contexto);

        if (json_object_response.getJson_object_response().get("error").getAsString().equals("false") && json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));

            if (!json_object_response.getJson_response_data().get("reporte").isJsonNull()) {
                jsonArray = (JsonArray) json_object_response.getJson_response_data().get("reporte");
                if (jsonArray.size() != 0) {
                    try {
                        CargarArray(jsonArray, 10);
                    } catch (Exception e) {
                        return "404";
                    }
                    return "Exitoso";
                } else {
                    return "404";
                }
            } else {
                return "404";
            }

        } else {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            if (json_object_response.getJson_response_data().get("message").isJsonNull()) {
                return "Problemas con el servidor";
            } else
                return json_object_response.getJson_response_data().get("message").getAsString();
        }
    }

    public String data_cambio_password(Context contexto) {

        bd_data = new utilitiesTuRedBD(contexto);

        if (json_object_response.getJson_object_response().get("error").getAsString().equals("false") && json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));

            return json_object_response.getJson_response_data().get("message").toString();
        } else {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            if (json_object_response.getJson_response_data().get("message").isJsonNull()) {
                return "Problemas con el servidor";
            } else
                return json_object_response.getJson_response_data().get("message").getAsString();
        }
    }

    public String data_getsaldorecarga(Context contexto) {

        bd_data = new utilitiesTuRedBD(contexto);

        if (json_object_response.getJson_object_response().get("error").getAsString().equals("false") && json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            try {
                if (!json_object_response.getJson_response_data().get("message").isJsonNull()) {
                    return json_object_response.getJson_response_data().get("message").getAsString();
                } else {
                    return "Error interno  SOLICITAR SALDO respuesta del servidor";
                }
            } catch (Exception e) {
                return "Error interno  SOLICITAR SALDO";
            }
        } else {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            return json_object_response.getJson_response_data().get("message").getAsString();
        }
    }

    public String data_reporte_ultimas_transacciones(Context contexto) {

        bd_data = new utilitiesTuRedBD(contexto);

        if (json_object_response.getJson_object_response().get("error").getAsString().equals("false") && json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));

            if (!json_object_response.getJson_response_data().get("ultimas_transacciones").isJsonNull()) {
                jsonArray = (JsonArray) json_object_response.getJson_response_data().get("ultimas_transacciones");
                if (jsonArray.size() != 0) {
                    try {
                        CargarArray(jsonArray, 11);
                    } catch (Exception e) {
                        return "404";
                    }
                    return "Exitoso";
                } else {
                    return "404";
                }
            } else {
                return "404";
            }

        } else {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            return json_object_response.getJson_response_data().get("message").getAsString();
        }
    }

    public String data_getcertificadosnr(Context contexto) {

        bd_data = new utilitiesTuRedBD(contexto);

        if (json_object_response.getJson_object_response().get("error").getAsString().equals("false") && json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            return json_object_response.getJson_response_data().get("message").getAsString();
        } else {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            return json_object_response.getJson_response_data().get("message").getAsString();
        }
    }

    public String data_getcertificadosnrcompra(Context contexto) {

        bd_data = new utilitiesTuRedBD(contexto);

        if (json_object_response.getJson_object_response().get("error").getAsString().equals("false") && json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            return json_object_response.getJson_response_data().get("message").getAsString();
        } else {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            return json_object_response.getJson_response_data().get("message").getAsString();
        }
    }

    public String message_consulta() {


        if (json_object_response.getJson_object_response().get("error").getAsString().equals("false") && json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));

            return json_object_response.getJson_response_data().get("message").toString();
        } else {
            json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
            if (json_object_response.getJson_response_data().get("message").isJsonNull()) {
                return "Problemas con el servidor";
            } else
                return json_object_response.getJson_response_data().get("message").getAsString();
        }
    }

    public void formulario_soat() {
        Soat.formulario formularioSoat;
        Gson gson = new Gson();
        interfacesModelo.formulario.clear();
        try{
            jsonArray = (JsonArray) json_object_response.getJson_response_data().get("formulario");

            for (int i = 0; i < jsonArray.size(); i++) {
                formularioSoat = gson.fromJson(jsonArray.get(i), Soat.formulario.class);
                interfacesModelo.formulario.add(formularioSoat);

            }
        }catch (Exception e){

        }
    }

    public Soat.publico publico_soat() {
        Soat.publico publicoSoat = new Soat.publico();
        Gson gson = new Gson();
        try{
            if(!json_object_response.getJson_response_data().get("publico").isJsonNull()) {
                json_aux = (JsonObject) json_object_response.getJson_response_data().get("publico");
                publicoSoat = gson.fromJson(json_aux, Soat.publico.class);
            }else{
                return null;
            }
        }catch (Exception e){

        }
        return publicoSoat;
    }


}
