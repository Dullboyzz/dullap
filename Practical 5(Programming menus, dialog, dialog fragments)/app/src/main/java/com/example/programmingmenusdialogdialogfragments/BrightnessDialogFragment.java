package com.example.programmingmenusdialogdialogfragments;

import android.app.Dialog;
import android.content.ContentResolver;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class BrightnessDialogFragment extends DialogFragment {

    private SeekBar brightnessSeekBar;
    private TextView brightnessPercentage;
    private ContentResolver contentResolver;
    private int currentBrightness; // Stores the original brightness level

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Inflate Custom Layout
        View view = LayoutInflater.from(getContext()).inflate(R.layout.brightness_dialog, null);
        dialog.setContentView(view);

        // Initialize UI Elements
        brightnessSeekBar = view.findViewById(R.id.seekBrightness);
        brightnessPercentage = view.findViewById(R.id.txtBrightnessPercentage);
        Button btnOk = view.findViewById(R.id.btnOk);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        contentResolver = requireActivity().getContentResolver();

        // Get Current Brightness Level
        try {
            currentBrightness = Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
            brightnessSeekBar.setProgress(currentBrightness);
            updateBrightnessPercentage(currentBrightness);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            currentBrightness = 125; // Default brightness level
        }

        // SeekBar Listener (Updates Preview & Percentage)
        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateScreenBrightness(progress);
                updateBrightnessPercentage(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // OK Button (Saves Brightness)
        btnOk.setOnClickListener(v -> {
            int newBrightness = brightnessSeekBar.getProgress();
            if (Settings.System.canWrite(requireContext())) {
                Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, newBrightness);
                Toast.makeText(requireContext(), "Brightness Updated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Permission Required!", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });

        // Cancel Button (Restores Original Brightness)
        btnCancel.setOnClickListener(v -> {
            updateScreenBrightness(currentBrightness); // Restore previous brightness
            dismiss();
        });

        return dialog;
    }

    private void updateScreenBrightness(int brightness) {
        Window window = requireActivity().getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.screenBrightness = brightness / 255f;
        window.setAttributes(layoutParams);
    }

    private void updateBrightnessPercentage(int brightness) {
        int percentage = (int) ((brightness / 255.0) * 100);
        brightnessPercentage.setText(percentage + "%");
    }
}
