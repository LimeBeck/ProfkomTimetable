package ru.profdstu.profkomtimetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by metal on 09.03.2018.
 */

public class DayActivity extends AppCompatActivity {
    TimeTableData mTimeTableData;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);



        if(fragment == null){
            fragment = new DaysFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
