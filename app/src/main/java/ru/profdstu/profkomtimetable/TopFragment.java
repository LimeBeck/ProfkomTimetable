package ru.profdstu.profkomtimetable;

import android.content.Intent;
import android.media.audiofx.Equalizer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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

    //Todo: Сохранить неделю при повороте экрана
    private TimeTableData mTimeTableData;
    FragmentManager mFragmentManager;
    public Boolean mWeekEven = WeekCompute.IsWeekEven();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mFragmentManager = getFragmentManager();
        mTimeTableData = TimeTableData.get(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day_list, container, false);
        String firstUseParameter = mTimeTableData.getParameterFromSettings(SettingSchema.Settings.FIRST_RUN);
        Boolean mFirstUse;
        if(firstUseParameter.contentEquals("true")){
            mFirstUse = true;
        }
        else{
            mFirstUse = false;
        }
        if(mFirstUse){
            startSettingsActivity();
        }
        updateUI();

        return v;

    }

    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_day_list, menu);
        MenuItem weekButton = menu.findItem(R.id.change_week);
        if (mWeekEven) {
            weekButton.setTitle(R.string.to_nor_even_week);
            //weekButton.setIcon(R.drawable.ic_to_up_week);
        } else {
            weekButton.setTitle(R.string.to_even_week);
            //weekButton.setIcon(R.drawable.ic_to_down_week);
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
            case R.id.settings:
                startSettingsActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startSettingsActivity(){
        Intent intent = new Intent(getActivity(),SettingsActivity.class);
        startActivity(intent);
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
        createSubtitle();
    }

    private void createSubtitle(){
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(WeekCompute.IsWeekEven()){
            activity.getSupportActionBar().setSubtitle(R.string.today_downweek);
        }
        else {
            activity.getSupportActionBar().setSubtitle(R.string.today_upweek);
        }
    }
}
