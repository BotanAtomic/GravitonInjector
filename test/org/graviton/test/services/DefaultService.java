package org.graviton.test.services;


import org.graviton.injector.api.Execute;
import org.graviton.injector.api.Inject;
import org.graviton.test.messages.Email;
import org.graviton.test.messages.Message;

public class DefaultService {
    @Inject private Message message;

    @Inject("First") private Email firstEmail;
    @Inject("Second") private Email secondEmail;

    @Execute
    public void test() {
        message.bip();
        firstEmail.bip();
        secondEmail.bip();
    }

}
