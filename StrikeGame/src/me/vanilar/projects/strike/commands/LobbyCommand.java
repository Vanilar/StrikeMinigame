package me.vanilar.projects.strike.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.vanilar.projects.strike.StrikeGame;
import me.vanilar.projects.strike.utils.Message;
import me.vanilar.projects.strike.utils.Messager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LobbyCommand implements CommandExecutor {

    private Messager messager = StrikeGame.getInstance().getMessager();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Player sender = (Player) commandSender;
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF("STRIKE_LOBBY");
            int seconds = 10;
            sender.sendMessage(String.format(messager.getMessage(Message.CONNECT_TO_LOBBY), seconds));
            new BukkitRunnable() {
                int time = 0;

                @Override
                public void run() {
                    if(Bukkit.getServer().getPlayer(sender.getName()) == null) this.cancel();
                    if(time >= seconds) {
                        sender.sendPluginMessage(StrikeGame.getInstance(), "BungeeCord", out.toByteArray());
                        this.cancel();
                    }
                    else {
                        time += 1;
                    }
                }
            }.runTaskTimerAsynchronously(StrikeGame.getInstance(), 10L, 20L);
        }
        return true;
    }
}
