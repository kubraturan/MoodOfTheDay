package com.example.developer.moodoftheday;

/**
 * Created by developer on 28.09.2017.
 */
class Message {

    String name;
    String message;



    String id;

    Message(String name, String message,String id) {
        this.id=id;
        this.name = name;
        this.message = message;
    }

    Message() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
