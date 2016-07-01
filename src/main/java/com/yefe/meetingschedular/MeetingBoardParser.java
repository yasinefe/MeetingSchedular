package com.yefe.meetingschedular;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import com.yefe.meetingschedular.io.MeetingReader;
import com.yefe.meetingschedular.model.Meeting;
import com.yefe.meetingschedular.model.MeetingBoard;

/**
 * This class parse data and return MeetingBoardParser
 * 
 * @author Yasin EFE
 */
public class MeetingBoardParser {

	private MeetingReader meetingReader;

	// Simple date format not thread safe so be carefull
	// In this case thread safety is not an issue
	private SimpleDateFormat sdfSubmission = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdfStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public MeetingBoardParser(MeetingReader meetingReader) {
		this.meetingReader = meetingReader;
	}

	public MeetingBoard parse() throws IncorrectDataException {
		List<String> lines;
		try {
			lines = meetingReader.read();
		} catch (IOException e) {
			throw new IncorrectDataException(e.getMessage());
		}

		if (lines != null) {
			MeetingBoard meetingBoard = new MeetingBoard();

			if (lines.size() < 1) {
				throw new IncorrectDataException("First line must be provided");
			}

			if (lines.size() % 2 == 0) {
				throw new IncorrectDataException("Source does not contain data properly");
			}

			parseFirstLine(lines.get(0), meetingBoard);

			Meeting meeting = null;
			for (int i = 1; i < lines.size(); i++) {
				if (i % 2 == 1) {
					meeting = new Meeting();
					parseSubmissionTime(lines.get(i), meeting);
				} else {
					parseStartTimeEndDuration(lines.get(i), meeting);
					meetingBoard.addMeeting(meeting);
				}
			}

			return meetingBoard;
		} else {
			throw new IncorrectDataException("Reader returned null");
		}
	}

	private void parseFirstLine(String line, MeetingBoard meetingBoard)
			throws IncorrectDataException {
		if (line == null) {
			throw new IncorrectDataException("Line is null");
		}
		int lastIndexOfSpace = line.lastIndexOf(" ");
		if (lastIndexOfSpace == -1) {
			throw new IncorrectDataException("Submission line must contain space");
		}
		int startTime = Integer.parseInt(line.substring(0, lastIndexOfSpace));
		int endTime = Integer.parseInt(line.substring(lastIndexOfSpace + 1));
		meetingBoard.setStartTime(startTime);
		meetingBoard.setEndTime(endTime);

	}

	private void parseSubmissionTime(String line, Meeting meeting) throws IncorrectDataException {
		if (line == null) {
			throw new IncorrectDataException("Line is null");
		}
		int lastIndexOfSpace = line.lastIndexOf(" ");
		if (lastIndexOfSpace == -1) {
			throw new IncorrectDataException("Submission line must contain space");
		}
		Date submissionTime;
		try {
			submissionTime = sdfSubmission.parse(line.substring(0, lastIndexOfSpace));
		} catch (ParseException e) {
			throw new IncorrectDataException(e.getMessage());
		}
		String person = line.substring(lastIndexOfSpace + 1);
		meeting.setSubmissionTime(submissionTime);
		meeting.setPerson(person);
	}

	private void parseStartTimeEndDuration(String line, Meeting meeting)
			throws IncorrectDataException {
		if (line == null) {
			throw new IncorrectDataException("Line is null");
		}
		int lastIndexOfSpace = line.lastIndexOf(" ");
		if (lastIndexOfSpace == -1) {
			throw new IncorrectDataException("Meeting start time line must contain space");
		}

		Date startTime;
		try {
			startTime = sdfStartDate.parse(line.substring(0, lastIndexOfSpace));
		} catch (ParseException e) {
			throw new IncorrectDataException(e.getMessage());
		}
		int duration = Integer.parseInt(line.substring(lastIndexOfSpace + 1));
		meeting.setStartDate(startTime);
		meeting.setDuration(duration);
	}
}
