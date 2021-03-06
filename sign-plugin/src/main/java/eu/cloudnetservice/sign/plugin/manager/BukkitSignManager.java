package eu.cloudnetservice.sign.plugin.manager;

import de.dytanic.cloudnet.api.CloudAPI;
import eu.cloudnetservice.sign.core.manager.SignManager;
import eu.cloudnetservice.sign.plugin.adapter.SignNetworkHandlerAdapter;
import eu.cloudnetservice.sign.plugin.event.BukkitUpdateSignLayoutsEvent;
import eu.cloudnetservice.sign.plugin.hook.ThreadImpl;
import eu.cloudnetservice.sign.plugin.listener.SignListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitSignManager extends SignManager {

    public BukkitSignManager(JavaPlugin plugin) {
        super(new ThreadImpl(new SignNetworkHandlerAdapter(plugin)));
        CloudAPI.getInstance().getNetworkHandlerProvider().registerHandler(((ThreadImpl) getWorker()).getSignNetworkHandlerAdapter());
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getScheduler().runTaskTimer(plugin, getWorker(), 0L, 20L);

        Bukkit.getPluginManager().registerEvents(new SignListener(this), plugin);
    }

    @Override
    public void updateLayoutCall() {
        Bukkit.getPluginManager().callEvent(new BukkitUpdateSignLayoutsEvent(getSignLayoutConfig()));
    }
}
