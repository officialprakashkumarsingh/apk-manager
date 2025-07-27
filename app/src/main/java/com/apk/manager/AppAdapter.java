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

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.AppViewHolder> {
    private List<AppInfo> apps;
    private OnExportClickListener onExportClickListener;

    public interface OnExportClickListener {
        void onExportClick(AppInfo appInfo);
    }

    public AppAdapter(List<AppInfo> apps, OnExportClickListener listener) {
        this.apps = apps;
        this.onExportClickListener = listener;
    }

    @NonNull
    @Override
    public AppViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_app, parent, false);
        return new AppViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppViewHolder holder, int position) {
        AppInfo app = apps.get(position);
        holder.bind(app, onExportClickListener);
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public void updateApps(List<AppInfo> newApps) {
        this.apps = newApps;
        notifyDataSetChanged();
    }

    static class AppViewHolder extends RecyclerView.ViewHolder {
        private ImageView appIcon;
        private TextView appName;
        private TextView packageName;
        private TextView versionText;
        private TextView sizeText;
        private MaterialButton exportButton;
        private AppManager appManager;

        public AppViewHolder(@NonNull View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.appIcon);
            appName = itemView.findViewById(R.id.appName);
            packageName = itemView.findViewById(R.id.packageName);
            versionText = itemView.findViewById(R.id.versionText);
            sizeText = itemView.findViewById(R.id.sizeText);
            exportButton = itemView.findViewById(R.id.exportButton);
            appManager = new AppManager(itemView.getContext());
        }

        public void bind(AppInfo app, OnExportClickListener listener) {
            appIcon.setImageDrawable(app.getAppIcon());
            appName.setText(app.getAppName());
            packageName.setText(app.getPackageName());
            versionText.setText("Version: " + app.getVersionName());
            sizeText.setText(appManager.formatSize(app.getSize()));

            exportButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onExportClick(app);
                }
            });
        }
    }
}