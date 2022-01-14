package me.vanilar.projects.strike.room;

import me.vanilar.projects.strike.GamemodeType;
import me.vanilar.projects.strike.room.data.RoomMember;
import me.vanilar.projects.strike.room.maps.MapsManager;

import java.util.ArrayList;

public class Room {

    private MapsManager mapsManager;

    private ArrayList<RoomMember> members;
    private GamemodeType mode;

    public Room() {
        this.mapsManager = new MapsManager();
    }
}
