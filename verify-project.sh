#!/bin/bash

echo "🔍 APK Manager Project Verification"
echo "=================================="
echo ""

# Check project structure
echo "📁 Project Structure:"
echo "✓ Root build.gradle exists: $([ -f build.gradle ] && echo "YES" || echo "NO")"
echo "✓ App build.gradle exists: $([ -f app/build.gradle ] && echo "YES" || echo "NO")"
echo "✓ Android manifest exists: $([ -f app/src/main/AndroidManifest.xml ] && echo "YES" || echo "NO")"
echo "✓ MainActivity exists: $([ -f app/src/main/java/com/apk/manager/MainActivity.java ] && echo "YES" || echo "NO")"
echo ""

# Check key Java files
echo "📱 Core Java Files:"
echo "✓ AppManager.java: $([ -f app/src/main/java/com/apk/manager/AppManager.java ] && echo "YES" || echo "NO")"
echo "✓ AppInfo.java: $([ -f app/src/main/java/com/apk/manager/AppInfo.java ] && echo "YES" || echo "NO")"
echo "✓ ApkFile.java: $([ -f app/src/main/java/com/apk/manager/ApkFile.java ] && echo "YES" || echo "NO")"
echo "✓ AppListFragment.java: $([ -f app/src/main/java/com/apk/manager/AppListFragment.java ] && echo "YES" || echo "NO")"
echo "✓ ApkRepositoryFragment.java: $([ -f app/src/main/java/com/apk/manager/ApkRepositoryFragment.java ] && echo "YES" || echo "NO")"
echo ""

# Check layouts
echo "🎨 Layout Files:"
echo "✓ activity_main.xml: $([ -f app/src/main/res/layout/activity_main.xml ] && echo "YES" || echo "NO")"
echo "✓ fragment_app_list.xml: $([ -f app/src/main/res/layout/fragment_app_list.xml ] && echo "YES" || echo "NO")"
echo "✓ item_app.xml: $([ -f app/src/main/res/layout/item_app.xml ] && echo "YES" || echo "NO")"
echo "✓ item_apk_file.xml: $([ -f app/src/main/res/layout/item_apk_file.xml ] && echo "YES" || echo "NO")"
echo ""

# Check resources
echo "📋 Resources:"
echo "✓ strings.xml: $([ -f app/src/main/res/values/strings.xml ] && echo "YES" || echo "NO")"
echo "✓ colors.xml: $([ -f app/src/main/res/values/colors.xml ] && echo "YES" || echo "NO")"
echo "✓ themes.xml: $([ -f app/src/main/res/values/themes.xml ] && echo "YES" || echo "NO")"
echo ""

# Check drawable icons
echo "🎯 Drawable Icons:"
echo "✓ ic_download.xml: $([ -f app/src/main/res/drawable/ic_download.xml ] && echo "YES" || echo "NO")"
echo "✓ ic_share.xml: $([ -f app/src/main/res/drawable/ic_share.xml ] && echo "YES" || echo "NO")"
echo "✓ ic_delete.xml: $([ -f app/src/main/res/drawable/ic_delete.xml ] && echo "YES" || echo "NO")"
echo "✓ ic_apk.xml: $([ -f app/src/main/res/drawable/ic_apk.xml ] && echo "YES" || echo "NO")"
echo ""

# Check APK repository
echo "📦 APK Repository:"
echo "✓ Repository directory: $([ -d apk-repository ] && echo "YES" || echo "NO")"
echo "✓ Repository README: $([ -f apk-repository/README.md ] && echo "YES" || echo "NO")"
echo ""

# Count files
echo "📊 File Counts:"
echo "• Java source files: $(find app/src/main/java -name "*.java" 2>/dev/null | wc -l)"
echo "• Layout files: $(find app/src/main/res/layout -name "*.xml" 2>/dev/null | wc -l)"
echo "• Drawable files: $(find app/src/main/res/drawable -name "*.xml" 2>/dev/null | wc -l)"
echo "• Test files: $(find app/src/test -name "*.java" 2>/dev/null | wc -l)"
echo ""

echo "✅ Project verification complete!"
echo ""
echo "🚀 To build the project:"
echo "1. Open in Android Studio"
echo "2. Sync Gradle files"
echo "3. Build > Make Project"
echo "4. Run on device/emulator"