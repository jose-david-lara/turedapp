package com.sb.tured.interfaces;


import android.media.Image;
import android.widget.ImageView;

import com.sb.tured.R;
import com.sb.tured.activity.MenuProductosActivity;
import com.sb.tured.model.BalanceProductos;
import com.sb.tured.model.CargaImagenes;
import com.sb.tured.model.CarteraUsuario;
import com.sb.tured.model.CategoriasProductos;
import com.sb.tured.model.CertificadosSNR;
import com.sb.tured.model.HomeMenu;
import com.sb.tured.model.HomeProductos;
import com.sb.tured.model.InformacionMobil;
import com.sb.tured.model.JsonResponse;
import com.sb.tured.model.PackOperRecarga;
import com.sb.tured.model.ProductInfo;
import com.sb.tured.model.ProductosSaldo;
import com.sb.tured.model.RecargaServ;
import com.sb.tured.model.ReporteVentasBusqueda;
import com.sb.tured.model.ServOperPackRecarga;
import com.sb.tured.model.Soat;
import com.sb.tured.model.TransactionsLast;
import com.sb.tured.model.TransactionsReport;
import com.sb.tured.model.Usuario;
import com.sb.tured.model.UsuarioSesion;
import com.sb.tured.model.ValueDefaultOperRecarga;
import com.sb.tured.utilities.utilitiesTuRedBD;

import java.util.ArrayList;

public interface interfacesModelo {



    Usuario data_user = new Usuario();
    ProductInfo product_info = new ProductInfo();
    BalanceProductos balance_producto = new BalanceProductos();
    HomeProductos home_productos = new HomeProductos();
    PackOperRecarga pack_oper_recarga = new PackOperRecarga();
    ServOperPackRecarga serv_oper_pack_recarga = new ServOperPackRecarga();
    ValueDefaultOperRecarga valor_default_oper_recarga = new ValueDefaultOperRecarga();
    RecargaServ recarga_servicio = new RecargaServ();
    TransactionsReport trans_report = new TransactionsReport();
    CertificadosSNR oficinas_certificados_snr = new CertificadosSNR();
    ArrayList<CarteraUsuario> cartera_usuario_list = new ArrayList<>();
    ArrayList<BalanceProductos> producto_saldo_list = new ArrayList<>();
    ArrayList<ReporteVentasBusqueda> reporte_venta_busqueda = new ArrayList<>();
    InformacionMobil informacion_mobil = new InformacionMobil();
    UsuarioSesion usuario_sesion =  new UsuarioSesion();
    CargaImagenes carga_imagenes =  new CargaImagenes();
    Soat soat =  new Soat();
    Soat.comsumo_servicio_soat consumo_servicio_soat = new Soat.comsumo_servicio_soat();
    CategoriasProductos categoria_producto_info = new CategoriasProductos();
    ArrayList<Soat.formulario> formulario = new ArrayList<>();

    TransactionsLast trans_last = new TransactionsLast();
    JsonResponse json_object_response = new JsonResponse();


    




}
