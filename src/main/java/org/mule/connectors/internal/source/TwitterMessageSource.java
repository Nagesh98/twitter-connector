package org.mule.connectors.internal.source;

import org.mule.connectors.api.Location;
import org.mule.connectors.api.TwitterAttributes;
import org.mule.connectors.internal.TwitterConnector;
import org.mule.connectors.internal.connection.TwitterConnection;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.NullSafe;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.runtime.operation.Result;
import org.mule.runtime.extension.api.runtime.source.Source;
import org.mule.runtime.extension.api.runtime.source.SourceCallback;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;

@MediaType("text/plain")
public class TwitterMessageSource extends Source<String, TwitterAttributes> {
    private static final String COUNTER_VARIABLE = "counter";

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterMessageSource.class);

    @Connection
    private ConnectionProvider<TwitterConnection> connection;

    @Config
    private TwitterConnector config;

    @Parameter
    @Optional(defaultValue = "true")
    private boolean ignoreRetweets;

    @Parameter
    @Optional(defaultValue = "0")
    private int count;

    @Parameter
    @Optional
    @NullSafe
    private Set<Long> users;

    @Parameter
    @Optional
    @NullSafe
    private Set<String> keyboards;

    @Parameter
    @Optional
    @NullSafe
    private Set<String> languages;

    @Parameter
    @Optional
    @NullSafe
    private Set<Location> locations;
    private TwitterConnection twitterConnection;
    private TwitterStream twitterStream;

    /**
     * This method is called to start the Message Source.
     * The Source is considered Started once the onStart method finished, so it's required to start a new thread to
     * execute the source logic.
     * In this case the SchedulerService is used to schedule at a fixed rate to execute the {@link SourceRunnable}
     */
    public void onStart(SourceCallback<String, TwitterAttributes> sourceCallback) throws ConnectionException {

        twitterConnection = connection.connect();

        TwitterStream streamClient = twitterConnection.getStreamClient();

        StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {
                if (ignoreRetweets && status.isRetweet()) {
                    return;
                }
                sourceCallback.handle(Result.<String, TwitterAttributes>builder()
                        .output(status.getText())
                        .attributes(new TwitterAttributes(status))
                        .build());
            }

            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            }

            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            }

            @Override
            public void onScrubGeo(long l, long l1) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };

        streamClient.addListener(listener);

        long[] users = this.users.stream().mapToLong(u -> u).toArray();
        String[] track = keyboards.toArray(new String[]{});
        twitterStream = streamClient.filter(new FilterQuery(count, users, track, new double[][]{}, languages.toArray(new String[]{})));
    }

    public void onStop() {
        LOGGER.info("Stopping Source");
        //TODO this will shutdown all listeners

        if (twitterConnection != null) {
            if (twitterStream != null) {
                twitterStream.shutdown();
            }
        }

    }
}
