package org.graviton.injector.modules;


import org.graviton.injector.binder.Binder;
import org.graviton.injector.binder.BindingBuilder;

public abstract class AbstractModule {

    private Binder binder;

    public abstract void configure();

    public void attachBinder(Binder binder) {
        this.binder = binder;
    }

    protected <T> BindingBuilder<T> bind(Class<T> clazz) {
        return binder.bind(clazz);
    }

}
