package me.vanilar.projects.strike.room.data;

import me.vanilar.projects.strike.teams.TeamType;

import org.bukkit.entity.Player;

public class RoomMember {

    private Player member;
    private TeamType teamType;

    public RoomMember(Player player, TeamType type) {
        this.member = player;
        this.teamType = type;
    }
}
