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

import java.util.Arrays;


public class PWMT 
{
	public static void main(String[] args) throws InterruptedException 
	{
		int limit = 3;
		
		long start = System.currentTimeMillis();
		byte[] ver = new byte[limit];
		Arrays.fill(ver, (byte)0x00);
		
		int threadCount = 100;
		Thread[] t = new Thread[threadCount];
		
		for(int i = 0; i< threadCount; i++)
		{
			ThreadHash th = new ThreadHash(ver, i + 1, limit);
		
			t[i] = new Thread(th);
			t[i].start();
		}
		
		for(int i = 0; i< threadCount; i++)
		{
			t[i].join();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Total time : " + (end - start));
		System.out.println("Total counter : " + Stat.total_counter);
	}
}
