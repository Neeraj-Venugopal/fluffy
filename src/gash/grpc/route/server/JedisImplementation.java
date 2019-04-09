package gash.grpc.route.server;

import redis.clients.jedis.Jedis;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.List;
import java.util.TreeSet;

import java.text.SimpleDateFormat;

public class JedisImplementation{
    public String data;
    public String fileName;
    public String userName;
    public int sequenceNumber;
    private String key;
    private Jedis jedis;

    public JedisImplementation(String data, String fileName, String userName, int sequenceNumber){
        this.data = data;
        this.key = userName + "_" + fileName + "_" + sequenceNumber;
        this.fileName = fileName;
        this.userName = userName;
        this.sequenceNumber = sequenceNumber;

        //System.out.println("Inside Jedis Implementation Constructor");
        jedis = new Jedis("localhost"); 
       // System.out.println("Connection to Jedis server sucessfully");
    }

    public JedisImplementation(String fileName, String userName){
        this.fileName = fileName;
        this.userName = userName;
        this.key = userName + "_" + fileName;
        jedis = new Jedis("localhost"); 
    }

	public void setData(){
        try{
            jedis.ping();  
            jedis.hset(this.key, "data", this.data);
            jedis.hset(this.key, "fileName", this.fileName);
            jedis.hset(this.key, "userName", this.userName);
            jedis.hset(this.key, "sequenceNum", Integer.toString(this.sequenceNumber));

            System.out.println("Inserted data onto Redis         " + " | File Name: " + this.fileName + " | User Name: " + this.userName + " | Sequence Number: " + this.sequenceNumber);
        }
        catch(Exception e){
            System.out.println("Insert data onto Redis has Failed" + " | File Name: " + this.fileName + " | User Name: " + this.userName + " | Sequence Number: " + this.sequenceNumber);
            e.printStackTrace();
        }
	}

	public ArrayList<String> getData(){
        jedis.ping();  
        ArrayList<String> payload = new ArrayList<String>();
        System.out.println(this.key);
        Set<String> keys = jedis.keys(this.key + "*");
        //Returns all keys with all sequences with that username/filename
        //Total keys = Total Sequences
        int maxSequence = keys.size();
        //TreeSet<String> keysTreeSet = new TreeSet<String>(keys);
        if(keys.size() > 0){
            for(int i = 1; i<=maxSequence; i++ )
            {
                String tempKey = this.key + "_" + i;
                System.out.println("Reading Key " + tempKey + " for download.");
                Map <String, String> values = jedis.hgetAll(tempKey);
                String value = values.get("data");
                //System.out.println(value);
                payload.add(value);
            }   
        }
        return payload;
    }
    
    public boolean searchFile(){
        jedis.ping();
        Set<String> keys = jedis.keys(this.key + "*");

        if(keys.size() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteFile(){
        jedis.ping();
        Set<String> keys = jedis.keys(this.key + "*");
        System.out.println("Deleting the File in Jedis DB");
        if(keys.size() > 0){
            for(String key : keys){
                jedis.del(key);
            }
            return true;
        }
        else{
            System.out.println("File is not Present to delete in Jedis DB");
            return false;
        }        
    }
}