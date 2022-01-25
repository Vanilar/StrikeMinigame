package me.vanilar.projects.strike.room.data;

import me.vanilar.projects.strike.teams.TeamType;

import org.bukkit.entity.Player;

public class RoomMember {

    private String name;
    private Player member;
    private TeamType teamType;

    public RoomMember(Player player, TeamType type) {
        this.name = player.getName();
        this.member = player;
        this.teamType = type;
    }
}
