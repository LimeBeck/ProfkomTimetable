package ru.profdstu.profkomtimetable;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;


/**
 * Created by metal on 11.03.2018.
 */

public class SettingsFragment extends Fragment {

    //private EditText mGroup;
    private AutoCompleteTextView mGroup;
    private Button mOkButton;
    private Boolean mFirstUse;
    private WebHelper mWebHelper;
    protected TimeTableData mTimeTableData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        final TimeTableData timeTableData = TimeTableData.get(getContext());
        mWebHelper = WebHelper.getInstance(getContext());
        mTimeTableData = TimeTableData.get(getContext());
        mFirstUse = timeTableData.getParameterFromSettings(SettingSchema.Settings.FIRST_RUN).contentEquals("true");

        mGroup = (AutoCompleteTextView) v.findViewById(R.id.edit_usergroup);
        mGroup.setThreshold(1);
        mGroup.setAdapter(new LessonAutocomleteAdapter(getContext()));
        mGroup.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                mGroup.setText((String) adapterView.getItemAtPosition(position));
            }
        });


        final String group = timeTableData.getParameterFromSettings(SettingSchema.Settings.USER_GROUP);
        if (group != null) {
            mGroup.setText(group);
        }

        mOkButton = (Button) v.findViewById(R.id.btn_ok);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mOkButton.setEnabled(false);
                timeTableData.setParameterToSettings(SettingSchema.Settings.USER_GROUP, mGroup.getText().toString().toUpperCase());
                timeTableData.setParameterToSettings(SettingSchema.Settings.FIRST_RUN, "false");

                mWebHelper.makeLessonsRequest(mGroup.getText().toString().toUpperCase(), new WebHelper.VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        //Toast toast = Toast.makeText(getContext(),"Готово!",Toast.LENGTH_SHORT);
                        //toast.show();
                        getActivity().finish();

                    }
                },mTimeTableData);

            }
        });

        return v;
    }



}
