package com.fiuady.android.fluidproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.fiuady.android.fluidproject.AdjustFragment.INFO;
import static com.fiuady.android.fluidproject.AdjustFragment.manualMode;
import static com.fiuady.android.fluidproject.MainActivity.tempData;

public class InformationFragment extends Fragment {

    ImageButton btnRefresh;
    TextView txtLastDate;
    TextView txtNextDate;
    static TextView tempText;
    static TextView plant1;
    static TextView plant2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_information, container, false);

        //lastDateConfigure dateConfigure = data.getLastConfigureData();
        //String ndate = dateConfigure.getStringDay() + "/" + dateConfigure.getStringMonth() + "/" + dateConfigure.getStringYear() + " a las " + dateConfigure.getStringHour() + ":" + dateConfigure.getStringMinute() + " horas";

       // lastPumpOn pumpOn = data.getLastPumpOn();
        //String ldate = pumpOn.getStringDay() + "/" + pumpOn.getStringMonth() + "/" + pumpOn.getStringYear() + " a las " + pumpOn.getStringHour() + ":" + pumpOn.getStringMinute() + " horas";
        txtNextDate = (TextView) view.findViewById(R.id.txtNextDate);
      //  txtNextDate.setText(ndate);
        txtLastDate = (TextView) view.findViewById(R.id.txtLastdate) ;
       // txtLastDate.setText(ldate);
        tempText = (TextView) view.findViewById(R.id.tempText) ;
        plant1 = (TextView) view.findViewById(R.id.plant1) ;
        plant2 = (TextView) view.findViewById(R.id.plant2) ;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, INFO, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    txtLastDate.setText(formatDate(response.getString("last_irrigation")));
                    txtNextDate.setText(formatDate(response.getString("next_irrigation")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(getContext()).addToRequestque(jsonObjectRequest);

        btnRefresh = (ImageButton) view.findViewById(R.id.btnRefresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temporal = tempData;
                int[] index;
                index = new int[2];
                index[0] = temporal.indexOf("/");
                index[1] = temporal.indexOf("|");
                String label = temporal.substring(0, index[0]);
                tempText.setText(label);
                label = temporal.substring(index[0]+1, index[1]);
                plant1.setText(label);
                label = temporal.substring(index[1]+1);
                plant2.setText(label);

              //  lastDateConfigure dateConfigure = data.getLastConfigureData();
              //  String ndate = dateConfigure.getStringDay() + "/" + dateConfigure.getStringMonth() + "/" + dateConfigure.getStringYear() + " a las " + dateConfigure.getStringHour() + ":" + dateConfigure.getStringMinute() + " horas";

               // lastPumpOn pumpOn = data.getLastPumpOn();
               // String ldate = pumpOn.getStringDay() + "/" + pumpOn.getStringMonth() + "/" + pumpOn.getStringYear() + " a las " + pumpOn.getStringHour() + ":" + pumpOn.getStringMinute() + " horas";

                if (manualMode) {
              //      ndate = "No asignado";
                }

               // txtLastDate.setText(ldate);
              //  txtNextDate.setText(ndate);
            }
        });
        return view;
    }

    private String formatDate(String date) {
        DateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String r = "(...)";
        try {
            Date fDate = pattern.parse(date);
            r =  pattern.format(fDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return r;
    }

    private  void updateDateConfigure () {
        Calendar actualDate = GregorianCalendar.getInstance();

       // lastDateConfigure dateConfigure = data.getLastConfigureData();
        Calendar lastDate = GregorianCalendar.getInstance();
       // lastDate.set(dateConfigure.getYear(),dateConfigure.getMonth()-1,dateConfigure.getDay(), dateConfigure.getHour(), dateConfigure.getMinute());

        if (actualDate.compareTo(lastDate) > 0 ) {

            long difference = actualDate.getTimeInMillis() - lastDate.getTimeInMillis();
            //double dif = Math.floor((double)difference);
           // int d = (int)dif;
            int d = (int) (Math.floor((double)difference/(1000*60*60*24)));
            if (d == 0) {
            //    d = dateConfigure.getEach();
            }
            else if (d>0 &&d <= 3) {
          //      d = d - dateConfigure.getEach();
            }
            else if (d > 3) {
          //      d = d%dateConfigure.getEach();
            }
            actualDate.add(Calendar.DAY_OF_YEAR, d);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String tempDate = simpleDateFormat.format(actualDate.getTime());
            int year = Integer.valueOf(tempDate.substring(0, 4));
            int month = Integer.valueOf(tempDate.substring(5, 7));
            int day = Integer.valueOf(tempDate.substring(8, 10));

        //    dateConfigure.setYear(year);
          //  dateConfigure.setMonth(month);
          //  dateConfigure.setDay(day);

          //  data.updateLastDateConfigure(dateConfigure.getDay(), dateConfigure.getMonth(), dateConfigure.getYear(), dateConfigure.getHour(), dateConfigure.getMinute(), dateConfigure.getEach());
        }
    }
}




