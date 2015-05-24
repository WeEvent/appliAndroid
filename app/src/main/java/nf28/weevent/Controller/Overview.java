package nf28.weevent.Controller;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by KM on 13/05/15.
 */
public class Overview extends Fragment {
    private Context context = null;
    private TextView eventDesc = null;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.overview,container,false);
        context = inflater.getContext();
        // Assigning ViewPager View and setting the adapter
        eventDesc = (TextView)v.findViewById(R.id.overview);

        eventDesc.setText(DataManager.getInstance().getSelectedEvt().getContactList().toString());

        return v;
    }
}