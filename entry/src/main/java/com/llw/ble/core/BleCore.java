package com.llw.ble.core;

import com.llw.ble.core.scan.BleScan;
import com.llw.ble.core.scan.ScanCallback;
import com.llw.ble.utils.LogUtils;
import ohos.app.Context;
import ohos.bluetooth.BluetoothHost;
import ohos.bluetooth.ProfileBase;
import ohos.bluetooth.ble.BlePeripheralCallback;
import ohos.bluetooth.ble.BlePeripheralDevice;

public class BleCore {

    private static final String TAG = BleCore.class.getSimpleName();
    private static volatile BleCore mInstance;
    private final BleScan bleScan;
    private final BluetoothHost mBluetoothHost;
    private final BleDeviceCallback mBleDeviceCallback;
    private BleCallback bleCallback;
    private BlePeripheralDevice mDevice;
    private boolean mIsConnected;

    public BleCore(Context context) {
        //蓝牙扫描
        bleScan = BleScan.getInstance(context);
        // 获取蓝牙本机管理对象
        mBluetoothHost = BluetoothHost.getDefaultHost(context);
        //蓝牙设备类
        mBleDeviceCallback = new BleDeviceCallback();
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

    public void setBleCallback(BleCallback bleCallback) {
        this.bleCallback = bleCallback;
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

    public void setDevice(BlePeripheralDevice device) {
        mDevice = device;
    }

    public boolean isConnected() {
        return mIsConnected;
    }

    public void connect() {
        if (mDevice == null) return;
        deviceInfo("连接设备...");
        mDevice.connect(false, mBleDeviceCallback);
    }

    public void disconnect() {
        if (mDevice == null) return;
        deviceInfo("断开连接设备...");
        mDevice.disconnect();
    }

    private void deviceInfo(String info) {
        if (bleCallback != null) {
            bleCallback.deviceInfo(info);
        }
    }

    private void connectState(boolean state) {
        mIsConnected = state;
        if (bleCallback != null) {
            bleCallback.connectionStateChange(state);

        }
    }


    private class BleDeviceCallback extends BlePeripheralCallback {

        /**
         * 连接状态改变
         *
         * @param connectionState 状态码
         */
        @Override
        public void connectionStateChangeEvent(int connectionState) {
            String address = mDevice.getDeviceAddr();
            if (connectionState == ProfileBase.STATE_CONNECTED) {
                deviceInfo("连接成功：" + address);
                connectState(true);
                deviceInfo("发现服务中... ");
                mDevice.discoverServices();//发现服务
            } else if (connectionState == ProfileBase.STATE_DISCONNECTED) {
                deviceInfo("断开连接成功：" + address);
                connectState(false);
                mDevice.close();
            }
        }

        /**
         * 发现服务
         *
         * @param status 状态
         */
        @Override
        public void servicesDiscoveredEvent(int status) {
            if (status == BlePeripheralDevice.OPERATION_SUCC) {
                deviceInfo("发现" + mDevice.getServices().size() + "服务");
                if (bleCallback != null) {
                    bleCallback.servicesDiscovered(mDevice.getServices());
                }
            }
        }
    }
}
