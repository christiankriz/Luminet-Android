package com.luminet.mobile.luminetandroid.screenViewsFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.activity.UpdatePasswordActivity;
import com.luminet.mobile.luminetandroid.login.Login;
import com.luminet.mobile.luminetandroid.login.LoginMain;
import com.luminet.mobile.luminetandroid.login.RegisterUser;

import static com.luminet.mobile.luminetandroid.LuminetSlashScreen.MyPREFERENCES;

/**
 * Created by chris on 2017/10/17.
 */

public class UserProfileFragment  extends Fragment implements View.OnClickListener{
    public static final String ARG_PAGE = "ARG_PAGE";

    private View view;
    private int mPage;

    Button userNameButton, changePasswordButton, supportButton, termsConditionButton, aboutButton, logoutButton;

    public static UserProfileFragment newInstane(int page){
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_user_profile, parent, false);
        userNameButton = view.findViewById(R.id.user_detail_button);
        userNameButton.setOnClickListener(this);
        changePasswordButton = view.findViewById(R.id.to_change_password_screen_button);
        changePasswordButton.setOnClickListener(this);
        supportButton = view.findViewById(R.id.support_button);
        supportButton.setOnClickListener(this);
        termsConditionButton = view.findViewById(R.id.terms_and_condition_button);
        termsConditionButton.setOnClickListener(this);
        aboutButton = view.findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(this);
        return view;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {

            case R.id.user_detail_button:
                break;

            case R.id.to_change_password_screen_button:
                i = new Intent(getContext(), UpdatePasswordActivity.class);
                startActivity(i);

                break;

            case R.id.support_button:
                break;

            case R.id.terms_and_condition_button:
                break;

            case R.id.about_button:
                break;

            case R.id.logout_button:
                SharedPreferences settings = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                settings.edit().clear().commit();
                i = new Intent(getContext(), LoginMain.class);
                startActivity(i);
                break;

            default:
                break;
        }

    }

}
