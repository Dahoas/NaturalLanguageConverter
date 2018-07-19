import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//Need to remove whitespace lines in between text
//Refutation counts for two lines. Need to include...
//Premises is # of premises.
//Everything else is one line

//Modify sentences of text to always conclude new deductions with 'thus'
//Likewise always end a deduction with a period

//Modify backwards sentences to have new goal be "prove" then the goal
//End that with period too

//Change "new goal is to show the negation of ~M" for the contradiction by removing unquoted text

//Always put outermost parens around implies

/*Glitches:
 * 1. Repeats conclusion twice sometimes(when not contradiction)
 * 2. Also quite inefficient, should transition to BufferedStreamReader
 * 3. Need to fix formatting
 * 4. Need to address when assumptions are made(backwards arrow elim)*/

/*Addons:
 * 1. Incorporate justifications
 * 2. Incorporate levels of assumption*/

public class reverseSolution {
	
	static String format(String line) {
		line = line.replaceAll("the negation of ", "~");
		line = line.replaceAll("~~", "");
		line = line.replaceAll("it follows that", "thus");
		line = line.replaceAll(" is proven as well", "");
		line = line.replaceAll("then we have shown", "thus");
		line = line.replaceAll(" for the contradiction", "");
		return line;
	}
	
	static int countLines(String name) {
		
		int count = 0;
		
		try {
			Scanner inFile = new Scanner(new FileInputStream(name));
			while(inFile.hasNextLine()) {
				String line = inFile.nextLine();
				count++;
				if(line.indexOf("refutation") >= 0) {
					count++;
				}
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	static void modify(String line, int num) {
		
		ArrayList<String> lines = new ArrayList<String>();
		try {
			Scanner inFile = new Scanner(new FileInputStream("output"));
			while(inFile.hasNextLine()) {
				lines.add(inFile.nextLine());
			}
			inFile.close();
			//System.out.print("lines.size(): ");System.out.println(lines.size());
			lines.set(num, line);
			FileWriter outFile = new FileWriter("output");
			for(int i = 0;i<lines.size();i++) {
				outFile.write(lines.get(i)+"\n");
			}
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static String extract(int num) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			Scanner inFile = new Scanner(new FileInputStream("output"));
			while(inFile.hasNextLine()) {
				lines.add(inFile.nextLine());
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lines.get(num);
	}
	
	static int countParens(String line,int index) {
		int count = 0;
		for(int i = 0;i<=index;i++) {
			if(String.valueOf(line.charAt(i)) == "(") {
				count++;
			}
			else if(String.valueOf(line.charAt(i)) == ")") {
				count--;
			}
		}
		return count;
	}
	
	static int extractPremise(String line) {
		int num = line.indexOf("-&gt;");
		int count = countParens(line,num);
		if(count > 0) {
			return extractPremise(line.substring(0,num)+line.substring(num+5))+5;
		}
		else {
			//line = line.substring(0, num-1);
			return num;
		}
	}
	
	static void clean() {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			Scanner inFile = new Scanner(new FileInputStream("output"));
			while(inFile.hasNextLine()) {
				lines.add(inFile.nextLine());
			}
			inFile.close();
			//System.out.print("lines.size(): ");System.out.println(lines.size());
			for(int i = 0;i<lines.size();i++) {
				lines.set(i, Integer.toString(i+1)+". "+format(lines.get(i)));
			}
			FileWriter outFile = new FileWriter("output");
			for(int i = 0;i<lines.size();i++) {
				outFile.write(lines.get(i)+"\n");
			}
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static ArrayList<String> extractPremiseGoal(String line) {
		ArrayList<String> list = new ArrayList<String>();
		int index = line.indexOf(":");
		line = line.substring(index+2);
		
		while(line.indexOf(",") > 0) {
			index = line.indexOf(",");
			list.add(line.substring(0, index));
			line = line.substring(index+2);
		}
		
		index = line.indexOf(".");
		list.add(line.substring(0,index));
		
		index = line.indexOf(":");
		line = line.substring(index+2);
		
		list.add(line.substring(0,line.length()-1));
		
		return list;
	}
	
	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Input file name: ");
		String name = keyboard.nextLine();
		int length = countLines(name);
		
		Scanner inFile;
		try {
			inFile = new Scanner(new FileInputStream(name));
			String temp = inFile.nextLine();
			ArrayList<String> premises = extractPremiseGoal(temp);
			String goal = premises.get(premises.size()-1);
			premises.remove(premises.size()-1);
			
			//Need to figure out relationship bewteen length of file and number of lines printed
			length = length+premises.size()-1;
			int forwardCount = 0;
			int backwardCount = length-1;
			
			FileWriter outFile = new FileWriter("output");
			for(int i = 0;i<length;i++) {
				outFile.write("*\n");
			}
			outFile.close();
			
			//System.out.print("Premises.size : "); System.out.println(premises.size());
			for(int i=0;i<premises.size();i++) {
				modify(premises.get(i),forwardCount);
				forwardCount++;
			}
			
			modify(goal,length-1);
			backwardCount--;
			
			while(inFile.hasNextLine()) {
				String line = inFile.nextLine();
				//System.out.println(line);
				line = format(line);
				//System.out.println(line);
				if(line.indexOf("backwards") > 0) {
					if(line.indexOf("refutation")>0) {
						//Does contradiction have to be immediately before???
						String back = extract(backwardCount+1);
						modify("~"+back,forwardCount);
						forwardCount++;
						modify("_|_",backwardCount);
						backwardCount--;
					}
					else if(line.indexOf("arrow introduction")>0) {
						//System.out.println("test");
						//Does implication have to be immediately before arrow introduction???
						String back = extract(backwardCount+1);
						back = back.substring(1,back.length()-1);
						int pivot = extractPremise(back);
						String premise = back.substring(0, pivot-1);
						String result = back.substring(pivot+5+1);
						modify(premise,forwardCount);
						forwardCount++;
						modify(result,backwardCount);
						backwardCount--;
					}
					else {
						if(line.indexOf("prove") > 0) {
							line = line.substring(line.indexOf("prove")+5+1);
							int dot = line.indexOf(".");
							line = line.substring(0, dot);
							modify(line,backwardCount);
							backwardCount--;
						}
						else {
							
						}
					}
				}
				else {
					//All deductions
					if(line.indexOf("thus")>0) {
						//System.out.println("Test");
						line = line.substring(line.indexOf("thus")+4+1);
						int dot = line.indexOf(".");
						line = line.substring(0, dot);
						modify(line,forwardCount);
						forwardCount++;
					}
					else {
						
					}
				}
			}
			clean();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		keyboard.close();
	}
}
