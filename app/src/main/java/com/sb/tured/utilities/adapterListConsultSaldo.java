package com.sb.tured.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.model.BalanceProductos;
import com.sb.tured.model.ProductInfo;
import com.sb.tured.model.TransactionsReport;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class adapterListConsultSaldo extends RecyclerView.Adapter<adapterListConsultSaldo.ViewHolderList>
        implements View.OnClickListener{

    private ArrayList<BalanceProductos> listConsultSaldo;
    private View.OnClickListener listener;
    private formatText format_text = new formatText();



    public adapterListConsultSaldo(ArrayList<BalanceProductos> listConsultSaldo) {
        this.listConsultSaldo = listConsultSaldo;
    }



    @Override
    public ViewHolderList onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.consult_saldo_card_view,null,false);

        view.setOnClickListener(this);

        return new ViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderList holder, int position) {




        holder.etiNombre.setText(listConsultSaldo.get(position).getNombre());
        holder.etiSaldoConsulSaldo.setText("Saldo: "+format_text.stringFormat(listConsultSaldo.get(position).getSaldo()));
        //holder.etiComisMaxConsulSaldo.setText("Comision: "+listConsultSaldo.get(position).getComision_maxima());
        //holder.etiValMinConsulSaldo.setText("Valor Minimo: "+listConsultSaldo.get(position).getValor_minimo());

        /*if(listConsultSaldo.get(position).getNombre().equals("RECARGAS")) {
            //holder.foto.setImageResource(R.drawable.oper_recargas_trans_report);
            Picasso.get()
                    .load(R.drawable.oper_recargas_trans_report)
                    .transform(new RoundedTransformation(50, 4))
                    .resize(350, 300)
                    .centerCrop()
                    .into(holder.foto);
        }else{*/
            Picasso.get()
                    .load(listConsultSaldo.get(position).getUrl_img())
                    .transform(new RoundedTransformation(50, 4))
                    .resize(350, 300)
                    .centerCrop()
                    .into(holder.foto);
        //}


    }

    @Override
    public int getItemCount() {
        return listConsultSaldo.size();
    }



    public void setOnClickListener(View.OnClickListener listener){

        this.listener=listener;

    }


    @Override
    public void onClick(View v) {

        if (listener!=null){

            listener.onClick(v);
        }
    }

    public class ViewHolderList extends RecyclerView.ViewHolder {
        TextView etiNombre;
        TextView etiSaldoConsulSaldo;
        //TextView etiComisMaxConsulSaldo;
        //TextView etiValMinConsulSaldo;
        ImageView foto;


        public ViewHolderList(View itemView) {
            super(itemView);
            etiNombre = itemView.findViewById(R.id.tvNombreConsulSaldo);
            etiSaldoConsulSaldo = itemView.findViewById(R.id.tvSaldoConsulSaldo);
            //etiComisMaxConsulSaldo = (TextView) itemView.findViewById(R.id.tvComisMaxConsulSaldo);
            //etiValMinConsulSaldo = (TextView) itemView.findViewById(R.id.tvValMinConsulSaldo);
            foto= itemView.findViewById(R.id.imagenConsulSaldo);
        }
    }
}
