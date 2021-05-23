package de.mymiggi.r6.stats.wrapper;

public enum PlatfromType
{
	UPLAY
	{
		@Override
		public String getType()
		{
			return "uplay";
		}

		@Override
		public int getID()
		{
			return 0;
		}

		@Override
		public String getTypeForStats()
		{
			return "PC";
		}
	},
	PLAYSTATION
	{
		@Override
		public String getType()
		{
			return "psn";
		}

		@Override
		public int getID()
		{
			return 1;
		}

		@Override
		public String getTypeForStats()
		{
			return "PS4";
		}
	},
	XBOX
	{
		@Override
		public String getType()
		{
			return "xb1";
		}

		@Override
		public int getID()
		{
			return 2;
		}

		@Override
		public String getTypeForStats()
		{
			return "XONE";
		}
	};

	public abstract String getType();

	public abstract int getID();

	public abstract String getTypeForStats();
}
