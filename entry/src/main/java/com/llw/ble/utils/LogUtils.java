package com.llw.ble.utils;

import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * 日志工具类
 */
public class LogUtils {

    static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "HarmonyBle");

    private static HiLogLabel logLabel;

    public static void setLogLabel(HiLogLabel logLabel) {
        LogUtils.logLabel = logLabel;
    }

    public static void Log(String content) {
        HiLog.info(LABEL, content);
    }

    public static void LogI(String TAG, String content) {
        HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x00201, TAG);
        HiLog.info(label, content);
    }

    public static void LogD(String TAG, String content) {
        HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x00201, TAG);
        HiLog.debug(label, content);
    }

    public static void LogE(String TAG, String content) {
        HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x00201, TAG);
        HiLog.error(label, content);
    }

    public static void LogW(String TAG, String content) {
        HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x00201, TAG);
        HiLog.warn(label, content);
    }

}
