package com.sb.tured.utilities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.model.CarteraUsuario;
import com.sb.tured.model.HomeProductos;
import com.sb.tured.model.ReporteVentasBusqueda;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class adapterListReportSearchRecarga extends RecyclerView.Adapter<adapterListReportSearchRecarga.ViewHolderList>
        implements View.OnClickListener {

    private ArrayList<ReporteVentasBusqueda> listReportSearchRecarga;
    private Context context;
    private View.OnClickListener listener;
    private formatText format_text = new formatText();
    private List home_productos_local;
    private utilitiesTuRedBD utils_bd = new utilitiesTuRedBD(context);
    private HomeProductos objaux = new HomeProductos();

    public adapterListReportSearchRecarga(ArrayList<ReporteVentasBusqueda> listReportSearchRecarga, Context context) {
        this.context = context;
        this.listReportSearchRecarga = listReportSearchRecarga;
    }


    @Override
    public ViewHolderList onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_search_recarga_card_view, null, false);

        view.setOnClickListener(this);

        return new ViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderList holder, int position) {

        try {
            holder.etiNumero.setText(listReportSearchRecarga.get(position).getReferencia());
            holder.etiOperador.setText(listReportSearchRecarga.get(position).getOperador());
            holder.etiValorRecarga.setText(listReportSearchRecarga.get(position).getValor_recarga());
            holder.etiNumAutorizacion.setText(listReportSearchRecarga.get(position).getNumero_autorizacion());


            if (!listReportSearchRecarga.get(position).getEstado().equals("Exitosa")) {
                holder.foto.setImageResource(R.drawable.ic_fail_trans);
            } else {
                holder.foto.setImageResource(R.drawable.ic_successful_trans);
            }
        }catch (Exception ex){
        }



    }

    @Override
    public int getItemCount() {
        return listReportSearchRecarga.size();
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
        TextView etiNumero;
        TextView etiOperador;
        TextView etiValorRecarga;
        TextView etiNumAutorizacion;
        ImageView foto;


        public ViewHolderList(View itemView) {
            super(itemView);
            etiNumero = itemView.findViewById(R.id.tvMinSearchRecarga);
            etiOperador = itemView.findViewById(R.id.tvOperSearchRecarga);
            etiValorRecarga = itemView.findViewById(R.id.tvValueSearchRecarga);
            etiNumAutorizacion = itemView.findViewById(R.id.tvNumAutorizSearchRecarga);
            foto = itemView.findViewById(R.id.imagenSearchRecarga);
        }
    }
}
