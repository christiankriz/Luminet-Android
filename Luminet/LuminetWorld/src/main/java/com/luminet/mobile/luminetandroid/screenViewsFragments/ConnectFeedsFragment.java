package com.luminet.mobile.luminetandroid.screenViewsFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.adapter.NewsFeedAdapter;
import com.luminet.mobile.luminetandroid.api.MuleAPI;
import com.luminet.mobile.luminetandroid.newsFeed.NewsFeed;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by chris on 2017/10/17.
 */

public class ConnectFeedsFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView.Adapter adapter;
    public static ArrayList<NewsFeed> newsFeeds = new ArrayList<NewsFeed>();
    private final OkHttpClient client = new OkHttpClient();
    ListView lv_newsfeeds;
    NewsFeedAdapter newsFeedAdapter;
    private Toolbar toolbar;
    private String userId;
    private View view;

    private int mPage;

    public static ConnectFeedsFragment newInstane(int page){
        ConnectFeedsFragment fragment = new ConnectFeedsFragment();
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
        view =  inflater.inflate(R.layout.connect_news_fragment, parent, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Bundle extras = getActivity().getIntent().getExtras();
        userId = null;
        if (extras != null) {
            userId = extras.getString("userId");
        }
        getNewsFeeds();
        return view;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }


    private void getNewsFeeds(){
        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("params", "{\"searchkey\":\"cell\",\"searchKeyValue\":\"" + userId + "\"}");
        RequestBody formBody = formBuilder.build();
        Request request = new Request.Builder()
                .url(MuleAPI.newsFeedsUrl)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    Log.v(TAG, "Check Log " + jsonData);
                    if (response.isSuccessful()) {
                        JSONArray jsonArr = new JSONArray(jsonData);
                        Gson gson = new Gson();
                        Type colType = new TypeToken<ArrayList<NewsFeed>>() {
                        }.getType();
                        newsFeeds = gson.fromJson(jsonArr.toString(), colType);

                        newsFeedAdapter = new NewsFeedAdapter(newsFeeds, getContext() );
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // This code will always run on the UI thread, therefore is safe to modify UI elements.
                                recyclerView.setAdapter(newsFeedAdapter);
                            }
                        });

                    } else {
                        ;
                    }
                } catch (IOException e) {
                    Log.v(TAG, "Exception caught : ", e);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
