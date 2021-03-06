package com.example.skybeatz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
        TextView TV2 = listitemView.findViewById(R.id.textView3);
        ImageView IV = listitemView.findViewById(R.id.img1);
        TV1.setText(cardModel.getName() );
        TV2.setText(cardModel.getArtistName() );
//        IV.setImageResource(cardModel.getImgid());
        String imgUrl = cardModel.getImg_url();
        Picasso.get().load(imgUrl).into(IV);
        return listitemView;
    }
}
//
//
//public class cardAdapter extends FragmentStateAdapter{
//    ArrayList<cardModel> cardModelArrayList = new ArrayList<cardModel>();
//
//    public cardAdapter(@NonNull FragmentActivity fragmentActivity) {
//        super(fragmentActivity);
//    }
//
//    @NonNull
//    @Override
//    public Fragment createFragment(int position) {
//        return null;
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//}