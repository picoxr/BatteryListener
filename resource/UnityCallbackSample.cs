using System.Collections;
using System.Collections.Generic;
using UnityEngine;

// This script demonstrates how to get the battery status through callback in Unity
public class UnityCallbackSample : MonoBehaviour
{
    void Start()
    {
        AndroidJavaObject context = new AndroidJavaClass("com.unity3d.player.UnityPlayer").GetStatic<AndroidJavaObject>("currentActivity");
        AndroidJavaObject listener = new AndroidJavaObject("com.picovr.libs.BatteryListener", context, gameObject.name, "BatteryCallBack");
    }

    void BatteryCallBack(string state)
    {
        Debug.LogError("====TAG==== BatteryCallBack  " + state);
    }
}
