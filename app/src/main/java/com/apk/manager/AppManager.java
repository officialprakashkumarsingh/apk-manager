package com.apk.manager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppManager {
    private static final String TAG = "AppManager";
    private Context context;
    private PackageManager packageManager;

    public AppManager(Context context) {
        this.context = context;
        this.packageManager = context.getPackageManager();
    }

    public List<AppInfo> getInstalledApps(boolean includeSystemApps) {
        List<AppInfo> apps = new ArrayList<>();
        List<PackageInfo> packages = packageManager.getInstalledPackages(0);

        for (PackageInfo packageInfo : packages) {
            ApplicationInfo appInfo = packageInfo.applicationInfo;
            boolean isSystemApp = (appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;

            if (!includeSystemApps && isSystemApp) {
                continue;
            }

            try {
                String appName = appInfo.loadLabel(packageManager).toString();
                Drawable appIcon = appInfo.loadIcon(packageManager);
                
                File sourceFile = new File(appInfo.sourceDir);
                long size = sourceFile.length();

                AppInfo app = new AppInfo(
                    appName,
                    packageInfo.packageName,
                    packageInfo.versionName != null ? packageInfo.versionName : "Unknown",
                    packageInfo.versionCode,
                    appIcon,
                    packageInfo.firstInstallTime,
                    packageInfo.lastUpdateTime,
                    appInfo.sourceDir,
                    size,
                    isSystemApp
                );
                apps.add(app);
            } catch (Exception e) {
                Log.e(TAG, "Error getting app info for " + packageInfo.packageName, e);
            }
        }

        return apps;
    }

    public List<AppInfo> getSystemApps() {
        return getInstalledApps(true);
    }

    public List<AppInfo> getUserApps() {
        return getInstalledApps(false);
    }

    public boolean exportApk(AppInfo appInfo) {
        try {
            File sourceFile = new File(appInfo.getSourceDir());
            if (!sourceFile.exists()) {
                Log.e(TAG, "Source APK file not found: " + appInfo.getSourceDir());
                return false;
            }

            // Create APK Manager directory
            File apkDir = new File(Environment.getExternalStorageDirectory(), "APK Manager");
            if (!apkDir.exists()) {
                apkDir.mkdirs();
            }

            // Create filename with app name and version
            String fileName = sanitizeFileName(appInfo.getAppName()) + "_" + 
                            appInfo.getVersionName() + ".apk";
            File destFile = new File(apkDir, fileName);

            // Copy APK file
            return copyFile(sourceFile, destFile);

        } catch (Exception e) {
            Log.e(TAG, "Error exporting APK for " + appInfo.getPackageName(), e);
            return false;
        }
    }

    public List<ApkFile> getExportedApks() {
        List<ApkFile> apkFiles = new ArrayList<>();
        File apkDir = new File(Environment.getExternalStorageDirectory(), "APK Manager");
        
        if (!apkDir.exists() || !apkDir.isDirectory()) {
            return apkFiles;
        }

        File[] files = apkDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".apk"));
        
        if (files != null) {
            for (File file : files) {
                try {
                    PackageInfo packageInfo = packageManager.getPackageArchiveInfo(
                        file.getAbsolutePath(), 0);
                    
                    if (packageInfo != null) {
                        ApplicationInfo appInfo = packageInfo.applicationInfo;
                        appInfo.sourceDir = file.getAbsolutePath();
                        appInfo.publicSourceDir = file.getAbsolutePath();
                        
                        String appName = appInfo.loadLabel(packageManager).toString();
                        
                        ApkFile apkFile = new ApkFile(
                            file.getName(),
                            file.getAbsolutePath(),
                            file.length(),
                            file.lastModified(),
                            packageInfo.packageName,
                            packageInfo.versionName != null ? packageInfo.versionName : "Unknown",
                            packageInfo.versionCode,
                            appName
                        );
                        apkFiles.add(apkFile);
                    } else {
                        // If package info can't be read, create basic file info
                        ApkFile apkFile = new ApkFile(
                            file.getName(),
                            file.getAbsolutePath(),
                            file.length(),
                            file.lastModified()
                        );
                        apkFiles.add(apkFile);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error reading APK file: " + file.getName(), e);
                    // Still add as basic file
                    ApkFile apkFile = new ApkFile(
                        file.getName(),
                        file.getAbsolutePath(),
                        file.length(),
                        file.lastModified()
                    );
                    apkFiles.add(apkFile);
                }
            }
        }

        return apkFiles;
    }

    private boolean copyFile(File source, File dest) {
        try (FileInputStream inStream = new FileInputStream(source);
             FileOutputStream outStream = new FileOutputStream(dest);
             FileChannel inChannel = inStream.getChannel();
             FileChannel outChannel = outStream.getChannel()) {
            
            inChannel.transferTo(0, inChannel.size(), outChannel);
            return true;
        } catch (IOException e) {
            Log.e(TAG, "Error copying file", e);
            return false;
        }
    }

    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9.-]", "_");
    }

    public String formatSize(long bytes) {
        if (bytes < 1024) return bytes + " B";
        if (bytes < 1024 * 1024) return String.format(Locale.getDefault(), "%.1f KB", bytes / 1024.0);
        if (bytes < 1024 * 1024 * 1024) return String.format(Locale.getDefault(), "%.1f MB", bytes / (1024.0 * 1024.0));
        return String.format(Locale.getDefault(), "%.1f GB", bytes / (1024.0 * 1024.0 * 1024.0));
    }

    public String formatDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
}