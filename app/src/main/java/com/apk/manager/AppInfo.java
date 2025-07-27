package com.apk.manager;

import android.graphics.drawable.Drawable;

public class AppInfo {
    private String appName;
    private String packageName;
    private String versionName;
    private int versionCode;
    private Drawable appIcon;
    private long installTime;
    private long updateTime;
    private String sourceDir;
    private long size;
    private boolean isSystemApp;

    public AppInfo(String appName, String packageName, String versionName, int versionCode, 
                   Drawable appIcon, long installTime, long updateTime, String sourceDir, 
                   long size, boolean isSystemApp) {
        this.appName = appName;
        this.packageName = packageName;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.appIcon = appIcon;
        this.installTime = installTime;
        this.updateTime = updateTime;
        this.sourceDir = sourceDir;
        this.size = size;
        this.isSystemApp = isSystemApp;
    }

    // Getters
    public String getAppName() { return appName; }
    public String getPackageName() { return packageName; }
    public String getVersionName() { return versionName; }
    public int getVersionCode() { return versionCode; }
    public Drawable getAppIcon() { return appIcon; }
    public long getInstallTime() { return installTime; }
    public long getUpdateTime() { return updateTime; }
    public String getSourceDir() { return sourceDir; }
    public long getSize() { return size; }
    public boolean isSystemApp() { return isSystemApp; }

    // Setters
    public void setAppName(String appName) { this.appName = appName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public void setVersionName(String versionName) { this.versionName = versionName; }
    public void setVersionCode(int versionCode) { this.versionCode = versionCode; }
    public void setAppIcon(Drawable appIcon) { this.appIcon = appIcon; }
    public void setInstallTime(long installTime) { this.installTime = installTime; }
    public void setUpdateTime(long updateTime) { this.updateTime = updateTime; }
    public void setSourceDir(String sourceDir) { this.sourceDir = sourceDir; }
    public void setSize(long size) { this.size = size; }
    public void setSystemApp(boolean systemApp) { isSystemApp = systemApp; }
}