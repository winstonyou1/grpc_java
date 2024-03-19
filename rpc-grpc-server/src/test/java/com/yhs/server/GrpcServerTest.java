package com.yhs.server;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

public class GrpcServerTest {

    private static final String LOCALHOST = "127.0.0.1";

    private static final int PORT = 8082;

    private Socket socket;

    @Test
    public void testMain() throws IOException {
        // Given
        int attempts = 10;

        // When
        boolean connected = false;
        for (int i = 0; i < attempts; i++) {
            try (Socket socket = new Socket(LOCALHOST, PORT)) {
                connected = true;
                break;
            } catch (IOException e) {
                // Server not yet started, wait and retry
            }
        }

        // Then
        assertTrue("Server was not started", connected);
    }

    @Before
    public void setUp() throws IOException {
        socket = new Socket(LOCALHOST, PORT);
    }

    @After
    public void tearDown() throws IOException {
        socket.close();
    }
}
