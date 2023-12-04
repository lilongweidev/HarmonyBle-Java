package com.llw.ble.provider;

import ohos.bluetooth.ble.GattCharacteristic;
import ohos.bluetooth.ble.GattService;

import java.util.List;

/**
 * 操作回调
 */
public interface OperateCallback {

    /**
     * 属性操作
     */
    void onPropertyOperate(GattCharacteristic characteristic, String operateName);
}
