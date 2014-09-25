package com.example.AndroidHelloWorld;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainMenuActivity extends ListActivity {

    private final MainMenuItem[] menuItems = MainMenuItem.values();
    private ArrayAdapter<MainMenuItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ArrayAdapter<MainMenuItem>(this, android.R.layout.simple_list_item_1, menuItems);
        setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        MainMenuItem selectedItem = (MainMenuItem) l.getItemAtPosition(position);
        launchActivity(selectedItem.getActivity());
    }

    private void launchActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_about: {
                launchActivity(AboutActivity.class);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
