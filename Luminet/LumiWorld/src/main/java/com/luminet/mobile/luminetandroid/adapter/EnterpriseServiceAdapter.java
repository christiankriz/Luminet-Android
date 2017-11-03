package com.luminet.mobile.luminetandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.luminet.mobile.luminetandroid.R;
import com.luminet.mobile.luminetandroid.data.EnterpriseDTO;
import com.luminet.mobile.luminetandroid.data.EnterpriseServiceDTO;
import com.luminet.mobile.luminetandroid.data.ServicesDTO;
import com.luminet.mobile.luminetandroid.realm.RealmController;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by chris on 2017/11/03.
 */

public class EnterpriseServiceAdapter extends RealmRecyclerViewAdapater<EnterpriseDTO> {
    private Context context;
    private Realm realmController;
    private LayoutInflater inflater;
    private RealmResults<EnterpriseServiceDTO> enterpriseServiceDTOs;
    EnterpriseServiceDTO enterpriseServiceDTO;

    public EnterpriseServiceAdapter(Context context) {
        this.context = context;
    }

    public EnterpriseServiceAdapter(Context context, RealmResults<EnterpriseServiceDTO> enterpriseServiceDTOs) {
        this.context = context;
        this.enterpriseServiceDTOs = enterpriseServiceDTOs;
        this.realmController = RealmController.with((Activity) context).getRealm();
    }

    // create new views (invoked by the layout manager)
    @Override
    public EnterpriseServiceAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enterprise_service_item, parent, false);
        return new EnterpriseServiceAdapter.CardViewHolder(view);
    }

    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        enterpriseServiceDTO = enterpriseServiceDTOs.get(position);
        final EnterpriseServiceAdapter.CardViewHolder holder = (EnterpriseServiceAdapter.CardViewHolder) viewHolder;
        holder.card.setTag(position);
        holder.serviceButton.setText(getServiceName(enterpriseServiceDTO.getServiceID()));
        holder.serviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private String getServiceName(String serviceId){
        ServicesDTO servicesDTO = realmController.where(ServicesDTO.class).endsWith("id", serviceId).findFirst();
        return servicesDTO.getDisplayName();
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
        protected Button serviceButton;

        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.service_card_view);
            serviceButton = itemView.findViewById(R.id.service_name_button);
        }
    }
}
