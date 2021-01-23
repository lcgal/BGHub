package com.example.bghub.Models.Chat;

public class ChatRoom {

    private String hostName;

    private String gameName;

    public ChatRoom(String hostName, String gameName) {
        this.hostName = hostName;
        this.gameName = gameName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }


}
