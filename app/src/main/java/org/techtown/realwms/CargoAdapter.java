package org.techtown.realwms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CargoAdapter extends RecyclerView.Adapter<CargoAdapter.ViewHolder>
        implements OnPersonItemClickListener{
    private ArrayList<Cargo> arrayList;
    private Context context;
    int az;

    public CargoAdapter(ArrayList<Cargo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    OnPersonItemClickListener listener;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.cargo_item, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position) ;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //Person item = items.get(position);
        //viewHolder.setItem(item);

        holder.consignee.setText(arrayList.get(position).getConsignee());
        holder.cargo_v.setText(arrayList.get(position).getCargo_v());
        holder.pqty.setText(arrayList.get(position).getPqty());
        holder.bl.setText(arrayList.get(position).getBl());
        holder.des.setText(arrayList.get(position).getDes());
        holder.cont.setText(arrayList.get(position).getCont());
        holder.seal.setText(arrayList.get(position).getSeal());
        holder.qty.setText(arrayList.get(position).getQty());
    }

    @Override
    public int getItemCount() {
        return (arrayList !=null ? arrayList.size():0);
    }
    public Cargo getItem(int position) {
        return arrayList.get(position);
    }


    public void setOnItemClickListener(OnPersonItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView consignee;
        TextView cargo_v;
        TextView qty;
        TextView pqty;
        TextView bl;
        TextView des;
        TextView cont;
        TextView seal;
        public ViewHolder(View itemView, final OnPersonItemClickListener listener) {
            super(itemView);
            consignee = itemView.findViewById(R.id.textView3);
            cargo_v = itemView.findViewById(R.id.textView4);
            cont = itemView.findViewById(R.id.textView5);
            seal = itemView.findViewById(R.id.textView6);
            bl = itemView.findViewById(R.id.textView7);
            pqty = itemView.findViewById(R.id.textView8);
            qty = itemView.findViewById(R.id.textView9);
            des=itemView.findViewById(R.id.textView10);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(Cargo item) {
            consignee.setText(item.getConsignee());
            cargo_v.setText(item.getCargo_v());
            qty.setText(item.getQty());
            pqty.setText(item.getPqty());
            bl.setText(item.getBl());
            des.setText(item.getDes());
            seal.setText(item.getSeal());
            cont.setText(item.getCont());


        }


    }

}
