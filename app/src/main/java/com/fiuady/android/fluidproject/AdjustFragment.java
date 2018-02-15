package com.fiuady.android.fluidproject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdjustFragment extends Fragment {

    Switch sw1;
    Switch sw2;
    static  Spinner spinner;
    static boolean manualMode;
    TextView humidity1;
    TextView humidity2;
    ImageButton btnaccept;
    ImageButton configureDate;
    static  TextView newDate;
    static String label;
    static String labelDate;

    static int yy;
    static int mm;
    static int dd;
    static int hr;
    static int min;
    static int each;
    static String formatdate;
    static int hum1;
    static int hum2;
    static boolean dateSelected;
    static boolean vtempfplant1;
    static boolean vtempfplant2;

    static String DEVICE_INFO = "http://spia-services.tk/api/devices/1";
    static String PLANT1_INFO = "http://spia-services.tk/api/plants/1";
    static String PLANT2_INFO = "http://spia-services.tk/api/plants/2";
    static String PUMP_ON = "http://spia-services.tk/api/devices/1/pump-on";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_adjust, container, false);
        dateSelected = false;
        List <String> list = new ArrayList<>();
        list.add("Diario");
        list.add("Cada dos días");
        list.add("Cada tres días");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner)view.findViewById(R.id.spinnerEachDay);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        each = 1;
                        if (dateSelected) {
                            updateLabel();
                        }
                        break;
                    }

                    case 1: {
                        each = 2;
                        if (dateSelected) {
                            updateLabel();
                        }
                        break;
                    }

                    case 2: {
                        each = 3;
                        if (dateSelected) {
                            updateLabel();
                        }
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        newDate = (TextView) view.findViewById(R.id.newDate);
        humidity1 = (TextView) view.findViewById(R.id.Humidity1) ;
        humidity2 = (TextView) view.findViewById(R.id.Humidity2);

        updateData(true);

        btnaccept = (ImageButton)view.findViewById(R.id.btnaccept);
        btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!manualMode) {
                    if (dateSelected) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                        final View view1 = getLayoutInflater(null).inflate(R.layout.pinsecurity, null);

                        builder1.setView(view1);
                        final AlertDialog dialog = builder1.create();

                        final ImageButton btnok = (ImageButton) view1.findViewById(R.id.btnok);
                        final ImageButton btncancel = (ImageButton) view1.findViewById(R.id.btncancel);
                        final EditText password = (EditText) view1.findViewById(R.id.pasword);

                        btnok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (password.getText().toString().equals("1234")) {
                                    each = -1;
                                    switch (spinner.getSelectedItemPosition()) {
                                        case 0: {
                                            each = 1;
                                            break;
                                        }

                                        case 1: {
                                            each = 2;
                                            break;
                                        }

                                        case 2: {
                                            each = 3;
                                            break;
                                        }
                                    }
                                    configurePump(each);
                                    Toast.makeText(getContext(), "Bomba configurada correctamente", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(view1.getContext(), "¡PIN incorrecto!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        });

                        btncancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                        doKeepDialog(dialog);
                    }
                    else {
                        Toast.makeText(getContext(), "Selecciona una fecha para reconfigurar el sistema", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Toast.makeText(getContext(), "El modo manual está activo, cambiarlo a modo automático para reconfigurar el sistema", Toast.LENGTH_LONG).show();
                }
            }
        });

        sw2 = (Switch) view.findViewById(R.id.s2);

        sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pumpOperation();
            }
        });

        sw1 = (Switch)view.findViewById(R.id.s1);

        sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!manualMode) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                    final View view1 = getLayoutInflater(null).inflate(R.layout.pinsecurity, null);

                    builder1.setView(view1);
                    final AlertDialog dialog = builder1.create();

                    final ImageButton btnok = (ImageButton) view1.findViewById(R.id.btnok);
                    final ImageButton btncancel = (ImageButton) view1.findViewById(R.id.btncancel);
                    final EditText password = (EditText) view1.findViewById(R.id.pasword);

                    btnok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (password.getText().toString().equals("1234")) {
                                Toast.makeText(view1.getContext(), "Modo manual activo", Toast.LENGTH_SHORT).show();
                                sw2.setEnabled(true);
                                manualMode = true;
                                sw1.setChecked(true);
                                automaticModeOff();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(view1.getContext(), "¡PIN incorrecto!", Toast.LENGTH_SHORT).show();
                                sw1.setChecked(false);
                                dialog.dismiss();
                            }
                        }
                    });

                    btncancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                    doKeepDialog(dialog);
                }

                else {
                    manualMode = false;
                    sw1.setChecked(false);
                    sw2.setEnabled(false);
                    sw2.setChecked(false);
                    pumpOff();
                    automaticModeOn();
                }
            }
        });
        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!manualMode) {
                    sw1.setChecked(false);
                }
            }
        });

        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               /* if (isChecked) {

                    String temporal = tempData;
                    int[] index;
                    index = new int[2];
                    index[0] = temporal.indexOf("/");
                    index[1] = temporal.indexOf("|");
                    int ip1  = Math.round(Float.valueOf(temporal.substring(index[0] + 1, index[1])));
                    int ip2 = Math.round(Float.valueOf(temporal.substring(index[1] + 1)));

                    if (ip1 >= 25 || ip2 >= 25) {

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                        final View view1 = getLayoutInflater(null).inflate(R.layout.alert_dialog, null);

                        builder1.setView(view1);
                        final AlertDialog dialog = builder1.create();

                        final ImageButton btnok = (ImageButton) view1.findViewById(R.id.btnyes);
                        final ImageButton btncancel = (ImageButton) view1.findViewById(R.id.btnnot);
                        final TextView txtp1 = (TextView) view1.findViewById(R.id.txtp1);
                        final TextView txtp2 = (TextView) view1.findViewById(R.id.txtp2);

                        String label = temporal.substring(index[0] + 1, index[1]) + "%";
                        txtp1.setText(label);
                        label = temporal.substring(index[1] + 1) + "%";
                        txtp2.setText(label);

                        btnok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pumpOn();
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String date = simpleDateFormat.format(calendar.getTime());
                                String year = date.substring(0, 4);
                                String month = date.substring(5, 7);
                                String day = date.substring(8, 10);
                                simpleDateFormat = new SimpleDateFormat("HH:mm a");
                                date = simpleDateFormat.format(calendar.getTime());
                                String hour = date.substring(0, 2);
                                String minute = date.substring(3, 5);

                                updateLastPumpOn(day, month, year, hour, minute);

                                dialog.dismiss();
                            }
                        });

                        btncancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sw2.setChecked(false);
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                        doKeepDialog(dialog);
                    }

                    else {
                        pumpOn();
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String date = simpleDateFormat.format(calendar.getTime());
                        String year = date.substring(0, 4);
                        String month = date.substring(5, 7);
                        String day = date.substring(8, 10);
                        simpleDateFormat = new SimpleDateFormat("HH:mm a");
                        date = simpleDateFormat.format(calendar.getTime());
                        String hour = date.substring(0, 2);
                        String minute = date.substring(3, 5);

                        updateLastPumpOn(day, month, year, hour, minute);
                    }
                }

                else {
                    pumpOff();
                }*/
            }
        });

        configureDate = (ImageButton) view.findViewById(R.id.ConfigureDate);
        configureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        vtempfplant1 = false;
        vtempfplant2 = false;

        //lastHumidity lastHumidity = data.getLastHumidity();
        //final String planta1 = String.valueOf(lastHumidity.getPlant1());
       // final String planta2 = String.valueOf(lastHumidity.getPlant2());

        //humidity1.setText(planta1);
        //humidity2.setText(planta2);

        humidity1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), numberpickerP.class);
                intent.putExtra("planta", Integer.valueOf(humidity1.getText().toString()));
                intent.putExtra("nplant", 1);

                startActivity(intent);

            }
        });

        humidity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), numberpickerP.class);
                intent.putExtra("planta", Integer.valueOf(humidity2.getText().toString()));
                intent.putExtra("nplant", 2);

                startActivity(intent);
            }
        });

        ///////////////////////////////////////////////////////////////////////

        //changePlant1();
        //changePlant2();
        return view;
    }

    private void updateData(final boolean server) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, DEVICE_INFO, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (server) {
                        hum1 = response.getInt("max_humidity_1");
                        hum2 = response.getInt("max_humidity_2");
                        formatdate = response.getString("last_irrigation");
                        each = response.getInt("hours_to_repeat")/24;

                        if (response.getString("automatic_mode").equals("off")) {
                            manualMode = true;
                            sw2.setEnabled(true);
                            sw1.setChecked(true);
                        }
                        else {
                            manualMode = false;
                            sw2.setEnabled(false);
                            sw1.setChecked(false);
                        }

                    }

                    humidity1.setText(String.valueOf(hum1));
                    humidity2.setText(String.valueOf(hum2));
                    spinner.setSelection(each-1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "No se pudo acceder al servidor", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(getContext()).addToRequestque(jsonObjectRequest);
    }

    @Override
    public void onResume() {
        super.onResume();

       updateData(false);
    }

    public static void updateLabel() {
        label = labelDate + each + " días";
        newDate.setText(label);
    }

    private  void doKeepDialog(Dialog dialog){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
    }

    private void pumpOperation()
    {
        //Prender la bomba

        if (sw2.isChecked()) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, PUMP_ON, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("success").equals("Pump on sent to Arduino")) {
                            Toast.makeText(getContext(), "La bomba esta activa", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
            MySingleton.getInstance(getContext()).addToRequestque(jsonObjectRequest);
        }

        else {
            Toast.makeText(getContext(), "La bomba se ha desactivado", Toast.LENGTH_SHORT).show();
        }
    }


    private void pumpOff()
    {
        /*if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("0".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }*/
    }

    private void automaticModeOff () {
        /*if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("AMOFF".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }*/
    }

    private void automaticModeOn () {
        /*if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("AMON".getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }*/
    }

    private void configurePump (int each) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("next_irrigation", formatdate);
            jsonObject.put("hours_to_repeat", 24*each);
            jsonObject.put("automatic_mode", "on");
            jsonObject.put("source", "app");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Configuración del dispositivo
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, DEVICE_INFO, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), "La bomba se configuró correctamente", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(getContext()).addToRequestque(jsonObjectRequest);
    }

    public void updateLastPumpOn (String day, String month, String year, String hour, String minute) {
       // data.updateLastPumpOn(day, month, year, hour, minute);
    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }
        public void populateSetDate(int year, int month, int day) {
            yy = year;
            mm = month;
            dd = day;

            DialogFragment newFragment = new SelectTimeFragment();
            newFragment.show(getFragmentManager(), "DatePicker");
        }

    }

    public static class SelectTimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            populateSetTime(hourOfDay, minute);
        }
        public void populateSetTime(int hour, int minute) {
            hr = hour;
            min = minute;

            String tday;
            String tmonth;
            String thour;
            String tminute;

            if(dd < 10) {
                tday = "0" + String.valueOf(dd);
            }
            else {
                tday = String.valueOf(dd);
            }

            if(mm < 10) {
                tmonth = "0" + String.valueOf(mm);
            }
            else {
                tmonth = String.valueOf(mm);
            }

            if(hr < 10) {
                thour = "0" + String.valueOf(hr);
            }
            else {
                thour = String.valueOf(hr);
            }

            if(min < 10) {
                tminute = "0" + String.valueOf(min);
            }
            else {
                tminute = String.valueOf(min);
            }

            labelDate = tday+"/"+tmonth+"/"+yy+" a las "+thour+":"+tminute+" horas cada ";
            dateSelected = true;
            formatdate = yy + "-" + tmonth + "-" + tday + " " + thour + ":" + tminute + ":" + "00";
            updateLabel();
        }
    }
}




