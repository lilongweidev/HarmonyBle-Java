package com.llw.ble.provider;

import com.llw.ble.ResourceTable;
import com.llw.ble.utils.BleUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;
import ohos.bluetooth.ble.GattService;

import java.util.List;

/**
 * 属性提供者
 */
public class PropertyProvider extends BaseItemProvider {

    private final List<String> propertyList;
    private final AbilitySlice slice;

    public PropertyProvider(List<String> list, AbilitySlice slice) {
        this.propertyList = list;
        this.slice = slice;
    }

    @Override
    public int getCount() {
        return propertyList == null ? 0 : propertyList.size();
    }

    @Override
    public Object getItem(int position) {
        if (propertyList != null && position >= 0 && position < propertyList.size()) {
            return propertyList.get(position);
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
        PropertyHolder holder;

        if (component == null) {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_item_property, null, false);
            holder = new PropertyHolder(cpt);
            //将获取到的子组件信息绑定到列表项的实例中
            cpt.setTag(holder);
        } else {
            cpt = component;
            // 从缓存中获取到列表项实例后，直接使用绑定的子组件信息进行数据填充。
            holder = (PropertyHolder) cpt.getTag();
        }

        holder.txProperty.setText(propertyList.get(position));
        return cpt;
    }

    /**
     * 用于保存列表项的子组件信息
     */
    public static class PropertyHolder {
        Text txProperty;
        public PropertyHolder(Component component) {
            txProperty = (Text) component.findComponentById(ResourceTable.Id_tx_property);
        }
    }
}
