package com.llw.ble.provider;

import com.llw.ble.ResourceTable;
import com.llw.ble.utils.BleUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;
import ohos.bluetooth.ble.GattCharacteristic;

import java.util.List;

/**
 * 特性提供者
 */
public class CharacteristicProvider extends BaseItemProvider {

    private final List<GattCharacteristic> characteristicList;
    private final AbilitySlice slice;
    private final OperateCallback operateCallback;

    public CharacteristicProvider(List<GattCharacteristic> list, AbilitySlice slice, OperateCallback operateCallback) {
        this.characteristicList = list;
        this.slice = slice;
        this.operateCallback = operateCallback;
    }

    @Override
    public int getCount() {
        return characteristicList == null ? 0 : characteristicList.size();
    }

    @Override
    public Object getItem(int position) {
        if (characteristicList != null && position >= 0 && position < characteristicList.size()) {
            return characteristicList.get(position);
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
        CharacteristicHolder holder;

        GattCharacteristic characteristic = characteristicList.get(position);
        if (component == null) {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_item_characteristic, null, false);
            holder = new CharacteristicHolder(cpt);
            //将获取到的子组件信息绑定到列表项的实例中
            cpt.setTag(holder);
        } else {
            cpt = component;
            // 从缓存中获取到列表项实例后，直接使用绑定的子组件信息进行数据填充。
            holder = (CharacteristicHolder) cpt.getTag();
        }

        String characteristicsName = BleUtils.getCharacteristicsName(characteristic.getUuid());
        holder.txCharacterName.setText(characteristicsName);
        holder.txUuid.setText(BleUtils.getShortUUID(characteristic.getUuid()));
        holder.txUuid.setText(characteristicsName.equals(BleUtils.UNKNOWN_CHARACTERISTICS) ? characteristic.getUuid().toString() : BleUtils.getShortUUID(characteristic.getUuid()));

        List<String> properties = BleUtils.getProperties(characteristic.getProperties());
        //加载属性
        holder.lcProperty.setItemProvider(new PropertyProvider(properties, slice));
        //属性列表点击
        holder.lcProperty.setItemClickedListener((listContainer, component1, propertyPosition, l) -> {
            if (operateCallback != null) {
                //属性操作回调
                operateCallback.onPropertyOperate(characteristic, properties.get(propertyPosition));
            }
        });
        //加载特性下的描述
        if (characteristic.getDescriptors().size() > 0) {
            holder.lcDescriptor.setItemProvider(new DescriptorProvider(characteristic.getDescriptors(), slice));
        } else {
            holder.layDescriptor.setVisibility(Component.HIDE);
        }

        return cpt;
    }

    /**
     * 用于保存列表项的子组件信息
     */
    public static class CharacteristicHolder {
        Text txCharacterName;
        Text txUuid;
        ListContainer lcProperty;
        DirectionalLayout layDescriptor;
        ListContainer lcDescriptor;

        public CharacteristicHolder(Component component) {
            txCharacterName = (Text) component.findComponentById(ResourceTable.Id_tx_character_name);
            txUuid = (Text) component.findComponentById(ResourceTable.Id_tx_uuid);
            lcProperty = (ListContainer) component.findComponentById(ResourceTable.Id_lc_property);
            layDescriptor = (DirectionalLayout) component.findComponentById(ResourceTable.Id_lay_descriptors);
            lcDescriptor = (ListContainer) component.findComponentById(ResourceTable.Id_lc_descriptor);
        }
    }
}
