package nf28.weevent.Controller;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nf28.weevent.Controller.List.ActionSlideExpandableListView;
import nf28.weevent.Model.Group;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by CD on 13/05/2015.
 */
public class ContactsFragment extends Fragment {

    private ActionSlideExpandableListView list;
    private ArrayAdapter<String> adapter;
    private Integer positionToDelete;

    @Override
    public ActionSlideExpandableListView onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        list =  (ActionSlideExpandableListView) inflater
                .inflate(R.layout.contacts, container, false);

        adapter = buildData();
        list.setAdapter(adapter);

        list.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {

            @Override
            public void onClick(View listView, View clickedView, final int position) {

                if(clickedView.getId()==R.id.btn_add_contact_to_group) {
                    Intent intent = new Intent(getActivity(), AddContactToGroupSelectGroupActivity.class);
                    intent.putExtra("contactToAdd", list.getItemAtPosition(position).toString());
                    startActivity(intent);

                } else {
                    positionToDelete = position;
                    new AlertDialog.Builder(getActivity())
                            .setMessage("Are you sure you want to delete this contact ?")
                            .setCancelable(false)
                            .setNegativeButton("Yes", DeleteContactListener)
                            .setPositiveButton("No", null)
                            .show();

                }



            }

        }, R.id.btn_add_contact_to_group, R.id.btn_delete_contact);

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


    DialogInterface.OnClickListener DeleteContactListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            String loginToRemove = list.getItemAtPosition(positionToDelete).toString();
            list.collapse();
            DataManager.getInstance().removeContact(loginToRemove);
            for (Group group: DataManager.getInstance().getUser().getGroups().values()) {
                if(group.getContactsList().contains(loginToRemove)){
                    DataManager.getInstance().removeGroupUser(group.getName(), loginToRemove);
                }
            }

            adapter.notifyDataSetChanged();

            positionToDelete = null;
        }
    };

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







