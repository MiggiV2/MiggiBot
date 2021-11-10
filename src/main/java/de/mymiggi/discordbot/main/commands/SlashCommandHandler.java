package de.mymiggi.discordbot.main.commands;

import org.javacord.api.DiscordApi;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mymiggi.discordbot.commands.simple.CommandHelp;
import de.mymiggi.discordbot.commands.simple.IPInfoCommand;
import de.mymiggi.discordbot.commands.simple.PingTest;
import de.mymiggi.discordbot.commands.simple.PowerOff;
import de.mymiggi.discordbot.commands.simple.Purger;
import de.mymiggi.discordbot.commands.simple.ServerInfo;
import de.mymiggi.discordbot.main.BotMainCore;
import de.mymiggi.discordbot.music.youtube.MusicCore;
import de.mymiggi.discordbot.music.youtube.MusicHelper;
import de.mymiggi.discordbot.server.member.playlist.MemberPlayListCore;
import de.mymiggi.discordbot.server.member.playlist.help.MemberPlayListHelper;
import de.mymiggi.discordbot.server.r6.R6CommandHelper;
import de.mymiggi.discordbot.server.r6.map.RandomR6MapCore;
import de.mymiggi.discordbot.server.r6.stats.R6StatsCommandCore;
import de.mymiggi.discordbot.server.untis.UntisCommandHelper;
import de.mymiggi.discordbot.server.untis.timetable.TimeTableCore;

public class SlashCommandHandler
{
	private static Logger logger = LoggerFactory.getLogger(SlashCommandHandler.class.getSimpleName());
	private R6StatsCommandCore r6StatsCommandCore = new R6StatsCommandCore();
	private RandomR6MapCore r6MapCore = CommandHandler.R6_MAP_CORE;

	public void run(DiscordApi api)
	{
		api.addSlashCommandCreateListener(event -> {
			MusicCore musicCore = CommandHandler.MUSIC_CORE;
			MemberPlayListCore memberPlayListCore = BotMainCore.getMemberPlayListCore();
			SlashCommandInteraction interaction = event.getSlashCommandInteraction();
			String name = interaction.getCommandName();
			logger.info("Got command: " + name);
			switch (name)
			{
				case "help":
					new CommandHelp().send(event, false);
					break;
				case "help-admin":
					new CommandHelp().send(event, true);
					break;
				case "help-playlist":
					new MemberPlayListHelper().run(event);
					break;
				case "r6-help":
					new R6CommandHelper().run(event);
					break;
				case "untis-help":
					new UntisCommandHelper().run(event);
					break;
				case "ping":
					new PingTest().run(event);
					break;
				case "off":
					new PowerOff().run(event, api);
					break;
				case "purge":
					new Purger().clear(event);
					break;
				case "covid":
					runCovidCommand(event);
					break;
				case "lookup":
					new IPInfoCommand().run(event);
					break;
				case "server":
					new ServerInfo().get(event);
					break;
				case "music":
					new MusicHelper().send(event);
					break;
				case "play":
					musicCore.play(event, false, false, false);
					break;
				case "stop":
					musicCore.stop(event);
					break;
				case "skip":
					musicCore.skip(event);
					break;
				case "pause":
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
				case "playlist":
					musicCore.play(event, true, false, false);
					break;
				case "push":
					musicCore.play(event, false, true, false);
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
				case "sharedparty":
					musicCore.playSharedPlayList(event);
					break;
				case "move":
					musicCore.moveToVoiceChannel(event);
					break;
				case "fresh":
					musicCore.playFreshPlayList(event);
					break;
				case "create":
					memberPlayListCore.createNewPlayListInfo(event);
					break;
				case "add":
					memberPlayListCore.addSong(event);
					break;
				case "join":
					memberPlayListCore.joinPlaylist(event);
					break;
				case "see":
					memberPlayListCore.sendCurrentPlayList(event);
					break;
				case "delete":
					memberPlayListCore.deleteSongFromPlayList(event);
					break;
				case "delete-playlist":
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
					memberPlayListCore.viewForeignPlaylist(event);
					break;
				case "r6-stats":
					r6StatsCommandCore.runStats(event);
					break;
				case "r6-highlight":
					r6StatsCommandCore.runWeekly(event);
					break;
				case "r6-rank":
					r6StatsCommandCore.runRankedStats(event);
					break;
				case "r6-link":
					r6StatsCommandCore.linkDiscordUser(event);
					break;
				case "r6-map":
					r6MapCore.get(event);
					break;
				case "r6-map-add":
					r6MapCore.add(event);
					break;
				case "r6-map-update":
					r6MapCore.update(event);
					break;
				case "r6-map-show":
					r6MapCore.list(event);
					break;
				case "untis-next":
					BotMainCore.getTimeTableReminderCore().nextSubjectEmbed(event);
					break;
				case "untis-timetable":
					new TimeTableCore().run(event);
					break;
			}
		});
	}

	private void runCovidCommand(SlashCommandCreateEvent event)
	{
		if (!event.getSlashCommandInteraction().getFirstOption().isPresent())
		{
			CommandHandler.RKI_PROVINCE.send(event);
		}
		else
		{
			CommandHandler.RKI_COUNTRY.send(event);
		}
	}
}
