package nf28.weevent.Controller;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import nf28.weevent.R;

/**
 * Created by CD on 13/05/2015.
 */
public class GroupsActivity extends Fragment{

    @Override
    public ListView onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ListView list = (ListView)inflater.inflate(R.layout.groups, container, false);

        list.setAdapter(buildDummyData());

        AdapterView.OnItemClickListener l = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GroupActivity.class);
                intent.putExtra("group", parent.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        };

        list.setOnItemClickListener(l);

        return list;
    }

    public ListAdapter buildDummyData() {
        String [] options = {
                "NF28",
                "RU",
                "UTC",
                "Famille",
                "Filles",
                "Garçons",
                "Autres"
        };

        return new ArrayAdapter<String>
                (getActivity(), R.layout.groups_list_item, R.id.text, options);

    }
}
