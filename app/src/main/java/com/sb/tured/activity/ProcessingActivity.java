package com.sb.tured.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sb.tured.R;
import com.sb.tured.controller.ValidateJson;
import com.sb.tured.interfaces.interfaceOnResponse;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.io.ConsumeServicesExpress;
import com.sb.tured.model.HomeProductos;
import com.sb.tured.utilities.RoundedTransformation;
import com.sb.tured.utilities.utilalpsprinter.iposprinterservice.IPosPrinterCallback;
import com.sb.tured.utilities.utilalpsprinter.iposprinterservice.IPosPrinterService;
import com.sb.tured.utilities.utilalpsprinter.printer.IPosPrinterTestDemo;
import com.sb.tured.utilities.utilalpsprinter.printer.ThreadPoolManager;
import com.sb.tured.utilities.utilalpsprinter.printer.Utils.HandlerUtils;
import com.sb.tured.utilities.utilitiesClass;
import com.sb.tured.utilities.utilitiesTuRedBD;
import com.sb.tured.utilities.utilssunmiprinter.printerSunmiV1;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;


public class ProcessingActivity extends AppCompatActivity implements interfacesModelo {


    private ProgressBar progressBar;
    private TextView text_one;
    private ValidateJson validate_user = new ValidateJson();
    private int tipo = 0;
    private String id = "";
    private String operador = "";
    private String tabla = "";
    private String url_imagen = "";
    private JsonObject json_aux;
    private String servicio;
    private String saldo;
    private String mensaje_recarga;
    private String mensaje_body_change_password;
    private AsyncTask<Void, Void, Void> hilo;
    private List home_productos_pack_local;
    private utilitiesTuRedBD utils_bd = new utilitiesTuRedBD(this);
    private HomeProductos objaux = new HomeProductos();
    private utilitiesClass utils_class;
    private int x = 0;
    private String mensaja_red = "";
    private printerSunmiV1 printer_sunmi_v1 = new printerSunmiV1();


    //variables test print
    public IPosPrinterCallback callback = null;
    public HandlerUtils.MyHandler handler;
    public static final String TAG = "IPosPrinterTestDemo";
    public int printerStatus = 0;
    private final String PRINTER_NORMAL_ACTION = "com.iposprinter.iposprinterservice.NORMAL_ACTION";
    private final String PRINTER_PAPERLESS_ACTION = "com.iposprinter.iposprinterservice.PAPERLESS_ACTION";
    private final String PRINTER_PAPEREXISTS_ACTION = "com.iposprinter.iposprinterservice.PAPEREXISTS_ACTION";
    private final String PRINTER_THP_HIGHTEMP_ACTION = "com.iposprinter.iposprinterservice.THP_HIGHTEMP_ACTION";
    private final String PRINTER_THP_NORMALTEMP_ACTION = "com.iposprinter.iposprinterservice.THP_NORMALTEMP_ACTION";
    private final String PRINTER_MOTOR_HIGHTEMP_ACTION = "com.iposprinter.iposprinterservice.MOTOR_HIGHTEMP_ACTION";
    private final String PRINTER_BUSY_ACTION = "com.iposprinter.iposprinterservice.BUSY_ACTION";
    private final String PRINTER_CURRENT_TASK_PRINT_COMPLETE_ACTION = "com.iposprinter.iposprinterservice.CURRENT_TASK_PRINT_COMPLETE_ACTION";
    private final String GET_CUST_PRINTAPP_PACKAGENAME_ACTION = "android.print.action.CUST_PRINTAPP_PACKAGENAME";

    public IPosPrinterService mIPosPrinterService;

    private ServiceConnection connectService = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIPosPrinterService  =  IPosPrinterService.Stub.asInterface(service);
        }

        public void onServiceDisconnected(ComponentName name) {
            mIPosPrinterService = null;
        }
    };

    private HandlerUtils.IHandlerIntent iHandlerIntent = new HandlerUtils.IHandlerIntent() {
        @SuppressLint("WrongConstant")
        public void handlerIntent(Message msg) {
            switch (msg.what) {
                case 3:
                    Toast.makeText(ProcessingActivity.this, R.string.printer_is_working, 0).show();
                    return;
                case 4:
                    Toast.makeText(ProcessingActivity.this, R.string.out_of_paper, 0).show();
                    return;
                case 5:
                    Toast.makeText(ProcessingActivity.this, R.string.exists_paper, 0).show();
                    return;
                case 6:
                    Toast.makeText(ProcessingActivity.this, R.string.printer_high_temp_alarm, 0).show();
                    return;
                case 8:
                    Toast.makeText(ProcessingActivity.this, R.string.motor_high_temp_alarm, 0).show();
                    handler.sendEmptyMessageDelayed(9, 180000);
                    return;
                case 9:
                    printerInit();
                    return;
                case 10:
                    Toast.makeText(ProcessingActivity.this, R.string.printer_current_task_print_complete, 0).show();
                    return;
                default:
                    return;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);


        tipo = Integer.parseInt(getIntent().getExtras().getString("tipo"));
        id = getIntent().getExtras().getString("id");
        if (id != null) {
            operador = getIntent().getExtras().getString("operador");
            tabla = getIntent().getExtras().getString("tabla");
            url_imagen = getIntent().getExtras().getString("url_imagen");
            servicio = getIntent().getExtras().getString("servicio");
            if (tipo == 4) {
                saldo = getIntent().getExtras().getString("saldo");
            }
            home_productos.setId(id);
            home_productos.setTabla(tabla);
        }

        utils_class = new utilitiesClass();


        text_one = findViewById(R.id.textOne);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(1000);
        progressBar.setVisibility(ProgressBar.VISIBLE);


        if (id != null) {
            text_one.setText("Cargando Parametros Operador " + operador);
        } else if (tipo != 4 && tipo != 5) {
            text_one.setText("Autenticando");
        } else {
            text_one.setText("Solicitando Recarga");
        }

        mensaja_red = utils_class.informacion_red(this);
        if (mensaja_red.length() > 0 && !interfacesModelo.informacion_mobil.isRed_conectada()) {
            tipo = 99;
            Intent intento = new Intent(this, MenuPrincActivity.class); // Lanzamos SiguienteActivity
            intento.putExtra("mensaje_saldo", mensaja_red);
            startActivity(intento);
            this.finish();
            return;
            //utils_class.msjsSimple(ProcessingActivity.this, "INFORMACION SERVICIO", mensaja_red, "Aceptar", "", 0,1);
        }
        transaccional_servicios();


        if(Build.MANUFACTURER.equals("9220")) {
            inicializarImpresion(0);
        }else if (Build.MANUFACTURER.equals("SUNMI"))
            inicializarImpresion(1);

    }


    public void transaccional_servicios() {
        progressBar.setVisibility(View.VISIBLE);


        new ConsumeServicesExpress().consume_api(tipo, new interfaceOnResponse() {
            @Override
            public void finish_consume_services() {
                validar_respuesta();
            }

            @Override
            public void solicit_token_error_services(String mensaje_token) {
                progressBar.setVisibility(View.GONE);
                //json_object_response.setJson_response_data((JsonObject) json_object_response.getJson_object_response().get("data"));
                //utils_class.msjs_opciones_simple(ProcessingActivity.this,"INFORMACION", json_object_response.getJson_response_data().get("message").getAsString(), "Aceptar", "");
                Intent intento = new Intent(ProcessingActivity.this, LoginActivity.class);
                intento.putExtra("mensaje_inicio", mensaje_token);
                startActivity(intento);
                ProcessingActivity.this.finish();
            }

            @Override
            public void finish_fail_consume_services(int codigo) {
                progressBar.setVisibility(View.INVISIBLE);
                Intent intento = null;
                if(tipo == 1) {
                    intento = new Intent(ProcessingActivity.this, LoginActivity.class); // Lanzamos SiguienteActivity
                }else{
                    intento = new Intent(ProcessingActivity.this, MenuPrincActivity.class); // Lanzamos SiguienteActivity
                }
                if(codigo == 999) {
                    if(tipo == 1) {
                        intento.putExtra("mensaje_inicio", "Error en el servicio codigo INTERNET " + codigo + ". Por favor revisar conexiones a internet.");
                    }else {
                        intento.putExtra("mensaje_saldo", "Error en el servicio codigo INTERNET " + codigo + ". Por favor revisar conexiones a internet.");
                    }
                } else {
                    if(tipo == 1) {
                        intento.putExtra("mensaje_inicio", "Error en el servicio codigo " + codigo + ". Por favor revisar conexiones a internet.");
                    }else {
                        intento.putExtra("mensaje_saldo", "Error en el servicio codigo " + codigo + ". Por favor revisar conexiones a internet.");
                    }
                }
                startActivity(intento);
                ProcessingActivity.this.finish();
            }

        },this);


    }

    public void validar_respuesta() {
        switch (tipo) {
            case 1:
                if (json_object_response.getJson_object_response() == null) {
                    Toast.makeText(ProcessingActivity.this, "Error en el servicio de autenticacion", Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tipo = 99;
                    Intent intento = new Intent(ProcessingActivity.this, LoginActivity.class); // Lanzamos SiguienteActivity
                    intento.putExtra("mensaje_inicio", "Error en el servicio de autenticacion");
                    startActivity(intento);
                    this.finish();
                } else {
                    if (!validate_user.data_user_token(this,true)) {
                        Toast.makeText(ProcessingActivity.this, json_object_response.getJson_object_response().get("message").getAsString(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        tipo = 99;
                        Intent intento = new Intent(ProcessingActivity.this, LoginActivity.class); // Lanzamos SiguienteActivity
                        intento.putExtra("mensaje_inicio", json_object_response.getJson_object_response().get("message").getAsString());
                        startActivity(intento);
                        ProcessingActivity.this.finish();
                        this.finish();
                    } else {
                        utils_class.llenar_informacion_inicio_sesion_usuario("01",ProcessingActivity.this);
                        text_one.setText("Cargando Parametros");
                        tipo++;
                        transaccional_servicios();
                    }
                }
                break;
            case 2:
                if (json_object_response.getJson_object_response() == null) {
                    Toast.makeText(ProcessingActivity.this, "Error en el servicio de parametros", Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent intento = new Intent(ProcessingActivity.this, LoginActivity.class); // Lanzamos SiguienteActivity
                    intento.putExtra("mensaje_inicio", "Error en el servicio de parametros");
                    startActivity(intento);
                    ProcessingActivity.this.finish();
                    this.finish();
                    tipo = 99;
                } else {
                    if (!validate_user.data_gethome(this)) {
                        if (json_object_response.getJson_object_response().get("code").toString().equals("17")) {
                            json_object_response.setJson_response_data(json_object_response.getJson_object_response().get("data").getAsJsonObject());
                            mensaje_body_change_password = json_object_response.getJson_response_data().get("message").toString();
                            Intent intento = new Intent(this, ChangePasswordActivity.class); // Lanzamos SiguienteActivity
                            intento.putExtra("mensaje_body", mensaje_body_change_password);
                            startActivity(intento);
                            ProcessingActivity.this.finish();
                            this.finish();
                            tipo = 99;
                        } else {
                            json_object_response.setJson_response_data(json_object_response.getJson_object_response().get("data").getAsJsonObject());

                            Toast.makeText(ProcessingActivity.this, json_object_response.getJson_response_data().get("message").toString(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            tipo = 99;
                            Intent intento = new Intent(ProcessingActivity.this, LoginActivity.class); // Lanzamos SiguienteActivity
                            intento.putExtra("mensaje_inicio", json_object_response.getJson_response_data().get("message").toString());
                            startActivity(intento);
                            ProcessingActivity.this.finish();
                            this.finish();
                        }
                    } else {
                        x = 0;
                        text_one.setText("Cargando Parametros Generales");
                        home_productos_pack_local = utils_bd.getAllHomeProductosPack();
                        objaux = (HomeProductos) home_productos_pack_local.get(x);
                        id = objaux.getId();
                        tabla = objaux.getTabla();
                        interfacesModelo.home_productos.setId(id);
                        interfacesModelo.home_productos.setTabla(tabla);
                        cargarImagenes(this,2);
                        tipo++;
                        transaccional_servicios();
                    }
                }
                break;
            case 3:
                x++;
                if (!validate_user.data_getproductosmontos(this)) {
                    Toast.makeText(ProcessingActivity.this, "No es posible descargar paquetes del operador " + objaux.getOperador(), Toast.LENGTH_LONG).show();
                }
                if (x < home_productos_pack_local.size()) {
                    objaux = (HomeProductos) home_productos_pack_local.get(x);
                    id = objaux.getId();
                    tabla = objaux.getTabla();
                    home_productos.setId(id);
                    home_productos.setTabla(tabla);

                    tipo = 3;
                    transaccional_servicios();
                } else {
                    tipo = 99;
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent intento = new Intent(ProcessingActivity.this, MenuPrincActivity.class); // Lanzamos SiguienteActivity
                    intento.putExtra("mensaje_saldo", "01");
                    startActivity(intento);
                    this.finish();
                }

                break;
            case 4://Consumir servicio recarga
                if (json_object_response.getJson_object_response() == null) {
                    Toast.makeText(ProcessingActivity.this, "Error en el servicio consumo de recargas", Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent intento = new Intent(ProcessingActivity.this, MenuPrincActivity.class); // Lanzamos SiguienteActivity
                    intento.putExtra("mensaje_saldo", "");
                    startActivity(intento);
                    ProcessingActivity.this.finish();
                    this.finish();
                    tipo = 99;
                } else {
                    String resp = validate_user.data_getresprecarga();
                    if (resp.equals("")) {
                        json_object_response.setJson_response_data(json_object_response.getJson_object_response().get("data").getAsJsonObject());
                        utils_class.msjsSimple(this, "INFORMACION", json_object_response.getJson_response_data().get("message").toString(), "Aceptar", "", 0,1);
                        progressBar.setVisibility(View.INVISIBLE);
                        tipo = 99;
                        Intent intento = new Intent(ProcessingActivity.this, MenuPrincActivity.class); // Lanzamos SiguienteActivity
                        intento.putExtra("mensaje_saldo", "");
                        startActivity(intento);
                        ProcessingActivity.this.finish();
                        this.finish();
                    } else {
                        if(json_object_response.getJson_object_response().get("code").getAsString().equals("0"))
                        {
                            try {
                                if (Build.MANUFACTURER.equals("alps")) {
                                    if (getPrinterStatus() == 0) {
                                        printImpresionRecargas(0);
                                    }
                                } else if (Build.MANUFACTURER.equals("SUNMI")) {
                                    printImpresionRecargas(1);
                                }
                            }catch (Exception ex){

                            }
                        }
                        json_object_response.setJson_response_data(json_object_response.getJson_object_response().get("data").getAsJsonObject());

                        Toast.makeText(ProcessingActivity.this, json_object_response.getJson_response_data().get("message").toString(), Toast.LENGTH_LONG).show();
                        if (json_object_response.getJson_response_data().get("message").getAsString().length() == 0) {
                            mensaje_recarga = resp;
                        } else {
                            mensaje_recarga = json_object_response.getJson_response_data().get("message").toString();
                        }

                        tipo = 5;
                        text_one.setText("Actualizando Saldos");
                        transaccional_servicios();

                    }
                }
                break;
            case 5://Consumir actualizacion de saldo
                if (json_object_response.getJson_object_response() == null) {
                    Toast.makeText(ProcessingActivity.this, "Error en el servicio actualizacion de saldo", Toast.LENGTH_LONG).show();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent intento = new Intent(ProcessingActivity.this, MenuPrincActivity.class); // Lanzamos SiguienteActivity
                    intento.putExtra("mensaje_saldo", "");
                    startActivity(intento);
                    ProcessingActivity.this.finish();
                    this.finish();
                    tipo = 99;
                } else {
                    String resp = validate_user.data_getsaldo(this);
                    if (resp == "") {
                        json_object_response.setJson_response_data(json_object_response.getJson_object_response().get("data").getAsJsonObject());
                        utils_class.msjsSimple(this, "INFORMACION", json_object_response.getJson_response_data().get("message").toString(), "Aceptar", "", 0,1);
                        progressBar.setVisibility(View.INVISIBLE);
                        tipo = 99;
                        Intent intento = new Intent(ProcessingActivity.this, MenuPrincActivity.class); // Lanzamos SiguienteActivity
                        intento.putExtra("mensaje_saldo", "");
                        startActivity(intento);
                        ProcessingActivity.this.finish();
                        this.finish();
                    } else {
                        json_object_response.setJson_response_data(json_object_response.getJson_object_response().get("data").getAsJsonObject());
                        Toast.makeText(ProcessingActivity.this, "Saldo Actualizado", Toast.LENGTH_LONG).show();

                        tipo = 99;
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent intento = new Intent(this, MenuPrincActivity.class); // Lanzamos SiguienteActivity
                        intento.putExtra("mensaje_saldo", mensaje_recarga);
                        startActivity(intento);
                        this.finish();
                    }
                }
                break;

            default:
                break;
        }

    }


    public void cargarImagenes (Context context, int opc){

        ImageView img_local;

        if(opc == 1) {
            img_local   = new ImageView(context);
            interfacesModelo.carga_imagenes.img.add(img_local);
            Picasso.get()
                    .load("https://multiservicios.madefor.work/assets/img/pdv.png")
                    .transform(new RoundedTransformation(50, 4))
                    .resize(350, 300)
                    .centerCrop()
                    .into(interfacesModelo.carga_imagenes.img.get(0));

            img_local   = new ImageView(context);
            interfacesModelo.carga_imagenes.img.add(img_local);
            Picasso.get()
                    .load("https://multiservicios.madefor.work/assets/img/lateral.jpg")
                    .resize(800, 800)
                    .centerCrop()
                    .into(interfacesModelo.carga_imagenes.img.get(1));

            img_local   = new ImageView(context);
            interfacesModelo.carga_imagenes.img.add(img_local);
            Picasso.get()
                    .load("https://multiservicios.madefor.work/assets/img/admin.png")
                    .transform(new RoundedTransformation(50, 4))
                    .resize(350, 300)
                    .centerCrop()
                    .into(interfacesModelo.carga_imagenes.img.get(2));
        }

    }



    //funciones test print
    public void inicializarImpresion( int opc) {

        if (opc == 0) {


            handler = new HandlerUtils.MyHandler(this.iHandlerIntent);
            callback = new IPosPrinterCallback.Stub() {
                public void onRunResult(boolean isSuccess) throws RemoteException {
                    Log.i(IPosPrinterTestDemo.TAG, "result:" + isSuccess + "\n");
                }

                public void onReturnString(String value) throws RemoteException {
                    Log.i(IPosPrinterTestDemo.TAG, "result:" + value + "\n");
                }
            };
            Intent intent = new Intent();
            intent.setPackage("com.iposprinter.iposprinterservice");
            intent.setAction("com.iposprinter.iposprinterservice.IPosPrintService");
            bindService(intent, connectService, Context.BIND_AUTO_CREATE);

            //注册打印机状态接收器
            IntentFilter printerStatusFilter = new IntentFilter();
            printerStatusFilter.addAction(PRINTER_NORMAL_ACTION);
            printerStatusFilter.addAction(PRINTER_PAPERLESS_ACTION);
            printerStatusFilter.addAction(PRINTER_PAPEREXISTS_ACTION);
            printerStatusFilter.addAction(PRINTER_THP_HIGHTEMP_ACTION);
            printerStatusFilter.addAction(PRINTER_THP_NORMALTEMP_ACTION);
            printerStatusFilter.addAction(PRINTER_MOTOR_HIGHTEMP_ACTION);
            printerStatusFilter.addAction(PRINTER_BUSY_ACTION);

            registerReceiver(IPosPrinterStatusListener, printerStatusFilter);
        }else if (opc == 1){//Sunmi v1 POS-1
            printer_sunmi_v1.initPrinter(this);
        }


    }




    public int getPrinterStatus() {

        Log.i(TAG, "***** printerStatus" + this.printerStatus);
        try {
            this.printerStatus = mIPosPrinterService.getPrinterStatus();
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Error:"+e.getMessage());
        }
        Log.i(TAG, "#### printerStatus" + this.printerStatus);
        return this.printerStatus;

    }

    public void printerInit() {
        ThreadPoolManager.getInstance().executeTask(new Runnable() {
            public void run() {
                try {
                    mIPosPrinterService.printerInit(callback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    public void printImpresionRecargas(int opc) {

        final String referencia = json_object_response.getJson_response_data().get("referencia").toString().replace("\"","");
        final String autorizacion = json_object_response.getJson_response_data().get("autorizacion").toString().replace("\"","");
        final String fecha = json_object_response.getJson_response_data().get("fecha").toString().substring(1,json_object_response.getJson_response_data().get("fecha").toString().indexOf(" "));
        final String hora = json_object_response.getJson_response_data().get("fecha").toString().substring(json_object_response.getJson_response_data().get("fecha").toString().indexOf(" "),json_object_response.getJson_response_data().get("fecha").toString().length() - 1);
        final String monto = interfacesModelo.recarga_servicio.getMonto();


        if(opc == 0) {
            com.sb.tured.utilities.utilalpsprinter.printer.ThreadPoolManager.getInstance().executeTask(new Runnable() {
                public void run() {


                    //Bitmap mBitmap = BitmapFactory.decodeResource(this, R.mipmap.ic_bills);
                    try {
                        ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 28, callback);
                        ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 28, callback);
                        ProcessingActivity.this.mIPosPrinterService.printBlankLines(1, 8, callback);
                        ProcessingActivity.this.mIPosPrinterService.PrintSpecFormatText("ORIGINAL\n", "ST", 24, 1, callback);
                        ProcessingActivity.this.mIPosPrinterService.printBlankLines(1, 8, callback);
                        ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("Terminal:" + data_user.getId_dispositivo_usuario() + "\n", "ST", 24, callback);
                        ProcessingActivity.this.mIPosPrinterService.printBlankLines(1, 8, callback);
                        ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("Fecha: " + fecha + "\nHora: " + hora + "\n", "ST", 24, callback);
                        ProcessingActivity.this.mIPosPrinterService.printBlankLines(1, 16, callback);
                        //IPosPrinterTestDemo.this.mIPosPrinterService.printBitmap(1, 12, mBitmap, IPosPrinterTestDemo.this.callback);
                        ProcessingActivity.this.mIPosPrinterService.printBlankLines(1, 16, callback);
                        ProcessingActivity.this.mIPosPrinterService.PrintSpecFormatText("--------------------------------\n", "ST", 24, 1, callback);
                        ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 24, callback);
                        ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("Nro. Operacion: " + autorizacion + "\n\n", "ST", 24, callback);
                        ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("Telefono: " + referencia + "\n", "ST", 24, callback);
                        ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("Importe Pesos: $" + monto + "\n", "ST", 24, callback);
                        ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 24, callback);
                        ProcessingActivity.this.mIPosPrinterService.PrintSpecFormatText("TU-RED\n", "ST", 24, 1, callback);
                        ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 24, callback);
                        ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("\n\n", "ST", 16, callback);
                        ProcessingActivity.this.mIPosPrinterService.printBlankLines(1, 16, callback);
                        ProcessingActivity.this.mIPosPrinterService.printBlankLines(1, 16, callback);

                        ProcessingActivity.this.mIPosPrinterService.printerPerformPrint(160, callback);

                    } catch (RemoteException e) {
                        e.printStackTrace();

                    }
                }


            });
        }else if ( opc == 1){//Sunmi v1 POS-1
            printer_sunmi_v1.isBold = true;
            printer_sunmi_v1.isUnderLine = false;
            printer_sunmi_v1.templatePrintRecarga( data_user.getId_dispositivo_usuario(), fecha,  hora,  autorizacion,  referencia,  monto);
        }
    }

    public void printTest(int opc) {



        if(opc == 0) {
            com.sb.tured.utilities.utilalpsprinter.printer.ThreadPoolManager.getInstance().executeTask(new Runnable() {
                public void run() {


                    try {
                        mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 28, callback);
                        mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 28, callback);
                        mIPosPrinterService.printBlankLines(1, 8, callback);
                        mIPosPrinterService.PrintSpecFormatText("PRUEBA DE IMPRESION\n", "ST", 24, 1, callback);
                        mIPosPrinterService.printBlankLines(1, 8, callback);
                        mIPosPrinterService.printSpecifiedTypeText("Terminal:" , "ST", 24, callback);
                        mIPosPrinterService.printBlankLines(1, 8, callback);
                        mIPosPrinterService.printSpecifiedTypeText("Fecha: " , "ST", 24, callback);
                        mIPosPrinterService.printBlankLines(1, 16, callback);
                        mIPosPrinterService.printBlankLines(1, 16, callback);
                        mIPosPrinterService.PrintSpecFormatText("--------------------------------\n", "ST", 24, 1, callback);
                        mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 24, callback);
                        mIPosPrinterService.printSpecifiedTypeText("Nro. Operacion: " , "ST", 24, callback);
                        mIPosPrinterService.printSpecifiedTypeText("Telefono: " , "ST", 24, callback);
                        mIPosPrinterService.printSpecifiedTypeText("Importe Pesos: $" , "ST", 24, callback);
                        mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 24, callback);
                        mIPosPrinterService.PrintSpecFormatText("TU-RED\n", "ST", 24, 1, callback);
                        mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 24, callback);
                        mIPosPrinterService.printSpecifiedTypeText("\n\n", "ST", 16, callback);
                        mIPosPrinterService.printBlankLines(1, 16, callback);
                        mIPosPrinterService.printBlankLines(1, 16, callback);

                        mIPosPrinterService.printerPerformPrint(160, callback);

                    } catch (RemoteException e) {
                        e.printStackTrace();

                    }
                }


            });
        }else if ( opc == 1){//Sunmi v1 POS-1
            printer_sunmi_v1.isBold = true;
            printer_sunmi_v1.isUnderLine = false;
            //printer_sunmi_v1.templatePrintRecarga( data_user.getId_dispositivo_usuario(), fecha,  hora,  autorizacion,  referencia,  monto);
        }
    }


    public void onResume() {
        super.onResume();
        if(Build.MANUFACTURER.equals("alps")) {
            Log.d(TAG, "activity onResume");
            Intent intent = new Intent();
            intent.setPackage("com.iposprinter.iposprinterservice");
            intent.setAction("com.iposprinter.iposprinterservice.IPosPrintService");
            bindService(intent, connectService, Context.BIND_AUTO_CREATE);
            IntentFilter printerStatusFilter = new IntentFilter();
            printerStatusFilter.addAction("com.iposprinter.iposprinterservice.NORMAL_ACTION");
            printerStatusFilter.addAction("com.iposprinter.iposprinterservice.PAPERLESS_ACTION");
            printerStatusFilter.addAction("com.iposprinter.iposprinterservice.PAPEREXISTS_ACTION");
            printerStatusFilter.addAction("com.iposprinter.iposprinterservice.THP_HIGHTEMP_ACTION");
            printerStatusFilter.addAction("com.iposprinter.iposprinterservice.THP_NORMALTEMP_ACTION");
            printerStatusFilter.addAction("com.iposprinter.iposprinterservice.MOTOR_HIGHTEMP_ACTION");
            printerStatusFilter.addAction("com.iposprinter.iposprinterservice.BUSY_ACTION");
            printerStatusFilter.addAction("android.print.action.CUST_PRINTAPP_PACKAGENAME");
            registerReceiver(IPosPrinterStatusListener, printerStatusFilter);
        }
    }
    /* access modifiers changed from: protected */
    public void onStop() {
        Log.e(TAG, "activity onStop");
        super.onStop();
        if(Build.MANUFACTURER.equals("alps")) {

            //AppIndex.AppIndexApi.end(this.client, getIndexApiAction());

            unregisterReceiver(IPosPrinterStatusListener);
            unbindService(connectService);
            // this.client.disconnect();
        }
    }

    public void onPause() {
            Log.d(TAG, "activity onPause");
            super.onPause();
    }


    private BroadcastReceiver IPosPrinterStatusListener = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                Log.d(IPosPrinterTestDemo.TAG, "IPosPrinterStatusListener onReceive action = null");
                return;
            }
            Log.d(IPosPrinterTestDemo.TAG, "IPosPrinterStatusListener action = " + action);
            switch (action) {
                case "com.iposprinter.iposprinterservice.NORMAL_ACTION":
                    ProcessingActivity.this.handler.sendEmptyMessageDelayed(2, 0);
                    break;
                case "com.iposprinter.iposprinterservice.PAPERLESS_ACTION":
                    ProcessingActivity.this.handler.sendEmptyMessageDelayed(4, 0);
                    break;
                case "com.iposprinter.iposprinterservice.BUSY_ACTION":
                    ProcessingActivity.this.handler.sendEmptyMessageDelayed(3, 0);
                    break;
                case "com.iposprinter.iposprinterservice.PAPEREXISTS_ACTION":
                    ProcessingActivity.this.handler.sendEmptyMessageDelayed(5, 0);
                    break;
                case "com.iposprinter.iposprinterservice.THP_HIGHTEMP_ACTION":
                    ProcessingActivity.this.handler.sendEmptyMessageDelayed(6, 0);
                    break;
                case "com.iposprinter.iposprinterservice.THP_NORMALTEMP_ACTION":
                    ProcessingActivity.this.handler.sendEmptyMessageDelayed(7, 0);
                    break;
                case "com.iposprinter.iposprinterservice.MOTOR_HIGHTEMP_ACTION":
                    ProcessingActivity.this.handler.sendEmptyMessageDelayed(8, 0);
                    break;
                case "com.iposprinter.iposprinterservice.CURRENT_TASK_PRINT_COMPLETE_ACTION":
                    ProcessingActivity.this.handler.sendEmptyMessageDelayed(10, 0);
                    break;
                case "android.print.action.CUST_PRINTAPP_PACKAGENAME":
                    Log.d(IPosPrinterTestDemo.TAG, "*******GET_CUST_PRINTAPP_PACKAGENAME_ACTION：" + action + "*****mPackageName:" + intent.getPackage());
                    break;
                default:
                    ProcessingActivity.this.handler.sendEmptyMessageDelayed(1, 0);
                    break;
            }
        }
    };


}
