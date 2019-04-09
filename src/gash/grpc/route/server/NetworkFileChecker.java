package gash.grpc.route.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.TimerTask;
import java.text.SimpleDateFormat;
import java.util.Date;


public abstract class NetworkFileChecker extends TimerTask {
	private long timeLastModified;
	private File file;

	public NetworkFileChecker (File file){
	
		this.file = file;
		this.timeLastModified = file.lastModified();

	}

	public final void run(){
	       // System.out.println(file.lastModified());
		long timeLastModified = file.lastModified();

		if(this.timeLastModified != timeLastModified){
			this.timeLastModified = timeLastModified;
			fileChanged(file);
		}
	}

	protected abstract void fileChanged(File file);
}
	

	
		
