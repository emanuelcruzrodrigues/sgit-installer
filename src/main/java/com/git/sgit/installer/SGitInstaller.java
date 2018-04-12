package com.git.sgit.installer;

import java.io.IOException;

public interface SGitInstaller {

	void install(String[] args) throws IOException, InterruptedException;
	
}
