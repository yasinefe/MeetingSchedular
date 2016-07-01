package com.yefe.meetingschedular.io;

import java.io.PrintStream;

import java.util.List;

/**
 * Simple writer implementation, writes data to console
 * 
 * @author Yasin EFE
 */
public class MeetingConsoleWriter implements MeetingWriter {

	private PrintStream out;

	public MeetingConsoleWriter(PrintStream out) {
		this.out = out;
	}

	public void write(List<String> lines) {
		for (String line : lines) {
			out.println(line);
		}
	}

}
