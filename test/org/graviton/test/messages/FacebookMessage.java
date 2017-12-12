package org.graviton.test.messages;

public class FacebookMessage implements Message {
    private String content;

    public FacebookMessage(String content) {
        this.content = content;
    }

    @Override
    public void bip() {
        System.err.println("Facebook BIP : " + content);
    }

}
