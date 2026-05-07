name: Build Android APK

on:
  push:
    branches: [ main ]
  workflow_dispatch:

env:
  FORCE_JAVASCRIPT_ACTIONS_TO_NODE24: true

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'

      - name: Setup Android SDK (use pre-installed)
        run: |
          # Use ONLY the pre-installed SDK — no conflict
          unset ANDROID_SDK_ROOT
          echo "ANDROID_HOME=/usr/local/lib/android/sdk" >> $GITHUB_ENV
          echo "ANDROID_SDK_ROOT=/usr/local/lib/android/sdk" >> $GITHUB_ENV
          echo "/usr/local/lib/android/sdk/cmdline-tools/latest/bin" >> $GITHUB_PATH
          echo "/usr/local/lib/android/sdk/platform-tools" >> $GITHUB_PATH
          echo "/usr/local/lib/android/sdk/build-tools/34.0.0" >> $GITHUB_PATH

      - name: Accept SDK Licenses & install packages
        run: |
          yes | /usr/local/lib/android/sdk/cmdline-tools/latest/bin/sdkmanager --licenses > /dev/null 2>&1 || true
          /usr/local/lib/android/sdk/cmdline-tools/latest/bin/sdkmanager \
            "platforms;android-34" \
            "build-tools;34.0.0" \
            "platform-tools"

      - name: Find project root & generate Gradle wrapper
        run: |
          # Find where settings.gradle lives
          SETTINGS=$(find . -name "settings.gradle" | head -1)
          PROJECT_DIR=$(dirname "$SETTINGS")
          echo "Found project at: $PROJECT_DIR"
          echo "PROJECT_DIR=$PROJECT_DIR" >> $GITHUB_ENV

          # Download and run Gradle to create wrapper
          wget -q https://services.gradle.org/distributions/gradle-8.0-bin.zip -O /tmp/gradle.zip
          unzip -q /tmp/gradle.zip -d /tmp/gradle-install
          GRADLE_BIN=/tmp/gradle-install/gradle-8.0/bin/gradle
          chmod +x $GRADLE_BIN

          cd "$PROJECT_DIR"
          $GRADLE_BIN wrapper --gradle-version 8.0 2>&1
          chmod +x gradlew

      - name: Build Debug APK
        run: |
          cd "$PROJECT_DIR"
          # Force single SDK path to avoid conflict
          unset ANDROID_SDK_ROOT
          export ANDROID_HOME=/usr/local/lib/android/sdk
          ./gradlew assembleDebug \
            -Pandroid.sdk.home=/usr/local/lib/android/sdk \
            --no-daemon \
            --stacktrace 2>&1

      - name: Find and display APK
        run: find . -name "*.apk" 2>/dev/null

      - name: Upload APK
        uses: actions/upload-artifact@v4
        with:
          name: OnboardingApp-debug
          path: "**/app/build/outputs/apk/debug/app-debug.apk"
