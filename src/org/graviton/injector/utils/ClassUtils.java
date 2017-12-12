package org.graviton.injector.utils;

import org.graviton.injector.api.Inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ClassUtils {

    public static Constructor getCompatibleConstructor(List<Constructor> constructors) {
        return constructors.stream().filter(constructor -> constructor.isAnnotationPresent(Inject.class)).findAny().orElse(null);
    }

    public static List<Method> getMethods(Object clazz) {
        return Arrays.asList(clazz.getClass().getMethods());
    }

    public static List<Field> getFields(Object clazz) {
        return Arrays.asList(clazz.getClass().getDeclaredFields());
    }

    public static void setField(Object clazz, Object instance, Field field) {
        try {
            field.set(clazz, instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object invoke(Method method, Object instance) {
        try {
            return method.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
