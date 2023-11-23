package com.llw.ble.utils;

import java.util.UUID;

/**
 * Ble工具类
 */
public class BleUtils {

    public static final String generic = "-0000-1000-8000-00805F9B34FB";

    public static String getServiceUUID(UUID uuid) {
        return "0x" + uuid.toString().substring(4, 8).toUpperCase();
    }

    /**
     * 获取蓝牙服务名称
     *
     * @param uuid UUID
     */
    public static String getServiceName(UUID uuid) {
        String targetUuid = getServiceUUID(uuid);
        switch (targetUuid) {
            case "0x1800":
                return "Generic Access service";
            case "0x1801":
                return "Generic Attribute service";
            case "0x1802":
                return "Immediate Alert service";
            case "0x1803":
                return "Link Loss service";
            case "0x1804":
                return "Tx Power service";
            case "0x1805":
                return "Current Time service";
            case "0x1806":
                return "Reference Time Update service";
            case "0x1807":
                return "Next DST Change service";
            case "0x1808":
                return "Glucose service";
            case "0x1809":
                return "Health Thermometer service";
            case "0x180A":
                return "Device Information service";
            case "0x180D":
                return "Heart Rate service";
            case "0x180E":
                return "Phone Alert Status service";
            case "0x180F":
                return "Battery service";
            case "0x1810":
                return "Blood Pressure service";
            case "0x1811":
                return "Alert Notification service";
            case "0x1812":
                return "Human Interface Device service";
            case "0x1813":
                return "Scan Parameters service";
            case "0x1814":
                return "Running Speed and Cadence service";
            case "0x1815":
                return "Automation IO service";
            case "0x1816":
                return "Cycling Speed and Cadence service";
            case "0x1818":
                return "Cycling Power service";
            case "0x1819":
                return "Location and Navigation service";
            case "0x181A":
                return "Environmental Sensing service";
            case "0x181B":
                return "Body Composition service";
            case "0x181C":
                return "User Data service";
            case "0x181D":
                return "Weight Scale service";
            case "0x181E":
                return "Bond Management service";
            case "0x181F":
                return "Continuous Glucose Monitoring service";
            case "0x1820":
                return "Internet Protocol Support service";
            case "0x1821":
                return "Indoor Positioning service";
            case "0x1822":
                return "Pulse Oximeter service";
            case "0x1823":
                return "HTTP Proxy service";
            case "0x1824":
                return "Transport Discovery service";
            case "0x1825":
                return "Object Transfer service";
            case "0x1826":
                return "Fitness Machine service";
            case "0x1827":
                return "Mesh Provisioning service";
            case "0x1828":
                return "Mesh Proxy service";
            case "0x1829":
                return "Reconnection Configuration service";
            case "0x183A":
                return "Insulin Delivery service";
            case "0x183B":
                return "Binary Sensor service";
            case "0x183C":
                return "Emergency Configuration service";
            case "0x183D":
                return "Authorization Control service";
            case "0x183E":
                return "Physical Activity Monitor service";
            case "0x183F":
                return "Elapsed Time service";
            case "0x1840":
                return "Generic Health Sensor service";
            case "0x1843":
                return "Audio Input Control service";
            case "0x1844":
                return "Volume Control service";
            case "0x1845":
                return "Volume Offset Control service";
            case "0x1846":
                return "Coordinated Set Identification service";
            case "0x1847":
                return "Device Time service";
            case "0x1848":
                return "Media Control service";
            case "0x1849":
                return "Generic Media Control service";
            case "0x184A":
                return "Constant Tone Extension service";
            case "0x184B":
                return "Telephone Bearer service";
            case "0x184C":
                return "Generic Telephone Bearer service";
            case "0x184D":
                return "Microphone Control service";
            case "0x184E":
                return "Audio Stream Control service";
            case "0x184F":
                return "Broadcast Audio Scan service";
            case "0x1850":
                return " Published Audio Capabilities service";
            case "0x1851":
                return "Basic Audio Announcement service";
            case "0x1852":
                return "Broadcast Audio Announcement service";
            case "0x1853":
                return "Common Audio service";
            case "0x1854":
                return "Hearing Access service";
            case "0x1855":
                return "Telephony and Media Audio service";
            case "0x1856":
                return "Public Broadcast Announcement service";
            case "0x1857":
                return "Electronic Shelf Label service";
            default:
                return "Unknown Service";
        }
    }
}
