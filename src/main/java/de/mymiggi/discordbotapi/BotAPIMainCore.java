package de.mymiggi.discordbotapi;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbotapi.util.DiscordServerResponse;

@Path("/bot")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BotAPIMainCore
{
	@GET
	@Path("/start")
	public Response start()
	{
		BotMainCore.run();
		return Response.ok().status(201).build();
	}

	@GET
	@Path("/vc")
	public List<DiscordServerResponse> getVC()
	{
		List<DiscordServerResponse> connectedVC = new ArrayList<DiscordServerResponse>();
		BotMainCore.api.getYourself()
			.getConnectedVoiceChannels()
			.forEach(vc -> {
				connectedVC.add(new DiscordServerResponse(vc.getServer()));
			});
		return connectedVC;
	}

	@GET
	@Path("/server")
	public List<DiscordServerResponse> getDiscordServer()
	{
		List<DiscordServerResponse> connectedVC = new ArrayList<DiscordServerResponse>();
		BotMainCore.api
			.getServers()
			.forEach(server -> {
				connectedVC.add(new DiscordServerResponse(server));
			});
		return connectedVC;
	}
}
