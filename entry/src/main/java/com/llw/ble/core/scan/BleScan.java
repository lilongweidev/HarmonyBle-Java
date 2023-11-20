package com.llw.ble.core.scan;

import ohos.app.Context;
import ohos.bluetooth.ble.BleCentralManager;
import ohos.bluetooth.ble.BleCentralManagerCallback;
import ohos.bluetooth.ble.BleScanFilter;
import ohos.bluetooth.ble.BleScanResult;

import java.util.List;

/**
 * BLE蓝牙扫描类
 */
public class BleScan {

    private final BleCentralManager centralManager;

    private boolean isScanning = false;

    private ScanCallback scanCallback;

    // 创建扫描过滤器然后开始扫描
    private List<BleScanFilter> filters;

    private static volatile BleScan mInstance;

    //初始化
    public static BleScan getInstance(Context context) {
        if (mInstance == null) {
            synchronized (BleScan.class) {
                if (mInstance == null) {
                    mInstance = new BleScan(context);
                }
            }
        }
        return mInstance;
    }

    public BleScan(Context context) {
        BleScanCallback centralManagerCallback = new BleScanCallback();
        centralManager = new BleCentralManager(context, centralManagerCallback);
    }

    /**
     * 当前是否正在扫描
     * @return true 扫描中，false 未扫描
     */
    public boolean isScanning() {
        return isScanning;
    }

    /**
     * 设置过滤信息
     * @param filters 蓝牙扫描过滤列表
     */
    public void setFilters(List<BleScanFilter> filters) {
        this.filters = filters;
    }

    /**
     * 设置扫描回调，页面需要实现才能获取扫描到的设备
     * @param scanCallback 扫描回调
     */
    public void setScanCallback(ScanCallback scanCallback) {
        this.scanCallback = scanCallback;
    }

    /**
     * 开始扫描
     */
    public void startScan() {
        if (centralManager == null) {
            localScanFailed("Bluetooth not turned on.");
            return;
        }
        centralManager.startScan(filters);
        isScanning = true;
    }

    /**
     * 停止扫描
     */
    public void stopScan() {
        if (!isScanning) {
            localScanFailed("Not currently scanning, your stop has no effect.");
            return;
        }
        centralManager.stopScan();
        isScanning = false;
    }

    /**
     * 实现扫描回调
     */
    public class BleScanCallback implements BleCentralManagerCallback {

        @Override
        public void scanResultEvent(BleScanResult bleScanResult) {
            if (scanCallback != null) {
                scanCallback.onScanResult(bleScanResult);
            }
        }

        @Override
        public void scanFailedEvent(int resultCode) {
            if (scanCallback != null) {
                scanCallback.onScanFailed(String.valueOf(resultCode));
            }
        }

        @Override
        public void groupScanResultsEvent(final List<BleScanResult> scanResults) {
            // 对扫描结果进行处理
            if (scanCallback != null) {
                scanCallback.onGroupScanResultsEvent(scanResults);
            }
        }
    }

    /**
     * 本地扫描失败处理
     * @param failed 错误信息
     */
    private void localScanFailed(String failed) {
        if (scanCallback != null) {
            scanCallback.onScanFailed(failed);
        }
    }
}
