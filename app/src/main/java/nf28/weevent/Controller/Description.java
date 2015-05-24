package nf28.weevent.Controller;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by KM on 13/05/15.
 */
public class Description extends Fragment {
    private Button sendValid = null;
    private TextView eventDesc = null;
    private Context context = null;

    ///

    private CheckBox radio_evt;
    private CheckBox radio_date;
    private CheckBox radio_map;
    private CheckBox radio_transp;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.description,container,false);
        // Assigning ViewPager View and setting the adapter
        sendValid = (Button)v.findViewById(R.id.btnSendInvitation);
        context = inflater.getContext();
        // Assigning ViewPager View and setting the adapter
        eventDesc = (TextView)v.findViewById(R.id.eventDescription);

        eventDesc.setText(DataManager.getInstance().getSelectedEvt().getDesc());

        eventDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);

                View promptView = layoutInflater.inflate(R.layout.dialog, null);

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
                                DataManager.getInstance().getSelectedEvt().setDesc(input.getText().toString());
                                eventDesc.setText(DataManager.getInstance().getSelectedEvt().getDesc());
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
        sendValid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),InviteFriendsActivity.class));
            }
        });

        return v;
    }
}