COMMENTS
--------------------------------
I will give some dteails about this application.

I spend 3 hours to develop this application.

- Unit test coverage %88, I did not write unit test for Main class and Builder class. Becuase normall I used spring or something else to manage IoC container.
- The classes which contains business logic has 100% test coverage
- If you want create a deployable tar.gx package which contains this project then you should use 'mvn package' command.
- If you want to measure unit test coverage you should use 'mvn cobertura:cobertura' command
- Deployable tar.gz file contains these directories:
	- bin contains start sh and bat files.
	- conf contains source.txt file.
	- lib contains jar files, dependencies and telnet server jar.
	- log files is empty in the first time then if you start the application log files will write in this folder.
	
- You can find cobertura coverage report in the RAR file in target folder.
- You can find tar.gz deployable file in target folder
- You can find the jar file of the project in target folder
- You can find class diagram in com.akqa.meetingschedular package

USAGE
--------------------------------
- You can extract tar.gz file
- You can change source.txt file in conf directory.
- You can start with files in bin directory
- You can create different scenarios
- Result will be printed on console

ASSUMPTIONS
--------------------------------
I did some assumptions

- I used common io jar.
- I used commons lang jar.
