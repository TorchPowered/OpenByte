/*
 * This file is part of OpenByte IDE, licensed under the MIT License (MIT).
 *
 * Copyright (c) TorchPowered 2016
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.openbyte.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.openbyte.server.net.NetworkManager;
import net.openbyte.server.net.pipeline.ProtocolChannelHandler;
import net.openbyte.server.util.ByteBufDecoders;
import net.openbyte.server.util.PacketBuilder;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * Represents the integrated server implementation of CodeUpdate inside of OpenByte.
 */
public final class CodeUpdateServer {
    private NetworkManager networkManager = null;

    private boolean isTestClientRunning = false;
    public static boolean authPacketSent = false;

    public void startServer() throws Exception {
        if(!(networkManager == null)) {
            return;
        }
        networkManager = NetworkManager.init(new InetSocketAddress(InetAddress.getLocalHost(), 4000));
        getNetworkManager().registerPipelineClass(ProtocolChannelHandler.class);
        getNetworkManager().networking();
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public void startTestClient() throws Exception {
        if(isTestClientRunning || !getNetworkManager().isRunning()) {
            return;
        }
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap clientBootstrap = new Bootstrap()
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext context) throws Exception {
                                System.out.println("Test client channel is active, sending validation packet to server!");
                                String name = "TEST_CLIENT";
                                String email = "client@example.org";
                                int clientId = -1; // test id

                                ByteBuf packet = new PacketBuilder()
                                        .varInt(0x00)
                                        .string(name)
                                        .string(email)
                                        .varInt(clientId)
                                        .build();

                                context.writeAndFlush(packet);
                            }

                            String authenticationId = null;

                            @Override
                            public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
                                ByteBuf read = (ByteBuf) msg;
                                int packetId = ByteBufDecoders.readVarInt(read);
                                System.out.println("Test client has received a packet (ID: " + packetId + ")");
                                if(packetId == 0x00) {
                                    // okay packet
                                    boolean validated = read.readBoolean();
                                    if(!validated) {
                                        System.out.println("Test client has not been given access to server. Stopping authentication process.");
                                        return;
                                    }
                                    authenticationId = ByteBufDecoders.readUTF8(read);
                                    System.out.println("Test client has authenticated successfully! (" + authenticationId + ")");
                                    System.out.println("Running server protocol tests...");
                                    System.out.println("Running get clients packet protocol...");
                                    ByteBuf packet = new PacketBuilder()
                                            .varInt(0x03)
                                            .string(authenticationId)
                                            .build();
                                    System.out.println("Sending protocol packet (get clients)...");
                                    context.writeAndFlush(packet);
                                    System.out.println("Awaiting for packet response...");
                                }
                                if(packetId == 0x03) {
                                    if(authPacketSent) {
                                        System.out.println("Authentication security failed!");
                                        System.exit(0);
                                    }
                                    // get client response
                                    int clientsConnected = ByteBufDecoders.readVarInt(read);
                                    String clients = ByteBufDecoders.readUTF8(read);

                                    System.out.println("Protocol packet (get clients) successfully executed with following results.");
                                    System.out.println("Amount of clients connected: " + clientsConnected + ", clients connected:");
                                    System.out.println(clients);

                                    //System.out.println("Test client successfully executed tests.");
                                    //System.exit(0);

                                    System.out.println("Running authentication security test (use fake authentication id)...");
                                    ByteBuf packet = new PacketBuilder()
                                            .varInt(0x03)
                                            .string(UUID.randomUUID().toString())
                                            .build();
                                    context.writeAndFlush(packet);
                                    System.out.println("Invalid authentication packet sent!");
                                    authPacketSent = true;
                                }
                            }
                        });
                    }
                });
        clientBootstrap.connect(InetAddress.getLocalHost(), 4000).sync();
        isTestClientRunning = true;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Starting server (networking and configuration manager)...");
        CodeUpdateServer server = new CodeUpdateServer();
        server.startServer();
        System.out.println("Starting tests client to ensure the server is running healthy!");
        server.startTestClient();
        while (true) {}
    }
}
