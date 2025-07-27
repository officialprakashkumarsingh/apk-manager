package com.apk.manager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApkRepositoryFragment extends Fragment {
    private AppManager appManager;
    private RecyclerView recyclerView;
    private ApkFileAdapter adapter;
    private ProgressBar progressBar;
    private TextInputEditText searchEditText;
    private List<ApkFile> allApkFiles = new ArrayList<>();
    private ExecutorService executorService;

    public static ApkRepositoryFragment newInstance(AppManager appManager) {
        ApkRepositoryFragment fragment = new ApkRepositoryFragment();
        fragment.appManager = appManager;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        loadApkFiles();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progressBar);
        searchEditText = view.findViewById(R.id.searchEditText);
        searchEditText.setHint("Search APK files...");
    }

    private void setupRecyclerView() {
        adapter = new ApkFileAdapter(new ArrayList<>(), this::onShareClicked, this::onDeleteClicked);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void setupSearch() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterApkFiles(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadApkFiles() {
        progressBar.setVisibility(View.VISIBLE);
        
        executorService.execute(() -> {
            List<ApkFile> apkFiles = appManager.getExportedApks();

            requireActivity().runOnUiThread(() -> {
                allApkFiles = apkFiles;
                adapter.updateApkFiles(apkFiles);
                progressBar.setVisibility(View.GONE);
            });
        });
    }

    private void filterApkFiles(String query) {
        if (query.isEmpty()) {
            adapter.updateApkFiles(allApkFiles);
            return;
        }

        List<ApkFile> filteredFiles = new ArrayList<>();
        String lowercaseQuery = query.toLowerCase();
        
        for (ApkFile file : allApkFiles) {
            if (file.getFileName().toLowerCase().contains(lowercaseQuery) ||
                (file.getAppName() != null && file.getAppName().toLowerCase().contains(lowercaseQuery)) ||
                (file.getPackageName() != null && file.getPackageName().toLowerCase().contains(lowercaseQuery))) {
                filteredFiles.add(file);
            }
        }
        
        adapter.updateApkFiles(filteredFiles);
    }

    private void onShareClicked(ApkFile apkFile) {
        try {
            File file = apkFile.getFile();
            Uri fileUri = FileProvider.getUriForFile(
                requireContext(),
                "com.apk.manager.fileprovider",
                file
            );

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/vnd.android.package-archive");
            shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            
            startActivity(Intent.createChooser(shareIntent, "Share APK"));
        } catch (Exception e) {
            MainActivity activity = (MainActivity) getActivity();
            if (activity != null) {
                activity.showToast("Failed to share APK file");
            }
        }
    }

    private void onDeleteClicked(ApkFile apkFile) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete APK")
                .setMessage("Are you sure you want to delete " + apkFile.getFileName() + "?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    File file = apkFile.getFile();
                    if (file.delete()) {
                        loadApkFiles(); // Refresh the list
                        MainActivity activity = (MainActivity) getActivity();
                        if (activity != null) {
                            activity.showToast("APK file deleted");
                        }
                    } else {
                        MainActivity activity = (MainActivity) getActivity();
                        if (activity != null) {
                            activity.showToast("Failed to delete APK file");
                        }
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadApkFiles(); // Refresh when coming back to this tab
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}