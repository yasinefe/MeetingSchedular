package com.yefe.meetingschedular;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.yefe.meetingschedular.IncorrectDataException;
import com.yefe.meetingschedular.MeetingBoardParser;
import com.yefe.meetingschedular.io.MeetingReader;
import com.yefe.meetingschedular.model.Meeting;
import com.yefe.meetingschedular.model.MeetingBoard;

public class MeetingBoardParserShould {

	private MeetingReader meetingReader;
	private MeetingBoardParser meetingBoardParser;

	@Before
	public void setup() {
		meetingReader = Mockito.mock(MeetingReader.class);
		meetingBoardParser = new MeetingBoardParser(meetingReader);
	}

	@Test(expected = IncorrectDataException.class)
	public void throwIncorrectDataExceptionWhenIOExceptionIsOccured() throws IOException,
			IncorrectDataException {
		// Given
		Mockito.when(meetingReader.read()).thenThrow(new IOException());

		// When
		meetingBoardParser.parse();
	}

	@Test(expected = IncorrectDataException.class)
	public void throwIncorrectDataExceptionWhenReaderReturnNull() throws IOException,
			IncorrectDataException {
		// Given
		Mockito.when(meetingReader.read()).thenReturn(null);
		// When
		meetingBoardParser.parse();
	}

	@Test(expected = IncorrectDataException.class)
	public void throwIncorrectDataExceptionWhenReaderReturnEmptyList() throws IOException,
			IncorrectDataException {
		// Given
		Mockito.when(meetingReader.read()).thenReturn(new ArrayList<String>());
		// When
		meetingBoardParser.parse();
	}

	@Test(expected = IncorrectDataException.class)
	public void throwIncorrectDataExceptionWhenReaderReturnIncorrectLineSize() throws IOException,
			IncorrectDataException {
		// Given
		Mockito.when(meetingReader.read()).thenReturn(Arrays.asList("sample1", "sample2"));
		// When
		meetingBoardParser.parse();
	}

	@Test(expected = IncorrectDataException.class)
	public void throwIncorrectDataExceptionWhenReaderReturnFirstLineNull() throws IOException,
			IncorrectDataException {
		// Given
		Mockito.when(meetingReader.read()).thenReturn(
				Arrays.asList(null, "2011-03-17 10:17:06 EMP001", "2011-03-21 09:00 2"));
		// When
		meetingBoardParser.parse();
	}

	@Test(expected = IncorrectDataException.class)
	public void throwIncorrectDataExceptionWhenReaderReturnSubmissionLineNull() throws IOException,
			IncorrectDataException {
		// Given
		Mockito.when(meetingReader.read()).thenReturn(
				Arrays.asList("0900 1730", null, "2011-03-21 09:00 2"));
		// When
		meetingBoardParser.parse();
	}

	@Test(expected = IncorrectDataException.class)
	public void throwIncorrectDataExceptionWhenReaderReturnStartLineNull() throws IOException,
			IncorrectDataException {
		// Given
		Mockito.when(meetingReader.read()).thenReturn(
				Arrays.asList("0900 1730", "2011-03-17 10:17:06 EMP001", null));
		// When
		meetingBoardParser.parse();
	}

	@Test(expected = IncorrectDataException.class)
	public void throwIncorrectDataExceptionWhenReaderReturnFirstLineIncorrect() throws IOException,
			IncorrectDataException {
		// Given
		Mockito.when(meetingReader.read()).thenReturn(
				Arrays.asList("09001730", "2011-03-17 10:17:06 EMP001", "2011-03-21 09:00 2"));
		// When
		meetingBoardParser.parse();
	}

	@Test(expected = IncorrectDataException.class)
	public void throwIncorrectDataExceptionWhenReaderReturnSubmissionLineInCorrect()
			throws IOException, IncorrectDataException {
		// Given
		Mockito.when(meetingReader.read()).thenReturn(
				Arrays.asList("0900 1730", "2011-03-1710:17:06EMP001", "2011-03-21 09:00 2"));
		// When
		meetingBoardParser.parse();
	}

	@Test(expected = IncorrectDataException.class)
	public void throwIncorrectDataExceptionWhenReaderReturnStartLineIncorrect() throws IOException,
			IncorrectDataException {
		// Given
		Mockito.when(meetingReader.read()).thenReturn(
				Arrays.asList("0900 1730", "2011-03-17 10:17:06 EMP001", "2011-03-2109:002"));
		// When
		meetingBoardParser.parse();
	}

	@Test(expected = IncorrectDataException.class)
	public void throwIncorrectDataExceptionWhenReaderReturnSubmissionLineDateIncorrect()
			throws IOException, IncorrectDataException {
		// Given
		Mockito.when(meetingReader.read()).thenReturn(
				Arrays.asList("0900 1730", "2011-03-17 10:17 EMP001", "2011-03-21 09:00 2"));
		// When
		meetingBoardParser.parse();
	}

	@Test(expected = IncorrectDataException.class)
	public void throwIncorrectDataExceptionWhenReaderReturnStartLineDateIncorrect()
			throws IOException, IncorrectDataException {
		// Given
		Mockito.when(meetingReader.read()).thenReturn(
				Arrays.asList("0900 1730", "2011-03-17 10:17:06 EMP001", "2011-03-21 09 2"));
		// When
		meetingBoardParser.parse();
	}

	@Test
	public void parseDataSuccessfully() throws IOException, IncorrectDataException, ParseException {
		// Given
		List<String> lines = new ArrayList<String>();
		lines.add("0900 1730");
		lines.add("2011-03-17 10:17:06 EMP001");
		lines.add("2011-03-21 09:00 2");
		lines.add("2011-03-16 12:34:56 EMP002");
		lines.add("2011-03-21 09:00 2");
		Mockito.when(meetingReader.read()).thenReturn(lines);

		SimpleDateFormat sdfSubmission = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		// When
		MeetingBoard actual = meetingBoardParser.parse();

		// Then
		MeetingBoard expected = new MeetingBoard();
		expected.setStartTime(900);
		expected.setEndTime(1730);

		Meeting meeting1 = new Meeting();
		meeting1.setPerson("EMP001");
		meeting1.setDuration(2);
		meeting1.setSubmissionTime(sdfSubmission.parse("2011-03-17 10:17:06"));
		meeting1.setStartDate(sdfStartDate.parse("2011-03-21 09:00"));
		expected.addMeeting(meeting1);

		Meeting meeting2 = new Meeting();
		meeting2.setPerson("EMP002");
		meeting2.setDuration(2);
		meeting2.setSubmissionTime(sdfSubmission.parse("2011-03-16 12:34:56"));
		meeting2.setStartDate(sdfStartDate.parse("2011-03-21 09:00"));

		expected.addMeeting(meeting2);

		Assert.assertEquals(expected, actual);

	}
}
