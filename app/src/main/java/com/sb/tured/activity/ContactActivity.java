package com.sb.tured.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.controller.ValidateJson;
import com.sb.tured.interfaces.interfaceOnResponse;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.io.ConsumeServicesExpress;
import com.sb.tured.model.BalanceProductos;
import com.sb.tured.model.Usuario;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterListConsultSaldo;
import com.sb.tured.utilities.formatText;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener, interfacesModelo {


    private ImageView button_back_activity;
    private ImageView button_info;
    private ImageView icono_rol_usuario;
    private TextView tittle_bar;
    private TextView tel_one;
    private TextView tel_two;
    private int origen_pantalla;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initComponents();
        llenarLayout();
        actionevents();

    }

    private void initComponents (){

        origen_pantalla = Integer.parseInt(getIntent().getExtras().getString("pantalla"));


        button_back_activity = findViewById(R.id.buttonBack);
        tittle_bar = findViewById(R.id.tittle_toolbar);
        button_info = findViewById(R.id.buttonInfo);
        icono_rol_usuario = findViewById(R.id.iconoUsuarioRol);
        tel_one = findViewById(R.id.textBodyTelOne);
        tel_two = findViewById(R.id.textBodyTelTwo);



    }

    private void llenarLayout(){

        Picasso.get()
                .load("https://multiservicios.madefor.work/assets/img/pdv.png")
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(icono_rol_usuario);
        //icono_rol_usuario = interfacesModelo.carga_imagenes.img.get(0);
        tittle_bar.setText("CONTACTO");
        button_info.setVisibility(View.INVISIBLE);
        button_back_activity.setVisibility(View.INVISIBLE);


    }


    @Override
    public void onClick(View v) {
        if(v.getId() == button_back_activity.getId()){
            nextActivity();
        }else if(v.getId() == tel_one.getId()){
            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:3112306725"));
            if(ActivityCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(i);
        }else if(v.getId() == tel_one.getId()){
            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:0317049362"));
            if(ActivityCompat.checkSelfPermission(ContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                return;
            startActivity(i);
        }
    }

    public void nextActivity(){
        if(origen_pantalla == 1){
            Intent intento = new Intent(this, LoginActivity.class); // Lanzamos SiguienteActivity
            intento.putExtra("mensaje_inicio", "");
            startActivity(intento);
            ContactActivity.this.finish(); //Finalizamos este activity
        }else {
            Intent intento = new Intent(this, MenuPrincActivity.class);
            intento.putExtra("mensaje_saldo", "00");
            startActivity(intento);
            ContactActivity.this.finish();
        }
    }


    @Override
    public void onBackPressed() {
        nextActivity();
    }


    private void actionevents () {
        button_back_activity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onBackPressed();
                }
                return false;
            }
        });

    }
}
