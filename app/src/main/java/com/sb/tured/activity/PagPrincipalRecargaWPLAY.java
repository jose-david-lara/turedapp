package com.sb.tured.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.model.HomeProductos;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

public class PagPrincipalRecargaWPLAY extends AppCompatActivity implements View.OnClickListener  {

    private utilitiesTuRedBD bd_data;
    private HomeProductos producto_home;
    private String tipo = "";
    private String nombre_categoria;
    private ImageView imagen_operador;
    private utilitiesClass utils_class;
    private TextView title_tool_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_recarga_wplay);
        initComponents();
        tipo = getIntent().getExtras().getString("id");
        nombre_categoria = getIntent().getExtras().getString("nombre_categoria");

        completeLayout();
    }

    private void initComponents (){
        //init_button = (Button)findViewById(R.id.buttonInit);
        imagen_operador = findViewById(R.id.logoProductoRecarga);
        producto_home = new HomeProductos();
        utils_class = new utilitiesClass();
        title_tool_bar = findViewById(R.id.tittle_toolbar);
        bd_data = new utilitiesTuRedBD(this);
    }

    private void completeLayout(){




        producto_home  = bd_data.getProductoHome(tipo);
        title_tool_bar.setText(producto_home.getOperador()+"-"+producto_home.getServicio());

       if(!producto_home.isEstado()){
           utils_class.msjsSimple(this,"Información",producto_home.getMsjBD(),"Aceptar","",0,1);
           nextActivity(2);
       }

        Picasso.get()
                .load(producto_home.getUrl_img())
                .transform(new RoundedTransformation(50, 1))
                .resize(180, 180)
                .centerCrop()
                .into(imagen_operador);

    }

    @Override
    public void onClick(View v) {
       /* if(v.getId() == init_button.getId()){
            nextActivity(2);
        }*/
    }

    public void nextActivity(int opc){
        Intent intento;
        switch (opc) {
            case 1:
                intento = new Intent(this, LoginActivity.class); // Lanzamos SiguienteActivity
                startActivity(intento);
                this.finish();
            break;
            case 2:
                intento = new Intent(this, LoginActivity.class); // Lanzamos SiguienteActivity
                startActivity(intento);
                this.finish();
                break;
        }
    }
}
