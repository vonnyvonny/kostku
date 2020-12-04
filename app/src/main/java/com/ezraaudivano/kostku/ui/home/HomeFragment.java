package com.ezraaudivano.kostku.ui.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ezraaudivano.kostku.API.KostAPI;
import com.ezraaudivano.kostku.AddKostActivity;
import com.ezraaudivano.kostku.R;
import com.ezraaudivano.kostku.adapter.AdapterKost;
import com.ezraaudivano.kostku.databinding.FragmentHomeBinding;
import com.ezraaudivano.kostku.model.Kost;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.Request.Method.GET;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private AdapterKost kAdapter;
    private ArrayList<Kost> listKost = new ArrayList<>();
    private FloatingActionButton floatingActionButton;
    RecyclerView.LayoutManager layoutManager;
    View view;
    FragmentTransaction fragmentTransaction;

    FragmentHomeBinding binding;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String emailFB = user.getEmail();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        view = binding.getRoot();

        floatingActionButton = view.findViewById(R.id.fabAdd);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddKostActivity.class));
            }
        });
        
        getKost();




        kAdapter = new AdapterKost(listKost, view.getContext(), new AdapterKost.deleteItemListener() {
            @Override
            public void deleteItem(Boolean delete) {
                if(delete)
                {
                    getKost();
                }
            }
        });

        layoutManager = new LinearLayoutManager(getActivity());
        binding.kostRv.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        binding.kostRv.setHasFixedSize(true);
        binding.setMyAdapterHome(kAdapter);




        return view;
    }

    private void getKost() {

        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan Data Kost");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, KostAPI.URL_SELECT
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengambil data response json object yang berupa data mahasiswa
                    JSONArray jsonArray = response.getJSONArray("data");

                    if(!listKost.isEmpty())
                        listKost.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //Mengubah data jsonArray tertentu menjadi json Object
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        String id           = jsonObject.optString("id");
                        String nama_kost           = jsonObject.optString("nama_kost");
                        String tipe_kost        = jsonObject.optString("tipe_kost");
                        String alamat_kost        = jsonObject.optString("alamat_kost");
                        String emailPemilik        = jsonObject.optString("emailPemilik");
                        String luas_kost            = jsonObject.optString("luas_kost");
                        String sisa_kamar            = jsonObject.optString("sisa_kamar");
                        String imageUrl            = jsonObject.optString("image");

                        if(emailPemilik.equalsIgnoreCase(emailFB)) {
                            //Membuat objek user
                            Kost kost = new Kost(id, emailFB, nama_kost, tipe_kost, alamat_kost, Integer.parseInt(luas_kost), Integer.parseInt(sisa_kamar), imageUrl);
                            //Menambahkan objek user tadi ke list user
                            listKost.add(kost);
                        }
                    }
                    kAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), response.optString("message"),
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(view.getContext(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }


}