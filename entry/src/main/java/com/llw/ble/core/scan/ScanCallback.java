package com.llw.ble.core.scan;

import ohos.bluetooth.ble.BleScanResult;

import java.util.List;

/**
 * 扫描回调
 */
public interface ScanCallback {

    void onScanResult(BleScanResult result);

    default void onGroupScanResultsEvent(List<BleScanResult> results){}

    default void onScanFailed(String failed){}
}
