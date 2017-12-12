package org.graviton.test;

import org.graviton.injector.core.Injector;
import org.graviton.test.modules.DefaultModule;

public class Main {

    public static void main(String[] args) {
        Injector.create(new DefaultModule());
    }

}
