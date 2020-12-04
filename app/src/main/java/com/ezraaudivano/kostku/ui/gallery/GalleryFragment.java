package com.ezraaudivano.kostku.ui.gallery;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.ezraaudivano.kostku.R;
import com.ezraaudivano.kostku.adapter.AnggotaAdapter;

import com.ezraaudivano.kostku.databinding.FragmentGalleryBinding;
import com.ezraaudivano.kostku.databinding.ItemAnggotaBinding;
import com.ezraaudivano.kostku.geolocation.geoActivity;
import com.ezraaudivano.kostku.model.Anggota;
import com.ezraaudivano.kostku.model.DaftarAnggota;
import com.ezraaudivano.kostku.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    User users = new User();
    private MaterialButton btnNav;
//    SwitchMaterial btnClick;


    private GalleryViewModel galleryViewModel;
    FragmentGalleryBinding binding;
    private ArrayList<Anggota> daftarAnggota;
    private AnggotaAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
//        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_gallery, container, false);
        View view = binding.getRoot();
        func();
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        btnNav = view.findViewById(R.id.btnNav);

        btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), geoActivity.class));

            }
        });




        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        binding=null;
    }

    public void func(){
        daftarAnggota = new DaftarAnggota().ANGGOTA;
        adapter = new AnggotaAdapter(getContext(),daftarAnggota);
        binding.setMyAdapter(adapter);
    }
}