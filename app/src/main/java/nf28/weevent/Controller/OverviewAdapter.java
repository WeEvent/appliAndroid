package nf28.weevent.Controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import nf28.weevent.R;

/**
 * Created by KM on 23/05/15.
 */

public class OverviewAdapter extends ArrayAdapter<ModelAdapter>{
    ModelAdapter[] modelItems = null;
    ModelAdapter[] originalItems = null;
    CustomFilter filter = null;
    Context context;
    int pos = 0;
    public OverviewAdapter(Context context, ModelAdapter[] resource) {
        super(context, R.layout.check_friend,resource);
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


        if(modelItems !=null) {
            int posit = position %10;
            cb.setText(modelItems[posit].getName());
            cb.setEnabled(false);
            int idx = position /10;
            switch(position) {
                case 0:img.setImageResource(R.drawable.ic_time);
                    break;
                case 1:img.setImageResource(R.drawable.ic_place);
                    break;
                case 2:img.setImageResource(R.drawable.ic_transport);
                    break;
                default :
                    img.setImageResource(R.drawable.ic_overview);
            }
            if (modelItems[posit].getValue() == 1)
                cb.setChecked(true);
            else
                cb.setChecked(false);
        }

  //          cb.setVisibility(View.GONE);
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