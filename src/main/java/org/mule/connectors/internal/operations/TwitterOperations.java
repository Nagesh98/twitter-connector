package org.mule.connectors.internal.operations;

import org.mule.connectors.internal.connection.TwitterConnection;
import org.mule.runtime.extension.api.annotation.param.Connection;

import twitter4j.TwitterException;

public class TwitterOperations {

  public void tweet(@Connection TwitterConnection twitter, String message) throws TwitterException {
    twitter.getClient().updateStatus(message);
  }
}
