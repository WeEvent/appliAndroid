package nf28.weevent.Controller;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import nf28.weevent.R;

/**
 * Created by CD on 05/06/2015.
 */
public class InviteGroupsFragment extends Fragment{

    @Override
    public ListView onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

        ListView list = (ListView)inflater.inflate(R.layout.simple_list, container, false);

        SendInvitationActivity activity = (SendInvitationActivity) getActivity();
        list.setAdapter(activity.getGroupsAdapter());

        return list;
    }
}
