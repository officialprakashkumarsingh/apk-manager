package com.apk.manager;

import java.io.File;

public class ApkFile {
    private String fileName;
    private String filePath;
    private long fileSize;
    private long lastModified;
    private String packageName;
    private String versionName;
    private int versionCode;
    private String appName;

    public ApkFile(String fileName, String filePath, long fileSize, long lastModified) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.lastModified = lastModified;
    }

    public ApkFile(String fileName, String filePath, long fileSize, long lastModified,
                   String packageName, String versionName, int versionCode, String appName) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.lastModified = lastModified;
        this.packageName = packageName;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.appName = appName;
    }

    // Getters
    public String getFileName() { return fileName; }
    public String getFilePath() { return filePath; }
    public long getFileSize() { return fileSize; }
    public long getLastModified() { return lastModified; }
    public String getPackageName() { return packageName; }
    public String getVersionName() { return versionName; }
    public int getVersionCode() { return versionCode; }
    public String getAppName() { return appName; }

    // Setters
    public void setFileName(String fileName) { this.fileName = fileName; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public void setFileSize(long fileSize) { this.fileSize = fileSize; }
    public void setLastModified(long lastModified) { this.lastModified = lastModified; }
    public void setPackageName(String packageName) { this.packageName = packageName; }
    public void setVersionName(String versionName) { this.versionName = versionName; }
    public void setVersionCode(int versionCode) { this.versionCode = versionCode; }
    public void setAppName(String appName) { this.appName = appName; }

    public File getFile() {
        return new File(filePath);
    }
}