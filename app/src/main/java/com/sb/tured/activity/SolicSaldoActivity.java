package com.sb.tured.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.sb.tured.utilities.NumberTextWatcher;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterListConsultSaldo;
import com.sb.tured.utilities.adapterListSolicitudSaldo;
import com.sb.tured.utilities.formatText;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SolicSaldoActivity extends AppCompatActivity implements View.OnClickListener, interfacesModelo {

    //Button init_button;
    RecyclerView recyclerSolictSaldo;
    private List lista_producto_info;
    private List lista_productos_balance;
    private BalanceProductos objaux;
    private ImageView button_back_activity;
    private ImageView icono_rol_usuario;
    private ImageView imagen_operador_solict_saldo;
    private ImageView button_info;
    private utilitiesTuRedBD utils_bd;
    private formatText format_text;
    private ArrayList<BalanceProductos> ArraylistaBalanceProductos;
    private ArrayList<ProductInfo> ArraylistaProductoInfo;
    private Button button_consulta_saldo;
    private Button button_aceptar_recargar_saldo;
    private Button button_cancelar_recargas_saldo;
    private ProgressDialog progressDialog;
    private ValidateJson validate_user;
    private utilitiesClass utils_class;
    private TextView tittle_bar;
    private TextView text_body_solict_producto;
    private EditText cap_valor_recarga_saldo;
    private String id_producto;
    private String nombre_producto;
    private String msj="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solic_saldo);
        initComponents();
        llenarLayout();

        final adapterListSolicitudSaldo adapter = new adapterListSolicitudSaldo(ArraylistaBalanceProductos, ArraylistaProductoInfo);
        recyclerSolictSaldo.setLayoutManager(new LinearLayoutManager(this));


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagen_operador_solict_saldo.setVisibility(View.VISIBLE);

                //if (adapter.balance_productos.get(recyclerSolictSaldo.getChildPosition(v)).getNombre().equals("RECARGAS")) {
                //    imagen_operador_solict_saldo.setImageResource(R.drawable.oper_recargas_trans_report);
                //} else {
                    Picasso.get()
                            .load(adapter.balance_productos.get(recyclerSolictSaldo.getChildPosition(v)).getUrl_img())
                            .transform(new RoundedTransformation(10, 1))
                            .resize(265, 265)
                            .centerCrop()
                            .into(imagen_operador_solict_saldo);
               // }

                id_producto = adapter.balance_productos.get(recyclerSolictSaldo.getChildPosition(v)).getProductos_id();
                nombre_producto = adapter.balance_productos.get(recyclerSolictSaldo.getChildPosition(v)).getNombre();
                recyclerSolictSaldo.setVisibility(View.GONE);
                text_body_solict_producto.setVisibility(View.GONE);
                cap_valor_recarga_saldo.setText("");
                cap_valor_recarga_saldo.setVisibility(View.VISIBLE);
                button_aceptar_recargar_saldo.setVisibility(View.VISIBLE);
                button_cancelar_recargas_saldo.setVisibility(View.VISIBLE);
                cap_valor_recarga_saldo.addTextChangedListener(new NumberTextWatcher(cap_valor_recarga_saldo));

            }
        });

        recyclerSolictSaldo.setAdapter(adapter);


        actionevents();

    }

    private void initComponents() {
        //init_button = (Button) findViewById(R.id.buttonInit);
        recyclerSolictSaldo = findViewById(R.id.RecyclerViewSolictSaldo);
        objaux = new BalanceProductos();
        ArraylistaBalanceProductos = new ArrayList<>();
        ArraylistaProductoInfo = new ArrayList<>();
        button_info = findViewById(R.id.buttonInfo);
        button_consulta_saldo = findViewById(R.id.buttonActualizarSaldo);
        button_back_activity = findViewById(R.id.buttonBack);
        button_aceptar_recargar_saldo = findViewById(R.id.buttonAceptarSolictSaldo);
        button_cancelar_recargas_saldo = findViewById(R.id.buttonCancelarSolictSaldo);
        progressDialog = new ProgressDialog(this);
        validate_user = new ValidateJson();
        utils_class = new utilitiesClass();
        tittle_bar = findViewById(R.id.tittle_toolbar);
        text_body_solict_producto = findViewById(R.id.textBodySolictProducto);
        icono_rol_usuario = findViewById(R.id.iconoUsuarioRol);
        imagen_operador_solict_saldo = findViewById(R.id.imgOperadorSolictSaldo);
        cap_valor_recarga_saldo = findViewById(R.id.capSaldoSolictSaldo);
        format_text = new formatText();
    }

    void llenarLayout() {

        button_info.setVisibility(View.GONE);
        imagen_operador_solict_saldo.setVisibility(View.GONE);
        button_aceptar_recargar_saldo.setVisibility(View.GONE);
        button_cancelar_recargas_saldo.setVisibility(View.GONE);
        cap_valor_recarga_saldo.setVisibility(View.GONE);
        tittle_bar.setText("Solicitud De Saldo");


        Picasso.get()
                .load("https://multiservicios.madefor.work/assets/img/pdv.png")
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(icono_rol_usuario);
       // icono_rol_usuario = interfacesModelo.carga_imagenes.img.get(0);


        utils_bd = new utilitiesTuRedBD(this);


        lista_productos_balance = utils_bd.getAllBalanceProductos();
        lista_producto_info = utils_bd.getAllProductos();
        ArraylistaBalanceProductos.removeAll(ArraylistaBalanceProductos);

        for (int x = 0; x < lista_productos_balance.size(); x++) {
            if(Integer.parseInt(((BalanceProductos) lista_productos_balance.get(x)).getId()) > -1) {
                ArraylistaBalanceProductos.add((BalanceProductos) lista_productos_balance.get(x));
            }
        }

        for (int x = 0; x < lista_producto_info.size(); x++) {
            ArraylistaProductoInfo.add((ProductInfo) lista_producto_info.get(x));
        }
    }


    @Override
    public void onClick(View v) {
        /*if(v.getId() == button_consulta_saldo.getId()){
            llenarLayout();
        }else if(v.getId() == button_back_activity.getId()){
            nextActivity();
        }*/
    }

    public void nextActivity() {
        Intent intento = new Intent(this, MenuPrincActivity.class);


        intento.putExtra("mensaje_saldo", "00");

        startActivity(intento);
        SolicSaldoActivity.this.finish();
    }


    public void finish_process(int opc) {

        if(opc == 1) {
            try {
                if(getCurrentFocus()!=null) {
                    hideSoftKeyboard(SolicSaldoActivity.this);
                }
            }catch (Exception ex){
                progressDialog.dismiss();
            }
            progressDialog.dismiss();
        }

        if (opc == 1) {
            msj = validate_user.data_getsaldorecarga(this);
            utils_class.msjsSimple(this, "INFORMACION", msj, "Aceptar", "", 0,0);
            if (!json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
                recyclerSolictSaldo.setVisibility(View.VISIBLE);
                text_body_solict_producto.setVisibility(View.VISIBLE);
                cap_valor_recarga_saldo.setVisibility(View.GONE);
                imagen_operador_solict_saldo.setVisibility(View.GONE);
                button_aceptar_recargar_saldo.setVisibility(View.GONE);
                button_cancelar_recargas_saldo.setVisibility(View.GONE);
            }else{
                actualizarSaldos();
            }

        } else {
            msj = validate_user.data_getsaldo(this);
            msjsSimple("INFORMACION", msj, "Aceptar", "", 2);
        }
    }

    @Override
    public void onBackPressed() {
        nextActivity();
    }


    private void actionevents() {
        button_back_activity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onBackPressed();
                }
                return false;
            }
        });

        button_aceptar_recargar_saldo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    msjsSimple("CONFIRMACION", "¿Desea realizar la solicitud de saldo para el producto "+nombre_producto+" por valor de $"+cap_valor_recarga_saldo.getText().toString()+"?", "Aceptar", "Cancelar", 1);

                }
                return false;
            }
        });

        button_cancelar_recargas_saldo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (recyclerSolictSaldo.getVisibility() == View.GONE) {
                        recyclerSolictSaldo.setVisibility(View.VISIBLE);
                        text_body_solict_producto.setVisibility(View.VISIBLE);
                        cap_valor_recarga_saldo.setVisibility(View.GONE);
                        imagen_operador_solict_saldo.setVisibility(View.GONE);
                        button_aceptar_recargar_saldo.setVisibility(View.GONE);
                        button_cancelar_recargas_saldo.setVisibility(View.GONE);
                        if(getCurrentFocus()!=null) {
                            hideSoftKeyboard(SolicSaldoActivity.this);
                        }

                    }
                }
                return false;
            }
        });


    }

    private void solicitarSaldo() {
        String auxTemp;
        progressDialog.setMessage("Solicitando Saldo...");
        auxTemp = cap_valor_recarga_saldo.getText().toString().replace("$","");
        auxTemp = auxTemp.replace(",","");
        progressDialog.show();
        data_user.setValor_prestamo_saldo(auxTemp);
        data_user.setId_producto_trans_report(id_producto);
        new ConsumeServicesExpress().consume_api(11, new interfaceOnResponse() {
            @Override
            public void finish_consume_services() {
                finish_process(1);
            }

            @Override
            public void solicit_token_error_services(String mensaje_token) {
                progressDialog.dismiss();
                //json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
                //utils_class.msjs_opciones_simple(SolicSaldoActivity.this,"INFORMACION", json_object_response.getJson_response_data().get("message").getAsString(), "Aceptar", "");
                Intent intento = new Intent(SolicSaldoActivity.this, LoginActivity.class);
                intento.putExtra("mensaje_inicio", mensaje_token);
                startActivity(intento);
                SolicSaldoActivity.this.finish();
            }

            @Override
            public void finish_fail_consume_services(int codigo) {
                progressDialog.dismiss();
                if(codigo == 999){
                    utils_class.msjsSimple(SolicSaldoActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo INTERNET " + codigo, "Aceptar", "", 0, 1);
                }else {
                    utils_class.msjsSimple(SolicSaldoActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo " + codigo, "Aceptar", "", 0, 1);
                }
                return;
            }
        },this);
    }

    private void actualizarSaldos() {
        new ConsumeServicesExpress().consume_api(5, new interfaceOnResponse() {
            @Override
            public void finish_consume_services() {
                finish_process(2);
            }

            @Override
            public void solicit_token_error_services(String mensaje_token) {
                progressDialog.dismiss();
                //json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
                //utils_class.msjs_opciones_simple(SolicSaldoActivity.this,"INFORMACION", json_object_response.getJson_response_data().get("message").getAsString(), "Aceptar", "");
                Intent intento = new Intent(SolicSaldoActivity.this, LoginActivity.class);
                intento.putExtra("mensaje_inicio", mensaje_token);
                startActivity(intento);
                SolicSaldoActivity.this.finish();
            }

            @Override
            public void finish_fail_consume_services(int codigo) {
                progressDialog.dismiss();
                if(codigo == 999){
                    utils_class.msjsSimple(SolicSaldoActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo INTERNET " + codigo, "Aceptar", "", 0, 1);
                }else {
                    utils_class.msjsSimple(SolicSaldoActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo " + codigo, "Aceptar", "", 0, 1);
                }
                return;
            }
        },this);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        SolicSaldoActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void msjsSimple(String Tittle, String msjBody, String accept, String deny, final int parametroAceptar) {


        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle(Tittle);
        dialogo.setMessage(msjBody);
        dialogo.setCancelable(false);
        if(accept.length() > 0) {
            dialogo.setPositiveButton(accept, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                   aceptarDialogo(parametroAceptar);
                }

            });
        }
        if(deny.length() > 0) {
            dialogo.setNegativeButton(deny, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {
                    denegarDialogo();
                }
            });
        }
        dialogo.show();

    }

    private void aceptarDialogo (int opc){
        switch(opc){
            case 1:
                solicitarSaldo();
                break;
            case 2:
                nextActivity();
                break;
        }
    }

    private void denegarDialogo (){
        /*if (recyclerSolictSaldo.getVisibility() == View.GONE) {
            recyclerSolictSaldo.setVisibility(View.VISIBLE);
            text_body_solict_producto.setVisibility(View.VISIBLE);
            cap_valor_recarga_saldo.setVisibility(View.GONE);
            imagen_operador_solict_saldo.setVisibility(View.GONE);
            button_aceptar_recargar_saldo.setVisibility(View.GONE);
            button_cancelar_recargas_saldo.setVisibility(View.GONE);
            hideSoftKeyboard(SolicSaldoActivity.this);
        }*/

    }

}
