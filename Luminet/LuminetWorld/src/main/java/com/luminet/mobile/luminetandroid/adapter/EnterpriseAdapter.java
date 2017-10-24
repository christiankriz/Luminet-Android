package com.luminet.mobile.luminetandroid.adapter;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.data.EnterpriseDTO;
import com.luminet.mobile.luminetandroid.login.Login;
import com.luminet.mobile.luminetandroid.realm.RealmController;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by chris on 2017/10/19.
 */

public class EnterpriseAdapter extends RealmRecyclerViewAdapater<EnterpriseDTO> {
    private Context context;
    private Realm realm;
    private LayoutInflater inflater;
    private RealmResults<EnterpriseDTO> enterpriseDTOs;
    private Context ctx;
    EnterpriseDTO enterprise;
    byte[] decodedString;
    ImageView imageView;
    String companyNumber;

    public EnterpriseAdapter(Context context) {
        this.context = context;
    }

    public EnterpriseAdapter(Context context, RealmResults<EnterpriseDTO> enterpriseDTOs) {
        this.context = context;
        this.enterpriseDTOs = enterpriseDTOs;
    }

    // create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enterprise_list_item, parent, false);
        return new CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        realm = RealmController.getInstance().getRealm();
        enterprise = enterpriseDTOs.get(position);
        final CardViewHolder holder = (CardViewHolder) viewHolder;
        holder.enterpriseImage.setImageBitmap(getBitmap(enterprise.getEnterpriseLogo()));
        holder.enterpriseName.setText(enterprise.getDisplayName());
        companyNumber = enterprise.getContactNumber();
        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        holder.following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
         });

    }

    private Bitmap getBitmap(String stringBitmap){
        Bitmap bitmap = null;
        String base64Image = null;
        if(stringBitmap.contains(",")) {
            base64Image = stringBitmap.split(",")[1];
            decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        return bitmap;
    }

    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public CardView card;
        protected ImageView enterpriseImage;
        protected TextView enterpriseName;
        protected ImageView follow, following;

        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.enterprise_card_view);
            enterpriseImage = itemView.findViewById(R.id.enterprise_logo);
            enterpriseName = itemView.findViewById(R.id.enterprise_name);
            follow = itemView.findViewById(R.id.follow_enterprise);
            following = itemView.findViewById(R.id.followed_enterprise);
        }
    }

    private void displayMessage(final String message, final String title){

            android.support.v7.app.AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new android.support.v7.app.AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new android.support.v7.app.AlertDialog.Builder(context);
                }
                builder.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Uri number = Uri.parse("tel:" + companyNumber);
                                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                                context.startActivity(callIntent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
    }

}