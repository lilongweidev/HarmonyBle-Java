package com.llw.ble.provider;

import com.llw.ble.ResourceTable;
import com.llw.ble.core.BleDevice;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;
import java.util.List;
import java.util.Locale;

/**
 * 扫描设备提供商
 */
public class ScanDeviceProvider extends BaseItemProvider {

    private final List<BleDevice> deviceList;
    private final AbilitySlice slice;

    public ScanDeviceProvider(List<BleDevice> list, AbilitySlice slice) {
        this.deviceList = list;
        this.slice = slice;
    }

    @Override
    public int getCount() {
        return deviceList == null ? 0 : deviceList.size();
    }

    @Override
    public Object getItem(int position) {
        if (deviceList != null && position >= 0 && position < deviceList.size()) {
            return deviceList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component component, ComponentContainer componentContainer) {
        final Component cpt;
        ScanDeviceHolder holder;

        BleDevice device = deviceList.get(position);
        if (component == null) {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_item_scan_device, null, false);
            holder = new ScanDeviceHolder(cpt);
            //将获取到的子组件信息绑定到列表项的实例中
            cpt.setTag(holder);
        } else {
            cpt = component;
            // 从缓存中获取到列表项实例后，直接使用绑定的子组件信息进行数据填充。
            holder = (ScanDeviceHolder) cpt.getTag();
        }

        holder.deviceName.setText(device.getRealName());
        holder.deviceAddress.setText(device.getMacAddress());
        holder.rssi.setText(String.format(Locale.getDefault(), "%d dBm", device.getRssi()));

        return cpt;
    }

    /**
     * 用于保存列表项的子组件信息
     */
    public static class ScanDeviceHolder {
        Text deviceName;
        Text deviceAddress;
        Text rssi;
        public ScanDeviceHolder(Component component) {
            deviceName = (Text) component.findComponentById(ResourceTable.Id_device_name);
            deviceAddress = (Text) component.findComponentById(ResourceTable.Id_device_address);
            rssi = (Text) component.findComponentById(ResourceTable.Id_rssi);
        }
    }
}
