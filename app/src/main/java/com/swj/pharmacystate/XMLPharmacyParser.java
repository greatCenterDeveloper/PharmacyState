package com.swj.pharmacystate;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class XMLPharmacyParser {
    public static void setPharmacyList(ArrayList<PharmacyItem> items) {
        /*new Thread(){
            @Override
            public void run() {
                for(int i=1; i<=5; i++) loopAPI(items, i);
                Log.i("pharmacyThread", String.valueOf(items.size()));
            }
        }.start();*/
        for(int i=1; i<=5; i++) loopAPI(items, i);
    }

    private static void loopAPI(ArrayList<PharmacyItem> items, int pIndex) {
        try {
            String stringUrl = "https://openapi.gg.go.kr/ParmacyInfo" +
                    "?KEY=47daf33f4f0a456b86b3d139372a72d5" +
                    "&Type=xml" +
                    "&pIndex=" + pIndex +
                    "&pSize=1000";
            URL url = new URL(stringUrl);
            XmlPullParser xpp = XmlPullParserFactory.newInstance().newPullParser();
            xpp.setInput(new InputStreamReader(url.openStream()));

            int eventType = xpp.getEventType();
            PharmacyItem item = null;
            int num = 1;

            while(eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String tagName = xpp.getName();
                        switch (tagName) {
                            case "row":  // 새로운 약국 시작
                                item = new PharmacyItem();
                                item.num = num;
                                num++;
                                break;
                            case "SIGUN_NM":  // 시군구
                                xpp.next();
                                item.sigungu = xpp.getText();
                                break;
                            case "INST_NM":  // 약국 이름
                                xpp.next();
                                item.name = xpp.getText();
                                break;
                            case "REPRSNT_TELNO":  // 약국 전화번호
                                xpp.next();
                                item.tel = xpp.getText();
                                break;
                            case "MON_END_TREAT_TM":  // 월요일 마감시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.mondayEnd = xpp.getText();
                                break;
                            case "TUES_END_TREAT_TM":  // 화요일 마감시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.tuesdayEnd = xpp.getText();
                                break;
                            case "WED_END_TREAT_TM":  // 수요일 마감시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.wednesdayEnd = xpp.getText();
                                break;
                            case "THUR_END_TREAT_TM":  // 목요일 마감시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.thursdayEnd = xpp.getText();
                                break;
                            case "FRI_END_TREAT_TM":  // 금요일 마감시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.fridayEnd = xpp.getText();
                                break;
                            case "SAT_END_TREAT_TM":  // 토요일 마감시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.saturdayEnd = xpp.getText();
                                break;
                            case "SUN_END_TREAT_TM":  // 일요일 마감시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.sundayEnd = xpp.getText();
                                break;
                            case "HOLIDAY_END_TREAT_TM":  // 공휴일 마감시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.holidayEnd = xpp.getText();
                                break;
                            case "MON_BEGIN_TREAT_TM":  // 월요일 오픈시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.mondayOpen = xpp.getText();
                                break;
                            case "TUES_BEGIN_TREAT_TM":  // 화요일 오픈시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.tuesdayOpen = xpp.getText();
                                break;
                            case "WED_BEGIN_TREAT_TM":  // 수요일 오픈시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.wednesdayOpen = xpp.getText();
                                break;
                            case "THUR_BEGIN_TREAT_TM":  // 목요일 오픈시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.thursdayOpen = xpp.getText();
                                break;
                            case "FRI_BEGIN_TREAT_TM":  // 금요일 오픈시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.fridayOpen = xpp.getText();
                                break;
                            case "SAT_BEGIN_TREAT_TM":  // 토요일 오픈시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.saturdayOpen = xpp.getText();
                                break;
                            case "SUN_BEGIN_TREAT_TM":  // 일요일 오픈시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.sundayOpen = xpp.getText();
                                break;
                            case "HOLIDAY_BEGIN_TREAT_TM":  // 공휴일 오픈시간
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.holidayOpen = xpp.getText();
                                break;
                            case "REFINE_LOTNO_ADDR" :  // 일반 주소 (도로명 주소가 없을 경우 넣어주는 주소)
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.lotNoAddr = xpp.getText();
                                break;
                            case "REFINE_ROADNM_ADDR":  // 도로명 주소
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.roadAddr = xpp.getText();
                                break;
                            case "REFINE_WGS84_LOGT":  // 경도
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.gpsLongitude = Double.parseDouble(xpp.getText());
                                else if(xpp.getText() == null)
                                    item.gpsLongitude = 0.0;
                                break;
                            case "REFINE_WGS84_LAT":  // 위도
                                xpp.next();
                                if (xpp.getText() != null)
                                    item.gpsLatitude = Double.parseDouble(xpp.getText());
                                else if(xpp.getText() == null)
                                    item.gpsLatitude = 0.0;
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        String tagName2 = xpp.getName();
                        if(tagName2.equals("row")) {
                            String businessDay = getBusinessDay(item);
                            item.businessDay = businessDay;
                            items.add(item);
                        }
                        break;
                }
                eventType = xpp.next();
            }
        } catch (MalformedURLException e) { throw new RuntimeException(e); }
        catch (XmlPullParserException e) { throw new RuntimeException(e); }
        catch (IOException e) { throw new RuntimeException(e); }
    }

    private static String getBusinessDay(PharmacyItem item) {
        String businessDay = "";

        if(item.mondayOpen != null &&
                item.tuesdayOpen != null &&
                item.wednesdayOpen != null &&
                item.thursdayOpen != null &&
                item.fridayOpen != null)
            businessDay = "평일, ";

        if(!businessDay.contains("평일")) {
            if(item.mondayOpen != null)
                businessDay += "월, ";

            if(item.tuesdayOpen != null)
                businessDay += "화, ";

            if(item.wednesdayOpen != null)
                businessDay += "수, ";

            if(item.thursdayOpen != null)
                businessDay += "목, ";

            if(item.fridayOpen != null)
                businessDay += "금, ";
        }

        if(item.saturdayOpen != null && item.sundayOpen != null)
            businessDay += "주말, ";

        if(!businessDay.contains("주말")) {
            if (item.saturdayOpen != null)
                businessDay += "토, ";

            if (item.sundayOpen != null)
                businessDay += "일요일, ";
        }

        if(item.holidayOpen != null)
            businessDay += "공휴일";

        // 마지막에 붙어있는 [, ] 제거
        if(businessDay.endsWith(", "))
            businessDay = businessDay.substring(0, businessDay.length()-2);

        if(businessDay.equals("평일, 주말, 공휴일"))
            businessDay = "365일 연중무휴";

        return businessDay;
    }
}
