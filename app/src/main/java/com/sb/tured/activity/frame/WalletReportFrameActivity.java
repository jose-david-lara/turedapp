package com.sb.tured.activity.frame;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.ColorInt;
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
import android.widget.LinearLayout;
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
import com.sb.tured.model.Producto;
import com.sb.tured.model.TransactionsReport;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterListBuyReportTrans;
import com.sb.tured.utilities.adapterListWalletReportTrans;
import com.sb.tured.utilities.formatText;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class WalletReportFrameActivity extends Fragment implements View.OnClickListener, interfacesModelo {


    /*private ArrayList<CarteraUsuario> listaBuyReportTrans;
    private TransactionsReport trans_buy_report;*/
    private EditText cap_fecha_inicial;
    private EditText cap_fecha_final;
    private utilitiesClass utils_class;
    private Button button_buscar_trans_report;
    private ProgressDialog progressDialog;
    private utilitiesTuRedBD utils_bd;
    private Context context;
    private int dia, mes, ano;
    private ListView list_view;
    //private ImageView img_operador_buy;
    private List productos_local_wallet;
    private ProductInfo objaux;
    private ValidateJson validate_user = new ValidateJson();
    final Calendar c = Calendar.getInstance();
    private RecyclerView recyclerBuyTransReport;
    private View view_buy;
    private TextView text_list_recarga_wallet;
    private TextView text_list_wplay_wallet;
    private TextView text_list_soat_wallet;
    private TextView text_recargas_price_wallet;
    private TextView text_wplay_price_wallet;
    private TextView text_soat_price_wallet;
    private TextView text_total_price_wallet;
    private TextView text_deuda_inicial_wallet;
    private TextView text_deuda_total_wallet;
    private TextView text_abono_wallet;
    private TextView text_deuda_final_wallet;
    private RelativeLayout relative_ly_one_wallet;
    private TableLayout tbl_productos_local;
    private TableLayout tbl_valores_local;
    private formatText format_text;
    private String msj = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view_buy = inflater.inflate(R.layout.activity_frame_report_wallet, container, false);
        context = view_buy.getContext();
        initComponents();

        relative_ly_one_wallet.setVisibility(View.GONE);

        //img_operador_buy = (ImageView)view_buy.findViewById(R.id.logoOperBuyTransReportWallet) ;
        utils_class = new utilitiesClass();

        objaux = new ProductInfo();
        //recyclerBuyTransReport = (RecyclerView)view_buy.findViewById(R.id.RecyclerViewWalletReportTrans);
        //et1 = (EditText)view.findViewById(R.id.ETViewReportTrans);
        //setHasOptionsMenu(true);

        /*listaBuyReportTrans = new ArrayList<>();
        trans_buy_report = new TransactionsReport();*/
        utils_bd = new utilitiesTuRedBD(context);
        list_view = new ListView(context);
        //registerForContextMenu(img_operador_buy);


        progressDialog = new ProgressDialog(context);


        cap_fecha_inicial.setOnClickListener(this);
        cap_fecha_final.setOnClickListener(this);
        button_buscar_trans_report.setOnClickListener(this);


        return view_buy;
    }

    void initComponents() {

        format_text = new formatText();
        utils_class = new utilitiesClass();
        cap_fecha_inicial = view_buy.findViewById(R.id.capFechaInicialWallet);
        cap_fecha_final = view_buy.findViewById(R.id.capFechaFinalWallet);
        button_buscar_trans_report = view_buy.findViewById(R.id.buttonBuscarReportWallet);
        //img_operador_buy = (ImageView)view_buy.findViewById(R.id.logoOperBuyTransReportBuy) ;

        /*text_list_recarga_wallet = (TextView)view_buy.findViewById(R.id.textrecargas_wallet);
        text_list_wplay_wallet = (TextView)view_buy.findViewById(R.id.textwplay_wallet);
        text_list_soat_wallet = (TextView)view_buy.findViewById(R.id.textsoat_wallet);*/
        /*text_recargas_price_wallet = (TextView) view_buy.findViewById(R.id.pricerecarga_wallet);
        text_wplay_price_wallet = (TextView) view_buy.findViewById(R.id.pricewplay_wallet);
        text_soat_price_wallet = (TextView) view_buy.findViewById(R.id.pricesoat_wallet);*/
        text_total_price_wallet = view_buy.findViewById(R.id.pricetotal_wallet);
        text_deuda_inicial_wallet = view_buy.findViewById(R.id.pricedeuda_inicial_wallet);
        text_deuda_total_wallet = view_buy.findViewById(R.id.pricedeuda_total_wallet);
        text_abono_wallet = view_buy.findViewById(R.id.priceabono_wallet);
        text_deuda_final_wallet = view_buy.findViewById(R.id.pricedeuda_final_wallet);
        relative_ly_one_wallet = view_buy.findViewById(R.id.relativeOneBodyWallet);
        tbl_productos_local = view_buy.findViewById(R.id.tbl_productos);
        tbl_valores_local = view_buy.findViewById(R.id.tbl_valores);
    }


    /*private void llenarListaNomOper (Menu menu_local){

        int contador = 0;
        int x = 0;
        productos_local_wallet = utils_bd.getAllProductos();


        //WPLAY-SOAT-CERTIFICADOS-RECARGAS-ABONO
        while(contador < 5) {
            x = 0;
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

    void llenarListTrans() {
        long total = 0;
        tbl_productos_local.removeAllViews();
        tbl_valores_local.removeAllViews();

        try {
            for(int x = 0; x < interfacesModelo.cartera_usuario_list.get(0).productos_info.size(); x++){
                //Productos
                TableRow fila_productos = new TableRow(context);
                fila_productos.setLeft(10);

                fila_productos.setId(x+1);
                TextView text_producto = new TextView(context);
                text_producto.setId(x+2);
                text_producto.setText(interfacesModelo.cartera_usuario_list.get(0).productos_info.get(x).getNombre());
                text_producto.setTextColor(Color.WHITE);
                text_producto.setTextSize(18);
                text_producto.setLeft(20);

                text_producto.setTypeface(null, Typeface.BOLD);
                fila_productos.addView(text_producto, ViewGroup.MarginLayoutParams.WRAP_CONTENT,ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                tbl_productos_local.addView(fila_productos);

                //valores
                TableRow fila_valores = new TableRow(context);
                fila_valores.setLeft(10);

                fila_valores.setId(x+1);
                TextView text_valores = new TextView(context);
                text_valores.setId(x+2);
                text_valores.setText(format_text.integerFormat(Integer.parseInt(interfacesModelo.cartera_usuario_list.get(0).productos_info.get(x).getValor().replace(".00",""))));
                text_valores.setTextColor(Color.WHITE);
                text_valores.setTextSize(18);
                text_valores.setLeft(20);

                text_valores.setTypeface(null, Typeface.BOLD);
                fila_valores.addView(text_valores, ViewGroup.MarginLayoutParams.WRAP_CONTENT,ViewGroup.MarginLayoutParams.WRAP_CONTENT);
                tbl_valores_local.addView(fila_valores);
                total += Long.parseLong(interfacesModelo.cartera_usuario_list.get(0).productos_info.get(x).getValor().replace(".00",""));

            }

            if (interfacesModelo.cartera_usuario_list.get(0).getDeuda_inicial().length() > 0) {
                text_deuda_inicial_wallet.setText(format_text.integerFormat(Integer.parseInt(interfacesModelo.cartera_usuario_list.get(0).getDeuda_inicial().replace(".00", ""))));
            } else {
                text_deuda_inicial_wallet.setText(format_text.integerFormat(0));
            }
            if (interfacesModelo.cartera_usuario_list.get(0).getDeuda_total().length() > 0) {
                text_deuda_total_wallet.setText(format_text.integerFormat(Integer.parseInt(interfacesModelo.cartera_usuario_list.get(0).getDeuda_total().replace(".00", ""))));
                if(!interfacesModelo.cartera_usuario_list.get(0).getDeuda_total().equals("0")){
                    text_deuda_total_wallet.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            } else {
                text_deuda_total_wallet.setText(format_text.integerFormat(0));
            }
            if (interfacesModelo.cartera_usuario_list.get(0).getAbono().length() > 0) {
                text_abono_wallet.setText(format_text.integerFormat(Integer.parseInt(interfacesModelo.cartera_usuario_list.get(0).getAbono().replace(".00", ""))));
            } else {
                text_abono_wallet.setText(format_text.integerFormat(0));
            }

            if (interfacesModelo.cartera_usuario_list.get(0).getDeuda_final().length() > 0) {
                text_deuda_final_wallet.setText(format_text.integerFormat(Integer.parseInt(interfacesModelo.cartera_usuario_list.get(0).getDeuda_final().replace(".00", ""))));
                if(!interfacesModelo.cartera_usuario_list.get(0).getDeuda_final().equals("0")){
                    text_deuda_final_wallet.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            } else {
                text_deuda_final_wallet.setText(format_text.integerFormat(0));
            }
            text_total_price_wallet.setText(format_text.longFormat(total));



            /*if (interfacesModelo.cartera_usuario_list.get(0).getRecargas().length() > 0) {
                text_recargas_price_wallet.setText(format_text.integerFormat(Integer.parseInt(interfacesModelo.cartera_usuario_list.get(0).getRecargas().replace(".00", ""))));
                total += Long.parseLong(interfacesModelo.cartera_usuario_list.get(0).getRecargas().replace(".00", ""));
            } else {
                text_recargas_price_wallet.setText(format_text.integerFormat(0));
                text_list_recarga_wallet.setVisibility(View.GONE);
            }
            if (interfacesModelo.cartera_usuario_list.get(0).getWplay().length() > 0) {
                text_wplay_price_wallet.setText(format_text.integerFormat(Integer.parseInt(interfacesModelo.cartera_usuario_list.get(0).getWplay().replace(".00", ""))));
                total += Long.parseLong(interfacesModelo.cartera_usuario_list.get(0).getWplay().replace(".00", ""));
            } else {
                text_wplay_price_wallet.setText(format_text.integerFormat(0));
                text_wplay_price_wallet.setVisibility(View.GONE);
                text_list_wplay_wallet.setVisibility(View.GONE);
            }
            if (interfacesModelo.cartera_usuario_list.get(0).getSoat().length() > 0) {
                text_soat_price_wallet.setText(format_text.integerFormat(Integer.parseInt(interfacesModelo.cartera_usuario_list.get(0).getSoat().replace(".00", ""))));
                total += Long.parseLong(interfacesModelo.cartera_usuario_list.get(0).getSoat().replace(".00", ""));
            } else {
                text_soat_price_wallet.setText(format_text.integerFormat(0));
                text_soat_price_wallet.setVisibility(View.GONE);
                text_list_soat_wallet.setVisibility(View.GONE);
            }

            text_total_price_wallet.setText(format_text.longFormat(total));

            if (interfacesModelo.cartera_usuario_list.get(0).getDeuda_inicial().length() > 0) {
                text_deuda_inicial_wallet.setText(format_text.integerFormat(Integer.parseInt(interfacesModelo.cartera_usuario_list.get(0).getDeuda_inicial().replace(".00", ""))));
            } else {
                text_deuda_inicial_wallet.setText(format_text.integerFormat(0));
            }
            if (interfacesModelo.cartera_usuario_list.get(0).getDeuda_total().length() > 0) {
                text_deuda_total_wallet.setText(format_text.integerFormat(Integer.parseInt(interfacesModelo.cartera_usuario_list.get(0).getDeuda_total().replace(".00", ""))));
                if(!interfacesModelo.cartera_usuario_list.get(0).getDeuda_total().equals("0")){
                    text_deuda_total_wallet.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            } else {
                text_deuda_total_wallet.setText(format_text.integerFormat(0));
            }
            if (interfacesModelo.cartera_usuario_list.get(0).getAbono().length() > 0) {
                text_abono_wallet.setText(format_text.integerFormat(Integer.parseInt(interfacesModelo.cartera_usuario_list.get(0).getAbono().replace(".00", ""))));
            } else {
                text_abono_wallet.setText(format_text.integerFormat(0));
            }

            if (interfacesModelo.cartera_usuario_list.get(0).getDeuda_final().length() > 0) {
                text_deuda_final_wallet.setText(format_text.integerFormat(Integer.parseInt(interfacesModelo.cartera_usuario_list.get(0).getDeuda_final().replace(".00", ""))));
                if(!interfacesModelo.cartera_usuario_list.get(0).getDeuda_final().equals("0")){
                    text_deuda_final_wallet.setTextColor(getResources().getColor(R.color.colorAccent));
                }
            } else {
                text_deuda_final_wallet.setText(format_text.integerFormat(0));
            }*/



        } catch (Exception e) {
        }

    }

    @Override
    public void onClick(View view) {
        c.getTime();
        DatePickerDialog datePickerDialog;
        switch (view.getId()) {
            case R.id.capFechaInicialWallet:

                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                ano = c.get(Calendar.YEAR);


                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if ((monthOfYear+1) < 10 && dayOfMonth < 10) {
                            cap_fecha_inicial.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                        } else if ((monthOfYear+1) < 10) {
                            cap_fecha_inicial.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                        } else if (dayOfMonth < 10) {
                            cap_fecha_inicial.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                        } else {
                            cap_fecha_inicial.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }
                }
                        , ano, mes, dia);
                datePickerDialog.updateDate(ano, mes, dia);
                datePickerDialog.show();
                break;
            case R.id.capFechaFinalWallet:
                dia = c.get(Calendar.DAY_OF_MONTH);
                mes = c.get(Calendar.MONTH);
                ano = c.get(Calendar.YEAR);


                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if ((monthOfYear+1) < 10 && dayOfMonth < 10) {
                            cap_fecha_final.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                        } else if ((monthOfYear+1) < 10) {
                            cap_fecha_final.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                        } else if (dayOfMonth < 10) {
                            cap_fecha_final.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                        } else {
                            cap_fecha_final.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }
                }
                        , ano, mes, dia);
                datePickerDialog.updateDate(ano, mes, dia);
                datePickerDialog.show();
                break;
            case R.id.buttonBuscarReportWallet:
                if (cap_fecha_inicial.length() == 0 || cap_fecha_final.length() == 0) {
                    utils_class.msjsSimple(context, "INFORMACION", "Por favor digitar todos los campos", "Aceptar", "", 0,1);
                } else {
                   /* if(interfacesModelo.data_user.getId_producto_trans_report() == null || interfacesModelo.data_user.getId_producto_trans_report().length() == 0 ){
                        utils_class.msjsSimple(context,"INFORMACION","Por favor seleccione un producto","Aceptar","",0);
                        return;
                    }*/
                    if (!validarFechas_buy())
                        return;
                    progressDialog.setMessage("Consultando...");
                    progressDialog.show();
                    interfacesModelo.data_user.setFecha_inicio_report_trans(cap_fecha_inicial.getText().toString());
                    interfacesModelo.data_user.setFecha_final_report_trans(cap_fecha_final.getText().toString());

                    new ConsumeServicesExpress().consume_api(8, new interfaceOnResponse() {
                        @Override
                        public void finish_consume_services() {
                            finish_process_wallet();
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
                            return;
                        }

                    },context);

                }
                break;
        }
    }

    boolean validarFechas_buy() {
        String[] fechaInicial_split = cap_fecha_inicial.getText().toString().split("-");
        String[] fechaFinal_split = cap_fecha_inicial.getText().toString().split("-");
        String fechaInicial = cap_fecha_inicial.getText().toString().replace("-", "");
        String fechaFinal = cap_fecha_final.getText().toString().replace("-", "");


        if (Integer.parseInt(fechaInicial) > Integer.parseInt(fechaFinal)) {
            utils_class.msjsSimple(context, "INFORMACION-ERROR", "Fecha inicial es mayor a la fecha final.", "Aceptar", "", 0,1);
        } else if (Integer.parseInt(fechaFinal) < Integer.parseInt(fechaInicial)) {
            utils_class.msjsSimple(context, "INFORMACION-ERROR", "Fecha final es menor a la fecha inicial.", "Aceptar", "", 0,1);
        } else if (Integer.parseInt(fechaInicial_split[0]) > ano) {
            utils_class.msjsSimple(context, "INFORMACION-ERROR", "Fecha inicial es mayor al año actual.", "Aceptar", "", 0,1);
        } else if (Integer.parseInt(fechaInicial_split[0]) < ano) {
            utils_class.msjsSimple(context, "INFORMACION-ERROR", "Fecha inicial es menor al año actual.", "Aceptar", "", 0,1);
        } else if (Integer.parseInt(fechaFinal_split[0]) > ano) {
            utils_class.msjsSimple(context, "INFORMACION-ERROR", "Fecha final es mayor al año actual.", "Aceptar", "", 0,1);
        } else if (Integer.parseInt(fechaFinal_split[0]) < ano) {
            utils_class.msjsSimple(context, "INFORMACION-ERROR", "Fecha final es menor al año actual.", "Aceptar", "", 0,1);
        } else if ((Integer.parseInt(fechaFinal_split[1]) - Integer.parseInt(fechaInicial_split[1])) >= 1) {
            utils_class.msjsSimple(context, "INFORMACION-ERROR", "El reporte solo se puede generar sobre los ultimos 15 dias.", "Aceptar", "", 0,1);
        } else if ((Integer.parseInt(fechaFinal_split[1]) - Integer.parseInt(fechaInicial_split[1])) == 0) {
            if ((Integer.parseInt(fechaFinal_split[2]) - Integer.parseInt(fechaInicial_split[2])) > 15) {
                utils_class.msjsSimple(context, "INFORMACION-ERROR", "El reporte solo se puede generar sobre los ultimos 15 dias.", "Aceptar", "", 0,1);
            } else {
                return true;
            }
        } else {
            return true;
        }

        return false;
    }

    public void finish_process_wallet() {

        progressDialog.dismiss();
        msj = validate_user.data_reporte_cartera(context);

        if (msj.equals("404")) {
            utils_class.msjsSimple(context, "INFORMACION", "No existen informacion", "Aceptar", "", 0,1);
            if(relative_ly_one_wallet.getVisibility() == View.VISIBLE){
                relative_ly_one_wallet.setVisibility(View.GONE);
            }
        } else if (json_object_response.getJson_object_response().get("error").getAsString().equals("true")) {
            utils_class.msjsSimple(context, "INFORMACION", msj, "Aceptar", "", 0,1);
            if(relative_ly_one_wallet.getVisibility() == View.VISIBLE){
                relative_ly_one_wallet.setVisibility(View.GONE);
            }
        } else {
            llenarListTrans();
            relative_ly_one_wallet.setVisibility(View.VISIBLE);
        }
    }



}
