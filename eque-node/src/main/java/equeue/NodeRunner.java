/*
 * Copyright 2018 skrymets.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package equeue;

import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.MembershipListener;
import org.jgroups.Message;
import org.jgroups.View;
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_SINGLETON;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * @author skrymets
 */
@Component
@Scope(scopeName = SCOPE_SINGLETON)
public class NodeRunner implements NodeControl, RequestHandler, MembershipListener {

    private static final Logger LOG = LoggerFactory.getLogger(NodeRunner.class);

    private JChannel channel;

    private MessageDispatcher md;

    private String clusterName;

    public NodeRunner() {
    }

    @PostConstruct
    void createDispatecher() {
        md = new MessageDispatcher(null, this)
                .setMembershipListener(this);
    }

    @Override
    public void viewAccepted(View view) {
        LOG.info(view.getMembers().stream().map(Address::toString).collect(Collectors.joining(", ")));
    }

    JChannel createChannel() {
        try {
            return new JChannel();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized boolean play(String clusterName) {
        if (channel == null || channel.isClosed()) {
            channel = createChannel();
            md.stop();
            md.setChannel(channel);
        }

        if (isChannelInUse()) {
            LOG.warn("Channel is being used by application. Free it first.");
            return false;
        }

        try {
            md.start();
            channel.connect(clusterName);
            this.clusterName = clusterName;
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void pause() {
        md.stop();
        channel.disconnect();
    }

    @Override
    @PreDestroy
    public void leave() {
        if (channel == null) {
            return;
        }
        md.stop();
        channel.close();
    }

    @Override
    public boolean isOnline() {
        return isChannelInUse();
    }

    @Override
    public String getClusterName() {
        return StringUtils.isEmpty(clusterName) ? "" : clusterName;
    }

    private boolean isChannelInUse() {
        return (channel != null && (channel.isConnected() || channel.isConnecting()));
    }

    @Override
    public Object handle(Message msg) throws Exception {
        LOG.info(msg.toString());
        return new Object();
    }

    @EventListener
    public void shutdownEventHandler(ShutdownNodeEvent event) {
        LOG.info("Shutting down the node ...");
        leave();
    }

}
