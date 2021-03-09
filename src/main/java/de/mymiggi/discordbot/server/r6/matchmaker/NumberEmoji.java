package de.mymiggi.discordbot.server.r6.matchmaker;

public enum NumberEmoji
{
	ONE
	{
		@Override
		public int getNumber()
		{
			return 1;
		}

		@Override
		public String getEmoji()
		{
			return "1️⃣";
		}
	},
	TOW
	{
		@Override
		public int getNumber()
		{
			return 2;
		}

		@Override
		public String getEmoji()
		{
			return "2️⃣";
		}
	},
	THREE
	{
		@Override
		public int getNumber()
		{
			return 3;
		}

		@Override
		public String getEmoji()
		{
			return "3️⃣";
		}
	},
	FOUR
	{
		@Override
		public int getNumber()
		{
			return 4;
		}

		@Override
		public String getEmoji()
		{
			return "4️⃣";
		}
	},
	FIVE
	{
		@Override
		public int getNumber()
		{
			return 5;
		}

		@Override
		public String getEmoji()
		{
			return "5️⃣";
		}
	},
	SIX
	{
		@Override
		public int getNumber()
		{
			return 6;
		}

		@Override
		public String getEmoji()
		{
			return "6️⃣";
		}
	},
	SEVEN
	{
		@Override
		public int getNumber()
		{
			return 7;
		}

		@Override
		public String getEmoji()
		{
			return "7️⃣";
		}
	};

	public abstract int getNumber();

	public abstract String getEmoji();
}
