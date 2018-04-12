package com.git.sgit.installer;

import java.io.File;
import java.io.IOException;

public class SGitInstallerLinux implements SGitInstaller{

	@Override
	public void install(String[] args) throws IOException, InterruptedException {
		if (args.length == 0) {

			install();

		}else {
			if (args[0].equals("templates")) {
				installTemplates();
			} else if (args[0].equals("install")) {
				installSGit();
			}
		}
	}

	private void install() throws IOException, InterruptedException {

		String installerFile = getInstallerFile();

		System.out.println("Installing sgit templates");

		Process processInstallTemplates = new ProcessBuilder("java", "-jar", installerFile, "templates").start();
		processInstallTemplates.waitFor();

		File userHome = new File(System.getProperty("user.home"));
		if(!new File(String.format("%s/.sgit/hooks/commit-msg", userHome)).exists()){
			System.out.println("Error installing sgit templates.");
			return;
		}else {
			System.out.println("sgit templates installed successfully");
		}

		System.out.println("Installing sgit");

		Process processInstall = new ProcessBuilder("gksudo", "java -jar " + installerFile + " install").start();
		processInstall.waitFor();

		if(!new File("/bin/sgit.jar").exists() || !new File("/bin/sgit").exists()) {
			System.out.println("Error installing sgit. Are you superuser?");
		}else {
			System.out.println("sgit installed successfully");
		}
	}

	private void installTemplates() {


		File userHome = new File(System.getProperty("user.home"));
		String installerLocation = getInstallerFile();

		new CommandLine().run(
				String.format("cd %s", userHome)
				, "mkdir .sgit"
				, "cd .sgit"
				, String.format("jar xf %s %s", installerLocation, "hooks")
				, String.format("git config --global init.templatedir '%s/.sgit'", userHome)
				, String.format("chmod +x %s/.sgit/hooks/commit-msg", userHome)
				);


	}

	private void installSGit() {

		String installerLocation = getInstallerFile();

		new CommandLine().run(
				"cd /bin"
				, String.format("jar xf %s %s", installerLocation, "sgit.jar")
				, String.format("jar xf %s %s", installerLocation, "sgit")
				, "chmod +x sgit"
				);


	}

	private static String getInstallerFile() {
		return new File("").getAbsolutePath() + "/sgit-installer.jar";
	}

}
