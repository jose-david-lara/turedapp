package com.sb.tured.activity.frame;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.JsonObject;
import com.sb.tured.R;
import com.sb.tured.activity.LastTransActivity;
import com.sb.tured.activity.LoginActivity;
import com.sb.tured.controller.ValidateJson;
import com.sb.tured.interfaces.interfaceOnResponse;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.io.ConsumeServicesExpress;
import com.sb.tured.model.HomeMenu;
import com.sb.tured.model.TransactionsLast;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterListLastFiveTrans;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class LastFiveFrameActivity extends Fragment implements View.OnClickListener, interfacesModelo {

    private Context context;
    private ProgressDialog progressDialog;
    private View view;
    private ArrayList<TransactionsLast> listaLastFiveTrans;
    private List list_last_five_trans;
    private TransactionsLast trans_last_five;
    private utilitiesTuRedBD utils_bd;
    private Button actualizar_transacciones;
    private ValidateJson validate_user;
    private Calendar c = null;
    private utilitiesClass utils_class;
    private RecyclerView recyclerLastFiveTrans;
    private String msj = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.activity_frame_last_five_trans, container, false);
        context = view.getContext();
        actualizar_transacciones = view.findViewById(R.id.buttonBuscarReportFiveLast);
        progressDialog = new ProgressDialog(context);
        recyclerLastFiveTrans = view.findViewById(R.id.RecyclerViewFiveLastTrans);
        listaLastFiveTrans = new ArrayList<>();
        trans_last_five = new TransactionsLast();
        utils_bd = new utilitiesTuRedBD(context);
        validate_user = new ValidateJson();
        c = Calendar.getInstance();
        utils_class = new utilitiesClass();

        actualizar_transacciones.setOnClickListener(this);


        return view;
    }

    private boolean llenarLista () {


        listaLastFiveTrans.removeAll(listaLastFiveTrans);
        list_last_five_trans = utils_bd.getAllLastTrans();
        if (((TransactionsLast) list_last_five_trans.get(0)).getId().length() != 0) {
            for (int x = 0; x < list_last_five_trans.size(); x++) {
                listaLastFiveTrans.add((TransactionsLast) list_last_five_trans.get(x));
            }

            adapterListLastFiveTrans adapter = new adapterListLastFiveTrans(listaLastFiveTrans);
            recyclerLastFiveTrans.setLayoutManager(new LinearLayoutManager(context));
            recyclerLastFiveTrans.setAdapter(adapter);

            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        String fecha;
        int dayOfMonth, monthOfYear, year;
        switch (view.getId()) {
            case R.id.buttonBuscarReportFiveLast:
                progressDialog.setMessage("Consultando...");
                progressDialog.show();
               /* c.getTime();
                DatePickerDialog datePickerDialog;
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                monthOfYear = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);
                if (monthOfYear < 10 && dayOfMonth < 10) {
                    fecha = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                } else if (monthOfYear < 10) {
                    fecha = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                } else if (dayOfMonth < 10) {
                    fecha = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                } else {
                    fecha = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                }
                interfacesModelo.data_user.setFecha_inicio_report_trans(fecha);
                interfacesModelo.data_user.setFecha_final_report_trans(fecha);*/
                solicitarUltimasTransacciones();
                break;
        }
    }


    private void finish_process_last_five (){


        progressDialog.dismiss();
        msj = validate_user.data_reporte_ultimas_transacciones(context);
        if (msj.equals("404") ) {
            utils_class.msjsSimple(context, "INFORMACION", "No existen transacciones", "Aceptar", "", 0,1);
            if (recyclerLastFiveTrans.getVisibility() == View.VISIBLE) {
                recyclerLastFiveTrans.setVisibility(View.GONE);
            }
        }else if(!json_object_response.getJson_object_response().get("code").getAsString().equals("0")){
            utils_class.msjsSimple(context, "INFORMACION", msj, "Aceptar", "", 0,1);
        } else{
            llenarLista();
        }

    }

    private void solicitarUltimasTransacciones (){

        new ConsumeServicesExpress().consume_api(12, new interfaceOnResponse() {
            @Override
            public void finish_consume_services() {
                finish_process_last_five();
            }

            @Override
            public void solicit_token_error_services(String mensaje_token) {
                Activity actividad = (Activity) context;
                progressDialog.dismiss();
                //json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
                //utils_class.msjs_opciones_simple(context,"INFORMACION", json_object_response.getJson_response_data().get("message").getAsString(), "Aceptar", "");
                Intent intento = new Intent(context, LoginActivity.class);
                intento.putExtra("mensaje_inicio", mensaje_token);
                startActivity(intento);
                actividad.finish();
            }

            @Override
            public void finish_fail_consume_services(int codigo) {
                progressDialog.dismiss();
                if(codigo == 999){
                    utils_class.msjsSimple(context, "INFORMACION SERVICIO", "Error en el servicio codigo INTERNET " + codigo, "Aceptar", "", 0, 1);
                }else {
                    utils_class.msjsSimple(context, "INFORMACION SERVICIO", "Error en el servicio codigo " + codigo, "Aceptar", "", 0, 1);
                }
            }
        },context);

    }


}
