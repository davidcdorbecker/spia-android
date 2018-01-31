package com.fiuady.android.fluidproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import static com.fiuady.android.fluidproject.AdjustFragment.vtempfplant1;
import static com.fiuady.android.fluidproject.AdjustFragment.vtempfplant2;

public class numberpickerP extends Activity {
    public void modifyP(int planta, int nplant) {
        data data = new data(getApplicationContext());
        data.modifyP(planta, nplant);
    }

    NumberPicker numberPicker;
    ImageButton btnaccept;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.numberpicker);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        final int planta = getIntent().getExtras().getInt("planta");
        final int nplant = getIntent().getExtras().getInt("nplant");

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.4), (int)(height*.5));

        numberPicker = (NumberPicker) findViewById(R.id.numberPickerP);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(100);
        numberPicker.setValue(planta);

        btnaccept = (ImageButton) findViewById(R.id.btnSave);
        btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modifyP(numberPicker.getValue(), nplant);

                if (nplant == 1) {
                    vtempfplant1 = true;
                }

                if (nplant == 2) {
                    vtempfplant2 = true;
                }

                finish();
            }
        });
    }
}
