package de.mymiggi.discordbot.server.untis.timetable.reactions;

import java.time.LocalDate;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.reaction.ReactionAddEvent;

import de.mymiggi.discordbot.server.untis.timetable.TimeTableEmbed;

public class TimeTableReactionHandler
{
	private LocalDate date;

	public TimeTableReactionHandler(LocalDate date)
	{
		this.date = date;
	}

	public void run(ReactionAddEvent reactionAddEvent)
	{
		if (!reactionAddEvent.getUser().get().isYourself())
		{
			if (reactionAddEvent.getEmoji().equalsEmoji("❌"))
			{
				reactionAddEvent.getMessage().ifPresent(message -> {
					if (message.canYouDelete())
					{
						message.delete();
					}
				});
			}
			else
			{
				if (reactionAddEvent.getEmoji().equalsEmoji("◀️") && !date.getDayOfWeek().toString().equals("MONDAY"))
				{
					date = date.minusDays(1);
					EmbedBuilder embed = new TimeTableEmbed().build(date);
					reactionAddEvent.editMessage(embed);
				}
				if (reactionAddEvent.getEmoji().equalsEmoji("▶️") && date.getDayOfWeek().getValue() < 5)
				{
					date = date.plusDays(1);
					EmbedBuilder embed = new TimeTableEmbed().build(date);
					reactionAddEvent.editMessage(embed);
				}
				reactionAddEvent.getMessage().ifPresent(message -> {
					message.removeReactionsByEmoji(reactionAddEvent.getUser().get(), reactionAddEvent.getEmoji());
				});
			}
		}
	}
}
