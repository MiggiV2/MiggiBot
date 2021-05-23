package de.mymiggi.r6.stats.wrapper;

public enum RankedRegions
{
	EMEA
	{
		@Override
		public String getCode()
		{
			return "emea";
		}
	},
	NCSA
	{
		@Override
		public String getCode()
		{
			return "ncsa";
		}
	},
	APAC
	{
		@Override
		public String getCode()
		{
			return "apac";
		}
	};

	abstract public String getCode();
}
