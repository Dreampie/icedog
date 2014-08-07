package org.icedog.function.common;

/**
 * Created by wangrenhui on 2014/8/7.
 */
public class Message {
  private String type;
  private String msg;

  public Message(MessageType type, String msg) {
    this.type = type.value;
    this.msg = msg;
  }

  public enum MessageType {
    SUCCESS("success"), INFO("info"), WARNING("warning"), DANGER("danger");
    private final String value;

    private MessageType(String value) {
      this.value = value;
    }

    public String value() {
      return this.value;
    }
  }
}
