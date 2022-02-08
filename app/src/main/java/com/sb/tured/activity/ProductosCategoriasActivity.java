package com.sb.tured.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.model.BalanceProductos;
import com.sb.tured.model.CategoriasProductos;
import com.sb.tured.model.HomeProductos;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterListProduct;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductosCategoriasActivity extends AppCompatActivity implements View.OnClickListener, interfacesModelo {


    TextView text_tittle;
    ImageView back_button;
    ImageView info_button;
    ImageView imagen_layout_back_local;
    Drawable aux_img = null;
    private RelativeLayout layout_menu_productos;
    private ImageView icono_rol_usuario;
    private HomeProductos objaux = new HomeProductos();
    private CategoriasProductos objauxCategoria = new CategoriasProductos();
    private String nombre_categoria;
    private BalanceProductos balance_productos_usuario;
    private String id_recargas;
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
        nombre_categoria = getIntent().getExtras().getString("nombre_categoria");
        initComponents();
        llenarFondoLayout();
        actionevents();


        listMenuPrincipal();


        final adapterListProduct adapter = new adapterListProduct(this, listaMenuHome,null);

        recyclerMenuHome.setLayoutManager(new GridLayoutManager(this, 3));


        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                objaux = utils_bd.getProductoPackageHome(adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getOperador(),
                        adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getServicio());

                nextActivity(adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getId(),
                        adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getId_producto(),
                        adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getOperador(),
                        adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getTabla(),
                        adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getUrl_img(),
                        adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getServicio(),
                        adapter.listProduct.get(recyclerMenuHome.getChildPosition(v)).getProductos_id());


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
        text_tittle.setText(nombre_categoria);

        back_button = findViewById(R.id.buttonBack);
        layout_menu_productos = findViewById(R.id.layoutMenuProductos);
        imagen_layout_back_local = new ImageView(this);



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



        for ( x = 0; x < home_productos_local.size(); x++) {
            objaux = (HomeProductos) home_productos_local.get(x);
            if (objaux.getCategoria().equals(nombre_categoria) ) {
                listaMenuHome.add((HomeProductos) home_productos_local.get(x));
            }
        }


    }


    @Override
    public void onBackPressed() {
        Intent intento = new Intent(this, MenuProductosActivity.class);
        startActivity(intento);
        this.finish();

    }


    @Override
    public void onClick(View v) {
    }

    public void nextActivity(String id, String id_producto, String operador, String tabla, String url_imagen, String servicio, String producto_id) {
        Intent intento;
        if (Integer.parseInt(id_producto) >= 1 && !operador.equals("CERTIFICADOS") && !operador.equals("SOAT")) {

            balance_productos_usuario = utils_bd.getProductoBalance("MULTISERVICIOS");
            if (nombre_categoria.equals("PINES"))
                intento = new Intent(this, PagPrincipalRecargaPaquetes.class); // Lanzamos SiguienteActivity
            else  if (nombre_categoria.equals("APUESTAS"))
                intento = new Intent(this, PagPrincipalRecargaValoresPredert.class); // Lanzamos SiguienteActivity
            else {
                intento = new Intent(this, PagPrincipalRecarga.class); // Lanzamos SiguienteActivity
                intento.putExtra("saldo", balance_productos_usuario.getSaldo());
            }


            /*intento.putExtra("operador",operador);
            intento.putExtra("id",id);
            intento.putExtra("tabla",tabla);
            intento.putExtra("url_imagen",url_imagen);
            intento.putExtra("servicio","RECARGAS");
            intento.putExtra("id_producto",id_producto);
            intento.putExtra("id_recargas",id_recargas);
            intento.putExtra("saldo",texto_operador_info.getText().toString());
            intento.putExtra("nombre_categoria",nombre_categoria);*/




            intento.putExtra("operador", operador);
            intento.putExtra("id", id);
            intento.putExtra("tabla", tabla);
            intento.putExtra("url_imagen", url_imagen);
            if (!nombre_categoria.equals("PINES") && !nombre_categoria.equals("APUESTAS")){
                intento.putExtra("servicio", "PAQUETES");
            }else {
                if(nombre_categoria.equals("PINES"))
                    intento.putExtra("servicio", nombre_categoria);
                else{
                    if (operador.equals("WPLAY") || Integer.parseInt(id_producto) == 3 || tabla.equals("producto_multiservicios")) {
                        intento.putExtra("servicio", "RECARGAS");
                    } else {
                        intento.putExtra("servicio", servicio);
                    }
                }
            }
            intento.putExtra("id_producto",id_producto);
            intento.putExtra("id_recargas",id_recargas);
            intento.putExtra("nombre_categoria",nombre_categoria);
            startActivity(intento);
            this.finish();
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
            intento.putExtra("producto_id",producto_id);
            intento.putExtra("nombre_categoria", nombre_categoria);
        } else {
            intento = new Intent(this, ProductInactiveActivity.class); // Lanzamos SiguienteActivity
            intento.putExtra("id", id);
        }


        startActivity(intento);
        this.finish();

    }


}
