package com.sb.tured.activity.frame;

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
import com.sb.tured.model.ProductInfo;
import com.sb.tured.model.TransactionsReport;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterListLastReportTrans;
import com.sb.tured.utilities.formatText;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class LastReportFrameActivity extends Fragment implements View.OnClickListener, interfacesModelo {


    private ArrayList<TransactionsReport> listaLastReportTrans;
    private List list_last_report_trans;
    private utilitiesClass utils_class;
    private Button button_dia_actual;
    private Button button_tres_dias_pasados;
    private ProgressDialog progressDialog;
    private utilitiesTuRedBD utils_bd;
    private Context context;
    private int dayOfMonth, monthOfYear, year;
    private ListView list_view;
    private ImageView img_operador;
    private List productos_local;
    private ProductInfo objaux;
    private ValidateJson validate_user = new ValidateJson();
    final Calendar c = Calendar.getInstance();
    private TableLayout table;
    private TextView text_operador_report;
    private TextView text_referencia_report;
    private TextView text_valor_report;
    private TextView text_fecha_report;
    private TextView text_autorizacion_report;
    private TextView text_ventas_total;
    private TextView text_rangos_fecha;
    private formatText format_text;
    private View view;
    private long total_ventas = 0;
    private TableRow tableRow;
    private String msj = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.activity_frame_report_trans, container, false);
        context = view.getContext();


        initComponent();
        text_ventas_total.setVisibility(View.GONE);
        text_rangos_fecha.setVisibility(View.GONE);
        table.setVisibility(View.GONE);
        setHasOptionsMenu(true);

        listaLastReportTrans = new ArrayList<>();
        utils_bd = new utilitiesTuRedBD(context);
        list_view = new ListView(context);
        registerForContextMenu(img_operador);


        progressDialog = new ProgressDialog(context);
        //contexMenu.setsetAdapter(adapter);
        //list_view.setAdapter(listaMenuHome);
        //registerForContextMenu(list_view);


        //button_buscar_trans_report.setOnClickListener(this);
        button_dia_actual.setOnClickListener(this);
        button_tres_dias_pasados.setOnClickListener(this);


        return view;
    }

    private void initComponent() {

        //button_buscar_trans_report =  view.findViewById(R.id.buttonBuscarReport);
        button_tres_dias_pasados =  view.findViewById(R.id.buttonReportDayActul);
        button_dia_actual =  view.findViewById(R.id.buttonReportThreeLastDay);
        img_operador = view.findViewById(R.id.logoOperTransReport);
        utils_class = new utilitiesClass();
        objaux = new ProductInfo();
        table = view.findViewById(R.id.TablaReport);
        text_ventas_total = view.findViewById(R.id.textSaldoTotalReport);
        text_rangos_fecha = view.findViewById(R.id.textFechaRangos);

        text_ventas_total.setText("");
        format_text = new formatText();


    }


    private void llenarListaNomOper(Menu menu_local) {

        int contador = 0;
        int x = 0;
        productos_local = utils_bd.getAllProductos();


        //WPLAY-SOAT-CERTIFICADOS-RECARGAS
       // while (contador < 3) {
            x = 0;
            for (x = 0; x < productos_local.size(); x++) {
                objaux = (ProductInfo) productos_local.get(x);
                if (objaux.getNombre().equals("RECARGAS") && contador == 0) {
                    menu_local.findItem(R.id.ctx_opc_uno).setTitle(objaux.getNombre());
                    contador++;
                } else if (objaux.getNombre().equals("WPLAY") && contador == 1) {
                    menu_local.findItem(R.id.ctx_opc_dos).setTitle(objaux.getNombre());
                    contador++;
                } else if (objaux.getNombre().equals("SOAT") && contador == 2) {
                    menu_local.findItem(R.id.ctx_opc_tres).setTitle(objaux.getNombre());
                    contador++;
                }
            }
       // }
    }

    void llenarListTrans() {

        total_ventas = 0;
        int num_columnas = 5;
        int num_filas;
        //TableRow tableRow = (TableRow)view.findViewById(R.id.Cabecera);

        table.setVisibility(View.VISIBLE);
        text_ventas_total.setVisibility(View.VISIBLE);
        text_rangos_fecha.setVisibility(View.VISIBLE);

        list_last_report_trans = utils_bd.getAllTransReportProductos();
        listaLastReportTrans.removeAll(listaLastReportTrans);
        for (int x = 0; x < list_last_report_trans.size(); x++) {
            listaLastReportTrans.add((TransactionsReport) list_last_report_trans.get(x));
        }

        num_filas = listaLastReportTrans.size() + 1;
        //TableRow tableRow;


        table.removeAllViews();
        for (int i = 0; i < num_filas; i++) {

            tableRow = new TableRow(getContext());

            //TableRow tableRow = view.findViewById(R.id.cabeceraReport);
            text_operador_report = new TextView(view.getContext());
            text_referencia_report = new TextView(view.getContext());
            text_valor_report = new TextView(view.getContext());
            text_fecha_report = new TextView(view.getContext());
            text_autorizacion_report = new TextView(view.getContext());


            for (int j = 0; j < num_columnas; j++) {
                tableRow.addView(llenarCabeceraTablaReporte(i, j));
            }
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    200, 200));
            table.addView(tableRow);
        }

        table.setBackgroundColor(getResources().getColor(R.color.white));
        text_ventas_total.setText("TOTAL VENTAS " + format_text.longFormat(total_ventas));

        /*
        adapterListLastReportTrans adapter = new adapterListLastReportTrans(listaLastReportTrans);
        recyclerLastTransReport.setLayoutManager(new LinearLayoutManager(context));
        recyclerLastTransReport.setAdapter(adapter);*/
    }

    private TextView llenarCabeceraTablaReporte(int i, int opc) {
        if (i == 0) {
            switch (opc) {
                case 0:
                    text_operador_report.setText("  Operador  ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        text_operador_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    }
                    return text_operador_report;
                case 1:
                    text_referencia_report.setText("  Referencia  ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        text_referencia_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    }
                    return text_referencia_report;
                case 2:
                    text_valor_report.setText("  Valor  ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        text_valor_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    }
                    return text_valor_report;
                case 3:
                    text_fecha_report.setText("  Fecha  ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        text_fecha_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    }
                    return text_fecha_report;
                case 4:
                    text_autorizacion_report.setText("   Autorizacion  ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        text_autorizacion_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    }
                    return text_autorizacion_report;
            }
        } else {
            i = i - 1;
            switch (opc) {
                case 0:
                    text_operador_report.setText("   " + listaLastReportTrans.get(i).getOperador() + "   ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        text_operador_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    }
                    if (listaLastReportTrans.get(i).getCodigo_respuesta().equals("Error")) {
                        text_operador_report.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_fail_trans, 0, 0, 0);
                    } else {
                        text_operador_report.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_successful_trans, 0, 0, 0);
                    }
                    return text_operador_report;
                case 1:
                    text_referencia_report.setText("   " + listaLastReportTrans.get(i).getMin_recarga() + "  ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        text_referencia_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    }
                    return text_referencia_report;
                case 2:
                    text_valor_report.setText("   " + format_text.integerFormat(Integer.parseInt(listaLastReportTrans.get(i).getValor_recarga().replace(".00", ""))) + "  ");
                    if (!listaLastReportTrans.get(i).getCodigo_respuesta().equals("Error")) {
                        total_ventas += Long.parseLong(listaLastReportTrans.get(i).getValor_recarga().replace(".00", ""));
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        text_valor_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    }
                    return text_valor_report;
                case 3:
                    text_fecha_report.setText("   " + listaLastReportTrans.get(i).getFecha_solicitud() + "  ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        text_fecha_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    }
                    return text_fecha_report;
                case 4:
                    text_autorizacion_report.setText("   " + listaLastReportTrans.get(i).getNumero_autorizacion() + "  ");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        text_autorizacion_report.setBackground(getResources().getDrawable(R.drawable.border_morado));
                    }
                    return text_autorizacion_report;
            }

        }
        return null;
    }

    @Override
    public void onClick(View view) {

        progressDialog.setMessage("Consultando...");
        progressDialog.show();


        if(text_ventas_total.getVisibility() == View.VISIBLE){
            text_ventas_total.setVisibility(View.GONE);
            table.setVisibility(View.GONE);
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


        if (monthOfYear < 10 && dayOfMonth < 10) {
            fecha_inicial = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
        } else if (monthOfYear < 10) {
            fecha_inicial = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
        } else if (dayOfMonth < 10) {
            fecha_inicial = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
        } else {
            fecha_inicial = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        }
        fecha_final = fecha_inicial;

        switch (view.getId()) {

            case R.id.buttonReportThreeLastDay:

                c.add(Calendar.DATE, -1);
                dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                monthOfYear = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);

                if ((monthOfYear+1) < 10 && dayOfMonth < 10) {
                    fecha_final = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                } else if ((monthOfYear+1) < 10) {
                    fecha_final = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                } else if (dayOfMonth < 10) {
                    fecha_final = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                } else {
                    fecha_final = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                }

                fecha_inicial = fecha_final;
                c.add(Calendar.DATE, +1);

                break;
        }

        if (interfacesModelo.data_user.getId_producto_trans_report() == null || interfacesModelo.data_user.getId_producto_trans_report().length() == 0) {
            progressDialog.dismiss();
            utils_class.msjsSimple(context, "INFORMACION", "Por favor seleccione un producto", "Aceptar", "", 0,1);
            return;
        }
        interfacesModelo.data_user.setFecha_inicio_report_trans(fecha_inicial);
        interfacesModelo.data_user.setFecha_final_report_trans(fecha_final);
        text_rangos_fecha.setText(fecha_inicial+" a "+fecha_final);
        new ConsumeServicesExpress().consume_api(6, new interfaceOnResponse() {
            @Override
            public void finish_consume_services() {
                finish_process();
            }

            @Override
            public void solicit_token_error_services(String mensaje_token) {
                Activity actividad = (Activity) context;
                progressDialog.dismiss();
                Intent intento = new Intent(context, LoginActivity.class);
                intento.putExtra("mensaje_inicio", mensaje_token);
                startActivity(intento);
                actividad.finish();
            }

            @Override
            public void finish_fail_consume_services(int codigo) {
                progressDialog.dismiss();
                if(codigo == 9999){
                    utils_class.msjsSimple(context, "INFORMACION SERVICIO", "Error en el servicio codigo INTERNET " + codigo, "Aceptar", "", 0, 1);
                }else {
                    utils_class.msjsSimple(context, "INFORMACION SERVICIO", "Error en el servicio codigo " + codigo, "Aceptar", "", 0, 1);
                }
                return;
            }
        },context);
    }


    public void finish_process() {

        progressDialog.dismiss();
        progressDialog.setMessage("Procesando...");
        progressDialog.show();
        msj = validate_user.data_reporte_transacciones(context);
        if (msj.equals("404")) {
            progressDialog.dismiss();
            utils_class.msjsSimple(context, "INFORMACION", "No existen transacciones", "Aceptar", "", 0,1);
            if (table.getVisibility() == View.VISIBLE) {
                table.setVisibility(View.GONE);
            }

            if(text_ventas_total.getVisibility() == View.VISIBLE){
                text_ventas_total.setVisibility(View.GONE);
                table.setVisibility(View.GONE);
            }
            if(text_rangos_fecha.getVisibility() == View.VISIBLE){
                text_rangos_fecha.setVisibility(View.GONE);
            }
        }else if(!json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            progressDialog.dismiss();
            utils_class.msjsSimple(context, "INFORMACION", msj, "Aceptar", "", 0,1);
        } else {
            llenarListTrans();
            progressDialog.dismiss();
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menu_only = new MenuInflater(context);
        menu.setHeaderTitle("Elija un producto:");
        menu_only.inflate(R.menu.menu_contextual_last_trans_report, menu);
        llenarListaNomOper(menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (getUserVisibleHint()) {
            String id_producto = "";
            if (item.getTitle().toString().equals("RECARGAS")) {
                id_producto = buscarID_producto(item.getTitle().toString());
            } else {
                id_producto = buscarID_producto(item.getTitle().toString());
            }
            if (id_producto.length() == 0) {
                utils_class.msjsSimple(context, "INFORMACION", "Por favor seleccione un operador", "Aceptar", "", 0,1);
            } else {
                interfacesModelo.data_user.setId_producto_trans_report(id_producto);
            }
            return true;
        } else {
            return false;
        }

    }


    String buscarID_producto(String nombre_producto) {
        String id = "";

        for (int x = 0; x < productos_local.size(); x++) {
            objaux = (ProductInfo) productos_local.get(x);
            if (objaux.getNombre().equals(nombre_producto) && Integer.parseInt(objaux.getId()) > 1) {
                id = objaux.getId();
                Picasso.get()
                        .load(objaux.getUrl_img())
                        .transform(new RoundedTransformation(50, 4))
                        .resize(300, 300)
                        .centerCrop()
                        .into(img_operador);
                break;
            } else if (objaux.getNombre().equals(nombre_producto) && Integer.parseInt(objaux.getId()) == 1) {
                id = objaux.getId();
                img_operador.setImageResource(R.drawable.oper_recargas_trans_report);
                break;
            }

        }
        return id;
    }

}
