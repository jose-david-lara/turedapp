package com.sb.tured.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.model.CarteraUsuario;
import com.sb.tured.model.TransactionsReport;

import java.util.ArrayList;

public class adapterListBuyReportTrans extends RecyclerView.Adapter<adapterListBuyReportTrans.ViewHolderList>
        implements View.OnClickListener{

    private ArrayList<CarteraUsuario> listBuyReportTrans;
    private View.OnClickListener listener;
    private formatText format_text = new formatText();

    public adapterListBuyReportTrans(ArrayList<CarteraUsuario> listBuyReportTrans) {
        this.listBuyReportTrans = listBuyReportTrans;
    }



    @Override
    public ViewHolderList onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.report_buy_card_view,null,false);

        view.setOnClickListener(this);

        return new ViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderList holder, int position) {

        holder.etiNombre.setText(listBuyReportTrans.get(position).getName());
        holder.etiFechaBuy.setText(listBuyReportTrans.get(position).getFecha());
        holder.etiRecargaBuy.setText(listBuyReportTrans.get(position).getNombre());
        holder.etiValorBuy.setText("Valor:"+format_text.integerFormat(Integer.parseInt(listBuyReportTrans.get(position).getValor().replace(".00",""))));


        //holder.etiFechaTrans.setText(listBuyReportTrans.get(position).getFecha_solicitud());

        /*if(listBuyReportTrans.get(position).getCodigo_respuesta().equals("ERROR")) {
            holder.foto.setImageResource(R.drawable.ic_fail_trans);
        }else{
            holder.foto.setImageResource(R.drawable.ic_successful_trans);
        }*/

        //holder.foto.setImageResource(Integer.parseInt(listProduct.get(position).getImg()));
    }

    @Override
    public int getItemCount() {
        return listBuyReportTrans.size();
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
        TextView etiFechaBuy;
        TextView etiRecargaBuy;
        TextView etiValorBuy;
        ImageView foto;


        public ViewHolderList(View itemView) {
            super(itemView);
            etiNombre = itemView.findViewById(R.id.tvNombreBuy);
            etiFechaBuy = itemView.findViewById(R.id.tvFechaBuy);
            etiRecargaBuy = itemView.findViewById(R.id.tvRecargaBuy);
            etiValorBuy = itemView.findViewById(R.id.tvValorBuy);
            foto= itemView.findViewById(R.id.imagenTrans);
        }
    }
}
