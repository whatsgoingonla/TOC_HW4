package hw4;
import java.io.*;
public class Road {
	public int year,max=0,min=100000000,county=0;
	String road = "1"; 
	int[] month = new int[20];
	public Road(){}
	public Road(String r,int y){
		year = y;
		road = r;
	}

}
