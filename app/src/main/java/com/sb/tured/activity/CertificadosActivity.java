package com.sb.tured.activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.sb.tured.R;
import com.sb.tured.controller.ValidateJson;
import com.sb.tured.interfaces.interfaceOnResponse;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.io.ConsumeServicesExpress;
import com.sb.tured.model.BalanceProductos;
import com.sb.tured.model.CertificadosSNR;
import com.sb.tured.model.ProductInfo;
import com.sb.tured.model.ServOperPackRecarga;
import com.sb.tured.model.Usuario;
import com.sb.tured.model.ValueDefaultOperRecarga;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterListConsultSaldo;
import com.sb.tured.utilities.formatText;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CertificadosActivity extends AppCompatActivity implements View.OnClickListener, interfacesModelo {

    private List oficinas_certificados_snr_list;
    private List oficinas_certificados_snr_ciudades_list;
    private List value_default_oper_recarga;
    private utilitiesTuRedBD utils_bd;
    private formatText format_text;
    private ValueDefaultOperRecarga obj_aux;
    private ArrayList<CertificadosSNR> oficinas_certificados_snr_arrayList;
    private BalanceProductos balance_productos_usuario;
    private ServOperPackRecarga serv_value_default_oper_recarga;
    private ImageView button_back_activity;
    private ImageView button_info;
    private ImageView icono_rol_usuario;
    private ImageView logo_certificados_snr;
    private TextView tittle_bar;
    private TextView text_info_saldo;
    private TextView text_info_certificados;
    private EditText cap_codigo_ciudad;
    private EditText cap_matricula;
    private EditText cap_celular_envio_codigo;
    private EditText cap_letra_ciudad;
    private EditText cap_correo_electronico;
    private String url_imagen;
    private String id_producto;
    private String operador;
    private String servicio;
    private String msj_recarga;
    private String url_msj;
    private Button aceptar_certificados;
    private Button cancelar_certificados;
    private Spinner spinner_oficinas_certificados_snr;
    private ProgressDialog progressDialog;
    private ValidateJson validate_user;
    private utilitiesClass utils_class;
    private ArrayAdapter<String> comboAdapter;
    private LinearLayout linear_layout_info_certificados;
    private CheckBox chbox_button_bolsa;
    private boolean flag_venta_certificados;
    private boolean flag_vendido_certificados = false;
    private String msj = "";
    private String nombre_categoria;
    private Usuario usuario_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificados);
        url_imagen = getIntent().getExtras().getString("url_imagen");
        id_producto = getIntent().getExtras().getString("id_producto");
        operador = getIntent().getExtras().getString("operador");
        servicio = getIntent().getExtras().getString("servicio");
        nombre_categoria = getIntent().getExtras().getString("nombre_categoria");
        initComponents();
        llenarLayout(0);


        actionevents();


        cap_letra_ciudad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                comboAdapter.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        cap_codigo_ciudad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void initComponents() {

        oficinas_certificados_snr_arrayList = new ArrayList<>();
        button_back_activity = findViewById(R.id.buttonBack);
        logo_certificados_snr = findViewById(R.id.logoCertificadosSNR);
        progressDialog = new ProgressDialog(this);
        validate_user = new ValidateJson();
        utils_class = new utilitiesClass();
        balance_productos_usuario = new BalanceProductos();
        tittle_bar = findViewById(R.id.tittle_toolbar);
        text_info_saldo = findViewById(R.id.textInfoSaldo);
        button_info = findViewById(R.id.buttonInfo);
        icono_rol_usuario = findViewById(R.id.iconoUsuarioRol);
        format_text = new formatText();
        spinner_oficinas_certificados_snr = findViewById(R.id.spOficinasCertificadosSNR);
        cap_letra_ciudad = findViewById(R.id.capLetrasCiudad);
        aceptar_certificados = findViewById(R.id.buttonAceptarCertificados);
        cancelar_certificados = findViewById(R.id.buttonCancelCertificados);
        cap_correo_electronico = findViewById(R.id.capCorreoEnvioCodigo);
        cap_codigo_ciudad = findViewById(R.id.capCodigoCiudad);
        cap_matricula = findViewById(R.id.capCodigoMatricula);
        cap_celular_envio_codigo = findViewById(R.id.capCelularEnvioCodigo);
        text_info_certificados = findViewById(R.id.textInfoCertificadosSNR);
        linear_layout_info_certificados = findViewById(R.id.body_four_child_certificados);
        chbox_button_bolsa = findViewById(R.id.chBoxOpcOneCertificados);
        flag_venta_certificados = false;
        usuario_info = new Usuario();

    }

    void llenarLayout(int opc) {

        Picasso.get()
                .load("https://multiservicios.madefor.work/assets/img/pdv.png")
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(icono_rol_usuario);
        //icono_rol_usuario = interfacesModelo.carga_imagenes.img.get(0);

        Picasso.get()
                .load(url_imagen)
                .transform(new RoundedTransformation(150, 1))
                .resize(265, 265)
                .centerCrop()
                .into(logo_certificados_snr);

        text_info_certificados.setText("");
        linear_layout_info_certificados.setVisibility(View.GONE);
        text_info_certificados.setVisibility(View.GONE);
        utils_bd = new utilitiesTuRedBD(this);
        tittle_bar.setText("Certificados");
        button_info.setVisibility(View.INVISIBLE);


        try {
            balance_productos_usuario = utils_bd.getProductoBalance("RECARGAS");
            text_info_saldo.setText("Saldo: " + format_text.stringFormat(balance_productos_usuario.getSaldo()));
            serv_value_default_oper_recarga = utils_bd.getServPackOperRecarga(operador, servicio);
            value_default_oper_recarga = utils_bd.getAllValueDefaultOperRecarga(serv_value_default_oper_recarga.getId(), serv_value_default_oper_recarga.getTabla());
            if (value_default_oper_recarga.size() == 0) {
                new AlertDialog.Builder(CertificadosActivity.this)
                        .setTitle("INFORMACION")
                        .setMessage("No existen valores para la compra de certificados.")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @TargetApi(11)
                                    public void onClick(DialogInterface dialog, int id) {
                                        nextActivity(2);

                                    }

                                }
                        )
                        .show();
            }
            obj_aux = (ValueDefaultOperRecarga) value_default_oper_recarga.get(0);
            oficinas_certificados_snr_list = utils_bd.getAllOficinasCertificadosSNR();
            if (oficinas_certificados_snr_list.size() == 0) {
                new AlertDialog.Builder(CertificadosActivity.this)
                        .setTitle("INFORMACION")
                        .setMessage("No existen oficinas")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @TargetApi(11)
                                    public void onClick(DialogInterface dialog, int id) {
                                        nextActivity(2);

                                    }

                                }
                        )
                        .show();

            }
            oficinas_certificados_snr_ciudades_list = utils_bd.getAllOficinasCertificadosSNR();
            oficinas_certificados_snr_ciudades_list.removeAll(oficinas_certificados_snr_ciudades_list);
            oficinas_certificados_snr_ciudades_list.add("Seleccione una ciudad");
            for (int x = 0; x < oficinas_certificados_snr_list.size(); x++) {
                oficinas_certificados_snr_arrayList.add((CertificadosSNR) oficinas_certificados_snr_list.get(x));
                oficinas_certificados_snr_ciudades_list.add(oficinas_certificados_snr_arrayList.get(x).getCiudad() + "-" + oficinas_certificados_snr_arrayList.get(x).getCodigo());
            }
            comboAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, oficinas_certificados_snr_ciudades_list);
            spinner_oficinas_certificados_snr.setAdapter(comboAdapter);
        } catch (Exception ex) {

        }


    }


    @Override
    public void onClick(View v) {
        if (v.getId() == button_back_activity.getId()) {
            nextActivity(2);
        } else if (v.getId() == aceptar_certificados.getId()) {

        } else if (v.getId() == cancelar_certificados.getId()) {
            nextActivity(2);
        }
    }

    public void nextActivity(int opc) {
        if (opc == 1) {
            Intent intento = new Intent(this, MenuPrincActivity.class); // Lanzamos SiguienteActivity
            if (flag_vendido_certificados)
                intento.putExtra("mensaje_saldo", "CERTIFICADOS-" + url_msj);
            else
                intento.putExtra("mensaje_saldo", msj_recarga);
            startActivity(intento);
            this.finish();
            CertificadosActivity.this.finish();
        } else if (opc == 2) {
            Intent intento = new Intent(this, MenuProductosActivity.class); // Lanzamos SiguienteActivity
            startActivity(intento);
            this.finish();
            /*Intent intento = new Intent(this, ProductosCategoriasActivity.class); // Lanzamos SiguienteActivity
            intento.putExtra("nombre_categoria", nombre_categoria);
            startActivity(intento);
            this.finish();*/
        }
    }


    public void finish_process() {

        progressDialog.dismiss();
        msj = validate_user.data_getcertificadosnr(this);
        try {
            if (!flag_venta_certificados) {
                if (json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
                    if (text_info_certificados.getVisibility() == View.GONE) {
                        linear_layout_info_certificados.setVisibility(View.VISIBLE);
                        text_info_certificados.setVisibility(View.VISIBLE);
                    }
                    text_info_certificados.setText(msj);
                    aceptar_certificados.setText(getText(R.string.text_boton_comprar_certificado_certificado_snr));
                    aceptar_certificados.setBackgroundColor(getResources().getColor(R.color.verde54b45c));
                    flag_venta_certificados = true;

                } else {
                    utils_class.msjsSimple(this, "INFORMACION", msj, "Aceptar", "", 0, 1);
                }
            } else {
                flag_venta_certificados = false;
                if (json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
                    msj_recarga = msj.substring(0, msj.indexOf(":"));
                    url_msj = msj.substring(msj.indexOf("=") + 2, msj.lastIndexOf("\""));
                    flag_vendido_certificados = true;
                    nextActivity(1);
                    //msjDialogoWeb();
                } else {
                    msj_recarga = msj;
                    nextActivity(1);
                }

            }
        } catch (Exception e) {

            msj_recarga = "Error 506 en Certificados SNR";
            nextActivity(1);
        }

    }

    @Override
    public void onBackPressed() {
        nextActivity(2);
    }


    private void actionevents() {

        spinner_oficinas_certificados_snr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                if (adapterView.getItemAtPosition(pos) != null && !adapterView.getItemAtPosition(pos).toString().equals("Seleccione una ciudad")) {
                    cap_codigo_ciudad.setText(oficinas_certificados_snr_arrayList.get(pos - 1).getCodigo());
                } else {
                    if (cap_codigo_ciudad.getText().length() > 0) {
                        cap_codigo_ciudad.setText("");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        aceptar_certificados.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!flag_venta_certificados)
                        consultarMatricula();
                    else {
                        if (cap_celular_envio_codigo.getText().length() == 0 || cap_correo_electronico.getText().length() == 0) {
                            utils_class.msjsSimple(CertificadosActivity.this, "INFORMACION", "Por favor llenar todos los campos", "Aceptar", "", 0, 1);
                            //cap_celular_envio_codigo.setHintTextColor(getResources().getColor(R.color.verde54b45c));
                        } else {
                            comprarCertificado();
                        }
                    }
                }
                return false;
            }
        });

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

    private void consultarMatricula() {
        progressDialog.setMessage("Consultando...");
        progressDialog.show();

        oficinas_certificados_snr.setCirculoregistral(cap_codigo_ciudad.getText().toString());
        oficinas_certificados_snr.setMatricula(cap_matricula.getText().toString());
        new ConsumeServicesExpress().consume_api(13, new interfaceOnResponse() {
            @Override
            public void finish_consume_services() {
                finish_process();
            }

            @Override
            public void solicit_token_error_services(String mensaje_token) {
                progressDialog.dismiss();
                //json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
                //utils_class.msjs_opciones_simple(CertificadosActivity.this, "INFORMACION", json_object_response.getJson_response_data().get("message").getAsString(), "Aceptar", "");
                Intent intento = new Intent(CertificadosActivity.this, LoginActivity.class);
                intento.putExtra("mensaje_inicio", mensaje_token);
                startActivity(intento);
                CertificadosActivity.this.finish();
            }

            @Override
            public void finish_fail_consume_services(int codigo) {
                progressDialog.dismiss();
                if(codigo == 999){
                    utils_class.msjsSimple(CertificadosActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo INTERNET " + codigo, "Aceptar", "", 0, 1);
                }else {
                    utils_class.msjsSimple(CertificadosActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo " + codigo, "Aceptar", "", 0, 1);
                }
                return;
            }
        },this);

    }

    private void comprarCertificado() {
        progressDialog.setMessage("Comprando Certificiado...");
        progressDialog.show();


        recarga_servicio.setPosx("0");
        recarga_servicio.setPosy("0");
        recarga_servicio.setProducto(id_producto);//falta
        recarga_servicio.setReferencia(cap_codigo_ciudad.getText().toString() + "|" + cap_matricula.getText().toString() + "|" + cap_codigo_ciudad.getText().toString() + "|" + cap_celular_envio_codigo.getText().toString() + "|" + cap_correo_electronico.getText().toString());
        recarga_servicio.setMonto(obj_aux.getValor().replace(".00", ""));
        recarga_servicio.setIdmonto(obj_aux.getId());//falta
        recarga_servicio.setServicio_id(serv_value_default_oper_recarga.getId());//falta
        recarga_servicio.setId_recargas(id_producto);//falta
        if (chbox_button_bolsa.isChecked()) {
            recarga_servicio.setBolsa_venta("ganancias");
        } else {
            recarga_servicio.setBolsa_venta("");
        }

        usuario_info = utils_class.obtenerInfoUsuario(this);
        if(usuario_info == null){
            utils_class.msjsSimple(this, "Información", "No es posible obtener informacion del token", "Aceptar", "", 0, 1);

            return;
        }

        recarga_servicio.setToken( usuario_info.getToken());
        //recarga_servicio.setToken(data_user.getToken());
        //recarga_servicio.setToken( ((Usuario) new utilitiesTuRedBD(this).getUsuarioInfo(data_user.getEmail())).getToken());

        new ConsumeServicesExpress().consume_api(4, new interfaceOnResponse() {
            @Override
            public void finish_consume_services() {
                finish_process();
            }

            @Override
            public void solicit_token_error_services(String mensaje_token) {
                progressDialog.dismiss();
                //json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
                //utils_class.msjs_opciones_simple(CertificadosActivity.this, "INFORMACION", json_object_response.getJson_response_data().get("message").getAsString(), "Aceptar", "");
                Intent intento = new Intent(CertificadosActivity.this, LoginActivity.class);
                intento.putExtra("mensaje_inicio", mensaje_token);
                startActivity(intento);
                CertificadosActivity.this.finish();
            }

            @Override
            public void finish_fail_consume_services(int codigo) {
                progressDialog.dismiss();
                if(codigo == 999){
                    utils_class.msjsSimple(CertificadosActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo INTERNET " + codigo, "Aceptar", "", 0, 1);
                }else {
                    utils_class.msjsSimple(CertificadosActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo " + codigo, "Aceptar", "", 0, 1);
                }
                return;
            }
        },this);
    }

    private void msjDialogoWeb() {

        // WebView is created programatically here.
        WebView myWebView = new WebView(CertificadosActivity.this);
        myWebView.loadUrl(url_msj);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        new AlertDialog.Builder(CertificadosActivity.this).setView(myWebView)
                .setTitle("TRANSACCION EXITOSA")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                msj_recarga = "00";
                                nextActivity(1);

                            }

                        }
                )
                .setNeutralButton("Descargar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Uri uri = Uri.parse(url_msj);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                })
                .show();


    }
}
