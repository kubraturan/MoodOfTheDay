package com.example.developer.moodoftheday;

/**
 * Created by developer on 28.09.2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by developer on 26.09.2017.
 */

public class MessagesAdapter extends ArrayAdapter<Message> {
    public MessagesAdapter(Context context, ArrayList<Message> messages) {
        super(context, 0, messages);
    }

    FirebaseUser user;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message msg = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itemmessage, parent, false);
        }
user= FirebaseAuth.getInstance().getCurrentUser();
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView message = (TextView) convertView.findViewById(R.id.message);
        TextView idsi = (TextView) convertView.findViewById(R.id.idsi);

        idsi.setText(user.getUid().toString());
        name.setText(msg.name);
        message.setText(msg.message);


        return convertView;
    }
}
