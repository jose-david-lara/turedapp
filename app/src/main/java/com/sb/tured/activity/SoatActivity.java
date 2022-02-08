package com.sb.tured.activity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.gson.JsonObject;
import com.sb.tured.R;
import com.sb.tured.controller.ValidateJson;
import com.sb.tured.interfaces.interfaceOnResponse;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.io.ConsumeServicesExpress;
import com.sb.tured.model.CategoriasProductos;
import com.sb.tured.model.Soat;
import com.sb.tured.model.Usuario;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;
import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_CLASS_TEXT;


public class SoatActivity extends AppCompatActivity implements View.OnClickListener, interfacesModelo
{



    private Soat.publico soat_publico;
    private int estado_soat;
    private TextView text_tittle;
    private TextView text_consulta;
    private TextView text_consulta_varias_placas;
    private EditText cap_placa;
    private ImageView back_button;
    private ImageView button_info;
    private ImageView icono_rol_usuario;
    private Button button_consul_placa;
    private ProgressDialog progressDialog;
    private utilitiesClass utils_class = new utilitiesClass();
    private ValidateJson validate_user;
    private String msj = "";
    private String operador;
    private String id;
    private String servicio;
    private String producto_id;
    private Soat.formulario formulario_local =  new Soat.formulario();
    private LinearLayout consulta_layout;
    private LinearLayout contenedor_consulta ;
    private LinearLayout contenedor_consulta_view ;
    private TextView mensaje_publico;
    private RadioGroup opcion_pago_publico;
    private CheckBox checkbox_bolsa_venta_publico;
    private CheckBox checkbox_three_publico;
    private CheckBox checkbox_four_publico;
    private Button button_aceptar_soat;
    private Button button_cancelar_soat;
    private LinearLayout compra_soat_button_layout;
    private ArrayAdapter<String> componentAdapter;
    private String nombre_categoria;
    private Usuario usuario_info;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soat);
        operador = getIntent().getExtras().getString("operador");
        id = getIntent().getExtras().getString("id");
        servicio = getIntent().getExtras().getString("servicio");
        producto_id = getIntent().getExtras().getString("producto_id");
        nombre_categoria = getIntent().getExtras().getString("nombre_categoria");

        initComponents();



        checkbox_three_publico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    consumo_servicio_soat.setTipo_publico("URBANO");
                    radioGroupServices();
                }

            }
        });

        checkbox_four_publico.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    consumo_servicio_soat.setTipo_publico("OPER_NACIONAL");
                    radioGroupServices();
                }

            }
        });

        Picasso.get()
                .load("https://multiservicios.madefor.work/assets/img/pdv.png")
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(icono_rol_usuario);



    }

    @SuppressLint("WrongViewCast")
    private void initComponents() {
        try {

            estado_soat = 0;
            soat_publico = new Soat.publico();
            back_button = findViewById(R.id.buttonBack);
            button_info = findViewById(R.id.buttonInfo);
            button_info.setVisibility(View.GONE);


            icono_rol_usuario = findViewById(R.id.iconoUsuarioRol);

            //Titulo de tool bar
            text_tittle = findViewById(R.id.tittle_toolbar);
            text_tittle.setText("SOAT");


            progressDialog = new ProgressDialog(this);
            validate_user = new ValidateJson();

            button_consul_placa = findViewById(R.id.buttonConsPlaca);
            cap_placa = findViewById(R.id.capPlaca);
            text_consulta =  findViewById(R.id.textConsulta);
            text_consulta_varias_placas =  findViewById(R.id.textConsultaVariasPlacas);
            text_consulta.setVisibility(View.GONE);
            text_consulta_varias_placas.setVisibility(View.GONE);

            button_aceptar_soat = findViewById(R.id.buttonAceptarSoat);
            button_cancelar_soat= findViewById(R.id.buttonCancelarSoat);
            checkbox_bolsa_venta_publico = findViewById(R.id.chBoxOpcOneSoat);
            compra_soat_button_layout = findViewById(R.id.layoutCompraButtonSoat);
            compra_soat_button_layout.setVisibility(View.GONE);


            checkbox_three_publico = findViewById(R.id.chBoxOpcThreeSoat);
            checkbox_three_publico.setVisibility(View.GONE);

            checkbox_four_publico = findViewById(R.id.chBoxOpcFourSoat);
            checkbox_four_publico.setVisibility(View.GONE);

            opcion_pago_publico = findViewById(R.id.opcionSoatPublico);
            opcion_pago_publico.setVisibility(View.GONE);


            consulta_layout = findViewById(R.id.layoutConsulta);
            consulta_layout.setVisibility(View.GONE);

            mensaje_publico = findViewById(R.id.textSoatRespServicioPublico);
            mensaje_publico.setVisibility(View.GONE);
            usuario_info = new Usuario();



        } catch (Exception e) {

        }


    }


    @Override
    public void onClick(View v) {

        System.out.println("entre en click");

        if (v.getId() == button_consul_placa.getId()) {
            if(getCurrentFocus()!=null) {
                hideSoftKeyboard(SoatActivity.this);
            }
            if (cap_placa.getText().length() == 0 ) {
                utils_class.msjsSimple(SoatActivity.this, "INFORMCAION", "Por favor ingresar la placa", "Aceptar", "", 0,1);
            } else {
                if(estado_soat != 0) {
                    mensaje_publico.setVisibility(View.GONE);
                    opcion_pago_publico.setVisibility(View.GONE);
                    checkbox_three_publico.setVisibility(View.GONE);
                    checkbox_four_publico.setVisibility(View.GONE);
                    estado_soat = 0;
                    consulta_layout.removeAllViews();
                    compra_soat_button_layout.setVisibility(View.GONE);
                    if(checkbox_bolsa_venta_publico.isChecked()){
                        checkbox_bolsa_venta_publico.setChecked(false);
                    }
                    checkbox_three_publico.setChecked(false);
                    checkbox_four_publico.setChecked(false);
                }
                interfacesModelo.soat.setPlaca(cap_placa.getText().toString());
                interfacesModelo.soat.setOperador(operador);
                progressDialog.setMessage("Consultando...");
                progressDialog.show();
                new ConsumeServicesExpress().consume_api(14, new interfaceOnResponse() {
                    @Override
                    public void finish_consume_services() {
                        finish_process();
                    }

                    @Override
                    public void solicit_token_error_services(String mensaje_token) {
                        progressDialog.dismiss();
                        Intent intento = new Intent(SoatActivity.this, LoginActivity.class);
                        intento.putExtra("mensaje_inicio", mensaje_token);
                        startActivity(intento);
                        SoatActivity.this.finish();
                    }

                    @Override
                    public void finish_fail_consume_services(int codigo) {
                        progressDialog.dismiss();
                        if(codigo == 999){
                            utils_class.msjsSimple(SoatActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo INTERNET " + codigo, "Aceptar", "", 0, 1);
                        }else {
                            utils_class.msjsSimple(SoatActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo " + codigo, "Aceptar", "", 0, 1);
                        }
                        return;
                    }
                }, this);
            }

        }else if (v.getId() == button_aceptar_soat.getId() && estado_soat == 1){


            if (cap_placa.getText().length() == 0  ||
                    searchIdComponent("Documento").length() == 0 || searchIdComponent("Nombre").length() == 0 || searchIdComponent("Apellido").length() == 0 ||
                    searchIdComponent("Dirección").length() == 0 || searchIdComponent("Celular").length() == 0 || searchIdComponent("Email").length() == 0 ||
                    searchIdComponent("Ciudad").length() == 0) {
                utils_class.msjsSimple(SoatActivity.this, "INFORMCAION", "Por favor llenar todos los campos", "Aceptar", "", 0,1);
            } else {


                interfacesModelo.soat.setOperador(operador);
                interfacesModelo.soat.setPlaca(cap_placa.getText().toString());
                consumo_servicio_soat.setTipo_documento(searchIdComponent("Tipo Documento"));
                consumo_servicio_soat.setDocumento(searchIdComponent("Documento"));
                consumo_servicio_soat.setNombre(searchIdComponent("Nombre"));
                consumo_servicio_soat.setApellido(searchIdComponent("Apellido"));
                consumo_servicio_soat.setId_ciudad(searchIdComponent("Ciudad"));
                if( consumo_servicio_soat.getId_ciudad().length() == 0 ||  consumo_servicio_soat.getId_ciudad().equals("")){
                    utils_class.msjsSimple(SoatActivity.this, "INFORMCAION", "Ciudad no pertene a la lista. Por favor seleccione una ciudad valida.", "Aceptar", "", 0,1);
                    return;
                }
                consumo_servicio_soat.setDireccion(searchIdComponent("Dirección"));
                consumo_servicio_soat.setCelular(searchIdComponent("Celular"));
                consumo_servicio_soat.setEmail(searchIdComponent("Email"));

                progressDialog.setMessage("Pre-Compra Soat...");
                progressDialog.show();
                new ConsumeServicesExpress().consume_api(16, new interfaceOnResponse() {
                    @Override
                    public void finish_consume_services() {

                        finish_process();
                    }

                    @Override
                    public void solicit_token_error_services(String mensaje_token) {
                        progressDialog.dismiss();
                        Intent intento = new Intent(SoatActivity.this, LoginActivity.class);
                        intento.putExtra("mensaje_inicio", mensaje_token);
                        startActivity(intento);
                        SoatActivity.this.finish();
                    }

                    @Override
                    public void finish_fail_consume_services(int codigo) {
                        progressDialog.dismiss();
                        if(codigo == 999){
                            utils_class.msjsSimple(SoatActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo INTERNET " + codigo, "Aceptar", "", 0, 1);
                        }else {
                            utils_class.msjsSimple(SoatActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo " + codigo, "Aceptar", "", 0, 1);
                        }
                        return;
                    }
                }, this);
            }
        }if (v.getId() == button_aceptar_soat.getId() && estado_soat == 2) {


            recarga_servicio.setPosx("0");
            recarga_servicio.setPosy("0");
            recarga_servicio.setProducto(producto_id);//falta
            recarga_servicio.setMonto("");
            recarga_servicio.setIdmonto("24");
            recarga_servicio.setServicio_id(id);
            recarga_servicio.setId_recargas(id);
            usuario_info = utils_class.obtenerInfoUsuario(this);
            if(usuario_info == null){
                utils_class.msjsSimple(this, "Información", "No es posible obtener informacion del token.", "Aceptar", "", 0, 1);
                estado_soat = 0;
                onBackPressed();
                return;
            }

            recarga_servicio.setToken( usuario_info.getToken());
            //recarga_servicio.setToken( ((Usuario) new utilitiesTuRedBD(this).getUsuarioInfo(data_user.getEmail())).getToken());
            if(checkbox_bolsa_venta_publico.isChecked()){
                recarga_servicio.setBolsa_venta("ganancias");
            }else{
                recarga_servicio.setBolsa_venta("");
            }

            interfacesModelo.soat.setPlaca(cap_placa.getText().toString());
            recarga_servicio.setReferencia(cap_placa.getText().toString());

            interfacesModelo.soat.setOperador(operador);

            progressDialog.setMessage("Comprando Soat...");
            progressDialog.show();


            new ConsumeServicesExpress().consume_api(17, new interfaceOnResponse() {
                @Override
                public void finish_consume_services() {

                    finish_process();
                }

                @Override
                public void solicit_token_error_services(String mensaje_token) {
                    progressDialog.dismiss();
                    Intent intento = new Intent(SoatActivity.this, LoginActivity.class);
                    intento.putExtra("mensaje_inicio", mensaje_token);
                    startActivity(intento);
                    SoatActivity.this.finish();
                }

                @Override
                public void finish_fail_consume_services(int codigo) {
                    progressDialog.dismiss();
                    if(codigo == 999){
                        utils_class.msjsSimple(SoatActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo INTERNET " + codigo, "Aceptar", "", 0, 1);
                    }else {
                        utils_class.msjsSimple(SoatActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo " + codigo, "Aceptar", "", 0, 1);
                    }
                    return;
                }
            }, this);


        }else if (v.getId() == button_cancelar_soat.getId()){

            initComponents();
            consulta_layout.removeAllViews();
            compra_soat_button_layout.setVisibility(View.GONE);
            if(checkbox_bolsa_venta_publico.isChecked()){
                checkbox_bolsa_venta_publico.setChecked(false);
            }
            checkbox_three_publico.setChecked(false);
            checkbox_four_publico.setChecked(false);
        }else if (v.getId() == back_button.getId()){
            onBackPressed();
        }
    }

    private boolean finish_process() {

        progressDialog.dismiss();
        msj = validate_user.message_consulta().replace("\"","");
        try {
            if (json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {


                if (estado_soat == 0 || estado_soat == -1 || estado_soat == 1) {
                    text_consulta.setText(msj);
                    if(!json_object_response.getJson_response_data().get("alerta").isJsonNull())
                        text_consulta_varias_placas.setText(json_object_response.getJson_response_data().get("alerta").getAsString());
                    else
                        text_consulta_varias_placas.setVisibility(View.GONE);
                    if (estado_soat == 0)
                        soat_publico = validate_user.publico_soat();
                    else {
                        if(!json_object_response.getJson_response_data().get("publico").isJsonNull())
                            soat_publico.setPublico(Integer.toString(json_object_response.getJson_response_data().get("publico").getAsInt()));
                    }

                    if (soat_publico != null && estado_soat == 0) {
                        mensaje_publico.setVisibility(View.VISIBLE);
                        opcion_pago_publico.setVisibility(View.VISIBLE);
                        checkbox_three_publico.setVisibility(View.VISIBLE);
                        checkbox_four_publico.setVisibility(View.VISIBLE);
                        compra_soat_button_layout.setVisibility(View.GONE);


                        checkbox_three_publico.setText(soat_publico.getOpcion_1());
                        checkbox_four_publico.setText(soat_publico.getOpcion_2());

                        mensaje_publico.setText(soat_publico.getAlerta());

                        estado_soat = -1;

                    } else {

                        if(estado_soat == 1)
                            consulta_layout.removeAllViews();


                        mensaje_publico.setVisibility(View.GONE);
                        opcion_pago_publico.setVisibility(View.GONE);
                        checkbox_three_publico.setVisibility(View.GONE);
                        checkbox_four_publico.setVisibility(View.GONE);


                        compra_soat_button_layout.setVisibility(View.VISIBLE);
                        button_cancelar_soat.setVisibility(View.VISIBLE);
                        button_cancelar_soat.setVisibility(View.VISIBLE);
                        compra_soat_button_layout.setVisibility(View.VISIBLE);


                        button_aceptar_soat.setText(R.string.text_continue_buy_soat);
                        validate_user.formulario_soat();
                        if (!interfacesModelo.formulario.isEmpty()) {
                            if(estado_soat == 1) {
                                estado_soat = 2;
                                button_aceptar_soat.setText("COMPRAR SOAT");
                            }else
                                estado_soat = 1;//precompra
                            initForm();
                        }
                    }


                }
                else if (estado_soat == 2) {
                    json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));


                    progressDialog.dismiss();
                    Intent intento = new Intent(SoatActivity.this, MenuPrincActivity.class); // Lanzamos SiguienteActivity
                    intento.putExtra("mensaje_saldo","SOAT");
                    startActivity(intento);
                    SoatActivity.this.finish();
                    this.finish();
                }
            } else {
                utils_class.msjsSimple(this, "INFORMACION", msj, "Aceptar", "", 0, 1);
            }
        }catch (Exception ex){
            Log.e("ERROR SOAT",ex.getMessage());
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        /*Intent intento;
        intento = new Intent(this, MenuProductosActivity.class);
        startActivity(intento);
        this.finish();*/
        Intent intento;
        intento = new Intent(this, ProductosCategoriasActivity.class);
        intento.putExtra("nombre_categoria", nombre_categoria);
        startActivity(intento);
        this.finish();

    }

    private void initForm (){

        if(text_consulta_varias_placas.getText().length() > 0)
            text_consulta_varias_placas.setVisibility(View.VISIBLE);
        consulta_layout.setVisibility(View.VISIBLE);

        for(int i = 0; i < interfacesModelo.formulario.size(); i++){

            formulario_local =interfacesModelo.formulario.get(i);
            contenedor_consulta = new LinearLayout(this);
            contenedor_consulta.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

            contenedor_consulta_view = new LinearLayout(this);
            contenedor_consulta_view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

            switch(formulario_local.getT()){
                case "select":
                    if(formulario_local.getN().equals("Tipo Documento")){
                        Spinner component = new Spinner(this);
                        TextView componentText = new TextView(this);
                        View view_space = new View(this);
                        componentAdapter =
                                new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,getResources().getStringArray(R.array.tipoDocumentoSoat));
                        component.setAdapter(componentAdapter);
                        component.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.cuarenta_sp)));
                        component.setId(100+formulario_local.getO());
                        component.setBackgroundDrawable(getResources().getDrawable(R.drawable.spinner_selector));
                        componentText.setText(Html.fromHtml("<b>"+formulario_local.getN()+":  </b>"));
                        componentText.setId(formulario_local.getO());
                        componentText.setTextColor(Color.WHITE);
                        componentText.setTextSize(16);
                        view_space.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,30));
                        contenedor_consulta.addView(componentText);
                        contenedor_consulta.addView(component);
                        contenedor_consulta_view.addView(view_space);
                    }else if(formulario_local.getN().equals("Ciudad")){
                        AutoCompleteTextView component = new AutoCompleteTextView(this);
                        TextView componentText = new TextView(this);
                        View view_space = new View(this);
                        componentAdapter =
                                new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,getResources().getStringArray(R.array.ciudadSoat));
                        component.setThreshold(3);
                        component.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.cuarenta_sp)));
                        component.setAdapter(componentAdapter);
                        component.setSingleLine(true);
                        component.setPadding(20,0,20,0);
                        component.setBackgroundDrawable(getResources().getDrawable(R.drawable.spinner_selector));
                        component.setTextColor(getResources().getColor(R.color.colorInitBack));
                        component.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_rounded));
                        component.setId(100+formulario_local.getO());
                        componentText.setText(Html.fromHtml("<b>"+formulario_local.getN()+":  </b>"));
                        componentText.setId(formulario_local.getO());
                        componentText.setTextColor(Color.WHITE);
                        componentText.setTextSize(16);
                        view_space.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,30));
                        contenedor_consulta.addView(componentText);
                        contenedor_consulta.addView(component);
                        contenedor_consulta_view.addView(view_space);
                    }
                    break;
                case "text":
                    TextView componentText = new TextView(this);
                    if(formulario_local.getEst() == 0) {
                        componentText.setText(Html.fromHtml("<b>"+formulario_local.getN()+":</b>  "+formulario_local.getValor()));
                        componentText.setId(formulario_local.getO());
                        componentText.setTextColor(Color.WHITE);
                        componentText.setTextSize(16);
                        contenedor_consulta.addView(componentText);
                    }else {
                        EditText componentEditText = new EditText(this);
                        View view_space = new View(this);
                        componentText.setText(Html.fromHtml("<b>"+formulario_local.getN()+":  </b>"));
                        componentText.setId(formulario_local.getO());
                        componentText.setTextColor(Color.WHITE);
                        componentText.setTextSize(16);
                        componentEditText.setId(100+formulario_local.getO());
                        componentEditText.setInputType(TYPE_CLASS_TEXT);
                        componentEditText.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.cuarenta_sp)));
                        componentEditText.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_rounded));
                        componentEditText.setPadding(20,0,20,0);
                        view_space.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,30));
                        contenedor_consulta.addView(componentText);
                        contenedor_consulta.addView(componentEditText);
                        contenedor_consulta_view.addView(view_space);
                    }
                    break;
                case "number":
                    TextView componentNumber = new TextView(this);
                    if(formulario_local.getEst() == 0) {
                        componentNumber.setText(Html.fromHtml("<b>"+formulario_local.getN()+":</b>  "+formulario_local.getValor()));
                        componentNumber.setId(formulario_local.getO());
                        componentNumber.setTextColor(Color.WHITE);
                        componentNumber.setTextSize(16);
                        contenedor_consulta.addView(componentNumber);
                    }else {
                        EditText componentEditText = new EditText(this);
                        View view_space = new View(this);
                        componentNumber.setText(Html.fromHtml("<b>"+formulario_local.getN()+":  </b>"));
                        componentNumber.setId(formulario_local.getO());
                        componentNumber.setTextColor(Color.WHITE);
                        componentNumber.setTextSize(16);
                        componentEditText.setPadding(20,0,20,0);
                        componentEditText.setId(100+formulario_local.getO());
                        componentEditText.setInputType(TYPE_CLASS_TEXT | TYPE_CLASS_NUMBER);
                        componentEditText.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.cuarenta_sp)));
                        componentEditText.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_rounded));

                        view_space.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,30));
                        contenedor_consulta.addView(componentNumber);
                        contenedor_consulta.addView(componentEditText);
                        contenedor_consulta_view.addView(view_space);
                    }
                    break;


            }
            consulta_layout.addView(contenedor_consulta);
            consulta_layout.addView(contenedor_consulta_view);


        }


    }

    private void radioGroupServices (){

        progressDialog.setMessage("Consultando...");
        progressDialog.show();
        new ConsumeServicesExpress().consume_api(15, new interfaceOnResponse() {
            @Override
            public void finish_consume_services() {
                finish_process();
            }

            @Override
            public void solicit_token_error_services(String mensaje_token) {
                progressDialog.dismiss();
                Intent intento = new Intent(SoatActivity.this, LoginActivity.class);
                intento.putExtra("mensaje_inicio", mensaje_token);
                startActivity(intento);
                SoatActivity.this.finish();
            }

            @Override
            public void finish_fail_consume_services(int codigo) {
                progressDialog.dismiss();
                if(codigo == 999){
                    utils_class.msjsSimple(SoatActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo INTERNET " + codigo, "Aceptar", "", 0, 1);
                }else {
                    utils_class.msjsSimple(SoatActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo " + codigo, "Aceptar", "", 0, 1);
                }
                return;
            }
        }, this);

    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        SolicSaldoActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


    private String searchIdComponent (String nombre_campo){

        for(int x = 0; x < interfacesModelo.formulario.size(); x++) {
            formulario_local = interfacesModelo.formulario.get(x);
            if(formulario_local.getN().equals(nombre_campo))
                break;
        }


        switch(formulario_local.getT()){
            case "select":
                if(formulario_local.getN().equals("Tipo Documento")){
                    Spinner component =findViewById(100+formulario_local.getO());
                    return Integer.toString(component.getSelectedItemPosition() + 1);
                }else if(formulario_local.getN().equals("Ciudad")){
                    componentAdapter =
                            new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,getResources().getStringArray(R.array.ciudadSoat));
                    AutoCompleteTextView component = findViewById(100+formulario_local.getO());

                    return Integer.toString(componentAdapter.getPosition(component.getText().toString())+2);
                }

            case "text":
            case "number":
                EditText componentEditText = findViewById(100+formulario_local.getO());
                return componentEditText.getText().toString();
        }
        return "";
    }


}
