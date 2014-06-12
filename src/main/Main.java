package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import  sun.audio.*;
import java.applet.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class Main {

	public static void main(String[] args) {

		MasterControlVariables mcv = new MasterControlVariables();
		
		windows.MainMenu mm = new windows.MainMenu(mcv);
		mm.setVisible(true);
		
		mcv.loadBasicSearchTermsFromFile();

	}
}
