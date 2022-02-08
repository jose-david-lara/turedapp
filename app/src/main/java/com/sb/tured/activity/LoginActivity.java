package com.sb.tured.activity;


import android.app.Activity;
import android.app.ProgressDialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.sb.tured.BuildConfig;
import com.sb.tured.R;
import com.sb.tured.interfaces.interfacesModelo;

import com.sb.tured.utilities.DatosMovil;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.utilitiesClass;
import com.squareup.picasso.Picasso;


public class LoginActivity extends  AppCompatActivity implements View.OnClickListener, interfacesModelo {

    private Button enter_button;
    private Button contact_button;
   // private ImageView image_logo;
    private TextView text_tittle;
    private EditText capUsuario;
    private EditText capPassword;
    private utilitiesClass utils_class = new utilitiesClass();
    private DatosMovil data_movil;
    private String mensaje_inicio;
    private String mensaje_red;
    private TextView texto_version;
    String version_app = "xxx";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();

        try {
            mensaje_inicio = getIntent().getExtras().getString("mensaje_inicio");
        }catch (Exception ex){
            mensaje_inicio = "";
        }


        try {
            version_app = BuildConfig.VERSION_NAME;
        } catch (Exception e) {
            e.printStackTrace();
        }


        texto_version.setText("Version-Datáfono: "+version_app);


        //image_logo.setVisibility(View.INVISIBLE);


        if (mensaje_inicio.length() > 0) {
            utils_class.msjsSimple(this, "Información", mensaje_inicio, "Aceptar", "", 0, 1);
        }


        mensaje_red = utils_class.informacion_red(this);
        if(mensaje_red.length() > 0){
            utils_class.msjsSimple(this, "Información", mensaje_red, "Aceptar", "", 0, 1);
        }



    }

    private void initComponents() {

        enter_button = findViewById(R.id.buttonEnter);
        contact_button = findViewById(R.id.buttonContact);
        contact_button.setVisibility(View.GONE);
        data_movil = new DatosMovil();
        texto_version = findViewById(R.id.text_version);


        //Captura de datos
        capUsuario = findViewById(R.id.capUser);
        capPassword = findViewById(R.id.capPass);
        capUsuario.setText("");
        capPassword.setText("");


    }




    @Override
    public void onClick(View v) {

        if(v.getId() == contact_button.getId()){
            Intent intento = new Intent(this, ContactActivity.class);
            intento.putExtra("pantalla","1");
            startActivity(intento);
            LoginActivity.this.finish();
        }else {

            utils_class.informacion_red(this);

            if (interfacesModelo.informacion_mobil.isRed_conectada()) {
                if (v.getId() == enter_button.getId()) {
                    if (!autenticar())
                        return;
                }
            } else {
                if (mensaje_red.length() == 0) {
                    utils_class.msjsSimple(this, "Información", "Redes de internet no disponible", "Aceptar", "", 0, 1);
                } else {
                    utils_class.msjsSimple(this, "Información", mensaje_red, "Aceptar", "", 0, 1);
                }
            }
        }
    }

    public void nextActivity(Class clase) {
        Intent intento = new Intent(this, clase.getClass());
        startActivity(intento);
        this.finish();
    }

    private boolean autenticar() {


        String var = data_movil.obtenerIMEI(this);
        if (var.length() == 0) {
            utils_class.msjsSimple(this, "Información", "No es posible obtener informacion del dispositivo", "Aceptar", "", 0, 1);
            return false;
        } else {
            data_user.setId_dispositivo_usuario(var);
        }


        if (capUsuario.length() == 0 || capPassword.length() == 0) {
            utils_class.msjsSimple(this, "Información", "Por favor llenar todos los campos", "Aceptar", "", 0, 1);
            return utils_class.retorno <= 0;
        } else {

            data_user.setEmail(capUsuario.getText().toString());
            data_user.setPassword(capPassword.getText().toString());


            Intent intento = new Intent(this, ProcessingActivity.class); // Lanzamos SiguienteActivity
            intento.putExtra("tipo", "1");
            startActivity(intento);
            this.finish();

        }

        return true;


    }





}
