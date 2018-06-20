package me.ryanhamshire.GriefPrevention.command;

import me.ryanhamshire.GriefPrevention.claim.Claim;
import me.ryanhamshire.GriefPrevention.claim.ClaimClerk;
import me.ryanhamshire.GriefPrevention.claim.ClaimPermission;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

/**
 * Created on 6/16/2018.
 *
 * @author RoboMWM
 */
public class BuildTrustCommand extends ClaimManagementCommands
{
    private JavaPlugin plugin;

    public BuildTrustCommand(ClaimClerk claimClerk)
    {
        super(claimClerk);
    }

    @Override
    public boolean execute(Player player, String[] args, Claim claim)
    {
        if (args.length == 0)
            return false;

        Map<UUID, ClaimPermission> trustees = claim.getTrustees();

        for (String playerName : args)
        {
            OfflinePlayer trustee = plugin.getServer().getOfflinePlayer(playerName);
            if (trustee.getUniqueId() == null)
            {
                player.sendMessage(playerName + " is not a valid name." + "");
                continue;
            }

            trustees.put(trustee.getUniqueId(), ClaimPermission.BUILD);
            player.sendMessage("Trusted " + trustee + " to this claim.");
        }

        if (!claimClerk.changeTrustees(claim, trustees))
        {
            player.sendMessage("Failed to save claim.");
        }

        return true;
    }
}
