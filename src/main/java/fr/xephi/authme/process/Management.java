package fr.xephi.authme.process;

import fr.xephi.authme.AuthMe;
import fr.xephi.authme.cache.auth.PlayerCache;
import fr.xephi.authme.datasource.DataSource;
import fr.xephi.authme.hooks.PluginHooks;
import fr.xephi.authme.process.email.AsyncAddEmail;
import fr.xephi.authme.process.email.AsyncChangeEmail;
import fr.xephi.authme.process.join.AsynchronousJoin;
import fr.xephi.authme.process.login.AsynchronousLogin;
import fr.xephi.authme.process.logout.AsynchronousLogout;
import fr.xephi.authme.process.quit.AsynchronousQuit;
import fr.xephi.authme.process.register.AsyncRegister;
import fr.xephi.authme.process.unregister.AsynchronousUnregister;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import javax.inject.Inject;

/**
 */
public class Management {

    @Inject
    private AuthMe plugin;
    @Inject
    private BukkitScheduler sched;
    @Inject
    private ProcessService processService;
    @Inject
    private DataSource dataSource;
    @Inject
    private PlayerCache playerCache;
    @Inject
    private PluginHooks pluginHooks;

    Management() { }

    public void performLogin(final Player player, final String password, final boolean forceLogin) {
        runTask(new AsynchronousLogin(player, password, forceLogin, plugin, dataSource, processService));
    }

    public void performLogout(final Player player) {
        runTask(new AsynchronousLogout(player, plugin, dataSource, processService));
    }

    public void performRegister(final Player player, final String password, final String email) {
        runTask(new AsyncRegister(player, password, email, plugin, dataSource, playerCache, processService));
    }

    public void performUnregister(final Player player, final String password, final boolean force) {
        runTask(new AsynchronousUnregister(player, password, force, plugin, processService));
    }

    public void performJoin(final Player player) {
        runTask(new AsynchronousJoin(player, plugin, dataSource, playerCache, pluginHooks, processService));
    }

    public void performQuit(final Player player, final boolean isKick) {
        runTask(new AsynchronousQuit(player, plugin, dataSource, isKick, processService));
    }

    public void performAddEmail(final Player player, final String newEmail) {
        runTask(new AsyncAddEmail(player, newEmail, dataSource, playerCache, processService));
    }

    public void performChangeEmail(final Player player, final String oldEmail, final String newEmail) {
        runTask(new AsyncChangeEmail(player, oldEmail, newEmail, dataSource, playerCache, processService));
    }

    private void runTask(Process process) {
        sched.runTaskAsynchronously(plugin, process);
    }
}
