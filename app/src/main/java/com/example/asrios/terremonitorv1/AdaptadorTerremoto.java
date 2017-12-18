package com.example.asrios.terremonitorv1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asrios on 16/12/17.
 */

public class AdaptadorTerremoto extends ArrayAdapter<Terremoto> {

    private ArrayList<Terremoto> listaTerremotos;
    private Context context;
    private int layoutId;

    public AdaptadorTerremoto(@NonNull Context context, int resource, @NonNull List<Terremoto> terremotos) {
        super(context, resource, terremotos);
        this.context = context;
        this.layoutId = resource;
        listaTerremotos = new ArrayList<>(terremotos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate (layoutId,null);

            holder = new ViewHolder();
            holder.magnitudTextView = (TextView) convertView.findViewById(R.id.terremoto_magnitud);
            holder.lugarTextView = (TextView) convertView.findViewById(R.id.terremoto_lugar);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Terremoto terremoto = listaTerremotos.get(position);

        holder.magnitudTextView.setText(String.valueOf(terremoto.getMagnitud()));
        holder.lugarTextView.setText(terremoto.getLugar());

        return convertView;
    }


    private class ViewHolder {
        public TextView magnitudTextView;
        public TextView lugarTextView;
    }
}

