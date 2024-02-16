package com.example.timemap.ui.coffee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.example.timemap.R;
import com.example.timemap.databinding.FragmentCoffeeBinding;

/**
 * (View) View for displaying a donation page or similar content.
 **/
public class CoffeeFragment extends Fragment {
    FragmentCoffeeBinding binding;
    private WebView webView;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialize the ViewModel for this fragment
        CoffeeViewModel coffeeViewModel = new ViewModelProvider(this).get(CoffeeViewModel.class);
        // Inflate the fragment layout
        binding = FragmentCoffeeBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        // Get a reference to the WebView from the layout
        webView = rootView.findViewById(R.id.webView);

        // Set up the button to open internal HTML
        Button btnOpenHtml = rootView.findViewById(R.id.btnOpenHtml);

        // Define the button click behavior
        btnOpenHtml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call the method to handle opening the HTML
                onOpenHtmlButtonClick();
                // Hide the button after it's clicked
                btnOpenHtml.setVisibility(View.GONE);
            }
        });

        return rootView;
    }

    public void onOpenHtmlButtonClick() {
        // HTML file name to be displayed
        String fileName = "rickRoll.html";

        // Construct the full URL to the HTML file
        String fileUrl = "file:///android_asset/" + fileName;
        // Get and configure the webview settings
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript

        // Set the cache mode to not use cache
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // Set the background color of the WebView
        int backgroundColor = getResources().getColor(R.color.beige);
        webView.setBackgroundColor(backgroundColor);
        // Load the specified URL
        webView.loadUrl(fileUrl);
        // Make the WebView visible and hide other elements if necessary
        webView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Nullify the binding when the view is destroyed to prevent memory leaks
        binding = null;
    }
}
