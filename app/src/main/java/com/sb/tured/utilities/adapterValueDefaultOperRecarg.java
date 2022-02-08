package com.sb.tured.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.model.PackOperRecarga;
import com.sb.tured.model.ValueDefaultOperRecarga;

import java.util.ArrayList;

public class adapterValueDefaultOperRecarg extends RecyclerView.Adapter<adapterValueDefaultOperRecarg.ViewHolderList>
        implements View.OnClickListener{

    public ArrayList<ValueDefaultOperRecarga> listValueDefaultOperRecarg;
    public View.OnClickListener listener;


    public adapterValueDefaultOperRecarg(ArrayList<ValueDefaultOperRecarga> listValueDefaultOperRecarg) {
        this.listValueDefaultOperRecarg = listValueDefaultOperRecarg;
    }


    @Override
    public ViewHolderList onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recarga_princp_card_view,null,false);

        view.setOnClickListener(this);


        return new ViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderList holder, final int position) {
        holder.etiNombre.setText(listValueDefaultOperRecarg.get(position).getDescripcion());
        //holder.etiInformacion.setText(listProduct.get(position).getServicio());
        /*Picasso.get()
                .load(listProduct.get(position).getUrl_img())
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(holder.foto);*/
       // holder.etiInformacion.setText(listPackOperRecarg.get(position).getValor());
        //holder.etiInformacion.setVisibility(View.INVISIBLE);


        //holder.foto.setImageResource(Integer.parseInt(listProduct.get(position).getImg()));
    }

    @Override
    public int getItemCount() {
        return listValueDefaultOperRecarg.size();
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
        //TextView etiInformacion;
        //ImageView foto;


        public ViewHolderList(View itemView) {
            super(itemView);
            etiNombre= itemView.findViewById(R.id.tvNombre);
           // etiInformacion= (TextView) itemView.findViewById(R.id.tvInformacion);

            //foto= (ImageView) itemView.findViewById(R.id.idImagen);


        }
    }
}
