package com.example.timemap.ui.coffee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.fragment.app.Fragment;

import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemap.R;
import com.example.timemap.databinding.FragmentCoffeeBinding;

/**
 * (View) View for donations
 **/
public class CoffeeFragment extends Fragment {
    FragmentCoffeeBinding binding;
    private WebView webView;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CoffeeViewModel coffeeViewModel = new ViewModelProvider(this).get(CoffeeViewModel.class);
        binding = FragmentCoffeeBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Obtener referencia al WebView desde el diseño
        webView = rootView.findViewById(R.id.webView);

        // Configurar el botón para abrir el HTML interno
        Button btnOpenHtml = rootView.findViewById(R.id.btnOpenHtml);

        btnOpenHtml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOpenHtmlButtonClick();
                btnOpenHtml.setVisibility(View.GONE);
            }
        });

        return rootView;
    }

    public void onOpenHtmlButtonClick() {

        String fileName = "rickRoll.html";

        // Construir la URL completa del archivo HTML
        String fileUrl = "file:///android_asset/" + fileName;
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        int backgroundColor = getResources().getColor(R.color.beige);
        webView.setBackgroundColor(backgroundColor);
        webView.loadUrl(fileUrl);
        // Hacer visible el WebView y ocultar el resto de los elementos si es necesario
        webView.setVisibility(View.VISIBLE);




    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
