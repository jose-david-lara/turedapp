package com.sb.tured.interfaces;

import com.sb.tured.model.BalanceProductos;
import com.sb.tured.model.HomeProductos;
import com.sb.tured.model.JsonResponse;
import com.sb.tured.model.PackOperRecarga;
import com.sb.tured.model.ProductInfo;
import com.sb.tured.model.RecargaServ;
import com.sb.tured.model.ServOperPackRecarga;
import com.sb.tured.model.TransactionsLast;
import com.sb.tured.model.TransactionsReport;
import com.sb.tured.model.Usuario;
import com.sb.tured.model.ValueDefaultOperRecarga;

public interface interfaceOnResponse {

    Usuario data_user = new Usuario();
    ProductInfo product_info = new ProductInfo();
    BalanceProductos balance_producto = new BalanceProductos();
    HomeProductos home_productos = new HomeProductos();
    PackOperRecarga pack_oper_recarga = new PackOperRecarga();
    ServOperPackRecarga serv_oper_pack_recarga = new ServOperPackRecarga();
    ValueDefaultOperRecarga valor_default_oper_recarga = new ValueDefaultOperRecarga();
    RecargaServ recarga_servicio = new RecargaServ();
    TransactionsReport trans_report = new TransactionsReport();

    TransactionsLast trans_last = new TransactionsLast();
    JsonResponse json_object_response = new JsonResponse();


    void finish_consume_services(  );
    void solicit_token_error_services( String mensaje_token );
    void finish_fail_consume_services( int codigo );
}
