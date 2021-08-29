package de.mymiggi.discordbot.music.fresh.entitys;

import java.util.Map;

public class FreshSong
{
	private String subclass;
	private String artist;
	private String title;
	private int starttime;
	private Map<String, String> images;
	private String image;

	public FreshSong(String subclass, String artist, String title, int starttime, Map<String, String> images, String image)
	{
		this.subclass = subclass;
		this.artist = artist;
		this.title = title;
		this.starttime = starttime;
		this.images = images;
		this.image = image;
	}

	public FreshSong()
	{
	}

	public String getSubclass()
	{
		return subclass;
	}

	public void setSubclass(String subclass)
	{
		this.subclass = subclass;
	}

	public String getArtist()
	{
		return artist;
	}

	public void setArtist(String artist)
	{
		this.artist = artist;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getStarttime()
	{
		return starttime;
	}

	public void setStarttime(int starttime)
	{
		this.starttime = starttime;
	}

	public Map<String, String> getImages()
	{
		return images;
	}

	public void setImages(Map<String, String> images)
	{
		this.images = images;
	}

	public String getImage()
	{
		return image;
	}

	public void setImage(String image)
	{
		this.image = image;
	}

	public boolean isMusic()
	{
		return (this.subclass == null) ? false : this.subclass.equals("music");
	}

	public String getSearchQuery()
	{
		return "ytsearch: " + this.getTitle() + this.getArtist();
	}
}
