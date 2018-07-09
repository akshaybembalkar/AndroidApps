package com.ab.searchgooglemapsapi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ab.searchgooglemapsapi.R;
import com.ab.searchgooglemapsapi.retrofit_models.AddressModel;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    List<AddressModel> addressList;
    Context context;
    LayoutInflater inflater;

    public CustomAdapter(Context context, List<AddressModel> addressList) {
        this.context = context;
        this.addressList = addressList;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return addressList.size();
    }

    @Override
    public Object getItem(int i) {
        return addressList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView= inflater.inflate(R.layout.layout_list_item, null, true);

        TextView txtTitle = rowView.findViewById(R.id.locationName);
        txtTitle.setText(addressList.get(i).getFormattedAddress());
        return rowView;
    }
}
