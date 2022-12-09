package com.sp.cryptotracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.cryptotracker.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.ViewHolder> {
    private ArrayList<CryptoModel> currencyRVModelArrayList;
    private Context context;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public CryptoAdapter(ArrayList<CryptoModel> currencyRVModelArrayList, Context context) {
        this.currencyRVModelArrayList = currencyRVModelArrayList;
        this.context = context;
    }
public void filterList(ArrayList<CryptoModel> filteredList) {
        currencyRVModelArrayList = filteredList;
        notifyDataSetChanged();
}
    @NonNull
    @Override
    public CryptoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.crypto_row, parent, false);
        return new CryptoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoAdapter.ViewHolder holder, int position) {
        CryptoModel currencyRVmodel = currencyRVModelArrayList.get(position);
        holder.currencyNameTV.setText(currencyRVmodel.getName());
        holder.symbolTV.setText(currencyRVmodel.getSymbol());
        holder.rateTV.setText("$ " + df2.format(currencyRVmodel.getPrice()));

        holder.cryptoSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }); //set button

    }

    @Override
    public int getItemCount() {
        return currencyRVModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView currencyNameTV, symbolTV, rateTV;
        private RelativeLayout cryptoSelection;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyNameTV = itemView.findViewById(R.id.cryptoNameRV);
            symbolTV = itemView.findViewById(R.id.symbolRV);
            rateTV = itemView.findViewById(R.id.priceRV);
            cryptoSelection = itemView.findViewById(R.id.cryptoRelativeLayout);

        }
    }
}
