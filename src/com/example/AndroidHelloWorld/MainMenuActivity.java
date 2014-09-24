package com.example.AndroidHelloWorld;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainMenuActivity extends ListActivity {

    private final String[] menuItems = new String[] { "WiFi Control", "Camera"};
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuItems);
        setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Toast.makeText(getApplicationContext(), l.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    }
}
