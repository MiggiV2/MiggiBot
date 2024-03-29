package de.mymiggi.discordbot.main.commands;

import java.awt.Color;
import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.bitcoin.BitCoinCommand;
import de.mymiggi.discordbot.commands.constructor.Command;
import de.mymiggi.discordbot.commands.simple.CommandHelp;
import de.mymiggi.discordbot.commands.simple.PingTest;
import de.mymiggi.discordbot.commands.simple.PowerOff;
import de.mymiggi.discordbot.commands.simple.Purger;
import de.mymiggi.discordbot.commands.simple.ServerInfo;
import de.mymiggi.discordbot.corona.covid19.CoronaAPI;
import de.mymiggi.discordbot.corona.rki.country.RKICountry;
import de.mymiggi.discordbot.corona.rki.province.RKIProvince;
import de.mymiggi.discordbot.corona.rki.province.automessage.NewCovidChannelConfig;
import de.mymiggi.discordbot.drivingschool.lessons.DrivingLessonsCore;
import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.MusicCore;
import de.mymiggi.discordbot.music.youtube.MusicHelper;
import de.mymiggi.discordbot.server.counter.NewCounterCreator;
import de.mymiggi.discordbot.server.logs.leaving.NewLeavingLog;
import de.mymiggi.discordbot.server.logs.welcomer.NewWelcomerCreater;
import de.mymiggi.discordbot.server.member.playlist.MemberPlayListCore;
import de.mymiggi.discordbot.server.r6.addplayer.StartMessageListener;
import de.mymiggi.discordbot.server.r6.map.RandomR6MapCore;
import de.mymiggi.discordbot.server.r6.matchmaker.DiscordMatchMakerCore;
import de.mymiggi.discordbot.server.reaction.role.newconfig.StartListening;
import de.mymiggi.discordbot.server.tenor.gif.PostRandomGitAction;
import de.mymiggi.discordbot.server.untis.UntisCommandHelper;
import de.mymiggi.discordbot.server.untis.reminder.manager.NewReminderChannel;
import de.mymiggi.discordbot.server.untis.timetable.TimeTableCore;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class CommandHandler
{
	public static final MusicCore MUSIC_CORE = new MusicCore();
	private MemberPlayListCore memberPlayListCore = BotMainCore.getMemberPlayListCore();
	private NewCovidChannelConfig newCovidChannelConfiger = new NewCovidChannelConfig();
	public static final RandomR6MapCore R6_MAP_CORE = new RandomR6MapCore();
	public static final RKICountry RKI_COUNTRY = new RKICountry();
	public static final RKIProvince RKI_PROVINCE = new RKIProvince();
	private CoronaAPI coronaAPI = new CoronaAPI();
	private final String prefix = BotMainCore.prefix;
	private DiscordApi api;
	private ArrayList<Command> list = new ArrayList<Command>();
	private Logger logger = LoggerFactory.getLogger(CommandHandler.class.getSimpleName());

	public CommandHandler(DiscordApi api)
	{
		this.api = api;

		api.addMessageCreateListener(event -> {
			runCommand(event);
		});
	}

	public void runCommand(MessageCreateEvent event)
	{
		String input = event.getMessageContent();
		String[] context = input.split(" ");

		if (!context[0].startsWith(prefix))
		{
			return;
		}

		if (context[0].length() < 3)
		{
			return;
		}
		String command = context[0].substring(2);

		switch (command)
		{
			case "PowerOff":
				new PowerOff().run(event, api);
				break;
			case "lookup":
				deprecatedEmbed(event);
				break;
			case "purge":
				new Purger().clear(event, context);
				break;
			case "info":
				new ServerInfo().get(event);
				break;
			case "play":
				MUSIC_CORE.play(event, context, false, false, false);
				break;
			case "stop":
				MUSIC_CORE.stop(event);
				break;
			case "skip":
				MUSIC_CORE.skip(event, context);
				break;
			case "s":
				MUSIC_CORE.skip(event, context);
				break;
			case "pause":
				MUSIC_CORE.pause(event);
				break;
			case "p":
				MUSIC_CORE.pause(event);
				break;
			case "resume":
				MUSIC_CORE.resume(event);
				break;
			case "queue":
				MUSIC_CORE.queue(event, false);
				break;
			case "shuffle":
				MUSIC_CORE.shuffle(event);
				break;
			case "playList":
				MUSIC_CORE.play(event, context, true, false, false);
				break;
			case "push":
				MUSIC_CORE.play(event, context, false, true, false);
				break;
			case "clear":
				MUSIC_CORE.clear(event);
				break;
			case "loop":
				MUSIC_CORE.loop(event);
				break;
			case "party":
				MUSIC_CORE.playMemberPlayList(event);
				break;
			case "sharedParty":
				MUSIC_CORE.playSharedPlayList(event, context);
				break;
			case "move":
				MUSIC_CORE.moveToVoiceChannel(event);
				break;
			// case "fresh":
			// musicCore.playFreshPlayList(event);
			// break;
			case "music":
				new MusicHelper().send(event);
				break;
			case "welcome":
				new NewWelcomerCreater().add(event, context);
				break;
			case "counter":
				new NewCounterCreator().add(event, context);
				break;
			case "corona":
				coronaAPI.get(event);
				break;
			case "covid19":
				RKI_PROVINCE.send(event);
				break;
			case "country":
				RKI_COUNTRY.send(event, context);
				break;
			case "countryHelp":
				RKI_COUNTRY.sendHelpEmbed(event);
				break;
			case "covidChannel":
				newCovidChannelConfiger.run(event, context);
				break;
			case "leavingLog":
				new NewLeavingLog().add(event);
				break;
			case "help":
				new CommandHelp().send(event, context);
				break;
			case "reactionRole":
				new StartListening().run(event, context);
				break;
			case "create":
				memberPlayListCore.createNewPlayListInfo(event, context);
				break;
			case "add":
				memberPlayListCore.addSong(event, context);
				break;
			case "join":
				memberPlayListCore.joinPlaylist(event, context);
				break;
			case "see":
				memberPlayListCore.sendCurrentPlayList(event);
				break;
			case "rm":
				memberPlayListCore.deleteSongFromPlayList(event, context);
				break;
			case "delete":
				memberPlayListCore.deleteSongFromPlayList(event, context);
				break;
			case "del":
				memberPlayListCore.deleteSongFromPlayList(event, context);
				break;
			case "delPlayList":
				memberPlayListCore.deleteEntirePlayList(event);
				break;
			case "rmPlayList":
				memberPlayListCore.deleteEntirePlayList(event);
				break;
			case "deletePlayList":
				memberPlayListCore.deleteEntirePlayList(event);
				break;
			case "playlists":
				memberPlayListCore.sendAllPlayListsEmbed(event);
				break;
			case "publish":
				memberPlayListCore.updatePlayListPublishStatus(event, true);
				break;
			case "hide":
				memberPlayListCore.updatePlayListPublishStatus(event, false);
				break;
			case "check":
				memberPlayListCore.sendAllPublishedPlayListsEmbed(event);
				break;
			case "view":
				memberPlayListCore.viewForeignPlaylist(event, context);
				break;
			case "helpPlaylist":
				deprecatedEmbed(event);
				break;
			case "newPlayer":
				new StartMessageListener().run(event);
				break;
			case "addUntisReminder":
				new NewReminderChannel().add(event, context);
				break;
			case "nächsteStunde":
				BotMainCore.getTimeTableReminderCore().nextSubjectEmbed(event);
				break;
			case "TimeTable":
				new TimeTableCore().run(event);
				break;
			case "untisHelp":
				new UntisCommandHelper().run(event);
				break;
			case "match":
				new DiscordMatchMakerCore().run(event);
				break;
			case "randomMap":
				deprecatedEmbed(event);
				break;
			case "listMaps":
				deprecatedEmbed(event);
				break;
			case "updateMaps":
				deprecatedEmbed(event);
				break;
			case "addNewMap":
				deprecatedEmbed(event);
				break;
			case "R6Help":
				deprecatedEmbed(event);
				break;
			case "gif":
				new PostRandomGitAction().run(event, context);
				break;
			case "ping":
				new PingTest().run(event);
				break;
			case "driving":
				new DrivingLessonsCore().beginSavingAction(event);
				break;
			case "deleteDriving":
				new DrivingLessonsCore().remove(event);
				break;
			case "BitCoin":
				new BitCoinCommand().run(event);
				break;
			case "r6Stats":
				deprecatedEmbed(event);
				break;
			case "r6Highlight":
				deprecatedEmbed(event);
				break;
			case "r6Rank":
				deprecatedEmbed(event);
				break;
		}
		for (Command temp : list)
		{
			String tempCommandStr = prefix + temp.getCommandName();
			if (tempCommandStr.equals(command))
			{
				event.getChannel().sendMessage(temp.getEmbed());
				logger.info(event.getMessageAuthor().getName() + " used command!");
				break;
			}
		}
	}

	public void setCommandList(ArrayList<Command> list)
	{
		this.list = list;
	}

	public void deprecatedEmbed(MessageCreateEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder()
			.setTitle("Sorry, but this command deprecated! Use the SlashCommand instead!")
			.setColor(Color.RED);
		event.getChannel().sendMessage(embed).thenAccept(embedMessage -> {
			MessageCoolDown.del(embedMessage.getLink().toString(), event.getChannel(), 12);
			MessageCoolDown.del(event.getMessage().getLink().toString(), event.getChannel(), 12);
		});
	}
}
