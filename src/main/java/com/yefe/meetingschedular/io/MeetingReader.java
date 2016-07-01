package com.yefe.meetingschedular.io;

import java.io.IOException;

import java.util.List;

/**
 * Reader interface is used to separate busines logic and reading process
 * 
 * @author Yasin EFE
 */
public interface MeetingReader {

	List<String> read() throws IOException;

}
