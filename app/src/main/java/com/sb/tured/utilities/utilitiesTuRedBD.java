package com.sb.tured.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sb.tured.model.BalanceProductos;
import com.sb.tured.model.CategoriasProductos;
import com.sb.tured.model.CertificadosSNR;
import com.sb.tured.model.HomeProductos;
import com.sb.tured.model.HomeProductosParametros;
import com.sb.tured.model.PackOperRecarga;
import com.sb.tured.model.ProductInfo;
import com.sb.tured.model.ServOperPackRecarga;
import com.sb.tured.model.TransactionsLast;
import com.sb.tured.model.TransactionsReport;
import com.sb.tured.model.Usuario;
import com.sb.tured.model.UsuarioSesion;
import com.sb.tured.model.ValueDefaultOperRecarga;


import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class utilitiesTuRedBD extends SQLiteOpenHelper {


    public utilitiesTuRedBD(Context context) {
        super(context, turedUserBD.DATABASE_NAME, null, turedUserBD.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(turedUserBD.TURED_TABLE_CREATE_USUARIO_INFO);
        db.execSQL(turedUserBD.TURED_TABLE_CREATE_TRANS_LAST);
        db.execSQL(turedUserBD.TURED_TABLE_CREATE_PRODUCTO_INFO);
        db.execSQL(turedUserBD.TURED_TABLE_CREATE_BALANCE_PRODUCTOS);
        db.execSQL(turedUserBD.TURED_TABLE_CREATE_HOME_PRODUCTOS);
        db.execSQL(turedUserBD.TURED_TABLE_CREATE_HOME_PRODUCTOS_PARAMETROS);
        db.execSQL(turedUserBD.TURED_TABLE_CREATE_PACK_OPER_RECARGA);
        db.execSQL(turedUserBD.TURED_TABLE_CREATE_SERV_OPER_PACK_RECARGA);
        db.execSQL(turedUserBD.TURED_TABLE_CREATE_VALUE_DEFAULT_OPER_RECARGA);
        db.execSQL(turedUserBD.TURED_TABLE_CREATE_REPORT_TRANS_PRODUCTOS);
        db.execSQL(turedUserBD.TURED_TABLE_CREATE_OFICINAS_CERTIFICADOS);
        if (!isTableExistsUsuarioSesion(turedUserBD.USUARIO_SESION.TABLE_NAME, db))
            db.execSQL(turedUserBD.TURED_TABLE_CREATE_USUARIO_SESION);
        db.execSQL(turedUserBD.TURED_TABLE_CREATE_CATEGORIAS_PRODUCTOS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(turedUserBD.TURED_TABLE_USUARIO_INFO_DROP);
        db.execSQL(turedUserBD.TURED_TABLE_TRANS_LAST_DROP);
        db.execSQL(turedUserBD.TURED_TABLE_PRODUCTO_INFO_DROP);
        db.execSQL(turedUserBD.TURED_TABLE_BALANCE_PRODUCTOS_DROP);
        db.execSQL(turedUserBD.TURED_TABLE_HOME_PRODUCTOS_DROP);
        db.execSQL(turedUserBD.TURED_TABLE_HOME_PRODUCTOS_PARAMETRPS_DROP);
        db.execSQL(turedUserBD.TURED_TABLE_PACK_OPER_RECARGA_DROP);
        db.execSQL(turedUserBD.TURED_TABLE_SERV_OPER_PACK_RECARGA_DROP);
        db.execSQL(turedUserBD.TURED_TABLE_VALUE_DEFAULT_OPER_RECARGA_DROP);
        db.execSQL(turedUserBD.TURED_TABLE_REPORT_TRANS_PRODUCTOS_DROP);
        db.execSQL(turedUserBD.TURED_TABLE_OFICINAS_CERTIFICADOS_SNR_DROP);
        db.execSQL(turedUserBD.TURED_TABLE_CATEGORIAS_PRODUCTOS_DROP);
        this.onCreate(db);
    }


    public SQLiteDatabase checkDataBase(String Database_path) {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(Database_path, null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
            return checkDB;
        } catch (SQLiteException e) {
            Log.e("Error", "No existe la base de datos " + e.getMessage());
            return checkDB;
        }
    }

    public boolean isTableExists(String nombreTabla) {
        boolean isExist = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + nombreTabla + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                isExist = true;
            } else {
            }
            cursor.close();
        }
        return isExist;
    }

    public boolean isTableExistsUsuarioSesion(String nombreTabla, SQLiteDatabase db) {
        boolean isExist = false;
        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + nombreTabla + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                isExist = true;
            } else {
            }
            cursor.close();
        }
        return isExist;
    }

//****Funciones para USUARIO INFO*********************************************************************************

    public void insertUsuarioInfo(Usuario usuario_info) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(turedUserBD.USUARIO_INFO.EMAIL, usuario_info.getEmail());
        values.put(turedUserBD.USUARIO_INFO.PASSWORD, usuario_info.getPassword());
        values.put(turedUserBD.USUARIO_INFO.TOKEN, usuario_info.getToken());
        values.put(turedUserBD.USUARIO_INFO.PRESTAMO_MAXIMO_DIA, usuario_info.getPrestamo_maximo_dia());
        values.put(turedUserBD.USUARIO_INFO.PREGUNTA, usuario_info.getPregunta());
        values.put(turedUserBD.USUARIO_INFO.DEUDA_NEGATIVA, usuario_info.getDeuda_negativa());
        values.put(turedUserBD.USUARIO_INFO.DEUDA_POSITIVA, usuario_info.getDeuda_positiva());
        values.put(turedUserBD.USUARIO_INFO.PRESTAMO_MAXIMO, usuario_info.getPrestamo_maximo());
        values.put(turedUserBD.USUARIO_INFO.NOMBRE_USUARIO, usuario_info.getNombre_usuario());

        db.insert(turedUserBD.USUARIO_INFO.TABLE_NAME, null, values);

        db.close();
    }


    public Usuario getUsuarioInfo(String email) {

        Usuario usuario_info = null;


        SQLiteDatabase db = this.getReadableDatabase();

        String[] COLUMNS = {
                turedUserBD.USUARIO_INFO.EMAIL,
                turedUserBD.USUARIO_INFO.PASSWORD,
                turedUserBD.USUARIO_INFO.TOKEN,
                turedUserBD.USUARIO_INFO.PRESTAMO_MAXIMO_DIA,
                turedUserBD.USUARIO_INFO.PREGUNTA,
                turedUserBD.USUARIO_INFO.DEUDA_NEGATIVA,
                turedUserBD.USUARIO_INFO.DEUDA_POSITIVA,
                turedUserBD.USUARIO_INFO.PRESTAMO_MAXIMO,
                turedUserBD.USUARIO_INFO.NOMBRE_USUARIO,

        };


        Cursor cursor =
                db.query(turedUserBD.USUARIO_INFO.TABLE_NAME,
                        COLUMNS,
                        " email = ?",
                        null,
                        null,
                        null,
                        null,
                        null);

        if (cursor != null) {
            cursor.moveToFirst();

            usuario_info = new Usuario();
            usuario_info.setEmail(cursor.getString(0));
            usuario_info.setPassword(cursor.getString(1));
            usuario_info.setToken(cursor.getString(2));
            usuario_info.setPrestamo_maximo_dia(cursor.getString(3));
            usuario_info.setPregunta(cursor.getString(4));
            usuario_info.setDeuda_negativa(cursor.getString(5));
            usuario_info.setDeuda_positiva(cursor.getString(6));
            usuario_info.setPrestamo_maximo(cursor.getString(7));
            usuario_info.setNombre_usuario(cursor.getString(8));
            cursor.close();
        }

        return usuario_info;
    }

    public List getUsuarioInfo() {
        List usuario_info_list = new ArrayList();


        String query = "SELECT  * FROM " + turedUserBD.USUARIO_INFO.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Usuario usuario_info_one = null;
        if (cursor.moveToFirst()) {
            do {
                usuario_info_one = new Usuario();
                usuario_info_one.setEmail(cursor.getString(0));
                usuario_info_one.setPassword(cursor.getString(1));
                usuario_info_one.setToken(cursor.getString(2));
                usuario_info_one.setPrestamo_maximo_dia(cursor.getString(3));
                usuario_info_one.setPregunta(cursor.getString(4));
                usuario_info_one.setDeuda_negativa(cursor.getString(5));
                usuario_info_one.setDeuda_positiva(cursor.getString(6));
                usuario_info_one.setPrestamo_maximo(cursor.getString(7));
                usuario_info_one.setNombre_usuario(cursor.getString(8));
                usuario_info_list.add(usuario_info_one);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return usuario_info_list;
    }


    public int updateUsuarioInfo(Usuario usuario_info) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(turedUserBD.USUARIO_INFO.EMAIL, usuario_info.getEmail());
        values.put(turedUserBD.USUARIO_INFO.PASSWORD, usuario_info.getPassword());
        values.put(turedUserBD.USUARIO_INFO.TOKEN, usuario_info.getToken());
        values.put(turedUserBD.USUARIO_INFO.PRESTAMO_MAXIMO_DIA, usuario_info.getPrestamo_maximo_dia());
        values.put(turedUserBD.USUARIO_INFO.PREGUNTA, usuario_info.getPregunta());
        values.put(turedUserBD.USUARIO_INFO.DEUDA_NEGATIVA, usuario_info.getDeuda_negativa());
        values.put(turedUserBD.USUARIO_INFO.DEUDA_POSITIVA, usuario_info.getDeuda_positiva());
        values.put(turedUserBD.USUARIO_INFO.PRESTAMO_MAXIMO, usuario_info.getPrestamo_maximo());
        values.put(turedUserBD.USUARIO_INFO.NOMBRE_USUARIO, usuario_info.getNombre_usuario());


        int i = db.update(turedUserBD.USUARIO_INFO.TABLE_NAME, //table
                values,
                turedUserBD.USUARIO_INFO.EMAIL + " = ?",
                new String[]{String.valueOf(usuario_info.getEmail())});

        db.close();
        return i;
    }


//****Funciones para ultimas transacciones*********************************************************************************

    public void insertTransactionLast(TransactionsLast trans_last) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(turedUserBD.USUARIO_TRANS_LAST.ID, trans_last.getId());
        values.put(turedUserBD.USUARIO_TRANS_LAST.OPERADOR, trans_last.getOperador());
        values.put(turedUserBD.USUARIO_TRANS_LAST.NUMERO, trans_last.getNumero());
        values.put(turedUserBD.USUARIO_TRANS_LAST.FECHA_SOLICITUD, trans_last.getFecha_solicitud());
        values.put(turedUserBD.USUARIO_TRANS_LAST.FECHA_RESPUESTA, trans_last.getFecha_respuesta());
        values.put(turedUserBD.USUARIO_TRANS_LAST.MENSAJE, trans_last.getMensaje());
        values.put(turedUserBD.USUARIO_TRANS_LAST.CODIGO, trans_last.getCodigo());
        values.put(turedUserBD.USUARIO_TRANS_LAST.ID_USER, trans_last.getId_user());
        values.put(turedUserBD.USUARIO_TRANS_LAST.VALOR_RECARGA, trans_last.getValor_Recarga());
        values.put(turedUserBD.USUARIO_TRANS_LAST.AUTORIZACION, trans_last.getAutorizacion());
        values.put(turedUserBD.USUARIO_TRANS_LAST.ESTADO, trans_last.getEstado());

        db.insert(turedUserBD.USUARIO_TRANS_LAST.TABLE_NAME, null, values);

        db.close();
    }


    public TransactionsLast getTransactionLast(String id_user) {

        TransactionsLast trans_last = null;


        SQLiteDatabase db = this.getReadableDatabase();

        String[] COLUMNS = {
                turedUserBD.USUARIO_TRANS_LAST.ID,
                turedUserBD.USUARIO_TRANS_LAST.OPERADOR,
                turedUserBD.USUARIO_TRANS_LAST.NUMERO,
                turedUserBD.USUARIO_TRANS_LAST.FECHA_SOLICITUD,
                turedUserBD.USUARIO_TRANS_LAST.FECHA_RESPUESTA,
                turedUserBD.USUARIO_TRANS_LAST.MENSAJE,
                turedUserBD.USUARIO_TRANS_LAST.CODIGO,
                turedUserBD.USUARIO_TRANS_LAST.ID_USER,
                turedUserBD.USUARIO_TRANS_LAST.VALOR_RECARGA,
                turedUserBD.USUARIO_TRANS_LAST.AUTORIZACION,
        };


        Cursor cursor =
                db.query(turedUserBD.USUARIO_TRANS_LAST.TABLE_NAME,
                        COLUMNS,
                        " id_user = ?",
                        new String[]{String.valueOf(id_user)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null) {
            cursor.moveToFirst();

            trans_last = new TransactionsLast();
            trans_last.setId(cursor.getString(0));
            trans_last.setOperador(cursor.getString(1));
            trans_last.setNumero(cursor.getString(2));
            trans_last.setFecha_solicitud(cursor.getString(3));
            trans_last.setFecha_respuesta(cursor.getString(4));
            trans_last.setMensaje(cursor.getString(5));
            trans_last.setCodigo(cursor.getString(6));
            trans_last.setId_user(cursor.getString(7));
            trans_last.setValor_Recarga(cursor.getString(8));
            trans_last.setAutorizacion(cursor.getString(9));
            cursor.close();

        }

        return trans_last;
    }

    public List getAllLastTrans() {
        List last_trans_list = new ArrayList();


        String query = "SELECT  * FROM " + turedUserBD.USUARIO_TRANS_LAST.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        TransactionsLast last_trans_local = null;

        if (cursor.moveToFirst()) {
            do {
                last_trans_local = new TransactionsLast();
                last_trans_local.setId(cursor.getString(0));
                last_trans_local.setOperador(cursor.getString(1));
                last_trans_local.setNumero(cursor.getString(2));
                last_trans_local.setFecha_solicitud(cursor.getString(3));
                last_trans_local.setFecha_respuesta(cursor.getString(4));
                last_trans_local.setMensaje(cursor.getString(5));
                last_trans_local.setCodigo(cursor.getString(6));
                last_trans_local.setId_user(cursor.getString(7));
                last_trans_local.setValor_Recarga(cursor.getString(8));
                last_trans_local.setAutorizacion(cursor.getString(9));
                last_trans_local.setEstado(cursor.getString(10));


                last_trans_list.add(last_trans_local);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return last_trans_list;
    }

    public int updateTransactionLast(TransactionsLast trans_last) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(turedUserBD.USUARIO_TRANS_LAST.ID, trans_last.getId());
        values.put(turedUserBD.USUARIO_TRANS_LAST.OPERADOR, trans_last.getOperador());
        values.put(turedUserBD.USUARIO_TRANS_LAST.NUMERO, trans_last.getNumero());
        values.put(turedUserBD.USUARIO_TRANS_LAST.FECHA_SOLICITUD, trans_last.getFecha_solicitud());
        values.put(turedUserBD.USUARIO_TRANS_LAST.FECHA_RESPUESTA, trans_last.getFecha_respuesta());
        values.put(turedUserBD.USUARIO_TRANS_LAST.MENSAJE, trans_last.getMensaje());
        values.put(turedUserBD.USUARIO_TRANS_LAST.CODIGO, trans_last.getCodigo());
        values.put(turedUserBD.USUARIO_TRANS_LAST.ID_USER, trans_last.getId_user());
        values.put(turedUserBD.USUARIO_TRANS_LAST.VALOR_RECARGA, trans_last.getValor_Recarga());
        values.put(turedUserBD.USUARIO_TRANS_LAST.AUTORIZACION, trans_last.getAutorizacion());

        int i = db.update(turedUserBD.USUARIO_TRANS_LAST.TABLE_NAME, //table
                values,
                turedUserBD.USUARIO_TRANS_LAST.ID_USER + " = ?",
                new String[]{String.valueOf(trans_last.getId_user())});

        db.close();
        return i;
    }

    public void deteleAllLastTrans() {


        String query = "DELETE  FROM " + turedUserBD.USUARIO_TRANS_LAST.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        db.rawQuery(query, null);
    }


//****Funciones para informacion productos*********************************************************************************


    public void insertProducto(ProductInfo producto_info) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(turedUserBD.USUARIO_INFO_PROD.ID, producto_info.getId());
        values.put(turedUserBD.USUARIO_INFO_PROD.NOMBRE, producto_info.getNombre());
        values.put(turedUserBD.USUARIO_INFO_PROD.DESCRIPCION, producto_info.getDescripcion());
        values.put(turedUserBD.USUARIO_INFO_PROD.COMISION_PRODUCTO, producto_info.getComision_producto());
        values.put(turedUserBD.USUARIO_INFO_PROD.TIPO_COMISION, producto_info.getTipo_comision());
        values.put(turedUserBD.USUARIO_INFO_PROD.VALOR_PRODUCTO, producto_info.getValor_producto());
        values.put(turedUserBD.USUARIO_INFO_PROD.URL_IMG, producto_info.getUrl_img());

        db.insert(turedUserBD.USUARIO_INFO_PROD.TABLE_NAME, null, values);

        db.close();
    }


    public ProductInfo getProducto(String id_producto) {

        ProductInfo producto_info = null;


        SQLiteDatabase db = this.getReadableDatabase();

        String[] COLUMNS = {
                turedUserBD.USUARIO_INFO_PROD.ID,
                turedUserBD.USUARIO_INFO_PROD.COMISION_PRODUCTO,
                turedUserBD.USUARIO_INFO_PROD.VALOR_PRODUCTO,
                turedUserBD.USUARIO_INFO_PROD.NOMBRE,
                turedUserBD.USUARIO_INFO_PROD.DESCRIPCION,
                turedUserBD.USUARIO_INFO_PROD.TIPO_COMISION,
                turedUserBD.USUARIO_INFO_PROD.URL_IMG,
        };


        Cursor cursor =
                db.query(turedUserBD.USUARIO_INFO_PROD.TABLE_NAME,
                        COLUMNS,
                        " id = ?",
                        new String[]{String.valueOf(id_producto)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null) {
            cursor.moveToFirst();

            producto_info = new ProductInfo();
            producto_info.setId(cursor.getString(0));
            producto_info.setComision_producto(cursor.getString(1));
            producto_info.setValor_producto(cursor.getString(2));
            producto_info.setNombre(cursor.getString(3));
            producto_info.setDescripcion(cursor.getString(4));
            producto_info.setTipo_comision(cursor.getString(5));
            producto_info.setUrl_img(cursor.getString(6));

            cursor.close();
        }

        return producto_info;
    }


    public int updateProducto(ProductInfo producto_info) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(turedUserBD.USUARIO_INFO_PROD.ID, producto_info.getId());
        values.put(turedUserBD.USUARIO_INFO_PROD.COMISION_PRODUCTO, producto_info.getComision_producto());
        values.put(turedUserBD.USUARIO_INFO_PROD.VALOR_PRODUCTO, producto_info.getValor_producto());
        values.put(turedUserBD.USUARIO_INFO_PROD.NOMBRE, producto_info.getNombre());
        values.put(turedUserBD.USUARIO_INFO_PROD.DESCRIPCION, producto_info.getDescripcion());
        values.put(turedUserBD.USUARIO_INFO_PROD.TIPO_COMISION, producto_info.getTipo_comision());
        values.put(turedUserBD.USUARIO_INFO_PROD.URL_IMG, producto_info.getUrl_img());

        int i = db.update(turedUserBD.USUARIO_INFO_PROD.TABLE_NAME, //table
                values,
                turedUserBD.USUARIO_INFO_PROD.ID + " = ?",
                new String[]{String.valueOf(producto_info.getId())});

        db.close();
        return i;
    }

    public List getAllProductos() {
        List productos = new ArrayList();


        String query = "SELECT  * FROM " + turedUserBD.USUARIO_INFO_PROD.TABLE_NAME + " order by " + turedUserBD.USUARIO_INFO_PROD.ID + " asc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ProductInfo producto_one = null;

        if (cursor.moveToFirst()) {
            do {
                producto_one = new ProductInfo();
                producto_one.setId(cursor.getString(0));
                producto_one.setNombre(cursor.getString(1));
                producto_one.setDescripcion(cursor.getString(2));
                producto_one.setComision_producto(cursor.getString(3));
                producto_one.setTipo_comision(cursor.getString(4));
                producto_one.setValor_producto(cursor.getString(5));
                producto_one.setUrl_img(cursor.getString(6));


                productos.add(producto_one);
            } while (cursor.moveToNext());
            cursor.close();
        }


        return productos;
    }


//****Funciones para informacion productos balance*********************************************************************************

    public void insertProductoBalance(BalanceProductos producto_balance) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(turedUserBD.USUARIO_BALANCE_PRODUCTOS.ID, producto_balance.getId());
        values.put(turedUserBD.USUARIO_BALANCE_PRODUCTOS.USER_ID, producto_balance.getUser_id());
        values.put(turedUserBD.USUARIO_BALANCE_PRODUCTOS.PRODUCTOS_ID, producto_balance.getProductos_id());
        values.put(turedUserBD.USUARIO_BALANCE_PRODUCTOS.SALDO, producto_balance.getSaldo());
        values.put(turedUserBD.USUARIO_BALANCE_PRODUCTOS.NOMBRE, producto_balance.getNombre());
        values.put(turedUserBD.USUARIO_BALANCE_PRODUCTOS.DESCRIPCION, producto_balance.getDescripion());
        values.put(turedUserBD.USUARIO_BALANCE_PRODUCTOS.ESTATUS, producto_balance.getEstatus());
        values.put(turedUserBD.USUARIO_BALANCE_PRODUCTOS.TIPO_PRODUCTO, producto_balance.getTipo_producto());
        values.put(turedUserBD.USUARIO_BALANCE_PRODUCTOS.ORDEN_PRESENTACION, producto_balance.getOrden_presentacion());
        values.put(turedUserBD.USUARIO_BALANCE_PRODUCTOS.URL_IMG, producto_balance.getUrl_img());


        db.insert(turedUserBD.USUARIO_BALANCE_PRODUCTOS.TABLE_NAME, null, values);

        db.close();
    }


    public BalanceProductos getProductoBalance(String nombre) {

        BalanceProductos producto_balance = null;


        SQLiteDatabase db = this.getReadableDatabase();

        String[] COLUMNS = {
                turedUserBD.USUARIO_BALANCE_PRODUCTOS.ID,
                turedUserBD.USUARIO_BALANCE_PRODUCTOS.USER_ID,
                turedUserBD.USUARIO_BALANCE_PRODUCTOS.PRODUCTOS_ID,
                turedUserBD.USUARIO_BALANCE_PRODUCTOS.SALDO,
                turedUserBD.USUARIO_BALANCE_PRODUCTOS.NOMBRE,
                turedUserBD.USUARIO_BALANCE_PRODUCTOS.DESCRIPCION,
                turedUserBD.USUARIO_BALANCE_PRODUCTOS.ESTATUS,
                turedUserBD.USUARIO_BALANCE_PRODUCTOS.TIPO_PRODUCTO,
                turedUserBD.USUARIO_BALANCE_PRODUCTOS.ORDEN_PRESENTACION,
                turedUserBD.USUARIO_BALANCE_PRODUCTOS.URL_IMG,
        };


        Cursor cursor =
                db.query(turedUserBD.USUARIO_BALANCE_PRODUCTOS.TABLE_NAME,
                        COLUMNS,
                        " nombre = ?",
                        new String[]{String.valueOf(nombre)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            producto_balance = new BalanceProductos();
            producto_balance.setId(cursor.getString(0));
            producto_balance.setUser_id(cursor.getString(1));
            producto_balance.setProductos_id(cursor.getString(2));
            producto_balance.setSaldo(cursor.getString(3));
            producto_balance.setNombre(cursor.getString(4));
            producto_balance.setDescripion(cursor.getString(5));
            producto_balance.setEstatus(cursor.getString(6));
            producto_balance.setTipo_producto(cursor.getString(7));
            producto_balance.setOrden_presentacion(cursor.getString(8));
            producto_balance.setUrl_img(cursor.getString(9));
            cursor.close();
        }

        return producto_balance;
    }

    public Boolean getProductoID(String id) {


        SQLiteDatabase db = this.getReadableDatabase();

        String[] COLUMNS = {
                turedUserBD.USUARIO_BALANCE_PRODUCTOS.ID
        };


        Cursor cursor =
                db.query(turedUserBD.USUARIO_BALANCE_PRODUCTOS.TABLE_NAME,
                        COLUMNS,
                        " id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);

        return cursor != null && cursor.getCount() > 0;

    }

    public List getAllBalanceProductos() {
        List productos_balance = new ArrayList();


        String query = "SELECT  * FROM " + turedUserBD.USUARIO_BALANCE_PRODUCTOS.TABLE_NAME + " order by " + turedUserBD.USUARIO_BALANCE_PRODUCTOS.PRODUCTOS_ID + " asc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        BalanceProductos producto_balance_one = null;

        if (cursor.moveToFirst()) {
            do {
                producto_balance_one = new BalanceProductos();
                producto_balance_one.setId(cursor.getString(0));
                producto_balance_one.setUser_id(cursor.getString(1));
                producto_balance_one.setProductos_id(cursor.getString(2));
                producto_balance_one.setSaldo(cursor.getString(3));
                producto_balance_one.setNombre(cursor.getString(4));
                producto_balance_one.setDescripion(cursor.getString(5));
                producto_balance_one.setEstatus(cursor.getString(6));
                producto_balance_one.setTipo_producto(cursor.getString(7));
                producto_balance_one.setOrden_presentacion(cursor.getString(8));
                producto_balance_one.setUrl_img(cursor.getString(9));

                productos_balance.add(producto_balance_one);
            } while (cursor.moveToNext());
            cursor.close();
        }


        return productos_balance;
    }


    //****Funciones para informacion productos home*********************************************************************************

    public void insertProductoHome(HomeProductos producto_home) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.ID, producto_home.getId());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.PROVEEDOR, producto_home.getProveedor());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.OPERADOR, producto_home.getOperador());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.SERVICIO, producto_home.getServicio());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.ESTATUS, producto_home.getEstatus());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.ID_PRODUCTO, producto_home.getId_producto());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.ORDEN_PRESENTACION, producto_home.getOrden_presentacion());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.TABLA, producto_home.getTabla());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.PRODUCTOS_ID, producto_home.getProductos_id());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.URL_IMG, producto_home.getUrl_img());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.CATEGORIA, producto_home.getCategoria());

        db.insert(turedUserBD.USUARIO_HOME_PRODUCTOS.TABLE_NAME, null, values);

        db.close();
    }

    public HomeProductos getProductoPackageHome(String operador, String servicio) {

        HomeProductos producto_home = null;


        SQLiteDatabase db = this.getReadableDatabase();

        String[] COLUMNS = {
                turedUserBD.USUARIO_HOME_PRODUCTOS.ID,
                turedUserBD.USUARIO_HOME_PRODUCTOS.PROVEEDOR,
                turedUserBD.USUARIO_HOME_PRODUCTOS.OPERADOR,
                turedUserBD.USUARIO_HOME_PRODUCTOS.SERVICIO,
                turedUserBD.USUARIO_HOME_PRODUCTOS.ESTATUS,
                turedUserBD.USUARIO_HOME_PRODUCTOS.ID_PRODUCTO,
                turedUserBD.USUARIO_HOME_PRODUCTOS.ORDEN_PRESENTACION,
                turedUserBD.USUARIO_HOME_PRODUCTOS.TABLA,
                turedUserBD.USUARIO_HOME_PRODUCTOS.PRODUCTOS_ID,
                turedUserBD.USUARIO_HOME_PRODUCTOS.URL_IMG,
                turedUserBD.USUARIO_HOME_PRODUCTOS.CATEGORIA,

        };


        Cursor cursor =
                db.query(turedUserBD.USUARIO_HOME_PRODUCTOS.TABLE_NAME,
                        COLUMNS,
                        " operador = ? and servicio = ?",
                        new String[]{operador, servicio},
                        null,
                        null,
                        null,
                        null);

        producto_home = new HomeProductos();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();


            producto_home.setId(cursor.getString(0));
            producto_home.setProveedor(cursor.getString(1));
            producto_home.setOperador(cursor.getString(2));
            producto_home.setServicio(cursor.getString(3));
            producto_home.setEstatus(cursor.getString(4));
            producto_home.setId_producto(cursor.getString(5));
            producto_home.setOrden_presentacion(cursor.getString(6));
            producto_home.setTabla(cursor.getString(7));
            producto_home.setProductos_id(cursor.getString(8));
            producto_home.setUrl_img(cursor.getString(9));
            producto_home.setCategoria(cursor.getString(10));
            producto_home.setEstado(true);
            producto_home.setMsjBD("EXITOSO");

        } else {
            producto_home.setEstado(false);
            producto_home.setMsjBD("NO SE ENCUENTRAN DATOS DEL PRODUCTO");
        }

        return producto_home;
    }


    public HomeProductos getProductoHome(String id) {

        HomeProductos producto_home = null;


        SQLiteDatabase db = this.getReadableDatabase();

        String[] COLUMNS = {
                turedUserBD.USUARIO_HOME_PRODUCTOS.ID,
                turedUserBD.USUARIO_HOME_PRODUCTOS.PROVEEDOR,
                turedUserBD.USUARIO_HOME_PRODUCTOS.OPERADOR,
                turedUserBD.USUARIO_HOME_PRODUCTOS.SERVICIO,
                turedUserBD.USUARIO_HOME_PRODUCTOS.ESTATUS,
                turedUserBD.USUARIO_HOME_PRODUCTOS.ID_PRODUCTO,
                turedUserBD.USUARIO_HOME_PRODUCTOS.ORDEN_PRESENTACION,
                turedUserBD.USUARIO_HOME_PRODUCTOS.TABLA,
                turedUserBD.USUARIO_HOME_PRODUCTOS.PRODUCTOS_ID,
                turedUserBD.USUARIO_HOME_PRODUCTOS.URL_IMG,
                turedUserBD.USUARIO_HOME_PRODUCTOS.CATEGORIA,

        };


        Cursor cursor =
                db.query(turedUserBD.USUARIO_HOME_PRODUCTOS.TABLE_NAME,
                        COLUMNS,
                        " id = ?",
                        new String[]{id},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null) {
            cursor.moveToFirst();

            producto_home = new HomeProductos();
            producto_home.setId(cursor.getString(0));
            producto_home.setProveedor(cursor.getString(1));
            producto_home.setOperador(cursor.getString(2));
            producto_home.setServicio(cursor.getString(3));
            producto_home.setEstatus(cursor.getString(4));
            producto_home.setId_producto(cursor.getString(5));
            producto_home.setOrden_presentacion(cursor.getString(6));
            producto_home.setTabla(cursor.getString(7));
            producto_home.setProductos_id(cursor.getString(8));
            producto_home.setUrl_img(cursor.getString(9));
            producto_home.setCategoria(cursor.getString(10));
            producto_home.setEstado(true);
            producto_home.setMsjBD("EXITOSO");
            cursor.close();
        } else {
            producto_home.setEstado(false);
            producto_home.setMsjBD("NO SE ENCUENTRAN DATOS DEL PRODUCTO");
        }

        return producto_home;
    }


    public List getAllHomeProductos() {
        List productos_home = new ArrayList();


        String query = "SELECT  * FROM " + turedUserBD.USUARIO_HOME_PRODUCTOS.TABLE_NAME + " order by " + turedUserBD.USUARIO_HOME_PRODUCTOS.ORDEN_PRESENTACION + " asc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        HomeProductos producto_home = null;

        if (cursor.moveToFirst()) {
            do {
                producto_home = new HomeProductos();
                producto_home.setId(cursor.getString(0));
                producto_home.setProveedor(cursor.getString(1));
                producto_home.setOperador(cursor.getString(2));
                producto_home.setServicio(cursor.getString(3));
                producto_home.setEstatus(cursor.getString(4));
                producto_home.setId_producto(cursor.getString(5));
                producto_home.setOrden_presentacion(cursor.getString(6));
                producto_home.setTabla(cursor.getString(7));
                producto_home.setProductos_id(cursor.getString(8));
                producto_home.setUrl_img(cursor.getString(9));
                producto_home.setCategoria(cursor.getString(10));

                productos_home.add(producto_home);
            } while (cursor.moveToNext());
            cursor.close();
        }


        return productos_home;
    }

    public List getAllHomeProductosPack() {
        List productos_home = new ArrayList();


        String query = "SELECT  * FROM " + turedUserBD.USUARIO_HOME_PRODUCTOS.TABLE_NAME + "  order by " + turedUserBD.USUARIO_HOME_PRODUCTOS.ORDEN_PRESENTACION + " asc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        HomeProductos producto_home = null;

        if (cursor.moveToFirst()) {
            do {
                producto_home = new HomeProductos();
                producto_home.setId(cursor.getString(0));
                producto_home.setProveedor(cursor.getString(1));
                producto_home.setOperador(cursor.getString(2));
                producto_home.setServicio(cursor.getString(3));
                producto_home.setEstatus(cursor.getString(4));
                producto_home.setId_producto(cursor.getString(5));
                producto_home.setOrden_presentacion(cursor.getString(6));
                producto_home.setTabla(cursor.getString(7));
                producto_home.setProductos_id(cursor.getString(8));
                producto_home.setUrl_img(cursor.getString(9));
                producto_home.setCategoria(cursor.getString(10));


                productos_home.add(producto_home);
            } while (cursor.moveToNext());
            cursor.close();
        }


        return productos_home;
    }


    public int updateProductoHome(HomeProductos producto_home) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.ID, producto_home.getId());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.PROVEEDOR, producto_home.getProveedor());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.OPERADOR, producto_home.getOperador());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.SERVICIO, producto_home.getServicio());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.ESTATUS, producto_home.getEstatus());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.ID_PRODUCTO, producto_home.getId_producto());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.ORDEN_PRESENTACION, producto_home.getOrden_presentacion());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.TABLA, producto_home.getTabla());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.PRODUCTOS_ID, producto_home.getProductos_id());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.URL_IMG, producto_home.getUrl_img());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS.CATEGORIA, producto_home.getCategoria());

        int i = db.update(turedUserBD.USUARIO_HOME_PRODUCTOS.TABLE_NAME, //table
                values,
                turedUserBD.USUARIO_HOME_PRODUCTOS.PRODUCTOS_ID + " = ?",
                new String[]{String.valueOf(producto_home.getProductos_id())});

        db.close();
        return i;
    }


    //****Funciones para informacion productos home parametros*********************************************************************************
    public void insertProductoHomeParametros(HomeProductosParametros producto_home_parametros) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.ID, producto_home_parametros.getId());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.N, producto_home_parametros.getN());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.T, producto_home_parametros.getT());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.LMN, producto_home_parametros.getLmn());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.LMX, producto_home_parametros.getLmx());
        values.put(turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.TABLA, producto_home_parametros.getTabla());

        db.insert(turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.TABLE_NAME, null, values);

        db.close();
    }

    public HomeProductosParametros getProductoHomeParametros(String id, String tabla) {

        HomeProductosParametros producto_home_parametros = null;


        SQLiteDatabase db = this.getReadableDatabase();

        String[] COLUMNS = {
                turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.ID,
                turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.N,
                turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.T,
                turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.LMN,
                turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.LMX,
                turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.TABLA,
        };


        Cursor cursor =
                db.query(turedUserBD.USUARIO_HOME_PRODUCTOS_PARAMETROS.TABLE_NAME,
                        COLUMNS,
                        " id = ? and tabla = ?",
                        new String[]{id,tabla},
                        null,
                        null,
                        null,
                        null);


        producto_home_parametros = new HomeProductosParametros();
        if (cursor != null) {
            cursor.moveToFirst();

            producto_home_parametros.setId(cursor.getString(0));
            producto_home_parametros.setN(cursor.getString(1));
            producto_home_parametros.setT(cursor.getString(2));
            producto_home_parametros.setLmn(cursor.getString(3));
            producto_home_parametros.setLmx(cursor.getString(4));
            producto_home_parametros.setTabla(cursor.getString(5));
            cursor.close();

        }

        return producto_home_parametros;
    }



    //****Funciones para informacion paquetes operador recarga*********************************************************************************

    public void insertPackOperRecarga(PackOperRecarga pack_operador_recarga) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(turedUserBD.PACK_OPER_RECARGA.ID, pack_operador_recarga.getId());
        values.put(turedUserBD.PACK_OPER_RECARGA.VALOR, pack_operador_recarga.getValor());
        values.put(turedUserBD.PACK_OPER_RECARGA.DESCRIPCION, pack_operador_recarga.getDescripcion());
        values.put(turedUserBD.PACK_OPER_RECARGA.CODIGO, pack_operador_recarga.getCodigo());
        values.put(turedUserBD.PACK_OPER_RECARGA.ESTATUS, pack_operador_recarga.getEstatus());
        values.put(turedUserBD.PACK_OPER_RECARGA.ID_PRODUCTO_RECARGAS, pack_operador_recarga.getId_producto_recargas());
        values.put(turedUserBD.PACK_OPER_RECARGA.CATEGORIA, pack_operador_recarga.getCategoria());
        values.put(turedUserBD.PACK_OPER_RECARGA.ORDEN_CATEGORIA, pack_operador_recarga.getOrden_categoria());

        db.insert(turedUserBD.PACK_OPER_RECARGA.TABLE_NAME, null, values);

        db.close();
    }


    public PackOperRecarga getPackOperRecarga(String id) {

        PackOperRecarga pack_operador_recarga = null;


        SQLiteDatabase db = this.getReadableDatabase();

        String[] COLUMNS = {
                turedUserBD.PACK_OPER_RECARGA.ID,
                turedUserBD.PACK_OPER_RECARGA.VALOR,
                turedUserBD.PACK_OPER_RECARGA.DESCRIPCION,
                turedUserBD.PACK_OPER_RECARGA.CODIGO,
                turedUserBD.PACK_OPER_RECARGA.ESTATUS,
                turedUserBD.PACK_OPER_RECARGA.ID_PRODUCTO_RECARGAS,
                turedUserBD.PACK_OPER_RECARGA.CATEGORIA,
                turedUserBD.PACK_OPER_RECARGA.ORDEN_CATEGORIA,
        };


        Cursor cursor =
                db.query(turedUserBD.PACK_OPER_RECARGA.TABLE_NAME,
                        COLUMNS,
                        " id = ?",
                        new String[]{id},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null) {
            cursor.moveToFirst();

            pack_operador_recarga = new PackOperRecarga();
            pack_operador_recarga.setId(cursor.getString(0));
            pack_operador_recarga.setValor(cursor.getString(1));
            pack_operador_recarga.setDescripcion(cursor.getString(2));
            pack_operador_recarga.setCodigo(cursor.getString(3));
            pack_operador_recarga.setEstatus(cursor.getString(4));
            pack_operador_recarga.setId_producto_recargas(cursor.getString(5));
            pack_operador_recarga.setCategoria(cursor.getString(6));
            pack_operador_recarga.setOrden_categoria(cursor.getString(7));
            cursor.close();

        }
        return pack_operador_recarga;
    }


    public List getAllPackOperRecarga(String id_producto_recargas) {
        List pack_operadores_recarga = new ArrayList();


        String query = "SELECT  * FROM " + turedUserBD.PACK_OPER_RECARGA.TABLE_NAME + " where id_producto_recargas ='" + id_producto_recargas + "' order by " + turedUserBD.PACK_OPER_RECARGA.ORDEN_CATEGORIA + " asc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        PackOperRecarga pack_operadores_recarga_one = null;

        if (cursor.moveToFirst()) {
            do {
                pack_operadores_recarga_one = new PackOperRecarga();
                pack_operadores_recarga_one.setId(cursor.getString(0));
                pack_operadores_recarga_one.setValor(cursor.getString(1));
                pack_operadores_recarga_one.setDescripcion(cursor.getString(2));
                pack_operadores_recarga_one.setCodigo(cursor.getString(3));
                pack_operadores_recarga_one.setEstatus(cursor.getString(4));
                pack_operadores_recarga_one.setId_producto_recargas(cursor.getString(5));
                pack_operadores_recarga_one.setCategoria(cursor.getString(6));
                pack_operadores_recarga_one.setOrden_categoria(cursor.getString(7));

                pack_operadores_recarga.add(pack_operadores_recarga_one);
            } while (cursor.moveToNext());
            cursor.close();
        }


        return pack_operadores_recarga;
    }


    public int updatePackOperRecarga(PackOperRecarga pack_operador_recarga) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(turedUserBD.PACK_OPER_RECARGA.ID, pack_operador_recarga.getId());
        values.put(turedUserBD.PACK_OPER_RECARGA.VALOR, pack_operador_recarga.getValor());
        values.put(turedUserBD.PACK_OPER_RECARGA.DESCRIPCION, pack_operador_recarga.getDescripcion());
        values.put(turedUserBD.PACK_OPER_RECARGA.CODIGO, pack_operador_recarga.getCodigo());
        values.put(turedUserBD.PACK_OPER_RECARGA.ESTATUS, pack_operador_recarga.getEstatus());
        values.put(turedUserBD.PACK_OPER_RECARGA.ID_PRODUCTO_RECARGAS, pack_operador_recarga.getId_producto_recargas());
        values.put(turedUserBD.PACK_OPER_RECARGA.CATEGORIA, pack_operador_recarga.getId_producto_recargas());
        values.put(turedUserBD.PACK_OPER_RECARGA.ORDEN_CATEGORIA, pack_operador_recarga.getId_producto_recargas());

        int i = db.update(turedUserBD.PACK_OPER_RECARGA.TABLE_NAME, //table
                values,
                turedUserBD.PACK_OPER_RECARGA.ID + " = ?",
                new String[]{String.valueOf(pack_operador_recarga.getId())});

        db.close();
        return i;
    }


    //****Funciones para informacion servicios paquetes operador recarga*********************************************************************************

    public void insertServPackOperRecarga(ServOperPackRecarga ser_pack_operador_recarga) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.ID, ser_pack_operador_recarga.getId());
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.PROVEEDOR, ser_pack_operador_recarga.getProveedor());
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.OPERADOR, ser_pack_operador_recarga.getOperador());
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.SERVICIO, ser_pack_operador_recarga.getServicio());
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.ESTATUS, ser_pack_operador_recarga.getEstatus());
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.ID_PRODUCTO, ser_pack_operador_recarga.getId_producto());
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.ID_RECARGAS, ser_pack_operador_recarga.getId_recargas());
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.TABLA, ser_pack_operador_recarga.getTabla());

        db.insert(turedUserBD.SERV_OPER_PACK_RECARGA.TABLE_NAME, null, values);

        db.close();
    }


    public ServOperPackRecarga getServPackOperRecarga(String operador, String servicio) {

        ServOperPackRecarga serv_pack_operador_recarga = null;


        SQLiteDatabase db = this.getReadableDatabase();

        String[] COLUMNS = {
                turedUserBD.SERV_OPER_PACK_RECARGA.ID,
                turedUserBD.SERV_OPER_PACK_RECARGA.PROVEEDOR,
                turedUserBD.SERV_OPER_PACK_RECARGA.OPERADOR,
                turedUserBD.SERV_OPER_PACK_RECARGA.SERVICIO,
                turedUserBD.SERV_OPER_PACK_RECARGA.ESTATUS,
                turedUserBD.SERV_OPER_PACK_RECARGA.ID_PRODUCTO,
                turedUserBD.SERV_OPER_PACK_RECARGA.ID_RECARGAS,
                turedUserBD.SERV_OPER_PACK_RECARGA.TABLA,
        };


        Cursor cursor =
                db.query(turedUserBD.SERV_OPER_PACK_RECARGA.TABLE_NAME,
                        COLUMNS,
                        " operador = ? and servicio = ?",
                        new String[]{operador, servicio},
                        null,
                        null,
                        null,
                        null);

        serv_pack_operador_recarga = new ServOperPackRecarga();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            serv_pack_operador_recarga.setId(cursor.getString(0));
            serv_pack_operador_recarga.setProveedor(cursor.getString(1));
            serv_pack_operador_recarga.setOperador(cursor.getString(2));
            serv_pack_operador_recarga.setServicio(cursor.getString(3));
            serv_pack_operador_recarga.setEstatus(cursor.getString(4));
            serv_pack_operador_recarga.setId_producto(cursor.getString(5));
            serv_pack_operador_recarga.setId_recargas(cursor.getString(6));
            serv_pack_operador_recarga.setTabla(cursor.getString(7));
            cursor.close();
        } else {
            serv_pack_operador_recarga.setEstatus("FALSE");
        }
        return serv_pack_operador_recarga;
    }


    public List getAllServPackOperRecarga() {
        List serv_pack_operadores_recarga = new ArrayList();


        String query = "SELECT  * FROM " + turedUserBD.SERV_OPER_PACK_RECARGA.TABLE_NAME + " order by " + turedUserBD.SERV_OPER_PACK_RECARGA.ID + " asc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ServOperPackRecarga serv_pack_operadores_recarga_one = null;

        if (cursor.moveToFirst()) {
            do {
                serv_pack_operadores_recarga_one = new ServOperPackRecarga();
                serv_pack_operadores_recarga_one.setId(cursor.getString(0));
                serv_pack_operadores_recarga_one.setProveedor(cursor.getString(1));
                serv_pack_operadores_recarga_one.setOperador(cursor.getString(2));
                serv_pack_operadores_recarga_one.setServicio(cursor.getString(3));
                serv_pack_operadores_recarga_one.setEstatus(cursor.getString(4));
                serv_pack_operadores_recarga_one.setId_producto(cursor.getString(5));
                serv_pack_operadores_recarga_one.setId_recargas(cursor.getString(6));
                serv_pack_operadores_recarga_one.setTabla(cursor.getString(7));

                serv_pack_operadores_recarga.add(serv_pack_operadores_recarga_one);
            } while (cursor.moveToNext());
            cursor.close();
        }


        return serv_pack_operadores_recarga;
    }


    public int updateServPackOperRecarga(ServOperPackRecarga serv_pack_operador_recarga) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.ID, serv_pack_operador_recarga.getId());
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.PROVEEDOR, serv_pack_operador_recarga.getProveedor());
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.OPERADOR, serv_pack_operador_recarga.getOperador());
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.SERVICIO, serv_pack_operador_recarga.getServicio());
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.ESTATUS, serv_pack_operador_recarga.getEstatus());
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.ID_PRODUCTO, serv_pack_operador_recarga.getId_producto());
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.ID_RECARGAS, serv_pack_operador_recarga.getId_recargas());
        values.put(turedUserBD.SERV_OPER_PACK_RECARGA.TABLA, serv_pack_operador_recarga.getTabla());

        int i = db.update(turedUserBD.SERV_OPER_PACK_RECARGA.TABLE_NAME, //table
                values,
                turedUserBD.SERV_OPER_PACK_RECARGA.ID + " = ?",
                new String[]{String.valueOf(serv_pack_operador_recarga.getId())});

        db.close();
        return i;
    }


    //****Funciones para informacion valores predeterminados de recarga operador recarga*********************************************************************************

    public void insertValueDefaultOperRecarga(ValueDefaultOperRecarga value_default_operador_recarga) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.ID, value_default_operador_recarga.getId());
        values.put(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.VALOR, value_default_operador_recarga.getValor());
        values.put(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.DESCRIPCION, value_default_operador_recarga.getDescripcion());
        values.put(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.CODIGO, value_default_operador_recarga.getCodigo());
        values.put(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.ESTATUS, value_default_operador_recarga.getEstatus());
        values.put(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.ID_PRODUCTO_RECARGAS, value_default_operador_recarga.getId_producto_recargas());
        values.put(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.CATEGORIA, value_default_operador_recarga.getCategoria());
        values.put(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.ORDEN_CATEGORIA, value_default_operador_recarga.getOrden_categoria());
        values.put(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.TABLA, value_default_operador_recarga.getTabla());

        db.insert(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.TABLE_NAME, null, values);

        db.close();
    }


    public ValueDefaultOperRecarga getValueDefaultOperRecarga(String id) {

        ValueDefaultOperRecarga value_default_operador_recarga = null;


        SQLiteDatabase db = this.getReadableDatabase();

        String[] COLUMNS = {
                turedUserBD.VALUE_DEFAULT_OPER_RECARGA.ID,
                turedUserBD.VALUE_DEFAULT_OPER_RECARGA.VALOR,
                turedUserBD.VALUE_DEFAULT_OPER_RECARGA.DESCRIPCION,
                turedUserBD.VALUE_DEFAULT_OPER_RECARGA.CODIGO,
                turedUserBD.VALUE_DEFAULT_OPER_RECARGA.ESTATUS,
                turedUserBD.VALUE_DEFAULT_OPER_RECARGA.ID_PRODUCTO_RECARGAS,
                turedUserBD.VALUE_DEFAULT_OPER_RECARGA.CATEGORIA,
                turedUserBD.VALUE_DEFAULT_OPER_RECARGA.ORDEN_CATEGORIA,
                turedUserBD.VALUE_DEFAULT_OPER_RECARGA.TABLA,
        };


        Cursor cursor =
                db.query(turedUserBD.VALUE_DEFAULT_OPER_RECARGA.TABLE_NAME,
                        COLUMNS,
                        " id = ?",
                        new String[]{id},
                        null,
                        null,
                        null,
                        null);

        value_default_operador_recarga = new ValueDefaultOperRecarga();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();


            value_default_operador_recarga.setId(cursor.getString(0));
            value_default_operador_recarga.setValor(cursor.getString(1));
            value_default_operador_recarga.setDescripcion(cursor.getString(2));
            value_default_operador_recarga.setCodigo(cursor.getString(3));
            value_default_operador_recarga.setEstatus(cursor.getString(4));
            value_default_operador_recarga.setId_producto_recargas(cursor.getString(5));
            value_default_operador_recarga.setCategoria(cursor.getString(6));
            value_default_operador_recarga.setOrden_categoria(cursor.getString(7));
            value_default_operador_recarga.setTabla(cursor.getString(8));
            cursor.close();

        }
        return value_default_operador_recarga;
    }


    public List getAllValueDefaultOperRecarga(String id_producto_recargas, String tabla) {
        List value_default_operador_recarga = new ArrayList();


        String query = "SELECT  * FROM " + turedUserBD.VALUE_DEFAULT_OPER_RECARGA.TABLE_NAME + " where id_producto_recargas ='" + id_producto_recargas + "' and tabla='" + tabla + "' order by length(" + turedUserBD.VALUE_DEFAULT_OPER_RECARGA.VALOR + ") asc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        ValueDefaultOperRecarga value_default_operador_recarga_one = null;

        if (cursor.moveToFirst()) {
            do {
                value_default_operador_recarga_one = new ValueDefaultOperRecarga();
                value_default_operador_recarga_one.setId(cursor.getString(0));
                value_default_operador_recarga_one.setValor(cursor.getString(1));
                value_default_operador_recarga_one.setDescripcion(cursor.getString(2));
                value_default_operador_recarga_one.setCodigo(cursor.getString(3));
                value_default_operador_recarga_one.setEstatus(cursor.getString(4));
                value_default_operador_recarga_one.setId_producto_recargas(cursor.getString(5));
                value_default_operador_recarga_one.setCategoria(cursor.getString(6));
                value_default_operador_recarga_one.setOrden_categoria(cursor.getString(7));
                value_default_operador_recarga_one.setTabla(cursor.getString(8));

                value_default_operador_recarga.add(value_default_operador_recarga_one);
            } while (cursor.moveToNext());
            cursor.close();
        }


        return value_default_operador_recarga;
    }


    //****Funciones para informacion paquetes operador recarga*********************************************************************************

    public void insertReportTransProductos(TransactionsReport repor_trans_productos) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.ID, repor_trans_productos.getId());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.CODIGO_TRANSACCION, repor_trans_productos.getCodigo_transaccion());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.TIPO_IDENTIFICACION, repor_trans_productos.getTipo_identificacion());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.MIN_RECARGA, repor_trans_productos.getMin_recarga());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.NUMERO_CONTROL, repor_trans_productos.getNumero_control());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.VALOR_RECARGA, repor_trans_productos.getValor_recarga());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.FECHA_SOLICITUD, repor_trans_productos.getFecha_solicitud());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.DESCRIPCION_TERMINAL, repor_trans_productos.getDescripcion_terminal());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.PROVEEDOR, repor_trans_productos.getProveedor());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.ID_MONTO, repor_trans_productos.getId_monto());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.FECHA_RESPUESTA, repor_trans_productos.getFecha_respuesta());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.CODIGO_RESPUESTA, repor_trans_productos.getCodigo_respuesta());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.NUMERO_AUTORIZACION, repor_trans_productos.getNumero_autorizacion());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.ID_USER, repor_trans_productos.getId_user());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.ESTATUS, repor_trans_productos.getEstatus());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.RESPUESTA, repor_trans_productos.getRespuesta());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.ID_PRODUCTO, repor_trans_productos.getId_producto());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.SALDO_ANTES, repor_trans_productos.getSaldo_antes());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.SALDO_DESPUES, repor_trans_productos.getSaldo_despues());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.VALOR, repor_trans_productos.getValor());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.DESCRIPCION, repor_trans_productos.getDescripcion());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.CODIGO, repor_trans_productos.getCodigo());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.ID_PRODUCTO_RECARGAS, repor_trans_productos.getId_producto_recargas());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.OPERADOR, repor_trans_productos.getOperador());
        values.put(turedUserBD.REPORT_TRANS_PRODUCTOS.SERVICIO, repor_trans_productos.getServicio());


        db.insert(turedUserBD.REPORT_TRANS_PRODUCTOS.TABLE_NAME, null, values);

        db.close();
    }


    public List getAllTransReportProductos() {
        List report_trans_productos = new ArrayList();


        String query = "SELECT  * FROM " + turedUserBD.REPORT_TRANS_PRODUCTOS.TABLE_NAME + "  order by " + turedUserBD.REPORT_TRANS_PRODUCTOS.FECHA_SOLICITUD + " desc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        TransactionsReport report_trans_productos_one = null;

        if (cursor.moveToFirst()) {
            do {
                report_trans_productos_one = new TransactionsReport();
                report_trans_productos_one.setId(cursor.getString(0));
                report_trans_productos_one.setCodigo_transaccion(cursor.getString(1));
                report_trans_productos_one.setTipo_identificacion(cursor.getString(2));
                report_trans_productos_one.setMin_recarga(cursor.getString(3));
                report_trans_productos_one.setNumero_control(cursor.getString(4));
                report_trans_productos_one.setValor_recarga(cursor.getString(5));
                report_trans_productos_one.setFecha_solicitud(cursor.getString(6));
                report_trans_productos_one.setDescripcion_terminal(cursor.getString(7));
                report_trans_productos_one.setProveedor(cursor.getString(8));
                report_trans_productos_one.setId_monto(cursor.getString(9));
                report_trans_productos_one.setFecha_respuesta(cursor.getString(10));
                report_trans_productos_one.setCodigo_respuesta(cursor.getString(11));
                report_trans_productos_one.setNumero_autorizacion(cursor.getString(12));
                report_trans_productos_one.setId_user(cursor.getString(13));
                report_trans_productos_one.setEstatus(cursor.getString(14));
                report_trans_productos_one.setRespuesta(cursor.getString(15));
                report_trans_productos_one.setId_producto(cursor.getString(16));
                report_trans_productos_one.setSaldo_antes(cursor.getString(17));
                report_trans_productos_one.setSaldo_despues(cursor.getString(18));
                report_trans_productos_one.setValor(cursor.getString(19));
                report_trans_productos_one.setDescripcion(cursor.getString(20));
                report_trans_productos_one.setCodigo(cursor.getString(21));
                report_trans_productos_one.setId_producto_recargas(cursor.getString(22));
                report_trans_productos_one.setOperador(cursor.getString(23));
                report_trans_productos_one.setServicio(cursor.getString(24));


                report_trans_productos.add(report_trans_productos_one);
            } while (cursor.moveToNext());
            cursor.close();
        }


        return report_trans_productos;
    }


    //****Funciones para informacion oficinas para certificados SNR*********************************************************************************

    public void insertOficinasCertificadosSNR(CertificadosSNR oficina_certificados_snr_param) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(turedUserBD.OFICINAS_CERTIFICADOS_SNR.ID, oficina_certificados_snr_param.getId());
        values.put(turedUserBD.OFICINAS_CERTIFICADOS_SNR.CIUDAD, oficina_certificados_snr_param.getCiudad());
        values.put(turedUserBD.OFICINAS_CERTIFICADOS_SNR.CODIGO, oficina_certificados_snr_param.getCodigo());
        values.put(turedUserBD.OFICINAS_CERTIFICADOS_SNR.ESTATUS, oficina_certificados_snr_param.getEstatus());

        db.insert(turedUserBD.OFICINAS_CERTIFICADOS_SNR.TABLE_NAME, null, values);

        db.close();
    }


    public CertificadosSNR getOficinasCertificadosSNR(String id) {

        CertificadosSNR oficina_certificados_snr_local = null;


        SQLiteDatabase db = this.getReadableDatabase();

        String[] COLUMNS = {
                turedUserBD.OFICINAS_CERTIFICADOS_SNR.ID,
                turedUserBD.OFICINAS_CERTIFICADOS_SNR.CIUDAD,
                turedUserBD.OFICINAS_CERTIFICADOS_SNR.CODIGO,
                turedUserBD.OFICINAS_CERTIFICADOS_SNR.ESTATUS,
        };


        Cursor cursor =
                db.query(turedUserBD.OFICINAS_CERTIFICADOS_SNR.TABLE_NAME,
                        COLUMNS,
                        " id = ?",
                        new String[]{id},
                        null,
                        null,
                        null,
                        null);

        oficina_certificados_snr_local = new CertificadosSNR();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();


            oficina_certificados_snr_local.setId(cursor.getString(0));
            oficina_certificados_snr_local.setCiudad(cursor.getString(1));
            oficina_certificados_snr_local.setCodigo(cursor.getString(2));
            oficina_certificados_snr_local.setEstatus(cursor.getString(3));
            cursor.close();

        }
        return oficina_certificados_snr_local;
    }


    public List getAllOficinasCertificadosSNR() {
        List oficinas_certificados_snr_list = new ArrayList();


        String query = "SELECT  * FROM " + turedUserBD.OFICINAS_CERTIFICADOS_SNR.TABLE_NAME + " order by CAST(id AS INT) asc";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        CertificadosSNR oficinas_certificados_snr_one = null;

        if (cursor.moveToFirst()) {
            do {
                oficinas_certificados_snr_one = new CertificadosSNR();
                oficinas_certificados_snr_one.setId(cursor.getString(0));
                oficinas_certificados_snr_one.setCiudad(cursor.getString(1));
                oficinas_certificados_snr_one.setCodigo(cursor.getString(2));
                oficinas_certificados_snr_one.setEstatus(cursor.getString(3));


                oficinas_certificados_snr_list.add(oficinas_certificados_snr_one);
            } while (cursor.moveToNext());
            cursor.close();
        }


        return oficinas_certificados_snr_list;
    }

    public void deleteOficinasCertificadosSNR() {

        String query = "DELETE  FROM " + turedUserBD.OFICINAS_CERTIFICADOS_SNR.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        db.rawQuery(query, null);
    }


//****Funciones para USUARIO SESION*********************************************************************************

    public void insertUsuarioSesion(String fecha, String hora, String id, String version, String estado, String operador, String token) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(turedUserBD.USUARIO_SESION.FECHA, fecha);
        values.put(turedUserBD.USUARIO_SESION.HORA, hora);
        values.put(turedUserBD.USUARIO_SESION.ID, id);
        values.put(turedUserBD.USUARIO_SESION.VERSION, version);
        values.put(turedUserBD.USUARIO_SESION.ESTADO, estado);
        values.put(turedUserBD.USUARIO_SESION.OPERADOR, operador);
        values.put(turedUserBD.USUARIO_SESION.TOKEN, token);

        db.insert(turedUserBD.USUARIO_SESION.TABLE_NAME, null, values);

        db.close();
    }


    public List getUsuarioSesion(String id, String fecha) {
        List usuario_sesion_list = new ArrayList();


        String query = "SELECT  * FROM " + turedUserBD.USUARIO_SESION.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        UsuarioSesion usuario_sesion_one = null;
        if (cursor.moveToFirst()) {
            do {
                usuario_sesion_one = new UsuarioSesion();
                usuario_sesion_one.setFecha(cursor.getString(0));
                usuario_sesion_one.setHora(cursor.getString(1));
                usuario_sesion_one.setId(cursor.getString(2));
                usuario_sesion_one.setVersion(cursor.getString(3));
                usuario_sesion_one.setEstado(cursor.getString(4));
                usuario_sesion_one.setOperador(cursor.getString(5));
                usuario_sesion_one.setToken(cursor.getString(6));
                usuario_sesion_list.add(usuario_sesion_one);
            } while (cursor.moveToNext());
            cursor.close();

        }
        return usuario_sesion_list;
    }


    public List getUsuarioSesionActivo(String estado) {
        List usuario_sesion_list = new ArrayList();
        System.out.println("Usuario sesion activo");

        String query = "SELECT  * FROM " + turedUserBD.USUARIO_SESION.TABLE_NAME + " where estado = '" + estado + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        UsuarioSesion usuario_sesion_one = null;
        if (cursor.moveToFirst()) {
            do {
                usuario_sesion_one = new UsuarioSesion();
                usuario_sesion_one.setFecha(cursor.getString(0));
                System.out.println("Fecha:" + usuario_sesion_one.getFecha());
                usuario_sesion_one.setHora(cursor.getString(1));
                System.out.println("Hora:" + usuario_sesion_one.getHora());
                usuario_sesion_one.setId(cursor.getString(2));
                System.out.println("Id:" + usuario_sesion_one.getId());
                usuario_sesion_one.setVersion(cursor.getString(3));
                System.out.println("Version:" + usuario_sesion_one.getVersion());
                usuario_sesion_one.setEstado(cursor.getString(4));
                System.out.println("Estado:" + usuario_sesion_one.getEstado());
                usuario_sesion_one.setOperador(cursor.getString(5));
                System.out.println("Operador:" + usuario_sesion_one.getOperador());
                usuario_sesion_one.setToken(cursor.getString(6));
                System.out.println("Token:" + usuario_sesion_one.getToken());
                usuario_sesion_list.add(usuario_sesion_one);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return usuario_sesion_list;
    }

    public void updateSesionUsuario(String fecha, String id, String version, String estado) {

        SQLiteDatabase db = this.getWritableDatabase();

        String qr = "update " + turedUserBD.USUARIO_SESION.TABLE_NAME + " set fecha = '" + fecha + "',id='" + id + "',version='" + version + "',estado ='" + estado + "' " +
                "where fecha = '" + fecha + "' and hora = (select max(hora) from " + turedUserBD.USUARIO_SESION.TABLE_NAME + " where id = '" + id + "' and fecha = '" + fecha + "')";

        db.execSQL(qr);

        db.close();
    }

    public void updateSesionUsuarioDesActivado(String estado) {

        SQLiteDatabase db = this.getWritableDatabase();
        int i = 0;


        String qr = "update " + turedUserBD.USUARIO_SESION.TABLE_NAME + " set estado ='" + estado + "' ";

        db.execSQL(qr);

        db.close();

    }




//****Funciones para categorias productos*********************************************************************************

    public void insertCategoriasProductos(CategoriasProductos categorias_productos) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(turedUserBD.CATEGORIAS_PRODUCTOS.CATEGORIA, categorias_productos.getCategoria());
        values.put(turedUserBD.CATEGORIAS_PRODUCTOS.IMG, categorias_productos.getImg());

        db.insert(turedUserBD.CATEGORIAS_PRODUCTOS.TABLE_NAME, null, values);

        db.close();
    }


    public CategoriasProductos getCategoriaProducto(String categoria) {

        CategoriasProductos categoria_producto = null;


        SQLiteDatabase db = this.getReadableDatabase();

        String[] COLUMNS = {
                turedUserBD.CATEGORIAS_PRODUCTOS.CATEGORIA,
                turedUserBD.CATEGORIAS_PRODUCTOS.IMG,

        };


        Cursor cursor =
                db.query(turedUserBD.USUARIO_INFO.TABLE_NAME,
                        COLUMNS,
                        " categoria = ?",
                        new String[]{String.valueOf(categoria)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null) {
            cursor.moveToFirst();

            categoria_producto = new CategoriasProductos();
            categoria_producto.setCategoria(cursor.getString(0));
            categoria_producto.setImg(cursor.getString(1));
            cursor.close();
        }

        return categoria_producto;
    }

    public List getCategoriasProductos() {
        List categoria_producto_list = new ArrayList();


        String query = "SELECT  * FROM " + turedUserBD.CATEGORIAS_PRODUCTOS.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        CategoriasProductos categoria_producto_one = null;
        if (cursor.moveToFirst()) {
            do {
                categoria_producto_one = new CategoriasProductos();
                categoria_producto_one.setCategoria(cursor.getString(0));
                categoria_producto_one.setImg(cursor.getString(1));
                categoria_producto_list.add(categoria_producto_one);
            } while (cursor.moveToNext());
            cursor.close();

        }
        return categoria_producto_list;
    }





    public void deleteTable(String TABLE_NAME, boolean closeBD) {

        // 1. Obtenemos una reference de la BD con permisos de escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. Borramos el registro
        db.delete(TABLE_NAME, null, null);

        // 3. Cerramos la conexión a la Bd
        if (closeBD) {
            db.close();
        }

    }

    public void deleteBD(int versionOld, int versionNew) {

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            this.onUpgrade(db, 66, 67);
        } catch (Exception e) {
        }

    }

}

