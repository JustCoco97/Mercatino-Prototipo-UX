package com.manuel.newmercatino;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class CardFragment extends Fragment {

    public CardFragment() {
        // Costruttore vuoto necessario
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Carichiamo il disegno XML (Assicurati che il nome sia fragment_card o fragment_card2)
        View view = inflater.inflate(R.layout.fragment_card, container, false);

        // Colleghiamo gli elementi del design al codice
        ImageView barcodeImage = view.findViewById(R.id.barcode_image);
        TextView barcodeText = view.findViewById(R.id.barcode_number);

        // Il tuo numero di carta
        String mioNumero = "0000827771";
        barcodeText.setText(mioNumero);

        // Generiamo il codice a barre vero
        try {
            Bitmap bitmap = generateBarcode(mioNumero);
            barcodeImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    // Il "motore" che disegna il codice a barre
    private Bitmap generateBarcode(String text) throws Exception {
        int width = 600;
        int height = 300;
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, width, height);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Se il bit è vero colora di nero (0xFF000000), altrimenti bianco (0xFFFFFFFF)
                bitmap.setPixel(i, j, bitMatrix.get(i, j) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return bitmap;
    }
}