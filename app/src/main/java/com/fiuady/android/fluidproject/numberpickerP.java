package com.fiuady.android.fluidproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static com.fiuady.android.fluidproject.AdjustFragment.PLANT1_INFO;
import static com.fiuady.android.fluidproject.AdjustFragment.PLANT2_INFO;

import static com.fiuady.android.fluidproject.AdjustFragment.hum1;
import static com.fiuady.android.fluidproject.AdjustFragment.hum2;

public class numberpickerP extends Activity {

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

                finish();
            }
        });
    }

    private void modifyP(int value, int plant) {

        String key = "max_humidity";

        if (plant == 1) {

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(key, String.valueOf(value));
                jsonObject.put("source", "app");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, PLANT1_INFO, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getApplicationContext(), "La humedad permisible se ha modificado", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
                }
            });
            MySingleton.getInstance(getApplicationContext()).addToRequestque(jsonObjectRequest);
            hum1 = value;
        }

        if (plant == 2) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(key, String.valueOf(value));
                jsonObject.put("source", "app");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, PLANT2_INFO, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getApplicationContext(), "La humedad permisible se ha modificado", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
                }
            });
            MySingleton.getInstance(getApplicationContext()).addToRequestque(jsonObjectRequest);
            hum2 = value;
        }
    }
}
