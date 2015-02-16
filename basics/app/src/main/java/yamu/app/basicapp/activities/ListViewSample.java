package yamu.app.basicapp.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.google.common.collect.Lists;

import java.util.ArrayList;

/**
 * Created by Yali on 2/14/2015.
 */
public class ListViewSample extends ListActivity
{
    ArrayList<String> peoplesList = Lists.newArrayList();

    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        peoplesList.add("Allie");
        peoplesList.add("Yibin");
        peoplesList.add("Yali");

        listAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, peoplesList);

        setListAdapter(listAdapter);
    }


}
