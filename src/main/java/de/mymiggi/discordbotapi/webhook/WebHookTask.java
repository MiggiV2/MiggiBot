package de.mymiggi.discordbotapi.webhook;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import de.mymiggi.discordbotapi.util.WebhookMessage;
import de.mymiggi.discordbotapi.webhook.actions.UpdateMcPlayerAction;

public class WebHookTask
{
	public Response run(String json)
	{
		WebhookMessage message = new Gson().fromJson(json, WebhookMessage.class);
		if (message.getWebhookID().equals("cheTroSToyUtRUvusefr_weZonLxOd#fr9thib#inezoklHoyucr$drothaZimUW"))
		{
			boolean success = new UpdateMcPlayerAction().run(json);
			return (success ? Response.ok().build() : Response.status(Status.BAD_REQUEST).build());
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
