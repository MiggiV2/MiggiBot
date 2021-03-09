package de.mymiggi.tenor.util;

import java.util.Map;

public class TenorGif
{
	private String[] tags;
	private String url;
	private float created;
	private String itemurl;
	private Map<String, TenorMediaRespone>[] media;

	public String[] getTags()
	{
		return tags;
	}

	public void setTags(String[] tags)
	{
		this.tags = tags;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public float getCreated()
	{
		return created;
	}

	public void setCreated(float created)
	{
		this.created = created;
	}

	public String getItemurl()
	{
		return itemurl;
	}

	public void setItemurl(String itemurl)
	{
		this.itemurl = itemurl;
	}

	public Map<String, TenorMediaRespone>[] getMedia()
	{
		return media;
	}

	public void setMedia(Map<String, TenorMediaRespone>[] media)
	{
		this.media = media;
	}

}
