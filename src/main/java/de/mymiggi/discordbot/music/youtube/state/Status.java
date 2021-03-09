package de.mymiggi.discordbot.music.youtube.state;

public enum Status
{
	STOPPED
	{
		@Override
		public String getStatusEmoji()
		{
			return "🚫";
		}
	},
	PLAYING
	{
		@Override
		public String getStatusEmoji()
		{
			return "▶️";
		}
	},
	PAUSED
	{
		@Override
		public String getStatusEmoji()
		{
			return "⏸";
		}
	};

	public abstract String getStatusEmoji();
}
