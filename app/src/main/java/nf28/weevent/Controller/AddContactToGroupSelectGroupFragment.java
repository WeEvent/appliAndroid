package nf28.weevent.Controller;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nf28.weevent.Model.Group;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by CD on 19/05/2015.
 */
public class AddContactToGroupSelectGroupFragment extends Fragment{

    @Override
    public ListView onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

        ListView list = (ListView)inflater.inflate(R.layout.groups, container, false);

        list.setAdapter(buildData());

        AdapterView.OnItemClickListener l = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //retrieve the contact to add
                AddContactToGroupSelectGroupActivity activity = (AddContactToGroupSelectGroupActivity) getActivity();
                String contactToAdd = activity.getContactToAdd();

                // add contact to group
                String group = parent.getItemAtPosition(position).toString();
                DataManager.getInstance().addGroupUser(group, contactToAdd);

                //display message
                CharSequence text = "Contact added to group!";
                Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                toast.show();
            }
        };

        list.setOnItemClickListener(l);

        return list;
    }

    public ListAdapter buildData() {

        HashMap<String,Group> groups = DataManager.getInstance().getUser().getGroups();
        List<String> values = new ArrayList<String>();

        for (String key: groups.keySet()) {
            values.add(key);
        }

        return new ArrayAdapter<String>
                (getActivity(), R.layout.simple_list_item, R.id.text, values);

    }
}
