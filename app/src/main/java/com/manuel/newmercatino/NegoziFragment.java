package com.manuel.newmercatino;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NegoziFragment extends Fragment {

    public NegoziFragment() {
        // Vuoto, serve così
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Carichiamo il file XML che abbiamo creato prima
        return inflater.inflate(R.layout.fragment_negozi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Qui aggiungeremo i negozi nella lista. Per ora facciamo una prova:
        creaRigaNegozio(view, "Mercatino Milano Centro");
        creaRigaNegozio(view, "Mercatino Roma Nord");
        creaRigaNegozio(view, "Mercatino Napoli");
    }

    // Funzione veloce per aggiungere un negozio alla lista senza troppi file
    private void creaRigaNegozio(View rootView, String nome) {
        LinearLayout container = rootView.findViewById(R.id.lista_negozi_container);

        TextView tv = new TextView(getContext());
        tv.setText(nome);
        tv.setTextSize(18);
        tv.setPadding(20, 40, 20, 40);
        tv.setBackgroundResource(android.R.drawable.list_selector_background); // Effetto clic

        tv.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Hai scelto: " + nome, Toast.LENGTH_SHORT).show();
            // Qui poi metteremo il codice per tornare indietro e cambiare il nome!
        });

        container.addView(tv);
    }
}