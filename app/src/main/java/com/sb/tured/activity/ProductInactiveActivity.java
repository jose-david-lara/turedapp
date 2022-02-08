package com.sb.tured.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.model.HomeProductos;
import com.sb.tured.utilities.utilitiesTuRedBD;

public class ProductInactiveActivity extends AppCompatActivity implements View.OnClickListener  {

    private ImageView back_button;
    private TextView title_tool_bar;
    private Button button_back_activity;
    private utilitiesTuRedBD bd_data;
    private HomeProductos producto_home;
    private String tipo ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_inactive);
        initComponents();
        actionevents();
        tipo = getIntent().getExtras().getString("id");

        producto_home  = bd_data.getProductoHome(tipo);
        title_tool_bar.setText(producto_home.getOperador()+"-"+producto_home.getServicio());
    }

    private void initComponents (){

        button_back_activity = findViewById(R.id.buttonBackActivity);
        back_button = findViewById(R.id.buttonBack);
        title_tool_bar = findViewById(R.id.tittle_toolbar);
        bd_data = new utilitiesTuRedBD(this);
        producto_home = new HomeProductos();
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

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        nextActivity();
    }

    public void nextActivity(){
        Intent intento = new Intent(this, MenuProductosActivity.class); // Lanzamos SiguienteActivity
        startActivity(intento);
        this.finish();
    }
}
