package com.sb.tured.activity;

import android.app.AppComponentFactory;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.sb.tured.R;
import com.sb.tured.controller.ValidateJson;
import com.sb.tured.interfaces.interfaceOnResponse;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.io.ConsumeServicesExpress;
import com.sb.tured.model.HomeProductos;
import com.sb.tured.model.ProductInfo;
import com.sb.tured.model.ServOperPackRecarga;
import com.sb.tured.model.TransactionsReport;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterListLastReportTrans;
import com.sb.tured.utilities.adapterListReportSearchRecarga;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ReportSaleSearchRecargaActivity extends AppCompatActivity implements View.OnClickListener,interfacesModelo {



    private EditText cap_fecha_inicial;
    private EditText cap_fecha_final;
    private EditText cap_num_buscar;
    private utilitiesClass utils_class;
    private Button button_buscar_trans_report;
    private ProgressDialog progressDialog;
    private utilitiesTuRedBD utils_bd;
    private Context context;
    private  int dia,mes,ano;
    private ImageView img_operador;
    private List productos_local;
    private HomeProductos objaux;
    private ValidateJson validate_user = new ValidateJson();
    private final Calendar c= Calendar.getInstance();
    private TextView title_tool_bar;
    private ImageView button_back;
    private ImageView button_info;
    private ImageView icono_rol_usuario;
    private RecyclerView recyclerSearchRecarga;
    private String msj = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_search_recarga);
        initComponents();

        Picasso.get()
                .load("https://multiservicios.madefor.work/assets/img/pdv.png")
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(icono_rol_usuario);
        //icono_rol_usuario = interfacesModelo.carga_imagenes.img.get(0);


        button_info.setVisibility(View.INVISIBLE);
        title_tool_bar.setText("Consultas");
        //setHasOptionsMenu(true);
        registerForContextMenu(img_operador);

        button_back.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intento = new Intent(context, MenuPrincActivity.class);
                    intento.putExtra("mensaje_saldo","00");
                    startActivity(intento);
                    ReportSaleSearchRecargaActivity.this.finish();
                }
                return false;
            }
        });
       /* Picasso.get()
                .load("https://multiservicios.madefor.work/assets/img/pdv.png")
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(icono_rol_usuario);

        button_info.setVisibility(View.INVISIBLE);
        tittle_toolbar_local.setText("Transacciones");
        actionevents();*/

    }

    private void initComponents (){
        button_buscar_trans_report = findViewById(R.id.buttonBuscarReport);
        cap_fecha_inicial = findViewById(R.id.capFechaInicial);
        cap_fecha_final = findViewById(R.id.capFechaFinal);
        cap_num_buscar = findViewById(R.id.capNumText);
        recyclerSearchRecarga = findViewById(R.id.RecyclerViewSearchRecarga);
        title_tool_bar = findViewById(R.id.tittle_toolbar);
        button_back = findViewById(R.id.buttonBack);
        button_info = findViewById(R.id.buttonInfo);
        img_operador = findViewById(R.id.logoOperTransReport);
        utils_bd = new utilitiesTuRedBD(this);
        progressDialog =new ProgressDialog(this);
        utils_class = new utilitiesClass();
        icono_rol_usuario = findViewById(R.id.iconoUsuarioRol);
        context = this;
    }


    private void llenarListaNomOper (Menu menu_local){

        int contador = 0;
        int x = 0;
        productos_local = utils_bd.getAllHomeProductos();


        //WPLAY-SOAT-CERTIFICADOS-RECARGAS
       // while(contador < 3) {
            x = 0;
            for (x = 0; x < productos_local.size(); x++) {
                objaux = (HomeProductos) productos_local.get(x);
                if ( contador == 0) {
                    menu_local.findItem(R.id.ctx_opc_uno).setTitle("RECARGAS");
                    contador++;
                }else if (objaux.getOperador().equals("WPLAY")) {
                        menu_local.findItem(R.id.ctx_opc_dos).setTitle(objaux.getOperador());
                        contador++;
                } else if (objaux.getOperador().equals("SOAT")) {
                        menu_local.findItem(R.id.ctx_opc_tres).setTitle(objaux.getOperador());
                        contador++;
                }
            }
        //}
        if(menu_local.findItem(R.id.ctx_opc_uno).getTitle().length() == 0) {
            menu_local.findItem(R.id.ctx_opc_uno).setVisible(false);
        }else if(menu_local.findItem(R.id.ctx_opc_dos).getTitle().length() == 0){
            menu_local.findItem(R.id.ctx_opc_dos).setVisible(false);
        }else if(menu_local.findItem(R.id.ctx_opc_tres).getTitle().length() == 0){
            menu_local.findItem(R.id.ctx_opc_tres).setVisible(false);
        }
    }

    void llenarListTrans ( ){


        adapterListReportSearchRecarga adapter = new adapterListReportSearchRecarga(interfacesModelo.reporte_venta_busqueda,this);
        recyclerSearchRecarga.setLayoutManager(new LinearLayoutManager(context));
        recyclerSearchRecarga.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {


        if(view.getId() == button_back.getId()){
            Intent intento = new Intent(this, MenuPrincActivity.class);
            intento.putExtra("mensaje_saldo","00");
            startActivity(intento);
            ReportSaleSearchRecargaActivity.this.finish();
        }

        c.getTime();
        DatePickerDialog datePickerDialog;
        switch (view.getId()) {
            case R.id.capFechaInicial:

                dia=c.get(Calendar.DAY_OF_MONTH);
                mes=c.get(Calendar.MONTH);
                ano=c.get(Calendar.YEAR);


                 datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if((monthOfYear+1) < 10 && dayOfMonth < 10) {
                            cap_fecha_inicial.setText(year + "-0" + (monthOfYear+1) + "-0" + dayOfMonth);
                        }else if((monthOfYear+1) < 10 ) {
                            cap_fecha_inicial.setText(year + "-0" + (monthOfYear+1) + "-" + dayOfMonth);
                        }else if(dayOfMonth < 10 ) {
                            cap_fecha_inicial.setText(year + "-" + (monthOfYear+1) + "-0" + dayOfMonth);
                        }else{
                            cap_fecha_inicial.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                        }
                    }
                }
                        ,ano,mes,dia);
                datePickerDialog.updateDate(ano,mes,dia);
                datePickerDialog.show();
                break;
            case R.id.capFechaFinal:
                dia=c.get(Calendar.DAY_OF_MONTH);
                mes=c.get(Calendar.MONTH);
                ano=c.get(Calendar.YEAR);


                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if((monthOfYear+1) < 10 && dayOfMonth < 10) {
                            cap_fecha_final.setText(year + "-0" + (monthOfYear+1) + "-0" + dayOfMonth);
                        }else if((monthOfYear+1) < 10 ) {
                            cap_fecha_final.setText(year + "-0" + (monthOfYear+1) + "-" + dayOfMonth);
                        }else if(dayOfMonth < 10 ) {
                            cap_fecha_final.setText(year + "-" + (monthOfYear+1) + "-0" + dayOfMonth);
                        }else{
                            cap_fecha_final.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                        }
                    }
                }
                        ,ano,mes,dia);
                datePickerDialog.updateDate(ano,mes,dia);
                datePickerDialog.show();
                break;
            case R.id.buttonBuscarReport:
                if(cap_fecha_inicial.length() == 0 || cap_fecha_final.length() == 0 ){
                    utils_class.msjsSimple(context,"INFORMACION","Por favor digitar todos los campos","Aceptar","",0,1);
                }else {
                    try {
                        if (interfacesModelo.data_user.getId_producto_trans_report().length() == 0 || interfacesModelo.data_user.getId_producto_trans_report() == null) {
                            utils_class.msjsSimple(context, "INFORMACION", "Por favor seleccione un producto", "Aceptar", "", 0, 1);
                            return;
                        }
                        if (!validarFechas())
                            return;
                        progressDialog.setMessage("Consultando...");
                        progressDialog.show();
                        interfacesModelo.data_user.setFecha_inicio_report_trans(cap_fecha_inicial.getText().toString());
                        interfacesModelo.data_user.setFecha_final_report_trans(cap_fecha_final.getText().toString());
                        interfacesModelo.data_user.setNum_buscar_reporte_busqueda_recarga(cap_num_buscar.getText().toString());

                        new ConsumeServicesExpress().consume_api(9, new interfaceOnResponse() {
                            @Override
                            public void finish_consume_services() {
                                finish_process();
                            }

                            @Override
                            public void solicit_token_error_services(String mensaje_token) {
                                progressDialog.dismiss();
                                //json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
                                //utils_class.msjs_opciones_simple(ReportSaleSearchRecargaActivity.this,"INFORMACION", json_object_response.getJson_response_data().get("message").getAsString(), "Aceptar", "");
                                Intent intento = new Intent(ReportSaleSearchRecargaActivity.this, LoginActivity.class);
                                intento.putExtra("mensaje_inicio", mensaje_token);
                                startActivity(intento);
                                ReportSaleSearchRecargaActivity.this.finish();
                            }

                            @Override
                            public void finish_fail_consume_services(int codigo) {
                                progressDialog.dismiss();
                                if (codigo == 999) {
                                    utils_class.msjsSimple(context, "INFORMACION SERVICIO", "Error en el servicio codigo INTERNET " + codigo, "Aceptar", "", 0, 1);
                                } else {
                                    utils_class.msjsSimple(context, "INFORMACION SERVICIO", "Error en el servicio codigo " + codigo, "Aceptar", "", 0, 1);
                                }
                                return;
                            }
                        }, this);
                    }catch (Exception ex){

                    }

                }
                break;

        }


    }

    boolean validarFechas(){
        try {
            String[] fechaInicial_split = cap_fecha_inicial.getText().toString().split("-");
            String[] fechaFinal_split = cap_fecha_inicial.getText().toString().split("-");
            String fechaInicial = cap_fecha_inicial.getText().toString().replace("-", "");
            String fechaFinal = cap_fecha_final.getText().toString().replace("-", "");


            if (Integer.parseInt(fechaInicial) > Integer.parseInt(fechaFinal)) {
                utils_class.msjsSimple(context, "INFORMACION-ERROR", "Fecha inicial es mayor a la fecha final.", "Aceptar", "", 0, 1);
            } else if (Integer.parseInt(fechaFinal) < Integer.parseInt(fechaInicial)) {
                utils_class.msjsSimple(context, "INFORMACION-ERROR", "Fecha final es menor a la fecha inicial.", "Aceptar", "", 0, 1);
            } else if (Integer.parseInt(fechaInicial_split[0]) > ano) {
                utils_class.msjsSimple(context, "INFORMACION-ERROR", "Fecha inicial es mayor al año actual.", "Aceptar", "", 0, 1);
            } else if (Integer.parseInt(fechaInicial_split[0]) < ano) {
                utils_class.msjsSimple(context, "INFORMACION-ERROR", "Fecha inicial es menor al año actual.", "Aceptar", "", 0, 1);
            } else if (Integer.parseInt(fechaFinal_split[0]) > ano) {
                utils_class.msjsSimple(context, "INFORMACION-ERROR", "Fecha final es mayor al año actual.", "Aceptar", "", 0, 1);
            } else if (Integer.parseInt(fechaFinal_split[0]) < ano) {
                utils_class.msjsSimple(context, "INFORMACION-ERROR", "Fecha final es menor al año actual.", "Aceptar", "", 0, 1);
            } else if ((Integer.parseInt(fechaFinal_split[1]) - Integer.parseInt(fechaInicial_split[1])) >= 1) {
                utils_class.msjsSimple(context, "INFORMACION-ERROR", "El reporte solo se puede generar sobre los ultimos 15 dias.", "Aceptar", "", 0, 1);
            } else if ((Integer.parseInt(fechaFinal_split[1]) - Integer.parseInt(fechaInicial_split[1])) == 0) {
                if ((Integer.parseInt(fechaFinal_split[2]) - Integer.parseInt(fechaInicial_split[2])) > 15) {
                    utils_class.msjsSimple(context, "INFORMACION-ERROR", "El reporte solo se puede generar sobre los ultimos 15 dias.", "Aceptar", "", 0, 1);
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }catch (Exception ex){
            utils_class.msjsSimple(context,"INFORMACION","Error en fechas, por favor verifica los datos ingresados","Aceptar","",0,1);
            return  false;
        }

        return false;
    }

    public void finish_process() {

        progressDialog.dismiss();
        msj = validate_user.data_reporte_busqueda_recarga(context);
        if(msj.equals("404") ){
            utils_class.msjsSimple(context,"INFORMACION","No existen informacion","Aceptar","",0,1);
        }else if(json_object_response.getJson_object_response().get("error").getAsString().equals("true")){
            utils_class.msjsSimple(context,"INFORMACION",msj,"Aceptar","",0,1);
        }else{
            llenarListTrans();
        }
    }

    public class listing extends ListActivity {

    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menu_only = new MenuInflater(context);
        menu.setHeaderTitle("Elija un producto:");
        menu_only.inflate(R.menu.menu_contextual_last_trans_report, menu);
        llenarListaNomOper( menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {


            String id_producto = "";
            if(item.getTitle().toString().equals("RECARGAS")) {
                id_producto = buscarID_producto(item.getTitle().toString());
            }else{
                id_producto = buscarID_producto(item.getTitle().toString());
            }
            if(id_producto.length() == 0){
                utils_class.msjsSimple(context,"INFORMACION","Por favor seleccione un operador","Aceptar","",0,1);
            }else{
                interfacesModelo.data_user.setId_producto_trans_report(id_producto);
            }
            return true;

    }


    String buscarID_producto( String nombre_producto){
        String id = "";

        for(int x = 0; x < productos_local.size(); x++){
            objaux = (HomeProductos) productos_local.get(x);
            if(objaux.getOperador().equals(nombre_producto) && Integer.parseInt(objaux.getId()) >= 1){
                id = objaux.getProductos_id();
                Picasso.get()
                        .load(objaux.getUrl_img())
                        .transform(new RoundedTransformation(50, 4))
                        .resize(300, 300)
                        .centerCrop()
                        .into(img_operador);
                break;
            }else if(nombre_producto.equals("RECARGAS")){
                id = "1";
                img_operador.setImageResource(R.drawable.oper_recargas_trans_report);
                break;
            }

        }
        return id;
    }

    @Override
    public void onBackPressed() {
        Intent intento = new Intent(this, MenuPrincActivity.class);
        intento.putExtra("mensaje_saldo","00");
        startActivity(intento);
        ReportSaleSearchRecargaActivity.this.finish();
    }

}
