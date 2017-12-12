package org.graviton.test.modules;

import org.graviton.injector.api.Provide;
import org.graviton.injector.modules.AbstractModule;
import org.graviton.test.messages.Email;
import org.graviton.test.messages.FacebookMessage;
import org.graviton.test.messages.NtpkMessage;
import org.graviton.test.services.DefaultService;

public class DefaultModule extends AbstractModule {

    @Override
    public void configure() {
        bind(DefaultService.class).asSingleton();
        bind(Email.class).toName("First").toInstance(new Email("First mail"));
        bind(Email.class).toName("Second").toInstance(new Email("Second mail"));
        bind(NtpkMessage.class).asSingleton();
    }

    @Provide
    public FacebookMessage facebookMessage() {
        return new FacebookMessage("Salam");
    }

}
