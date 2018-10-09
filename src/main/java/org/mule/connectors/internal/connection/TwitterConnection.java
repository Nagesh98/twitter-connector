package org.mule.connectors.internal.connection;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;

public class TwitterConnection {
  private final TwitterFactory clientFactory;
  private final TwitterStreamFactory streamClientFactory;

  public TwitterConnection(Configuration config) {
    this.clientFactory =  new TwitterFactory(config);
    streamClientFactory = new TwitterStreamFactory(config);
  }

  public Twitter getClient() {
   return clientFactory.getInstance();
  }

  public TwitterStream getStreamClient() {
    return streamClientFactory.getInstance();
  }
}
