package com.example.mac.uemr;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mac on 17/3/15.
 */

public class ItemHolder_Phmc extends RecyclerView.ViewHolder {
    ImageView image;
    TextView medicineID;
    TextView medicineName;
    TextView medicinePrice;
    TextView inventory;
    TextView function;


    public ItemHolder_Phmc(View view) {
        super(view);

        image = (ImageView) view.findViewById(R.id.image);
        medicineID = (TextView) view.findViewById(R.id.drugid);
        medicineName = (TextView) view.findViewById(R.id.drugname);
        medicinePrice= (TextView) view.findViewById(R.id.drugprice);
        inventory = (TextView) view.findViewById(R.id.inventory);
        function=(TextView)view.findViewById(R.id.functonfield);
    }
}
