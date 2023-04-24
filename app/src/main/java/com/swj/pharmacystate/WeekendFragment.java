package com.swj.pharmacystate;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeekendFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weekend, container, false);
    }

    ArrayList<PharmacyItem> items;
    ArrayList<PharmacyItem> currentItems = new ArrayList<>();
    ImageView ivPrevPage, ivNextPage;
    TextView tvCurrentPage;
    RecyclerView recyclerView;
    PharmacyAdapter adapter;
    int currentPage = 1;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview_weekend);
        ivPrevPage = view.findViewById(R.id.iv_prev_page);
        ivNextPage = view.findViewById(R.id.iv_next_page);
        tvCurrentPage = view.findViewById(R.id.tv_current_page);

        ivPrevPage.setOnClickListener(view1 -> prevPage());
        ivNextPage.setOnClickListener(view1 -> nextPage());

        // 여기에 대량의 데이터를 추가해서 보여줘야 하므로.. currentItems는 멤버 변수에 넣어야 한다.
        adapter = new PharmacyAdapter(getActivity(), currentItems);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getActivity() != null)
            items = ((MainActivity)getActivity()).weekEndItems;

        ((MainActivity)getActivity()).currentSelectedBottomNavigationViewItem = "weekend";

        for(int i=0; i<items.size(); i++) {
            if(currentItems.size() >= 10) break;
            currentItems.add(items.get(i));
        }
    }


    public void searchName(String query, ArrayList<PharmacyItem> mainActivityitems) {
        items = mainActivityitems;
        currentPage = 1;
        tvCurrentPage.setText(String.valueOf(currentPage));

        ArrayList<PharmacyItem> tempList = new ArrayList<>();
        int num = 1;
        for(int i=0; i<items.size(); i++) {
            if(items.get(i).name.contains(query)) {
                items.get(i).num = num;
                tempList.add(items.get(i));
                num++;
            }
        }
        items = tempList;

        currentItems.clear();
        for(int i=0; i<items.size(); i++) {
            if(i == 10) break;
            currentItems.add(items.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    public void searchSigungu(String sigungu, ArrayList<PharmacyItem> mainActivityitems) {
        items = mainActivityitems;
        currentPage = 1;
        tvCurrentPage.setText(String.valueOf(currentPage));

        ArrayList<PharmacyItem> tempList = new ArrayList<>();
        int num = 1;
        for(int i=0; i<items.size(); i++) {
            if(items.get(i).sigungu.equals(sigungu)) {
                items.get(i).num = num;
                tempList.add(items.get(i));
                num++;
            }
        }
        items = tempList;

        currentItems.clear();
        for(int i=0; i<items.size(); i++) {
            if(i == 10) break;
            currentItems.add(items.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    public void setItems (ArrayList<PharmacyItem> mainActivityitems) {
        items = mainActivityitems;
        currentPage = 1;
        tvCurrentPage.setText(String.valueOf(currentPage));

        currentItems.clear();
        for(int i=(currentPage*10 - 10); i<(currentPage*10); i++) {
            items.get(i).num = i+1;
            currentItems.add(items.get(i));
        }

        //getActivity().runOnUiThread(new Runnable() {
        //    @Override
        //    public void run() {
                /*if(adapter != null) */adapter.notifyDataSetChanged();
        //    }
        //});
    }

    private void prevPage() {
        if(currentPage > 1) currentPage--;
        else return;

        tvCurrentPage.setText(String.valueOf(currentPage));

        currentItems.clear();
        for(int i= (currentPage*10 - 10); i<(currentPage*10); i++) {
            items.get(i).num = i+1;
            currentItems.add(items.get(i));
        }
        adapter.notifyDataSetChanged();
    }

    private void nextPage() {
        int pageMax = items.size()/10 + 1;

        if(items.size() % 10 == 0)
            pageMax = items.size()/10;

        int maxPage = 1;

        if(currentPage == pageMax) return;

        if(currentPage < pageMax){
            currentPage++;
            maxPage = currentPage*10;

            if(currentPage == pageMax)
                maxPage = items.size();
        }

        currentItems.clear();
        for(int i= (currentPage*10 - 10); i<maxPage; i++) {
            items.get(i).num = i+1;
            currentItems.add(items.get(i));
        }

        tvCurrentPage.setText(String.valueOf(currentPage));
        adapter.notifyDataSetChanged();
    }
}
