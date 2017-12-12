package org.graviton.test.messages;

import org.graviton.injector.api.Inject;

public class NtpkMessage implements Message {

    @Inject
    public NtpkMessage(Email first) {
        first.bip();
    }

    @Override
    public void bip() {
        System.err.println("Ntpk BIP : NULL");
    }

}
