package com.llw.ble.provider;

import com.llw.ble.ResourceTable;
import com.llw.ble.utils.BleUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;
import ohos.bluetooth.ble.GattDescriptor;

import java.util.List;

/**
 * 描述提供者
 */
public class DescriptorProvider extends BaseItemProvider {

    private final List<GattDescriptor> descriptorList;
    private final AbilitySlice slice;

    public DescriptorProvider(List<GattDescriptor> list, AbilitySlice slice) {
        this.descriptorList = list;
        this.slice = slice;
    }

    @Override
    public int getCount() {
        return descriptorList == null ? 0 : descriptorList.size();
    }

    @Override
    public Object getItem(int position) {
        if (descriptorList != null && position >= 0 && position < descriptorList.size()) {
            return descriptorList.get(position);
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
        DescriptorHolder holder;

        GattDescriptor descriptor = descriptorList.get(position);
        if (component == null) {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_item_descriptor, null, false);
            holder = new DescriptorHolder(cpt);
            //将获取到的子组件信息绑定到列表项的实例中
            cpt.setTag(holder);
        } else {
            cpt = component;
            // 从缓存中获取到列表项实例后，直接使用绑定的子组件信息进行数据填充。
            holder = (DescriptorHolder) cpt.getTag();
        }

        String descriptorName = BleUtils.getDescriptorName(descriptor.getUuid());
        holder.txDescriptorName.setText(descriptorName);
        holder.txUuid.setText(descriptorName.equals(BleUtils.UNKNOWN_DESCRIPTOR) ? descriptor.getUuid().toString() : BleUtils.getShortUUID(descriptor.getUuid()));
        return cpt;
    }

    /**
     * 用于保存列表项的子组件信息
     */
    public static class DescriptorHolder {
        Text txDescriptorName;
        Text txUuid;
        ListContainer lcProperty;

        public DescriptorHolder(Component component) {
            txDescriptorName = (Text) component.findComponentById(ResourceTable.Id_tx_descriptor_name);
            txUuid = (Text) component.findComponentById(ResourceTable.Id_tx_uuid);
            lcProperty = (ListContainer) component.findComponentById(ResourceTable.Id_lc_property);
        }
    }
}
