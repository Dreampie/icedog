package org.icedog.common.web.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ice on 14-9-17.
 */
@ServerEndpoint(
    value = "/notice/{cid}",
    encoders = {MessageEncoder.class},
    decoders = {MessageDecoder.class},
    configurator = MessageConfigurator.class
)
public class NoticeServer {
  private static final Logger logger = LoggerFactory.getLogger(NoticeServer.class);
  /* Queue for all open WebSocket sessions */
//  static Queue<Session> sessions = new ConcurrentLinkedQueue<Session>();
  static Map<String, Session> users = new ConcurrentHashMap<String, Session>();

  @OnOpen
  public void open(Session session,
                   EndpointConfig conf,
                   @PathParam("cid") String cid) {
//    HandshakeRequest req = (HandshakeRequest) conf.getUserProperties()
//        .get("handshakereq");
    try {
      session.getBasicRemote().sendObject(new Message("Welcome"));
    } catch (IOException e) {
      logger.error(e.toString());
    } catch (EncodeException e) {
      logger.error(e.toString());
    }
    /* Register this connection in the queue */
    users.put(cid, session);
    logger.info("Connection opened.");
  }


  @OnMessage
  public void message(Session session, Message msg) {
//    if (msg instanceof MessageA) {
//      // We received a MessageA object...
//    } else if (msg instanceof MessageB) {
//      // We received a MessageB object...
//    }
    if (msg instanceof Message) {
      try {
        session.getBasicRemote().sendObject(msg);
      } catch (IOException e) {
        logger.error(e.toString());
      } catch (EncodeException e) {
        logger.error(e.toString());
      }
    }
  }


  @OnError
  public void error(Session session, Throwable t) {
    /* Remove this connection from the queue */
    removeSession(session);
    logger.error(t.toString());
    logger.error("Connection error.");
  }

  @OnClose
  public void close(Session session,
                    CloseReason reason) {
     /* Remove this connection from the queue */
    removeSession(session);
    logger.info(reason.toString());
    logger.info("Connection closed.");
  }

  public boolean removeSession(Session session) {
    Collection<Session> sessions = users.values();
    if (sessions.contains(session)) {
      return sessions.remove(session);
    }
    return false;
  }

  public static void send(Message message) {
    String receiver = message.getReceiver();
    if (receiver == null || receiver.isEmpty()) {
      logger.info("Send: {0}", "not receiver");
      return;
    }
    try {
         /* Send updates to all open WebSocket sessions */
      for (String cid : users.keySet()) {
        if (cid.equals(receiver)) {
          users.get(cid).getBasicRemote().sendObject(message);
        }
        logger.info("Send: {0}", message);
      }
    } catch (IOException e) {
      logger.error(e.toString());
    } catch (EncodeException e) {
      logger.error(e.toString());
    }
  }

  public static void sendAll(Message message) {
    try {
         /* Send updates to all open WebSocket sessions */
      for (Session session : users.values()) {
        session.getBasicRemote().sendObject(message);
        logger.info("SendAll: {0}", message);
      }
    } catch (IOException e) {
      logger.error(e.toString());
    } catch (EncodeException e) {
      logger.error(e.toString());
    }
  }

}
