package com.sb.tured.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sb.tured.BuildConfig;
import com.sb.tured.R;
import com.sb.tured.activity.CertificadosActivity;
import com.sb.tured.activity.LoginActivity;
import com.sb.tured.activity.MenuPrincActivity;
import com.sb.tured.activity.SolicSaldoActivity;
import com.sb.tured.activity.SplashActivity;
import com.sb.tured.controller.ValidateJson;
import com.sb.tured.interfaces.interfaceOnResponse;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.io.ConsumeServicesExpress;
import com.sb.tured.model.Usuario;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class utilitiesClass extends AppCompatActivity implements interfacesModelo {

    public int retorno = 0;
    private Context contexto;
    public static ImageView imagen_icono;
    private ValidateJson validate_user = new ValidateJson();
    private ProgressDialog progressDialog;
    private AlertDialog.Builder builder;
    private View v = null;
    private boolean parametro_aceptar = false;

    public void msjsSimple(final Context activity, String Tittle, String msjBody, String accept, String deny, int imagen, final int opc) {

        imagen_icono = new ImageView(activity);

        switch (imagen) {
            case 1://Punto de venta
                Picasso.get()
                        .load("https://multiservicios.madefor.work/assets/img/pdv.png")
                        .transform(new RoundedTransformation(50, 4))
                        .resize(350, 300)
                        .centerCrop()
                        .into(imagen_icono);
                //imagen_icono = interfacesModelo.carga_imagenes.img.get(0);
                //interfacesModelo.carga_imagenes.img.set(0,imagen_icono);
                break;
            case 2://Distribuidor
                Picasso.get()
                        .load("https://multiservicios.madefor.work/assets/img/admin.png")
                        .transform(new RoundedTransformation(50, 4))
                        .resize(350, 300)
                        .centerCrop()
                        .into(imagen_icono);
                //imagen_icono = interfacesModelo.carga_imagenes.img.get(2);
                //interfacesModelo.carga_imagenes.img.set(2,imagen_icono);
                break;
        }

        AlertDialog.Builder dialogo = new AlertDialog.Builder(activity);
        dialogo.setTitle(Tittle);
        dialogo.setMessage(msjBody);
        dialogo.setIcon(imagen_icono.getDrawable());
        dialogo.setCancelable(false);
        if (accept.length() > 0) {
            dialogo.setPositiveButton(accept, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    llenar(2);
                }

            });
        }
        if (deny.length() > 0) {
            dialogo.setNegativeButton(deny, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    llenar(3);
                }
            });
        }
        dialogo.show();

    }

    private void llenar(int retorno_class) {
        retorno = retorno_class;
    }


    public static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(Resources.getSystem(), x);
    }

    public boolean solicitar_token(final Activity actividad) {
        contexto = actividad.getApplicationContext();

        builder = new AlertDialog.Builder(actividad);

        LayoutInflater inflater = actividad.getLayoutInflater();

        v = inflater.inflate(R.layout.dialog_solic_token, null);

        builder.setView(v);

        Button solicitar = v.findViewById(R.id.entrar_boton_solicit_token);
        Button cancelar = v.findViewById(R.id.cancelar_boton_solicit_token);
        final EditText cap_clave = v.findViewById(R.id.clave_solicit_token);

        solicitar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        progressDialog = new ProgressDialog(v.getContext());
                        if (cap_clave.getText().length() == 0) {
                            msjsSimple(contexto, "Información", "Por favor llenar todos los campos", "Aceptar", "", 0, 1);
                        } else {

                            data_user.setPassword(cap_clave.getText().toString());
                            progressDialog.setMessage("Solicitando Token...");
                            progressDialog.show();

                            new ConsumeServicesExpress().consume_api(1, new interfaceOnResponse() {
                                @Override
                                public void finish_consume_services() {
                                    progressDialog.dismiss();
                                    if (!validate_user.data_user_token(contexto,true)) {

                                    } else {
                                        v.setVisibility(View.GONE);
                                        return;
                                    }
                                }

                                @Override
                                public void solicit_token_error_services(String mensaje_token) {
                                    progressDialog.dismiss();
                                    Intent intento = new Intent(contexto, LoginActivity.class);
                                    startActivity(intento);
                                    actividad.finish();
                                }

                                @Override
                                public void finish_fail_consume_services(int codigo) {
                                    progressDialog.dismiss();
                                    Intent intento = new Intent(contexto, LoginActivity.class);
                                    startActivity(intento);
                                    actividad.finish();
                                    return;
                                }
                            }, actividad);
                        }
                    }
                }
        );

        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intento = new Intent(contexto, LoginActivity.class);
                        startActivity(intento);
                        actividad.finish();
                    }
                }

        );


        return true;
    }


    public boolean seleccionar_bolsa_recarga(final Activity actividad) {
        contexto = actividad.getApplicationContext();

        builder = new AlertDialog.Builder(actividad);

        LayoutInflater inflater = actividad.getLayoutInflater();

        v = inflater.inflate(R.layout.dialog_solic_token, null);

        builder.setView(v);

        Button solicitar = v.findViewById(R.id.entrar_boton_solicit_token);
        Button cancelar = v.findViewById(R.id.cancelar_boton_solicit_token);
        final EditText cap_clave = v.findViewById(R.id.clave_solicit_token);

        solicitar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        progressDialog = new ProgressDialog(v.getContext());
                        if (cap_clave.getText().length() == 0) {
                            msjsSimple(contexto, "Información", "Por favor llenar todos los campos", "Aceptar", "", 0, 1);
                        } else {

                            data_user.setPassword(cap_clave.getText().toString());
                            progressDialog.setMessage("Solicitando Token...");
                            progressDialog.show();

                            new ConsumeServicesExpress().consume_api(1, new interfaceOnResponse() {
                                @Override
                                public void finish_consume_services() {
                                    progressDialog.dismiss();
                                    if (!validate_user.data_user_token(contexto,true)) {

                                    } else {
                                        v.setVisibility(View.GONE);
                                        return;
                                    }
                                }

                                @Override
                                public void solicit_token_error_services(String mensaje_token) {
                                    progressDialog.dismiss();
                                    Intent intento = new Intent(contexto, LoginActivity.class);
                                    startActivity(intento);
                                    actividad.finish();
                                }

                                @Override
                                public void finish_fail_consume_services(int codigo) {
                                    progressDialog.dismiss();
                                    Intent intento = new Intent(contexto, LoginActivity.class);
                                    startActivity(intento);
                                    actividad.finish();
                                    return;
                                }
                            }, actividad);
                        }
                    }
                }
        );

        cancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intento = new Intent(contexto, LoginActivity.class);
                        startActivity(intento);
                        actividad.finish();
                    }
                }

        );


        return true;
    }

    public void msjs_opciones_simple(final Context activity, String Tittle, String msjBody, String accept, String deny) {


        AlertDialog.Builder dialogo = new AlertDialog.Builder(activity);
        dialogo.setTitle(Tittle);
        dialogo.setMessage(msjBody);
        dialogo.setCancelable(false);
        if (accept.length() > 0) {
            dialogo.setPositiveButton(accept, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    parametro_aceptar = true;
                }

            });
        }
        if (deny.length() > 0) {
            dialogo.setNegativeButton(deny, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    parametro_aceptar = false;
                }
            });
        }
        dialogo.show();

    }

    public String informacion_red(Context contexto) {

        ConnectivityManager manager = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        try {
            if (networkInfo != null) {

                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {

                    interfacesModelo.informacion_mobil.red_conectada = true;

                    if (networkInfo.getTypeName().equals("WIFI") || networkInfo.getTypeName().equals("wifi")) {
                        interfacesModelo.informacion_mobil.setTipo_red(networkInfo.getTypeName());
                    } else if (networkInfo.getTypeName().equals("MOBILE") || networkInfo.getTypeName().equals("mobile")) {
                        interfacesModelo.informacion_mobil.setTipo_red(networkInfo.getTypeName());
                    }

                } else {
                    interfacesModelo.informacion_mobil.red_conectada = false;
                    return "Redes de internet no disponible - 404";
                }
            }else {
                interfacesModelo.informacion_mobil.red_conectada = false;
                return "Redes de internet no disponible - 500";
            }
        }catch (Exception ex){
            return "";
        }
        return "";
    }


    public boolean llenar_informacion_inicio_sesion_usuario(String estado, Context contexto) {

        int dayOfMonth, monthOfYear, year;
        final Calendar c = Calendar.getInstance();
        DatosMovil data_movil = new DatosMovil();
        utilitiesTuRedBD bd_tu_red = new utilitiesTuRedBD(contexto);
        String red_movil = "xxx";
        String version_app = "xxx";
        String id_dispositivo = "000";
        String fecha = "";
        String hora = "";
        String token = "";
        c.getTime();

        //Fecha
        dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        monthOfYear = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);

        if ((monthOfYear + 1) < 10 && dayOfMonth < 10) {
            fecha = "0" + dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year;
        } else if ((monthOfYear + 1) < 10) {
            fecha = dayOfMonth + "/0" + (monthOfYear + 1) + "/" + year;
        } else if (dayOfMonth < 10) {
            fecha = "0" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        } else {
            fecha = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        }

        //Hora
        hora = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        if (Integer.parseInt(hora) < 10)
            hora = "0" + hora;
        hora += ":" + (c.get(Calendar.MINUTE));
        if (c.get(Calendar.SECOND) < 10) {
            hora += ":0" + (c.get(Calendar.SECOND));
        } else {
            hora += ":" + (c.get(Calendar.SECOND));
        }


        id_dispositivo = data_movil.obtenerIMEI(contexto);

        try {
            version_app = BuildConfig.VERSION_NAME;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (informacion_red(contexto).length() == 0) {
            red_movil = interfacesModelo.informacion_mobil.getTipo_red();
        } else {
            red_movil = "xxx";
        }

        token = data_user.getToken();

        try {
            bd_tu_red.updateSesionUsuarioDesActivado("00");
            bd_tu_red.insertUsuarioSesion(fecha, hora, id_dispositivo, version_app, estado, red_movil, token);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public boolean cerrar_sesion_usuario(Context contexto) {

       utilitiesTuRedBD bd_tu_red = new utilitiesTuRedBD(contexto);

        try {
            bd_tu_red.updateSesionUsuarioDesActivado("00");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public static boolean obtener_sesion_usuario(Context contexto) {

        List lista_sesion = new ArrayList();
        utilitiesTuRedBD bd_tu_red = new utilitiesTuRedBD(contexto);

        try {
            lista_sesion = bd_tu_red.getUsuarioSesionActivo("01");
            if (!lista_sesion.isEmpty())
                return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public Usuario obtenerInfoUsuario (Context context){
        utilitiesTuRedBD util_tu_red = new utilitiesTuRedBD(context);
        List list_usuario_info = new ArrayList();

        list_usuario_info =  util_tu_red.getUsuarioInfo();

        if(list_usuario_info.isEmpty())
            return null;
        return (Usuario) list_usuario_info.get(0);

    }

}
