package com.example.AndroidHelloWorld.wifi;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.AndroidHelloWorld.R;

import java.util.List;

public class WifiScanArrayAdapter extends ArrayAdapter<ScanResult> {

    private final Context context;
    private final List<ScanResult> objects;

    public WifiScanArrayAdapter(Context context, List<ScanResult> objects) {
        super(context, R.layout.wifi_list_item, objects);
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
            itemView = inflater.inflate(R.layout.wifi_list_item, parent, false);

            holder = new ViewHolder();
            holder.txtSSID = (TextView) itemView.findViewById(R.id.txtSSID);
            holder.txtLevel = (TextView) itemView.findViewById(R.id.txtLevel);

            itemView.setTag(holder);
        } else {
            holder = (ViewHolder) itemView.getTag();
        }

        holder.txtSSID.setText(objects.get(position).SSID);

        int signalLevel = objects.get(position).level;
        holder.txtLevel.setText("Level: "+String.valueOf(signalLevel));
        holder.txtLevel.setTextColor(getLevelColor(signalLevel));

        return itemView;
    }

    private int getLevelColor(int signalLevel) {
        if (signalLevel >= -65) {
            return Color.GREEN;
        } else if (signalLevel < -65 && signalLevel >= 80) {
            return Color.YELLOW;
        } else {
            return Color.RED;
        }
    }

    public void clearAll() {
        objects.clear();
        this.clear();
    }
}
