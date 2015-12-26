#/bin/sh

if [ $1 = '-l' ]; then
  adb logcat -s Cornowser:*
elif [ $1 = "--cleardata" ]; then
  adb shell pm clear io.xdevs23.cornowser.browser
elif [ $1 = "-i" ]; then
  adb push app/app-debug.apk /sdcard/CBCustom.apk
  adb shell pm set-install-location 1
  adb shell pm install -rdtf /sdcard/CBCustom.apk
elif [ $1 = "--start" ]; then
  adb shell am start -n io.xdevs23.cornowser.browser/.CornBrowser
elif [ $1 = "--uninstall" ]; then
  adb shell pm uninstall io.xdevs23.cornowser.browser/.CornBrowser
elif [ $1 = "--reinstall" ]; then
  adb shell pm uninstall io.xdevs23.cornowser.browser/.CornBrowser
  adb push app/app-debug.apk /sdcard/CBCustom.apk
  adb shell pm set-install-location 1
  adb shell pm install -rdtf /sdcard/CBCustom.apk
else
  adb push app/app-debug.apk /sdcard/CBCustom.apk
  adb shell pm set-install-location 1
  adb shell pm install -rdtf /sdcard/CBCustom.apk
  adb shell am start -n io.xdevs23.cornowser.browser/.CornBrowser
  adb logcat -s Cornowser:*
fi