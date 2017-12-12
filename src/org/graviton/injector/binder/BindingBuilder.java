package org.graviton.injector.binder;

public class BindingBuilder<T> {
    private final Class<T> objectClass;

    private T instance;

    private BinderType binderType;

    private String name;

    BindingBuilder(Class<T> clazz) {
        this.objectClass = clazz;
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
