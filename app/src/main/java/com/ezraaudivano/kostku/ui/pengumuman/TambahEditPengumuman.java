package com.ezraaudivano.kostku.ui.pengumuman;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ezraaudivano.kostku.R;
import com.ezraaudivano.kostku.API.PengumumanAPI;
import com.ezraaudivano.kostku.model.Pengumuman;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;


public class TambahEditPengumuman extends Fragment {

    private TextInputEditText txtJudul, txtSubjudul, txtKonten;
    private Button btnSimpan, btnBatal;
    private String status;
    private int idPengumuman;
    private Pengumuman pengumuman;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tambah_edit_pengumuman, container, false);
        init();
        setAttribut();

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void init(){
        pengumuman   = (Pengumuman) getArguments().getSerializable("pengumuman");
        txtJudul       = view.findViewById(R.id.etJudul);
        txtSubjudul    = view.findViewById(R.id.etSubjudul);
        txtKonten      = view.findViewById(R.id.etKonten);
        btnSimpan      = view.findViewById(R.id.btnSimpan);
        btnBatal       = view.findViewById(R.id.btnBatal);

        status = getArguments().getString("status");
        if(status.equals("edit"))
        {
            idPengumuman = pengumuman.getId();
            txtJudul.setText(pengumuman.getJudul());
            txtSubjudul.setText(pengumuman.getSubjudul());
            txtKonten.setText(pengumuman.getKonten());
        }
    }

    private void setAttribut() {
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judulPengumuman = txtJudul.getText().toString();
                String subJudulPengumuman = txtSubjudul.getText().toString();
                String kontenPengumuman = txtKonten.getText().toString();

                if (judulPengumuman.isEmpty() || subJudulPengumuman.isEmpty() || kontenPengumuman.isEmpty())
                    Toast.makeText(getContext(), "Data Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                else {
//                    pengumuman = new Pengumuman(judulPengumuman, subJudulPengumuman, kontenPengumuman);
                    if (status.equals("tambah")) {
                        tambahPengumuman(judulPengumuman, subJudulPengumuman, kontenPengumuman);
                    } else {
                        editPengumuman(judulPengumuman, subJudulPengumuman, kontenPengumuman);
                    }
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new PengumumanFragment());
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            fragmentTransaction.setReorderingAllowed(false);
        }
        fragmentTransaction.replace(R.id.frame_tambah_edit_pengumuman, fragment)
                .detach(this)
                .attach(this)
                .commit();
    }

    public void tambahPengumuman(final String judul, final String subjudul, final String konten){
        //Tambahkan tambah pengumuman disini
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menambahkan data pengumuman");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(POST, PengumumanAPI.URL_ADD,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{
                    JSONObject obj = new JSONObject(response);

                        loadFragment(new PengumumanFragment());

                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("judul", judul);
                params.put("subjudul", subjudul);
                params.put("konten", konten);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void editPengumuman(final String judul, final String subjudul, final String konten) {
        //Tambahkan edit pengumuman disini
        RequestQueue queue = Volley.newRequestQueue(getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Mengupdate data pengumuman");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(PUT, PengumumanAPI.URL_UPDATE+idPengumuman,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{
                    JSONObject obj = new JSONObject(response);

                    loadFragment(new PengumumanFragment());

                    Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("judul", judul);
                params.put("subjudul", subjudul);
                params.put("konten", konten);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}