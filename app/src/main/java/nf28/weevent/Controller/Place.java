package nf28.weevent.Controller;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;

import nf28.weevent.Model.PollValue;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by KM on 13/05/15.
 */
public class Place extends Fragment {
    private Button addPlace = null;
    private ListView mainListView = null;
    private ModelAdapter[] modelItems = new ModelAdapter[1];
    private PlaceAdapter adapter = null;
    private Context context = null;

    private Collection<PollValue> pollValues = null;
    // Search EditText
    EditText inputSearch = null;
    int pollIndex = 0; /// index used to fill the container for friends
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.place,container,false);
        context =  context = inflater.getContext();
        mainListView = (ListView) v.findViewById( R.id.PlaceView );
        System.err.println( DataManager.getInstance().getSelectedEvt().getCategory("Cat_3").getName());
        pollValues = DataManager.getInstance().getSelectedEvt().getCategory("Cat_3").getPollValues();
        modelItems = new ModelAdapter[pollValues.size()];
        for (PollValue p : pollValues) {
            modelItems[pollIndex++] = new ModelAdapter(p.getValue(),(p.hasVoted(DataManager.getInstance().getUser().getLogin()))?1:0);
        }
        //TODO a user can be inserted only once!!!!!!!
        adapter = new PlaceAdapter(context, modelItems);
        mainListView.setAdapter(adapter);

        addPlace = (Button) v.findViewById(R.id.add_new_place);
        addPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);

                View promptView = layoutInflater.inflate(R.layout.dialog_place, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to be the layout file of the alertdialog builder
                alertDialogBuilder.setView(promptView);

                final EditText input = (EditText) promptView.findViewById(R.id.userInput);

                // setup a dialog window
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                               // DataManager.getInstance().getSelectedEvt().setDesc(input.getText().toString());
                                DataManager.getInstance().getSelectedEvt().getCategory("Cat_3").addPollValue(input.getText().toString());
                                pollValues = DataManager.getInstance().getSelectedEvt().getCategory("Cat_3").getPollValues();
                                modelItems = new ModelAdapter[pollValues.size()];
                                pollIndex = 0;
                                for (PollValue p : pollValues) {
                                    modelItems[pollIndex++] = new ModelAdapter(p.getValue(),(p.hasVoted(DataManager.getInstance().getUser().getLogin()))?1:0);
                                }

                               adapter = new PlaceAdapter(context, modelItems);
                               mainListView.setAdapter(adapter);

                               adapter.notifyDataSetChanged();
                               Toast.makeText(context,"Place added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create an alert dialog
                AlertDialog alertD = alertDialogBuilder.create();

                alertD.show();
            }

        });
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(context, ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                DataManager.getInstance().getSelectedEvt().getCategory("Cat_3").getPollValue(((TextView) view).getText()
                        .toString()).addVoter(DataManager.getInstance().getUser().getLogin());

            }
        });

        inputSearch = (EditText) v.findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //TODO filter not working properly
                Place.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });


        return v;
    }
}