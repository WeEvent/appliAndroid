package nf28.weevent.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.Vector;

import nf28.weevent.Model.Category;
import nf28.weevent.Model.Event;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by KM on 18/05/15.
 */
public class EventActivity extends Fragment {
    // All the radio buttons
    private CheckBox radio_desc;
    private CheckBox radio_evt;
    private CheckBox radio_date;
    private CheckBox radio_map;
    private CheckBox radio_transport;
    private CheckBox radio_type;
    private CheckBox radio_overview;
    private Context context  = null;
    private Button btn_events_create;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.update_event, container, false);
        context = inflater.getContext();


        // radio_desc = (CheckBox) findViewById(R.id.select_desc);
        radio_date = (CheckBox) v.findViewById(R.id.select_date);
        radio_map = (CheckBox) v.findViewById(R.id.select_map);
        radio_transport = (CheckBox) v.findViewById(R.id.select_transp);
        radio_type = (CheckBox) v.findViewById(R.id.select_type);
        //radio_overview = (CheckBox) findViewById(R.id.select_resume);

        updateCategory();
        Button btn_events_create = (Button) v.findViewById(R.id.btn_events_update);


        btn_events_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
                startActivity(new Intent(context,CategoriesActivity.class));
               // DataManager.getInstance().addEvent(DataManager.getInstance().getSelectedEvt());
                Toast.makeText(context," Added new Category", Toast.LENGTH_SHORT).show();
            }
        });

        return v;

    }

    private void updateCategory(){
        Event event = DataManager.getInstance().getSelectedEvt();
        if(event != null){
            for(Category cat : event.getCategoryList()) {

                if (cat.getName().equalsIgnoreCase("Cat_1")) {
                    radio_type.setEnabled(false);
                    radio_type.setChecked(true);
                }
                if (cat.getName().equalsIgnoreCase("Cat_2")) {
                    radio_date.setEnabled(false);
                    radio_date.setChecked(true);
                }else
                if (cat.getName().equalsIgnoreCase("Cat_3")) {
                    radio_map.setEnabled(false);
                    radio_map.setChecked(true);
                }else
                if (cat.getName().equalsIgnoreCase("Cat_4")) {
                    radio_transport.setEnabled(false);
                    radio_transport.setChecked(true);
                }
            }
        }else
            Toast.makeText(context," Network error ", Toast.LENGTH_SHORT).show();
    }


    private void init(){
        Vector<Integer> tabs = new Vector<Integer>();

        Event event = DataManager.getInstance().getSelectedEvt();

        tabs.add(0);

        tabs.add(1);


        if(radio_date.isChecked()) {
            tabs.add(2);
            if(radio_date.isEnabled()) {
                event.addCategory("Cat_2", "Categ_2");
                DataManager.getInstance().addCategory("Cat_2", new Category("Cat_2", "Categ_2"));
            }
        }
        if(radio_map.isChecked()) {
            tabs.add(3);
            if(radio_map.isEnabled()) {
                event.addCategory("Cat_3", "Categ_3");
                DataManager.getInstance().addCategory("Cat_3", new Category("Cat_3", "Categ_3"));
            }
        }
        if(radio_transport.isChecked()) {
            tabs.add(4);
            if(radio_transport.isEnabled()){
                event.addCategory("Cat_4","Categ_4") ;
                DataManager.getInstance().addCategory("Cat_2", new Category("Cat_4", "Categ_4"));

            }
        }


        tabs.add(5);
        tabs.add(6);

        DataManager.getInstance().setSelectedEvt(event);
        updateEventCategories(tabs);

    }

    private void updateEventCategories(Vector<Integer> tabs) {
        ViewPagerAdapter.resetTabs();
        for (Integer i : tabs) {
            ViewPagerAdapter.addTab(i);
        }
    }
}
