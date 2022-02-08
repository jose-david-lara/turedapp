package com.sb.tured.utilities;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.model.HomeMenu;
import com.sb.tured.model.TransactionsLast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterListLastFiveTrans extends RecyclerView.Adapter<adapterListLastFiveTrans.ViewHolderList>
        implements View.OnClickListener{

    private ArrayList<TransactionsLast> listLastFiveTrans;
    private View.OnClickListener listener;
    private formatText format_text = new formatText();

    public adapterListLastFiveTrans(ArrayList<TransactionsLast> listLastFiveTrans) {
        this.listLastFiveTrans = listLastFiveTrans;
    }



    @Override
    public ViewHolderList onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.last_trans_five_card_view,null,false);

        view.setOnClickListener(this);

        return new ViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderList holder, int position) {

        try {
            holder.etiNombre.setText(listLastFiveTrans.get(position).getNumero());
            holder.etiValOperTrans.setText(format_text.integerFormat(Integer.parseInt(listLastFiveTrans.get(position).getValor_Recarga().replace(".00", ""))) + " - " + listLastFiveTrans.get(position).getOperador());

            holder.etiFechaTrans.setText(listLastFiveTrans.get(position).getFecha_solicitud());

            if (!listLastFiveTrans.get(position).getEstado().equals("Exitosa")) {
                holder.foto.setImageResource(R.drawable.ic_fail_trans);
                holder.etiMsjError.setText(listLastFiveTrans.get(position).getMensaje());
            } else {
                holder.foto.setImageResource(R.drawable.ic_successful_trans);
                holder.etiMsjError.setVisibility(View.GONE);
            }
        }catch (Exception e){
        }

    }

    @Override
    public int getItemCount() {
        return listLastFiveTrans.size();
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
        TextView etiValOperTrans;
        TextView etiFechaTrans;
        TextView etiMsjError;
        ImageView foto;


        public ViewHolderList(View itemView) {
            super(itemView);
            etiNombre = itemView.findViewById(R.id.tvNombreTrans);
            etiValOperTrans = itemView.findViewById(R.id.tvNumValTrans);
            etiFechaTrans = itemView.findViewById(R.id.tvFechaTrans);
            etiMsjError = itemView.findViewById(R.id.tvMsjError);
            foto= itemView.findViewById(R.id.imagenTrans);
        }
    }
}
