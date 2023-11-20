package com.llw.ble.core;

import com.llw.ble.core.scan.BleScan;
import com.llw.ble.core.scan.ScanCallback;
import ohos.app.Context;
import ohos.bluetooth.BluetoothHost;

public class BleCore  {

    private static volatile BleCore mInstance;
    private final BleScan bleScan;

    private final BluetoothHost mBluetoothHost;

    public BleCore(Context context) {
        //mBleCallback = new BleCallback();
        //蓝牙扫描
        bleScan = BleScan.getInstance(context);
        // 获取蓝牙本机管理对象
        mBluetoothHost = BluetoothHost.getDefaultHost(context);
    }

    public static BleCore getInstance(Context context) {
        if (mInstance == null) {
            synchronized (BleCore.class) {
                if (mInstance == null) {
                    mInstance = new BleCore(context);
                }
            }
        }
        return mInstance;
    }

    public void setPhyScanCallback(ScanCallback scanCallback) {
        bleScan.setScanCallback(scanCallback);
    }

    public boolean isScanning() {
        return bleScan.isScanning();
    }

    public void startScan() {
        bleScan.startScan();
    }

    public void stopScan() {
        bleScan.stopScan();
    }

    public boolean isEnableBt() {
        return mBluetoothHost.getBtState() == BluetoothHost.STATE_ON;
    }

    public void enableBt() {
        mBluetoothHost.enableBt();
    }
}
