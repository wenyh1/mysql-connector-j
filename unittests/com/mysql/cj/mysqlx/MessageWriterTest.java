/*
  Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.

  The MySQL Connector/J is licensed under the terms of the GPLv2
  <http://www.gnu.org/licenses/old-licenses/gpl-2.0.html>, like most MySQL Connectors.
  There are special exceptions to the terms and conditions of the GPLv2 as it is applied to
  this software, see the FLOSS License Exception
  <http://www.mysql.com/about/legal/licensing/foss-exception.html>.

  This program is free software; you can redistribute it and/or modify it under the terms
  of the GNU General Public License as published by the Free Software Foundation; version 2
  of the License.

  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
  See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with this
  program; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth
  Floor, Boston, MA 02110-1301  USA

 */

package com.mysql.cj.mysqlx;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.google.protobuf.ByteString;

import org.junit.Before;
import org.junit.Test;

import static com.mysql.cj.mysqlx.protobuf.Mysqlx.ClientMessages;
import static com.mysql.cj.mysqlx.protobuf.Mysqlx.Ok;
import static com.mysql.cj.mysqlx.protobuf.MysqlxSession.AuthenticateStart;

import com.mysql.cj.core.exceptions.WrongArgumentException;

public class MessageWriterTest {
    private ByteArrayOutputStream outputStream;
    private MessageWriter writer;

    @Before
    public void setUp() {
        this.outputStream = new ByteArrayOutputStream();
        this.writer = new MessageWriter(new BufferedOutputStream(this.outputStream));
    }

    /**
     * Test that we can (properly) write a complete message.
     */
    @Test
    public void testCompleteWriteMessage() throws Exception {
        // construct and write the message
        AuthenticateStart.Builder msgBuilder = AuthenticateStart.newBuilder();
        msgBuilder.setMechName("Unit-Test");
        msgBuilder.setAuthData(ByteString.copyFromUtf8("some-auth-data"));
        AuthenticateStart msg = msgBuilder.build();
        this.writer.write(msg);

        // verify the written packet
        byte[] sentBytes = this.outputStream.toByteArray();
        int msgSize = msg.getSerializedSize();
        int totalSize = msgSize + MessageWriter.HEADER_LEN;
        assertTrue("Required for rest of test, should never fail", msgSize < Byte.MAX_VALUE);
        // message size (4 bytes big endian)
        assertEquals(0, sentBytes[0]);
        assertEquals(0, sentBytes[1]);
        assertEquals(0, sentBytes[2]);
        assertEquals(totalSize, sentBytes[3]);
        assertEquals("Type tag", ClientMessages.Type.SESS_AUTHENTICATE_START_VALUE, sentBytes[4]);
        assertEquals("Entire packet size should be header bytes + serialized message", totalSize, sentBytes.length);
    }

    @Test
    public void testBadMessageClass() throws Exception {
        try {
            // try sending "Ok" which is a server-sent message. should fail with exception
            this.writer.write(Ok.getDefaultInstance());
            fail("Writing OK message should fail");
        } catch (WrongArgumentException ex) {
        }
    }
}
