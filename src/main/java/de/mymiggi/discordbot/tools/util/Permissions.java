package de.mymiggi.discordbot.tools.util;

import java.util.List;

import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class Permissions
{
	public static boolean isAdmin(MessageCreateEvent event)
	{
		return isAdmin(event.getMessageAuthor().asUser().get(), event.getServer().get());
	}

	public static boolean isAdmin(User user, Server server)
	{
		long ownerID = server.getOwnerId();
		long userID = user.getId();

		if (ownerID == userID)
		{
			return true;
		}
		List<Role> roles = user.getRoles(server);

		for (Role temp : roles)
		{
			if (temp.getAllowedPermissions().contains(PermissionType.ADMINISTRATOR))
			{
				return true;
			}
		}
		return false;
	}
}
