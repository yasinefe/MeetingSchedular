package com.yefe.meetingschedular.io;

import java.util.List;

/**
 * Writer interface is used to separate busines logic and writing process
 * 
 * @author Yasin EFE
 */
public interface MeetingWriter {

	void write(List<String> lines);

}
