package com.sb.tured.utilities.utilalpsprinter.iposprinterservice;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPosPrinterService extends IInterface {
    void PrintSpecFormatText(String str, String str2, int i, int i2, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    int getPrinterStatus() throws RemoteException;

    void printBarCode(String str, int i, int i2, int i3, int i4, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void printBitmap(int i, int i2, Bitmap bitmap, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void printBlankLines(int i, int i2, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void printColumnsText(String[] strArr, int[] iArr, int[] iArr2, int i, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void printQRCode(String str, int i, int i2, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void printRawData(byte[] bArr, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void printSpecifiedTypeText(String str, String str2, int i, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void printText(String str, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void printerFeedLines(int i, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void printerInit(IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void printerPerformPrint(int i, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void sendUserCMDData(byte[] bArr, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void setPrinterPrintAlignment(int i, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void setPrinterPrintDepth(int i, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void setPrinterPrintFontSize(int i, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    void setPrinterPrintFontType(String str, IPosPrinterCallback iPosPrinterCallback) throws RemoteException;

    abstract class Stub extends Binder implements IPosPrinterService {
        private static final String DESCRIPTOR = "com.iposprinter.iposprinterservice.IPosPrinterService";
        static final int TRANSACTION_PrintSpecFormatText = 11;
        static final int TRANSACTION_getPrinterStatus = 1;
        static final int TRANSACTION_printBarCode = 14;
        static final int TRANSACTION_printBitmap = 13;
        static final int TRANSACTION_printBlankLines = 8;
        static final int TRANSACTION_printColumnsText = 12;
        static final int TRANSACTION_printQRCode = 15;
        static final int TRANSACTION_printRawData = 16;
        static final int TRANSACTION_printSpecifiedTypeText = 10;
        static final int TRANSACTION_printText = 9;
        static final int TRANSACTION_printerFeedLines = 7;
        static final int TRANSACTION_printerInit = 2;
        static final int TRANSACTION_printerPerformPrint = 18;
        static final int TRANSACTION_sendUserCMDData = 17;
        static final int TRANSACTION_setPrinterPrintAlignment = 6;
        static final int TRANSACTION_setPrinterPrintDepth = 3;
        static final int TRANSACTION_setPrinterPrintFontSize = 5;
        static final int TRANSACTION_setPrinterPrintFontType = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IPosPrinterService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IPosPrinterService)) {
                return new Proxy(obj);
            }
            return (IPosPrinterService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Bitmap _arg2;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    int _result = getPrinterStatus();
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    printerInit(IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    setPrinterPrintDepth(data.readInt(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    setPrinterPrintFontType(data.readString(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    setPrinterPrintFontSize(data.readInt(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    setPrinterPrintAlignment(data.readInt(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    printerFeedLines(data.readInt(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    printBlankLines(data.readInt(), data.readInt(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    printText(data.readString(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    printSpecifiedTypeText(data.readString(), data.readString(), data.readInt(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    PrintSpecFormatText(data.readString(), data.readString(), data.readInt(), data.readInt(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    printColumnsText(data.createStringArray(), data.createIntArray(), data.createIntArray(), data.readInt(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg2 = Bitmap.CREATOR.createFromParcel(data);
                    } else {
                        _arg2 = null;
                    }
                    printBitmap(_arg0, _arg1, _arg2, IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    printBarCode(data.readString(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    printQRCode(data.readString(), data.readInt(), data.readInt(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    printRawData(data.createByteArray(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    sendUserCMDData(data.createByteArray(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    printerPerformPrint(data.readInt(), IPosPrinterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPosPrinterService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public int getPrinterStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readInt();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void printerInit(IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPrinterPrintDepth(int depth, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(depth);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPrinterPrintFontType(String typeface, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(typeface);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPrinterPrintFontSize(int fontsize, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(fontsize);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPrinterPrintAlignment(int alignment, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(alignment);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void printerFeedLines(int lines, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(lines);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void printBlankLines(int lines, int height, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(lines);
                    _data.writeInt(height);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void printText(String text, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(text);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void printSpecifiedTypeText(String text, String typeface, int fontsize, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(text);
                    _data.writeString(typeface);
                    _data.writeInt(fontsize);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void PrintSpecFormatText(String text, String typeface, int fontsize, int alignment, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(text);
                    _data.writeString(typeface);
                    _data.writeInt(fontsize);
                    _data.writeInt(alignment);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void printColumnsText(String[] colsTextArr, int[] colsWidthArr, int[] colsAlign, int isContinuousPrint, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(colsTextArr);
                    _data.writeIntArray(colsWidthArr);
                    _data.writeIntArray(colsAlign);
                    _data.writeInt(isContinuousPrint);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void printBitmap(int alignment, int bitmapSize, Bitmap mBitmap, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(alignment);
                    _data.writeInt(bitmapSize);
                    if (mBitmap != null) {
                        _data.writeInt(1);
                        mBitmap.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void printBarCode(String data, int symbology, int height, int width, int textposition, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(data);
                    _data.writeInt(symbology);
                    _data.writeInt(height);
                    _data.writeInt(width);
                    _data.writeInt(textposition);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void printQRCode(String data, int modulesize, int mErrorCorrectionLevel, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(data);
                    _data.writeInt(modulesize);
                    _data.writeInt(mErrorCorrectionLevel);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void printRawData(byte[] rawPrintData, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(rawPrintData);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void sendUserCMDData(byte[] data, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(data);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void printerPerformPrint(int feedlines, IPosPrinterCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(feedlines);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }
    }
}
