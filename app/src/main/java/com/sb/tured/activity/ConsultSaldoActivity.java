package com.sb.tured.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.sb.tured.R;
import com.sb.tured.controller.ValidateJson;
import com.sb.tured.interfaces.interfaceOnResponse;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.io.ConsumeServicesExpress;
import com.sb.tured.model.BalanceProductos;
import com.sb.tured.model.ProductInfo;
import com.sb.tured.model.Usuario;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterListConsultSaldo;
import com.sb.tured.utilities.formatText;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ConsultSaldoActivity extends AppCompatActivity implements View.OnClickListener, interfacesModelo {

    //Button init_button;
    RecyclerView recyclerConsulSaldo;
    private List productos_local;
    private List productos_local_balance;
    private BalanceProductos objaux;
    private Usuario user_info;
    private utilitiesTuRedBD utils_bd;
    private formatText format_text;
    private ArrayList<BalanceProductos> listaConsulSaldo;
    private Button button_consulta_saldo;
    private ImageView button_back_activity;
    private ImageView button_info;
    private ImageView icono_rol_usuario;
    private ProgressDialog progressDialog;
    private ValidateJson validate_user;
    private utilitiesClass utils_class;
   // private utilitiesTuRedBD utils_bd;
    private TextView tittle_bar;
    private TextView text_tu_deuda;
    private adapterListConsultSaldo adapter;
    private String msj = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_saldo);
        initComponents();
        llenarLayout();

        adapter = new adapterListConsultSaldo(listaConsulSaldo);
        recyclerConsulSaldo.setLayoutManager(new LinearLayoutManager(this));
        recyclerConsulSaldo.setAdapter(adapter);
        actionevents();

    }

    private void initComponents (){
        //init_button = (Button)findViewById(R.id.buttonInit);
        recyclerConsulSaldo = findViewById(R.id.RecyclerViewConsulSaldo);
        objaux = new BalanceProductos();
        listaConsulSaldo = new ArrayList<>();
        button_consulta_saldo = findViewById(R.id.buttonActualizarSaldo);
        button_back_activity = findViewById(R.id.buttonBack);
        progressDialog =new ProgressDialog(this);
        validate_user = new ValidateJson();
        utils_class = new utilitiesClass();
        tittle_bar = findViewById(R.id.tittle_toolbar);
        button_info = findViewById(R.id.buttonInfo);
        icono_rol_usuario = findViewById(R.id.iconoUsuarioRol);
        text_tu_deuda = findViewById(R.id.tvTuDeudaConsulSaldo);
        user_info = new Usuario();
        format_text = new formatText();
    }

    void llenarLayout( ){

        Picasso.get()
                .load("https://multiservicios.madefor.work/assets/img/pdv.png")
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(icono_rol_usuario);
        //icono_rol_usuario = interfacesModelo.carga_imagenes.img.get(0);



        utils_bd = new utilitiesTuRedBD(this);




        tittle_bar.setText("Consulta De Saldo");
        button_info.setVisibility(View.INVISIBLE);
        try {
            user_info = utils_bd.getUsuarioInfo(data_user.getEmail());

            text_tu_deuda.setVisibility(View.GONE);
            int x = 0;
            int contador = 0;


            progressDialog.setMessage("Consultando Saldo...");
            progressDialog.show();


            new ConsumeServicesExpress().consume_api(5, new interfaceOnResponse() {
                @Override
                public void finish_consume_services() {
                    finish_process();
                    //productos_local = utils_bd.getAllProductos();
                    //productos_local_balance.removeAll(productos_local_balance);
                    //productos_local_balance = utils_bd.getAllBalanceProductos();


                    listaConsulSaldo.removeAll(listaConsulSaldo);
                    for (int x = 0; x < interfacesModelo.producto_saldo_list.size(); x++) {
                        objaux = interfacesModelo.producto_saldo_list.get(x);
                        listaConsulSaldo.add(objaux);
                    }
                    adapter = new adapterListConsultSaldo(listaConsulSaldo);
                    recyclerConsulSaldo.setLayoutManager(new LinearLayoutManager(ConsultSaldoActivity.this));
                    recyclerConsulSaldo.setAdapter(adapter);
                }

                @Override
                public void solicit_token_error_services(String mensaje_token) {
                    progressDialog.dismiss();
                    //json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
                    //utils_class.msjs_opciones_simple(ConsultSaldoActivity.this,"INFORMACION", interfacesModelo.json_object_response.getJson_response_data().get("message").getAsString(), "Aceptar", "");
                    Intent intento = new Intent(ConsultSaldoActivity.this, LoginActivity.class);
                    intento.putExtra("mensaje_inicio", mensaje_token);
                    startActivity(intento);
                    ConsultSaldoActivity.this.finish();
                    return;
                }

                @Override
                public void finish_fail_consume_services(int codigo) {
                    progressDialog.dismiss();
                    try {
                        if (codigo == 999) {
                            utils_class.msjsSimple(ConsultSaldoActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo INTERNET " + codigo, "Aceptar", "", 0, 1);
                        } else {
                            utils_class.msjsSimple(ConsultSaldoActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo " + codigo, "Aceptar", "", 0, 1);
                        }
                    }catch (Exception ex){
                        return;
                    }
                    return;
                }
            }, this);
        }catch (Exception ex){
            utils_class.msjsSimple(ConsultSaldoActivity.this, "INFORMACION SERVICIO", "Error en consultar informacion de usuario, por favor reintente ", "Aceptar", "", 0, 1);
        }



    }



    @Override
    public void onClick(View v) {
        if(v.getId() == button_consulta_saldo.getId()){
            llenarLayout();
        }else if(v.getId() == button_back_activity.getId()){
            nextActivity();
        }
    }

    public void nextActivity(){
        Intent intento = new Intent(this, MenuPrincActivity.class);
        intento.putExtra("mensaje_saldo","00");
        startActivity(intento);
        ConsultSaldoActivity.this.finish();
    }


    public void finish_process() {

        progressDialog.dismiss();
        msj = validate_user.data_getsaldo(this);

        utils_class.msjsSimple(this,"INFORMACION",msj,"Aceptar","",0,1);

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
