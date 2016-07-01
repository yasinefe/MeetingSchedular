package com.yefe.meetingschedular;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import com.yefe.meetingschedular.io.MeetingWriter;
import com.yefe.meetingschedular.model.Meeting;
import com.yefe.meetingschedular.model.MeetingBoard;

/**
 * This class removes conflict, present meeting in a riter class.
 * 
 * @author Yasin EFE
 */
public class MeetingBoardPresenter {

	private MeetingWriter meetingWriter;

	// Simple date format not thread safe so be carefull
	// In this case thread safety is not an issue
	private SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat sdfTime = new SimpleDateFormat("HHmm");
	private SimpleDateFormat sdfTimeWrite = new SimpleDateFormat("HH:mm");

	public MeetingBoardPresenter(MeetingWriter meetingWriter) {
		this.meetingWriter = meetingWriter;
	}

	public void present(MeetingBoard meetingBoard) {
		Map<String, List<Meeting>> meetingMap = normalize(meetingBoard);
		write(meetingMap);
	}

	private void write(Map<String, List<Meeting>> meetingMap) {
		List<String> lines = new ArrayList<String>();

		Set<String> keySet = meetingMap.keySet();
		for (String key : keySet) {
			lines.add(key);

			List<Meeting> meetings = meetingMap.get(key);
			for (Meeting meeting : meetings) {
				long endTime = meeting.getStartDate().getTime()
						+ TimeUnit.HOURS.toMillis(meeting.getDuration());
				lines.add(sdfTimeWrite.format(meeting.getStartDate()) + " "
						+ sdfTimeWrite.format(new Date(endTime)) + " " + meeting.getPerson());
			}
		}
		meetingWriter.write(lines);
	}

	private Map<String, List<Meeting>> normalize(MeetingBoard meetingBoard) {
		Map<String, List<Meeting>> meetingMap = new TreeMap<String, List<Meeting>>();

		List<Meeting> allMeetings = meetingBoard.getMeetings();
		Collections.sort(allMeetings, new Comparator<Meeting>() {
			public int compare(Meeting o1, Meeting o2) {
				return o1.getSubmissionTime().compareTo(o2.getSubmissionTime());
			}
		});

		for (Meeting meeting : meetingBoard.getMeetings()) {
			if (!checkMeetingDuration(meeting, meetingBoard)) {
				continue;
			}
			List<Meeting> meetingsInSameDay = meetingMap.get(sdfDay.format(meeting.getStartDate()));
			if (meetingsInSameDay != null) {
				boolean conflict = false;
				for (Meeting existingMeeting : meetingsInSameDay) {
					conflict = checkIsThereAConflict(meeting, existingMeeting);
					if (conflict) {
						break;
					}
				}
				if (!conflict) {
					meetingsInSameDay.add(meeting);
				}
			} else {
				List<Meeting> meetings = new ArrayList<Meeting>();
				meetings.add(meeting);
				meetingMap.put(sdfDay.format(meeting.getStartDate()), meetings);
			}
		}

		return meetingMap;
	}

	private boolean checkIsThereAConflict(Meeting meeting, Meeting existingMeeting) {
		long startTimeOfNewMeeting = meeting.getStartDate().getTime();
		long endTimeOfNewMeeting = meeting.getStartDate().getTime()
				+ TimeUnit.HOURS.toMillis(meeting.getDuration());

		long startTimeOfExistingMeeting = existingMeeting.getStartDate().getTime();
		long endTimeOfExistingMeeting = existingMeeting.getStartDate().getTime()
				+ TimeUnit.HOURS.toMillis(existingMeeting.getDuration());

		boolean conflict = (startTimeOfNewMeeting >= endTimeOfExistingMeeting)
				|| (endTimeOfNewMeeting <= startTimeOfExistingMeeting);
		return !conflict;
	}

	private boolean checkMeetingDuration(Meeting meeting, MeetingBoard meetingBoard) {
		long endTime = meeting.getStartDate().getTime()
				+ TimeUnit.HOURS.toMillis(meeting.getDuration());
		if (meetingBoard.getStartTime() <= getTime(meeting.getStartDate())
				&& getTime(new Date(endTime)) <= meetingBoard.getEndTime()) {
			return true;
		}
		return false;
	}

	private int getTime(Date date) {
		return Integer.parseInt(sdfTime.format(date));
	}
}
