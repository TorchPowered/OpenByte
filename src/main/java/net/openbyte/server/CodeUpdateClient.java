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
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.openbyte.FileUtil;
import net.openbyte.gui.SettingsFrame;
import net.openbyte.server.util.ByteBufDecoders;
import net.openbyte.server.util.PacketBuilder;

import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * Represents a client implementation of CodeUpdate inside of OpenByte.
 */
public class CodeUpdateClient {
    private File projectDirectory;

    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private ChannelFuture future;
    private String clientName = new File(System.getProperty("user.home")).getName();
    private ClientChannelHandler handler = new ClientChannelHandler(projectDirectory, clientName);
    private Bootstrap clientBootstrap = new Bootstrap()
            .group(workerGroup)
            .channel(NioSocketChannel.class)
            .handler(handler);
    private String authenticationId = handler.getAuthenticationId();
    private boolean connected = false;

    private InetSocketAddress address;

    public CodeUpdateClient(File projectDirectory) {
        this.projectDirectory = projectDirectory;
    }

    public void connect(InetAddress address, int port) throws Exception {
        this.address = new InetSocketAddress(address, port);
        future = this.clientBootstrap.connect(this.address).sync();
        connected = true;
    }

    public void disconnect() throws Exception {
        if(connected || !(authenticationId == null)) {
            return;
        }
        ByteBuf disconnectionPacket = new PacketBuilder()
                .varInt(0x08)
                .string(authenticationId)
                .string(clientName)
                .build();
        ChannelFuture task = future.channel().writeAndFlush(disconnectionPacket);
        while (!task.isDone()) {}
        future.channel().closeFuture().sync();
        workerGroup.shutdownGracefully();
    }

    public void addFile(String filePath, String content) throws Exception {
        ByteBuf packet = new PacketBuilder()
                .varInt(0x01)
                .string(authenticationId)
                .string(filePath)
                .string(content)
                .build();
        future.channel().writeAndFlush(packet);
    }

    public void updateFile(String filePath, String content) throws Exception {
        if(!(new File(filePath).exists())) {
            return;
        }
        ByteBuf packet = new PacketBuilder()
                .varInt(0x02)
                .string(authenticationId)
                .string(filePath)
                .string(content)
                .build();
        future.channel().writeAndFlush(packet);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Starting server...");
        CodeUpdateServer server = new CodeUpdateServer();
        server.startServer();
        System.out.println("Starting client implementation...");
        CodeUpdateClient client = new CodeUpdateClient(null);
        client.connect(InetAddress.getLocalHost(), 4000);
        System.out.println("Starting tests client...");
        server.startTestClient();
        while (true) {}
    }

    public static class ClientChannelHandler extends ChannelHandlerAdapter {
        private String clientName;
        private File projectDirectory;
        private String authenticationId = UUID.randomUUID().toString();

        public ClientChannelHandler(File projectDirectory, String clientName) { this.projectDirectory = projectDirectory; this.clientName = clientName; }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            ByteBuf connectionPacket = new PacketBuilder()
                    .varInt(0x00)
                    .string(clientName)
                    .string(SettingsFrame.email)
                    .varInt(0)
                    .build();
            ctx.writeAndFlush(connectionPacket);
        }

        public String getAuthenticationId() {
            return authenticationId;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            ByteBuf read = (ByteBuf) msg;
            int packetId = ByteBufDecoders.readVarInt(read);

            if (packetId == 0x00) {
                // okay packet
                boolean validated = read.readBoolean();
                if(!validated) {
                    return;
                }
                String validAuthId = ByteBufDecoders.readUTF8(read);
                authenticationId = validAuthId;
                return;
            }

            if (packetId == 0x01) {
                // add file packet
                String filePath = ByteBufDecoders.readUTF8(read);
                String content = ByteBufDecoders.readUTF8(read);

                File file = new File(projectDirectory, filePath);
                file.getParentFile().mkdirs();
                file.createNewFile();

                FileUtil.format(content, file);
                return;
            }

            if (packetId == 0x02) {
                // update file packet
                String filePath = ByteBufDecoders.readUTF8(read);
                String content = ByteBufDecoders.readUTF8(read);

                File file = new File(projectDirectory, filePath);

                if(!file.exists()) {
                    return;
                }

                FileUtil.format(content, file);
                return;
            }
        }
    }
}
