package com.sb.tured.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.model.ServOperPackRecarga;
import com.sb.tured.model.Usuario;
import com.sb.tured.model.ValueDefaultOperRecarga;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.adapterValueDefaultOperRecarg;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.sb.tured.interfaces.interfacesModelo.carga_imagenes;
import static com.sb.tured.interfaces.interfacesModelo.data_user;
import static com.sb.tured.interfaces.interfacesModelo.recarga_servicio;

public class PagPrincipalRecargaConfirmar extends AppCompatActivity implements View.OnClickListener  {


    private String operador = "";
    private String url_imagen = "";
    private ImageView imagen_operador;
    private TextView title_tool_bar;
    private ImageView back_button;
    private ImageView button_info;
    private ImageView icono_rol_usuario;
    private ArrayList<ValueDefaultOperRecarga> listaPackOperRecar;
    private ValueDefaultOperRecarga objaux;
    private String tabla;
    private String id;
    private String servicio;
    private String referencia;
    private String valor;
    private String paquete;
    private String id_producto;
    private String id_recargas;
    private String id_monto;
    private String id_servicio;
    private String saldo;
    private String nombre_categoria;
    private Button boton_cancelar;
    private Button boton_enviar_Recarga;
    private TextView text_num_recarga;
    private TextView text_valor_recarga;
    private TextView text_descripcion_recarga;
    private TextView text_confirmacion_recarga;
    private TextView descripcion_paquete_recarga;
    private CheckBox chbox_button_bolsa;
    private utilitiesClass utils_class;
    private Usuario usuario_info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_recarga_confirmar);
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
        referencia = getIntent().getExtras().getString("referencia");
        valor = getIntent().getExtras().getString("valor");
        paquete = getIntent().getExtras().getString("paquete");
        id_producto = getIntent().getExtras().getString("id_producto");
        id_recargas = getIntent().getExtras().getString("id_recargas");
        id_monto = getIntent().getExtras().getString("id_monto");
        id_servicio = getIntent().getExtras().getString("id_servicio");
        saldo = getIntent().getExtras().getString("saldo");
        nombre_categoria = getIntent().getExtras().getString("nombre_categoria");

        completeLayout();
        actionevents();



    }

    private void initComponents (){

        imagen_operador = findViewById(R.id.logoProductoRecarga);
        title_tool_bar = findViewById(R.id.tittle_toolbar);
        back_button = findViewById(R.id.buttonBack);
        button_info = findViewById(R.id.buttonInfo);
        icono_rol_usuario = findViewById(R.id.iconoUsuarioRol);
        objaux = new ValueDefaultOperRecarga();
        boton_cancelar = findViewById(R.id.buttonCancelarRecarga);
        text_num_recarga = findViewById(R.id.textNumeroRecarga);
        text_valor_recarga = findViewById(R.id.textNumeroValor);
        text_descripcion_recarga = findViewById(R.id.textDescripcionRecarga);
        text_confirmacion_recarga = findViewById(R.id.textConfRecarga);
        boton_enviar_Recarga = findViewById(R.id.buttonEnviarRecarga);
        descripcion_paquete_recarga = findViewById(R.id.textDescripcionPaquete);
        chbox_button_bolsa = findViewById(R.id.chBoxOpcOne);
        utils_class = new utilitiesClass();
        usuario_info = new Usuario();
    }

    private void completeLayout(){

        if(paquete != null){
            descripcion_paquete_recarga.setText(paquete);
        }else{
            descripcion_paquete_recarga.setVisibility(View.GONE);
        }

        text_confirmacion_recarga.setText("CONFIRMACION DE RECARGA");
        if(operador.equals("WPLAY") || Integer.parseInt(id_producto) == 3){
            text_num_recarga.setText("Cedula: " + referencia);
        }else {
            text_num_recarga.setText("Numero: " + referencia);
        }
        text_valor_recarga.setText("Valor: "+valor);


        ValueDefaultOperRecarga recargaLocal = new ValueDefaultOperRecarga();
        recargaLocal.setDescripcion("Ingresar Valor");
        recargaLocal.setId("99");
        title_tool_bar.setText(operador);
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

        boton_cancelar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    nextActivity(2);
                    /*if(servicio.equals("RECARGAS")) {
                        nextActivity(2);
                    }else if(servicio.equals("SERVICIOS")) {
                        nextActivity(3);
                    }*/

                }
                return false;
            }
        });

        boton_enviar_Recarga.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // utils_class.msjsSimple(PagPrincipalRecargaConfirmar.this,"INFORMACION","Servicio de recargas en construccion","Aceptar","");
                    nextActivity(1);
                }
                return false;
            }
        });



    }

    @Override
    public void onBackPressed() {
        nextActivity(2);
    }

    public void nextActivity(int opc){
        Intent intento;
        switch (opc) {
            case 1:
                recarga_servicio.setPosx("0");
                recarga_servicio.setPosy("0");
                recarga_servicio.setProducto(id_producto);//falta
                recarga_servicio.setReferencia(referencia);
                recarga_servicio.setMonto(valor.replace(",",""));
                recarga_servicio.setIdmonto(id_monto);//falta
                recarga_servicio.setServicio_id(id_servicio);
                recarga_servicio.setId_recargas(id_recargas);

                usuario_info = utils_class.obtenerInfoUsuario(this);
                if(usuario_info == null){
                    utils_class.msjsSimple(this, "Información", "No es posible obtener informacion del token", "Aceptar", "", 0, 1);
                    return;
                }

                recarga_servicio.setToken( usuario_info.getToken());
                //recarga_servicio.setToken( ((Usuario) new utilitiesTuRedBD(this).getUsuarioInfo(data_user.getEmail())).getToken());
                //recarga_servicio.setToken(data_user.getToken());
                if(chbox_button_bolsa.isChecked()){
                    recarga_servicio.setBolsa_venta("ganancias");
                }else{
                    recarga_servicio.setBolsa_venta("");
                }
                intento = new Intent(this, ProcessingActivity.class); // Lanzamos SiguienteActivity0
                intento.putExtra("tipo","4");
                intento.putExtra("saldo",saldo);
                startActivity(intento);
                this.finish();
                break;
            case 2:
            case 3:
                if(nombre_categoria.equals("PINES") ) {
                    intento = new Intent(this, PagPrincipalRecargaPaquetes.class); // Lanzamos SiguienteActivity
                    intento.putExtra("saldo",saldo);
                    intento.putExtra("servicio",nombre_categoria);
                }else if(nombre_categoria.equals("APUESTAS") ) {
                    intento = new Intent(this, PagPrincipalRecargaValoresPredert.class); // Lanzamos SiguienteActivity
                    intento.putExtra("servicio","RECARGAS");//
                    intento.putExtra("saldo",saldo);//
                }else {
                    intento = new Intent(this, PagPrincipalRecarga.class); // Lanzamos SiguienteActivity
                    intento.putExtra("servicio","RECARGAS");
                }

                intento.putExtra("operador",operador);
                intento.putExtra("id",id);
                intento.putExtra("url_imagen",url_imagen);
                intento.putExtra("tabla",tabla);
                intento.putExtra("id_producto",id_producto);
                intento.putExtra("id_recargas",id_recargas);
                intento.putExtra("nombre_categoria", nombre_categoria);
                startActivity(intento);
                this.finish();
                break;
        }
    }
}
