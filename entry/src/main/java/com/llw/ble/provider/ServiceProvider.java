package com.llw.ble.provider;

import com.llw.ble.ResourceTable;
import com.llw.ble.utils.BleUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;
import ohos.bluetooth.ble.GattService;

import java.util.List;
import java.util.Locale;

/**
 * 服务提供者
 */
public class ServiceProvider extends BaseItemProvider {

    private final List<GattService> serviceList;
    private final AbilitySlice slice;

    public ServiceProvider(List<GattService> list, AbilitySlice slice) {
        this.serviceList = list;
        this.slice = slice;
    }

    @Override
    public int getCount() {
        return serviceList == null ? 0 : serviceList.size();
    }

    @Override
    public Object getItem(int position) {
        if (serviceList != null && position >= 0 && position < serviceList.size()) {
            return serviceList.get(position);
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
        ServiceHolder holder;

        GattService service = serviceList.get(position);
        if (component == null) {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_item_service, null, false);
            holder = new ServiceHolder(cpt);
            //将获取到的子组件信息绑定到列表项的实例中
            cpt.setTag(holder);
        } else {
            cpt = component;
            // 从缓存中获取到列表项实例后，直接使用绑定的子组件信息进行数据填充。
            holder = (ServiceHolder) cpt.getTag();
        }

        holder.txServiceName.setText(BleUtils.getServiceName(service.getUuid()));
        holder.txUuid.setText(BleUtils.getServiceUUID(service.getUuid()));

        return cpt;
    }

    /**
     * 用于保存列表项的子组件信息
     */
    public static class ServiceHolder {
        Text txServiceName;
        Text txUuid;
        public ServiceHolder(Component component) {
            txServiceName = (Text) component.findComponentById(ResourceTable.Id_tx_service_name);
            txUuid = (Text) component.findComponentById(ResourceTable.Id_tx_uuid);
        }
    }
}
