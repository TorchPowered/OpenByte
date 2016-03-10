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

package net.openbyte.server.net.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import net.openbyte.server.ServerConnection;
import net.openbyte.server.util.ByteBufDecoders;
import net.openbyte.server.util.PacketBuilder;

import java.util.ArrayList;

/**
 * A netty channel handler that follows all of the protocol guidelines of CodeUpdate.
 */
public class ProtocolChannelHandler extends ChannelHandlerAdapter {
    public static final ArrayList<ServerConnection> SERVER_CONNECTIONS = new ArrayList<ServerConnection>();

    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) throws Exception {
        ByteBuf read = (ByteBuf) msg;
        int packetId = ByteBufDecoders.readVarInt(read);

        if (packetId == 0x00) {
            //validation packet
            String name = ByteBufDecoders.readUTF8(read);
            String email = ByteBufDecoders.readUTF8(read);
            int clientId = ByteBufDecoders.readVarInt(read);

            //generate server connection object representation and authenticate client
            ServerConnection connection = new ServerConnection(context.channel(), name, email, clientId);
            /*  [OLD]
                ByteBuf packet = Unpooled.buffer();
                ByteBufDecoders.writeVarInt(packet, 0x00);
                packet.writeBoolean(true);
                ByteBufDecoders.writeUTF8(packet, connection.getAuthenticationId());
            */
            ByteBuf packet = new PacketBuilder()
                    .varInt(0x00)
                    .bool(true)
                    .string(connection.getAuthenticationId())
                    .build();
            SERVER_CONNECTIONS.add(connection);
            ByteBuf notifyPacket = new PacketBuilder()
                    .varInt(0x07)
                    .string(connection.getClientName())
                    .string(connection.getEmailAddress())
                    .varInt(connection.getClientId())
                    .build();

            // send authenticate packet
            context.writeAndFlush(packet);
            for (ServerConnection con : SERVER_CONNECTIONS) {
                if(!con.getAuthenticationId().equals(connection.getAuthenticationId())) {
                    con.getClientChannel().writeAndFlush(notifyPacket);
                }
            }
            return;
        }

        // get client data
        String authenticationId = ByteBufDecoders.readUTF8(read);
        // create object representation of the connection
        ServerConnection client = null;
        for (ServerConnection clientone : SERVER_CONNECTIONS) {
            if (authenticationId.equals(clientone.getAuthenticationId())) {
                client = clientone;
            }
        }
        // check if authentication id to client was unsuccessful to prevent NPEs
        if (client == null) {
            ByteBuf failedAuthPacket = new PacketBuilder()
                    .varInt(0x05)
                    .build();
            context.writeAndFlush(failedAuthPacket);
            return;
        }

        if (packetId == 0x01) {
            // add file packet
            String filePath = ByteBufDecoders.readUTF8(read);
            String content = ByteBufDecoders.readUTF8(read);

            // generate packet
            /** [OLD]
             *  ByteBuf packet = Unpooled.buffer();
             *  ByteBufDecoders.writeVarInt(packet, 0x01);
             *  ByteBufDecoders.writeUTF8(read, filePath);
             * ByteBufDecoders.writeUTF8(read, content);
             */
            ByteBuf packet = new PacketBuilder()
                    .varInt(0x01)
                    .string(filePath)
                    .string(content)
                    .build();

            // send packet
            for (ServerConnection connection : SERVER_CONNECTIONS) {
                if (!connection.getAuthenticationId().equals(authenticationId)) {
                    connection.getClientChannel().writeAndFlush(packet);
                }
            }
            return;
        }

        if (packetId == 0x02) {
            // update file packet
            String filePath = ByteBufDecoders.readUTF8(read);
            String content = ByteBufDecoders.readUTF8(read);

            // generate packet
            ByteBuf packet = new PacketBuilder()
                    .varInt(0x02)
                    .string(filePath)
                    .string(content)
                    .build();

            // send packet
            for (ServerConnection connection : SERVER_CONNECTIONS) {
                if(!connection.getAuthenticationId().equals(authenticationId)) {
                    connection.getClientChannel().writeAndFlush(packet);
                }
            }
            return;
        }

        if (packetId == 0x03) {
            // get clients packet
            // generate packet
            StringBuilder members = new StringBuilder();
            for (ServerConnection connection : SERVER_CONNECTIONS) {
                members.append(connection.getClientName() + " ");
            }

            ByteBuf packet = new PacketBuilder()
                    .varInt(0x03)
                    .varInt(SERVER_CONNECTIONS.size())
                    .string(members.toString())
                    .build();

            // send packet
            context.writeAndFlush(packet);
            return;
        }

        if (packetId == 0x04) {
            // get email packet
            String clientName = ByteBufDecoders.readUTF8(read);
            ServerConnection clientNameRepresentation = null;
            for (ServerConnection connection : SERVER_CONNECTIONS) {
                if (connection.getClientName().equals(clientName)) {
                    clientNameRepresentation = connection;
                }
            }
            if (clientNameRepresentation == null) {
                return;
            }

            // generate packet
            ByteBuf packet = new PacketBuilder()
                    .varInt(0x04)
                    .string(clientNameRepresentation.getEmailAddress())
                    .build();

            // send packet
            context.writeAndFlush(packet);
            return;
        }

        if (packetId == 0x08) {
            // disconnect packet
            String clientName = ByteBufDecoders.readUTF8(read);

            for (ServerConnection con : SERVER_CONNECTIONS) {
                if(con.getClientName().equals(clientName)) {
                    SERVER_CONNECTIONS.remove(con);
                }
            }

            // generate packet
            ByteBuf packet = new PacketBuilder()
                    .varInt(0x08)
                    .string(clientName)
                    .build();

            // send packet
            for (ServerConnection con : SERVER_CONNECTIONS) {
                if(!con.getAuthenticationId().equals(client.getAuthenticationId())) {
                    con.getClientChannel().writeAndFlush(packet);
                }
            }
            return;
        }
    }
}
