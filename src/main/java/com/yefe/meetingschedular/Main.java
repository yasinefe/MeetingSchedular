package com.yefe.meetingschedular;

/**
 * Runs the application, used MeetingSchedularBuilder
 * 
 * @author Yasin EFE
 */
public class Main {

	public static MeetingSchedularBuilder meetingSchedularBuilder;

	public static void main(String[] args) {
		meetingSchedularBuilder = new MeetingSchedularBuilder("source.txt");
		try {
			meetingSchedularBuilder.start();
		} catch (IncorrectDataException e) {
			System.err.println(e.getMessage());
		}
	}

}
