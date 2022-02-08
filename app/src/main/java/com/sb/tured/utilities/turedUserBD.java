package com.sb.tured.utilities;

public class turedUserBD {


    public static final String DATABASE_NAME = "tured";
    public static final int DATABASE_VERSION = 78;


    public static class USUARIO_INFO {

        public static final String TABLE_NAME = "usuario_info";

        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String TOKEN = "token";
        public static final String PRESTAMO_MAXIMO_DIA = "prestamos_maximo_dia";
        public static final String PREGUNTA = "pregunta";
        public static final String DEUDA_NEGATIVA = "deuda_negativa";
        public static final String DEUDA_POSITIVA = "deuda_positiva";
        public static final String PRESTAMO_MAXIMO = "prestamo_maximo";
        public static final String NOMBRE_USUARIO = "nombre_usuario";


    }

    public static final String TURED_TABLE_CREATE_USUARIO_INFO =
            "CREATE TABLE " + USUARIO_INFO.TABLE_NAME + " (" +
                    USUARIO_INFO.EMAIL + " TEXT , " +
                    USUARIO_INFO.PASSWORD + " TEXT, " +
                    USUARIO_INFO.TOKEN + " TEXT, " +
                    USUARIO_INFO.PRESTAMO_MAXIMO_DIA + " TEXT," +
                    USUARIO_INFO.PREGUNTA + " TEXT," +
                    USUARIO_INFO.DEUDA_NEGATIVA + " TEXT," +
                    USUARIO_INFO.DEUDA_POSITIVA + " TEXT," +
                    USUARIO_INFO.PRESTAMO_MAXIMO + " TEXT," +
                    USUARIO_INFO.NOMBRE_USUARIO + " TEXT);";

    public static final String TURED_TABLE_USUARIO_INFO_DROP = "DROP TABLE IF EXISTS " + USUARIO_INFO.TABLE_NAME;


    public static class USUARIO_TRANS_LAST {

        public static final String TABLE_NAME = "transaction_last";

        public static final String ID = "id";
        public static final String OPERADOR = "operador";
        public static final String NUMERO = "numero";
        public static final String FECHA_SOLICITUD = "fecha_solicitud";
        public static final String FECHA_RESPUESTA = "fecha_respuesta";
        public static final String MENSAJE = "mensaje";
        public static final String CODIGO = "codigo";
        public static final String ID_USER = "id_user";
        public static final String VALOR_RECARGA = "valor_recarga";
        public static final String AUTORIZACION = "autorizacion";
        public static final String ESTADO = "estado";
    }

    public static final String TURED_TABLE_CREATE_TRANS_LAST =
            "CREATE TABLE " + USUARIO_TRANS_LAST.TABLE_NAME + " (" +
                    USUARIO_TRANS_LAST.ID + " TEXT , " +
                    USUARIO_TRANS_LAST.OPERADOR + " TEXT, " +
                    USUARIO_TRANS_LAST.NUMERO + " TEXT, " +
                    USUARIO_TRANS_LAST.FECHA_SOLICITUD + " TEXT, " +
                    USUARIO_TRANS_LAST.FECHA_RESPUESTA + " TEXT, " +
                    USUARIO_TRANS_LAST.MENSAJE + " TEXT, " +
                    USUARIO_TRANS_LAST.CODIGO + " TEXT, " +
                    USUARIO_TRANS_LAST.ID_USER + " TEXT, " +
                    USUARIO_TRANS_LAST.VALOR_RECARGA + " TEXT, " +
                    USUARIO_TRANS_LAST.AUTORIZACION + " TEXT," +
                    USUARIO_TRANS_LAST.ESTADO + " TEXT);";

    public static final String TURED_TABLE_TRANS_LAST_DROP = "DROP TABLE IF EXISTS " + USUARIO_TRANS_LAST.TABLE_NAME;


    public static class USUARIO_INFO_PROD {

        public static final String TABLE_NAME = "producto_info";

        public static final String ID = "id";
        public static final String NOMBRE = "nombre";
        public static final String DESCRIPCION = "descripcion";
        public static final String COMISION_PRODUCTO = "comision_producto";
        public static final String TIPO_COMISION = "tipo_comision";
        public static final String VALOR_PRODUCTO = "valor_producto";
        public static final String URL_IMG = "url_img";

    }

    public static final String TURED_TABLE_CREATE_PRODUCTO_INFO =
            "CREATE TABLE " + USUARIO_INFO_PROD.TABLE_NAME + " (" +
                    USUARIO_INFO_PROD.ID + " TEXT , " +
                    USUARIO_INFO_PROD.NOMBRE + " TEXT," +
                    USUARIO_INFO_PROD.DESCRIPCION + " TEXT," +
                    USUARIO_INFO_PROD.COMISION_PRODUCTO + " TEXT, " +
                    USUARIO_INFO_PROD.TIPO_COMISION + " TEXT," +
                    USUARIO_INFO_PROD.VALOR_PRODUCTO + " TEXT, " +
                    USUARIO_INFO_PROD.URL_IMG + " TEXT);";

    public static final String TURED_TABLE_PRODUCTO_INFO_DROP = "DROP TABLE IF EXISTS " + USUARIO_INFO_PROD.TABLE_NAME;


    public static class USUARIO_BALANCE_PRODUCTOS {

        public static final String TABLE_NAME = "balance_productos";

        public static final String ID = "id";
        public static final String USER_ID = "user_id";
        public static final String PRODUCTOS_ID = "productos";
        public static final String SALDO = "saldo";
        public static final String NOMBRE = "nombre";
        public static final String DESCRIPCION = "descripcion";
        public static final String ESTATUS = "estatus";
        public static final String TIPO_PRODUCTO = "tipo_comision";
        public static final String ORDEN_PRESENTACION = "orden_presentacion";
        public static final String URL_IMG = "url_mg";
    }

    public static final String TURED_TABLE_CREATE_BALANCE_PRODUCTOS =
            "CREATE TABLE " + USUARIO_BALANCE_PRODUCTOS.TABLE_NAME + " (" +
                    USUARIO_BALANCE_PRODUCTOS.ID + " TEXT , " +
                    USUARIO_BALANCE_PRODUCTOS.USER_ID + " TEXT, " +
                    USUARIO_BALANCE_PRODUCTOS.PRODUCTOS_ID + " TEXT, " +
                    USUARIO_BALANCE_PRODUCTOS.SALDO + " TEXT, " +
                    USUARIO_BALANCE_PRODUCTOS.NOMBRE + " TEXT, " +
                    USUARIO_BALANCE_PRODUCTOS.DESCRIPCION + " TEXT, " +
                    USUARIO_BALANCE_PRODUCTOS.ESTATUS + " TEXT, " +
                    USUARIO_BALANCE_PRODUCTOS.TIPO_PRODUCTO + " TEXT, " +
                    USUARIO_BALANCE_PRODUCTOS.ORDEN_PRESENTACION + " TEXT,"+
                    USUARIO_BALANCE_PRODUCTOS.URL_IMG + " TEXT );";

    public static final String TURED_TABLE_BALANCE_PRODUCTOS_DROP = "DROP TABLE IF EXISTS " + USUARIO_BALANCE_PRODUCTOS.TABLE_NAME;





    public static class USUARIO_HOME_PRODUCTOS {

        public static final String TABLE_NAME = "home_productos";

        public static final String ID = "id";
        public static final String PROVEEDOR = "proveedor";
        public static final String OPERADOR = "operador";
        public static final String SERVICIO = "servicio";
        public static final String ESTATUS = "estatus";
        public static final String ID_PRODUCTO = "id_producto";
        public static final String ORDEN_PRESENTACION = "orden_presentacion";
        public static final String TABLA = "tabla";
        public static final String PRODUCTOS_ID = "productos_id";
        public static final String URL_IMG = "url_img";
        public static final String CATEGORIA = "categoria";
    }

    public static final String TURED_TABLE_CREATE_HOME_PRODUCTOS =
            "CREATE TABLE " + USUARIO_HOME_PRODUCTOS.TABLE_NAME + " (" +
                    USUARIO_HOME_PRODUCTOS.ID + " TEXT , " +
                    USUARIO_HOME_PRODUCTOS.PROVEEDOR + " TEXT, " +
                    USUARIO_HOME_PRODUCTOS.OPERADOR + " TEXT, " +
                    USUARIO_HOME_PRODUCTOS.SERVICIO + " TEXT, " +
                    USUARIO_HOME_PRODUCTOS.ESTATUS + " TEXT, " +
                    USUARIO_HOME_PRODUCTOS.ID_PRODUCTO + " TEXT, " +
                    USUARIO_HOME_PRODUCTOS.ORDEN_PRESENTACION + " TEXT, " +
                    USUARIO_HOME_PRODUCTOS.TABLA + " TEXT, " +
                    USUARIO_HOME_PRODUCTOS.PRODUCTOS_ID + " TEXT, " +
                    USUARIO_HOME_PRODUCTOS.URL_IMG + " TEXT, "+
                    USUARIO_HOME_PRODUCTOS.CATEGORIA + " TEXT );";

    public static final String TURED_TABLE_HOME_PRODUCTOS_DROP = "DROP TABLE IF EXISTS " + USUARIO_HOME_PRODUCTOS.TABLE_NAME;


    public static class USUARIO_HOME_PRODUCTOS_PARAMETROS {

        public static final String TABLE_NAME = "home_productos_parametros";

        public static final String ID = "id";
        public static final String N = "n";
        public static final String T = "t";
        public static final String LMN = "lmn";
        public static final String LMX = "lmx";
        public static final String TABLA = "tabla";
    }

    public static final String TURED_TABLE_CREATE_HOME_PRODUCTOS_PARAMETROS =
            "CREATE TABLE " + USUARIO_HOME_PRODUCTOS_PARAMETROS.TABLE_NAME + " (" +
                    USUARIO_HOME_PRODUCTOS_PARAMETROS.ID + " TEXT , " +
                    USUARIO_HOME_PRODUCTOS_PARAMETROS.N + " TEXT, " +
                    USUARIO_HOME_PRODUCTOS_PARAMETROS.T + " TEXT, " +
                    USUARIO_HOME_PRODUCTOS_PARAMETROS.LMN + " TEXT, " +
                    USUARIO_HOME_PRODUCTOS_PARAMETROS.LMX + " TEXT ," +
                    USUARIO_HOME_PRODUCTOS_PARAMETROS.TABLA + " TEXT );";

    public static final String TURED_TABLE_HOME_PRODUCTOS_PARAMETRPS_DROP = "DROP TABLE IF EXISTS " + USUARIO_HOME_PRODUCTOS_PARAMETROS.TABLE_NAME;


    public static class PACK_OPER_RECARGA {

        public static final String TABLE_NAME = "paquete_operador_recarga";

        public static final String ID = "id";
        public static final String VALOR = "valor";
        public static final String DESCRIPCION = "descripcion";
        public static final String CODIGO = "codigo";
        public static final String ESTATUS = "estatus";
        public static final String ID_PRODUCTO_RECARGAS = "id_producto_recargas";
        public static final String CATEGORIA = "categoria";
        public static final String ORDEN_CATEGORIA = "orden_categoria";
    }

    public static final String TURED_TABLE_CREATE_PACK_OPER_RECARGA =
            "CREATE TABLE " + PACK_OPER_RECARGA.TABLE_NAME + " (" +
                    PACK_OPER_RECARGA.ID + " TEXT , " +
                    PACK_OPER_RECARGA.VALOR + " TEXT, " +
                    PACK_OPER_RECARGA.DESCRIPCION + " TEXT, " +
                    PACK_OPER_RECARGA.CODIGO + " TEXT, " +
                    PACK_OPER_RECARGA.ESTATUS + " TEXT, " +
                    PACK_OPER_RECARGA.ID_PRODUCTO_RECARGAS + " TEXT," +
                    PACK_OPER_RECARGA.CATEGORIA + " TEXT," +
                    PACK_OPER_RECARGA.ORDEN_CATEGORIA + " TEXT );";

    public static final String TURED_TABLE_PACK_OPER_RECARGA_DROP = "DROP TABLE IF EXISTS " + PACK_OPER_RECARGA.TABLE_NAME;


    public static class SERV_OPER_PACK_RECARGA {

        public static final String TABLE_NAME = "serv_oper_pack_recarga";

        public static final String ID = "id";
        public static final String PROVEEDOR = "proveedor";
        public static final String OPERADOR = "operador";
        public static final String SERVICIO = "servicio";
        public static final String ESTATUS = "estatus";
        public static final String ID_PRODUCTO = "id_producto";
        public static final String ID_RECARGAS = "id_recargas";
        public static final String TABLA = "tabla";
    }

    public static final String TURED_TABLE_CREATE_SERV_OPER_PACK_RECARGA =
            "CREATE TABLE " + SERV_OPER_PACK_RECARGA.TABLE_NAME + " (" +
                    SERV_OPER_PACK_RECARGA.ID + " TEXT , " +
                    SERV_OPER_PACK_RECARGA.PROVEEDOR + " TEXT, " +
                    SERV_OPER_PACK_RECARGA.OPERADOR + " TEXT, " +
                    SERV_OPER_PACK_RECARGA.SERVICIO + " TEXT, " +
                    SERV_OPER_PACK_RECARGA.ESTATUS + " TEXT, " +
                    SERV_OPER_PACK_RECARGA.ID_PRODUCTO + " TEXT, " +
                    SERV_OPER_PACK_RECARGA.ID_RECARGAS + " TEXT, " +
                    SERV_OPER_PACK_RECARGA.TABLA + " TEXT );";

    public static final String TURED_TABLE_SERV_OPER_PACK_RECARGA_DROP = "DROP TABLE IF EXISTS " + SERV_OPER_PACK_RECARGA.TABLE_NAME;





    public static class VALUE_DEFAULT_OPER_RECARGA {

        public static final String TABLE_NAME = "value_default_operador_recarga";

        public static final String ID = "id";
        public static final String VALOR = "valor";
        public static final String DESCRIPCION = "descripcion";
        public static final String CODIGO = "codigo";
        public static final String ESTATUS = "estatus";
        public static final String ID_PRODUCTO_RECARGAS = "id_producto_recargas";
        public static final String CATEGORIA = "categoria";
        public static final String ORDEN_CATEGORIA = "orden_categoria";
        public static final String TABLA = "tabla";
    }

    public static final String TURED_TABLE_CREATE_VALUE_DEFAULT_OPER_RECARGA =
            "CREATE TABLE " + VALUE_DEFAULT_OPER_RECARGA.TABLE_NAME + " (" +
                    VALUE_DEFAULT_OPER_RECARGA.ID + " TEXT , " +
                    VALUE_DEFAULT_OPER_RECARGA.VALOR + " TEXT, " +
                    VALUE_DEFAULT_OPER_RECARGA.DESCRIPCION + " TEXT, " +
                    VALUE_DEFAULT_OPER_RECARGA.CODIGO + " TEXT, " +
                    VALUE_DEFAULT_OPER_RECARGA.ESTATUS + " TEXT, " +
                    VALUE_DEFAULT_OPER_RECARGA.ID_PRODUCTO_RECARGAS + " TEXT, " +
                    VALUE_DEFAULT_OPER_RECARGA.CATEGORIA + " TEXT, " +
                    VALUE_DEFAULT_OPER_RECARGA.ORDEN_CATEGORIA + " TEXT, " +
                    VALUE_DEFAULT_OPER_RECARGA.TABLA + " TEXT); ";

    public static final String TURED_TABLE_VALUE_DEFAULT_OPER_RECARGA_DROP = "DROP TABLE IF EXISTS " + VALUE_DEFAULT_OPER_RECARGA.TABLE_NAME;





    public static class REPORT_TRANS_PRODUCTOS {

        public static final String TABLE_NAME = "report_trans_productos";

        public static final String ID = "id";
        public static final String CODIGO_TRANSACCION = "codigo_transaccion";
        public static final String TIPO_IDENTIFICACION = "tipo_identificacion";
        public static final String MIN_RECARGA = "min_recarga";
        public static final String NUMERO_CONTROL = "numero_control";
        public static final String VALOR_RECARGA = "valor_recarga";
        public static final String FECHA_SOLICITUD = "fecha_solicitud";
        public static final String DESCRIPCION_TERMINAL = "descripcion_terminal";
        public static final String PROVEEDOR = "proveedor";
        public static final String ID_MONTO = "id_monto";
        public static final String FECHA_RESPUESTA = "fecha_respuesta";
        public static final String CODIGO_RESPUESTA = "codigo_respuesta";
        public static final String NUMERO_AUTORIZACION = "numero_autorizacion";
        public static final String ID_USER = "id_user";
        public static final String ESTATUS = "estatus";
        public static final String RESPUESTA = "respuesta";
        public static final String ID_PRODUCTO = "id_producto";
        public static final String SALDO_ANTES = "saldo_antes";
        public static final String SALDO_DESPUES = "saldo_despues";
        public static final String VALOR = "valor";
        public static final String DESCRIPCION = "descripcion";
        public static final String CODIGO = "codigo";
        public static final String ID_PRODUCTO_RECARGAS = "id_producto_recargas";
        public static final String OPERADOR = "operador";
        public static final String SERVICIO = "servicio";
    }

    public static final String TURED_TABLE_CREATE_REPORT_TRANS_PRODUCTOS =
            "CREATE TABLE " + REPORT_TRANS_PRODUCTOS.TABLE_NAME + " (" +
                    REPORT_TRANS_PRODUCTOS.ID + " TEXT , " +
                    REPORT_TRANS_PRODUCTOS.CODIGO_TRANSACCION + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.TIPO_IDENTIFICACION + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.MIN_RECARGA + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.NUMERO_CONTROL + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.VALOR_RECARGA + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.FECHA_SOLICITUD + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.DESCRIPCION_TERMINAL + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.PROVEEDOR + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.ID_MONTO + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.FECHA_RESPUESTA + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.CODIGO_RESPUESTA + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.NUMERO_AUTORIZACION + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.ID_USER + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.ESTATUS + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.RESPUESTA + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.ID_PRODUCTO + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.SALDO_ANTES + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.SALDO_DESPUES + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.VALOR + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.DESCRIPCION + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.CODIGO + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.ID_PRODUCTO_RECARGAS + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.OPERADOR + " TEXT, " +
                    REPORT_TRANS_PRODUCTOS.SERVICIO + " TEXT); ";

    public static final String TURED_TABLE_REPORT_TRANS_PRODUCTOS_DROP = "DROP TABLE IF EXISTS " + REPORT_TRANS_PRODUCTOS.TABLE_NAME;





    public static class OFICINAS_CERTIFICADOS_SNR {

        public static final String TABLE_NAME = "oficinas_certificados_snr";

        public static final String ID = "id";
        public static final String CIUDAD = "ciudad";
        public static final String CODIGO = "codigo";
        public static final String ESTATUS = "estatus";
    }

    public static final String TURED_TABLE_CREATE_OFICINAS_CERTIFICADOS =
            "CREATE TABLE " + OFICINAS_CERTIFICADOS_SNR.TABLE_NAME + " (" +
                    OFICINAS_CERTIFICADOS_SNR.ID + " TEXT , " +
                    OFICINAS_CERTIFICADOS_SNR.CIUDAD + " TEXT, " +
                    OFICINAS_CERTIFICADOS_SNR.CODIGO + " TEXT, " +
                    OFICINAS_CERTIFICADOS_SNR.ESTATUS + " TEXT); ";

    public static final String TURED_TABLE_OFICINAS_CERTIFICADOS_SNR_DROP = "DROP TABLE IF EXISTS " + OFICINAS_CERTIFICADOS_SNR.TABLE_NAME;







    //Monitor inicio sesion
    public static class USUARIO_SESION {

        public static final String TABLE_NAME = "usuario_sesion";

        public static final String FECHA = "fecha";
        public static final String HORA = "hora";
        public static final String ID = "id";
        public static final String VERSION = "version";
        public static final String ESTADO = "estado";
        public static final String OPERADOR = "operador";
        public static final String TOKEN = "token";

    }

    public static final String TURED_TABLE_CREATE_USUARIO_SESION =
            "CREATE TABLE " + USUARIO_SESION.TABLE_NAME + " (" +
                    USUARIO_SESION.FECHA + " TIMESTAMP , " +
                    USUARIO_SESION.HORA + " TIME, " +
                    USUARIO_SESION.ID + " TEXT, " +
                    USUARIO_SESION.VERSION + " TEXT," +
                    USUARIO_SESION.ESTADO + " TEXT," +
                    USUARIO_SESION.OPERADOR + " TEXT,"+
                    USUARIO_SESION.TOKEN + " TEXT);";

    public static final String TURED_TABLE_USUARIO_SESION_DROP = "DROP TABLE IF EXISTS " + USUARIO_SESION.TABLE_NAME;




    public static class CATEGORIAS_PRODUCTOS{

        public static final String TABLE_NAME = "categoria_productos";

        public static final String CATEGORIA = "categoria";
        public static final String IMG = "img";

    }

    public static final String TURED_TABLE_CREATE_CATEGORIAS_PRODUCTOS =
            "CREATE TABLE " + CATEGORIAS_PRODUCTOS.TABLE_NAME + " (" +
                    CATEGORIAS_PRODUCTOS.CATEGORIA+ " TEXT , " +
                    CATEGORIAS_PRODUCTOS.IMG +  " TEXT);";

    public static final String TURED_TABLE_CATEGORIAS_PRODUCTOS_DROP = "DROP TABLE IF EXISTS " + CATEGORIAS_PRODUCTOS.TABLE_NAME;


}


