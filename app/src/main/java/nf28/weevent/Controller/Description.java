package nf28.weevent.Controller;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
    private Button participants = null;
    private Button leaveEvent = null;
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
        participants = (Button)v.findViewById(R.id.btnParticipants);
        leaveEvent = (Button)v.findViewById(R.id.btnLeaveEvent);
        context = inflater.getContext();
        // Assigning ViewPager View and setting the adapter
        eventDesc = (TextView)v.findViewById(R.id.eventDescription);

        eventDesc.setText(DataManager.getInstance().getSelectedEvt().getDesc());

        eventDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);

                View promptView = layoutInflater.inflate(R.layout.dialog_description, null);

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
                                eventDesc.setText(input.getText().toString());
                                DataManager.getInstance().setDescEvent(input.getText().toString());
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
                //startActivity(new Intent(getActivity(), InviteFriendsActivity.class));
                startActivity(new Intent(getActivity(), SendInvitationActivity.class));
                getActivity().finish();
            }
        });
        participants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ParticipantsActivity.class));
                getActivity().finish();
            }
        });
        leaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setMessage("Are you sure you want to leave this event ?")
                        .setCancelable(false)
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                new Task().execute();
                            }
                        })
                        .setPositiveButton("No", null)
                        .show();

            }
        });

        return v;
    }

    private class Task extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            Boolean ret = DataManager.getInstance().removeContactFromEvent(DataManager.getInstance().getUser().getLogin());
            if (ret){
                sendMessage("updateEvents");
                getActivity().onBackPressed();
            }
            return ret;
        }
    }

    private void sendMessage(String signalName)
    {
        Intent intent = new Intent(signalName);
        //intent.putExtra("update",  "chat");
        getActivity().sendBroadcast(intent);
    }
}