package de.mymiggi.discordbot.music.fresh.entitys;

import java.util.ArrayList;
import java.util.List;

public class FreshSongsWrapper
{
	private boolean showinfo;
	private boolean realtime;
	private FreshSong now;
	private List<FreshSong> history;

	public FreshSongsWrapper(boolean showinfo, boolean realtime, FreshSong now, List<FreshSong> history)
	{
		this.showinfo = showinfo;
		this.realtime = realtime;
		this.now = now;
		this.history = history;
	}

	public FreshSongsWrapper()
	{
	}

	public boolean isShowinfo()
	{
		return showinfo;
	}

	public void setShowinfo(boolean showinfo)
	{
		this.showinfo = showinfo;
	}

	public boolean isRealtime()
	{
		return realtime;
	}

	public void setRealtime(boolean realtime)
	{
		this.realtime = realtime;
	}

	public FreshSong getNow()
	{
		return now;
	}

	public void setNow(FreshSong now)
	{
		this.now = now;
	}

	public List<FreshSong> getHistory()
	{
		return history;
	}

	public void setHistory(List<FreshSong> history)
	{
		this.history = history;
	}

	public List<FreshSong> getSongs()
	{
		List<FreshSong> songs = new ArrayList<FreshSong>();
		history
			.stream()
			.filter(temp -> temp.isMusic())
			.forEach(temp -> songs.add(temp));
		songs.add(now);
		return songs;
	}
}
