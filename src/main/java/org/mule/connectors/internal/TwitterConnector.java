package org.mule.connectors.internal;

import org.mule.connectors.internal.connection.TwitterConnectionProvider;
import org.mule.connectors.internal.operations.TwitterOperations;
import org.mule.connectors.internal.source.TwitterMessageSource;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.Sources;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

@Extension(
    name = "Twitter"
)
@Sources(TwitterMessageSource.class)
@Operations(TwitterOperations.class)
@ConnectionProviders(TwitterConnectionProvider.class)
public class TwitterConnector {
}
