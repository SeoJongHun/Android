package com.example.filebase_viewcloset.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.filebase_viewcloset.Ootd.Style_Save_Activity;
import com.example.filebase_viewcloset.R;
import com.example.filebase_viewcloset.clothing.TemperatureClothingActivity;
import com.example.filebase_viewcloset.clothing.TemperatureClothingActivity2;
import com.example.filebase_viewcloset.clothing.TemperatureClothingActivity3;
import com.example.filebase_viewcloset.clothing.TemperatureClothingActivity4;
import com.example.filebase_viewcloset.clothing.TemperatureClothingActivity5;
import com.example.filebase_viewcloset.clothing.TemperatureClothingActivity6;
import com.example.filebase_viewcloset.clothing.TemperatureClothingActivity7;
import com.example.filebase_viewcloset.clothing.TemperatureClothingActivity8;
import com.example.filebase_viewcloset.data.DailyItem;
import com.example.filebase_viewcloset.data.HourlyItem;
import com.example.filebase_viewcloset.ui.main.adapter.HourlyItemAdapter;
import com.example.filebase_viewcloset.util.PreferenceManager;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainWeatherFragment extends Fragment {
    private int tabPosition;

    private View rootView;

    private DrawerLayout drawerLayout;
    private ImageButton menuButton;
    private NavigationView navigationView;
    private TextView dateNow;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageButton searchButton;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton delButton;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private ImageView weathericon;

    private TextView region;                //??????
    private TextView current_temp;          //????????????
    private TextView current_location;      //?????? ??????
    private TextView current_bodily_temp;   //?????? ??????
    private TextView current_rain;          //?????????
    private TextView current_desc;          //??????
    private TextView current_humidity;
    private String temperature;
    private String bodily_temperature;

    private String Daily_image;
    private ImageButton search_button;
    private String dailyLow;
    private String dailyHigh;
    private String temp_f;

    private String rain_1h;
    private String rain_3h;
    private String address_text;
    private String level1;
    private String level2;
    private ArrayList<HourlyItem> hourlyItemList = new ArrayList<>();
    private ArrayList<DailyItem> dailyItemList = new ArrayList<>();
    private String temp_extra;

    private ProgressDialog progressDialog;

    private final Class [] clothingClasses = {
            TemperatureClothingActivity.class, TemperatureClothingActivity2.class, TemperatureClothingActivity3.class,
            TemperatureClothingActivity4.class, TemperatureClothingActivity5.class, TemperatureClothingActivity6.class,
            TemperatureClothingActivity7.class, TemperatureClothingActivity8.class};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_weather, container, false);

        initView(inflater, container, savedInstanceState);

        displayWeather(rootView.getContext());

        setBackgroundByTime();



        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(PreferenceManager.getBoolean(getContext(),"IS_ADDRESS_CHANGED")==true){
            PreferenceManager.setBoolean(getContext(),"IS_ADDRESS_CHANGED",false);
            displayWeather(getContext());
        }
    }

    private void initView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        region=(TextView)rootView.findViewById(R.id.region_text);
        current_temp = (TextView)rootView.findViewById(R.id.temp_now);
        current_location = (TextView)rootView.findViewById(R.id.region_text);
        current_bodily_temp=(TextView)rootView.findViewById(R.id.bodily_temp);
        current_rain=(TextView)rootView.findViewById(R.id.precipitation_text);
        current_desc=(TextView)rootView.findViewById(R.id.weather_description);
        weathericon = (ImageView)rootView.findViewById(R.id.image_weather);
        current_humidity =(TextView)rootView.findViewById(R.id.humidity);

        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout);

        /* pull to refresh */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /* ???????????? ??? ????????? ?????? */
                setBackgroundByTime();
                displayWeather(rootView.getContext());

                /* ???????????? ?????? */
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        /* drawer layout */
        drawerLayout = (DrawerLayout)rootView.findViewById(R.id.main_drawer_layout);
        menuButton = (ImageButton)rootView.findViewById(R.id.main_menu_btn);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
                else {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
            }
        });

        navigationView = (NavigationView)rootView.findViewById(R.id.drawer_nav_view);
        navigationView.addHeaderView((View)inflater.inflate(R.layout.drawer_header,container,false));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int temperature = Integer.parseInt(temp_extra);

                int index=0;

                if(temperature<=4) index = 0;
                else if(temperature<=9) index = 1;
                else if(temperature<=13) index = 2;
                else if(temperature<=17) index = 3;
                else if(temperature<=20) index = 4;
                else if(temperature<=23) index = 5;
                else if(temperature<=27) index = 6;
                else index = 7;

                switch (item.getItemId()){
                    case R.id.nav_clothing:
                        startActivity(new Intent(rootView.getContext(), clothingClasses[index])
                                .putExtra("temperature",temp_extra)
                                .putExtra("city",PreferenceManager.getString(rootView.getContext(),"CITY")));
                        Log.e("SEULGI INTENT",PreferenceManager.getString(rootView.getContext(),"CITY"));
                        break;
                    case R.id.nav_c_com:
                        startActivity(new Intent(rootView.getContext(), View_Page_Main.class)
                                .putExtra("city",PreferenceManager.getString(rootView.getContext(),"CITY")));
                        Log.e("SEULGI INTENT",PreferenceManager.getString(rootView.getContext(),"CITY"));
                        break;
                    case R.id.nav_style:
                        startActivity(new Intent(rootView.getContext(), com.example.filebase_viewcloset.Ootd.stylistActivity.class));
                        break;
                    case R.id.nav_style_save:
                        startActivity(new Intent(rootView.getContext(), Style_Save_Activity.class));
                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawer(Gravity.RIGHT);
                return false;
            }
        });
    }

    private void setBackgroundByTime() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String hourText = sdfHour.format(date);

        String nowText = sdfNow.format(date);

        int time = Integer.parseInt(hourText);
//        if(time >= 0 && time < 6){
//            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_night_background);
//        }
//        else if(time >= 6 && time < 15){
//            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_afternoon_background);
//        }
//        else if(time >= 15 && time < 20){
//            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_sunset_background);
//        }
//        else if(time >= 20 && time < 24){
//            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_night_background);
//        }
    }

    public void find_weather(float latitude, float longitude){
        String url = "http://api.openweathermap.org/data/2.5/weather?appid=44cc7ff859937e12de419868524f8704&units=metric&id=1841811&lang=kr";
        url += "&lat="+String.valueOf(latitude)+"&lon="+String.valueOf(longitude);
        Log.e("SEULGI WEATHER API URL", url);

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray weather_object = response.getJSONArray("weather");
                    JSONObject sys_object = response.getJSONObject("sys");

                    //??????
                    temperature = main_object.getString("temp");
                    temperature = String.valueOf(Math.round(Double.valueOf(temperature)));
                    temp_extra = temperature;
                    current_temp.setText(temperature+getString(R.string.temperature_unit));

                    //????????????
                    bodily_temperature = main_object.getString("feels_like");
                    bodily_temperature = String.valueOf(Math.round(Double.valueOf(bodily_temperature)));
                    current_bodily_temp.setText(bodily_temperature+getString(R.string.temperature_unit));

                    //??????
                    String humidity = main_object.getString("humidity");
                    current_humidity.setText(humidity+getString(R.string.percent_unit));

                    //?????????
                    if(response.has("rain")){
                        JSONObject rain_object = response.getJSONObject("rain");
                        if(rain_object.has("1h")){
                            rain_1h = rain_object.getString("1h");
                            rain_1h = String.valueOf(Math.round(Double.valueOf(rain_1h)*10));
                            current_rain.setText(rain_1h+getString(R.string.precipitation_unit));

                        }
                        else if(rain_object.has("3h")){
                            rain_3h = rain_object.getString("3h");
                            rain_3h = String.valueOf(Math.round(Double.valueOf(rain_3h)*10));
                            current_rain.setText(rain_3h+getString(R.string.precipitation_unit));
                        }
                        else {
                            current_rain.setText("0"+getString(R.string.precipitation_unit));
                        }
                    }
                    else {
                        current_rain.setText("0"+getString(R.string.precipitation_unit));
                    }

                    JSONObject weather= weather_object.getJSONObject(0);
                    String description = weather.getString("description");
                    current_desc.setText(description);

                    //?????? ?????????
                    String icon = weather.getString("icon");
                    int resID = getResId("icon_"+icon, R.drawable.class);
                    weathericon.setImageResource(resID);

                }catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SEULGI API RESPONSE",error.toString());
            }
        }
        );

        RequestQueue queue =  Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(jor);

    }

    public void find_future_weather(float latitude, float longitude) {
        String url = "http://api.openweathermap.org/data/2.5/onecall?appid=44cc7ff859937e12de419868524f8704&units=metric&id=1841811&lang=kr";
        url += "&lat="+String.valueOf(latitude)+"&lon="+String.valueOf(longitude);

        if(!hourlyItemList.isEmpty()) hourlyItemList.clear();
        hourlyItemList = new ArrayList<>();

        if(!dailyItemList.isEmpty()) dailyItemList.clear();
        dailyItemList = new ArrayList<>();

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray hourly_object = response.getJSONArray("hourly");
                    JSONArray daily_object = response.getJSONArray("daily");

                    for(int i=0;i<hourly_object.length() && i<36; i+=2){ //2?????? ?????? | 18?????? ?????????
                        JSONObject rec= hourly_object.getJSONObject(i);

                        //??????
                        String dt = rec.getString("dt");
                        long timestamp = Long.parseLong(dt);
                        Date date = new java.util.Date(timestamp*1000L);
                        SimpleDateFormat sdf = new java.text.SimpleDateFormat("a h" + "???");
                        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
                        dt = sdf.format(date);

                        //??????
                        temp_f = rec.getString("temp");
                        temp_f = String.valueOf(Math.round(Double.valueOf(temp_f)));

                        JSONArray weather_object = rec.getJSONArray("weather");
                        JSONObject weather = weather_object.getJSONObject(0);
                        String icon = weather.getString("icon");
                        int resID = getResId("icon_"+icon, R.drawable.class);

                        if(i==0){
                            hourlyItemList.add(new HourlyItem("??????",resID,temp_f+getString(R.string.temperature_unit)));
                        }
                        else {
                            hourlyItemList.add(new HourlyItem(dt,resID,temp_f+getString(R.string.temperature_unit)));
                        }
                    }

                    for(int i=1; i<daily_object.length(); i++){
                        JSONObject rec = daily_object.getJSONObject(i);
                        JSONObject get_temp = rec.getJSONObject("temp");

                        //??????
                        String dt = rec.getString("dt");
                        long timestamp = Long.parseLong(dt);
                        Date date = new java.util.Date(timestamp*1000L);
                        SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE", Locale.KOREAN);
                        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
                        dt = sdf.format(date);

                        //????????????
                        dailyLow = get_temp.getString("min");
                        dailyLow = String.valueOf(Math.round(Double.valueOf(dailyLow)));

                        //????????????
                        dailyHigh = get_temp.getString("max");
                        dailyHigh = String.valueOf(Math.round(Double.valueOf(dailyHigh)));

                        //?????????
                        JSONArray weather_object = rec.getJSONArray("weather");
                        JSONObject weather = weather_object.getJSONObject(0);
                        String icon = weather.getString("icon");
                        int resID = getResId("icon_"+icon, R.drawable.class);
;


                        dailyItemList.add(new DailyItem(dt,dailyLow+getString(R.string.temperature_unit),dailyHigh+getString(R.string.temperature_unit),resID));
                    }

                    /* HOURLY RECYCLERVIEW */
                    recyclerView = (RecyclerView) rootView.findViewById(R.id.hourly_recycler);
                    LinearLayoutManager layoutManager_h= new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(layoutManager_h);

                    HourlyItemAdapter adapter_h;
                    adapter_h = new HourlyItemAdapter(getActivity(),hourlyItemList);
                    recyclerView.setAdapter(adapter_h);
                    adapter_h.notifyDataSetChanged();

                    /*DAILY RECYCLERVIEW */
//                    recyclerView2 = (RecyclerView) rootView.findViewById(R.id.daily_recycler);
//                    LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
//                    recyclerView2.setLayoutManager(layoutManager);
//
//                    DailyItemAdapter adapter;
//                    adapter = new DailyItemAdapter(getActivity(),dailyItemList);
//                    recyclerView2.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();

                    closeProgressDialog();

                }catch(JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SEULGI API RESPONSE",error.toString());
            }
        }
        );

        RequestQueue queue =  Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(jor);

    }

    public String getCurrentAddress( double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude,longitude,1);
        } catch (IOException ioException) {
            return null;
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }
        if (addresses == null || addresses.size() == 0) {
            return null;
        }

        Address address = addresses.get(0);
        return address.getAddressLine(0);
    }

    public void displayWeather(Context context) {
        openProgressDialog();

        float lat = PreferenceManager.getFloat(context,"LATITUDE");
        float lon = PreferenceManager.getFloat(context,"LONGITUDE");

        find_weather(lat,lon);
        find_future_weather(lat,lon);

        String address = getCurrentAddress(lat, lon);
        if(address!=null){
            //region.setText(PreferenceManager.getString(getContext(),"CITY"));
        }
        else {
            PreferenceManager.setFloat(getContext(),"LATITUDE",35F);
            PreferenceManager.setFloat(getContext(),"LONGITUDE",(float)127F);
            displayWeather(getContext());

            Toast.makeText(getContext(),"????????? ?????????????????????.",Toast.LENGTH_SHORT);
        }
    }

    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void openProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("??????????????????..");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        progressDialog.show();
    }

    private void closeProgressDialog() {
        progressDialog.dismiss();
    }

    public void setTabPosition(int position) { tabPosition=position; }

    public int getTabPosition() { return tabPosition; }
}