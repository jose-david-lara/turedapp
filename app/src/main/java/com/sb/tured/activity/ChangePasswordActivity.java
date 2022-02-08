package com.sb.tured.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.sb.tured.R;
import com.sb.tured.controller.ValidateJson;
import com.sb.tured.interfaces.interfaceOnResponse;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.io.ConsumeServicesExpress;
import com.sb.tured.utilities.DatosMovil;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.utilitiesClass;
import com.squareup.picasso.Picasso;


public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener, interfacesModelo {

    Button button_change_password;
    Button button_change_cancel_password;
    ImageView back_button;
    ImageView button_info;
    ImageView icono_rol_usuario;
    ProgressDialog progressDialog;
    TextView text_tittle;
    EditText capPasswordOld;
    EditText capPasswordNew;
    EditText capPasswordNewConf;
    utilitiesClass utils_class = new utilitiesClass();
    DatosMovil data_movil;
    ValidateJson validate_user;
    String mensaje_body_change_password;
    TextView body_change_password;
    private String msj = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        initComponents();


        body_change_password.setText(mensaje_body_change_password);
        Picasso.get()
                .load("https://multiservicios.madefor.work/assets/img/pdv.png")
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(icono_rol_usuario);
        //icono_rol_usuario = interfacesModelo.carga_imagenes.img.get(0);


        back_button.setVisibility(View.INVISIBLE);

    }

    private void initComponents() {
        try {
            button_change_password = findViewById(R.id.buttonActPassword);
            button_change_cancel_password = findViewById(R.id.buttonCancelActPassword);
            back_button = findViewById(R.id.buttonBack);
            button_info = findViewById(R.id.buttonInfo);

            data_movil = new DatosMovil();

            icono_rol_usuario = findViewById(R.id.iconoUsuarioRol);

            //Titulo de tool bar
            text_tittle = findViewById(R.id.tittle_toolbar);
            text_tittle.setText("Cambio Contraseña");

            //Captura de datos
            capPasswordOld = findViewById(R.id.capPassActual);
            capPasswordNew = findViewById(R.id.capPassNew);
            capPasswordNewConf = findViewById(R.id.capPassNewConfirm);
            body_change_password = findViewById(R.id.textChangePassword);
            capPasswordOld.setText("");
            capPasswordNew.setText("");
            capPasswordNewConf.setText("");
            progressDialog = new ProgressDialog(this);
            validate_user = new ValidateJson();
            mensaje_body_change_password = getIntent().getExtras().getString("mensaje_body").replace("\"","");
        } catch (Exception e) {

        }


    }


    @Override
    public void onClick(View v) {

        if (v.getId() == button_change_password.getId()) {
            if (capPasswordOld.getText().length() == 0 || capPasswordNew.getText().length() == 0 || capPasswordNewConf.getText().length() == 0) {
                utils_class.msjsSimple(ChangePasswordActivity.this, "INFORMCAION", "Por favor llenar todos los campos", "Aceptar", "", 0,1);
            } else {

                if (capPasswordNew.getText().toString().equals(capPasswordNewConf.getText().toString())) {
                    data_user.setPassword_old(data_user.getPassword());
                    data_user.setPassword(capPasswordNew.getText().toString());
                    progressDialog.setMessage("Consultando...");
                    progressDialog.show();
                    new ConsumeServicesExpress().consume_api(10, new interfaceOnResponse() {
                        @Override
                        public void finish_consume_services() {
                            finish_process();
                        }

                        @Override
                        public void solicit_token_error_services(String mensaje_token) {
                            progressDialog.dismiss();
                            //json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
                            //utils_class.msjs_opciones_simple(ChangePasswordActivity.this,"INFORMACION", json_object_response.getJson_response_data().get("message").getAsString(), "Aceptar", "");
                            Intent intento = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            intento.putExtra("mensaje_inicio", mensaje_token);
                            startActivity(intento);
                            ChangePasswordActivity.this.finish();
                        }

                        @Override
                        public void finish_fail_consume_services(int codigo) {
                            progressDialog.dismiss();
                            if(codigo == 999){
                                utils_class.msjsSimple(ChangePasswordActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo INTERNET " + codigo, "Aceptar", "", 0, 1);
                            }else {
                                utils_class.msjsSimple(ChangePasswordActivity.this, "INFORMACION SERVICIO", "Error en el servicio codigo " + codigo, "Aceptar", "", 0, 1);
                            }
                            return;
                        }
                    },this);

                } else {
                    utils_class.msjsSimple(ChangePasswordActivity.this, "INFORMCAION", "La nueva contraseña y la confirmación de la contraseña nueva son diferentes", "Aceptar", "", 0,1);
                }
            }
        } else if (v.getId() == button_change_cancel_password.getId()) {
            onBackPressed();
        }
    }

    private boolean finish_process() {


        progressDialog.dismiss();
        msj = validate_user.data_cambio_password(this);
        if (!json_object_response.getJson_object_response().get("code").getAsString().equals("0")) {
            utils_class.msjsSimple(this, "INFORMACION", msj, "Aceptar", "", 0,1);
            return false;
        } else {
            utils_class.msjsSimple(this, "INFORMACION", msj, "Aceptar", "", 0,1);
            data_user.setPassword_old("");
            Intent intento = new Intent(this, ProcessingActivity.class); // Lanzamos SiguienteActivity
            intento.putExtra("tipo", "1");
            startActivity(intento);
            this.finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intento = new Intent(this, LoginActivity.class);
        startActivity(intento);
        ChangePasswordActivity.this.finish();
    }


}
