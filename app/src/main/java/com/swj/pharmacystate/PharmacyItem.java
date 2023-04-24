package com.swj.pharmacystate;

import java.io.Serializable;

public class PharmacyItem implements Serializable {

    int num;                // 순서
    String sigungu;         // 시군구
    String name;            // 약국 이름
    String tel;             // 약국 전화번호
    String mondayOpen;      //  월요일 시작 시간
    String mondayEnd;       // 월요일 마감 시간
    String tuesdayOpen;     // 화요일 시작 시간
    String tuesdayEnd;      // 화요일 마감 시간
    String wednesdayOpen;   // 수요일 시작 시간
    String wednesdayEnd;    // 수요일 마감 시간
    String thursdayOpen;    // 목요일 시작 시간
    String thursdayEnd;     // 목요일 마감 시간
    String fridayOpen;      // 금요일 시작 시간
    String fridayEnd;       // 금요일 마감 시간
    String saturdayOpen;    // 토요일 시작 시간
    String saturdayEnd;     // 토요일 마감 시간
    String sundayOpen;      // 일요일 시작 시간
    String sundayEnd;       // 일요일 마감 시간
    String holidayOpen;     // 공휴일 시작 시간
    String holidayEnd;      // 공휴일 마감 시간
    String lotNoAddr;       // 약국 일반 주소 (도로명 주소가 비어있을 경우 사용)
    String roadAddr;         // 약국 도로명 주소
    String businessDay;     // 영업일 : 평일, 주말, 공휴일

    double gpsLatitude;     // 위도
    double gpsLongitude;    // 경도

    public PharmacyItem() {}

    public PharmacyItem(PharmacyItem item) {
        this.num = item.num;
        this.sigungu = item.sigungu;
        this.name = item.name;
        this.tel = item.tel;
        this.mondayOpen = item.mondayOpen;
        this.mondayEnd = item.mondayEnd;
        this.tuesdayOpen = item.tuesdayOpen;
        this.tuesdayEnd = item.tuesdayEnd;
        this.wednesdayOpen = item.wednesdayOpen;
        this.wednesdayEnd = item.wednesdayEnd;
        this.thursdayOpen = item.thursdayOpen;
        this.thursdayEnd = item.thursdayEnd;
        this.fridayOpen = item.fridayOpen;
        this.fridayEnd = item.fridayEnd;
        this.saturdayOpen = item.saturdayOpen;
        this.saturdayEnd = item.saturdayEnd;
        this.sundayOpen = item.sundayOpen;
        this.sundayEnd = item.sundayEnd;
        this.holidayOpen = item.holidayOpen;
        this.holidayEnd = item.holidayEnd;
        this.lotNoAddr = item.lotNoAddr;
        this.roadAddr = item.roadAddr;
        this.businessDay = item.businessDay;
        this.gpsLatitude = item.gpsLatitude;
        this.gpsLongitude = item.gpsLongitude;
    }
}
