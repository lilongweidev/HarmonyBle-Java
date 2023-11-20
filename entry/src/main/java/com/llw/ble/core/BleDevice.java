package com.llw.ble.core;

import ohos.bluetooth.ble.BlePeripheralDevice;
import ohos.bluetooth.ble.BleScanResult;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * 蓝牙
 */
public class BleDevice {

    private String realName = "Unknown device"; //蓝牙设备真实名称
    private String macAddress;                  //地址
    private int rssi;                           //信号强度
    private BlePeripheralDevice device;         //设备

    public BleDevice(BleScanResult scanResult) {
        this.device = scanResult.getPeripheralDevice();
        this.macAddress = device.getDeviceAddr();
        String name = device.getDeviceName().get();
        if (name != null || !name.isEmpty()) {
            this.realName = name;
        }
        this.rssi = scanResult.getRssi();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public BlePeripheralDevice getDevice() {
        return device;
    }

    public void setDevice(BlePeripheralDevice device) {
        this.device = device;
    }
}
