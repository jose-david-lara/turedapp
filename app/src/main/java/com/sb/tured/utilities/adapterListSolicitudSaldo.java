package com.sb.tured.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.model.BalanceProductos;
import com.sb.tured.model.CarteraUsuario;
import com.sb.tured.model.ProductInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterListSolicitudSaldo extends RecyclerView.Adapter<adapterListSolicitudSaldo.ViewHolderList>
        implements View.OnClickListener {

    public ArrayList<BalanceProductos> balance_productos;
    public ArrayList<ProductInfo> producto_info;
    private View.OnClickListener listener;
    private formatText format_text = new formatText();

    public adapterListSolicitudSaldo(ArrayList<BalanceProductos> balance_productos, ArrayList<ProductInfo> producto_info) {
        this.balance_productos = balance_productos;
        this.producto_info = producto_info;
    }


    @Override
    public ViewHolderList onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.solicitud_saldo_card_view, null, false);

        view.setOnClickListener(this);

        return new ViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderList holder, int position) {
        int x = 0;

        for (x = 0; x < producto_info.size(); x++) {

            if (balance_productos.get(position).getId().equals(producto_info.get(x).getId())) {
                break;
            }

        }


        holder.etiProductoSolictSaldo.setText(balance_productos.get(position).getNombre());
        holder.etiSaldoSolictSaldo.setText("Saldo: "+format_text.stringFormat(balance_productos.get(position).getSaldo()));
        holder.etiComisionSolictSaldo.setText("");

        Picasso.get()
                .load(balance_productos.get(position).getUrl_img())
                .transform(new RoundedTransformation(20, 4))
                .resize(150, 150)
                .centerCrop()
                .into(holder.foto);

        /*else if (balance_productos.get(position).getNombre().equals("SOAT")) {
            Picasso.get()
                    .load(producto_info.get(x).getUrl_img())
                    .transform(new RoundedTransformation(20, 4))
                    .resize(150, 150)
                    .centerCrop()
                    .into(holder.foto);
        } else if (balance_productos.get(position).getNombre().equals("WPLAY")) {
            Picasso.get()
                    .load(producto_info.get(x).getUrl_img())
                    .transform(new RoundedTransformation(20, 4))
                    .resize(150, 150)
                    .centerCrop()
                    .into(holder.foto);
        }*/
        //balance_productos.get(position).setUrl_img(producto_info.get(x).getUrl_img());
        holder.etiComisionSolictSaldo.setVisibility(View.GONE);

        /*Picasso.get()
                .load(R.drawable.ic_cuentas)
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(holder.foto);*/
        /*if(listBuyReportTrans.get(position).getCodigo_respuesta().equals("ERROR")) {

        }else{
            holder.foto.setImageResource(R.drawable.ic_successful_trans);
        }*/

        //holder.foto.setImageResource(Integer.parseInt(listProduct.get(position).getImg()));
    }

    @Override
    public int getItemCount() {
        return balance_productos.size();

    }


    public void setOnClickListener(View.OnClickListener listener) {

        this.listener = listener;

    }


    @Override
    public void onClick(View v) {

        if (listener != null) {

            listener.onClick(v);
        }
    }

    public class ViewHolderList extends RecyclerView.ViewHolder {
        TextView etiProductoSolictSaldo;
        TextView etiSaldoSolictSaldo;
        TextView etiComisionSolictSaldo;
        ImageView foto;


        public ViewHolderList(View itemView) {
            super(itemView);
            etiProductoSolictSaldo = itemView.findViewById(R.id.tvProductoSolictSaldo);
            etiSaldoSolictSaldo = itemView.findViewById(R.id.tvSaldoSolictSaldo);
            etiComisionSolictSaldo = itemView.findViewById(R.id.tvComisionSolictSaldo);
            foto = itemView.findViewById(R.id.imagenTransSolictSaldo);
        }
    }
}
