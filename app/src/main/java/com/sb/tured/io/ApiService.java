package com.sb.tured.io;

import com.google.gson.JsonObject;
import com.sb.tured.model.Usuario;
import com.sb.tured.model.tokenResponse;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("get")
    Call<Usuario> getDiseases();

    @FormUrlEncoded
    @POST("gettoken")
    Call<JsonObject> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("version") String version
    );

    @FormUrlEncoded
    @POST("gethome")
    Call<JsonObject> paramethome(
            @Field("token") String token,
            @Field("posx") String posx,
            @Field("posy") String posy,
            @Field("serial") String id_dispositivo,
            @Field("version") String version
    );

    @FormUrlEncoded
    @POST("getproductosmontos")
    Call<JsonObject> productosmontos(
            @Field("token") String token,
            @Field("id") String id,
            @Field("tabla") String tabla,
            @Field("version") String version
    );

    @FormUrlEncoded
    @POST("sendrequestservice")
    Call<JsonObject> sendrequestservice(
            @Field("serial") String serial,
            @Field("posx") String posx,
            @Field("posy") String posy,
            @Field("producto") String producto,
            @Field("referencia") String referencia,
            @Field("monto") String monto,
            @Field("idmonto") String idmonto,
            @Field("servicio_id") String servicio_id,
            @Field("id_recargas") String id_recargas,
            @Field("bolsa_venta") String bolsa_venta,
            @Field("token") String token,
            @Field("version") String version
    );

    @FormUrlEncoded
    @POST("getsaldo")
    Call<JsonObject> getsaldo(
            @Field("token") String token,
            @Field("version") String version
    );

    @FormUrlEncoded
    @POST("gettransaccionespdv")
    Call<JsonObject> gettransaccionespdv(
            @Field("token") String token,
            @Field("id_producto") String id_producto,
            @Field("fecha_inicio") String fecha_inicio,
            @Field("fecha_fin") String fecha_final
            ,@Field("version") String version
    );

    @FormUrlEncoded
    @POST("getReporteCarteraDiscriminado")
    Call<JsonObject> getReporteCarteraDiscriminado(
            @Field("token") String token,
            @Field("fechaini") String fecha_ini,
            @Field("fechafin") String fecha_final,
            @Field("version") String version
    );

    @FormUrlEncoded
    @POST("getReporteCartera")
    Call<JsonObject> getReporteCartera(
            @Field("token") String token,
            @Field("fechaini") String fecha_inicio,
            @Field("fechafin") String fecha_final,
            @Field("version") String version
    );

    @FormUrlEncoded
    @POST("getReporteVentas")
    Call<JsonObject> getReporteVentas(
            @Field("token") String token,
            @Field("fechaini") String fecha_inicio,
            @Field("fechafin") String fecha_final,
            @Field("producto") String producto,
            @Field("busqueda") String busqueda,
            @Field("version") String version
    );

    @FormUrlEncoded
    @POST("changePassword")
    Call<JsonObject> changePassword(
            @Field("token") String token,
            @Field("clave") String clave,
            @Field("nueva_clave") String nueva_clave,
            @Field("version") String version
    );

    @FormUrlEncoded
    @POST("setprestamoproductopdv")
    Call<JsonObject> setprestamoproductopdv(
            @Field("serial") String serial,
            @Field("token") String token,
            @Field("producto") String producto,
            @Field("valor") String valor,
            @Field("version") String version
    );

    @FormUrlEncoded
    @POST("getultimastransacciones")
    Call<JsonObject> getultimastransacciones(
            @Field("serial") String serial,
            @Field("token") String token,
            @Field("version") String version
    );

    @FormUrlEncoded
    @POST("getconsultasnr")
    Call<JsonObject> getconsultasnr(
            @Field("serial") String serial,
            @Field("token") String token,
            @Field("circuloregistral") String circuloregistral,
            @Field("matricula") String matricula,
            @Field("version") String version
    );



    @FormUrlEncoded
    @POST("validateplaca")
    Call<JsonObject> validateplaca(
            @Field("serial") String serial,
            @Field("token") String token,
            @Field("operador") String operador,
            @Field("placa") String placa,
            @Field("version") String version
    );

    @FormUrlEncoded
    @POST("validateplaca")
    Call<JsonObject> validatesoat_validar_publico_urbano(
                    @Field("serial") String serial,
                    @Field("token") String token,
                    @Field("operador") String operador,
                    @Field("placa") String placa,
                    @Field("tipo_publico") String tipo_publico,
                    @Field("version") String version
            );

    @FormUrlEncoded
    @POST("validateplaca")
    Call<JsonObject> validatesoat_pre_compra(
            @Field("serial") String serial,
            @Field("token") String token,
            @Field("operador") String operador,
            @Field("placa") String placa,
            @Field("tipo_publico") String tipo_publico,
            @Field("tipo_documento") String tipo_documento,
            @Field("documento") String documento,
            @Field("nombre") String nombre,
            @Field("apellido") String apellido,
            @Field("ciudad") String ciudad,
            @Field("direccion") String direccion,
            @Field("celular") String celular,
            @Field("email") String email,
            @Field("version") String version
    );

    @FormUrlEncoded
    @POST("sendrequestservice")
    Call<JsonObject> validateSoatCompra(
            @Field("posx") String posx,
            @Field("posy") String posy,
            @Field("monto") String monto,
            @Field("idmonto") String idmonto,
            @Field("token") String token,
            @Field("serial") String serial,
            @Field("servicio_id") String servicio_id,
            @Field("referencia") String referencia,
            @Field("bolsa_venta") String bolsa_venta,
            @Field("producto") String producto,
            @Field("version") String version
    );

   /* @GET("login")
    Call<LoginResponse> getLogin(
            @Query("username") String username,
            @Query("password") String password
    );

    @FormUrlEncoded
    @POST("product")
    Call<SimpleResponse> postNewProduct(
            @Field("code") String code,
            @Field("name") String name,
            @Field("description") String description
    );*/
}
