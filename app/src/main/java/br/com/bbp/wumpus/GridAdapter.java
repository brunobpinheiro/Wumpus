package br.com.bbp.wumpus;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Bruno on 23/03/2017.
 */

public class GridAdapter extends BaseAdapter {

    private int[] array;
    private Context context;
    private int size;

    public GridAdapter(Context context, int[] array) {

        this.context = context;
        this.array = array;

        SharedPreferences sharedPreferences = context.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE);
        size = sharedPreferences.getInt("size", 0);
    }

    @Override
    public int getCount() {
        return array.length;
    }

    @Override
    public Integer getItem(int position) {
        return array[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = new ImageView(context);
        imageView.setImageResource(array[position]);
        imageView.setAdjustViewBounds(true);
        return imageView;
    }
}
