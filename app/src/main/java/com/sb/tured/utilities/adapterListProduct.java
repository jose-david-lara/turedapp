package com.sb.tured.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sb.tured.R;
import com.sb.tured.activity.MenuProductosActivity;
import com.sb.tured.model.CategoriasProductos;
import com.sb.tured.model.HomeProductos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.sb.tured.R.drawable.cargando;

public class adapterListProduct extends RecyclerView.Adapter<adapterListProduct.ViewHolderList>
        implements View.OnClickListener{

    public ArrayList<HomeProductos> listProduct;
    public ArrayList<CategoriasProductos> listCategorias;
    public View.OnClickListener listener;
    private Context mContext ;





    public adapterListProduct(Context mContext,ArrayList<HomeProductos> listProduct, ArrayList<CategoriasProductos> listCategorias) {
        this.listProduct = listProduct;
        this.listCategorias = listCategorias;
        this.mContext = mContext;

    }


    @Override
    public ViewHolderList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list,null,false);

        view.setOnClickListener(this);



        return new ViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderList holder, final int position) {
        int contCategorias = 0;

        if(listProduct != null && listCategorias != null) {
            if (position < listProduct.size()) {
                Picasso.get()
                        .load(listProduct.get(position).getUrl_img())
                        .transform(new RoundedTransformation(10, 1))
                        .resize(265, 265)
                        .centerCrop()
                        .error(R.drawable.ic_fail_trans)
                        .into(holder.foto);
                holder.nombre_producto.setVisibility(View.GONE);
            } else {
                contCategorias = listCategorias.size() - ((listProduct.size() + listCategorias.size()) - position);
                if (contCategorias < listCategorias.size()) {
                    Picasso.get()
                            .load(listCategorias.get(contCategorias).getImg())
                            .transform(new RoundedTransformation(10, 1))
                            .resize(265, 265)
                            .centerCrop()
                            .error(R.drawable.ic_fail_trans)
                            .into(holder.foto);
                    holder.nombre_producto.setText(listCategorias.get(contCategorias).getCategoria());
                    holder.nombre_producto.setVisibility(View.VISIBLE);
                }
            }
        }else if(listProduct != null){
            Picasso.get()
                    .load(listProduct.get(position).getUrl_img())
                    .transform(new RoundedTransformation(10, 1))
                    .resize(265, 265)
                    .centerCrop()
                    .error(R.drawable.ic_fail_trans)
                    .into(holder.foto);
            holder.nombre_producto.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {

        if(listProduct != null && listCategorias != null) {
            return listProduct.size() + listCategorias.size();
        }else if(listProduct != null){
            return listProduct.size();
        }else{
            return 0;
        }
    }



    public void setOnClickListener(View.OnClickListener listener){


        this.listener=listener;


    }


    @Override
    public void onClick(View v) {

        if (listener!=null){

            AnimateBell(v);
            listener.onClick(v);

        }
    }

    public class ViewHolderList extends RecyclerView.ViewHolder {
        TextView nombre_producto;
        ImageView foto;


        public ViewHolderList(View itemView) {
            super(itemView);
            nombre_producto= (TextView) itemView.findViewById(R.id.tvNombreProducto);
            foto= itemView.findViewById(R.id.idImagen);

        }
    }

    public void AnimateBell(View v) {
        ViewHolderList view_local_list = new ViewHolderList(v);

        Animation shake = AnimationUtils.loadAnimation(v.getContext(), R.anim.animacion_imagen_productos);
        ImageView imgBell = v.findViewById(R.id.idImagen);
        imgBell.setImageResource(R.mipmap.ic_check_producto);
        imgBell.setAnimation(shake);
    }
}
