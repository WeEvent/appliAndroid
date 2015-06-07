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
import java.util.List;

import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by CD on 25/05/2015.
 */
public class AddContactToGroupSelectContactFragment extends Fragment {
    ArrayAdapter<String> adapter;
    ListView list;

    @Override
    public ListView onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

        list = (ListView)inflater.inflate(R.layout.contacts_simple_list, container, false);

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
                AddContactToGroupSelectContactActivity activity = (AddContactToGroupSelectContactActivity) getActivity();
                String group = activity.getGroup();

                // add contact to group
                String contactToAdd = parent.getItemAtPosition(position).toString();
                DataManager.getInstance().addGroupUser(group, contactToAdd);

                //display message
                CharSequence text = "Contact added to group!";
                Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                toast.show();

                if(IsListEmpty())
                {
                    activity.finish();
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
        AddContactToGroupSelectContactActivity activity = (AddContactToGroupSelectContactActivity) getActivity();

        List<String> contacts = DataManager.getInstance().getUser().getContactList();
        List<String> contactsInGroup = DataManager.getInstance().getUser().getGroup(activity.getGroup()).getContactsList();

        List<String> contactsToAdd = new ArrayList<String>();

        for (String contact : contacts)
        {
            if (!contactsInGroup.contains(contact)){
                contactsToAdd.add(contact);
            }
        }

        return new ArrayAdapter<String>
                (getActivity(), R.layout.simple_list_item, R.id.text, contactsToAdd);

    }

    private Boolean IsListEmpty(){
        AddContactToGroupSelectContactActivity activity = (AddContactToGroupSelectContactActivity) getActivity();

        List<String> contacts = DataManager.getInstance().getUser().getContactList();
        List<String> contactsInGroup = DataManager.getInstance().getUser().getGroup(activity.getGroup()).getContactsList();

        for (String contact : contacts)
        {
            if (!contactsInGroup.contains(contact)){
                return false;
            }
        }

        return true;
    }
}
