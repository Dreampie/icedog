package org.icedog.common.web.websocket;

import com.alibaba.fastjson.JSON;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created by ice on 14-9-17.
 */
public class MessageDecoder implements Decoder.Text<Message> {
  @Override
  public void init(EndpointConfig ec) {
  }

  @Override
  public void destroy() {
  }

  @Override
  public Message decode(String msg) throws DecodeException {
    // Read message...
//    if ( /* message is an A message */)
//      return new MessageA(...);
//    else if ( /* message is a B message */)
//      return new MessageB(...);
    if (willDecode(msg)) {
      return JSON.parseObject(msg, Message.class);
    } else {
      throw new DecodeException(msg, "[Message] Can't decode.");
    }
  }

  @Override
  public boolean willDecode(String msg) {
    // Determine if the message can be converted into either a
    // MessageA object or a MessageB object...
    Message m = JSON.parseObject(msg, Message.class);
    return m != null;
  }
}
