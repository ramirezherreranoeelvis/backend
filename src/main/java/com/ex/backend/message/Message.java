package com.ex.backend.message;

import lombok.Getter;
import lombok.Setter;

public class Message {
        @Getter
        @Setter
        private String message;

        public static Message Message(String message) {
                var x = new Message();
                x.message = message;
                return x;
        }
}
