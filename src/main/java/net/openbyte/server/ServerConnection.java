package net.openbyte.server;

import io.netty.channel.Channel;

import java.util.UUID;

/**
 * Represents a client connection to the server.
 */
public class ServerConnection {
    private String clientName;
    private String emailAddress;
    private int clientId;
    private Channel clientChannel;
    private String authenticationId;

    public ServerConnection(Channel channel, String clientName, String emailAddress, int clientId) {
        this.clientChannel = channel;
        this.clientName = clientName;
        this.emailAddress = emailAddress;
        this.clientId = clientId;
        this.authenticationId = UUID.randomUUID().toString();
    }

    public Channel getClientChannel() {
        return clientChannel;
    }

    public int getClientId() {
        return clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }
}
