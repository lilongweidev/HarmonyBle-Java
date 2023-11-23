package com.llw.ble.core;

import ohos.aafwk.ability.AbilityPackage;
import ohos.bluetooth.ble.GattService;

import java.util.List;

/**
 * Ble回调
 */
public interface BleCallback {

    /**
     * 设备的所有信息
     *
     * @param info 信息
     */
    void deviceInfo(String info);

    /**
     * 连接状态
     *
     * @param state true or false
     */
    void connectionStateChange(boolean state);

    /**
     * 发现服务
     *
     * @param services 服务列表
     */
    void servicesDiscovered(List<GattService> services);
}
