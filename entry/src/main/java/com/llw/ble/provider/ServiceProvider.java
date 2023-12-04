package com.llw.ble.provider;

import com.llw.ble.ResourceTable;
import com.llw.ble.utils.BleUtils;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;
import ohos.agp.components.element.VectorElement;
import ohos.bluetooth.ble.GattService;

import java.util.List;

/**
 * 服务提供者
 */
public class ServiceProvider extends BaseItemProvider {

    private final List<GattService> serviceList;
    private final AbilitySlice slice;

    private OperateCallback operateCallback;

    public void setOperateCallback(OperateCallback operateCallback) {
        this.operateCallback = operateCallback;
    }

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

        holder.itemService.setClickedListener(component1 -> {
            boolean isShow = holder.lcCharacteristics.getVisibility() == Component.VISIBLE;
            //显示特性列表
            holder.lcCharacteristics.setVisibility(isShow ? Component.HIDE : Component.VISIBLE);
            //更换图标
            VectorElement vectorElement = new VectorElement(slice.getContext(), isShow ? ResourceTable.Graphic_ic_right_24 : ResourceTable.Graphic_ic_down_24);
            holder.ivState.setBackground(vectorElement);
            //刷新Item高度，这个很重要，不加会造成内容覆盖。
            notifyDataSetItemChanged(position);
        });
        //加载特性 设置属性回调
        holder.lcCharacteristics.setItemProvider(new CharacteristicProvider(service.getCharacteristics(), slice, operateCallback));

        holder.txServiceName.setText(BleUtils.getServiceName(service.getUuid()));
        holder.txUuid.setText(BleUtils.getShortUUID(service.getUuid()));

        return cpt;
    }

    /**
     * 用于保存列表项的子组件信息
     */
    public static class ServiceHolder {
        DependentLayout itemService;
        Text txServiceName;
        Text txUuid;
        Image ivState;
        ListContainer lcCharacteristics;
        public ServiceHolder(Component component) {
            itemService = (DependentLayout) component.findComponentById(ResourceTable.Id_item_service);
            txServiceName = (Text) component.findComponentById(ResourceTable.Id_tx_service_name);
            txUuid = (Text) component.findComponentById(ResourceTable.Id_tx_uuid);
            ivState = (Image) component.findComponentById(ResourceTable.Id_iv_state);
            lcCharacteristics = (ListContainer) component.findComponentById(ResourceTable.Id_lc_characteristics);
        }
    }
}
