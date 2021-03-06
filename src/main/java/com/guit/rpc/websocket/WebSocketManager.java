package com.guit.rpc.websocket;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;

import com.guit.client.GuitEntryPoint;

public class WebSocketManager {

  private static WebSocketManager INSTANCE;

  public static WebSocketManager get() {
    return INSTANCE;
  }

  private WebSocket webSocket;

  private Timer reconnectTimer;

  private OpenHandler openHandler = new OpenHandler() {
    @Override
    public void onOpen(WebSocket webSocket) {
      if (reconnectTimer != null) {
        reconnectTimer.cancel();
        reconnectTimer = null;
      }
      GuitEntryPoint.getEventBus().fireEvent(new ConnectionOpenEvent());
    }
  };
  private CloseHandler closeHandler = new CloseHandler() {
    @Override
    public void onClose(WebSocket webSocket) {
      GuitEntryPoint.getEventBus().fireEvent(new ConnectionCloseEvent());

      reconnectTimer = new Timer() {
        @Override
        public void run() {
          init();
        }
      };
      reconnectTimer.scheduleRepeating(5000);
    }
  };

  private ErrorHandler errorHandler;
  private MessageHandler messageHandler;

  public WebSocketManager() {
    init();
    INSTANCE = this;
  }

  private void init() {
    webSocket = WebSocket.create("ws://" + getBaseUrl() + "websocket");
    webSocket.setOnOpen(openHandler);
    webSocket.setOnClose(closeHandler);
    if (errorHandler != null) {
      webSocket.setOnError(errorHandler);
    }
    if (messageHandler != null) {
      webSocket.setOnMessage(messageHandler);
    }
  }

  private static String getBaseUrl() {
    String s = GWT.getHostPageBaseURL();
    if (s.startsWith("http://")) {
      s = s.substring(7);
    } else if (s.startsWith("https://")) {
      s = s.substring(8);
    }
    return s;
  }

  public int getReadyState() {
    return webSocket.getReadyState();
  }

  public void setOnMessage(MessageHandler messageHandler) {
    this.messageHandler = messageHandler;
    webSocket.setOnMessage(messageHandler);
  }

  public void setOnError(ErrorHandler errorHandler) {
    this.errorHandler = errorHandler;
    webSocket.setOnError(errorHandler);
  }

  public void send(String data) {
    if (webSocket.getReadyState() == WebSocket.OPEN) {
      webSocket.send(data);
    } else {
      throw new RuntimeException("The connection is not opened. Listen for ConnectionOpen event");
    }
  }
}
