package com.llw.ble.slice;

import com.llw.ble.BleApp;
import com.llw.ble.ResourceTable;
import com.llw.ble.core.BleCore;
import com.llw.ble.core.BleDevice;
import com.llw.ble.core.scan.ScanCallback;
import com.llw.ble.provider.ScanDeviceProvider;
import com.llw.ble.utils.LogUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Component;
import ohos.agp.components.ListContainer;
import ohos.agp.components.Text;
import ohos.bluetooth.BluetoothHost;
import ohos.bluetooth.ble.BleScanResult;
import ohos.bundle.IBundleManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ohos.agp.components.Component.HIDE;
import static ohos.agp.components.Component.VISIBLE;

/**
 * 扫描
 */
public class ScanSlice extends AbilitySlice implements ScanCallback {

    private final String TAG = ScanSlice.class.getSimpleName();
    private BleCore bleCore;

    private Text txScanStatus;
    private ListContainer lcDevice;

    private final List<BleDevice> mList = new ArrayList<>();
    private ScanDeviceProvider provider;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_slice_scan);

        bleCore = BleApp.getBleCore();
        bleCore.setPhyScanCallback(this);
        LogUtils.LogD(TAG, "onStart");

        txScanStatus = (Text) findComponentById(ResourceTable.Id_tx_scan_status);
        lcDevice = (ListContainer) findComponentById(ResourceTable.Id_lc_device);

        //点击监听
        txScanStatus.setClickedListener(component -> {
            if (bleCore.isScanning()) {
                stopScan();//扫描开关停止扫描
            } else {
                startScan();//开始扫描
            }
        });

        provider = new ScanDeviceProvider(mList, this);
        lcDevice.setItemProvider(provider);
        //列表item点击监听
        lcDevice.setItemClickedListener((listContainer, component, position, id) -> {
            //设置设备
            bleCore.setDevice(mList.get(position).getDevice());
            Intent jumpIntent = new Intent();
            Operation operation = new Intent.OperationBuilder()
                    .withAction("action.main")
                    .withDeviceId("")
                    .withBundleName("com.llw.ble")
                    .withAbilityName("com.llw.ble.MainAbility")
                    .build();
            jumpIntent.setOperation(operation);
            startAbility(jumpIntent);
        });
    }

    @Override
    public void onActive() {
        // 判断是否打开蓝牙
        if (!bleCore.isEnableBt()) {
            //打开蓝牙
            bleCore.enableBt();
            return;
        }
        // 是否获取定位权限
        String locationPermission = "ohos.permission.LOCATION";
        if (verifySelfPermission(locationPermission) != IBundleManager.PERMISSION_GRANTED) {
            requestPermissionsFromUser(new String[]{locationPermission}, 100);
            return;
        }
        // 是否在扫描中
        if (!bleCore.isScanning()) {
            startScan();
        }
        LogUtils.LogD(TAG, "onActive");
    }

    @Override
    protected void onInactive() {
        LogUtils.LogD(TAG, "onInactive");
    }

    @Override
    public void onForeground(Intent intent) {
        LogUtils.LogD(TAG, "onForeground");
    }

    @Override
    protected void onBackground() {
        LogUtils.LogD(TAG, "onBackground");
    }

    @Override
    protected void onStop() {
        LogUtils.LogD(TAG, "onStop");
    }

    private void startScan() {
        mList.clear();
        provider.notifyDataChanged();
        bleCore.startScan();
        txScanStatus.setText("停止");
        LogUtils.LogD(TAG,"开始扫描设备！");
    }

    private void stopScan() {
        bleCore.stopScan();
        txScanStatus.setText("搜索");
        LogUtils.LogD(TAG,"已经停止扫描设备！");
    }

    private int findIndex(BleDevice bleDevice, List<BleDevice> deviceList) {
        int index = 0;

        for (final BleDevice devi : deviceList) {
            if (bleDevice.getMacAddress().equals(devi.getDevice().getDeviceAddr())) return index;
            index += 1;
        }
        return -1;
    }

    @Override
    public void onScanResult(BleScanResult result) {
        BleDevice bleDevice = new BleDevice(result);

        int index = findIndex(bleDevice, mList);
        if (index == -1) {
            //添加新设备
            mList.add(bleDevice);
        } else {
            //更新已有设备的rssi和时间戳
            mList.get(index).setRssi(bleDevice.getRssi());
        }
        getUITaskDispatcher().syncDispatch(() -> provider.notifyDataChanged());
    }
}
