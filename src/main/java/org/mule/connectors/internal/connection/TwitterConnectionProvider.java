package org.mule.connectors.internal.connection;

import java.lang.String;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.param.Parameter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This is a Connection Provider, is executed to obtain new connections when an operation or message source requires.;
 */
public class TwitterConnectionProvider implements CachedConnectionProvider<TwitterConnection> {

  @Parameter
  private String consumerKey;

  @Parameter
  private String consumerSecret;

  @Parameter
  private String accessToken;

  @Parameter
  private String accessTokenSecret;

  public TwitterConnection connect() throws ConnectionException {
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true)
            .setOAuthConsumerKey(consumerKey)
            .setOAuthConsumerSecret(consumerSecret)
            .setOAuthAccessToken(accessToken)
            .setOAuthAccessTokenSecret(accessTokenSecret);
    return new TwitterConnection(cb.build());
  }

  public void disconnect(TwitterConnection connection) {
    // Execute disconnection logic
  }

  public ConnectionValidationResult validate(TwitterConnection connection) {
    // Execute validation logic
    return ConnectionValidationResult.success();
  }

}
