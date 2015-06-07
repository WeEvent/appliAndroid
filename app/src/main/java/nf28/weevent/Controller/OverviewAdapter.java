package nf28.weevent.Controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.Vector;

import nf28.weevent.R;

/**
 * Created by KM on 23/05/15.
 */

public class OverviewAdapter extends ArrayAdapter<ModelAdapter>{
    Vector<ModelAdapter> modelItems = null;
    Vector<ModelAdapter> originalItems = null;

    Context context;
    int pos = 0;
    public OverviewAdapter(Context context, Vector<ModelAdapter> resource) {
        super(context, R.layout.check_overview,resource);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.modelItems = resource;
        this.originalItems = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.check_overview, parent, false);
        //TextView name = (TextView) convertView.findViewById(R.id.textView1);
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.new_overview);
        ImageView img = (ImageView) convertView.findViewById(R.id.imageViewOverview);
        pos = position;  // update the position


        if(modelItems.elementAt(position) !=null) {
            Vector<Integer> tabs = ViewPagerAdapter.getTabs();

            cb.setText(modelItems.elementAt(position).getName());
            cb.setEnabled(false);
            System.err.println(tabs);

            switch(tabs.elementAt(position)) {
                case 0:img.setImageResource(R.drawable.ic_time);
                    break;
                case 1:img.setImageResource(R.drawable.ic_place);
                    break;
                case 2:img.setImageResource(R.drawable.ic_transport);
                    break;
                default :
                    img.setImageResource(R.drawable.ic_evt);
            }
            if (modelItems.elementAt(position).getValue() == 1)
                cb.setChecked(true);
            else
                cb.setChecked(false);
        }else{
            cb.setVisibility(View.GONE);
        }

        return convertView;
    }


}