package net.openbyte.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.util.ArrayList;

/**
 * Handles all integrated server networking.
 */
public final class NetworkManager {
    private InetSocketAddress serverAddress;

    private ServerBootstrap networkServer;
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private ChannelFuture future;

    private ArrayList<Class<? extends ChannelHandler>> pipelineClasses = new ArrayList<Class<? extends ChannelHandler>>();

    private boolean isRunning = false;

    private NetworkManager(InetSocketAddress serverAddress) { this.serverAddress = serverAddress; }

    public static NetworkManager init(InetSocketAddress serverAddress) {
        return new NetworkManager(serverAddress);
    }

    public InetSocketAddress getServerAddress() {
        return serverAddress;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void registerPipelineClass(Class<? extends ChannelHandler> pipelineClass) {
        pipelineClasses.add(pipelineClass);
    }

    public Class[] getPipelineClasses() {
        return pipelineClasses.toArray(new Class[pipelineClasses.size()]);
    }

    public void networking() throws Exception {
        if(isRunning) {
            return;
        }
        networkServer = new ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        for (Class pipelineClass : getPipelineClasses()) {
                            Object instance = pipelineClass.newInstance();
                            ChannelHandler channelHandler = (ChannelHandler) instance;
                            ch.pipeline().addLast(channelHandler);
                        }
                    }
                });
        future = networkServer.bind(getServerAddress().getAddress(), getServerAddress().getPort()).sync();
        isRunning = true;
    }
}
