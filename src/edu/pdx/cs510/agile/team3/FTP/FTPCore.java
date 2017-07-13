package edu.pdx.cs510.agile.team3.FTP;

/**
 * Created by henry on 7/12/17.
 */

import org.apache.commons.net.ftp.*;

import java.io.IOException;

// Core FTP class.
// Contains logic for interacting with java FTPClient -- all main FTP functionality lives here!
// All FTP functions should return generic results that can displayed either by the CLI app or the GUI.
//
// May contain (possibly many!) other classes -- e.g., log functionality should be in it's own Logger class, etc.
public class FTPCore {

    public FTPCore() {
        isConnected = false;
        currentConnection = null;
        ftpClient = new FTPClient();
    }

    public void disconnect() {
        isConnected = false;
        currentConnection = null;
        try {
            ftpClient.disconnect();
        } catch (IOException e) {
            // Don't care about this error!
        }
    }

    // Connects ftpClient to the server described by serverInfo.
    // If ftpClient is already connected, the current connection is disconnected.
    public FTPConnection connect(FTPServerInfo serverInfo) throws ConnectionFailedException {

        disconnect();

        try {
            ftpClient.connect(serverInfo.host, serverInfo.port);
        } catch (IOException e) {
            throw new ConnectionFailedException("Could not connect to FTP server at host: "
                    + serverInfo.host + " on port: " + serverInfo.port);
        }

        try {
            ftpClient.login(serverInfo.username, serverInfo.password);
        } catch (IOException e) {
            throw new ConnectionFailedException("Connection to FTP server failed: invalid username or password");
        }

        FTPConnection ftpConnection = new FTPConnection(serverInfo);
        isConnected = true;
        currentConnection = ftpConnection;
        return ftpConnection;
    }

    private boolean isConnected;
    private FTPConnection currentConnection;
    private FTPClient ftpClient;
}
