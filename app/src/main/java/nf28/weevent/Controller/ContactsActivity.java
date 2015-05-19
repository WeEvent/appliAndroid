package nf28.weevent.Controller;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import nf28.weevent.Controller.List.ActionSlideExpandableListView;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by CD on 13/05/2015.
 */
public class ContactsActivity extends Fragment {

    private ActionSlideExpandableListView list;
    private ArrayAdapter<String> adapter;

    @Override
    public ActionSlideExpandableListView onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        list =  (ActionSlideExpandableListView) inflater
                .inflate(R.layout.contacts, container, false);

        adapter = buildData();
        list.setAdapter(adapter);

        list.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {

            @Override
            public void onClick(View listView, View clickedView, int position) {

                if(clickedView.getId()==R.id.buttonA) {
                    CharSequence text = "Coming soon!";
                    Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                    toast.show();

                } else {

                    String loginToRemove = list.getItemAtPosition(position).toString();
                    DataManager.getInstance().removeContact(loginToRemove);

                    adapter.notifyDataSetChanged();

                    CharSequence text = "Contact removed!";
                    Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                    toast.show();
                }



            }

        }, R.id.buttonA, R.id.buttonB);

        return list;
    }



    public ArrayAdapter<String> buildData() {

        List<String> values = DataManager.getInstance().getUser().getContactList();

        return new ArrayAdapter<String>(
                getActivity(),
                R.layout.expandable_list_item,
                R.id.text,
                values
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        getActivity().onBackPressed();
        switch (menuItem.getItemId()) {
            case R.id.action_home:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }


}







