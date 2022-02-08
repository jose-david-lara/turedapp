package com.sb.tured.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.model.TransactionsLast;
import com.sb.tured.model.TransactionsReport;

import java.util.ArrayList;

public class adapterListLastReportTrans extends RecyclerView.Adapter<adapterListLastReportTrans.ViewHolderList>
        implements View.OnClickListener{

    private ArrayList<TransactionsReport> listLastReportTrans;
    private View.OnClickListener listener;
    private formatText format_text = new formatText();

    public adapterListLastReportTrans(ArrayList<TransactionsReport> listLastReportTrans) {
        this.listLastReportTrans = listLastReportTrans;
    }



    @Override
    public ViewHolderList onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.last_trans_report_card_view,null,false);

        view.setOnClickListener(this);

        return new ViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderList holder, int position) {

        if(position == 0){
            holder.etiOperadorReport.setText("Operador");
            holder.etiReferenciaReport.setText("Referencia");
            holder.etiValorReport.setText("Valor");
            holder.etiFechaReport.setText("Fecha");
            holder.etiAutorizacionReport.setText("Autorizacion");
        }else{


            holder.etiOperadorReport.setText(listLastReportTrans.get(position).getOperador());
            holder.etiReferenciaReport.setText(listLastReportTrans.get(position).getMin_recarga());
            holder.etiValorReport.setText(format_text.integerFormat(Integer.parseInt(listLastReportTrans.get(position).getValor_recarga().replace(".00",""))));
            holder.etiFechaReport.setText(listLastReportTrans.get(position).getFecha_solicitud());
            holder.etiAutorizacionReport.setText(listLastReportTrans.get(position).getNumero_autorizacion());
        }
        /*holder.etiNombre.setText(listLastReportTrans.get(position).getMin_recarga());
        holder.etiValOperTrans.setText(format_text.integerFormat(Integer.parseInt(listLastReportTrans.get(position).getValor_recarga().replace(".00","")))+" - "+listLastReportTrans.get(position).getProveedor());
        holder.etiRefTrans.setText("Ref: "+listLastReportTrans.get(position).getCodigo_respuesta());
        holder.etiFechaTrans.setText(listLastReportTrans.get(position).getFecha_solicitud());

        if(listLastReportTrans.get(position).getCodigo_respuesta().equals("ERROR")) {
            holder.foto.setImageResource(R.drawable.ic_fail_trans);
        }else{
            holder.foto.setImageResource(R.drawable.ic_successful_trans);
        }*/

        //holder.foto.setImageResource(Integer.parseInt(listProduct.get(position).getImg()));
    }

    @Override
    public int getItemCount() {
        return listLastReportTrans.size();
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
        TextView etiOperadorReport;
        TextView etiReferenciaReport;
        TextView etiValorReport;
        TextView etiFechaReport;
        TextView etiAutorizacionReport;
       // ImageView foto;


        public ViewHolderList(View itemView) {
            super(itemView);
            /*etiOperadorReport = (TextView) itemView.findViewById(R.id.columnaOperador);
            etiReferenciaReport = (TextView) itemView.findViewById(R.id.columnaReferencia);
            etiValorReport = (TextView) itemView.findViewById(R.id.columnaValor);
            etiFechaReport = (TextView) itemView.findViewById(R.id.columnaFecha);
            etiAutorizacionReport = (TextView) itemView.findViewById(R.id.columnaAutorizacion);*/


           // foto= (ImageView) itemView.findViewById(R.id.imagenTrans);
        }
    }
}
