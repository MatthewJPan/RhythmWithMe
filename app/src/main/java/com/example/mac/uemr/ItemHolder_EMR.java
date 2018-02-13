package com.example.mac.uemr;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by mac on 17/4/5.
 */

public class ItemHolder_EMR extends RecyclerView.ViewHolder{

    TextView historyID;
    TextView diseaseName;
    TextView description;
    TextView medicineQuantity;
    TextView medicineName;
    TextView price;
    TextView date;
    LinearLayout back;


    public ItemHolder_EMR(View view) {
        super(view);

        historyID = (TextView) view.findViewById(R.id.historyid);
        diseaseName = (TextView) view.findViewById(R.id.diseasename);
        description= (TextView) view.findViewById(R.id.description);
        medicineQuantity = (TextView) view.findViewById(R.id.medicinequantity);
        medicineName = (TextView) view.findViewById(R.id.medicinename);
        price= (TextView) view.findViewById(R.id.price);
        date= (TextView) view.findViewById(R.id.date);
        back=(LinearLayout) view.findViewById(R.id.backgroundbox);
    }
}
