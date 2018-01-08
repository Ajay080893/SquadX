package com.example.user.squadx.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.squadx.R;

import java.util.ArrayList;

/**
 * Created by user on 1/6/2018.
 */

public class SQLiteListAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> userID;
    ArrayList<String> UserDATE;
    ArrayList<String> User_TIME;
    ArrayList<String> UserQuantity ;
    ArrayList<String> UserInvesttype ;
    ArrayList<String> UserTotalValue ;


    public SQLiteListAdapter(
            Context context2,
            ArrayList<String> id,
            ArrayList<String> date,
            ArrayList<String> time,
            ArrayList<String> quantity,
             ArrayList<String> investtype ,
                    ArrayList<String> totalvalue
    )
    {

        this.context = context2;
        this.userID = id;
        this.UserDATE = date;
        this.User_TIME = time;
        this.UserQuantity = quantity ;
        this.UserInvesttype = investtype ;
        this.UserTotalValue = totalvalue ;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return userID.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {

        Holder holder;

        LayoutInflater layoutInflater;

        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listviewdatalayout, null);

            holder = new Holder();


            holder.txtdate = (TextView) child.findViewById(R.id.txtdate);
            holder.txttime = (TextView) child.findViewById(R.id.txttime);
            holder.txtinvesttype = (TextView) child.findViewById(R.id.txttype);
            holder.txttotalvalue = (TextView) child.findViewById(R.id.txtvalues);
            holder.txtquantity = (TextView) child.findViewById(R.id.txtquantity);

            child.setTag(holder);

        } else {

            holder = (Holder) child.getTag();
        }
        holder.txtdate.setText(UserDATE.get(position));
        holder.txttime.setText(User_TIME.get(position));
        holder.txtquantity.setText(UserQuantity.get(position));
        holder.txtinvesttype.setText(UserInvesttype.get(position));
        holder.txttotalvalue.setText(UserTotalValue.get(position));

        return child;
    }

    public class Holder {
        TextView txtdate;
        TextView txttime;
        TextView txtquantity;
        TextView txtinvesttype;
        TextView txttotalvalue;
    }

}