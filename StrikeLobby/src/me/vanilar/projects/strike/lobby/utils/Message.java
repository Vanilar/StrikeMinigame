package me.vanilar.projects.strike.lobby.utils;

public enum Message {

    CONNECT_TO_LOBBY("connect-to-lobby"),
    SERVER_IS_FULL("server-is-full"),
    CHOOSE_COMMAND("choose-command"),
    REOPEN_CHOOSE_COMMAND_MENU("reopen-choose-command-menu"),
    PLUGIN_PREFIX("plugin-prefix"),
    PLAYER_CHOOSE_COUNTER_TERRORISTS_COMMAND("player-choose-counter-terrorists-command"),
    PLAYER_CHOOSE_TERRORISTS_COMMAND("player-choose-terrorists-command"),
    PLAYER_CHOOSE_VIEWERS_COMMAND("player-choose-viewers-command"),
    COUNTER_TERRORISTS("counter-terrorists"),
    TERRORISTS("terrorists"),
    VIEWERS("viewers"),
    MEMBERS_COUNT_IN_ROOM("members-count-in-room");

    private String name;

    Message(String configString){
        this.name = configString;
    }

    public String getPath() {
        return this.name;
    }

}
