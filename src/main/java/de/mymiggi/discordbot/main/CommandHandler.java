package de.mymiggi.discordbot.main;

import java.awt.Color;
import java.util.ArrayList;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.commands.constructor.Command;
import de.mymiggi.discordbot.commands.simple.CommandHelp;
import de.mymiggi.discordbot.commands.simple.IPInfoCommand;
import de.mymiggi.discordbot.commands.simple.PingTest;
import de.mymiggi.discordbot.commands.simple.PowerOff;
import de.mymiggi.discordbot.commands.simple.Purger;
import de.mymiggi.discordbot.commands.simple.ServerInfo;
import de.mymiggi.discordbot.corona.covid19.CoronaAPI;
import de.mymiggi.discordbot.corona.rki.country.RKICountry;
import de.mymiggi.discordbot.corona.rki.province.RKIProvince;
import de.mymiggi.discordbot.corona.rki.province.automessage.NewCovidChannelConfig;
import de.mymiggi.discordbot.drivingschool.lessons.DrivingLessonsCore;
import de.mymiggi.discordbot.music.youtube.MusicCore;
import de.mymiggi.discordbot.music.youtube.MusicHelper;
import de.mymiggi.discordbot.server.counter.NewCounterCreator;
import de.mymiggi.discordbot.server.logs.leaving.NewLeavingLog;
import de.mymiggi.discordbot.server.logs.welcomer.NewWelcomerCreater;
import de.mymiggi.discordbot.server.member.playlist.MemberPlayListCore;
import de.mymiggi.discordbot.server.member.playlist.help.MemberPlayListHelper;
import de.mymiggi.discordbot.server.r6.R6CommandHelper;
import de.mymiggi.discordbot.server.r6.addplayer.StartMessageListener;
import de.mymiggi.discordbot.server.r6.map.RandomR6MapCore;
import de.mymiggi.discordbot.server.r6.matchmaker.DiscordMatchMakerCore;
import de.mymiggi.discordbot.server.reaction.role.newconfig.StartListening;
import de.mymiggi.discordbot.server.tenor.gif.PostRandomGitAction;
import de.mymiggi.discordbot.server.untis.UntisCommandHelper;
import de.mymiggi.discordbot.server.untis.reminder.manager.NewReminderChannel;
import de.mymiggi.discordbot.server.untis.timetable.TimeTableCore;
import de.mymiggi.discordbot.tools.UpdateDBToNewObject;
import de.mymiggi.discordbot.tools.util.MessageCoolDown;

public class CommandHandler
{
	private MusicCore musicCore = new MusicCore();
	private MemberPlayListCore memberPlayListCore = BotMainCore.getMemberPlayListCore();
	private NewCovidChannelConfig newCovidChannelConfiger = new NewCovidChannelConfig();
	private RandomR6MapCore randomR6MapCore = new RandomR6MapCore();

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
			// case "Rest":
			// new RestMessages().sendVisiCard(event);
			// break;
			// case "read":
			// disabelEmbed(event);
			// new RestMessages().read(event);
			// break;
			// case "send":
			// disabelEmbed(event);
			// new RestMessages().send(event, context);
			// break;
			case "lookup":
				new IPInfoCommand().send(event, context);
				break;
			case "purge":
				new Purger().clear(event, context);
				break;
			case "info":
				new ServerInfo().get(event);
				break;
			case "play":
				musicCore.play(event, context, false, false, false);
				break;
			case "stop":
				musicCore.stop(event);
				break;
			case "skip":
				musicCore.skip(event, context);
				break;
			case "s":
				musicCore.skip(event, context);
				break;
			case "pause":
				musicCore.pause(event);
				break;
			case "p":
				musicCore.pause(event);
				break;
			case "resume":
				musicCore.resume(event);
				break;
			case "queue":
				musicCore.queue(event, false);
				break;
			case "shuffle":
				musicCore.shuffle(event);
				break;
			case "playList":
				musicCore.play(event, context, true, false, false);
				break;
			case "push":
				musicCore.play(event, context, false, true, false);
				break;
			case "clear":
				musicCore.clear(event);
				break;
			case "loop":
				musicCore.loop(event);
				break;
			case "party":
				musicCore.playMemberPlayList(event);
				break;
			case "sharedParty":
				musicCore.playSharedPlayList(event, context);
				break;
			case "move":
				musicCore.moveToVoiceChannel(event);
				break;
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
				new CoronaAPI().get(event);
				break;
			case "covid19":
				new RKIProvince().send(event);
				break;
			case "country":
				new RKICountry().send(event, context);
				break;
			case "countryHelp":
				new RKICountry().sendHelpEmbed(event);
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
				new MemberPlayListHelper().run(event);
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
				randomR6MapCore.get(event, context);
				break;
			case "listMaps":
				randomR6MapCore.list(event);
				break;
			case "updateMaps":
				randomR6MapCore.update(event, context);
				break;
			case "addNewMap":
				randomR6MapCore.add(event);
				break;
			case "R6Help":
				new R6CommandHelper().run(event);
				break;
			case "gif":
				new PostRandomGitAction().run(event, context);
				break;
			case "ping":
				new PingTest().run(event);
				break;
			case "convert":
				new UpdateDBToNewObject().run(event);
				break;
			case "driving":
				new DrivingLessonsCore().beginSavingAction(event);
				break;
			case "deleteDriving":
				new DrivingLessonsCore().remove(event);
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

	public void prefixHint()
	{
		System.out.println("Prefix " + prefix + " can be found in Class CommandHandler");
	}

	public void disabelEmbed(MessageCreateEvent event)
	{
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Sorry, but this command is disabled!")
			.setColor(Color.RED);
		event.getChannel().sendMessage(embed).thenAccept(embedMessage -> {
			MessageCoolDown.del(embedMessage.getLink().toString(),
				event.getChannel(), 12);
			MessageCoolDown.del(event.getMessage().getLink().toString(),
				event.getChannel(), 12);
		});
	}
}
