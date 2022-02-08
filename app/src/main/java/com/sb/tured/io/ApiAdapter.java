package com.sb.tured.io;

import com.sb.tured.interfaces.interfacesModelo;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAdapter {

    private static ApiService API_SERVICE;

    public static ApiService getApiService() {
        String baseUrl = "";

        // Creamos un interceptor y le indicamos el log level a usar
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Asociamos el interceptor a las peticiones
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);

        //Pruebas
        //baseUrl = "https://multiservicios.madefor.work/api/";

        /*if (interfacesModelo.informacion_mobil.getTipo_red().equals("WIFI") || interfacesModelo.informacion_mobil.getTipo_red().equals("wifi"))
            baseUrl = "https://tu-red.com/api/";//
        else*/
        //Produccion
        baseUrl = "https://tu-red.com/api/";//baseUrl = "https://cobrorevertido.recargamos.co:36002/api/";


        //Produccion
        // String baseUrl = "https://tu-red.com/api/";

        if (API_SERVICE == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build()) // <-- usamos el log level
                    .build();
            API_SERVICE = retrofit.create(ApiService.class);
        }

        return API_SERVICE;
    }
}
