
package nf28.weevent.Controller;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

import nf28.weevent.Model.PollValue;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by KM on 13/05/15.
 */
public class Overview extends Fragment {
    private ListView mainListView = null;
    private ModelAdapter[] modelItems = new ModelAdapter[1];
    private OverviewAdapter adapter = null;
    private Context context = null;

    private Vector<PollValue> pollValues = null;
    // Search EditText
    int pollIndex = 0; /// index used to fill the container for friends
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.overview,container,false);
        context = inflater.getContext();

        mainListView = (ListView) v.findViewById( R.id.overviewView );
        //System.err.println( DataManager.getInstance().getSelectedEvt().getCategory("Cat_3").getName());
        pollValues = DataManager.getInstance().getSelectedEvt().getPreferedPolls();
        modelItems = new ModelAdapter[pollValues.size()];
        int i=0;
        for (PollValue p : pollValues) {
            if(p!= null)
                modelItems[pollIndex++] = new ModelAdapter(p.getValue(),10*i+((p.hasVoted(DataManager.getInstance().getUser().getLogin()))?1:0));
            i++;
        }
        //TODO a user can be inserted only once!!!!!!!
        System.out.println("++ \n "+ pollValues+" \n++");
        adapter = new OverviewAdapter(context, modelItems);

        mainListView.setAdapter(adapter);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(context, ((TextView) view).getText(), Toast.LENGTH_SHORT).show();


            }
        });

        return v;
    }
}