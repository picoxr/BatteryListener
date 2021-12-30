# How to use BatteryListener in Unity

- If you have any questions/comments, please visit [**Pico Developer Answers**](https://devanswers.pico-interactive.com/) and raise your question there.

AAR file is in /resource.    
Note: Regarding AAR file creation and usage, please refer to [the Guideline](http://static.appstore.picovr.com/docs/JarUnity/index.html)

## Introduction
This aar provides some methods to get the battery status.

## Class name
```
com.picovr.libs.BatteryListener
```

## Interface
```
public boolean isCharging()
public float getBatteryPercent()
```

## Sample Code
```
public void GetBatteryPercent()
{
    AndroidJavaObject context = new AndroidJavaClass("com.unity3d.player.UnityPlayer").GetStatic<AndroidJavaObject>("currentActivity");
    AndroidJavaObject listener = new AndroidJavaObject("com.picovr.libs.BatteryListener", context, "BatteryListener", "BatteryCallBack");
    float percent = listener.Call<float>("getBatteryPercent");
    Debug.Log("====TAG==== GetBatteryPercent  " + percent);
}

public void IsCharging()
{
    AndroidJavaObject context = new AndroidJavaClass("com.unity3d.player.UnityPlayer").GetStatic<AndroidJavaObject>("currentActivity");
    AndroidJavaObject listener = new AndroidJavaObject("com.picovr.libs.BatteryListener", context, "BatteryListener", "BatteryCallBack");
    bool isCharging = listener.Call<bool>("isCharging");
    Debug.Log("====TAG==== IsCharging  " + isCharging);
}

```
