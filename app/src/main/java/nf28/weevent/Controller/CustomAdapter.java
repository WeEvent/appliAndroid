package nf28.weevent.Controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import nf28.weevent.R;

/**
 * Created by KM on 23/05/15.
 */

public class CustomAdapter extends ArrayAdapter<ModelAdapter>{
        ModelAdapter[] modelItems = null;
        Context context;
        int pos = 0;
public CustomAdapter(Context context, ModelAdapter[] resource) {
        super(context, R.layout.check_friend,resource);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.modelItems = resource;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                convertView = inflater.inflate(R.layout.check_friend, parent, false);
                //TextView name = (TextView) convertView.findViewById(R.id.textView1);
                CheckBox cb = (CheckBox) convertView.findViewById(R.id.invited_friend);
                pos = position;  // update the position
                cb.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                    // TODO Auto-generated method stub
                     for(ModelAdapter m : modelItems)
                     {
                         if(m.getName().equalsIgnoreCase(arg0.getText().toString())){
                             m.setValue((arg1==true)?1:0);
                             break;
                         }
                     }
                }
            } );
                cb.setText(modelItems[position].getName());
                if(modelItems[position].getValue() == 1)
                cb.setChecked(true);
                else
                cb.setChecked(false);
                return convertView;
        }
  }