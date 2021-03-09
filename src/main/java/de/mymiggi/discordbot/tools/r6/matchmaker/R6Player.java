package de.mymiggi.discordbot.tools.r6.matchmaker;

public class R6Player
{
	public String name;
	public int skillIndex;

	public R6Player(String name, int skillIndex)
	{
		this.name = name;
		this.skillIndex = skillIndex;
	}

	public String getName()
	{
		return name;
	}

	public int getSkillIndex()
	{
		return skillIndex;
	}
}
