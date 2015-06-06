
package nf28.weevent.Controller;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;

import nf28.weevent.Model.PollValue;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by KM on 13/05/15.
 */
public class Date extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    private Button addDate = null;
    private ListView mainListView = null;
    private ModelAdapter[] modelItems = new ModelAdapter[1];
    private DateAdapter adapter = null;
    private Context context = null;


    private String tvDisplayDate;
    private DatePicker dpResult;


    private int year;
    private int month;
    private int day;
    private int mHour       = 0;
    private int mMinute     = 0;

    private int id ;

    static final int TIME_DIALOG_ID = 100;

    static final int DATE_DIALOG_ID = 999;

    private Collection<PollValue> pollValues = null;
    // Search EditText
    EditText inputSearch = null;
    int pollIndex = 0; /// index used to fill the container for friends
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.date,container,false);
        context = inflater.getContext();
        mainListView = (ListView) v.findViewById( R.id.DateView );
        System.err.println( DataManager.getInstance().getSelectedEvt().getCategory("Cat_2").getName());
        pollValues = DataManager.getInstance().getSelectedEvt().getCategory("Cat_2").getPollValues();
        modelItems = new ModelAdapter[pollValues.size()];
        for (PollValue p : pollValues) {
            modelItems[pollIndex++] = new ModelAdapter(p.getValue(),(p.hasVoted(DataManager.getInstance().getUser().getLogin()))?1:0,p.getVotersCount());
        }
        //TODO a user can be inserted only once!!!!!!!
        adapter = new DateAdapter(context, modelItems);
        mainListView.setAdapter(adapter);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(context, ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                DataManager.getInstance().getSelectedEvt().getCategory("Cat_2").getPollValue(((TextView) view).getText()
                        .toString()).addVoter(DataManager.getInstance().getUser().getLogin());

            }
        });

        setCurrentDateOnView(v);
        addListenerOnButton(v);

        inputSearch = (EditText) v.findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //TODO filter not working properly
                Date.this.adapter.getFilter().filter(cs);
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
    // display current date
    public void setCurrentDateOnView(View v) {


        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        tvDisplayDate = (new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" ")).toString();

        // set current date into datepicker


    }


    public void addListenerOnButton(View v) {

        addDate = (Button) v.findViewById(R.id.add_new_date);

        addDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //showDialog(TIME_DIALOG_ID);
                id = DATE_DIALOG_ID;
                Dialog date = onCreateDialog(null);

                showTime();
                date.show();
            }

        });

    }
    private void showTime(){
        Dialog d = new TimePickerDialog(context,
                mTimeSetListener, mHour, mMinute, false);
        id = TIME_DIALOG_ID;

        d.show();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(context, datePickerListener,
                        year, month,day);
            case TIME_DIALOG_ID:
                return new TimePickerDialog(context,
                        mTimeSetListener, mHour, mMinute, false);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    tvDisplayDate +=(new StringBuilder().append(" : ").append(mHour)
                            .append("--").append(mMinute)
                            .append(" ")).toString();
                    updateDisplay();
                }

            };

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.

        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            tvDisplayDate = (new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" ")).toString();

            // set selected date into datepicker also
            //dpResult.init(year, month, day, null);

        }

    };

    private void updateDisplay() {

        String mDateDisplay = ""+(month + 1)+"-"+day+"-"+year+"       "+mHour+" : "+(mMinute);
        DataManager.getInstance().addLineToPoll("Cat_2",mDateDisplay);
        pollValues = DataManager.getInstance().getSelectedEvt().getCategory("Cat_2").getPollValues();
        modelItems = new ModelAdapter[pollValues.size()];
        pollIndex = 0;
        for (PollValue p : pollValues) {
            modelItems[pollIndex++] = new ModelAdapter(p.getValue(),(p.hasVoted(DataManager.getInstance().getUser().getLogin()))?1:0,p.getVotersCount());
        }

        adapter = new DateAdapter(context, modelItems);
        mainListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        Toast.makeText(context,tvDisplayDate, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(c.getTime());
    }


}