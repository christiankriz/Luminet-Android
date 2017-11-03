package com.luminet.mobile.luminetandroid.newsFeed;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.api.MuleAPI;
import com.luminet.mobile.luminetandroid.data.EnterpriseDTO;
import com.luminet.mobile.luminetandroid.newsFeed.NewsFeed;
import com.luminet.mobile.luminetandroid.adapter.NewsFeedAdapter;
import com.luminet.mobile.luminetandroid.realm.RealmController;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.ArrayList;


public class DisplayNewsFeed extends AppCompatActivity {

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
    Realm realm;

    public String url= "http://196.223.97.152:11106/getlatestnewsfeedbyconsumer?limit=12&page=0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.realm = RealmController.with(this).getRealm();
        //toolbar =  findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setLogo(R.drawable.lumi_logo);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        Bundle extras = getIntent().getExtras();
        String userId = null;
        if (extras != null) {
            userId = extras.getString("userId");
        }
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
                        RealmResults<EnterpriseDTO> results = realm.where(EnterpriseDTO.class).findAll();
                        newsFeedAdapter = new NewsFeedAdapter(newsFeeds, getApplicationContext(), results );
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //stuff that updates ui
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
