package de.mymiggi.r6.stats.wrapper.actions.helper;

import de.mymiggi.r6.stats.wrapper.R6Rank;

public class GetRankedNameByID
{
	public R6Rank get(int id) throws Exception
	{
		int currentID = 0;
		for (R6Rank tempRank : R6Rank.values())
		{
			if (currentID == id)
			{
				return tempRank;
			}
			currentID++;
		}
		throw new Exception("Rank not found! ID = " + id);
	}
}
