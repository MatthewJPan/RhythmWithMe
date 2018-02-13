package com.example.mac.uemr;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lovexieyuan520 on 2015/10/9.
 */

public class EmptyViewHolder extends RecyclerView.ViewHolder {
    public TextView mTextView;
    public EmptyViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView)itemView.findViewById(R.id.textViewempty);
    }
}
