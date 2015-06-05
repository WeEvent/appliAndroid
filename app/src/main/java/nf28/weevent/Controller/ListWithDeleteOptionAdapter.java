package nf28.weevent.Controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nf28.weevent.Model.Group;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by CD on 25/05/2015.
 */
public class ListWithDeleteOptionAdapter extends BaseAdapter implements ListAdapter {

    private String group;
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private Integer positionToDelete;

    public ListWithDeleteOptionAdapter(ArrayList<String> list, Context context, String grp) {
        this.list = list;
        this.context = context;
        this.group = grp;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_with_delete_option, null);
        }

        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        ImageButton deleteBtn = (ImageButton)view.findViewById(R.id.btn_delete_contact);

        final View currentView = view;
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                positionToDelete = position;
                new AlertDialog.Builder(currentView.getContext())
                        .setMessage("Are you sure you want to remove this contact from the group?")
                        .setCancelable(false)
                        .setNegativeButton("Yes", DeleteContactListener)
                        .setPositiveButton("No", null)
                        .show();


            }
        });

        return view;
    }

    DialogInterface.OnClickListener DeleteContactListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            DataManager.getInstance().removeGroupUser(group, list.get(positionToDelete));
            notifyDataSetChanged();

            positionToDelete = null;
        }
    };


}
