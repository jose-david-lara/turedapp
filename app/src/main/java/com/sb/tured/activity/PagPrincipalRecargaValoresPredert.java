package com.sb.tured.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.model.HomeProductosParametros;
import com.sb.tured.model.PackOperRecarga;
import com.sb.tured.model.ServOperPackRecarga;
import com.sb.tured.model.ValueDefaultOperRecarga;
import com.sb.tured.utilities.NumberTextWatcher;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterPackOperRecarg;
import com.sb.tured.utilities.adapterValueDefaultOperRecarg;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PagPrincipalRecargaValoresPredert extends AppCompatActivity implements View.OnClickListener {

    private utilitiesTuRedBD bd_data;
    private List value_default_oper_recarga;
    private ServOperPackRecarga serv_value_default_oper_recarga;
    private ImageView icono_rol_usuario;
    private String operador = "";
    private String url_imagen = "";
    private ImageView imagen_operador;
    private utilitiesClass utils_class;
    private TextView title_tool_bar;
    private TextView texto_paquetes;
    private ImageView back_button;
    private ImageView button_info;
    private ArrayList<ValueDefaultOperRecarga> listaPackOperRecar;
    private RecyclerView recyclerMenuValueDefaultRecarga;
    private ValueDefaultOperRecarga objaux;
    private String tabla;
    private String id;
    private String servicio;
    private String id_monto;
    private String id_producto;
    private String id_recargas;
    private String saldo;
    private String nombre_categoria;
    private EditText cap_num_recarga;
    private TextView texto_valor_paquete;
    private Button boton_cancelar;
    private Button boton_confirmar_recarga;
    private EditText cap_valor_recarga;
    HomeProductosParametros productos_parametros = new HomeProductosParametros();

    //private Button button_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_recarga_valores_predert);
        initComponents();

        Picasso.get()
                .load("https://multiservicios.madefor.work/assets/img/pdv.png")
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(icono_rol_usuario);
        //icono_rol_usuario = interfacesModelo.carga_imagenes.img.get(0);


        button_info.setVisibility(View.INVISIBLE);
        operador = getIntent().getExtras().getString("operador");
        url_imagen = getIntent().getExtras().getString("url_imagen");
        tabla = getIntent().getExtras().getString("tabla");
        id = getIntent().getExtras().getString("id");
        servicio = getIntent().getExtras().getString("servicio");
        id_producto = getIntent().getExtras().getString("id_producto");
        id_recargas = getIntent().getExtras().getString("id_recargas");
        saldo = getIntent().getExtras().getString("saldo");
        nombre_categoria = getIntent().getExtras().getString("nombre_categoria");

        completeLayout();
        //actionevents();


        final adapterValueDefaultOperRecarg adapter = new adapterValueDefaultOperRecarg(listaPackOperRecar);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerMenuValueDefaultRecarga.setVisibility(View.GONE);
                if (adapter.listValueDefaultOperRecarg.get(recyclerMenuValueDefaultRecarga.getChildPosition(v)).getValor().equals("0")) {
                    texto_valor_paquete.setText("Opcion Seleccionada\n" + adapter.listValueDefaultOperRecarg.get(recyclerMenuValueDefaultRecarga.getChildPosition(v)).getDescripcion());
                    cap_valor_recarga.setText("");
                    cap_valor_recarga.setEnabled(true);
                    cap_valor_recarga.setVisibility(View.VISIBLE);
                    id_monto = adapter.listValueDefaultOperRecarg.get(recyclerMenuValueDefaultRecarga.getChildPosition(v)).getId();
                } else {
                    cap_valor_recarga.setText(adapter.listValueDefaultOperRecarg.get(recyclerMenuValueDefaultRecarga.getChildPosition(v)).getValor().replace(".00", ""));
                    cap_valor_recarga.setEnabled(false);
                    cap_valor_recarga.setVisibility(View.VISIBLE);
                    id_monto = adapter.listValueDefaultOperRecarg.get(recyclerMenuValueDefaultRecarga.getChildPosition(v)).getId();
                    texto_valor_paquete.setText("Valor Seleccionado\n" + adapter.listValueDefaultOperRecarg.get(recyclerMenuValueDefaultRecarga.getChildPosition(v)).getDescripcion());
                }
                texto_valor_paquete.setVisibility(View.VISIBLE);
                boton_cancelar.setVisibility(View.VISIBLE);
                boton_confirmar_recarga.setVisibility(View.VISIBLE);


            }
        });
        recyclerMenuValueDefaultRecarga.setAdapter(adapter);
        setupUI(findViewById (R.id.scrollLayout));
        actionevents();
    }

    private void initComponents() {

        imagen_operador = findViewById(R.id.logoProductoRecarga);
        utils_class = new utilitiesClass();
        title_tool_bar = findViewById(R.id.tittle_toolbar);
        bd_data = new utilitiesTuRedBD(this);
        back_button = findViewById(R.id.buttonBack);
        button_info = findViewById(R.id.buttonInfo);
        icono_rol_usuario = findViewById(R.id.iconoUsuarioRol);
        listaPackOperRecar = new ArrayList<>();
        recyclerMenuValueDefaultRecarga = findViewById(R.id.RecyclerViewPagRecargaValorPredert);
        recyclerMenuValueDefaultRecarga.setLayoutManager(new LinearLayoutManager(this));
        objaux = new ValueDefaultOperRecarga();
        texto_paquetes = findViewById(R.id.textPaquetes);
        cap_num_recarga = findViewById(R.id.capNumRecarga);
        texto_valor_paquete = findViewById(R.id.textValorSeleccionado);
        boton_cancelar = findViewById(R.id.buttonCancelarValorPredeterminado);
        cap_valor_recarga = findViewById(R.id.capValorRecarga);
        boton_confirmar_recarga = findViewById(R.id.buttonAceptarRecarga);
    }

    private void completeLayout() {



        productos_parametros = bd_data.getProductoHomeParametros(id,tabla);

        /*if(operador.equals("WPLAY") || Integer.parseInt(id_producto) == 3){
            cap_num_recarga.setHint("Cedula");
        }

        if(Integer.parseInt(id) == 12){
            cap_num_recarga.setHint("Número");
        }*/



        cap_num_recarga.setHint(productos_parametros.getN());
        cap_num_recarga.setFilters(new InputFilter[] {new InputFilter.LengthFilter(Integer.parseInt(productos_parametros.getLmx()))});

        cap_valor_recarga.addTextChangedListener(new NumberTextWatcher(cap_valor_recarga));

        //texto_valor_paquete.setVisibility(View.INVISIBLE);
        cap_valor_recarga.setText("");
        cap_valor_recarga.setEnabled(false);
        cap_valor_recarga.setVisibility(View.GONE);
        texto_valor_paquete.setText("\nSeleccione una opcion");
        boton_cancelar.setVisibility(View.GONE);
        boton_confirmar_recarga.setVisibility(View.GONE);

        ValueDefaultOperRecarga recargaLocal = new ValueDefaultOperRecarga();

        title_tool_bar.setText(operador);


        serv_value_default_oper_recarga = bd_data.getServPackOperRecarga(operador, servicio);
        id = serv_value_default_oper_recarga.getId();
        id_recargas = serv_value_default_oper_recarga.getId_recargas();
        if (!serv_value_default_oper_recarga.getEstatus().equals("FALSE")) {

            value_default_oper_recarga = bd_data.getAllValueDefaultOperRecarga(serv_value_default_oper_recarga.getId(), serv_value_default_oper_recarga.getTabla());


            //listaPackOperRecar.add(recargaLocal);
            for (int x = 0; x < value_default_oper_recarga.size(); x++) {
                objaux = (ValueDefaultOperRecarga) value_default_oper_recarga.get(x);
                if (Integer.parseInt(objaux.getEstatus()) == 1 && objaux.getValor().length() != 0) {
                    if (objaux.getValor().equals("0")) {
                        objaux.setDescripcion("Otro Valor");
                    }
                    listaPackOperRecar.add((ValueDefaultOperRecarga) value_default_oper_recarga.get(x));
                }
            }

        } else {

        }


        // title_tool_bar.setText(producto_info.getOperador()+"-"+producto_info.getServicio());


        Picasso.get()
                .load(url_imagen)
                .transform(new RoundedTransformation(50, 1))
                .resize(180, 180)
                .centerCrop()
                .into(imagen_operador);

       /* listaMenuHome.add(new HomeMenu(1,"Recargas y Productos","Todos los productos",R.mipmap.ic_producto));
        listaMenuHome.add(new HomeMenu(1,"Ultimas 5 Recargas","Ultimas 5 transacciones realizadas",R.mipmap.ic_last_trans));
        listaMenuHome.add(new HomeMenu(1,"Consulta de Saldo","Ultimas 5 transacciones realizadas",R.mipmap.ic_check_saldo));
        listaMenuHome.add(new HomeMenu(1,"Solicitud de Saldo","Ultimas 5 transacciones realizadas",R.mipmap.ic_solic_saldo));*/

    }

    @Override
    public void onClick(View v) {

    }

    private void actionevents() {
        back_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onBackPressed();
                }
                return false;
            }
        });

        boton_cancelar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    recyclerMenuValueDefaultRecarga.setVisibility(View.VISIBLE);
                    completeLayout();
                }
                return false;
            }
        });

        boton_confirmar_recarga.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (cap_num_recarga.length() == 0 || cap_valor_recarga.length() == 0) {
                        utils_class.msjsSimple(PagPrincipalRecargaValoresPredert.this, "INFORMACION", "Por favor llenar todos los campos", "Aceptar", "", 0, 1);
                    } else if ((cap_num_recarga.length() < Integer.parseInt(productos_parametros.getLmn()) || cap_num_recarga.length() > Integer.parseInt(productos_parametros.getLmx())) && (Integer.parseInt(id_producto) != 3 || !operador.equals("WPLAY")) && Integer.parseInt(id) != 12) {
                        utils_class.msjsSimple(PagPrincipalRecargaValoresPredert.this, "INFORMACION", "El numero celular excede los 10 digitos", "Aceptar", "", 0, 1);
                    }else {
                        nextActivity(3);
                    }
                }
                return false;
            }
        });

       /* button_enter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onBackPressed();
                }
                return false;
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        nextActivity(2);
    }

    public void nextActivity(int opc) {
        Intent intento;
        switch (opc) {
            case 1:

                break;
            case 2:
                if(!nombre_categoria.equals("APUESTAS")) {
                    intento = new Intent(this, PagPrincipalRecarga.class); // Lanzamos SiguienteActivity
                    intento.putExtra("operador", operador);
                    intento.putExtra("id", id);
                    intento.putExtra("url_imagen", url_imagen);
                    intento.putExtra("tabla", tabla);
                    intento.putExtra("servicio", "RECARGAS");
                    intento.putExtra("id_producto", id_producto);
                    intento.putExtra("id_recargas", id_recargas);
                    intento.putExtra("nombre_categoria", nombre_categoria);
                }else{
                    intento = new Intent(this, ProductosCategoriasActivity.class);
                    intento.putExtra("nombre_categoria", nombre_categoria);
                }
                startActivity(intento);
                this.finish();
                break;

            case 3:
                intento = new Intent(this, PagPrincipalRecargaConfirmar.class); // Lanzamos SiguienteActivity
                intento.putExtra("operador", operador);
                intento.putExtra("id", id);
                intento.putExtra("url_imagen", url_imagen);
                intento.putExtra("tabla", tabla);
                intento.putExtra("servicio", "RECARGAS");
                intento.putExtra("referencia", cap_num_recarga.getText().toString());
                if (cap_valor_recarga.getText().length() > 3) {
                    intento.putExtra("valor", cap_valor_recarga.getText().toString().replace(",", ""));
                }else
                    intento.putExtra("valor", cap_valor_recarga.getText().toString());
                intento.putExtra("id_producto", id_producto);
                intento.putExtra("id_recargas", id_recargas);
                intento.putExtra("id_servicio", id);
                intento.putExtra("id_monto", id_monto);
                intento.putExtra("saldo", saldo);
                intento.putExtra("nombre_categoria", nombre_categoria);
                startActivity(intento);
                this.finish();
                break;
        }
    }

    public void hideSoftKeyboard(View activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(
                        Activity.INPUT_METHOD_SERVICE);

            inputMethodManager.hideSoftInputFromWindow(
                    getWindow().getDecorView().getRootView().getWindowToken(), 0);
    }

    public void setupUI(final View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(view);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }
}
