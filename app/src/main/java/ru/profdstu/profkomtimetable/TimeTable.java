package ru.profdstu.profkomtimetable;



import android.support.v4.app.Fragment;

public class TimeTable extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new TopFragment();
    }
}
