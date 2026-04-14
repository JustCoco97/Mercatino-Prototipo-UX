package com.manuel.newmercatino;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private TextView toolbarTitle;
    private static final int PICK_IMAGE_REQUEST = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Richiesta permessi GPS all'avvio
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbarTitle = findViewById(R.id.toolbar_title);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // --- CARICAMENTO FOTO PROFILO SALVATA ---
        SharedPreferences prefs = getSharedPreferences("DatiUtente", MODE_PRIVATE);
        String fotoSalvata = prefs.getString("foto_profilo", null);
        if (fotoSalvata != null) {
            try {
                Uri uriFoto = Uri.parse(fotoSalvata);
                View headerView = navigationView.getHeaderView(0);
                ImageView img = headerView.findViewById(R.id.img_profilo);
                if (img != null) {
                    img.setPadding(0, 0, 0, 0);
                    img.setImageURI(uriFoto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Gestione Click sul cerchio della Foto Profilo per cambiarla
        View headerView = navigationView.getHeaderView(0);
        if (headerView != null) {
            View fotoContainer = headerView.findViewById(R.id.profile_image_container);
            if (fotoContainer != null) {
                fotoContainer.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                });
            }
        }

        // Icona Menu (le 3 lineette) per aprire il Drawer
        View menuIcon = findViewById(R.id.menu_icon);
        if (menuIcon != null) {
            menuIcon.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        }

        // --- GESTIONE CLIC MENU LATERALE (DRAWER) ---
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home || id == R.id.nav_prodotti) {
                switchFragment(new HomeFragment(), "Prodotti");
            } else if (id == R.id.nav_negozi) {
                switchFragment(new NegoziFragment(), "Negozi");
            } else if (id == R.id.nav_card) {
                switchFragment(new CardFragment(), "La tua tessera");
            } else if (id == R.id.nav_rimborsi) {
                switchFragment(new HomeFragment(), "I tuoi rimborsi");
            } else if (id == R.id.nav_news) {
                switchFragment(new HomeFragment(), "News");
            } else if (id == R.id.nav_eco) { // CORRETTO: Ora combacia con l'XML
                switchFragment(new HomeFragment(), "Impatto Ambientale");
            } else if (id == R.id.nav_logout) {
                // LOGICA LOGOUT
                Toast.makeText(this, "Logout effettuato", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // --- GESTIONE BOTTOM NAVIGATION BAR ---
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                switchFragment(new HomeFragment(), "Home");
            } else if (id == R.id.nav_card) {
                switchFragment(new CardFragment(), "La tua tessera");
            }
            return true;
        });

        // Schermata iniziale all'avvio
        if (savedInstanceState == null) {
            switchFragment(new HomeFragment(), "Home");
        }
    }

    // Metodo per cambiare schermata e titolo della barra in alto
    private void switchFragment(Fragment fragment, String title) {
        if (fragment != null) {
            toolbarTitle.setText(title);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    // Gestione del risultato quando scegli la foto dalla galleria
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            // Chiedo il permesso permanente per leggere la foto anche dopo il riavvio del telefono
            getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

            // Salvo l'URI della foto nelle impostazioni dell'app
            SharedPreferences.Editor editor = getSharedPreferences("DatiUtente", MODE_PRIVATE).edit();
            editor.putString("foto_profilo", imageUri.toString());
            editor.apply();

            // Aggiorno l'immagine nel menu laterale subito
            NavigationView navigationView = findViewById(R.id.navigation_view);
            View hView = navigationView.getHeaderView(0);
            ImageView imgProfilo = hView.findViewById(R.id.img_profilo);
            if (imgProfilo != null) {
                imgProfilo.setPadding(0, 0, 0, 0);
                imgProfilo.setImageURI(imageUri);
            }
        }
    }

    // Gestione del tasto "Indietro" del telefono
    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}