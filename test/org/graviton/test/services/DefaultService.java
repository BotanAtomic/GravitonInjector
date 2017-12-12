package org.graviton.test.services;


import org.graviton.injector.api.Execute;
import org.graviton.injector.api.Inject;
import org.graviton.test.messages.Email;
import org.graviton.test.messages.FacebookMessage;

public class DefaultService {
    @Inject private FacebookMessage message;

    @Inject("First") private Email firstEmail;
    @Inject("Second") private Email secondEmail;

    @Execute
    public void test() {
        message.bip();
        firstEmail.bip();
        secondEmail.bip();
    }

}
