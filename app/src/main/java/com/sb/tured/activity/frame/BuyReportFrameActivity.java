package com.sb.tured.activity.frame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.sb.tured.R;
import com.sb.tured.activity.LoginActivity;
import com.sb.tured.controller.ValidateJson;
import com.sb.tured.interfaces.interfaceOnResponse;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.io.ConsumeServicesExpress;
import com.sb.tured.model.CarteraUsuario;
import com.sb.tured.model.ProductInfo;
import com.sb.tured.model.TransactionsReport;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterListBuyReportTrans;
import com.sb.tured.utilities.formatText;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BuyReportFrameActivity extends Fragment implements View.OnClickListener, interfacesModelo {


    private utilitiesClass utils_class;
    private int dayOfMonth, monthOfYear, year;
    private Button button_dia_actual;
    private Button button_ayer_dias_pasados;
    private ProgressDialog progressDialog;
    private utilitiesTuRedBD utils_bd;
    private Context context;
    private List productos_local_buy;
    private ProductInfo objaux;
    private ValidateJson validate_user = new ValidateJson();
    private final Calendar c= Calendar.getInstance();
    private RecyclerView recyclerBuyTransReport;
    private View view_buy;
    private formatText format_text;
    private TableLayout table_buy;
    private TextView text_operador_report;
    private TextView text_referencia_report;
    private TextView text_valor_report;
    private TextView text_fecha_report;
    private TextView text_autorizacion_report;
    private TextView text_ventas_total;
    private TextView text_rangos_fecha;
    private long total_ventas = 0;
    private TableRow tableRow;
    private String msj = "";


    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view_buy = inflater.inflate(R.layout.activity_frame_report_buy, container, false);
        context = view_buy.getContext();
        initComponents();

        //relative_ly_one_buy.setVisibility(View.GONE);

        text_ventas_total.setVisibility(View.GONE);
        text_rangos_fecha.setVisibility(View.GONE);
        table_buy.setVisibility(View.GONE);
        objaux = new ProductInfo();


        utils_bd = new utilitiesTuRedBD(context);



        progressDialog =new ProgressDialog(context);

        button_ayer_dias_pasados.setOnClickListener(this);
        button_dia_actual.setOnClickListener(this);


        return view_buy;
    }

    void initComponents ( ){

        format_text = new formatText();
        utils_class = new utilitiesClass();
        table_buy = view_buy.findViewById(R.id.TablaReportBuy);
        button_ayer_dias_pasados =  view_buy.findViewById(R.id.buttonReportBuyYerterday);
        button_dia_actual =  view_buy.findViewById(R.id.buttonReportBuyDayActul);
        text_ventas_total = view_buy.findViewById(R.id.textSaldoTotalReportBuy);
        text_rangos_fecha = view_buy.findViewById(R.id.textFechaRangosBuy);

        text_ventas_total.setText("");

    }


    /*private void llenarListaNomOper (Menu menu_local){

        int contador = 0;
        int x;
        productos_local_buy = utils_bd.getAllProductos();


        //WPLAY-SOAT-CERTIFICADOS-RECARGAS-ABONO
        while(contador < 5) {
            for (x = 0; x < productos_local_buy.size(); x++) {
                objaux = (ProductInfo) productos_local_buy.get(x);
                if (objaux.getNombre().equals("RECARGAS") && contador == 0) {
                    menu_local.findItem(R.id.ctx_opc_uno_buy).setTitle(objaux.getNombre());
                    contador++;
                }else if (objaux.getNombre().equals("WPLAY") && contador == 1) {
                        menu_local.findItem(R.id.ctx_opc_dos_buy).setTitle(objaux.getNombre());
                        contador++;
                } else if (objaux.getNombre().equals("SOAT") && contador == 2) {
                        menu_local.findItem(R.id.ctx_opc_tres_buy).setTitle(objaux.getNombre());
                        contador++;
                } else if (objaux.getNombre().equals("CERTIFICADOS") && contador == 3) {
                        menu_local.findItem(R.id.ctx_opc_cuatro_buy).setTitle(objaux.getNombre());
                        contador++;
                        break;
                }else if ( contador == 4) {
                    menu_local.findItem(R.id.ctx_opc_cinco_buy).setTitle("ABONO");
                    contador++;
                    break;
                }
            }
        }
    }*/

    @SuppressLint("SetTextI18n")
    void llenarListTrans() {

        total_ventas = 0;
        int num_columnas = 3;
        int num_filas;

        table_buy.setVisibility(View.VISIBLE);
        text_ventas_total.setVisibility(View.VISIBLE);
        text_rangos_fecha.setVisibility(View.VISIBLE);



        num_filas = interfacesModelo.cartera_usuario_list.size() + 1;
        //TableRow tableRow;


        table_buy.removeAllViews();
        for (int i = 0; i < num_filas; i++) {

            tableRow = new TableRow(getContext());

            //TableRow tableRow = view.findViewById(R.id.cabeceraReport);
            text_operador_report = new TextView(view_buy.getContext());
            text_referencia_report = new TextView(view_buy.getContext());
            text_valor_report = new TextView(view_buy.getContext());
            text_fecha_report = new TextView(view_buy.getContext());
            text_autorizacion_report = new TextView(view_buy.getContext());


            for (int j = 0; j < num_columnas; j++) {
                tableRow.addView(llenarCabeceraTablaReporte(i, j));
            }
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    200, 200));
            table_buy.addView(tableRow);
        }

        table_buy.setBackgroundColor(getResources().getColor(R.color.white));
        text_ventas_total.setText("TOTAL COMPRAS " + format_text.longFormat(total_ventas));


    }

    @SuppressLint("SetTextI18n")
    private TextView llenarCabeceraTablaReporte(int i, int opc) {
        if (i == 0) {
            switch (opc) {
                case 0:
                    text_operador_report.setText("  Fecha  ");
                    text_operador_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    return text_operador_report;

                case 1:
                    text_valor_report.setText("  Compras  ");
                    text_valor_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    return text_valor_report;
                case 2:
                    text_fecha_report.setText("  Producto                ");
                    text_fecha_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    return text_fecha_report;

            }
        } else {
            i = i - 1;
            switch (opc) {
                case 0:
                    text_operador_report.setText("   " + cartera_usuario_list.get(i).getFecha() + "   ");
                    text_operador_report.setBackground(getResources().getDrawable(R.drawable.border_morado));

                    return text_operador_report;

                case 1:
                    text_valor_report.setText("   " + format_text.integerFormat(Integer.parseInt(cartera_usuario_list.get(i).getValor().replace(".00", ""))) + "  ");
                    total_ventas += Long.parseLong(cartera_usuario_list.get(i).getValor().replace(".00", ""));
                    text_valor_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    return text_valor_report;
                case 2:
                    text_fecha_report.setText("   " + cartera_usuario_list.get(i).getNombre() + "                ");
                    text_fecha_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    return text_fecha_report;

            }

        }
        return null;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {

        progressDialog.setMessage("Consultando...");
        progressDialog.show();

        if(text_ventas_total.getVisibility() == View.VISIBLE){
            text_ventas_total.setVisibility(View.GONE);
            table_buy.setVisibility(View.GONE);
        }
        if(text_rangos_fecha.getVisibility() == View.VISIBLE){
            text_rangos_fecha.setVisibility(View.GONE);
        }
        c.getTime();
        String fecha_inicial;
        String fecha_final;
        dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        monthOfYear = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);


        if ((monthOfYear+1) < 10 && dayOfMonth < 10) {
            fecha_inicial = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
        } else if ((monthOfYear+1) < 10) {
            fecha_inicial = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
        } else if (dayOfMonth < 10) {
            fecha_inicial = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
        } else {
            fecha_inicial = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        }
        fecha_final = fecha_inicial;

        if (view.getId() == R.id.buttonReportBuyYerterday) {
            c.add(Calendar.DATE, -1);
            dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            monthOfYear = c.get(Calendar.MONTH);
            year = c.get(Calendar.YEAR);

            if ((monthOfYear + 1) < 10 && dayOfMonth < 10) {
                fecha_final = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
            } else if ((monthOfYear + 1) < 10) {
                fecha_final = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
            } else if (dayOfMonth < 10) {
                fecha_final = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
            } else {
                fecha_final = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            }

            fecha_inicial = fecha_final;
            c.add(Calendar.DATE, +1);
        }


        interfacesModelo.data_user.setFecha_inicio_report_trans(fecha_inicial);
        interfacesModelo.data_user.setFecha_final_report_trans(fecha_final);
        text_rangos_fecha.setText(fecha_inicial+" a "+fecha_final);
        new ConsumeServicesExpress().consume_api(7, new interfaceOnResponse() {
            @Override
            public void finish_consume_services() {
                finish_process_buy();
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



    public void finish_process_buy() {

        progressDialog.dismiss();
        msj = validate_user.data_reporte_cartera_discriminado(context);
        if(msj.equals("404")){
            utils_class.msjsSimple(context,"INFORMACION","No existen transacciones","Aceptar","",0,1);

        }else{
            llenarListTrans();
        }
    }



}
