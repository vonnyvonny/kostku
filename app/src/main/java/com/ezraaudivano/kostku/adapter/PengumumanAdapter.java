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
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ezraaudivano.kostku.API.PengumumanAPI;
import com.ezraaudivano.kostku.EditKostActivity;
import com.ezraaudivano.kostku.R;
import com.ezraaudivano.kostku.model.Pengumuman;
import com.ezraaudivano.kostku.ui.pengumuman.TambahEditPengumuman;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.DELETE;

public class PengumumanAdapter extends RecyclerView.Adapter<PengumumanAdapter.pengumumanAdapterViewHolder> {

    private List<Pengumuman> pengumumanList;
    private List<Pengumuman> pengumumanListFiltered;
    private Context context;
    private View view;
    private deleteItemListener mListener;

    public PengumumanAdapter(Context context, List<Pengumuman> pengumumanList,
                             deleteItemListener mListener) {
        this.context            = context;
        this.pengumumanList           = pengumumanList;
        this.pengumumanListFiltered   = pengumumanList;
        this.mListener          = mListener;
    }

    public interface deleteItemListener {
        void deleteItem( Boolean delete);
    }

    @NonNull
    @Override
    public pengumumanAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.activity_pengumuman_adapter, parent, false);
        return new pengumumanAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull pengumumanAdapterViewHolder holder, int position) {
        final Pengumuman pengumuman = pengumumanListFiltered.get(position);

        holder.txtJudul.setText(pengumuman.getJudul());
        holder.txtSubjudul.setText(pengumuman.getSubjudul());
        holder.txtKonten.setText(pengumuman.getKonten());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Bundle data = new Bundle();
                data.putSerializable("pengumuman", pengumuman);
                data.putString("status", "edit");
                TambahEditPengumuman tambahEditPengumuman = new TambahEditPengumuman();
                tambahEditPengumuman.setArguments(data);
                loadFragment(tambahEditPengumuman);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Anda yakin ingin menghapus Pengumuman ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletePengumuman(pengumuman);
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
//        holder.ivHapus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setMessage("Anda yakin ingin menghapus buku ?");
//                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        deleteBuku(pengumuman);
//                    }
//                });
//                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                AlertDialog alert = builder.create();
//                alert.show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return (pengumumanListFiltered != null) ? pengumumanListFiltered.size() : 0;
    }

    public class pengumumanAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView txtJudul, txtSubjudul, txtKonten;
        private Button btnEdit, btnDelete;
        private CardView cardPengumuman;

        public pengumumanAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul        = itemView.findViewById(R.id.tvJudul);
            txtSubjudul     = itemView.findViewById(R.id.tvSubjudul);
            txtKonten       = itemView.findViewById(R.id.tvKonten);
            btnEdit         = itemView.findViewById(R.id.btnEdit);
            btnDelete       = itemView.findViewById(R.id.btnDelete);
            cardPengumuman  = itemView.findViewById(R.id.cardPengumuman);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String userInput = charSequence.toString();
                if (userInput.isEmpty()) {
                    pengumumanListFiltered = pengumumanList;
                }
                else {
                    List<Pengumuman> filteredList = new ArrayList<>();
                    for(Pengumuman pengumuman : pengumumanList) {
                        if(String.valueOf(pengumuman.getJudul()).toLowerCase().contains(userInput) ||
                                pengumuman.getSubjudul().toLowerCase().contains(userInput)) {
                            filteredList.add(pengumuman);
                        }
                    }
                    pengumumanListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = pengumumanListFiltered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                pengumumanListFiltered = (ArrayList<Pengumuman>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void loadFragment(Fragment fragment) {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_view_pengumuman,fragment)
                .commit();
    }

    public void deletePengumuman(Pengumuman pengumuman){
        //Tambahkan hapus pengumuman disini
        RequestQueue queue = Volley.newRequestQueue(context);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menghapus data pengumuman");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(DELETE, PengumumanAPI.URL_DELETE + pengumuman.getId(),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
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
                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}