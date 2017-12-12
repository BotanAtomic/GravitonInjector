package org.graviton.injector.binder;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Binder {
    protected List<BindingBuilder<?>> builders = new CopyOnWriteArrayList<>();

    public <T> BindingBuilder bind(Class<T> clazz) {
        return register(new BindingBuilder<>(clazz, this));
    }

    private <T> BindingBuilder<T> register(BindingBuilder<T> bindingBuilder) {
        builders.add(bindingBuilder);
        return bindingBuilder;
    }


    public <T> T getInstance(Class<T> clazz) {
        BindingBuilder bindingBuilder = builders.stream().filter(builder -> builder.abstractClass().equals(clazz)).findAny().orElse(null);
        return (T) bindingBuilder.get();
    }

    public <T> T getInstance(Class<T> clazz, String name) {
        BindingBuilder bindingBuilder = builders.stream().filter(builder -> builder.abstractClass().equals(clazz) &&
                name == null || name.equals(builder.name())).findAny().orElse(null);

        return (T) bindingBuilder.get();
    }

}
