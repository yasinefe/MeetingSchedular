package com.yefe.meetingschedular;

import com.yefe.meetingschedular.io.MeetingConsoleWriter;
import com.yefe.meetingschedular.io.MeetingFileReader;
import com.yefe.meetingschedular.io.MeetingReader;
import com.yefe.meetingschedular.io.MeetingWriter;
import com.yefe.meetingschedular.model.MeetingBoard;

/**
 * This uses builder pattern. I separated business logic and reader writer implementation via
 * interfaces
 * 
 * @author Yasin EFE
 */
public class MeetingSchedularBuilder {

	private MeetingReader meetingReader;
	private MeetingWriter meetingWriter;
	private MeetingBoardParser meetingBoardParser;
	private MeetingBoardPresenter meetingBoardPresenter;

	public MeetingSchedularBuilder(String sourceFileName) {
		meetingReader = new MeetingFileReader(sourceFileName);
		meetingWriter = new MeetingConsoleWriter(System.out);
		meetingBoardParser = new MeetingBoardParser(meetingReader);
		meetingBoardPresenter = new MeetingBoardPresenter(meetingWriter);
	}

	public void start() throws IncorrectDataException {
		MeetingBoard meetingBoard = meetingBoardParser.parse();
		meetingBoardPresenter.present(meetingBoard);
	}
}
