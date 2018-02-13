package com.example.mac.uemr;

import org.junit.Test;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void BeanClassForRecyclerViewEMR_isCorrect() throws Exception {
        BeanClassForRecyclerView_EMR myEMR=new BeanClassForRecyclerView_EMR( "historyID", "diseaseName",
                "description", "medicineQuantity", "medicineName", "price", "date",false);
        assertEquals(myEMR.getHistoryID(), "historyID");
        assertEquals(myEMR.getDiseaseName(), "diseaseName");
        assertEquals(myEMR.getDescription(), "description");
        assertEquals(myEMR.getMedicineQuantity(), "medicineQuantity");
        assertEquals(myEMR.getMedicineName(), "medicineName");
        assertEquals(myEMR.getPrice(), "price");
        assertEquals(myEMR.getDate(), "date");
        assertEquals((boolean)myEMR.getType(), false);

    }
    @Test
    public void BeanClassForRecyclerViewPhmc_isCorrect() throws Exception {
        BeanClassForRecyclerView_Phmc myPhmc=new BeanClassForRecyclerView_Phmc( R.drawable.pharmacy,
        "medicineID", "medicineName", 1, 1.0);
        assertEquals(myPhmc.getMedicineID(), "medicineID");
        assertEquals(myPhmc.getMedcineName(), "medicineName");
        assertEquals(myPhmc.getMedicinePrice(), 1.0);
        assertEquals((int)myPhmc.getInventory(), 1);
    }
    @Test
    public void DatePickerAcivity_isCorrect() throws Exception {
        DatePickerActivity myActivity=new DatePickerActivity();
        Calendar c = Calendar.getInstance();
        int yearNow = c.get(Calendar.YEAR);
        int monthNow = c.get(Calendar.MONTH)+1;
        assertEquals(yearNow,2017);
        assertEquals(monthNow,4);
        assertEquals(myActivity.getMonthName(1),"January");
    }

    @Test
    public void SearchAcivity_isCorrect() throws Exception {
        SearchActivity mySearch=new SearchActivity();
        LinkedList<List<String>> transaction=new LinkedList<List<String>>();
        List record=new LinkedList();
        record.add("a");
        transaction.add(record);
        record.add("b");
        record.add("c");
        transaction.add(record);
        mySearch.FPGrowth(transaction, null);
        assertEquals(mySearch.resultSet[0][0],"2");

    }
}