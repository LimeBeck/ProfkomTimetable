package ru.profdstu.profkomtimetable;

import android.support.v4.app.Fragment;

/**
 * Created by metal on 11.03.2018.
 */

public class SettingsActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment(){
        return new SettingsFragment();

    }


}
