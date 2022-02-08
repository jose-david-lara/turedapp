package com.sb.tured.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.sb.tured.R;
import com.sb.tured.controller.ValidateJson;
import com.sb.tured.interfaces.interfaceOnResponse;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.io.ConsumeServicesExpress;
import com.sb.tured.model.HomeMenu;
import com.sb.tured.model.HomeProductos;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterListMenuPrincp;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilssunmiprinter.printerSunmiV1;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import static com.sb.tured.interfaces.interfacesModelo.*;


public class MenuPrincActivity extends AppCompatActivity implements View.OnClickListener, interfacesModelo {


    private String mensaje_saldo;
    private TextView text_tittle;
    private ImageView back_button;
    private ImageView info_button;
    private ScrollView scroll_menu_principal;
    private static ImageView icono_rol_usuario_local;
    private Button button_cerrar_sesion;
    private Button button_close_app;
    private Button button_act_info;
    private Button button_categorias;
    private HomeProductos objaux = new HomeProductos();
    private Context contexto = this;
    private ArrayList<HomeMenu> listaMenuHome;
    private RecyclerView recyclerMenuHome;
    private utilitiesClass utils_class;
    private String url_msj;
    private ProgressDialog progressDialog;

    private ValidateJson validate_user = new ValidateJson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_princ);

        initComponents();
        listMenuPrincipal();
        adapterListMenuPrincp adapter = new adapterListMenuPrincp(listaMenuHome);
        mensaje_saldo = getIntent().getExtras().getString("mensaje_saldo");

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerMenuHome.getChildPosition(v) == 0) {
                    nextActivity(1);
                } else if (recyclerMenuHome.getChildPosition(v) == 1) {
                    nextActivity(2);
                } else if (recyclerMenuHome.getChildPosition(v) == 2) {
                    nextActivity(3);
                } else if (recyclerMenuHome.getChildPosition(v) == 3) {
                    nextActivity(4);
                } else if (recyclerMenuHome.getChildPosition(v) == 4) {
                    nextActivity(5);
                } else {
                    utils_class.msjsSimple(MenuPrincActivity.this, "INFORMACION", "Item desactivado", "Aceptar", "", 0, 1);
                }
            }
        });
        recyclerMenuHome.setAdapter(adapter);
        if (mensaje_saldo.length() > 0) {
            if (!mensaje_saldo.equals("00") && !mensaje_saldo.equals("01") && !mensaje_saldo.equals("CERTIFICADO") && !mensaje_saldo.equals("SOAT")) {
                utils_class.msjsSimple(this, "INFORMACION RECARGA", mensaje_saldo, "Aceptar", "", 0, 1);
            } else if (mensaje_saldo.equals("01")) {
                utils_class.msjsSimple(this, "BIENVENID@", data_user.getNombre_usuario(), "Aceptar", "", 1, 1);
            } else if (mensaje_saldo.equals("CERTIFICADO")) {
                url_msj = mensaje_saldo.substring(mensaje_saldo.indexOf("-"));
                msjDialogoWeb();
            }else if (mensaje_saldo.equals("SOAT")) {
                mensaje_saldo = json_object_response.getJson_response_data().get("respuesta").toString();
                mensaje_saldo = mensaje_saldo.substring(mensaje_saldo.indexOf("h") + 1);
                mensaje_saldo = mensaje_saldo.substring(mensaje_saldo.indexOf("h"));
                url_msj = mensaje_saldo.substring(0,mensaje_saldo.indexOf("\"")-1);
                mensaje_saldo = json_object_response.getJson_response_data().get("message").toString();
                msjDialogoWebSoat(mensaje_saldo);
            }else if(!interfacesModelo.informacion_mobil.isRed_conectada() && !mensaje_saldo.equals("00"))  {
                utils_class.msjsSimple(this, "INFORMACION",mensaje_saldo, "Aceptar", "", 1, 1);
            }
        }


    }

    private void initComponents() {

        info_button = findViewById(R.id.buttonInfo);
        info_button.setVisibility(View.INVISIBLE);
        icono_rol_usuario_local = findViewById(R.id.iconoUsuarioRol);




        utils_class = new utilitiesClass();

        listaMenuHome = new ArrayList<>();
        recyclerMenuHome = findViewById(R.id.RecyclerViewMenuPrincp);
        recyclerMenuHome.setLayoutManager(new LinearLayoutManager(this));


        //Titulo de tool bar
        text_tittle = findViewById(R.id.tittle_toolbar);
        text_tittle.setText("Menú-Datáfono");

        back_button = findViewById(R.id.buttonBack);
        back_button.setVisibility(View.INVISIBLE);

        button_cerrar_sesion = findViewById(R.id.buttonCerrarSesion);
        button_act_info = findViewById(R.id.buttonActInfo);

        button_close_app = findViewById(R.id.buttonCloseApp);
        button_categorias = findViewById(R.id.buttonCategorias);
        button_categorias.setVisibility(View.GONE);

        scroll_menu_principal = findViewById(R.id.ScrollView01);
        progressDialog = new ProgressDialog(MenuPrincActivity.this);


    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {

            if (v.getId() == button_cerrar_sesion.getId()) {
                cerrarSesion();
            }else if (v.getId() == button_act_info.getId()) {
                progressDialog.setMessage("Actualizando Información...");
                progressDialog.show();
                actualizarInformacionUsuario();
            }else if (v.getId() == button_close_app.getId()) {
               finish();
            }else if (v.getId() == button_categorias.getId()) {
                //printer_sunmi_v1.templatePrintRecarga("String terminal","String fecha", "String hora", "String autorizacion", "String referencia", "String monto");
                /*Intent intento = new Intent(contexto, MenuProductosActivity.class);
                startActivity(intento);
                MenuPrincActivity.this.finish();*/
            }

    }


    private void listMenuPrincipal() {



        Picasso.get()
                .load("https://multiservicios.madefor.work/assets/img/pdv.png")
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(icono_rol_usuario_local, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
       //icono_rol_usuario_local = interfacesModelo.carga_imagenes.img.get(1);

        listaMenuHome.add(new HomeMenu(1, "Recargas y Productos", "Todos los productos", R.mipmap.ic_producto_new));
        listaMenuHome.add(new HomeMenu(2, "Transacciones", "Ultimas 5 transacciones realizadas", R.mipmap.ic_last_trans_new));
        listaMenuHome.add(new HomeMenu(3, "Consulta de Saldo", "Consulta de saldo", R.mipmap.ic_check_saldo_new));
        listaMenuHome.add(new HomeMenu(4, "Solicitud de Saldo", "Solicitud de saldo", R.mipmap.ic_solic_saldo_new));//ic_solic_saldo_new
        listaMenuHome.add(new HomeMenu(5, "Consultar Recarga", "Consultar Recarga", R.mipmap.ic_check_recarga));//ic_solic_saldo_new

    }


    public void cerrarSesion() {

        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("SESION");
        dialogo.setMessage("Desea cerrar sesion?");
        dialogo.setCancelable(false);

        dialogo.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                utils_class.cerrar_sesion_usuario(MenuPrincActivity.this);
                Intent intento = new Intent(contexto, LoginActivity.class);
                intento.putExtra("mensaje_inicio", "");
                startActivity(intento);
                MenuPrincActivity.this.finish();
            }
        });
        dialogo.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {

            }
        });

        dialogo.show();
    }

    public void actualizarInformacionUsuario (){
        new ConsumeServicesExpress().consume_api(1, new interfaceOnResponse() {
            @Override
            public void finish_consume_services() {
                finish_process_update_info();
            }

            @Override
            public void solicit_token_error_services(String mensaje_token) {
                Activity actividad = MenuPrincActivity.this;
                progressDialog.dismiss();
                utils_class.msjsSimple(MenuPrincActivity.this, "INFORMACION SERVICIO", mensaje_token, "Aceptar", "", 0,1);

            }

            @Override
            public void finish_fail_consume_services(int codigo) {
                progressDialog.dismiss();
                if(codigo == 999){
                    utils_class.msjsSimple(MenuPrincActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo INTERNET " + codigo, "Aceptar", "", 0, 1);
                }else {
                    utils_class.msjsSimple(MenuPrincActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo " + codigo, "Aceptar", "", 0, 1);
                }

            }
        },MenuPrincActivity.this);
    }

    private void finish_process_update_info (){
        if (json_object_response.getJson_object_response() == null) {
            Toast.makeText(MenuPrincActivity.this, "Error en el servicio de autenticacion", Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            progressDialog.dismiss();
            if (!validate_user.data_user_token(this,false)) {
                Toast.makeText(MenuPrincActivity.this, json_object_response.getJson_object_response().get("message").getAsString(), Toast.LENGTH_LONG).show();
            } else{
                utils_class.msjsSimple(MenuPrincActivity.this, "INFORMACION", "Información actualizada", "Aceptar", "", 0,1);
            }
        }
    }




    public void nextActivity(int opc) {

        if (opc == 1) {
            Intent intento = new Intent(this, MenuProductosActivity.class);
            startActivity(intento);
            this.finish();
            /*Intent intento = new Intent(this, CategoriasProductosActivity.class);
            startActivity(intento);
            MenuPrincActivity.this.finish();*/
        } else if (opc == 2) {
            Intent intento = new Intent(this, LastTransActivity.class);
            startActivity(intento);
            this.finish();
        } else if (opc == 3) {
            Intent intento = new Intent(this, ConsultSaldoActivity.class);
            startActivity(intento);
            this.finish();
        } else if (opc == 4) {
            Intent intento = new Intent(this, SolicSaldoActivity.class);
            startActivity(intento);
            this.finish();
        } else if (opc == 5) {
            Intent intento = new Intent(this, ReportSaleSearchRecargaActivity.class);
            startActivity(intento);
            this.finish();
        }
    }


    private void msjDialogoWeb() {

        // WebView is created programatically here.
        WebView myWebView = new WebView(MenuPrincActivity.this);
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

        new AlertDialog.Builder(MenuPrincActivity.this).setView(myWebView)
                .setTitle("TRANSACCION EXITOSA")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {

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
                }).setCancelable(false)
                .show();

    }

    private void msjDialogoWebSoat(String msj) {

        WebView myWebView = new WebView(MenuPrincActivity.this);
        new AlertDialog.Builder(MenuPrincActivity.this).setView(myWebView)
                .setTitle("TRANSACCION EXITOSA")
                .setMessage(msj)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
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
                }).setCancelable(false)
                .show();
    }

}
