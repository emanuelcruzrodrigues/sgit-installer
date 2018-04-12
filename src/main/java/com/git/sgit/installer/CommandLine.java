package com.git.sgit.installer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.lang3.SystemUtils;

public class CommandLine {
	
	public void run(String ... commands) {
		try {
			
			String start = getStartCommand();
			
			ProcessBuilder processBuilder = new ProcessBuilder(start);
			processBuilder.redirectErrorStream(true);
			
			Process process = processBuilder.start();
			
			try(OutputStream outputStream = process.getOutputStream()){
				BufferedWriter prompt = new BufferedWriter(new OutputStreamWriter(outputStream));

				for (String command : commands) {
					run(prompt, command);
				}

				run(prompt, "exit");
				process.waitFor();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
}

	private String getStartCommand() {
		if (SystemUtils.IS_OS_WINDOWS) return "cmd";
		if (SystemUtils.IS_OS_LINUX) return "/bin/bash";
		throw new RuntimeException("Your OS is not supported");
	}

	private void run(BufferedWriter prompt, String command) throws IOException {
		prompt.write(command);
		prompt.newLine();
		prompt.flush();
	}

}
