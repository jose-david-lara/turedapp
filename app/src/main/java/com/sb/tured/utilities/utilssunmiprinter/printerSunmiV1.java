package com.sb.tured.utilities.utilssunmiprinter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.sb.tured.R;
import com.sb.tured.activity.ProcessingActivity;
import com.sb.tured.utilities.utilssunmiprinter.utils.BytesUtil;
import com.sb.tured.utilities.utilssunmiprinter.utils.ESCUtil;
import com.sb.tured.utilities.utilssunmiprinter.utils.SunmiPrintHelper;

import java.io.UnsupportedEncodingException;

import static com.sb.tured.utilities.utilssunmiprinter.utils.SunmiPrintHelper.*;

public class printerSunmiV1 {

    public boolean isBold;
    public boolean  isUnderLine;


    public  boolean initPrinter (Context context){
        getInstance().initSunmiPrinterService(context);
        if (getInstance().sunmiPrinter ==   getInstance().NoSunmiPrinter){
            return false;
        }
        return  true;
    }

    public  void templatePrintRecarga (String terminal,String fecha, String hora, String autorizacion, String referencia, String monto){
        String bufferPrint;
        float size = 20;

        bufferPrint = "------------------------------------\n";//ProcessingActivity.this.mIPosPrinterService.PrintSpecFormatText(   "--------------------------------\n", "ST", 24, 1, ProcessingActivity.this.callback);
        getInstance().printText(bufferPrint, size, isBold, isUnderLine);

        bufferPrint = "------------------------------------\n";//ProcessingActivity.this.mIPosPrinterService.PrintSpecFormatText(   "--------------------------------\n", "ST", 24, 1, ProcessingActivity.this.callback);
        getInstance().printText(bufferPrint, size, isBold, isUnderLine);

        //centro
        bufferPrint = "ORIGINAL";//ProcessingActivity.this.mIPosPrinterService.PrintSpecFormatText("ORIGINAL\n", "ST", 24, 1, ProcessingActivity.this.callback);
        getInstance().setAlign(1);
        getInstance().printText(bufferPrint, size, isBold, isUnderLine);

        getInstance().printLine(1);

        getInstance().setAlign(0);
        bufferPrint = "Terminal:"+terminal+"\n";//ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("Terminal:"+data_user.getId_dispositivo_usuario()+"\n", "ST", 24, ProcessingActivity.this.callback);
        getInstance().printText(bufferPrint, size, isBold, isUnderLine);

        getInstance().printLine(1);

        bufferPrint = "Fecha: "+fecha+"\nHora: "+hora+"\n";//ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("Fecha: "+fecha+"\nHora: "+hora+"\n", "ST", 24, ProcessingActivity.this.callback);
        getInstance().printText(bufferPrint, size, isBold, isUnderLine);

        getInstance().printLine(1);

        bufferPrint = "------------------------------------\n";//ProcessingActivity.this.mIPosPrinterService.PrintSpecFormatText(   "--------------------------------\n", "ST", 24, 1, ProcessingActivity.this.callback);
        getInstance().printText(bufferPrint, size, isBold, isUnderLine);


        bufferPrint = "------------------------------------\n";//ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 24, ProcessingActivity.this.callback);
        getInstance().printText(bufferPrint, size, isBold, isUnderLine);


        bufferPrint = "Nro. Operacion: "+autorizacion+"\n\n";//ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("Nro. Operacion: "+autorizacion+"\n\n", "ST", 24, ProcessingActivity.this.callback);
        getInstance().printText(bufferPrint, size, isBold, isUnderLine);


        bufferPrint = "Telefono: "+referencia+"\n";//ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("Telefono: "+referencia+"\n", "ST", 24, ProcessingActivity.this.callback);
        getInstance().printText(bufferPrint, size, isBold, isUnderLine);


        bufferPrint = "Importe Pesos: $"+monto+"\n";//ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("Importe Pesos: $"+monto+"\n", "ST", 24, ProcessingActivity.this.callback);
        getInstance().printText(bufferPrint, size, isBold, isUnderLine);


        bufferPrint = "------------------------------------\n";//ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 24, ProcessingActivity.this.callback);
        getInstance().printText(bufferPrint, size, isBold, isUnderLine);


        //centro
        bufferPrint = "TU-RED\n";//ProcessingActivity.this.mIPosPrinterService.PrintSpecFormatText("TU-RED\n", "ST", 24, 1, ProcessingActivity.this.callback);
        getInstance().setAlign(1);
        getInstance().printText(bufferPrint, size, isBold, isUnderLine);


        getInstance().setAlign(0);

        bufferPrint = "------------------------------------\n";//ProcessingActivity.this.mIPosPrinterService.printSpecifiedTypeText("--------------------------------\n", "ST", 24, ProcessingActivity.this.callback);
        getInstance().printText(bufferPrint, size, isBold, isUnderLine);
        getInstance().feedPaper();
        getInstance().feedPaper();
        getInstance().feedPaper();


    }




}
