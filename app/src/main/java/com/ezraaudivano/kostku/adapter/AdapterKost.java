package com.ezraaudivano.kostku.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ezraaudivano.kostku.API.KostAPI;
import com.ezraaudivano.kostku.AddKostActivity;
import com.ezraaudivano.kostku.EditKostActivity;
import com.ezraaudivano.kostku.databinding.ItemKostBinding;
import com.ezraaudivano.kostku.model.Kost;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import static com.android.volley.Request.Method.DELETE;

public class AdapterKost extends RecyclerView.Adapter<AdapterKost.kostViewHolder>{

    private List<Kost> listKost;
    private List<Kost> kostListFiltered;
    private Context context;
    private AdapterKost.deleteItemListener mListener;

    public AdapterKost(List<Kost> listKost, Context context, AdapterKost.deleteItemListener mListener) {
        this.listKost = listKost;
        this.context = context;
        this.mListener = mListener;
        notifyDataSetChanged();
    }

    public interface deleteItemListener {
        void deleteItem( Boolean delete);
    }

    @NonNull
    @Override
    public kostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemKostBinding binding = ItemKostBinding.inflate(layoutInflater, parent, false);

        return new AdapterKost.kostViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull kostViewHolder holder, int position) {
        final Kost kost = listKost.get(position);
        System.out.println("kost idx "+kost.getImageUrl());
        holder.myBinding(kost);

        holder.itemKostBinding.editKostBtnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditKostActivity.class);
                intent.putExtra("id", kost.getId());
                view.getContext().startActivity(intent);
            }
        });

        holder.itemKostBinding.deleteKostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this data?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                deleteKost(kost.getId());
                                System.out.println("kost id : "+kost.getId());
                                Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (listKost!=null) ? listKost.size():0;
    }

    public class kostViewHolder extends RecyclerView.ViewHolder{
        ItemKostBinding itemKostBinding;


        public kostViewHolder (@NonNull ItemKostBinding itemKostBinding){
            super(itemKostBinding.getRoot());
            this.itemKostBinding = itemKostBinding;

        }

        public void myBinding (Kost kost){
            itemKostBinding.setDafKost(kost);
            itemKostBinding.executePendingBindings();
        }

    }

    public void deleteKost(String id){
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(context);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menghapus data mahasiswa");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(DELETE, KostAPI.URL_DELETE + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);
                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    mListener.deleteItem(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}
