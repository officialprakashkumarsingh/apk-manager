package com.apk.manager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppListFragment extends Fragment {
    public static final String TYPE_INSTALLED = "installed";
    public static final String TYPE_SYSTEM = "system";
    
    private static final String ARG_TYPE = "type";
    private static final String ARG_APP_MANAGER = "app_manager";

    private String listType;
    private AppManager appManager;
    private RecyclerView recyclerView;
    private AppAdapter adapter;
    private ProgressBar progressBar;
    private TextInputEditText searchEditText;
    private List<AppInfo> allApps = new ArrayList<>();
    private ExecutorService executorService;

    public static AppListFragment newInstance(String type, AppManager appManager) {
        AppListFragment fragment = new AppListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragment.setArguments(args);
        fragment.appManager = appManager;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            listType = getArguments().getString(ARG_TYPE);
        }
        executorService = Executors.newSingleThreadExecutor();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_app_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViews(view);
        setupRecyclerView();
        setupSearch();
        loadApps();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        searchEditText = view.findViewById(R.id.searchEditText);
    }

    private void setupRecyclerView() {
        adapter = new AppAdapter(new ArrayList<>(), this::onExportClicked);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterApps(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadApps() {
        progressBar.setVisibility(View.VISIBLE);
        
        executorService.execute(() -> {
            List<AppInfo> apps;
            if (TYPE_SYSTEM.equals(listType)) {
                apps = appManager.getInstalledApps(true);
                // Filter to show only system apps
                List<AppInfo> systemApps = new ArrayList<>();
                for (AppInfo app : apps) {
                    if (app.isSystemApp()) {
                        systemApps.add(app);
                    }
                }
                apps = systemApps;
            } else {
                apps = appManager.getUserApps();
            }

            requireActivity().runOnUiThread(() -> {
                allApps = apps;
                adapter.updateApps(apps);
                progressBar.setVisibility(View.GONE);
            });
        });
    }

    private void filterApps(String query) {
        if (query.isEmpty()) {
            adapter.updateApps(allApps);
            return;
        }

        List<AppInfo> filteredApps = new ArrayList<>();
        String lowercaseQuery = query.toLowerCase();
        
        for (AppInfo app : allApps) {
            if (app.getAppName().toLowerCase().contains(lowercaseQuery) ||
                app.getPackageName().toLowerCase().contains(lowercaseQuery)) {
                filteredApps.add(app);
            }
        }
        
        adapter.updateApps(filteredApps);
    }

    private void onExportClicked(AppInfo appInfo) {
        progressBar.setVisibility(View.VISIBLE);
        
        executorService.execute(() -> {
            boolean success = appManager.exportApk(appInfo);
            
            requireActivity().runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                MainActivity activity = (MainActivity) getActivity();
                if (activity != null) {
                    if (success) {
                        activity.showToast(getString(R.string.export_success));
                    } else {
                        activity.showToast(getString(R.string.export_failed));
                    }
                }
            });
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}