# GameSDK-Global对接文档

[TOC]

## 1.注意事项
* `targetSdkVersion`必须>=29(Google Play 强制要求), `minSdkVersion`必须>=16;
* 游戏包名请与商务提供的包名保持一致;
* 游戏打包请务必使用新快签名文件，该文件在压缩包的签名文件夹下的xinyou.jks，密码在秘钥.txt文件内;
* 请用Android Studio打包，Eclipse不支持;
* 开启androidx依赖;
* 建议游戏主Activity的启动模式为`singleTask`;

### 1.1 关于Android P 非HTTPS连接适配
可通过以下方式解决：
```xml
 <application
    android:usesCleartextTraffic="true"/>
```

### 1.2 Android 10 沙盒存储适配
```xml
 <application
    android:requestLegacyExternalStorage="true"/>
```

### 1.3 迁移至Androidx
在项目根目录的`gradle.properties`文件中配置：
```properties
android.useAndroidX=true
# Automatically convert third-party libraries to use AndroidX
android.enableJetifier=true
```

### 1.4 方法数超64k

参考[官方文档](https://developer.android.com/studio/build/multidex)

## 2.导入SDK

### 2.1 添加SDK依赖
```groovy
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    //添加SDK依赖
    implementation 'com.xinkuai.globalsdk:gamesdk:1.0.0'
}
```

### 2.2 SDK参数配置

在项目的`AndroidManifest.xml`文件中配置相关参数：
```xml
<application>

    <!--新快SDK AppId-->
    <meta-data
        android:name="XK_APP_ID"
        android:value="123456" />

    <!--新快SDK AppKey-->
    <meta-data
        android:name="XK_APP_KEY"
        android:value="xxxxxx" />

    <!--Facebook AppId-->
    <!--TODO appid 前加上'fb'前缀-->
    <meta-data
        android:name="com.facebook.sdk.ApplicationId"
        android:value="fb12345611111" />

     <!--Google AD AppId-->
    <meta-data
        android:name="com.google.android.gms.ads.APPLICATION_ID"
        android:value="${GOOGLE_APP_ID}" />    

    <!--AppsFlyer DevKey-->
    <meta-data
        android:name="AF_DEV_KEY"
        android:value="xxxxxxx" />

</application>
```

### 2.3 集成Firebase

1. 将 Firebase Android 配置文件添加到您的应用：
    a. 将出包参数内的`google-services.json`文件复制到应用的模块（应用级）目录中

2. 添加`google-services 插件`到项目
    a. 在项目根目录下的`build.gradle`文件中添加插件依赖
    
    ```groovy
    buildscript {
    
        repositories {
            mavenLocal()
            google()
            jcenter()
            maven { url 'https://devrepo.kakao.com/nexus/content/groups/public/' }
        }
    
        dependencies {
            classpath 'com.android.tools.build:gradle:4.0.0'
            //添加这行
            classpath 'com.google.gms:google-services:4.3.3'
    
        }
    }
    ```
    
    b. 在应用的`build.gradle`文件中引入该插件
    ```groovy
    apply plugin: 'com.android.application'
    
    //添加这行
    apply plugin: 'com.google.gms.google-services'
    
    android{
    ...
    }
    ```