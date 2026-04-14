package com.manuel.newmercatino;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Questo comando dice al Java di andare a prendere il file XML dei layout
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Cerchiamo il pulsante nel layout XML
        Button btnCambia = view.findViewById(R.id.btn_cambia_negozio);

        if (btnCambia != null) {
            btnCambia.setOnClickListener(v -> {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new NegoziFragment())
                        .addToBackStack(null)
                        .commit();
            });
        }
    }
}