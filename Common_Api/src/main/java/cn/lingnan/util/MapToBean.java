package cn.lingnan.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MapToBean {
    public static Object transMap2Bean(Map<String, Object> map, Object obj) {
        try {
            //1.获取bean信息
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor prop : properties) {
                String key = prop.getName();

                if (map.containsKey(key) && map.get(key) != null && map.get(key) != "") {
                    Object value = map.get(key);
                    Method setMethod = prop.getWriteMethod();
                    if (prop.getPropertyType().equals(Long.class)) {
                        value = Long.parseLong(value.toString());
                    }
                    if (prop.getPropertyType().equals(Integer.class)) {
                        value = Integer.parseInt(value.toString());
                    }
                    setMethod.invoke(obj, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Map<String, Object> transBean2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        try {
            //1.获取bean信息
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
            if (properties != null && properties.length > 0) {
                for (PropertyDescriptor prop : properties) {
                    //2.得到属性名
                    String name = prop.getName();
                    //3.过滤class属性
                    if (!"class".equals(name)) {
                        //4.得到属性的get方法
                        Method getter = prop.getReadMethod();
                        //5.获取属性值
                        Object value = getter.invoke(obj);
                        //6.放入map中
                        map.put(name, value);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
