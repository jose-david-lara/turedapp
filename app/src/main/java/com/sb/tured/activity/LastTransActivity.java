package com.sb.tured.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.activity.frame.BuyReportFrameActivity;
import com.sb.tured.activity.frame.LastFiveFrameActivity;
import com.sb.tured.activity.frame.LastReportFrameActivity;
import com.sb.tured.activity.frame.WalletReportFrameActivity;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.TabViewPagerAdapter;
import com.squareup.picasso.Picasso;

public class LastTransActivity extends AppCompatActivity implements View.OnClickListener  {

    private ImageView button_info;
    private ImageView button_back;
    private ImageView icono_rol_usuario;
    private TextView tittle_toolbar_local;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_trans);
        initComponents();

        Picasso.get()
                .load("https://multiservicios.madefor.work/assets/img/pdv.png")
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(icono_rol_usuario);
       // icono_rol_usuario = interfacesModelo.carga_imagenes.img.get(0);


        button_info.setVisibility(View.INVISIBLE);
        tittle_toolbar_local.setText("Transacciones");
        actionevents();

    }

    private void initComponents (){

        button_info = findViewById(R.id.buttonInfo);
        button_back = findViewById(R.id.buttonBack);
        icono_rol_usuario = findViewById(R.id.iconoUsuarioRol);

        //objaux = new TransactionsLast();
        tittle_toolbar_local = findViewById(R.id.tittle_toolbar);

        //Frames
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        setUpViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);

        tabLayout.setupWithViewPager(viewPager);

        //recyclerLastFiveTrans.setLayoutManager(new LinearLayoutManager(this));

    }




    private void setUpViewPager(ViewPager viewPager){
        TabViewPagerAdapter tabViewPagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        tabViewPagerAdapter.addFragment(new LastFiveFrameActivity(), "5 Ultimas", LastTransActivity.this);
        tabViewPagerAdapter.addFragment(new LastReportFrameActivity(), "Ventas", LastTransActivity.this);
        tabViewPagerAdapter.addFragment(new BuyReportFrameActivity(), "Compra",LastTransActivity.this);
        tabViewPagerAdapter.addFragment(new WalletReportFrameActivity(), "Cartera", LastTransActivity.this);

        this.viewPager.setAdapter(tabViewPagerAdapter);
    }


    @Override
    public void onClick(View v) {

    }

    private void actionevents (){
        button_back.setOnTouchListener(new View.OnTouchListener() {
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
    public void onBackPressed() {
        nextActivity();
    }


    public void nextActivity(){
        Intent intento = new Intent(this, MenuPrincActivity.class); // Lanzamos SiguienteActivity
        intento.putExtra("mensaje_saldo","00");
        startActivity(intento);
        this.finish();
    }


}
