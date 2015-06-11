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
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import nf28.weevent.Model.Group;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by CD on 19/05/2015.
 */
public class AddContactToGroupSelectGroupFragment extends Fragment{

    ArrayAdapter<String> adapter;
    ListView list;

    @Override
    public ListView onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

        list = (ListView)inflater.inflate(R.layout.groups, container, false);

        adapter = buildData();
        adapter.sort(new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.toLowerCase().compareTo(rhs.toLowerCase());   //or whatever your sorting algorithm
            }
        });
        list.setAdapter(adapter);

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

                if(IsListEmpty())
                {
                    activity.onBackPressed();
                }
                else
                {
                    adapter = buildData();
                    list.setAdapter(adapter);
                }
            }
        };

        list.setOnItemClickListener(l);

        return list;
    }

    public ArrayAdapter<String> buildData() {

        AddContactToGroupSelectGroupActivity activity = (AddContactToGroupSelectGroupActivity) getActivity();
        String contactToAdd = activity.getContactToAdd();

        HashMap<String,Group> groups = DataManager.getInstance().getUser().getGroups();
        List<String> values = new ArrayList<String>();

        for (String key: groups.keySet()) {
            Group group = groups.get(key);
            if(!group.getContactsList().contains(contactToAdd))
            {
                values.add(key);
            }
        }

        return new ArrayAdapter<String>
                (getActivity(), R.layout.simple_list_item, R.id.text, values);

    }

    private Boolean IsListEmpty(){
        AddContactToGroupSelectGroupActivity activity = (AddContactToGroupSelectGroupActivity) getActivity();
        String contactToAdd = activity.getContactToAdd();

        HashMap<String,Group> groups = DataManager.getInstance().getUser().getGroups();

        for (String key: groups.keySet()) {
            Group group = groups.get(key);
            if(!group.getContactsList().contains(contactToAdd))
            {
                return false;
            }
        }

        return true;
    }
}
