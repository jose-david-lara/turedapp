package com.sb.tured.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.sb.tured.R;
import com.sb.tured.model.HomeMenu;
import com.sb.tured.model.HomeProductos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterListMenuPrincp extends RecyclerView.Adapter<adapterListMenuPrincp.ViewHolderList>
        implements View.OnClickListener{

    ArrayList<HomeMenu> listMenu;
    private View.OnClickListener listener;



    public adapterListMenuPrincp(ArrayList<HomeMenu> listMenu) {
        this.listMenu = listMenu;
    }



    @Override
    public ViewHolderList onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_princp_card_view,null,false);


        view.setOnClickListener(this);

        return new ViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderList holder, int position) {
        holder.etiNombre.setText(listMenu.get(position).getNombre());
        //holder.etiInformacion.setText(listProduct.get(position).getServicio());
        Picasso.get()
                .load(listMenu.get(position).getImg())
                .transform(new RoundedTransformation(50, 4))
                .resize(350, 300)
                .centerCrop()
                .into(holder.foto);

        //holder.foto.setImageResource(Integer.parseInt(listProduct.get(position).getImg()));


    }

    @Override
    public int getItemCount() {
        return listMenu.size();
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
       // TextView etiInformacion;
        ImageView foto;


        public ViewHolderList(View itemView) {
            super(itemView);
            etiNombre= itemView.findViewById(R.id.tvNombre);
            //etiInformacion= (TextView) itemView.findViewById(R.id.Info);

            foto= itemView.findViewById(R.id.imagen);
        }
    }



}
