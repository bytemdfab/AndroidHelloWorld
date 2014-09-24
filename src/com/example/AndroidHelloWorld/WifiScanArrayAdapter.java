package com.example.AndroidHelloWorld;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by byte on 23.09.2014.
 */
public class WifiScanArrayAdapter extends ArrayAdapter<ScanResult> {

    private final Context context;
    private final List<ScanResult> objects;

    public WifiScanArrayAdapter(Context context, List<ScanResult> objects) {
        super(context, R.layout.my_list_item, objects);
        this.context = context;
        this.objects = objects;
    }

    static class ViewHolder {
        public TextView txtSSID;
        public TextView txtLevel;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.my_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtSSID = (TextView) itemView.findViewById(R.id.txtSSID);
            holder.txtLevel = (TextView) itemView.findViewById(R.id.txtLevel);

            itemView.setTag(holder);
        } else {
            holder = (ViewHolder) itemView.getTag();
        }

        holder.txtSSID.setText(objects.get(position).SSID);
        holder.txtLevel.setText("Level: "+objects.get(position).level);

        return itemView;
    }
}
