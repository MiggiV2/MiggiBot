package de.mymiggi.discordbot.main;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.commands.constructor.SimpleCommandCore;
import de.mymiggi.discordbot.corona.rki.province.automessage.CovidAutoMessage;
import de.mymiggi.discordbot.drivingschool.lessons.reminder.ReminderThread;
import de.mymiggi.discordbot.main.commands.CommandHandler;
import de.mymiggi.discordbot.main.commands.SlashCommandHandler;
import de.mymiggi.discordbot.main.commands.SlashCommandRegisterAction;
import de.mymiggi.discordbot.main.corehelper.ConfigBuilder;
import de.mymiggi.discordbot.server.counter.MemberCounterCore;
import de.mymiggi.discordbot.server.logs.leaving.LeavingLogCore;
import de.mymiggi.discordbot.server.logs.welcomer.WelcomerRunner;
import de.mymiggi.discordbot.server.member.playlist.MemberPlayListCore;
import de.mymiggi.discordbot.server.reaction.role.ReactionRoleRunner;
import de.mymiggi.discordbot.server.untis.reminder.TimeTableReminderCore;

public class BotMainCore
{
	public static final BotConfig config = new ConfigBuilder().run();
	public static String prefix = config.getPrefix();
	public static final DiscordApi api = new DiscordApiBuilder()
		.setToken(config.getBotToken())
		.setAllIntents()
		.login()
		.join();

	private static Logger logger = LoggerFactory.getLogger(BotMainCore.class.getSimpleName());

	private static WelcomerRunner welcomer = new WelcomerRunner();
	private static MemberCounterCore counter = new MemberCounterCore();
	private static LeavingLogCore leavingLogger = new LeavingLogCore();
	private static ReactionRoleRunner reactionRole = new ReactionRoleRunner();
	private static MemberPlayListCore memberPlayListCore = new MemberPlayListCore();
	private static CovidAutoMessage covidAutoMessage = new CovidAutoMessage();
	private static TimeTableReminderCore timeTableReminderCore = new TimeTableReminderCore();
	private static ReminderThread drivingReminder = new ReminderThread();
	private static boolean isRunning = false;
	private static boolean updatedCommands = false;

	public static void run()
	{
		if (!isRunning)
		{
			SimpleCommandCore simpleCommandCore = new SimpleCommandCore();
			simpleCommandCore.run();

			new CommandHandler(api).setCommandList(simpleCommandCore.getCommandList());
			new SlashCommandHandler().run(api);

			welcomer.run();
			counter.run();
			leavingLogger.run();
			reactionRole.run();
			memberPlayListCore.load();
			covidAutoMessage.startThread();
			timeTableReminderCore.run();
			drivingReminder.run();

			printLoadedListSize();

			logger.info("Invite via Url: " + api.createBotInvite());
			// HelpStatusThread.run(api);
		}
		isRunning = true;
	}

	public static boolean updateShlashCommands()
	{
		if (!updatedCommands)
		{
			new SlashCommandRegisterAction().run(api);
			updatedCommands = true;
			return true;
		}
		return false;
	}

	public static DiscordApi getTestAPI()
	{
		return api;
	}

	public static WelcomerRunner getWelcomer()
	{
		return welcomer;
	}

	public static MemberCounterCore getCounter()
	{
		return counter;
	}

	public static LeavingLogCore getLeavingLogger()
	{
		return leavingLogger;
	}

	public static ReactionRoleRunner getReactionRole()
	{
		return reactionRole;
	}

	public static MemberPlayListCore getMemberPlayListCore()
	{
		return memberPlayListCore;
	}

	public static CovidAutoMessage getCovidAutoMessage()
	{
		return covidAutoMessage;
	}

	public static TimeTableReminderCore getTimeTableReminderCore()
	{
		return timeTableReminderCore;
	}

	private static void printLoadedListSize()
	{
		logger.info("Counter loaded " + counter.getListSize() + " items!");
		logger.info("Welcomer loaded " + welcomer.getListSize() + " items!");
		logger.info("LeavingLogger loaded " + leavingLogger.getListSize() + " items!");
		logger.info("ReactionRole loaded " + reactionRole.getListSize() + " items!");
		logger.info("MemberPlaylist loaded " + memberPlayListCore.loadedPlaylists() + " playlists!");
	}
}
