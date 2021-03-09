package de.mymiggi.discordbot.corona.rki.province;

public class Response
{
	private int Fallzahl;
	private String LAN_ew_GEN;
	private int Death;
	private int cases7_bl;
	private int death7_bl;
	private double cases7_bl_per_100k;
	private long Aktualisierung;

	public int getFallzahl()
	{
		return Fallzahl;
	}

	public void setFallzahl(int fallzahl)
	{
		Fallzahl = fallzahl;
	}

	public String getBundesLand()
	{
		return LAN_ew_GEN;
	}

	public void setLAN_ew_GEN(String lAN_ew_GEN)
	{
		LAN_ew_GEN = lAN_ew_GEN;
	}

	public int getDeath()
	{
		return Death;
	}

	public void setDeath(int death)
	{
		Death = death;
	}

	public int getCases7_bl()
	{
		return cases7_bl;
	}

	public void setCases7_bl(int cases7_bl)
	{
		this.cases7_bl = cases7_bl;
	}

	public int getDeath7_bl()
	{
		return death7_bl;
	}

	public void setDeath7_bl(int death7_bl)
	{
		this.death7_bl = death7_bl;
	}

	public long getAktualisierung()
	{
		return Aktualisierung;
	}

	public void setAktualisierung(long aktualisierung)
	{
		Aktualisierung = aktualisierung;
	}

	public double getCases7_bl_per_100k()
	{
		return cases7_bl_per_100k;
	}

	public void setCases7_bl_per_100k(double cases7_bl_per_100k)
	{
		this.cases7_bl_per_100k = cases7_bl_per_100k;
	}
}
