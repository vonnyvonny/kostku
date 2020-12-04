package com.ezraaudivano.kostku.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ezraaudivano.kostku.EditProfile;
import com.ezraaudivano.kostku.LoginActivity;
import com.ezraaudivano.kostku.MenuActivity;
import com.ezraaudivano.kostku.R;
import com.ezraaudivano.kostku.geolocation.geoActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutFragment extends Fragment {

    private LogoutshowViewModel logoutshowViewModel;
    Button edit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        logoutshowViewModel =
                ViewModelProviders.of(this).get(LogoutshowViewModel.class);
        View root = null;
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();




        return root;
    }
}