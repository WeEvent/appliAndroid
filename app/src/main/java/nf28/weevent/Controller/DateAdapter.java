package nf28.weevent.Controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by KM on 23/05/15.
 */

public class DateAdapter extends ArrayAdapter<ModelAdapter>{
        ModelAdapter[] modelItems = null;
        ModelAdapter[] originalItems = null;
        CustomFilter filter = null;
        Context context;
        int pos = 0;
public DateAdapter(Context context, ModelAdapter[] resource) {
        super(context, R.layout.check_date,resource);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.modelItems = resource;
        this.originalItems = resource;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                convertView = inflater.inflate(R.layout.check_date, parent, false);
                //TextView name = (TextView) convertView.findViewById(R.id.textView1);
                CheckBox cb = (CheckBox) convertView.findViewById(R.id.new_date);
                pos = position;  // update the position
                cb.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                    // TODO Auto-generated method stub
                    String text = arg0.getText().toString();
                     for(ModelAdapter m : modelItems)
                     {
                         if(m.getName().equalsIgnoreCase(text)){
                             //m.setValue((arg1==true)?1:0);
                             if(arg1==true){
                                 m.setValue(1);
                                 System.out.println("Added date");
                                 DataManager.getInstance().getSelectedEvt().getCategory("Cat_2").getPollValue(text
                                         .toString()).addVoter(DataManager.getInstance().getUser().getLogin());
                                 DataManager.getInstance().newVoteToPollValue("Cat_2",text
                                         .toString(),DataManager.getInstance().getUser().getLogin());
                             }else{
                                 m.setValue(0);
                                 System.out.println("Removed date");
                                 DataManager.getInstance().getSelectedEvt().getCategory("Cat_2").getPollValue(text
                                         .toString()).removeVoter(DataManager.getInstance().getUser().getLogin());
                                 DataManager.getInstance().removeVoteToPollValue("Cat_2",text
                                         .toString(),DataManager.getInstance().getUser().getLogin());
                             }


                             break;
                         }
                     }
                }
            } );
                if(position < modelItems.length) {
                    cb.setText(modelItems[position].getName());
                    if (modelItems[position].getValue() == 1)
                        cb.setChecked(true);
                    else
                        cb.setChecked(false);
                }else
                    cb.setVisibility(View.GONE);
                return convertView;
        }

    //TODO to be removed if there is a bug !!!!1
    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new CustomFilter();

        return filter;
    }

    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = originalItems;
                results.count = originalItems.length;
            } else {
                // We perform filtering operation
                List<ModelAdapter> modelList = new ArrayList<ModelAdapter>();

                for (ModelAdapter p : originalItems) {
                    if (p.getName().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        modelList.add(p);
                }

                ModelAdapter[] new_adapt = new ModelAdapter[modelList.size()];
                for (int i = 0; i < modelList.size(); i++)
                    new_adapt[i] = modelList.get(i);

                results.values = new_adapt;
                results.count = new_adapt.length;

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            // Now we have to inform the adapter about the new list filtered

            modelItems = (ModelAdapter[]) results.values;
            notifyDataSetChanged();

        }
    }
  }