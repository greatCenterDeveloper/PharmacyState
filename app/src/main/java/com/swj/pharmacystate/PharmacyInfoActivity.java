package com.swj.pharmacystate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PharmacyInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView tvName;
    TextView tvTel;
    TextView tvAddr;
    TextView tvMondayOpen, tvMondayEnd;
    TextView tvTuesdayOpen, tvTuesdayEnd;
    TextView tvWednesdayOpen, tvWednesdayEnd;
    TextView tvThursdayOpen, tvThursdayEnd;
    TextView tvFridayOpen, tvFridayEnd;
    TextView tvSaturdayOpen, tvSaturdayEnd;
    TextView tvSundayOpen, tvSundayEnd;
    TextView tvHolidayOpen, tvHolidayEnd;
    MapView mapView;
    NaverMap naverMap;

    double gpsLatitude;     // 위도
    double gpsLongitude;    // 경도

    private void back() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_info);

        /*Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_backspace);
        toolbar.setOnClickListener(view -> back());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("약국 상세");*/

        findViewById(R.id.ac_img_btn).setOnClickListener(view -> back());

        tvName = findViewById(R.id.tv_name);
        tvTel = findViewById(R.id.tv_tel);
        tvAddr = findViewById(R.id.tv_addr);
        tvMondayOpen = findViewById(R.id.tv_monday_open);
        tvMondayEnd = findViewById(R.id.tv_monday_end);
        tvTuesdayOpen = findViewById(R.id.tv_tuesday_open);
        tvTuesdayEnd = findViewById(R.id.tv_tuesday_end);
        tvWednesdayOpen = findViewById(R.id.tv_wednesday_open);
        tvWednesdayEnd = findViewById(R.id.tv_wednesday_end);
        tvThursdayOpen = findViewById(R.id.tv_thursday_open);
        tvThursdayEnd = findViewById(R.id.tv_thursday_end);
        tvFridayOpen = findViewById(R.id.tv_friday_open);
        tvFridayEnd = findViewById(R.id.tv_friday_end);
        tvSaturdayOpen = findViewById(R.id.tv_saturday_open);
        tvSaturdayEnd = findViewById(R.id.tv_saturday_end);
        tvSundayOpen = findViewById(R.id.tv_sunday_open);
        tvSundayEnd = findViewById(R.id.tv_sunday_end);
        tvHolidayOpen = findViewById(R.id.tv_holiday_open);
        tvHolidayEnd = findViewById(R.id.tv_holiday_end);
        mapView = findViewById(R.id.mapview);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Intent intent = getIntent();
        PharmacyItem item = (PharmacyItem) intent.getSerializableExtra("item");

        tvName.setText(item.name);
        tvTel.setText(item.tel);
        tvAddr.setText(item.roadAddr);

        if(item.roadAddr == null) tvAddr.setText(item.lotNoAddr);

        // 월요일
        if(item.mondayOpen != null) {
            tvMondayOpen.setText(item.mondayOpen);
            tvMondayEnd.setText(item.mondayEnd);
        } else {
            tvMondayOpen.setText("휴무");
            findViewById(R.id.tv_mon).setVisibility(View.GONE);
            tvMondayEnd.setVisibility(View.GONE);
        }


        // 화요일
        if(item.tuesdayOpen != null) {
            tvTuesdayOpen.setText(item.tuesdayOpen);
            tvTuesdayEnd.setText(item.tuesdayEnd);
        } else {
            tvTuesdayOpen.setText("휴무");
            findViewById(R.id.tv_tues).setVisibility(View.GONE);
            tvTuesdayEnd.setVisibility(View.GONE);
        }


        // 수요일
        if(item.wednesdayOpen != null) {
            tvWednesdayOpen.setText(item.wednesdayOpen);
            tvWednesdayEnd.setText(item.wednesdayEnd);
        } else {
            tvWednesdayOpen.setText("휴무");
            findViewById(R.id.tv_wedn).setVisibility(View.GONE);
            tvWednesdayEnd.setVisibility(View.GONE);
        }


        // 목요일
        if(item.thursdayOpen != null) {
            tvThursdayOpen.setText(item.thursdayOpen);
            tvThursdayEnd.setText(item.thursdayEnd);
        } else {
            tvThursdayOpen.setText("휴무");
            findViewById(R.id.tv_thur).setVisibility(View.GONE);
            tvThursdayEnd.setVisibility(View.GONE);
        }


        // 금요일
        if(item.fridayOpen != null) {
            tvFridayOpen.setText(item.fridayOpen);
            tvFridayEnd.setText(item.fridayEnd);
        } else {
            tvFridayOpen.setText("휴무");
            findViewById(R.id.tv_fri).setVisibility(View.GONE);
            tvFridayEnd.setVisibility(View.GONE);
        }


        // 토요일
        if(item.saturdayOpen != null) {
            tvSaturdayOpen.setText(item.saturdayOpen);
            tvSaturdayEnd.setText(item.saturdayEnd);
        } else {
            tvSaturdayOpen.setText("휴무");
            findViewById(R.id.tv_sat).setVisibility(View.GONE);
            tvSaturdayEnd.setVisibility(View.GONE);
        }


        // 일요일
        if(item.sundayOpen != null) {
            tvSundayOpen.setText(item.sundayOpen);
            tvSundayEnd.setText(item.sundayEnd);
        } else {
            tvSundayOpen.setText("휴무");
            findViewById(R.id.tv_sun).setVisibility(View.GONE);
            tvSundayEnd.setVisibility(View.GONE);
        }


        // 공휴일
        if(item.holidayOpen != null) {
            tvHolidayOpen.setText(item.holidayOpen);
            tvHolidayEnd.setText(item.holidayEnd);
        } else {
            tvHolidayOpen.setText("휴무");
            findViewById(R.id.tv_holi).setVisibility(View.GONE);
            tvHolidayEnd.setVisibility(View.GONE);
        }

        gpsLatitude = item.gpsLatitude;
        gpsLongitude = item.gpsLongitude;

        // api로 긁어온 데이터에 위도 경도가 없었다면.. 초기값 0.0일 것이므로...
        if(gpsLatitude == 0.0 && gpsLongitude == 0.0) {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);

            String addr = item.roadAddr;
            if(item.roadAddr == null) addr = item.lotNoAddr;

            try {
                List<Address> addresses = geocoder.getFromLocationName(addr, 3);
                gpsLatitude = addresses.get(0).getLatitude();
                gpsLongitude = addresses.get(0).getLongitude();
            } catch (IOException e) { throw new RuntimeException(e); }
        }

        // naver map
        String naverClientId = getString(R.string.NAVER_CLIENT_ID); // id 가져오기
        NaverMapSdk.getInstance(this).setClient( //id 등록
                new NaverMapSdk.NaverCloudPlatformClient(naverClientId));
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setMapType(NaverMap.MapType.Basic);

        //건물 표시
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);
        naverMap.setIndoorEnabled(false);

        //카메라 세팅
        CameraPosition cameraPosition = new CameraPosition(new LatLng(gpsLatitude, gpsLongitude), 17.5, 0, 0);
        naverMap.setCameraPosition(cameraPosition);
        this.naverMap = naverMap;
        UiSettings uiSettings = naverMap.getUiSettings();

        //네이버맵 UI 설정. 로고 클릭은 반드시 true 값으로 해야함(정책사항)
        uiSettings.setCompassEnabled(true);         // 나침반 보이기
        uiSettings.setLocationButtonEnabled(true);  // 현재 위치 버튼 보이기
        uiSettings.setLogoClickEnabled(true);       // 네이버 로고 클릭
        uiSettings.setScrollGesturesEnabled(true);  // 스크롤 제스처
        uiSettings.setZoomControlEnabled(true);     // 줌 컨트롤 보이기
        uiSettings.setRotateGesturesEnabled(true);  // 화면 회전 제스처

        Marker marker = new Marker();
        marker.setPosition(new LatLng(gpsLatitude, gpsLongitude));
        marker.setMap(naverMap);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}