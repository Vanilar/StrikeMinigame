package me.vanilar.projects.strike.lobby.teams;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TeamManager {
    private HashMap<Player, TeamType> players = new HashMap<>();
    private HashMap<Player, Integer> cooldowns = new HashMap<>();
    private final Integer MAX_TEAM_SIZE;
    private final Integer CHANGE_TEAM_DELAY;
    private int cTTeamPlayersCount = 0;
    private int tTeamPlayersCount = 0;
    private int vTeamPlayersCount = 0;

    public TeamManager(ConfigurationSection section) {
        this.MAX_TEAM_SIZE = section.getInt("max-team-size", 5);
        this.CHANGE_TEAM_DELAY = section.getInt("change-team-delay", 60);
    }

    public ChangeTeamResult changePlayerTeam(Player player, TeamType type) {
        if (this.players.isEmpty()) {
            return ChangeTeamResult.ERROR;
        }
        if (!this.cooldowns.isEmpty() && this.cooldowns.containsKey(player) &&
                this.cooldowns.get(player) > CHANGE_TEAM_DELAY) {

            return  ChangeTeamResult.DENY_BY_DELAY;
        }
        if (type == TeamType.COUNTER_TERRORISTS) {
            if((this.cTTeamPlayersCount+1) <= MAX_TEAM_SIZE) {
                cTTeamPlayersCount += 1;
                tTeamPlayersCount -= 1;
            }
            else {
                return ChangeTeamResult.DENY_BY_COUNT;
            }
        }
        else if(type == TeamType.TERRORISTS) {
            if((this.tTeamPlayersCount+1) <= MAX_TEAM_SIZE) {
                cTTeamPlayersCount -= 1;
                tTeamPlayersCount += 1;
            }
            else {
                return ChangeTeamResult.DENY_BY_COUNT;
            }
        }

        this.players.replace(player, type);
        return ChangeTeamResult.ALLOW;
    }

    public void addPlayer(Player player, TeamType type){
        this.players.put(player, type);
    }

    public boolean removePlayer(Player player) {
        if (this.players.isEmpty()) {
            return false;
        }
        else {
            this.players.remove(player);
            return true;
        }
    }

    public Integer getMaxTeamSize() {
        return MAX_TEAM_SIZE;
    }

    /**
     * Функция получения периода заморозки перехода из одной команды в другую
     * @return возвращает период в секундах
     */
    public Integer getChangeTeamDelay() {
        return CHANGE_TEAM_DELAY;
    }

    public Integer getCTTeamPlayersCount() {
        return this.cTTeamPlayersCount;
    }

    public Integer getTTeamPlayersCount() {
        return this.tTeamPlayersCount;
    }

    public Integer getVTeamPlayersCount() {
        return this.vTeamPlayersCount;
    }
}
