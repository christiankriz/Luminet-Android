package com.luminet.mobile.luminetandroid.screenViewsFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.activity.EnterpriseListActivity;
import com.luminet.mobile.luminetandroid.adapter.EnterpriseAdapter;
import com.luminet.mobile.luminetandroid.data.EnterpriseDTO;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by chris on 2017/10/17.
 */

public class LumiWorldFragment extends Fragment implements View.OnClickListener{
    public static final String ARG_PAGE = "ARG_PAGE";

    private View view;
    private int mPage;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    List<EnterpriseDTO> enterpriseDTOsArray = new ArrayList<>();
    Button communicationButton, communityButton, educationButton, entertainmentButton, financeButton,
            healthBeautyButton, homeShoppingButton, sportsRecButton, travelTransButton,
            professionalServiceButton;
    Realm realm;
    EnterpriseAdapter enterpriseAdapter;

    public static LumiWorldFragment newInstane(int page){
        LumiWorldFragment fragment = new LumiWorldFragment();
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
        view =  inflater.inflate(R.layout.fragment_my_organisations, parent, false);
        communicationButton = view.findViewById(R.id.communication_button);
        communicationButton.setOnClickListener(this);
        communityButton = view.findViewById(R.id.communities_button);
        communityButton.setOnClickListener(this);
        educationButton = view.findViewById(R.id.education_button);
        educationButton.setOnClickListener(this);
        entertainmentButton = view.findViewById(R.id.entertainment_button);
        entertainmentButton.setOnClickListener(this);
        financeButton = view.findViewById(R.id.finances_button);
        financeButton.setOnClickListener(this);
        healthBeautyButton = view.findViewById(R.id.health_beaty_button);
        healthBeautyButton.setOnClickListener(this);
        homeShoppingButton = view.findViewById(R.id.home_and_shopping_button);
        homeShoppingButton.setOnClickListener(this);
        sportsRecButton = view.findViewById(R.id.sports_recreation_button);
        sportsRecButton.setOnClickListener(this);
        travelTransButton = view.findViewById(R.id.travel_and_Transport_button);
        travelTransButton.setOnClickListener(this);
        professionalServiceButton = view.findViewById(R.id.professional_servive_button);
        professionalServiceButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Button btn = (Button) v;
        String parentId = btn.getTag().toString();
        Intent intent = new Intent(getContext(), EnterpriseListActivity.class);
        intent.putExtra("parentId", parentId);
        startActivity(intent);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }


}

