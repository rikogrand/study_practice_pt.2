package com.example.up.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.up.R;
import com.example.up.database.Database;
import com.example.up.database.entities.game;

import java.util.List;

public class gameAdapter extends ArrayAdapter<game> {
    private final LayoutInflater inflater;
    private final int layout;
    private final List<game> items;

    Database db;

    public gameAdapter(Context context, int resourse, List<game> items){
        super(context,resourse,items);
        this.items = items;
        this.layout=resourse;
        this.inflater = LayoutInflater.from(context);

        db=Database.getDatabase(getContext());
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        game item = items.get(position);
        viewHolder.Name.setText(item.name);
        viewHolder.Year.setText(String.valueOf( item.year ));
        viewHolder.Creator.setText(item.creator);
        viewHolder.Publisher.setText(item.publisher);
        return convertView;
    }

    private class ViewHolder{
        final TextView Name;
        final TextView Year;

        final TextView Creator;

        final TextView Publisher;
        public ViewHolder(View view){
            Name = view.findViewById(R.id.game_name);
            Year = view.findViewById(R.id.game_year);
            Creator = view.findViewById(R.id.game_creator);
            Publisher = view.findViewById(R.id.game_publisher);
        }
    }

    @Override
    public void remove(@Nullable game object) {
        if (object == null) {
            return;
        }
        items.remove(object);
        super.remove(object);
        notifyDataSetChanged();
    }
}
