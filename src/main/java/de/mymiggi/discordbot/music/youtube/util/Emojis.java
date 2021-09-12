package de.mymiggi.discordbot.music.youtube.util;

public enum Emojis
{
	SUN_GLASSES
	{
		@Override
		public String getEmoji()
		{
			return "😎";
		}
	},
	REWIND
	{
		@Override
		public String getEmoji()
		{
			return "⏪";
		}
	},
	FAST_FORWARD
	{
		@Override
		public String getEmoji()
		{
			return "⏩";
		}
	},
	ARROW_FORWARD
	{
		@Override
		public String getEmoji()
		{
			return "▶️";
		}
	},
	WAVE
	{
		@Override
		public String getEmoji()
		{
			return "👋";
		}
	},
	TWISTED_RIGHTWARDS_ARROWS
	{
		@Override
		public String getEmoji()
		{
			return "🔀";
		}
	},
	PENCIL
	{
		@Override
		public String getEmoji()
		{
			return "📝";
		}
	},
	NO_ENTRY_SIGN
	{
		@Override
		public String getEmoji()
		{
			return "🚫";
		}
	},
	PAUSE_BUTTON
	{
		@Override
		public String getEmoji()
		{
			return "⏸";
		}
	},
	THUMPS_UP
	{
		@Override
		public String getEmoji()
		{
			return "👍";
		}
	},
	LOOP_BUTTON
	{
		@Override
		public String getEmoji()
		{
			return "🔄";
		}
	},
	GREEN_CHECK
	{
		@Override
		public String getEmoji()
		{
			return "✅";
		}
	};
	public abstract String getEmoji();
}
