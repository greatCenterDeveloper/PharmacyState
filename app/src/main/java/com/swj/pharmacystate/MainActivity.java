package com.swj.pharmacystate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SearchView searchView;
    AutoCompleteTextView acTvSigungu;
    ArrayList<String> sigunguList; // AutoCompleteTextView 에 들어가는 시군구 리스트
    Fragment[] fragments = new Fragment[3];
    BottomNavigationView bnv;
    ArrayList<PharmacyItem> items = new ArrayList<>();
    ArrayList<PharmacyItem> weekEndItems = new ArrayList<>();
    ArrayList<PharmacyItem> holidayItems = new ArrayList<>();
    ProgressBar progressBar;
    String currentSelectedBottomNavigationViewItem = "없음";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_pharmacy);
        setSupportActionBar(toolbar);

        fragments[0] = new WeekdayFragment();
        fragments[1] = new WeekendFragment();
        fragments[2] = new HolidayFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_fragment, fragments[0])
                .commit();

        bnv = findViewById(R.id.bnv);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.bnv_weekday)
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_fragment, fragments[0])
                            .commit();
                else if(item.getItemId() == R.id.bnv_weekend)
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_fragment, fragments[1])
                            .commit();
                else if(item.getItemId() == R.id.bnv_holiday)
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_fragment, fragments[2])
                            .commit();
                return true;
            }
        });

        acTvSigungu = findViewById(R.id.ac_tv_sigungu);
        acTvSigungu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(currentSelectedBottomNavigationViewItem.equals("weekday")) {
                    ((WeekdayFragment)fragments[0]).searchSigungu(sigunguList.get(i), items);
                    if(i == 0) ((WeekdayFragment)fragments[0]).setItems(items);
                }
                else if(currentSelectedBottomNavigationViewItem.equals("weekend")) {
                    ((WeekendFragment)fragments[1]).searchSigungu(sigunguList.get(i), weekEndItems);
                    if(i == 0) ((WeekendFragment)fragments[1]).setItems(weekEndItems);
                }
                else if(currentSelectedBottomNavigationViewItem.equals("holiday")) {
                    ((HolidayFragment)fragments[2]).searchSigungu(sigunguList.get(i), holidayItems);
                    if(i == 0) ((HolidayFragment)fragments[2]).setItems(holidayItems);
                }
            }
        });

        progressBar = findViewById(R.id.progressbar);
        pharmacyThread(items);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);

        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("약국 명을 입력 하세요.");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(currentSelectedBottomNavigationViewItem.equals("weekday")) {
                    ((WeekdayFragment)fragments[0]).searchName(query, items);
                }
                else if(currentSelectedBottomNavigationViewItem.equals("weekend")) {
                    ((WeekendFragment)fragments[1]).searchName(query, weekEndItems);
                }
                else if(currentSelectedBottomNavigationViewItem.equals("holiday")) {
                    ((HolidayFragment)fragments[2]).searchName(query, holidayItems);
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if(currentSelectedBottomNavigationViewItem.equals("weekday"))
                    ((WeekdayFragment)fragments[0]).setItems(items);
                else if(currentSelectedBottomNavigationViewItem.equals("weekend"))
                    ((WeekendFragment)fragments[1]).setItems(weekEndItems);
                else if(currentSelectedBottomNavigationViewItem.equals("holiday"))
                    ((HolidayFragment)fragments[2]).setItems(holidayItems);

                //searchView.onActionViewCollapsed();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void pharmacyThread(ArrayList<PharmacyItem> items) {
        new Thread(){
            @Override
            public void run() {
                XMLPharmacyParser.setPharmacyList(items);
                ((WeekdayFragment)fragments[0]).setItems(items);

                int num = 1;
                for(int i=0; i<items.size(); i++) {
                    if (items.get(i).businessDay.contains("주말") ||
                        items.get(i).businessDay.contains("연중무휴") ||
                        items.get(i).businessDay.contains("토") ||
                        items.get(i).businessDay.contains("일요일")) {
                            PharmacyItem item = new PharmacyItem(items.get(i));
                            item.num = num;
                            num++;
                            weekEndItems.add(item);
                    }
                }

                num = 1;
                for(int i=0; i<items.size(); i++) {
                    if (items.get(i).businessDay.contains("공휴일")) {
                        PharmacyItem item = new PharmacyItem(items.get(i));
                        item.num = num;
                        num++;
                        holidayItems.add(item);
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        progressBar = null;

                        findViewById(R.id.iv_medicine).setVisibility(View.GONE);
                        findViewById(R.id.appbar).setVisibility(View.VISIBLE);
                        findViewById(R.id.layout_pharmacy).setVisibility(View.VISIBLE);

                        sigunguList = new ArrayList<>();
                        for(PharmacyItem item : items) {
                            if(!sigunguList.contains(item.sigungu))
                                sigunguList.add(item.sigungu);
                        }

                        sigunguList.add(0,"선택안함");

                        ArrayAdapter sigunguAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, sigunguList);
                        acTvSigungu.setAdapter(sigunguAdapter);
                    }
                });
            }
        }.start();
    }
} // MainActivity class..