package jp.example.ghclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class baseAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int layoutID;
    private String[] eventList;
    private String[] iconUri;
    private String[] eventDate;
    private Bitmap bitMap;


    static class ViewHolder {
        TextView event;
        TextView date;
        ImageView icon;
    }

    public baseAdapter(Context context, int itemLayoutId, String[] eventList, String[] iconUri, String[] eventDate ){
        this.context = context;
        inflater = LayoutInflater.from(context);
        layoutID = itemLayoutId;
        this.eventList = eventList;
        this.iconUri = iconUri;
        this.eventDate = eventDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(layoutID, null);
            holder = new ViewHolder();
            holder.event = convertView.findViewById(R.id.item_main);
            holder.date = convertView.findViewById(R.id.item_date);
            holder.icon = convertView.findViewById(R.id.item_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String[] str = eventList[position].split(" ");
        File file = new File(context.getCacheDir(), str[0]+".bmp");
        try {
            InputStream is = new FileInputStream(file);
            bitMap = BitmapFactory.decodeStream(new BufferedInputStream(is));
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.icon.setImageBitmap(bitMap);
        holder.date.setText(eventDate[position]);
        holder.event.setText(eventList[position]);

        return convertView;
    }
    @Override
    public int getCount() {
        return eventList.length;
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
}