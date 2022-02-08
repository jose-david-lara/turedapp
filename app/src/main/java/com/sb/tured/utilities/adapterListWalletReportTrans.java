package com.sb.tured.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.model.CarteraUsuario;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterListWalletReportTrans extends RecyclerView.Adapter<adapterListWalletReportTrans.ViewHolderList>
        implements View.OnClickListener{

    private ArrayList<CarteraUsuario> listBuyReportTrans;
    private View.OnClickListener listener;
    private formatText format_text = new formatText();

    public adapterListWalletReportTrans(ArrayList<CarteraUsuario> listBuyReportTrans) {
        this.listBuyReportTrans = listBuyReportTrans;
    }


    @Override
    public ViewHolderList onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.report_wallet_card_view,null,false);

        view.setOnClickListener(this);

        return new ViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderList holder, int position) {

        holder.etiNombre.setText(listBuyReportTrans.get(position).getName());
        holder.etiWalletFecha.setText(listBuyReportTrans.get(position).getFecha());
        holder.etiWalletRecargas.setText    ("Recargas    :"+format_text.integerFormat(Integer.parseInt(listBuyReportTrans.get(position).getRecargas().replace(".00",""))));
        holder.etiWalletWplay.setText       ("Wplay          :"+format_text.integerFormat(Integer.parseInt(listBuyReportTrans.get(position).getWplay().replace(".00",""))));
        holder.etiWalletSoat.setText        ("Soat             :"+format_text.integerFormat(Integer.parseInt(listBuyReportTrans.get(position).getSoat().replace(".00",""))));
        holder.etiWalletCertificados.setText("Certificados:"+format_text.integerFormat(Integer.parseInt(listBuyReportTrans.get(position).getCertificados().replace(".00",""))));
        holder.etiWalletAbono.setText       ("Abono          :"+format_text.integerFormat(Integer.parseInt(listBuyReportTrans.get(position).getAbono().replace(".00",""))));

        holder.foto.setImageResource(R.mipmap.ic_bills);
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
        TextView etiWalletFecha;
        TextView etiWalletRecargas;
        TextView etiWalletWplay;
        TextView etiWalletSoat;
        TextView etiWalletCertificados;
        TextView etiWalletAbono;


        ImageView foto;


        public ViewHolderList(View itemView) {
            super(itemView);
            etiNombre = itemView.findViewById(R.id.tvNombreWallet);
            etiWalletFecha = itemView.findViewById(R.id.tvFechaWallet);
            etiWalletRecargas = itemView.findViewById(R.id.tvWalletRecargas);
            etiWalletWplay = itemView.findViewById(R.id.tvWalletWplay);
            etiWalletSoat = itemView.findViewById(R.id.tvWalletSoat);
            etiWalletCertificados = itemView.findViewById(R.id.tvWalletCertificados);
            etiWalletAbono = itemView.findViewById(R.id.tvWalletAbono);
            foto= itemView.findViewById(R.id.imagenWallet);
        }
    }
}
