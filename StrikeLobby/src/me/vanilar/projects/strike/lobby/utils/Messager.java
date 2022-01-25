package me.vanilar.projects.strike.lobby.utils;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class Messager {
    private HashMap<Message, String> messages = new HashMap<>();

    public Messager(FileConfiguration config) {
        for(Message msg : Message.values()) {
            if(!config.isSet("messages."+msg.getPath())) {
                messages.put(msg, "null");
            }
            else {
                messages.put(msg, config.getString("messages."+msg.getPath()));
            }
        }
    }

    public String getMessage(Message msg) {
        return this.messages.get(msg);
    }
}
