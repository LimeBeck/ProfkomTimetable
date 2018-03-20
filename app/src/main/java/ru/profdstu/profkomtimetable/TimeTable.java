package ru.profdstu.profkomtimetable;

//Todo: Оставить комментарии для всего кода

import android.support.v4.app.Fragment;

public class TimeTable extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new TopFragment();
    }
}
