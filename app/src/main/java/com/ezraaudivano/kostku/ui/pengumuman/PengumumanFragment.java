package com.ezraaudivano.kostku.ui.pengumuman;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ezraaudivano.kostku.AddKostActivity;
import com.ezraaudivano.kostku.R;
import com.ezraaudivano.kostku.adapter.PengumumanAdapter;
import com.ezraaudivano.kostku.API.PengumumanAPI;
import com.ezraaudivano.kostku.model.Pengumuman;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class PengumumanFragment extends Fragment {
    private RecyclerView recyclerView;
    private PengumumanAdapter adapter;
    private List<Pengumuman> listPengumuman;
    private View view;
    private FloatingActionButton floatingActionButton;
    private Integer col=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pengumuman, container, false);
        floatingActionButton = view.findViewById(R.id.fabAdd);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putString("status", "tambah");
                TambahEditPengumuman tambahEditPengumuman = new TambahEditPengumuman();
                tambahEditPengumuman.setArguments(data);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager .beginTransaction()
                        .replace(R.id.frame_view_pengumuman, tambahEditPengumuman)
                        .commit();
            }
        });
        loadDaftarPengumuman();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void loadDaftarPengumuman(){
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE)
            col=2;
        else
            col=1;
        setAdapter();
        getPengumuman();
    }

    public void setAdapter(){
        getActivity().setTitle("Data Pengumuman");
        /*Buat tampilan untuk adapter jika potrait menampilkan 2 data dalam 1 baris,
        sedangakan untuk landscape 4 data dalam 1 baris*/
        listPengumuman = new ArrayList<Pengumuman>();
        recyclerView = view.findViewById(R.id.rvPengumuman);
        adapter = new PengumumanAdapter(view.getContext(),listPengumuman,
                new PengumumanAdapter.deleteItemListener() {
                    @Override
                    public void deleteItem(Boolean delete) {
                        if(delete)
                            loadDaftarPengumuman();
                    }
                });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),col);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void getPengumuman() {
        //Tambahkan tampil buku disini
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data pengumuman");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, PengumumanAPI.URL_SELECT,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try{
                    JSONArray jsonArray = response.getJSONArray("data");
                    if(!listPengumuman.isEmpty())
                        listPengumuman.clear();

                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Integer id = jsonObject.optInt("id");
                        String judul = jsonObject.optString("judul");
                        String subjudul = jsonObject.optString("subjudul");
                        String konten = jsonObject.optString("konten");

                        Pengumuman pengumuman = new Pengumuman(id, judul, subjudul, konten);
                        listPengumuman.add(pengumuman);
                    }
                    adapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), response.optString("message"),
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(view.getContext(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}
