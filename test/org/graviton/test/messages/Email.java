package org.graviton.test.messages;

public class Email implements Message{
    private String content;

    public Email(String content) {
        this.content = content;
    }


    @Override
    public void bip() {
        System.err.println("EMAIL BIP : " + content);
    }


}
