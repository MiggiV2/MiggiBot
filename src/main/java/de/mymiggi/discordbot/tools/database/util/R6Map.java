package de.mymiggi.discordbot.tools.database.util;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class R6Map implements Serializable
{
	@Id
	private String name;
	private static final long serialVersionUID = 5143551628134030774L;
	private String imageURL;
	private boolean rankedPool;

	public String getImageURL()
	{
		return imageURL;
	}

	public void setImageURL(String imageURL)
	{
		this.imageURL = imageURL;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isRankedPool()
	{
		return rankedPool;
	}

	public void setRankedPool(boolean rankedPool)
	{
		this.rankedPool = rankedPool;
	}
}
