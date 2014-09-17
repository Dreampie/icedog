package org.icedog.common.web.websocket;

/**
 * Created by ice on 14-9-17.
 */
public class Message {
  private String message;
  private String author;
  private String receiver;

  public Message() {
  }

  public Message(String message) {
    this.author="系统";
    this.message = message;
  }

  public Message(String receiver, String message) {
    this.author="系统";
    this.receiver = receiver;
    this.message = message;
  }

  public Message(String author, String receiver, String message) {
    this.author = author;
    this.receiver = receiver;
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }
}
