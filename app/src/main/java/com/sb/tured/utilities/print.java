package com.sb.tured.utilities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.sb.tured.controller.ValidateJson;
import com.sb.tured.interfaces.interfacesModelo;
import com.sb.tured.utilities.utilalpsprinter.iposprinterservice.IPosPrinterCallback;
import com.sb.tured.utilities.utilalpsprinter.iposprinterservice.IPosPrinterService;
import com.sb.tured.utilities.utilalpsprinter.printer.IPosPrinterTestDemo;
import com.sb.tured.utilities.utilalpsprinter.printer.ThreadPoolManager;
import com.sb.tured.utilities.utilalpsprinter.printer.Utils.HandlerUtils;

import java.util.Calendar;

public class print {


    private ValidateJson validate_user = new ValidateJson();
    private Context context;

    //variables test print
    public IPosPrinterCallback callback = null;
    public HandlerUtils.MyHandler handler;
    public static final String TAG = "IPosPrinterTestDemo";
    public int printerStatus = 0;
    private final String PRINTER_NORMAL_ACTION = "com.sb.tured.utilities.utilalpsprinter.iposprinterservic e.NORMAL_ACTION";
    private final String PRINTER_PAPERLESS_ACTION = "com.sb.tured.utilities.utilalpsprinter.iposprinterservice.PAPERLESS_ACTION";
    private final String PRINTER_PAPEREXISTS_ACTION = "com.sb.tured.utilities.utilalpsprinter.iposprinterservice.PAPEREXISTS_ACTION";
    private final String PRINTER_THP_HIGHTEMP_ACTION = "com.sb.tured.utilities.utilalpsprinter.iposprinterservice.THP_HIGHTEMP_ACTION";
    private final String PRINTER_THP_NORMALTEMP_ACTION = "com.sb.tured.utilities.utilalpsprinter.iposprinterservice.THP_NORMALTEMP_ACTION";
    private final String PRINTER_MOTOR_HIGHTEMP_ACTION = "com.sb.tured.utilities.utilalpsprinter.iposprinterservice.MOTOR_HIGHTEMP_ACTION";
    private final String PRINTER_BUSY_ACTION = "com.sb.tured.utilities.utilalpsprinter.iposprinterservice.BUSY_ACTION";
    private final String PRINTER_CURRENT_TASK_PRINT_COMPLETE_ACTION = "com.sb.tured.utilities.utilalpsprinter.iposprinterservice.CURRENT_TASK_PRINT_COMPLETE_ACTION";
    private final String GET_CUST_PRINTAPP_PACKAGENAME_ACTION = "android.print.action.CUST_PRINTAPP_PACKAGENAME";
    public IPosPrinterService mIPosPrinterService;
    final Calendar c = Calendar.getInstance();

    private ServiceConnection connectService = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder service) {
            IPosPrinterService unused = print.this.mIPosPrinterService = IPosPrinterService.Stub.asInterface(service);
            System.out.println("Paso 1");
        }

        public void onServiceDisconnected(ComponentName name) {
            IPosPrinterService unused = print.this.mIPosPrinterService = null;
        }
    };



    public print (Context context){
        this.context = context;
    }


    public boolean testPrint () {



        if (getPrinterStatus() == 0) {
            printRecarga();
            return true;
        }else{
            return false;
        }
    }

    //funciones test print
    public void inicializarImpresion() {
        iniciarlizarParamtrosPrint();

        this.handler = new HandlerUtils.MyHandler(this.iHandlerIntent);
        this.callback = new IPosPrinterCallback.Stub() {
            public void onRunResult(boolean isSuccess) throws RemoteException {
                Log.i(IPosPrinterTestDemo.TAG, "result:" + isSuccess + "\n");
            }

            public void onReturnString(String value) throws RemoteException {
                Log.i(IPosPrinterTestDemo.TAG, "result:" + value + "\n");
            }
        };
        Intent intent=new Intent();
        intent.setPackage("com.sb.tured.utilities.utilalpsprinter.iposprinterservice");
        intent.setAction("com.sb.tured.utilities.utilalpsprinter.iposprinterservice.IPosPrintService");
        context.bindService(intent, connectService, Context.BIND_AUTO_CREATE);

        //注册打印机状态接收器
        IntentFilter printerStatusFilter = new IntentFilter();
        printerStatusFilter.addAction(PRINTER_NORMAL_ACTION);
        printerStatusFilter.addAction(PRINTER_PAPERLESS_ACTION);
        printerStatusFilter.addAction(PRINTER_PAPEREXISTS_ACTION);
        printerStatusFilter.addAction(PRINTER_THP_HIGHTEMP_ACTION);
        printerStatusFilter.addAction(PRINTER_THP_NORMALTEMP_ACTION);
        printerStatusFilter.addAction(PRINTER_MOTOR_HIGHTEMP_ACTION);
        printerStatusFilter.addAction(PRINTER_BUSY_ACTION);

        context.registerReceiver(IPosPrinterStatusListener,printerStatusFilter);


    }

    private HandlerUtils.IHandlerIntent iHandlerIntent = new HandlerUtils.IHandlerIntent() {
        @SuppressLint("WrongConstant")
        public void handlerIntent(Message msg) {
            switch (msg.what) {
                case 3:
                    //Toast.makeText(print.this, R.string.printer_is_working, 0).show();
                    return;
                case 4:
                    //Toast.makeText(print.this, R.string.out_of_paper, 0).show();
                    return;
                case 5:
                    //Toast.makeText(print.this, R.string.exists_paper, 0).show();
                    return;
                case 6:
                    //Toast.makeText(print.this, R.string.printer_high_temp_alarm, 0).show();
                    return;
                case 8:
                    //Toast.makeText(print.this, R.string.motor_high_temp_alarm, 0).show();
                    handler.sendEmptyMessageDelayed(9, 180000);
                    return;
                case 9:
                    printerInit();
                    return;
                case 10:
                    //Toast.makeText(print.this, R.string.printer_current_task_print_complete, 0).show();
                    return;
                default:
                    return;
            }
        }
    };

    public int getPrinterStatus() {

        Log.i(TAG, "***** printerStatus" + this.printerStatus);
        try {
            this.printerStatus = this.mIPosPrinterService.getPrinterStatus();
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
                    print.this.mIPosPrinterService.printerInit(print.this.callback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void printRecarga() {
        final formatText format_text = new formatText();

        c.getTime();
        final String fecha = c.get(Calendar.DAY_OF_MONTH)+"/"+(c.get(Calendar.MONTH) + 1)+"/"+c.get(Calendar.YEAR);
        final String hora = c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)+" "+c.get(Calendar.AM_PM);
        com.sb.tured.utilities.utilalpsprinter.printer.ThreadPoolManager.getInstance().executeTask(new Runnable() {
            public void run() {


                //Bitmap mBitmap = BitmapFactory.decodeResource(this, R.mipmap.ic_bills);
                try {
                    print.this.mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 28, print.this.callback);
                    print.this.mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 28, print.this.callback);
                    print.this.mIPosPrinterService.printBlankLines(1, 8, print.this.callback);
                    print.this.mIPosPrinterService.PrintSpecFormatText("ORIGINAL\n", "ST", 24, 1, print.this.callback);
                    print.this.mIPosPrinterService.printBlankLines(1, 8, print.this.callback);
                    print.this.mIPosPrinterService.printSpecifiedTypeText("Terminal:"+validate_user.data_user.getId_dispositivo_usuario()+"\n", "ST", 24, print.this.callback);
                    print.this.mIPosPrinterService.printBlankLines(1, 8, print.this.callback);
                    print.this.mIPosPrinterService.printSpecifiedTypeText("Fecha: "+fecha+"\nHora: "+hora+"\n", "ST", 24, print.this.callback);
                    print.this.mIPosPrinterService.printBlankLines(1, 16, print.this.callback);
                    //IPosPrinterTestDemo.this.mIPosPrinterService.printBitmap(1, 12, mBitmap, IPosPrinterTestDemo.this.callback);
                    print.this.mIPosPrinterService.printBlankLines(1, 16, print.this.callback);
                    print.this.mIPosPrinterService.PrintSpecFormatText(   "--------------------------------\n", "ST", 24, 1, print.this.callback);
                    print.this.mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 24, print.this.callback);
                    print.this.mIPosPrinterService.printSpecifiedTypeText("Nro. Operacion: 854775025\n\n", "ST", 24, print.this.callback);
                    print.this.mIPosPrinterService.printSpecifiedTypeText("Telefono: "+ interfacesModelo.recarga_servicio.getReferencia()+"\n", "ST", 24, print.this.callback);
                    print.this.mIPosPrinterService.printSpecifiedTypeText("Importe Pesos: "+format_text.integerFormat(Integer.parseInt(interfacesModelo.recarga_servicio.getMonto()))+"\n", "ST", 24, print.this.callback);
                    print.this.mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 24, print.this.callback);
                    print.this.mIPosPrinterService.PrintSpecFormatText("TU-RED\n", "ST", 24, 1, print.this.callback);
                    print.this.mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 24, print.this.callback);
                    print.this.mIPosPrinterService.printSpecifiedTypeText("\n\n", "ST", 16, print.this.callback);
                    print.this.mIPosPrinterService.printBlankLines(1, 16, print.this.callback);
                    print.this.mIPosPrinterService.printBlankLines(1, 16, print.this.callback);
                    /*for (int i = 0; i < 12; i++) {
                        MenuPrincActivity.this.mIPosPrinterService.printRawData(BytesUtil.initLine1(384, i), MenuPrincActivity.this.callback);
                    }*/
                    //com.sb.tured.utilities.utilalpsprinter.printer.MemInfo.bitmapRecycle(mBitmap);
                    print.this.mIPosPrinterService.printerPerformPrint(160, print.this.callback);
                    //progressDialog.dismiss();
                } catch (RemoteException e) {
                    e.printStackTrace();

                }

            }




        });
    }

    public  void iniciarlizarParamtrosPrint() {
        Log.d(TAG, "activity onResume");
        //super.onResume();
        Intent intent = new Intent();
        intent.setPackage("com.sb.tured.utilities.utilalpsprinter.iposprinterservice");
        intent.setAction("com.sb.tured.utilities.utilalpsprinter.iposprinterservice.IPosPrintService");
        context.bindService(intent, print.this.connectService, Context.BIND_AUTO_CREATE);
        IntentFilter printerStatusFilter = new IntentFilter();
        printerStatusFilter.addAction("com.sb.tured.utilities.utilalpsprinter.iposprinterservic e.NORMAL_ACTION");
        printerStatusFilter.addAction("com.sb.tured.utilities.utilalpsprinter.iposprinterservice.PAPERLESS_ACTION");
        printerStatusFilter.addAction("com.sb.tured.utilities.utilalpsprinter.iposprinterservice.PAPEREXISTS_ACTION");
        printerStatusFilter.addAction("com.sb.tured.utilities.utilalpsprinter.iposprinterservice.THP_HIGHTEMP_ACTION");
        printerStatusFilter.addAction("com.sb.tured.utilities.utilalpsprinter.iposprinterservice.THP_NORMALTEMP_ACTION");
        printerStatusFilter.addAction("com.sb.tured.utilities.utilalpsprinter.iposprinterservice.MOTOR_HIGHTEMP_ACTION");
        printerStatusFilter.addAction("com.sb.tured.utilities.utilalpsprinter.iposprinterservice.BUSY_ACTION");
        printerStatusFilter.addAction("android.print.action.CUST_PRINTAPP_PACKAGENAME");
        context.registerReceiver(print.this.IPosPrinterStatusListener, printerStatusFilter);
    }
    /* access modifiers changed from: protected */
    /*public void onStop() {
        Log.e(TAG, "activity onStop");
        super.onStop();
        //AppIndex.AppIndexApi.end(this.client, getIndexApiAction());

        unregisterReceiver(this.IPosPrinterStatusListener);
        unbindService(this.connectService);
        // this.client.disconnect();
    }

    public void onPause() {
        Log.d(TAG, "activity onPause");
        super.onPause();
    }*/


    private BroadcastReceiver IPosPrinterStatusListener = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                Log.d(IPosPrinterTestDemo.TAG, "IPosPrinterStatusListener onReceive action = null");
                return;
            }
            Log.d(IPosPrinterTestDemo.TAG, "IPosPrinterStatusListener action = " + action);
            if (action.equals("com.sb.tured_datafono.iposprinterservic e.NORMAL_ACTION")) {
                print.this.handler.sendEmptyMessageDelayed(2, 0);
            } else if (action.equals("com.sb.tured_datafono.iposprinterservice.PAPERLESS_ACTION")) {
                print.this.handler.sendEmptyMessageDelayed(4, 0);
            } else if (action.equals("com.sb.tured_datafono.iposprinterservice.BUSY_ACTION")) {
                print.this.handler.sendEmptyMessageDelayed(3, 0);
            } else if (action.equals("com.sb.tured_datafono.iposprinterservice.PAPEREXISTS_ACTION")) {
                print.this.handler.sendEmptyMessageDelayed(5, 0);
            } else if (action.equals("com.sb.tured_datafono.iposprinterservice.THP_HIGHTEMP_ACTION")) {
                print.this.handler.sendEmptyMessageDelayed(6, 0);
            } else if (action.equals("com.sb.tured_datafono.iposprinterservice.THP_NORMALTEMP_ACTION")) {
                print.this.handler.sendEmptyMessageDelayed(7, 0);
            } else if (action.equals("com.sb.tured_datafono.iposprinterservice.MOTOR_HIGHTEMP_ACTION")) {
                print.this.handler.sendEmptyMessageDelayed(8, 0);
            } else if (action.equals("com.sb.tured_datafono.iposprinterservice.CURRENT_TASK_PRINT_COMPLETE_ACTION")) {
                print.this.handler.sendEmptyMessageDelayed(10, 0);
            } else if (action.equals("android.print.action.CUST_PRINTAPP_PACKAGENAME")) {
                Log.d(IPosPrinterTestDemo.TAG, "*******GET_CUST_PRINTAPP_PACKAGENAME_ACTION：" + action + "*****mPackageName:" + intent.getPackage());
            } else {
                print.this.handler.sendEmptyMessageDelayed(1, 0);
            }
        }
    };




}
