package com.redditClone.exception;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.e")
public class SpringRedditException extends RuntimeException{
    public SpringRedditException(String exMessage,Exception exception){
        super(exMessage,exception);

    }
    public SpringRedditException(String exMessage) {
        super(exMessage);
    }
}
