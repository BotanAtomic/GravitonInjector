package org.graviton.injector.core;


import org.graviton.injector.api.Execute;
import org.graviton.injector.api.Inject;
import org.graviton.injector.api.Provide;
import org.graviton.injector.binder.Binder;
import org.graviton.injector.binder.BindingBuilder;
import org.graviton.injector.modules.AbstractModule;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.graviton.injector.utils.ClassUtils.*;

public class Injector extends Binder {
    private List<AbstractModule> modules = new CopyOnWriteArrayList<>();

    private Injector(AbstractModule... modules) {
        this.modules.addAll(Arrays.asList(modules));

        bind(Injector.class).toInstance(this);
        bind(Binder.class).toInstance(this);

        setupModules();
        injectDependencies();
    }

    public static Injector create(AbstractModule... modules) {
        return new Injector(modules);
    }

    private void setupModules() {
        this.modules.forEach(module -> {
            module.attachBinder(this);
            module.configure();
            bind(module.getClass()).toInstance(module);

            getMethods(module).forEach(method -> {
                if (method.isAnnotationPresent(Provide.class)) {
                    Object returnObject = invoke(method, module);
                    if (returnObject != null)
                        bind(returnObject.getClass()).toInstance(returnObject);
                }
            });
        });
    }

    private void injectDependencies() {
        this.builders.stream().map(BindingBuilder::get).forEach(instance -> getFields(instance).stream().filter(field -> field.isAnnotationPresent(Inject.class))
                .forEach(field -> {
                    String name = field.getAnnotation(Inject.class).value();
                    field.setAccessible(true);
                    setField(instance, name.isEmpty() ? getInstance(field.getType()) : getInstance(field.getType(), name), field);
                }));


        this.builders.stream().map(BindingBuilder::get).forEach(instance -> getMethods(instance).stream().filter(method -> !method.getReturnType().getName().equals("void") && method.isAnnotationPresent(Provide.class))
                .map(method -> invoke(method, instance)).filter(Objects::nonNull).forEach(object -> bind(object.getClass()).toInstance(object)));

        this.builders.stream().map(BindingBuilder::get).forEach(instance -> getMethods(instance).stream().filter(method -> method.isAnnotationPresent(Execute.class))
                .forEach(method -> invoke(method, instance)));

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
