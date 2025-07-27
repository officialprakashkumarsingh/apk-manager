# APK Repository

This directory contains exported APK files from the APK Manager app.

## How it works

When you export an APK using the APK Manager app, the files are saved to:
- **Device Storage**: `/sdcard/APK Manager/`
- **This Repository**: Copy of exported APKs for version control

## File Naming Convention

APK files are named using the pattern: `{app_name}_{version}.apk`

Examples:
- `WhatsApp_2.23.24.23.apk`
- `Chrome_120.0.6099.144.apk`
- `APK_Manager_1.0.apk`

## Features

- **Export**: Extract APK files from installed apps
- **Share**: Share APK files with others
- **Repository**: Maintain a collection of APK files
- **Analysis**: View app information and metadata

## Usage

1. Open APK Manager app
2. Browse installed or system apps
3. Tap the export button on any app
4. APK will be saved and available in the repository tab
5. Use share or delete options as needed

## Permissions Required

- `READ_EXTERNAL_STORAGE`
- `WRITE_EXTERNAL_STORAGE` (Android < 11)
- `MANAGE_EXTERNAL_STORAGE` (Android 11+)
- `QUERY_ALL_PACKAGES`