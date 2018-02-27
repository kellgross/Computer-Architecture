import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
/*
 * Author: Kelly Gross
 * Date Last Modified: February 22, 2018
 * 
 */
public class MIPSDecode {
	int insts = 0;
	int rType = 0;
	int iType = 0;
	int jType = 0;
	int fwdTaken = 0;
	int bkwTaken = 0;
	int notTaken = 0;
	int loads = 0;
	int stores = 0;
	int[] regread = new int[32];
	int[] regwrite = new int[32];
	
	public void readFile(String in) {
		try {
			FileReader fin = new FileReader(in);
			BufferedReader reader = new BufferedReader(fin);
			String line = reader.readLine();
			while (line != null) {
				//KEEP A CURRENT AND NEXT
				++insts;
				parseLine(line);
				line = reader.readLine();
			}
		}
		catch (IOException e) {
			System.out.print("IOException");
		}
	}
	
	public void parseLine(String line) {
		String[] split = line.split(" ");
		long addressLong = Long.parseLong(split[0], 16);
		long instructionLong = Long.parseLong(split[1], 16);
		
		int address = (int) addressLong;
		int instruction = (int) instructionLong;
		//int address = Integer.parseInt(split[0], 16);
		//int instruction = Integer.parseInt(split[1], 16);
		//System.out.println(address); //DEBUGG
		int opcode = instruction>>>26;
		//System.out.println(opcode); //DEBUGG
		
		if (opcode == 0) {
			++rType;
			decodeRType(instruction);
		}
		else if(opcode == 2 || opcode == 3) {
			++jType;
			decodeJType(instruction, address);
		}
		else {
			++iType;
			decodeIType(instruction, address);
		}
		
	}
	
	public void decodeRType(int instruction) {
		//Method to decode r-type instructions
		//DECODE BASED ON FUNCTION TYPE
		//A LOT OF IF STATEMENTS 
		int rs = instruction>>>21;
		//rs = rs<<6;
		//System.out.println(rs);
		++regread[rs];
		//READ AND WRITE////////////////////////////////////////////////////////////////
		int rt = instruction<<11;
		rt = rt>>>27;
		//READ AND WRITE////////////////////////////////////////////////////////////////
		//System.out.println(rt);
		int rd = instruction<<16;
		rd = rd>>>27;
		//System.out.println(rd);
		//READ AND WRITE//////////////////////////////////////////////////////////////////
		
	}
	
	public void decodeJType(int instruction, int address) {
		
	}
	
	public void decodeIType(int instruction, int address) {
		
	}
	
	public void writeFile(String in) {
		try {
			FileWriter fout = new FileWriter(in);
			BufferedWriter writer = new BufferedWriter(fout);
		}
		catch(IOException e) {
			System.out.print("IOException");
		}
	}
	
	public static void main(String[] args) {
		MIPSDecode decode = new MIPSDecode();
		decode.readFile("project1-example-trace.txt");
		System.out.println("insts: " + decode.insts);
		System.out.println(decode.rType);
	}
}
