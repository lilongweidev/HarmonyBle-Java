package com.llw.ble;

import com.llw.ble.core.BleCore;
import ohos.aafwk.ability.AbilityPackage;

public class BleApp extends AbilityPackage {
    private static BleCore bleCore;

    @Override
    public void onInitialize() {
        super.onInitialize();

        bleCore = BleCore.getInstance(getContext());
    }

    public static BleCore getBleCore() {
        return bleCore;
    }
}
