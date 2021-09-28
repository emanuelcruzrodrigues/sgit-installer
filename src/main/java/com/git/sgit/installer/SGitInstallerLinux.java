package com.git.sgit.installer;

import java.io.File;
import java.io.IOException;

public class SGitInstallerLinux implements SGitInstaller{

	@Override
	public void install(String[] args) throws IOException, InterruptedException {
		String installerFile = getInstallerFile();
		
		String java = System.getProperty("java.home")+"/bin/java";

		System.out.println("JVM:" + java);
		System.out.println("Installer:" + installerFile);
		
		installTemplates();
		installSGit(installerFile);
	}

	private void installTemplates() {

		System.out.println("Installing sgit templates");

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

		if(!new File(String.format("%s/.sgit/hooks/commit-msg", userHome)).exists()){
			System.out.println("Error installing sgit templates.");
			return;
		}else {
			System.out.println("sgit templates installed successfully");
		}

	}
	
	private void installSGit(String installerFile) {
		System.out.println("Extracting sgit");
		
		new CommandLine().run(
				  String.format("jar xf %s %s", installerFile, "sgit.jar")
				, String.format("jar xf %s %s", installerFile, "sgit")
				, "chmod +x sgit.jar"
				, "chmod +x sgit"
				);
		
		System.out.println("Installing sgit");

		new CommandLine().run("sudo mv sgit.jar /bin/");
		new CommandLine().run("sudo mv sgit /bin/");

		if(!new File("/bin/sgit.jar").exists() || !new File("/bin/sgit").exists()) {
			System.out.println("Error installing sgit");
		}else {
			System.out.println("sgit installed successfully");
		}
	}

	private static String getInstallerFile() {
		return new File("").getAbsolutePath() + "/sgit-installer.jar";
	}
	
	public static void main(String[] args) {
		String javaHome = System.getProperty("java.home");
		System.out.println(javaHome);
	}

}
