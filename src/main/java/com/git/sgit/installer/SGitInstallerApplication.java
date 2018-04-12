package com.git.sgit.installer;

import java.io.IOException;

import org.apache.commons.lang3.SystemUtils;

public class SGitInstallerApplication {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		SGitInstaller installer = getInstaller();
		
		if (installer == null) {
			throw new RuntimeException("Your OS is not supported");
		}
		
		installer.install(args);
		
	}

	private static SGitInstaller getInstaller() {
		if (SystemUtils.IS_OS_LINUX) {
			return new SGitInstallerLinux();
		}
		return null;
	}

	


}
