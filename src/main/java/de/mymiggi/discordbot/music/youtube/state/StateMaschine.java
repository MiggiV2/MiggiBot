package de.mymiggi.discordbot.music.youtube.state;

public class StateMaschine
{
	private Status[] statusArray;

	public StateMaschine()
	{
		statusArray = Status.values();
	}

	public Status getStatusByEmoji(String emoijStr) throws Exception
	{
		for (Status status : statusArray)
		{
			if (status.getStatusEmoji().equals(emoijStr))
			{
				return status;
			}
		}
		throw new Exception("Emoji not in status List!");
	}
}
