package com.yefe.meetingschedular.io;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * Simple reader implementation. Reads data from file.
 * 
 * @author Yasin EFE
 */
public class MeetingFileReader implements MeetingReader {

	private String sourceFileName;

	public MeetingFileReader(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public List<String> read() throws IOException {
		return FileUtils.readLines(new File(getSourceFilePath()));
	}

	public String getSourceFilePath() throws IOException {
		URL resource = this.getClass().getClassLoader().getResource(sourceFileName);
		if (resource != null) {
			return resource.getPath();
		} else {
			throw new IOException("Source not found. sourceFileName : " + sourceFileName);
		}
	}

}
