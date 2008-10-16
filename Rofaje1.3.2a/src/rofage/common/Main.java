package rofage.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

import javax.swing.SwingUtilities;

import rofage.ihm.Messages;

public class Main {
	public static void main(String[] args) {
		try {
			FileOutputStream fos = new FileOutputStream("RoFage.log",true);
			System.setOut(new PrintStream(fos,true));
			System.out.println("Starting "+Messages.getString("AppTitle")+"..."+Messages.getString("Version")+" at "+new Date());
			
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					//new SplashScreen();
					//new MainWindow();
					new Engine();
				}
			});
			
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
