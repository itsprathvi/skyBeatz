package com.example.musicapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

public class cardAdapter extends ArrayAdapter<cardModel> {
    private static final String TAG = "ascd";

    public cardAdapter(@NonNull Context context, ArrayList<cardModel> cardModelArrayList) {
        super(context, 0, cardModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_view, parent, false);
        }
        cardModel cardModel = getItem(position);
        TextView TV1 = listitemView.findViewById(R.id.textView1);
        TextView TV2 = listitemView.findViewById(R.id.textView2);
        TextView TV3 = listitemView.findViewById(R.id.textView3);
        ImageView IV = listitemView.findViewById(R.id.img1);
        TV1.setText(cardModel.getTrackName() );
        TV2.setText(cardModel.getMovie() );
        TV3.setText(cardModel.getArtist() );
//        IV.setImageResource(cardModel.getImgid());
        String imgUrl = "https://i.scdn.co/image/ab6761610000e5ebc02d416c309a68b04dc94576";
        Picasso.get().load(imgUrl).into(IV);
        return listitemView;
    }
}