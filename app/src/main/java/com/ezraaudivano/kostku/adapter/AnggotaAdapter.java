package com.ezraaudivano.kostku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ezraaudivano.kostku.BR;
import com.ezraaudivano.kostku.R;
import com.ezraaudivano.kostku.databinding.ItemAnggotaBinding;
import com.ezraaudivano.kostku.model.Anggota;

import java.util.List;

public class AnggotaAdapter extends RecyclerView.Adapter<AnggotaAdapter.MyViewHolder> {
    private Context context;
    private List<Anggota> result;

    public AnggotaAdapter(){}

    public AnggotaAdapter(Context context, List<Anggota> result){
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAnggotaBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_anggota,parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final Anggota anggota = result.get(position);
        holder.bind(anggota);

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ItemAnggotaBinding itemBind;

        public MyViewHolder(ItemAnggotaBinding binding){
            super(binding.getRoot());
            this.itemBind = binding;
        }

        public void bind (Object object)
        {
            itemBind.setVariable(BR.anggota, object);
            itemBind.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
        }
    }
}