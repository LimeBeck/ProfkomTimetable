package ru.profdstu.profkomtimetable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created by metal on 11.03.2018.
 */

public class SettingsFragment extends Fragment {

    private EditText mGroup;
    private Button mOkButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        TimeTableData timeTableData = TimeTableData.get(getContext());

        mGroup = (EditText) v.findViewById(R.id.edit_usergroup);
        String group = timeTableData.getGroupFromSettings();
        if(group!=null){
            mGroup.setText(group);
        }
        else{
            //Сюда дописать чет, надо придумать
        }

        mOkButton = (Button) v.findViewById(R.id.btn_ok);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Тут будет реализованна загрузка расписания группы с сервера
            }
        });

        return v;
    }

}
