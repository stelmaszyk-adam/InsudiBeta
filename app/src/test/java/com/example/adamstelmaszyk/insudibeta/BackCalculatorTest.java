package com.example.adamstelmaszyk.insudibeta;



import com.example.adamstelmaszyk.insudibeta.db.entity.DiaryEntry;

import org.junit.Test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static com.example.adamstelmaszyk.insudibeta.BackCalculator.calculateActiveInsuline;
import static com.example.adamstelmaszyk.insudibeta.BackCalculator.calculateInsulineDosage;
import static org.junit.Assert.*;

public class BackCalculatorTest {
    @Test
    public void calculateInsulineDosage1_AssertEqual(){

        assertEquals(5.6,calculateInsulineDosage(130,100,50,0,5,1,1,1 ), 0);
    }

    @Test
    public void calculateInsulineDosage2_ActiveInsulin_Sport_AssertEqual(){

        assertEquals(1.6,calculateInsulineDosage(200,100,50,4.2,2,1,1,1-0.2 ),0);
    }
    @Test
    public void calculateInsulineDosage3_lowSugar_AssertEqual(){

        assertEquals(0.08,calculateInsulineDosage(54,100,50,0,1,1,1,1 ), 0.001);
    }
    @Test
    public void calculateInsulineDosage4_WBT_AssertEqual(){

        assertEquals(9.4,calculateInsulineDosage(180,100,50,0,6,1,1.3,1 ), 0);
    }
    @Test
    public void calculateInsulineDosage5_ActiveInsulin_AssertEqual(){

        assertEquals(3,calculateInsulineDosage(140,100,50,8.2,3,1,1,1 ), 0);
    }
    @Test
    public void calculateInsulineDosage6_AssertEqual(){

        assertEquals(5.42,calculateInsulineDosage(121,100,50,0,5,1,1,1 ), 0);
    }
    @Test
    public void calculateInsulineDosage7_IG_AssertEqual(){

        assertEquals(5.02,calculateInsulineDosage(111,100,50,0,4,1,1.2,1 ), 0);
    }
    @Test
    public void calculateInsulineDosage8_ActiveInsulin_highSugar_AssertEqual(){

        assertEquals(2.76,calculateInsulineDosage(263,100,50,2.5,2,1,1,1 ), 0);
    }
    @Test
    public void calculateInsulineDosage9_lowSugar_AssertEqual(){

        assertEquals(2.4,calculateInsulineDosage(70,100,50,0,3,1,1,1 ), 0);
    }
    @Test
    public void calculateInsulineDosage10_ActiveInsulin_AssertEqual(){

        assertEquals(4.3,calculateInsulineDosage(195,100,50,1.6,4,1,1,1 ), 0);
    }

    @Test
    public void calculateActiveInsuline_AssertEqual(){
        List<DiaryEntry> list = new ArrayList<DiaryEntry>();

           long marzec5_19_8_00 = 1551769200000L;
            list.add(new DiaryEntry(5.6, new Date(marzec5_19_8_00)));

           long marzec5_19_9_00 = 1551772800000L;

           assertEquals(4.2, calculateActiveInsuline(list, marzec5_19_9_00), 0.01);

    }
    @Test
    public void calculateActiveInsuline_ToLastDate_AssertEqual() {
        List<DiaryEntry> list = new ArrayList<DiaryEntry>();

        long marzec5_19_8_00 = 1551769200000L;
        list.add(new DiaryEntry(5.6, new Date(marzec5_19_8_00)));
        long marzec5_19_9_00 = 1551772800000L;
        list.add(new DiaryEntry(1.6, new Date(marzec5_19_9_00)));
        long marzec5_19_14_00 = 1551790800000L;
        list.add(new DiaryEntry(0.1, new Date(marzec5_19_14_00)));
        long marzec5_19_17_00 = 1551801600000L;
        list.add(new DiaryEntry(9.4, new Date(marzec5_19_17_00)));
        long marzec5_19_17_30 = 1551803400000L;
        list.add(new DiaryEntry(3, new Date(marzec5_19_17_30)));
        long marzec5_19_21_00 = 1551816000000L;
        list.add(new DiaryEntry(5.42, new Date(marzec5_19_21_00)));
        long marzec6_19_06_00 = 1551848400000L;
        list.add(new DiaryEntry(5.02, new Date(marzec6_19_06_00)));
        long marzec6_19_08_00 = 1551855600000L;
        list.add(new DiaryEntry(2.76, new Date(marzec6_19_08_00)));
        long marzec6_19_15_00 = 1551880800000L;
        list.add(new DiaryEntry(2.4, new Date(marzec6_19_15_00)));
        long marzec6_19_16_00 = 1551884400000L;


        assertEquals(1.6, calculateActiveInsuline(list, marzec6_19_16_00), 0);
    }

}
