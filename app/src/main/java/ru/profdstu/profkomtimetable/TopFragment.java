package ru.profdstu.profkomtimetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by metal on 11.03.2018.
 */

public class TopFragment extends Fragment {

    private TimeTableData mTimeTableData;
    FragmentManager mFragmentManager;
    public Boolean mWeekEven = WeekCompute.IsWeekEven();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mFragmentManager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day_list, container, false);

        updateUI();

        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_day_list, menu);
        MenuItem weekButton = menu.findItem(R.id.change_week);
        if (mWeekEven) {
            weekButton.setTitle(R.string.to_nor_even_week);
        } else {
            weekButton.setTitle(R.string.to_even_week);
        }

        MenuItem settingsButton = menu.findItem(R.id.settings);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_week:
                mWeekEven = !mWeekEven;
                getActivity().invalidateOptionsMenu();
                updateUI();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateUI() {

        List<Fragment> fragments = mFragmentManager.getFragments();
        if(fragments.size()>1){
            for (Fragment fragment: fragments
                 ) {
                if(fragment!=mFragmentManager.findFragmentById(R.id.fragment_container)){
                    mFragmentManager.beginTransaction().remove(fragment).commit();
                }

            }
        }

        Fragment fragment;
        int week = mWeekEven?2:1;

        for(int day=1;day<=6;day++){
            fragment = DaysFragment.newInstance(day, week);
            mFragmentManager.beginTransaction().add(R.id.day_view, fragment).commit();
        }

    }
}
