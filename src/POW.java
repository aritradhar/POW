//*************************************************************************************
//*********************************************************************************** *
//author Aritra Dhar 																* *
//Research Engineer																  	* *
//Xerox Research Center India													    * *
//Bangalore, India																    * *
//--------------------------------------------------------------------------------- * * 
///////////////////////////////////////////////// 									* *
//The program will do the following:::: // 											* *
///////////////////////////////////////////////// 									* *
//version 1.0 																		* *
//*********************************************************************************** *
//*************************************************************************************


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

class Stat
{
	public static volatile boolean ok = true;
	public static volatile int total_counter = 0;
}

class ThreadHash implements Runnable 
{
	byte[] ver;
	int id;
	int limit;
	
	public ThreadHash(byte[] ver, int id, int limit)
	{
		this.ver = ver;
		this.id = id;
		this.limit = limit;
	}
	public void run() 
	{
		Random rand = new Random();
		byte[] b = new byte[8];
		MessageDigest dst;
		int counter = 0;
		try 
		{
			dst = MessageDigest.getInstance("SHA-1");
			while(true && Stat.ok)
			{
				++counter;
				if(counter%10000 == 0)
					System.out.println(this.id + " : " +counter);

				rand.nextBytes(b);	
				dst.update(b);
				byte[] out = dst.digest();
				byte[] st = Arrays.copyOf(out, limit);

				if(Arrays.equals(st, this.ver))
				{
					Stat.ok = false;
					System.out.println("Found on Thread : " + this.id);
					break;
				}
			}
		}
		catch(Exception ex)
		{

		}	
		finally
		{
			Stat.total_counter += counter;
		}
		return;
		
	}
}


public class POW 
{
	public static void main(String[] args) throws NoSuchAlgorithmException 
	{
		int limit = 3;
		
		Random rand = new Random();
		byte[] b = new byte[16];
		byte[] ver = new byte[limit];
		Arrays.fill(ver, (byte)0x00);
		
		MessageDigest dst = MessageDigest.getInstance("SHA-1");

		int counter = 0;
		long start = System.currentTimeMillis();
		while(true)
		{
			++counter;
			if(counter%10000 == 0)
				System.out.println(counter);

			rand.nextBytes(b);	
			dst.update(b);
			byte[] out = dst.digest();
			byte[] st = Arrays.copyOf(out, limit);

			if(Arrays.equals(st, ver))
				break;
		}

		long end = System.currentTimeMillis();
		System.out.println("Total time : " + (end - start));
		
		for(int i = 0; i < b.length; i++)
			System.out.print(b[i] + ",");
	}
}
