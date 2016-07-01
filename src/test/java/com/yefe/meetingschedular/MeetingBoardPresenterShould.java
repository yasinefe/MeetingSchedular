package com.yefe.meetingschedular;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.yefe.meetingschedular.MeetingBoardPresenter;
import com.yefe.meetingschedular.io.MeetingWriter;
import com.yefe.meetingschedular.model.Meeting;
import com.yefe.meetingschedular.model.MeetingBoard;

public class MeetingBoardPresenterShould {

	private MeetingBoardPresenter meetingBoardPresenter;
	private MeetingWriter meetingWriter;

	@Before
	public void setup() {
		meetingWriter = Mockito.mock(MeetingWriter.class);
		meetingBoardPresenter = new MeetingBoardPresenter(meetingWriter);
	}

	@Test
	public void normalizeAndPresentCorrectly() throws ParseException {
		// Given
		SimpleDateFormat sdfSubmission = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		MeetingBoard meetingBoard = new MeetingBoard();
		meetingBoard.setStartTime(900);
		meetingBoard.setEndTime(1730);

		Meeting meeting1 = new Meeting();
		meeting1.setPerson("EMP001");
		meeting1.setDuration(2);
		meeting1.setSubmissionTime(sdfSubmission.parse("2011-03-17 10:17:06"));
		meeting1.setStartDate(sdfStartDate.parse("2011-03-21 09:00"));
		meetingBoard.addMeeting(meeting1);

		Meeting meeting2 = new Meeting();
		meeting2.setPerson("EMP002");
		meeting2.setDuration(2);
		meeting2.setSubmissionTime(sdfSubmission.parse("2011-03-16 12:34:56"));
		meeting2.setStartDate(sdfStartDate.parse("2011-03-21 09:00"));
		meetingBoard.addMeeting(meeting2);

		Meeting meeting3 = new Meeting();
		meeting3.setPerson("EMP003");
		meeting3.setDuration(2);
		meeting3.setSubmissionTime(sdfSubmission.parse("2011-03-16 09:28:23"));
		meeting3.setStartDate(sdfStartDate.parse("2011-03-22 14:00"));
		meetingBoard.addMeeting(meeting3);

		Meeting meeting4 = new Meeting();
		meeting4.setPerson("EMP004");
		meeting4.setDuration(1);
		meeting4.setSubmissionTime(sdfSubmission.parse("2011-03-17 11:23:45"));
		meeting4.setStartDate(sdfStartDate.parse("2011-03-22 16:00"));
		meetingBoard.addMeeting(meeting4);

		Meeting meeting5 = new Meeting();
		meeting5.setPerson("EMP005");
		meeting5.setDuration(3);
		meeting5.setSubmissionTime(sdfSubmission.parse("2011-03-15 17:29:12"));
		meeting5.setStartDate(sdfStartDate.parse("2011-03-21 16:00"));
		meetingBoard.addMeeting(meeting5);

		// When
		meetingBoardPresenter.present(meetingBoard);

		// Then
		List<String> lines = new ArrayList<String>();
		lines.add("2011-03-21");
		lines.add("09:00 11:00 EMP002");
		lines.add("2011-03-22");
		lines.add("14:00 16:00 EMP003");
		lines.add("16:00 17:00 EMP004");

		Mockito.verify(meetingWriter).write(lines);
	}
}
