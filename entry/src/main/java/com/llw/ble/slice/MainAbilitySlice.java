package com.llw.ble.slice;

import com.llw.ble.BleApp;
import com.llw.ble.ResourceTable;
import com.llw.ble.core.BleCallback;
import com.llw.ble.core.BleCore;
import com.llw.ble.provider.OperateCallback;
import com.llw.ble.provider.ServiceProvider;
import com.llw.ble.utils.LogUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Component;
import ohos.agp.components.DirectionalLayout;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.utils.Color;
import ohos.agp.utils.LayoutAlignment;
import ohos.agp.window.dialog.ToastDialog;
import ohos.bluetooth.ble.GattCharacteristic;
import ohos.bluetooth.ble.GattService;

import java.util.ArrayList;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice implements BleCallback, ListContainer.ItemClickedListener, OperateCallback {

    private static final String TAG = MainAbilitySlice.class.getSimpleName();
    private Text txDisconnect;
    private Text txDeviceInfo;
    private ListContainer lcService;
    private BleCore bleCore;
    private final List<GattService> serviceList = new ArrayList<>();
    private ServiceProvider serviceProvider;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        txDisconnect = (Text) findComponentById(ResourceTable.Id_tx_disconnect);
        txDeviceInfo = (Text) findComponentById(ResourceTable.Id_tx_device_info);
        lcService = (ListContainer) findComponentById(ResourceTable.Id_lc_service);

        bleCore = BleApp.getBleCore();
        bleCore.setBleCallback(this);
        //连接设备
        bleCore.connect();

        txDisconnect.setClickedListener(component -> {
            if (bleCore.isConnected()) {
                bleCore.disconnect();
            } else {
                bleCore.connect();
            }
        });

        serviceProvider = new ServiceProvider(serviceList, this);
        serviceProvider.setOperateCallback(this);
        lcService.setItemProvider(serviceProvider);
        lcService.setItemClickedListener(this);
    }

    @Override
    public void deviceInfo(String info) {
        getUITaskDispatcher().asyncDispatch(() -> {
            LogUtils.LogD(TAG, info);
            txDeviceInfo.setText(info);
        });
    }

    @Override
    public void connectionStateChange(boolean state) {
        getUITaskDispatcher().asyncDispatch(() -> txDisconnect.setText(state ? "断开连接" : "连接"));
    }

    @Override
    public void servicesDiscovered(List<GattService> services) {
        getUITaskDispatcher().asyncDispatch(() -> {
            serviceList.clear();
            serviceList.addAll(services);
            serviceProvider.notifyDataChanged();
        });
    }

    @Override
    public void onItemClicked(ListContainer listContainer, Component component, int position, long id) {
        showMsg(serviceList.get(position).getUuid().toString());
    }

    private void showMsg(String msg) {
        ToastDialog toastDialog = new ToastDialog(getContext());
        toastDialog.setSize(DirectionalLayout.LayoutConfig.MATCH_CONTENT, DirectionalLayout.LayoutConfig.MATCH_CONTENT);
        toastDialog.setDuration(2000);
        toastDialog.setText(msg);
        toastDialog.setAlignment(LayoutAlignment.CENTER);
        Text toastText = (Text) toastDialog.getComponent();
        if (toastText != null) {
            toastText.setMultipleLine(true);
            toastText.setTextSize(14, Text.TextSizeType.FP);
            toastText.setTextColor(Color.WHITE);
            toastText.setPadding(40, 20, 40, 20);
            ShapeElement toastBackground = new ShapeElement();
            toastBackground.setRgbColor(new RgbColor(24, 196, 124));
            toastBackground.setCornerRadius(60f);
            toastText.setBackground(toastBackground);
        }
        toastDialog.show();
    }

    @Override
    public void onPropertyOperate(GattCharacteristic characteristic, String operateName) {

    }
}
