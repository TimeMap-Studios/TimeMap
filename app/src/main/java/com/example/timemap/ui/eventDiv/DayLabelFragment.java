package com.example.timemap.ui.eventDiv;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.timemap.R;

/**
 * A fragment representing a label for a specific day. Displays the day name with decorative symbols.
 */
public class DayLabelFragment extends Fragment {
    // Variables
    private String dayName;
    private FragmentActivity context;

    /**
     * Constructor:
     * Initializes the DayLabelFragment with the specified context and day name.
     *
     * @param context The FragmentActivity context associated with the fragment.
     * @param dayName The name of the day to be displayed.
     */
    public DayLabelFragment(FragmentActivity context, String dayName) {
        this.context = context;
        this.dayName = "✧ " + dayName + " ✧";
    }

    /**
     * onCreateView:
     * Inflates the fragment layout, creating a TextView for the day label with specific styling.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views.
     * @param container          The parent view that this fragment's UI should be attached to.
     * @param savedInstanceState Bundle containing the saved state of the fragment.
     * @return The TextView representing the day label.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create a TextView for the day label
        TextView dayLabel = new TextView(context);
        dayLabel.setText(dayName);
        dayLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        dayLabel.setTextColor(ContextCompat.getColor(context, R.color.brown));
        dayLabel.setGravity(Gravity.CENTER);
        //dayLabel.setPaintFlags(dayLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        dayLabel.setAlpha(0.5f);
        return dayLabel;
    }
}