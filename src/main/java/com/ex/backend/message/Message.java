package com.ex.backend.message;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message<T> {

        private String message;

        private T result;

        public static <T> Message<T> message(String message) {
                var msg = new Message<T>();
                msg.setMessage(message);
                return msg;
        }

        public static <T> Message<T> message(String message, T result) {
                var msg = new Message<T>();
                msg.setMessage(message);
                msg.setResult(result);
                return msg;
        }
}
