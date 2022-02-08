package com.sb.tured.activity;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sb.tured.R;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.model.Usuario;
import com.sb.tured.utilities.DatosMovil;
import com.sb.tured.utilities.turedUserBD;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends AppCompatActivity implements  interfacesModelo {


    private AnimationDrawable animacion;
    private ImageView loading;
    private Animation transicion;
    private turedUserBD bd_data;
    private utilitiesClass utils_class = new utilitiesClass();
    private ProcessingActivity processingActivity = new ProcessingActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        loading = findViewById(R.id.loading);
        loading.setBackgroundResource(R.drawable.cargando);
        loading.setVisibility(View.INVISIBLE);
        animacion = (AnimationDrawable) loading.getBackground();
        animacion.start();


        bd_data = new turedUserBD();


        transicion = AnimationUtils.loadAnimation(this,R.anim.transicion);
        loading.startAnimation(transicion);
        transicion.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                nextActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        processingActivity.cargarImagenes(this,1);

        if(utilitiesClass.obtener_sesion_usuario(SplashActivity.this)){
            iniciarSesionAutomatizado();
        }else{
            SQLiteDatabase.deleteDatabase(new File(turedUserBD.DATABASE_NAME));
        }


    }

    public void nextActivity(){
        animacion.stop(); //Paramos el AnimationDrawable
        Intent intento = new Intent(this, LoginActivity.class); // Lanzamos SiguienteActivity
        intento.putExtra("mensaje_inicio", "");
        startActivity(intento);
        finish(); //Finalizamos este activity
    }


    private void iniciarSesionAutomatizado (){
        utilitiesTuRedBD util_tu_red = new utilitiesTuRedBD(SplashActivity.this);
        List list_usuario_info = new ArrayList();
        DatosMovil data_movil = new DatosMovil();
        String var = data_movil.obtenerIMEI(this);

        list_usuario_info =  util_tu_red.getUsuarioInfo();

        if(list_usuario_info.isEmpty())
            return;

        data_user.setEmail(((Usuario) list_usuario_info.get(0)).getEmail());
        data_user.setPassword(((Usuario) list_usuario_info.get(0)).getPassword());
        data_user.setToken(((Usuario) list_usuario_info.get(0)).getToken());

        if (var.length() == 0) {
            utils_class.msjsSimple(this, "Información", "No es posible obtener informacion del dispositivo", "Aceptar", "", 0, 1);

        } else {
            data_user.setId_dispositivo_usuario(var);
        }


        if (data_user.getEmail().length() == 0 || data_user.getPassword().length() == 0 || data_user.getToken().length() == 0) {
            utils_class.msjsSimple(this, "Información", "Por favor llenar todos los campos", "Aceptar", "", 0, 1);
        } else {
            Intent intento = new Intent(this, MenuPrincActivity.class); // Lanzamos SiguienteActivity
            intento.putExtra("mensaje_saldo", "01");
            startActivity(intento);
            this.finish();
        }
    }
}
