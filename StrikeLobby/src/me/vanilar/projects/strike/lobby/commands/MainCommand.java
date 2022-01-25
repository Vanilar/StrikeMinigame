package me.vanilar.projects.strike.lobby.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.vanilar.projects.strike.lobby.StrikeLobby;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MainCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            if(args.length == 1 && args[0].equalsIgnoreCase("room1")) {
                Player sender = (Player) commandSender;
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF("STRIKE_ROOM_1");
                int seconds = 10;
                sender.sendMessage("Вы войдёте в мини-игру через 10 секунд");
                new BukkitRunnable() {
                    int time = 0;

                    @Override
                    public void run() {
                        if(Bukkit.getServer().getPlayer(sender.getName()) == null) this.cancel();
                        if(time >= seconds) {
                            sender.sendPluginMessage(StrikeLobby.getInstance(), "BungeeCord", out.toByteArray());
                            this.cancel();
                        }
                        else {
                            time += 1;
                        }
                    }
                }.runTaskTimerAsynchronously(StrikeLobby.getInstance(), 10L, 20L);
            }
        }
        return true;
    }
}
