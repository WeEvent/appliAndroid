package nf28.weevent.Controller;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import nf28.weevent.Controller.List.ActionSlideExpandableListView;
import nf28.weevent.Model.Group;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by CD on 14/05/2015.
 */
public class GroupActivity extends MainActivity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group);

        final TextView groupName = (TextView) this.findViewById(R.id.groupName);
        String name = getIntent().getStringExtra("group");
        groupName.setText(name);

        ActionSlideExpandableListView list = (ActionSlideExpandableListView)this.findViewById(R.id.groupList);

        list.setAdapter(buildData(name));
        list.setItemActionListener(new ActionSlideExpandableListView.OnActionClickListener() {
            @Override
            public void onClick(View listView, View buttonView, int position) {
                   String actionName = "";
                   if(buttonView.getId()==R.id.buttonA) {
                         actionName = "buttonA";
                   } else {
                         actionName = "ButtonB";
                   }

                   Toast.makeText(
                           GroupActivity.this,
                           "Clicked Action: " + actionName + " in list item " + position,
                           Toast.LENGTH_SHORT
                           ).show();
            }
        }, R.id.buttonA, R.id.buttonB);
    }

    public ListAdapter buildData(String groupName) {

        HashMap<String,Group> groups = DataManager.getInstance().getUser().getGroups();
        Group group = groups.get(groupName);

        List<String> values = group.getContactsList();

        return new ArrayAdapter<String>(
                this,
                R.layout.expandable_list_item,
                R.id.text,
                values);
    }


}
