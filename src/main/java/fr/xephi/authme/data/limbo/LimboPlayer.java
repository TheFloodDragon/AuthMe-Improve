package fr.xephi.authme.data.limbo;

import com.github.Anon8281.universalScheduler.scheduling.tasks.MyScheduledTask;
import fr.xephi.authme.task.MessageTask;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a player which is not logged in and keeps track of certain states (like OP status, flying)
 * which may be revoked from the player until he has logged in or registered.
 */
public class LimboPlayer {

    public static final float DEFAULT_WALK_SPEED = 0.2f;
    public static final float DEFAULT_FLY_SPEED = 0.1f;

    private final boolean canFly;
    private final boolean operator;
    private final Collection<UserGroup> groups;
    private final Location loc;
    private final float walkSpeed;
    private final float flySpeed;
    private MyScheduledTask timeoutTask = null;
    private MessageTask messageTask = null;
    private MyScheduledTask inter;

    private LimboPlayerState state = LimboPlayerState.PASSWORD_REQUIRED;

    public LimboPlayer(Location loc, boolean operator, Collection<UserGroup> groups, boolean fly, float walkSpeed,
                       float flySpeed) {
        this.loc = loc;
        this.operator = operator;
        this.groups = new ArrayList<>(groups); // prevent bug #2413
        this.canFly = fly;
        this.walkSpeed = walkSpeed;
        this.flySpeed = flySpeed;
    }

    /**
     * Return the player's original location.
     *
     * @return The player's location
     */
    public Location getLocation() {
        return loc;
    }

    /**
     * Return whether the player is an operator or not (i.e. whether he is an OP).
     *
     * @return True if the player has OP status, false otherwise
     */
    public boolean isOperator() {
        return operator;
    }

    /**
     * Return the player's permissions groups.
     *
     * @return The permissions groups the player belongs to
     */
    public Collection<UserGroup> getGroups() {
        return groups;
    }

    public boolean isCanFly() {
        return canFly;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public float getFlySpeed() {
        return flySpeed;
    }

    /**
     * Return the timeout task, which kicks the player if he hasn't registered or logged in
     * after a configurable amount of time.
     *
     * @return The timeout task associated to the player
     */
    public MyScheduledTask getTimeoutTask() {
        return timeoutTask;
    }

    /**
     * Set the timeout task of the player. The timeout task kicks the player after a configurable
     * amount of time if he hasn't logged in or registered.
     *
     * @param timeoutTask The task to set
     */
    public void setTimeoutTask(MyScheduledTask timeoutTask) {
        if (this.timeoutTask != null) {
            this.timeoutTask.cancel();
        }
        this.timeoutTask = timeoutTask;
    }

    /**
     * Return the message task reminding the player to log in or register.
     *
     * @return The task responsible for sending the message regularly
     */
    public MessageTask getMessageTask() {
        return messageTask;
    }

    /**
     * Set the messages task responsible for telling the player to log in or register.
     *
     * @param messageTask The message task to set
     */
    public void setMessageTask(MessageTask messageTask, MyScheduledTask inter) {
        if (this.messageTask != null && this.inter != null) {
            this.inter.cancel();
        }
        this.messageTask = messageTask;
        this.inter = inter;
    }

    /**
     * Clears all tasks associated to the player.
     */
    public void clearTasks() {
        setMessageTask(null, null);
        setTimeoutTask(null);
    }

    public LimboPlayerState getState() {
        return state;
    }

    public void setState(LimboPlayerState state) {
        this.state = state;
    }
}
