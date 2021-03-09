package de.mymiggi.discordbot.corona.rki.country;

public class Response
{
	private int EWZ;
	private double death_rate;
	private int cases;
	private int deaths;
	private double cases_per_population; // in %
	private String BL;
	private String country;
	private String last_update;
	private double cases7_per_100k;
	private int EWZ_BL;
	private int death7_bl;
	private int cases7_lk;
	private int death7_lk;
	private double cases7_bl_per_100k;
	private int cases7_bl;
	private String GEN;

	public int getEWZ()
	{
		return EWZ;
	}

	public void setEWZ(int eWZ)
	{
		EWZ = eWZ;
	}

	public double getDeath_rate()
	{
		return death_rate;
	}

	public void setDeath_rate(double death_rate)
	{
		this.death_rate = death_rate;
	}

	public int getCases()
	{
		return cases;
	}

	public void setCases(int cases)
	{
		this.cases = cases;
	}

	public int getDeaths()
	{
		return deaths;
	}

	public void setDeaths(int deaths)
	{
		this.deaths = deaths;
	}

	public double getCases_per_population()
	{
		return cases_per_population;
	}

	public void setCases_per_population(double cases_per_population)
	{
		this.cases_per_population = cases_per_population;
	}

	public String getBL()
	{
		return BL;
	}

	public void setBL(String bL)
	{
		BL = bL;
	}

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String country)
	{
		this.country = country;
	}

	public String getLast_update()
	{
		return last_update;
	}

	public void setLast_update(String last_update)
	{
		this.last_update = last_update;
	}

	public double getCases7_per_100k()
	{
		return cases7_per_100k;
	}

	public void setCases7_per_100k(double cases7_per_100k)
	{
		this.cases7_per_100k = cases7_per_100k;
	}

	public int getEWZ_BL()
	{
		return EWZ_BL;
	}

	public void setEWZ_BL(int eWZ_BL)
	{
		EWZ_BL = eWZ_BL;
	}

	public int getDeath7_bl()
	{
		return death7_bl;
	}

	public void setDeath7_bl(int death7_bl)
	{
		this.death7_bl = death7_bl;
	}

	public int getCases7_lk()
	{
		return cases7_lk;
	}

	public void setCases7_lk(int cases7_lk)
	{
		this.cases7_lk = cases7_lk;
	}

	public int getDeath7_lk()
	{
		return death7_lk;
	}

	public void setDeath7_lk(int death7_lk)
	{
		this.death7_lk = death7_lk;
	}

	public double getCases7_bl_per_100k()
	{
		return cases7_bl_per_100k;
	}

	public void setCases7_bl_per_100k(double cases7_bl_per_100k)
	{
		this.cases7_bl_per_100k = cases7_bl_per_100k;
	}

	public int getCases7_bl()
	{
		return cases7_bl;
	}

	public void setCases7_bl(int cases7_bl)
	{
		this.cases7_bl = cases7_bl;
	}

	public String getGEN_Simple()
	{
		return GEN.split(" ")[0].toLowerCase();
	}

	public String getGEN()
	{
		return GEN;
	}

	public void setGEN(String gEN)
	{
		GEN = gEN;
	}
}
