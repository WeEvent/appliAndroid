package nf28.weevent.Controller;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
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
