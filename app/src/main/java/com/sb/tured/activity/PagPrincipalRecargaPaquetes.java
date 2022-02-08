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
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterPackOperRecarg;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PagPrincipalRecargaPaquetes extends AppCompatActivity implements View.OnClickListener {

    private utilitiesTuRedBD bd_data;
    private List pack_oper_recarga;
    private ServOperPackRecarga serv_pack_oper_recarga;
    private String operador = "";
    private String url_imagen = "";
    private ImageView imagen_operador;
    private ImageView icono_rol_usuario;
    private utilitiesClass utils_class;
    private TextView title_tool_bar;
    private TextView texto_paquetes;
    private TextView texto_voz;
    private TextView texto_incluido;
    private TextView texto_navegacion;
    private TextView texto_app_redes;
    private TextView texto_reventa;
    private TextView texto_larga_distancia;
    private TextView texto_tv;
    private TextView texto_inalambrico;
    private View view_oneone;
    private View view_two;
    private View view_three;
    private View view_four;
    private View view_five;
    private View view_six;
    private View view_seven;
    private View view_one;


    private ImageView back_button;
    private ImageView button_info;
    private ArrayList<PackOperRecarga> listaPackOperRecarVoz;
    private ArrayList<PackOperRecarga> listaPackOperRecarIncluido;
    private ArrayList<PackOperRecarga> listaPackOperRecarNavegacion;
    private ArrayList<PackOperRecarga> listaPackOperRecarAppRedes;
    private ArrayList<PackOperRecarga> listaPackOperRecarReventa;
    private ArrayList<PackOperRecarga> listaPackOperRecarLargDistancia;
    private ArrayList<PackOperRecarga> listaPackOperRecarTv;
    private ArrayList<PackOperRecarga> listaPackOperRecarInalambrico;
    private RecyclerView recyclerMenuPackRecargasVoz;
    private RecyclerView recyclerMenuPackRecargasTodoIncluido;
    private RecyclerView recyclerMenuPackRecargasTodoNavegacion;
    private RecyclerView recyclerMenuPackRecargasTodoAppRedes;
    private RecyclerView recyclerMenuPackRecargasTodoReventa;
    private RecyclerView recyclerMenuPackRecargasTodoLargaDistancia;
    private RecyclerView recyclerMenuPackRecargasTodoTv;
    private RecyclerView recyclerMenuPackRecargasTodoInalambrico;
    private PackOperRecarga objaux;
    private String tabla;
    private String id;
    private String servicio;
    private String id_producto;
    private String id_recargas;
    private String id_monto;
    private String saldo;
    private String nombre_categoria;
    private Button boton_cancelar_paquete;
    private Button boton_confirmar_recarga_paquete;
    private EditText cap_valor_recarga_paquete;
    private EditText cap_num_recarga_paquete;
    private TextView text_paquete_seleccionado;
    private HomeProductosParametros productos_parametros = new HomeProductosParametros();
    //private Button button_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_recarga_paquetes);
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
        saldo = getIntent().getExtras().getString("saldo");
        nombre_categoria = getIntent().getExtras().getString("nombre_categoria");

        completeLayout();


        final adapterPackOperRecarg adapterVoz = new adapterPackOperRecarg(listaPackOperRecarVoz);
        final adapterPackOperRecarg adapterIncluido = new adapterPackOperRecarg(listaPackOperRecarIncluido);
        final adapterPackOperRecarg adapterNavegacion = new adapterPackOperRecarg(listaPackOperRecarNavegacion);
        final adapterPackOperRecarg adapterAppRedes = new adapterPackOperRecarg(listaPackOperRecarAppRedes);
        final adapterPackOperRecarg adapterReventa = new adapterPackOperRecarg(listaPackOperRecarReventa);
        final adapterPackOperRecarg adapterLargaDistancia = new adapterPackOperRecarg(listaPackOperRecarLargDistancia);
        final adapterPackOperRecarg adapterTv = new adapterPackOperRecarg(listaPackOperRecarTv);
        final adapterPackOperRecarg adapterInalambrico = new adapterPackOperRecarg(listaPackOperRecarInalambrico);

        adapterVoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiosLayoutComponentes(1);
                cap_valor_recarga_paquete.setText(adapterVoz.listPackOperRecarg.get(recyclerMenuPackRecargasVoz.getChildPosition(v)).getValor().replace(".00", ""));
                cap_valor_recarga_paquete.setEnabled(false);
                cap_num_recarga_paquete.setEnabled(true);
                //id_producto = adapterVoz.listPackOperRecarg.get(recyclerMenuPackRecargasVoz.getChildPosition(v)).getId_producto_recargas();
                id_monto = adapterVoz.listPackOperRecarg.get(recyclerMenuPackRecargasVoz.getChildPosition(v)).getId();

                text_paquete_seleccionado.setText(adapterVoz.listPackOperRecarg.get(recyclerMenuPackRecargasVoz.getChildPosition(v)).getDescripcion());
            }
        });

        adapterIncluido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiosLayoutComponentes(1);
                cap_valor_recarga_paquete.setText(adapterIncluido.listPackOperRecarg.get(recyclerMenuPackRecargasTodoIncluido.getChildPosition(v)).getValor().replace(".00", ""));
                cap_valor_recarga_paquete.setEnabled(false);
                cap_num_recarga_paquete.setEnabled(true);
                //id_producto = adapterIncluido.listPackOperRecarg.get(recyclerMenuPackRecargasTodoIncluido.getChildPosition(v)).getId_producto_recargas();
                id_monto = adapterIncluido.listPackOperRecarg.get(recyclerMenuPackRecargasTodoIncluido.getChildPosition(v)).getId();
                text_paquete_seleccionado.setText(adapterIncluido.listPackOperRecarg.get(recyclerMenuPackRecargasTodoIncluido.getChildPosition(v)).getDescripcion());
            }
        });

        adapterNavegacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiosLayoutComponentes(1);
                cap_valor_recarga_paquete.setText(adapterNavegacion.listPackOperRecarg.get(recyclerMenuPackRecargasTodoNavegacion.getChildPosition(v)).getValor().replace(".00", ""));
                cap_valor_recarga_paquete.setEnabled(false);
                cap_num_recarga_paquete.setEnabled(true);
                //id_producto = adapterNavegacion.listPackOperRecarg.get(recyclerMenuPackRecargasTodoNavegacion.getChildPosition(v)).getId_producto_recargas();
                id_monto = adapterNavegacion.listPackOperRecarg.get(recyclerMenuPackRecargasTodoNavegacion.getChildPosition(v)).getId();
                text_paquete_seleccionado.setText(adapterNavegacion.listPackOperRecarg.get(recyclerMenuPackRecargasTodoNavegacion.getChildPosition(v)).getDescripcion());
            }
        });

        adapterAppRedes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiosLayoutComponentes(1);
                cap_valor_recarga_paquete.setText(adapterAppRedes.listPackOperRecarg.get(recyclerMenuPackRecargasTodoAppRedes.getChildPosition(v)).getValor());
                cap_valor_recarga_paquete.setEnabled(false);
                cap_num_recarga_paquete.setEnabled(true);
                //id_producto = adapterAppRedes.listPackOperRecarg.get(recyclerMenuPackRecargasTodoAppRedes.getChildPosition(v)).getId_producto_recargas();
                id_monto = adapterAppRedes.listPackOperRecarg.get(recyclerMenuPackRecargasTodoAppRedes.getChildPosition(v)).getId();
                text_paquete_seleccionado.setText(adapterAppRedes.listPackOperRecarg.get(recyclerMenuPackRecargasTodoAppRedes.getChildPosition(v)).getDescripcion());
            }
        });

        adapterReventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiosLayoutComponentes(1);
                cap_valor_recarga_paquete.setText(adapterReventa.listPackOperRecarg.get(recyclerMenuPackRecargasTodoReventa.getChildPosition(v)).getValor().replace(".00", ""));
                cap_valor_recarga_paquete.setEnabled(false);
                cap_num_recarga_paquete.setEnabled(true);
                //id_producto = adapterReventa.listPackOperRecarg.get(recyclerMenuPackRecargasTodoReventa.getChildPosition(v)).getId_producto_recargas();
                id_monto = adapterReventa.listPackOperRecarg.get(recyclerMenuPackRecargasTodoReventa.getChildPosition(v)).getId();
                text_paquete_seleccionado.setText(adapterReventa.listPackOperRecarg.get(recyclerMenuPackRecargasTodoReventa.getChildPosition(v)).getDescripcion());
            }
        });

        adapterLargaDistancia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiosLayoutComponentes(1);
                cap_valor_recarga_paquete.setText(adapterLargaDistancia.listPackOperRecarg.get(recyclerMenuPackRecargasTodoLargaDistancia.getChildPosition(v)).getValor().replace(".00", ""));
                cap_valor_recarga_paquete.setEnabled(false);
                cap_num_recarga_paquete.setEnabled(true);
                //id_producto = adapterLargaDistancia.listPackOperRecarg.get(recyclerMenuPackRecargasTodoLargaDistancia.getChildPosition(v)).getId_producto_recargas();
                id_monto = adapterLargaDistancia.listPackOperRecarg.get(recyclerMenuPackRecargasTodoLargaDistancia.getChildPosition(v)).getId();
                text_paquete_seleccionado.setText(adapterLargaDistancia.listPackOperRecarg.get(recyclerMenuPackRecargasTodoLargaDistancia.getChildPosition(v)).getDescripcion());
            }
        });

        adapterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiosLayoutComponentes(1);
                cap_valor_recarga_paquete.setText(adapterTv.listPackOperRecarg.get(recyclerMenuPackRecargasTodoTv.getChildPosition(v)).getValor().replace(".00", ""));
                cap_valor_recarga_paquete.setEnabled(false);
                cap_num_recarga_paquete.setEnabled(true);
                //id_producto = adapterTv.listPackOperRecarg.get(recyclerMenuPackRecargasTodoTv.getChildPosition(v)).getId_producto_recargas();
                id_monto = adapterTv.listPackOperRecarg.get(recyclerMenuPackRecargasTodoTv.getChildPosition(v)).getId();
                text_paquete_seleccionado.setText(adapterTv.listPackOperRecarg.get(recyclerMenuPackRecargasTodoTv.getChildPosition(v)).getDescripcion());
            }
        });

        adapterInalambrico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiosLayoutComponentes(1);
                cap_valor_recarga_paquete.setText(adapterInalambrico.listPackOperRecarg.get(recyclerMenuPackRecargasTodoInalambrico.getChildPosition(v)).getValor());
                cap_valor_recarga_paquete.setEnabled(false);
                cap_num_recarga_paquete.setEnabled(true);
                //id_producto = adapterInalambrico.listPackOperRecarg.get(recyclerMenuPackRecargasTodoInalambrico.getChildPosition(v)).getId_producto_recargas();
                id_monto = adapterInalambrico.listPackOperRecarg.get(recyclerMenuPackRecargasTodoInalambrico.getChildPosition(v)).getId();
                text_paquete_seleccionado.setText(adapterInalambrico.listPackOperRecarg.get(recyclerMenuPackRecargasTodoInalambrico.getChildPosition(v)).getDescripcion());
            }
        });


        recyclerMenuPackRecargasVoz.setAdapter(adapterVoz);
        recyclerMenuPackRecargasTodoIncluido.setAdapter(adapterIncluido);
        recyclerMenuPackRecargasTodoNavegacion.setAdapter(adapterNavegacion);
        recyclerMenuPackRecargasTodoAppRedes.setAdapter(adapterAppRedes);
        recyclerMenuPackRecargasTodoReventa.setAdapter(adapterReventa);
        recyclerMenuPackRecargasTodoLargaDistancia.setAdapter(adapterLargaDistancia);
        recyclerMenuPackRecargasTodoTv.setAdapter(adapterTv);
        recyclerMenuPackRecargasTodoInalambrico.setAdapter(adapterInalambrico);

        setupUI(findViewById(R.id.scrollLayout));
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
        listaPackOperRecarVoz = new ArrayList<>();
        listaPackOperRecarIncluido = new ArrayList<>();
        listaPackOperRecarNavegacion = new ArrayList<>();
        listaPackOperRecarAppRedes = new ArrayList<>();
        listaPackOperRecarReventa = new ArrayList<>();
        listaPackOperRecarLargDistancia = new ArrayList<>();
        listaPackOperRecarTv = new ArrayList<>();
        listaPackOperRecarInalambrico = new ArrayList<>();

        recyclerMenuPackRecargasVoz = findViewById(R.id.RecyclerViewPagRecargaPaquetesPackVoz);
        recyclerMenuPackRecargasVoz.setLayoutManager(new LinearLayoutManager(this));
        recyclerMenuPackRecargasTodoIncluido = findViewById(R.id.RecyclerViewPagRecargaPaquetesTodoIncluido);
        recyclerMenuPackRecargasTodoIncluido.setLayoutManager(new LinearLayoutManager(this));
        recyclerMenuPackRecargasTodoNavegacion = findViewById(R.id.RecyclerViewPagRecargaPaquetesNavegacion);
        recyclerMenuPackRecargasTodoNavegacion.setLayoutManager(new LinearLayoutManager(this));
        recyclerMenuPackRecargasTodoAppRedes = findViewById(R.id.RecyclerViewPagRecargaPaquetesAppRedes);
        recyclerMenuPackRecargasTodoAppRedes.setLayoutManager(new LinearLayoutManager(this));
        recyclerMenuPackRecargasTodoReventa = findViewById(R.id.RecyclerViewPagRecargaPaquetesReventa);
        recyclerMenuPackRecargasTodoReventa.setLayoutManager(new LinearLayoutManager(this));
        recyclerMenuPackRecargasTodoLargaDistancia = findViewById(R.id.RecyclerViewPagRecargaPaquetesLargDistancia);
        recyclerMenuPackRecargasTodoLargaDistancia.setLayoutManager(new LinearLayoutManager(this));
        recyclerMenuPackRecargasTodoTv = findViewById(R.id.RecyclerViewPagRecargaPaquetesTv);
        recyclerMenuPackRecargasTodoTv.setLayoutManager(new LinearLayoutManager(this));
        recyclerMenuPackRecargasTodoInalambrico = findViewById(R.id.RecyclerViewPagRecargaPaquetesInternetInalambrico);
        recyclerMenuPackRecargasTodoInalambrico.setLayoutManager(new LinearLayoutManager(this));

        texto_voz = findViewById(R.id.textPaquetesVoz);
        texto_incluido = findViewById(R.id.textPaquetesIncluido);
        texto_navegacion = findViewById(R.id.textPaquetesNavegacion);
        texto_app_redes = findViewById(R.id.textPaquetesAppRedes);
        texto_reventa = findViewById(R.id.textPaquetesReventa);
        texto_larga_distancia = findViewById(R.id.textPaquetesLargaDistancia);
        texto_tv = findViewById(R.id.textPaquetesTv);
        texto_inalambrico = findViewById(R.id.textPaquetesInternetInalambrico);

        view_oneone = findViewById(R.id.lineaOneOne);
        view_one = findViewById(R.id.lineaOne);
        view_two = findViewById(R.id.lineaTwo);
        view_three = findViewById(R.id.lineaThree);
        view_four = findViewById(R.id.lineaFor);
        view_five = findViewById(R.id.lineaFive);
        view_six = findViewById(R.id.lineaSix);
        view_seven = findViewById(R.id.lineaSeven);

        boton_cancelar_paquete = findViewById(R.id.buttonCancelarPaquete);
        boton_confirmar_recarga_paquete = findViewById(R.id.buttonAceptarPaquete);
        cap_valor_recarga_paquete = findViewById(R.id.capValorRecargaPaquete);
        text_paquete_seleccionado = findViewById(R.id.textPaqueteSeleccionado);
        cap_num_recarga_paquete = findViewById(R.id.capNumeroRecargaPaquete);

        objaux = new PackOperRecarga();
        texto_paquetes = findViewById(R.id.textPaquetes);
    }

    private void completeLayout() {
        String id_producto_local = id_producto;
        String id_local = id;
        String id_recargas_local = id_recargas;

        title_tool_bar.setText(operador);
        boton_cancelar_paquete.setVisibility(View.GONE);
        boton_confirmar_recarga_paquete.setVisibility(View.GONE);
        cap_valor_recarga_paquete.setVisibility(View.GONE);
        text_paquete_seleccionado.setVisibility(View.GONE);
        cap_num_recarga_paquete.setVisibility(View.GONE);


        try {
            productos_parametros = bd_data.getProductoHomeParametros(id, tabla);

            cap_num_recarga_paquete.setHint(productos_parametros.getN());
            cap_num_recarga_paquete.setFilters(new InputFilter[] {new InputFilter.LengthFilter(Integer.parseInt(productos_parametros.getLmx()))});


            serv_pack_oper_recarga = bd_data.getServPackOperRecarga(operador, servicio);
            id = serv_pack_oper_recarga.getId();
            id_producto = serv_pack_oper_recarga.getId_producto();
            id_recargas = serv_pack_oper_recarga.getId_recargas();
            if (!serv_pack_oper_recarga.getEstatus().equals("FALSE") && (serv_pack_oper_recarga.getServicio().equals("PAQUETES") || serv_pack_oper_recarga.getServicio().equals(nombre_categoria))) {

                pack_oper_recarga = bd_data.getAllPackOperRecarga(serv_pack_oper_recarga.getId());

                for (int x = 0; x < pack_oper_recarga.size(); x++) {
                    objaux = (PackOperRecarga) pack_oper_recarga.get(x);
                    if (Integer.parseInt(objaux.getEstatus()) == 1) {
                        switch (Integer.parseInt(objaux.getOrden_categoria())) {
                            case 2:
                                texto_voz.setText(objaux.getCategoria());
                                listaPackOperRecarVoz.add((PackOperRecarga) pack_oper_recarga.get(x));
                                break;
                            case 3:
                                texto_incluido.setText(objaux.getCategoria());
                                listaPackOperRecarIncluido.add((PackOperRecarga) pack_oper_recarga.get(x));
                                break;
                            case 1:
                                texto_incluido.setText(objaux.getCategoria());
                                listaPackOperRecarIncluido.add((PackOperRecarga) pack_oper_recarga.get(x));
                                break;
                            case 4:
                                texto_navegacion.setText(objaux.getCategoria());
                                listaPackOperRecarNavegacion.add((PackOperRecarga) pack_oper_recarga.get(x));
                                break;
                            case 5:
                                texto_app_redes.setText(objaux.getCategoria());
                                listaPackOperRecarAppRedes.add((PackOperRecarga) pack_oper_recarga.get(x));
                                break;
                            case 6:
                                texto_reventa.setText(objaux.getCategoria());
                                listaPackOperRecarReventa.add((PackOperRecarga) pack_oper_recarga.get(x));
                                break;
                            case 7:
                                texto_larga_distancia.setText(objaux.getCategoria());
                                listaPackOperRecarLargDistancia.add((PackOperRecarga) pack_oper_recarga.get(x));
                                break;
                            case 8:
                                texto_tv.setText(objaux.getCategoria());
                                listaPackOperRecarTv.add((PackOperRecarga) pack_oper_recarga.get(x));
                                break;
                            case 9:
                                texto_inalambrico.setText(objaux.getCategoria());
                                listaPackOperRecarInalambrico.add((PackOperRecarga) pack_oper_recarga.get(x));
                                break;
                        }
                    }
                }

                if (texto_voz.getText().length() == 0) {
                    view_oneone.setVisibility(View.GONE);
                }
                if (texto_incluido.getText().length() == 0) {
                    view_one.setVisibility(View.GONE);
                }
                if (texto_navegacion.getText().length() == 0) {
                    view_two.setVisibility(View.GONE);
                }
                if (texto_app_redes.getText().length() == 0) {
                    view_three.setVisibility(View.GONE);
                }
                if (texto_reventa.getText().length() == 0) {
                    view_four.setVisibility(View.GONE);
                }
                if (texto_larga_distancia.getText().length() == 0) {
                    view_five.setVisibility(View.GONE);
                }
                if (texto_tv.getText().length() == 0) {
                    view_six.setVisibility(View.GONE);
                }
                if (texto_inalambrico.getText().length() == 0) {
                    view_seven.setVisibility(View.GONE);
                }

                texto_paquetes.setText(operador + "-PAQUETES");
            } else {
                view_oneone.setVisibility(View.GONE);
                view_one.setVisibility(View.GONE);
                view_two.setVisibility(View.GONE);
                view_three.setVisibility(View.GONE);
                view_four.setVisibility(View.GONE);
                view_five.setVisibility(View.GONE);
                view_six.setVisibility(View.GONE);
                view_seven.setVisibility(View.GONE);
                texto_paquetes.setText("No hay paquetes...");
                id_producto = id_producto_local;
                id = id_local;
                id_recargas = id_recargas_local;
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
        }catch (Exception ex){
            nextActivity(2);
        }
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

        boton_cancelar_paquete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    cambiosLayoutComponentes(2);
                    completeLayout();
                }
                return false;
            }
        });

        boton_confirmar_recarga_paquete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (cap_num_recarga_paquete.length() == 0) {
                        utils_class.msjsSimple(PagPrincipalRecargaPaquetes.this, "INFORMACION", "Por favor digitar el numero a recargar", "Aceptar", "", 0, 1);
                    } else if (cap_num_recarga_paquete.length() < Integer.parseInt(productos_parametros.getLmn()) || cap_num_recarga_paquete.length() > Integer.parseInt(productos_parametros.getLmx())) {
                        utils_class.msjsSimple(PagPrincipalRecargaPaquetes.this, "INFORMACION", "El numero celular excede los 10 digitos", "Aceptar", "", 0, 1);
                    } else {
                        nextActivity(3);
                    }
                }
                return false;
            }
        });

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
                if(!nombre_categoria.equals("PINES") && !nombre_categoria.equals("APUESTAS")) {
                    intento = new Intent(this, PagPrincipalRecarga.class); // Lanzamos SiguienteActivity
                    intento.putExtra("operador", operador);
                    intento.putExtra("id", id);
                    intento.putExtra("url_imagen", url_imagen);
                    intento.putExtra("tabla", tabla);
                    intento.putExtra("servicio", "RECARGAS");
                    intento.putExtra("id_producto", id_producto);
                    intento.putExtra("id_monto", id_monto);
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
                intento.putExtra("servicio", "PAQUETES");
                intento.putExtra("referencia", cap_num_recarga_paquete.getText().toString());
                intento.putExtra("valor", cap_valor_recarga_paquete.getText().toString());
                intento.putExtra("paquete", text_paquete_seleccionado.getText().toString());
                intento.putExtra("id_producto", id_producto);
                intento.putExtra("id_recargas", id_recargas);
                intento.putExtra("id_monto", id_monto);
                intento.putExtra("id_servicio", id);
                intento.putExtra("saldo", saldo);
                intento.putExtra("nombre_categoria", nombre_categoria);
                startActivity(intento);
                this.finish();
                break;
        }
    }

    private void cambiosLayoutComponentes(int opc) {

        if (opc == 1) {
            recyclerMenuPackRecargasVoz.setVisibility(View.GONE);
            recyclerMenuPackRecargasTodoIncluido.setVisibility(View.GONE);
            recyclerMenuPackRecargasTodoNavegacion.setVisibility(View.GONE);
            recyclerMenuPackRecargasTodoAppRedes.setVisibility(View.GONE);
            recyclerMenuPackRecargasTodoReventa.setVisibility(View.GONE);
            recyclerMenuPackRecargasTodoLargaDistancia.setVisibility(View.GONE);
            recyclerMenuPackRecargasTodoTv.setVisibility(View.GONE);
            recyclerMenuPackRecargasTodoInalambrico.setVisibility(View.GONE);

            view_oneone.setVisibility(View.GONE);
            view_two.setVisibility(View.GONE);
            view_three.setVisibility(View.GONE);
            view_four.setVisibility(View.GONE);
            view_five.setVisibility(View.GONE);
            view_six.setVisibility(View.GONE);
            view_seven.setVisibility(View.GONE);
            view_one.setVisibility(View.GONE);

            texto_paquetes.setVisibility(View.GONE);
            texto_voz.setVisibility(View.GONE);
            texto_incluido.setVisibility(View.GONE);
            texto_navegacion.setVisibility(View.GONE);
            texto_app_redes.setVisibility(View.GONE);
            texto_reventa.setVisibility(View.GONE);
            texto_larga_distancia.setVisibility(View.GONE);
            texto_tv.setVisibility(View.GONE);
            texto_inalambrico.setVisibility(View.GONE);

            boton_cancelar_paquete.setVisibility(View.VISIBLE);
            boton_confirmar_recarga_paquete.setVisibility(View.VISIBLE);

            cap_valor_recarga_paquete.setVisibility(View.VISIBLE);
            text_paquete_seleccionado.setVisibility(View.VISIBLE);
            cap_num_recarga_paquete.setVisibility(View.VISIBLE);
            if (Integer.parseInt(id) == 12) {
                cap_num_recarga_paquete.setHint("Número");
            }
        } else if (opc == 2) {
            recyclerMenuPackRecargasVoz.setVisibility(View.VISIBLE);
            recyclerMenuPackRecargasTodoIncluido.setVisibility(View.VISIBLE);
            recyclerMenuPackRecargasTodoNavegacion.setVisibility(View.VISIBLE);
            recyclerMenuPackRecargasTodoAppRedes.setVisibility(View.VISIBLE);
            recyclerMenuPackRecargasTodoReventa.setVisibility(View.VISIBLE);
            recyclerMenuPackRecargasTodoLargaDistancia.setVisibility(View.VISIBLE);
            recyclerMenuPackRecargasTodoTv.setVisibility(View.VISIBLE);
            recyclerMenuPackRecargasTodoInalambrico.setVisibility(View.VISIBLE);

            view_oneone.setVisibility(View.VISIBLE);
            view_two.setVisibility(View.VISIBLE);
            view_three.setVisibility(View.VISIBLE);
            view_four.setVisibility(View.VISIBLE);
            view_five.setVisibility(View.VISIBLE);
            view_six.setVisibility(View.VISIBLE);
            view_seven.setVisibility(View.VISIBLE);
            view_one.setVisibility(View.VISIBLE);

            texto_paquetes.setVisibility(View.VISIBLE);
            texto_voz.setVisibility(View.VISIBLE);
            texto_incluido.setVisibility(View.VISIBLE);
            texto_navegacion.setVisibility(View.VISIBLE);
            texto_app_redes.setVisibility(View.VISIBLE);
            texto_reventa.setVisibility(View.VISIBLE);
            texto_larga_distancia.setVisibility(View.VISIBLE);
            texto_tv.setVisibility(View.VISIBLE);
            texto_inalambrico.setVisibility(View.VISIBLE);
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
