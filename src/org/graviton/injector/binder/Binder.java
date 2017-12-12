package org.graviton.injector.binder;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Binder {
    protected List<BindingBuilder<?>> builders = new CopyOnWriteArrayList<>();

    public <T> BindingBuilder bind(Class<T> clazz) {
        return register(new BindingBuilder<>(clazz));
    }

    private <T> BindingBuilder<T> register(BindingBuilder<T> bindingBuilder) {
        builders.add(bindingBuilder);
        return bindingBuilder;
    }

}
