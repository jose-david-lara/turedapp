package com.sb.tured.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.sb.tured.R;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.model.CategoriasProductos;
import com.sb.tured.model.HomeProductos;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterListMenuPrincp;
import com.sb.tured.utilities.adapterListProduct;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MenuProductosActivity extends AppCompatActivity implements View.OnClickListener, interfacesModelo {


    TextView text_tittle;
    ImageView back_button;
    ImageView info_button;
    ImageView imagen_layout_back_local;
    Drawable aux_img = null;
    private RelativeLayout layout_menu_productos;
    private ImageView icono_rol_usuario;
    private HomeProductos objaux = new HomeProductos();
    private CategoriasProductos objauxCategoria = new CategoriasProductos();
    private int contCategorias;
    Context contexto = this;
    utilitiesTuRedBD utils_bd = new utilitiesTuRedBD(this);
    List home_productos_local;
    List categorias_productos_local;
    ArrayList<HomeProductos> listaMenuHome;
    ArrayList<CategoriasProductos> listaMenuCategorias;
    RecyclerView recyclerMenuHome;
    utilitiesClass utils_class;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_product);
        initComponents();
        llenarFondoLayout();
        actionevents();


        listMenuPrincipal();


        final adapterListProduct adapter = new adapterListProduct(this, listaMenuHome,listaMenuCategorias);

        recyclerMenuHome.setLayoutManager(new GridLayoutManager(this, 3));


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(recyclerMenuHome.getChildPosition(v) < listaMenuHome.size()) {
                    objaux = utils_bd.getProductoPackageHome(adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getOperador(),
                            adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getServicio());

                    nextActivity(0,"",adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getId(),
                            adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getId_producto(),
                            adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getOperador(),
                            adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getTabla(),
                            adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getUrl_img(),
                            adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getServicio(),
                            adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getProductos_id());
                }else{
                    contCategorias = listaMenuCategorias.size() - ((listaMenuHome.size() + listaMenuCategorias.size()) - recyclerMenuHome.getChildPosition(v));
                    nextActivity(1,adapter.listCategorias.get(contCategorias).getCategoria(),
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null);
                }

            }
        });
        recyclerMenuHome.setAdapter(adapter);


    }


    private void initComponents() {

        info_button = findViewById(R.id.buttonInfo);
        info_button.setVisibility(View.INVISIBLE);
        icono_rol_usuario = findViewById(R.id.iconoUsuarioRol);


        listaMenuHome = new ArrayList<>();
        listaMenuCategorias = new ArrayList<>();
        recyclerMenuHome = findViewById(R.id.RecyclerViewMenuProductos);

        utils_class = new utilitiesClass();

        //Titulo de tool bar
        text_tittle = findViewById(R.id.tittle_toolbar);
        text_tittle.setText("Recargas y Productos");

        back_button = findViewById(R.id.buttonBack);
        layout_menu_productos = findViewById(R.id.layoutMenuProductos);
        imagen_layout_back_local = new ImageView(this);
        contCategorias = 0;


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

        /*button_info.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    utils_class.msjsSimple(LoginActivity.this,"APLICACION TU RED","Desarrollada por SEIBIS SAS","Aceptar","");
                }
                return false;
            }
        });*/
    }

    void llenarFondoLayout() {


        Picasso.get()
                .load("https://multiservicios.madefor.work/assets/img/lateral.jpg")
                .resize(800, 800)
                .centerCrop()
                .into(imagen_layout_back_local);


        new AsyncTask<String, Integer, Drawable>() {

            @Override
            protected Drawable doInBackground(String... strings) {
                Bitmap bmp = null;
                int x = 30;

                while (x > 0) {
                    if (imagen_layout_back_local != null) {
                        break;
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    x--;
                }

                return new BitmapDrawable(bmp);
            }

            protected void onPostExecute(Drawable result) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout_menu_productos.setBackground(imagen_layout_back_local.getDrawable());
                }

            }


        }.execute();


    }

    private void listMenuPrincipal() {

        int contador = 0;
        int x = 0;


        Picasso.get()
                .load("https://multiservicios.madefor.work/assets/img/pdv.png")
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(icono_rol_usuario);
        //icono_rol_usuario = carga_imagenes.img.get(0);

        home_productos_local = utils_bd.getAllHomeProductos();
        categorias_productos_local = utils_bd.getCategoriasProductos();


        //WPLAY-SOAT-CERTIFICADOS
            /*x = 0;
            for ( x = 0; x < home_productos_local.size(); x++) {
                objaux = (HomeProductos) home_productos_local.get(x);
                if (Integer.parseInt(objaux.getId_producto()) > 1 && !objaux.getServicio().equals("PAQUETES")) {
                    if (objaux.getOperador().equals("WPLAY") && contador == 0) {
                        listaMenuHome.add((HomeProductos) home_productos_local.get(x));
                        contador++;
                    }
                }
            }*/




        //OPERADORES RECARGAS
        x = 0;
        for (x = 0; x < home_productos_local.size(); x++) {
            objaux = (HomeProductos) home_productos_local.get(x);
            if ( objaux.getServicio().equals("RECARGAS") && objaux.getCategoria().equals("RECARGAS") && !objaux.getServicio().equals("PAQUETES") && !objaux.getOperador().equals("WPLAY") && !objaux.getOperador().equals("BETJUEGO")) {
                listaMenuHome.add((HomeProductos) home_productos_local.get(x));
            } else if (objaux.getOperador().equals("CERTIFICADOS") && objaux.getServicio().equals("CERTIFICADOS")) {
                listaMenuHome.add((HomeProductos) home_productos_local.get(x));
            }/*else if (objaux.getOperador().equals("SOAT") && objaux.getServicio().equals("SOAT")) {
                listaMenuHome.add((HomeProductos) home_productos_local.get(x));
            }*/
        }

        //CATEGORIAS
        x = 0;
        for ( x = 0; x < categorias_productos_local.size(); x++) {
            objauxCategoria = (CategoriasProductos) categorias_productos_local.get(x);
            if (!objauxCategoria.getCategoria().equals("RECARGAS")) {

                listaMenuCategorias.add((CategoriasProductos) categorias_productos_local.get(x));

            }
        }

    }


    @Override
    public void onBackPressed() {


        Intent intento = new Intent(contexto, MenuPrincActivity.class);
        intento.putExtra("mensaje_saldo", "00");
        startActivity(intento);
        MenuProductosActivity.this.finish();


    }


    @Override
    public void onClick(View v) {
       /* if(v.getId() == back_button.getId()){
            onBackPressed();
        }else if(v.getId() == aux_button.getId()){
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            dialogo.setTitle("INFORMACION");
            dialogo.setMessage("Construccion...");
            dialogo.setCancelable(false);

            dialogo.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo, int id) {

                }
            });

        }*/
    }

    public void nextActivity(int opc,String nombre_categoria,String id, String id_producto, String operador, String tabla, String url_imagen, String servicio, String producto_id) {

        Intent intento;
        if(opc == 0) {
            if (Integer.parseInt(id_producto) >= 1 && !operador.equals("CERTIFICADOS") && !operador.equals("SOAT")) {
                intento = new Intent(this, PagPrincipalRecarga.class); // Lanzamos SiguienteActivity
                intento.putExtra("operador", operador);
                intento.putExtra("id", id);
                intento.putExtra("tabla", tabla);
                intento.putExtra("url_imagen", url_imagen);
                intento.putExtra("tipo", "3");
                if (operador.equals("WPLAY") || Integer.parseInt(id_producto) == 3 || tabla.equals("producto_multiservicios")) {
                    intento.putExtra("servicio", "MULTISERVICIOS");
                } else {
                    intento.putExtra("servicio", servicio);
                }
                intento.putExtra("id_producto", id_producto);
                intento.putExtra("nombre_categoria", nombre_categoria);
            } else if (operador.equals("CERTIFICADOS")) {
                intento = new Intent(this, CertificadosActivity.class); // Lanzamos SiguienteActivity//intento = new Intent(this, PagPrincipalRecargaWPLAY.class); // Lanzamos SiguienteActivity
                intento.putExtra("url_imagen", url_imagen);
                intento.putExtra("id_producto", id_producto);
                intento.putExtra("operador", operador);
                intento.putExtra("servicio", servicio);
                intento.putExtra("nombre_categoria", nombre_categoria);
            } else if (operador.equals("SOAT")) {
                intento = new Intent(this, SoatActivity.class); // Lanzamos SiguienteActivity//intento = new Intent(this, PagPrincipalRecargaWPLAY.class); // Lanzamos SiguienteActivity
                intento.putExtra("operador", operador);
                intento.putExtra("servicio", servicio);
                intento.putExtra("id", id);
                intento.putExtra("producto_id", producto_id);
                intento.putExtra("nombre_categoria", nombre_categoria);
            } else {
                intento = new Intent(this, ProductInactiveActivity.class); // Lanzamos SiguienteActivity
                intento.putExtra("id", id);
            }
        }else{
            intento = new Intent(this, ProductosCategoriasActivity.class); // Lanzamos SiguienteActivity
            intento.putExtra("nombre_categoria", nombre_categoria);
        }

        startActivity(intento);
        this.finish();

    }


}
