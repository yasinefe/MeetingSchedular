package com.yefe.meetingschedular.io;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.yefe.meetingschedular.io.MeetingFileReader;

public class MeetingFileReaderShould {

	private MeetingFileReader meetingFileReader;

	@Before
	public void setup() {
		meetingFileReader = new MeetingFileReader("testSource.txt");
	}

	@Test
	public void returnLinesFromFile() throws IOException {
		// When
		List<String> actual = meetingFileReader.read();

		// Then
		List<String> expected = Arrays.asList("sample1", "sample2", "sample3", "sample4");
		Assert.assertEquals(expected, actual);
	}

	@Test(expected = IOException.class)
	public void throwIOExceptionWhenFileNotFound() throws IOException {
		// Given
		meetingFileReader = new MeetingFileReader("notFound.txt");

		// When
		meetingFileReader.read();
	}

}
