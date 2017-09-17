package com.takisoft.colorpicker.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.takisoft.colorpicker.ColorPickerDialog;
import com.takisoft.colorpicker.ColorPickerSwatch;

public class MainActivity extends AppCompatActivity implements ColorPickerSwatch.OnColorSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ColorPickerDialog.Params params = new ColorPickerDialog.Params.Builder(getApplicationContext())
                .setSize(ColorPickerDialog.SIZE_SMALL)
                .setColumns(2)
                .setSortColors(false)
                .setSelectedColor(getResources().getColor(R.color.deep_orange))
                .setColors(getResources().getIntArray(R.array.color_picker_default_colors))
                .build();

        findViewById(R.id.color_picker_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorPickerDialog dialog = new ColorPickerDialog(MainActivity.this, MainActivity.this, params);
                dialog.show();
            }
        });
    }

    @Override
    public void onColorSelected(int color) {
        Toast.makeText(this, "0x" + Integer.toHexString(color).toUpperCase(), Toast.LENGTH_SHORT).show();
    }
}
