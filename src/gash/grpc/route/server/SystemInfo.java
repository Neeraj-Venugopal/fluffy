package gash.grpc.route.server;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.lang.Runtime;
import java.lang.*;
import java.io.*;

public class SystemInfo{
    
    public Map<String, Double> getSystemInfo(String serverID){
       // System.out.println("System Information Called for the Server: " + serverID);
		Map <String, Double>dataCollect = new HashMap<String, Double> ();
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
                
        File newFile = new File("/");

        double diskSpace = 100 - Math.abs((newFile.getTotalSpace() - newFile.getFreeSpace() * 100) / newFile.getTotalSpace());
        dataCollect.put("diskSpace", diskSpace);
        double usedMem = 100 - (((osBean.getTotalPhysicalMemorySize() - osBean.getFreePhysicalMemorySize()) * 100)/osBean.getTotalPhysicalMemorySize());
        dataCollect.put("memoryUsed", usedMem);
        
        try{
           Process p = Runtime.getRuntime().exec("python scripts/cpuUtilization.py");
           //System.out.println(p.exitValue());
           BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
           String ret = in.readLine();
          //System.out.println(ret);
           dataCollect.put("cpuUtilization", Double.valueOf(ret));
        }
        catch(Exception e){
            System.out.println("Exception Occurred while Retriving CPU Utilization from scripts/cpuUtilization.py for Server ID: " + serverID);
            System.out.println(e);
        }
        return dataCollect;
    }
}
