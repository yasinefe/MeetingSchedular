package com.yefe.meetingschedular.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.yefe.meetingschedular.io.MeetingConsoleWriter;

public class MeetingConsoleWriterShould {

	private MeetingConsoleWriter meetingConsoleWriter;
	private ByteArrayOutputStream baos;

	@Before
	public void setup() {
		baos = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(baos);
		meetingConsoleWriter = new MeetingConsoleWriter(printStream);
	}

	@Test
	public void returnLinesFromFile() throws IOException {
		// Given
		List<String> expected = Arrays.asList("sample1", "sample2", "sample3", "sample4");

		// When
		meetingConsoleWriter.write(expected);
		Assert.assertTrue(baos.toString().contains("sample1"));
		Assert.assertTrue(baos.toString().contains("sample2"));
		Assert.assertTrue(baos.toString().contains("sample3"));
		Assert.assertTrue(baos.toString().contains("sample4"));
	}

}
