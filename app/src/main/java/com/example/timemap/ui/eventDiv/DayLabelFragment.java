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

public class DayLabelFragment extends Fragment {
    private String dayName;
    private FragmentActivity context;

    public DayLabelFragment(FragmentActivity context, String dayName) {
        this.context = context;
        this.dayName = "✧ " + dayName + " ✧";
    }

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