package com.apk.manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ApkFileAdapter extends RecyclerView.Adapter<ApkFileAdapter.ApkFileViewHolder> {
    private List<ApkFile> apkFiles;
    private OnShareClickListener onShareClickListener;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnShareClickListener {
        void onShareClick(ApkFile apkFile);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(ApkFile apkFile);
    }

    public ApkFileAdapter(List<ApkFile> apkFiles, OnShareClickListener shareListener, 
                         OnDeleteClickListener deleteListener) {
        this.apkFiles = apkFiles;
        this.onShareClickListener = shareListener;
        this.onDeleteClickListener = deleteListener;
    }

    @NonNull
    @Override
    public ApkFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_apk_file, parent, false);
        return new ApkFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApkFileViewHolder holder, int position) {
        ApkFile apkFile = apkFiles.get(position);
        holder.bind(apkFile, onShareClickListener, onDeleteClickListener);
    }

    @Override
    public int getItemCount() {
        return apkFiles.size();
    }

    public void updateApkFiles(List<ApkFile> newApkFiles) {
        this.apkFiles = newApkFiles;
        notifyDataSetChanged();
    }

    static class ApkFileViewHolder extends RecyclerView.ViewHolder {
        private ImageView fileIcon;
        private TextView fileName;
        private TextView appName;
        private TextView fileSizeText;
        private TextView dateText;
        private MaterialButton shareButton;
        private MaterialButton deleteButton;
        private AppManager appManager;

        public ApkFileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileIcon = itemView.findViewById(R.id.fileIcon);
            fileName = itemView.findViewById(R.id.fileName);
            appName = itemView.findViewById(R.id.appName);
            fileSizeText = itemView.findViewById(R.id.fileSizeText);
            dateText = itemView.findViewById(R.id.dateText);
            shareButton = itemView.findViewById(R.id.shareButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            appManager = new AppManager(itemView.getContext());
        }

        public void bind(ApkFile apkFile, OnShareClickListener shareListener, 
                        OnDeleteClickListener deleteListener) {
            fileName.setText(apkFile.getFileName());
            
            if (apkFile.getAppName() != null && !apkFile.getAppName().isEmpty()) {
                appName.setText(apkFile.getAppName());
                appName.setVisibility(View.VISIBLE);
            } else {
                appName.setVisibility(View.GONE);
            }
            
            fileSizeText.setText(appManager.formatSize(apkFile.getFileSize()));
            dateText.setText(appManager.formatDate(apkFile.getLastModified()));

            shareButton.setOnClickListener(v -> {
                if (shareListener != null) {
                    shareListener.onShareClick(apkFile);
                }
            });

            deleteButton.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onDeleteClick(apkFile);
                }
            });
        }
    }
}