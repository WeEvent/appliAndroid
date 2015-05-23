package nf28.weevent.Controller;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import nf28.weevent.Model.Event;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by KM on 13/05/15.
 */
public class Description extends Fragment {
    private Button sendValid = null;
    private TextView eventDesc = null;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.description,container,false);
        // Assigning ViewPager View and setting the adapter
        sendValid = (Button)v.findViewById(R.id.btnSendInvitation);

        // Assigning ViewPager View and setting the adapter
        eventDesc = (TextView)v.findViewById(R.id.eventDescription);

        eventDesc.setText(CategoriesActivity.getSelectedEvt().getDesc());


        sendValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }
}