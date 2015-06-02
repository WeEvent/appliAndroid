package nf28.weevent.Controller;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import nf28.weevent.Model.Message;
import nf28.weevent.R;
import nf28.weevent.Tools.DataManager;

/**
 * Created by CD on 01/06/2015.
 */
public class ChatArrayAdapter extends ArrayAdapter<Message>{


    private TextView newMessage;
    private List<Message> messageList = new ArrayList<Message>();
    private LinearLayout wrapper;

    @Override
    public void add(Message object) {
        messageList.add(object);
        super.add(object);
    }

    @Override
    public void clear() {
        messageList.clear();
        super.clear();
    }

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public int getCount() {
        return this.messageList.size();
    }

    public Message getItem(int index) {
        return this.messageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.chat_list_item, parent, false);
        }

        wrapper = (LinearLayout) row.findViewById(R.id.wrapper);

        Message message = getItem(position);

        newMessage = (TextView) row.findViewById(R.id.comment);

        boolean isMessageFromCurrentUser = message.getLogin().equals(DataManager.getInstance().getUser().getLogin());

        if(isMessageFromCurrentUser)
        {
            newMessage.setText(message.getTextMsg());
        }
        else
        {
            newMessage.setText(message.getLogin()+":\n"+message.getTextMsg());
        }


        newMessage.setBackgroundResource(!isMessageFromCurrentUser ? R.drawable.bubble_yellow : R.drawable.bubble_green);
        wrapper.setGravity(!isMessageFromCurrentUser ? Gravity.LEFT : Gravity.RIGHT);

        return row;
    }
}
