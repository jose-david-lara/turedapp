package com.sb.tured.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.model.BalanceProductos;
import com.sb.tured.model.CategoriasProductos;
import com.sb.tured.model.PackOperRecarga;
import com.sb.tured.model.ServOperPackRecarga;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterPackOperRecarg;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.sb.tured.interfaces.interfacesModelo.carga_imagenes;

public class PagPrincipalRecarga extends AppCompatActivity implements View.OnClickListener {

    private utilitiesTuRedBD bd_data;
    private String operador = "";
    private String url_imagen = "";
    private ImageView imagen_operador;
    private utilitiesClass utils_class;
    private TextView title_tool_bar;
    private TextView texto_operador_info;
    private Button button_paquetes;
    private Button button_recarga;
    private ImageView back_button;
    private ImageView button_info;
    private ImageView icono_rol_usuario;
    private ArrayList<PackOperRecarga> listaPackOperRecar;
    private PackOperRecarga objaux;
    private String tabla;
    private String id;
    private String servicio;
    private String id_producto;
    private String id_recargas;
    private BalanceProductos balance_productos_usuario;
    private RelativeLayout layout_menu_productos;
    private ImageView imagen_layout_back;
    private String nombre_categoria;



    //private Button button_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_recarga);
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
        nombre_categoria = getIntent().getExtras().getString("nombre_categoria");


        completeLayout();
        actionevents();

    }

    private void initComponents (){

        imagen_operador = findViewById(R.id.logoProductoRecarga);
        utils_class = new utilitiesClass();
        title_tool_bar = findViewById(R.id.tittle_toolbar);
        bd_data = new utilitiesTuRedBD(this);
        back_button = findViewById(R.id.buttonBack);
        button_info = findViewById(R.id.buttonInfo);
        icono_rol_usuario = findViewById(R.id.iconoUsuarioRol);
        listaPackOperRecar = new ArrayList<>();
        objaux = new PackOperRecarga();
        button_paquetes = findViewById(R.id.buttonRecargaPaquete);
        button_recarga = findViewById(R.id.buttonRecarga);
        balance_productos_usuario = new BalanceProductos();
        texto_operador_info = findViewById(R.id.textOperadorInfo);
        layout_menu_productos = findViewById(R.id.activity_relative_layout_producto_recarga);
        imagen_layout_back = new ImageView(this);
    }

    private void completeLayout(){
        Picasso.get()
                .load("https://multiservicios.madefor.work/assets/img/lateral.jpg")
                .resize(800, 800)
                .centerCrop()
                .into(imagen_layout_back);
        //imagen_layout_back = carga_imagenes.img.get(1);

        new AsyncTask<String, Integer, Drawable>(){

            @Override
            protected Drawable doInBackground(String... strings) {
                Bitmap bmp = null;
                int x = 30;

                while(x > 0) {
                    if (imagen_layout_back != null) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    x--;
                }

                return new BitmapDrawable(bmp);
            }

            protected void onPostExecute(Drawable result) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout_menu_productos.setBackground(imagen_layout_back.getDrawable());
                }

            }


        }.execute();

        title_tool_bar.setText(operador);

        balance_productos_usuario = bd_data.getProductoBalance(servicio);


        if(!title_tool_bar.getText().toString().equals("CERTIFICADOS")) {
            texto_operador_info.setText("Saldo: $" + balance_productos_usuario.getSaldo());
        }

        Picasso.get()
                .load(url_imagen)
                .transform(new RoundedTransformation(50, 1))
                .resize(180, 180)
                .centerCrop()
                .into(imagen_operador);


    }

    @Override
    public void onClick(View v) {

    }

    private void actionevents (){
        back_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onBackPressed();
                }
                return false;
            }
        });

        button_paquetes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    nextActivity(1);
                }
                return false;
            }
        });

        button_recarga.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    nextActivity(3);
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

    public void nextActivity(int opc){
        Intent intento;
        switch (opc) {
            case 1:
                intento = new Intent(this, PagPrincipalRecargaPaquetes.class); // Lanzamos SiguienteActivity
                intento.putExtra("operador",operador);
                intento.putExtra("id",id);
                intento.putExtra("tabla",tabla);
                intento.putExtra("url_imagen",url_imagen);
               if(!nombre_categoria.equals("PINES") && !nombre_categoria.equals("APUESTAS"))
                intento.putExtra("servicio","PAQUETES");
               else
                   intento.putExtra("servicio",nombre_categoria);
                intento.putExtra("id_producto",id_producto);
                intento.putExtra("id_recargas",id_recargas);
                intento.putExtra("saldo",texto_operador_info.getText().toString());
                intento.putExtra("nombre_categoria",nombre_categoria);
                startActivity(intento);
                this.finish();
                break;
            case 2:
               if(!nombre_categoria.equals("PINES") && !nombre_categoria.equals("APUESTAS")) {
                    intento = new Intent(this, MenuProductosActivity.class); // Lanzamos SiguienteActivity
                }else {
                    intento = new Intent(this, ProductosCategoriasActivity.class);
                    intento.putExtra("nombre_categoria", nombre_categoria);
                }
                startActivity(intento);
                this.finish();
                break;
            case 3:
                intento = new Intent(this, PagPrincipalRecargaValoresPredert.class); // Lanzamos SiguienteActivity
                intento.putExtra("operador",operador);
                intento.putExtra("id",id);
                intento.putExtra("tabla",tabla);
                intento.putExtra("url_imagen",url_imagen);
                intento.putExtra("servicio","RECARGAS");
                intento.putExtra("id_producto",id_producto);
                intento.putExtra("id_recargas",id_recargas);
                intento.putExtra("saldo",texto_operador_info.getText().toString());
                intento.putExtra("nombre_categoria",nombre_categoria);
                startActivity(intento);
                this.finish();
                break;
        }
    }
}
