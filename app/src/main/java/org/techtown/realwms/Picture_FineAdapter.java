package org.techtown.realwms;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Picture_FineAdapter extends RecyclerView.Adapter<Picture_FineAdapter.ViewHolder>
    implements OnPictureItemClickListener{
    private List<Picture_Fine> items = new ArrayList<>();
    public Map<Picture_Fine,Boolean> mCheckedMap=new HashMap<>();
    public List mCheckedList=new ArrayList<>();

    OnPictureItemClickListener listener;
    CheckBox checkBox8;
    private Object Picture_Fine;
    public void selectCao(){

      boolean result=checkBox8.isChecked();
      if (result){
          mCheckedList.toString();}

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).
         inflate(R.layout.picture_fine_gallery, viewGroup, false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Picture_Fine images=items.get(viewHolder.getAdapterPosition());
                mCheckedMap.put(images,isChecked);
                if(isChecked){
                    mCheckedList.add(images);
                }else {
                    mCheckedList.remove(images);
                }
            } });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Picture_Fine images = items.get(position);
        viewHolder.setItem(images);

        boolean isChecked=mCheckedMap.get(images)==null? false:
                mCheckedMap.get(images);
        viewHolder.checkBox8.setChecked(isChecked);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Picture_Fine item) {
        items.add(item);
    }

    public void setItems(ArrayList<Picture_Fine> items) {
        this.items = items;
    }

    public Picture_Fine getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Picture_Fine item) {
        items.set(position, item);
    }

    public void setOnItemClickListener(OnPictureItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView27;
        TextView textView28;
        ImageView imageView;
        CheckBox checkBox8;

        BitmapFactory.Options options = new BitmapFactory.Options();
//        private Picture_FineAdapter listener;

        public ViewHolder(View itemView) {
            super(itemView);

            textView27 = itemView.findViewById(R.id.textView27);
            textView28 = itemView.findViewById(R.id.textView28);
            imageView = itemView.findViewById(R.id.imageView);
            checkBox8=itemView.findViewById(R.id.checkBox8);

            options.inSampleSize = 12;

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//
//                    if (listener != null) {
//                        listener.onItemClick(ViewHolder.this, view, position);
//                    }
//                }
//            });
        }
        public void setItem(Picture_Fine item) {
            textView27.setText(item.getDisplayName());
            textView28.setText(item.getDateAdded());

            Bitmap bitmap = BitmapFactory.decodeFile(item.getPath(), options);
            imageView.setImageBitmap(bitmap);


        }

        }

}

