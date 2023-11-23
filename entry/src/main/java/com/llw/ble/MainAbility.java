package com.llw.ble;

import com.llw.ble.slice.MainAbilitySlice;
import com.llw.ble.slice.ScanSlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.agp.utils.Color;
import ohos.agp.window.service.Window;
import ohos.agp.window.service.WindowManager;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        Window window = WindowManager.getInstance().getTopWindow().get();
        window.setStatusBarColor(Color.getIntColor("#A7D3FF"));
        super.onStart(intent);
        super.setMainRoute(ScanSlice.class.getName());

        // add action for ability
        addActionRoute("action.main", MainAbilitySlice.class.getName());
    }
}
