
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

import java.util.HashMap;
import java.util.Vector;

import nf28.weevent.Model.PollValue;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by KM on 13/05/15.
 */
public class Overview extends Fragment {
    private ListView mainListView = null;
    private Vector<ModelAdapter> modelItems = new Vector<ModelAdapter>();
    private OverviewAdapter adapter = null;
    private Context context = null;
    private TextView img_empty = null;

    private HashMap<Integer,PollValue> pollValues = null;
    // Search EditText
    int pollIndex = 0; /// index used to fill the container for friends
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.overview,container,false);
        context = inflater.getContext();

        mainListView = (ListView) v.findViewById( R.id.overviewView );
        //System.err.println( DataManager.getInstance().getSelectedEvt().getCategory("Cat_3").getName());
        pollValues = DataManager.getInstance().getSelectedEvt().getPreferedPolls();

        for (Integer p : pollValues.keySet()) {
            if(pollValues.get(p)!= null)
                modelItems.add( new ModelAdapter(pollValues.get(p).getValue(),pollValues.get(p).hasVoted(DataManager.getInstance().getUser().getLogin())?1:0,pollValues.get(p).getVotersCount(),p));
        }
        //sort modelItems
        for(int i=0;i<modelItems.size()-1;i++)
            for(int j=i+1;j<modelItems.size();j++){
                if(modelItems.elementAt(j).getTab() < modelItems.elementAt(i).getTab()){
                    ModelAdapter tmp = modelItems.elementAt(i);
                    modelItems.set(i,modelItems.elementAt(j));
                    modelItems.set(j,tmp);
                }
            }
        //TODO a user can be inserted only once!!!!!!!
        System.out.println("++ \n "+ pollValues.keySet()+" \n++");

        img_empty = (TextView) v.findViewById(R.id.empty_view);
        if(modelItems.size()==0) {
            img_empty.setVisibility(View.VISIBLE);
        }else{
            img_empty.setVisibility(View.INVISIBLE);
        }

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