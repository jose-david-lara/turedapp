package com.sb.tured.io;


import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.sb.tured.BuildConfig;
import com.sb.tured.interfaces.interfaceOnResponse;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.utilities.utilitiesClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsumeServicesExpress implements interfacesModelo {


    int tipo_local = 0;
    private utilitiesClass utils_class = new utilitiesClass();


    public void consume_api(int tipo, final interfaceOnResponse interface_on_response, Context contexto) {
        Call<JsonObject> call = null;
        this.tipo_local = tipo;
        String mensaja_red = "";

        mensaja_red = utils_class.informacion_red(contexto);
        if (mensaja_red.length() > 0) {
            interface_on_response.finish_fail_consume_services(999);
            return;
        }


        switch (tipo) {
            case 1:

                call = ApiAdapter.getApiService().login(data_user.getEmail(), data_user.getPassword(), BuildConfig.VERSION_NAME);
                break;
            case 2:

                call = ApiAdapter.getApiService().paramethome(data_user.getToken(), "0", "0", data_user.getId_dispositivo_usuario(),BuildConfig.VERSION_NAME);
                break;
            case 3:

                call = ApiAdapter.getApiService().productosmontos(data_user.getToken(), interfacesModelo.home_productos.getId(), interfacesModelo.home_productos.getTabla(),BuildConfig.VERSION_NAME);
                break;
            case 4:

                call = ApiAdapter.getApiService().sendrequestservice(data_user.getId_dispositivo_usuario(),
                        recarga_servicio.getPosx(),
                        recarga_servicio.getPosy(),
                        recarga_servicio.getProducto(),
                        recarga_servicio.getReferencia(),
                        recarga_servicio.getMonto(),
                        recarga_servicio.getIdmonto(),
                        recarga_servicio.getServicio_id(),
                        recarga_servicio.getId_recargas(),
                        recarga_servicio.getBolsa_venta(),
                        recarga_servicio.getToken(),BuildConfig.VERSION_NAME);
                break;
            case 5:

                call = ApiAdapter.getApiService().getsaldo(data_user.getToken(),BuildConfig.VERSION_NAME);
                break;
            case 6:

                call = ApiAdapter.getApiService().gettransaccionespdv(data_user.getToken(),
                        data_user.getId_producto_trans_report(),//id_producto
                        data_user.getFecha_inicio_report_trans(),
                        data_user.getFecha_final_report_trans(),BuildConfig.VERSION_NAME);
                break;
            case 7:

                call = ApiAdapter.getApiService().getReporteCarteraDiscriminado(data_user.getToken(),
                        data_user.getFecha_inicio_report_trans(),
                        data_user.getFecha_final_report_trans(),BuildConfig.VERSION_NAME);
                break;
            case 8:

                call = ApiAdapter.getApiService().getReporteCartera(data_user.getToken(),
                        data_user.getFecha_inicio_report_trans(),
                        data_user.getFecha_final_report_trans(),BuildConfig.VERSION_NAME);
                break;
            case 9:

                call = ApiAdapter.getApiService().getReporteVentas(data_user.getToken(),
                        data_user.getFecha_inicio_report_trans(),
                        data_user.getFecha_final_report_trans(),
                        data_user.getId_producto_trans_report(),
                        data_user.getNum_buscar_reporte_busqueda_recarga(),BuildConfig.VERSION_NAME);
                break;
            case 10:

                call = ApiAdapter.getApiService().changePassword(data_user.getToken(),
                        data_user.getPassword_old(),
                        data_user.getPassword(),BuildConfig.VERSION_NAME);
                break;
            case 11:

                call = ApiAdapter.getApiService().setprestamoproductopdv(data_user.getId_dispositivo_usuario(),
                        data_user.getToken(),
                        data_user.getId_producto_trans_report(),
                        data_user.getValor_prestamo_saldo(),BuildConfig.VERSION_NAME);
                break;
            case 12:

                call = ApiAdapter.getApiService().getultimastransacciones(data_user.getId_dispositivo_usuario(),
                        data_user.getToken(),BuildConfig.VERSION_NAME);
                break;
            case 13:

                call = ApiAdapter.getApiService().getconsultasnr(data_user.getId_dispositivo_usuario(),
                        data_user.getToken(),
                        oficinas_certificados_snr.getCirculoregistral(),
                        oficinas_certificados_snr.getMatricula(),BuildConfig.VERSION_NAME);
                break;
            case 14:

                call = ApiAdapter.getApiService().validateplaca(data_user.getId_dispositivo_usuario(),
                        data_user.getToken(),
                        interfacesModelo.soat.getOperador(),
                        interfacesModelo.soat.getPlaca(),BuildConfig.VERSION_NAME);
                break;
            case 15:

                call = ApiAdapter.getApiService().validatesoat_validar_publico_urbano(data_user.getId_dispositivo_usuario(),
                        data_user.getToken(),
                        interfacesModelo.soat.getOperador(),
                        interfacesModelo.soat.getPlaca(),
                        consumo_servicio_soat.getTipo_publico(),BuildConfig.VERSION_NAME);
                break;
            case 16://validatesoat_pre_compra

                call = ApiAdapter.getApiService().validatesoat_pre_compra(data_user.getId_dispositivo_usuario(),
                        data_user.getToken(),
                        interfacesModelo.soat.getOperador(),
                        interfacesModelo.soat.getPlaca(),
                        consumo_servicio_soat.getTipo_publico(),
                        consumo_servicio_soat.getTipo_documento(),
                        consumo_servicio_soat.getDocumento(),
                        consumo_servicio_soat.getNombre(),
                        consumo_servicio_soat.getApellido(),
                        consumo_servicio_soat.getId_ciudad(),
                        consumo_servicio_soat.getDireccion(),
                        consumo_servicio_soat.getCelular(),
                        consumo_servicio_soat.getEmail(),BuildConfig.VERSION_NAME);
                break;
            case 17:
                call = ApiAdapter.getApiService().validateSoatCompra(recarga_servicio.getPosx(),
                        recarga_servicio.getPosy(),
                        recarga_servicio.getMonto(),
                        recarga_servicio.getIdmonto(),
                        recarga_servicio.getToken(),
                        data_user.getId_dispositivo_usuario(),
                        recarga_servicio.getServicio_id(),
                        interfacesModelo.soat.getPlaca(),
                        recarga_servicio.getBolsa_venta(),
                        recarga_servicio.getProducto(),BuildConfig.VERSION_NAME);
                break;
        }
        call.enqueue(new Callback<JsonObject>() {


            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    json_object_response.setJson_object_response(response.body());
                    if (tipo_local > 1) {
                        switch (Integer.parseInt(json_object_response.getJson_object_response().get("code").getAsString())) {
                            case 11:
                                json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
                                interface_on_response.solicit_token_error_services(json_object_response.getJson_response_data().get("message").getAsString());
                                break;
                            default:
                                interface_on_response.finish_consume_services();
                                break;
                        }
                    } else {
                        interface_on_response.finish_consume_services();
                    }

                } else {
                    interface_on_response.finish_fail_consume_services(response.code());
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                try {
                } catch (Exception e) {

                    interface_on_response.finish_fail_consume_services(t.hashCode());
                }
                interface_on_response.finish_fail_consume_services(t.hashCode());
            }

        });

    }
}
