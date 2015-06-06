package nf28.weevent.Controller;

/*

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Collection;

import nf28.weevent.Model.PollValue;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import nf28.weevent.R;

/**
 * Created by KM on 13/05/15.


public class DatePickerActivity extends DialogFragment {
    private TextView tvDisplayDate;
    private DatePicker dpResult;
    private Button btnChangeDate;

    private int year;
    private int month;
    private int day;
    private int mHour       = 0;
    private int mMinute     = 0;

    static final int TIME_DIALOG_ID = 100;

    static final int DATE_DIALOG_ID = 999;
    private Context context = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.androiddate,container,false);

        context  = inflater.getContext();


        setCurrentDateOnView(v);
        addListenerOnButton(v);
        return v;
    }

    // display current date
    public void setCurrentDateOnView(View v) {

        tvDisplayDate = (TextView) v.findViewById(R.id.tvDate);
        dpResult = (DatePicker) v.findViewById(R.id.dpResult);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into textview
        tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

        // set current date into datepicker
        dpResult.init(year, month, day, null);

    }

    public void addListenerOnButton(View v) {

        btnChangeDate = (Button) v.findViewById(R.id.btnChangeDate);

        btnChangeDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(TIME_DIALOG_ID);

            }

        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
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
                    tvDisplayDate.setText(new StringBuilder().append(mHour)
                            .append("--").append(mMinute)
                            .append(" "));
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
            tvDisplayDate.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

            // set selected date into datepicker also
            dpResult.init(year, month, day, null);

        }
    };

}

//

package nf28.weevent.Controller;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Collection;

import nf28.weevent.Model.PollValue;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by KM on 13/05/15.

public class Date extends Fragment {
    private Button addDate = null;
    private ListView mainListView = null;
    private ModelAdapter[] modelItems = new ModelAdapter[1];
    private DateAdapter adapter = null;
    private Context context = null;


    private int mYear;
    private int mMonth;
    private int mDay;

    private int mHour       = 0;
    private int mMinute     = 0;

    static final int DATE_DIALOG_ID = 0;

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

        addDate = (Button) v.findViewById(R.id.add_new_date);

        addDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Dialog d = new DatePickerDialog(context,
                        mDateSetListener,
                        mYear, mMonth, mDay);
                System.err.println(mYear +" - "+mMonth);
                Intent intent = new Intent(context, MyAndroidDate.class);
                startActivity(intent);
/*
                ((DatePickerDialog)d).setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int buttonId) {
                        showTime();
                        System.out.println("\n\n\n\nUpdate launched\n\n\n");
                    }
                });


                // d.show();
            }

        });
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(context, ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                DataManager.getInstance().getSelectedEvt().getCategory("Cat_2").getPollValue(((TextView) view).getText()
                        .toString()).addVoter(DataManager.getInstance().getUser().getLogin());

            }
        });

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

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

    private void showTime(){
        Dialog d = new TimePickerDialog(context,
                mTimeSetListener, mHour, mMinute, false);

        ((TimePickerDialog)d).setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {

                updateDisplay();
                System.out.println("\n\n\n\nUpdate launched\n\n\n");
            }
        });
        d.show();
    }
    // the callback received when the user "sets" the time in the dialog
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    Toast.makeText(context,mMinute+"-"+mHour, Toast.LENGTH_SHORT).show();
                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
    // updates the date we display in the TextView
    private void updateDisplay() {

        String mDateDisplay = ""+(mMonth + 1)+"-"+mDay+"-"+mYear+"       "+mHour+" : "+(mMinute);
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
        Toast.makeText(context,mDateDisplay, Toast.LENGTH_SHORT).show();

    }



    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;

                }
            };
}
//
*/