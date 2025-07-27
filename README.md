# APK Manager

A powerful Android application for managing APK files with a modern Material Design 3 interface. Built with Java and traditional Android Views, this app provides comprehensive APK management capabilities.

## Features

### 📱 App Management
- **Installed Apps**: View all user-installed applications
- **System Apps**: Browse system applications 
- **App Information**: Display app name, package, version, size, and install date
- **Search Functionality**: Quick search through apps by name or package

### 📦 APK Operations
- **Export APKs**: Extract APK files from installed applications
- **APK Repository**: Manage exported APK files
- **Share APKs**: Share APK files with other apps
- **Delete APKs**: Remove unwanted APK files from storage

### 🎨 Modern UI
- Material Design 3 components
- Tab-based navigation with ViewPager2
- Card-based app listings
- Search functionality
- Progress indicators
- Confirmation dialogs

## Screenshots

The app features three main tabs:
1. **Installed Apps** - View and export user applications
2. **System Apps** - Browse system applications  
3. **APK Repository** - Manage exported APK files

## Technical Architecture

### Project Structure
```
com.apk.manager/
├── MainActivity.java          # Main activity with tab navigation
├── AppManager.java           # Core APK management logic
├── AppInfo.java             # App data model
├── ApkFile.java             # APK file data model
├── MainPagerAdapter.java    # ViewPager2 adapter
├── AppListFragment.java     # Fragment for app lists
├── ApkRepositoryFragment.java # Fragment for APK repository
├── AppAdapter.java          # RecyclerView adapter for apps
└── ApkFileAdapter.java      # RecyclerView adapter for APK files
```

### Key Components
- **AppManager**: Handles app discovery, APK extraction, and file operations
- **MainActivity**: Manages navigation and permissions
- **Fragments**: Modular UI components for different app sections
- **Adapters**: RecyclerView adapters with search filtering

### Technologies Used
- **Language**: Java
- **UI Framework**: Traditional Android Views with Material Design 3
- **Architecture**: Fragment-based with ViewPager2
- **Storage**: External storage with FileProvider
- **Threading**: ExecutorService for background operations

## Permissions

The app requires the following permissions:

```xml
<uses-permission android:name="android.permission.QUERY_ALL_PACKAGES" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
```

## Installation & Building

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+ (minimum)
- Android SDK 34 (target)

### Build Instructions
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd apk-manager
   ```

2. Open in Android Studio
3. Sync Gradle files
4. Build and run on device or emulator

### Gradle Build
```bash
./gradlew assembleDebug      # Build debug APK
./gradlew assembleRelease    # Build release APK
./gradlew test              # Run unit tests
./gradlew connectedAndroidTest # Run instrumented tests
```

## APK Repository

Exported APKs are stored in:
- **Device**: `/sdcard/APK Manager/`
- **Project**: `apk-repository/` directory

The repository maintains exported APK files for version control and backup purposes.

## Package Information

- **Package Name**: `com.apk.manager`
- **Version**: 1.0 (Version Code: 1)
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

## Security Features

- FileProvider for secure file sharing
- Scoped storage compliance (Android 10+)
- Permission-based access control
- External storage validation

## Testing

The project includes both unit tests and instrumented tests:

- **Unit Tests**: `app/src/test/java/com/apk/manager/`
- **Instrumented Tests**: `app/src/androidTest/java/com/apk/manager/`

Run tests with:
```bash
./gradlew test                    # Unit tests
./gradlew connectedAndroidTest    # Integration tests
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is open source. See the LICENSE file for details.

## Support

For issues and feature requests, please use the GitHub issue tracker.