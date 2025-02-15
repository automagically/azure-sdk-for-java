// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.spring.messaging.container;

import com.azure.spring.integration.core.api.SubscribeByGroupOperation;
import org.springframework.util.ErrorHandler;

/**
 * @author Warren Zhu
 */
class DefaultMessageListenerContainer extends AbstractListenerContainer {
    private final SubscribeByGroupOperation subscribeOperation;

    DefaultMessageListenerContainer(SubscribeByGroupOperation subscribeOperation) {
        this.subscribeOperation = subscribeOperation;
    }

    @Override
    protected void doStart() {
        synchronized (this.getLifecycleMonitor()) {
            ErrorHandler errorHandler = getErrorHandler();
            if (errorHandler != null) {
                subscribeOperation.subscribe(getDestination(), getGroup(), getMessageHandler()::handleMessage,
                    errorHandler::handleError, getMessageHandler().getMessagePayloadType());
            } else {
                subscribeOperation.subscribe(getDestination(), getGroup(), getMessageHandler()::handleMessage,
                    getMessageHandler().getMessagePayloadType());
            }
        }
    }

    @Override
    protected void doStop() {
        synchronized (this.getLifecycleMonitor()) {
            subscribeOperation.unsubscribe(getDestination(), getGroup());
        }
    }
}
