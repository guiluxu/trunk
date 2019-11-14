package com.wavenet.ding.qpps.utils;

import android.content.ContentValues;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by zoubeiwen on 2019/5/15.
 */

public class ReflectUtil {
    /** 方法--属性复制 */
    public void fieldCopy(Object source, Object target,Class<?> pClass) throws Exception {
        Method[] methods = source.getClass().getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            System.out.println(methodName);
            if (methodName.startsWith("get")) {
                Object value = method.invoke(source, new Object[0]);
                System.out.println(value);
                String setMethodName = methodName.replaceFirst("(get)", "set");
                Method setMethod = pClass.getMethod(setMethodName,
                        method.getReturnType());
                setMethod.invoke(target, value);
            }
        }
    }

    /** 属性字段名、值、数据类型 */
    public static ContentValues getFields(Object object)  throws Exception {
        ContentValues cv = new ContentValues();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String classType = field.getType().toString();
            int lastIndex = classType.lastIndexOf(".");
            classType = classType.substring(lastIndex + 1);
            if ("String".equals(classType)){
                cv.put( field.getName(), (String) field.get(object));
            }else if ("double".equals(classType)){
                cv.put( field.getName(), (Double) field.get(object));
            }else if ("boolean".equals(classType)){
                cv.put( field.getName(), (Boolean) field.get(object));
            }else if ("int".equals(classType)){
                cv.put( field.getName(), (Integer) field.get(object));
            }


//            System.out.println("fieldName：" + field.getName() + ",type:"
//                    + classType + ",value:" + field.get(object));
        }
        return cv;
    }

}
