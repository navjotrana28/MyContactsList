package com.example.mycontacts.ContactListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycontacts.models.GetContactsClass;
import com.example.mycontacts.R;
import com.example.mycontacts.interaces.AdapterClicklistener;

import java.util.List;

public class MyContactAdapter extends RecyclerView.Adapter<MyContactAdapter.MyViewHolder> {

    private Context context;
    private AdapterClicklistener adapterClicklistener;
    private List<GetContactsClass> contactModels;

    public MyContactAdapter(Context context, List<GetContactsClass> getContactsClasses, AdapterClicklistener adapterClicklistener) {
        this.context = context;
        contactModels = getContactsClasses;
        this.adapterClicklistener = adapterClicklistener;
    }

    @NonNull
    @Override
    public MyContactAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyContactAdapter.MyViewHolder holder, final int position) {
        holder.contactName.setText(contactModels.get(position).getContactName());
        holder.contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterClicklistener.onClick(contactModels.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (contactModels != null) {
            return contactModels.size();
        } else return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView contactName;
        ConstraintLayout contactView;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contact_name);
            contactView = itemView.findViewById(R.id.contact_view);
        }
    }

}
