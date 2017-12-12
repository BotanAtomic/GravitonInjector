package org.graviton.injector.binder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

import static org.graviton.injector.utils.ClassUtils.getCompatibleConstructor;

public class BindingBuilder<T> {
    private final Class<T> objectClass;
    private final Binder binder;

    private T instance;

    private BinderType binderType;

    private String name;

    BindingBuilder(Class<T> clazz, Binder binder) {
        this.objectClass = clazz;
        this.binder = binder;
    }

    public BindingBuilder<T> toName(String name) {
        this.name = name;
        return this;
    }

    public void asSingleton() {
        this.binderType = BinderType.SINGLETON;
    }

    public void toInstance(T instance) {
        this.instance = instance;
        this.binderType = BinderType.INSTANCE;
    }

    public T get() {
        if (instance == null && binderType == BinderType.SINGLETON)
            return generate();
        return instance;
    }

    private T generate() {
        try {
            Constructor constructor;
            if ((constructor = getCompatibleConstructor(Arrays.asList(objectClass.getConstructors()))) != null) {
                Parameter[] parameters = constructor.getParameters();
                Map<Integer, Object> parametersObject = new TreeMap<>();
                IntStream.range(0, parameters.length).boxed().forEach(i -> parametersObject.put(i, binder.getInstance(parameters[i].getType())));
                objectClass.getDeclaredConstructor(constructor.getParameterTypes()).newInstance(parametersObject.values().toArray(new Object[]{}));
                return null;
            } else
                return instance = objectClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace(); //TODO : exception
            return null;
        }
    }

    public Class<T> abstractClass() {
        return objectClass;
    }

    public String name() {
        return name;
    }

}
