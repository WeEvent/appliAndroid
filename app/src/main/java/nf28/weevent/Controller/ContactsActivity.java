package nf28.weevent.Controller;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import nf28.weevent.Controller.List.ActionSlideExpandableListView;
import nf28.weevent.R;

/**
 * Created by CD on 13/05/2015.
 */
public class ContactsActivity extends Fragment {

    @Override
    public ActionSlideExpandableListView onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ActionSlideExpandableListView list =  (ActionSlideExpandableListView) inflater
                .inflate(R.layout.contacts, container, false);

        list.setAdapter(buildDummyData());

        list.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {

            @Override
            public void onClick(View listView, View buttonview, int position) {

                String actionName = "";
                if(buttonview.getId()==R.id.buttonA) {
                    actionName = "buttonA";
                } else {
                    actionName = "ButtonB";
                }

                Toast.makeText(
                        getActivity(),
                        "Clicked Action: " + actionName + " in list item " + position,
                        Toast.LENGTH_SHORT
                ).show();

            }

        }, R.id.buttonA, R.id.buttonB);

        return list;
    }



    public ListAdapter buildDummyData() {
        String[] values = {
                "Clément Mercier",
                "Nicolas Rouquette",
                "Kostandin Misho",
                "Chloé Dejon"
        };
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







