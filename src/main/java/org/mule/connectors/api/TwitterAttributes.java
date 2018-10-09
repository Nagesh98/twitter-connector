package org.mule.connectors.api;

import java.lang.String;

import twitter4j.Status;
import twitter4j.User;

public class TwitterAttributes {

  private final User user;

  public TwitterAttributes(Status status) {
    user = status.getUser();
  }

  public User getUser() {
    return user;
  }
}
