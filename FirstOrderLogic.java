import java.util.*;
import java.io.*;

//Notes: Need to improve
//1. Parenthization and grouping. 
//2. Functions such as exponentiation and roots
//3. Can't keep replacing parentheses with brackets

//Need standard format : 
//no compound sentences. 
//Must be ended with period
//if and only if = iff
//only if = onlyif
//Assume for sake of contradiction = afsoc
//greater than = greaterthan
//less than = lessthan
//For things like square roots, need to parenthesize
//Need spaces between every parentheses

public class FirstOrderLogic {
	
	static String format(String line) {
		line = line.replaceAll("if and only if", "iff");
		line = line.replaceAll("only if","onlyif");
		line = line.replaceAll("greater than", "greaterthan");
		line = line.replaceAll("less than", "lessthan");
		line = line.replaceAll("assume for sake of contradiction", "afsoc");
		line = line.replaceAll("square root", "sqrt");
		line = line.replaceAll("\\("," \\( ");
		line = line.replaceAll("\\)"," \\) ");
		line = line.replaceAll("there exists", "exists");
		line = line.replaceAll("Our goal is to prove", "WTS");
		return line;
	}
	
	static int extractIndex(String line) {
		int num = line.indexOf(" ");
		if (num == -1) {
			return line.indexOf(".");
		}
		else
			return num;
	}
	
	public static void main(String args[]) {
		
		Map<String,String> keywords = new HashMap<String,String>();
		
		//Constructing hash table of keywords
		keywords.put("let", "assume");
		keywords.put("assume", "assume");
		
		keywords.put("then", "");
		keywords.put("next", "");
		
		keywords.put("forall", "$\\forall$");
		keywords.put("exists", "$\\exists$");
		keywords.put("in", "$\\in$");
		
		keywords.put("iff", "$\\iff$");
		keywords.put("if", "$\\impliedby$");
		keywords.put("implies", "$\\implies$");
		keywords.put("onlyif", "$\\implies$");
		
		keywords.put("times", "$*$");
		keywords.put("plus","$+$");
		keywords.put("divides", "$|$");
		keywords.put("subtract", "$-$");
		
		keywords.put("such", "s.");
		keywords.put("that", "t.");
		
		keywords.put("afsoc", "AFSOC");
		keywords.put("contradiction", "$\\perp$");
		
		keywords.put(">", "$>$");
		keywords.put(">=", "$>=$");
		keywords.put("<", "$<$");
		keywords.put("<=", "$<=$");
		keywords.put("greaterthan", "$>$");
		keywords.put("lessthan", "$<$");
		
		keywords.put("equal", "$=$");
		keywords.put("equals", "$=$");
		
		keywords.put("and", "$\\land$");
		keywords.put("or", "$\\lor$");
		keywords.put("not", "$\\neg$");
		
		keywords.put("sqrt", "$\\sqrt");
		keywords.put("(", "{");
		keywords.put(")", "}$");
		
		keywords.put("reals", "$\\mathbb{R}$");
		keywords.put("integers", "$\\mathbb{Z}$");
		keywords.put("naturals", "$\\mathbb{N}$");
		keywords.put("rationals", "$\\mathbb{Q}$");
		
		
		Scanner keyboard = new Scanner(System.in);
		int lineNum = 1;
		int depth = 0;
		
		System.out.println("For text file input press 0. For line by line input press 1.");
		int typeInput = Integer.parseInt(keyboard.nextLine());
		
		if (typeInput == 0) {
			System.out.println("Input file name: ");
			String name = keyboard.nextLine();
			
			try {
				Scanner inFile = new Scanner(new FileInputStream(name));
				FileWriter outFile = new FileWriter("output");
			
				while(inFile.hasNextLine()) {
					outFile.write(Integer.toString(lineNum) + ". ");
					lineNum++;
					for(int i = depth; i>0;i--) {
						outFile.write("*");
					}
					outFile.write(" ");
					String line = inFile.nextLine();
					line = format(line);
					while(extractIndex(line) >= 0) {
						String word = line.substring(0, extractIndex(line));
						word = word.toLowerCase();
						String result = keywords.get(word);
						line = line.substring(extractIndex(line)+1);
						
						//There is a bug if there are no spaces
						if(result == null) {
							outFile.write(word + " ");
						}
						else {
							if(result.equals("AFSOC")) {
								depth++;
							}
							else if(result.equals("$\\perp$")) {
								depth--;
							}
							outFile.write(result+" ");
						}
					}
					outFile.write("\\\\");
					outFile.write("\n");
				}
				
				inFile.close();
				outFile.close();
			} 
			catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("File not found.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		else if (typeInput == 1) {
			
		}
		
		else {
			System.out.println("Input type invalid. Exiting program.");
			return;
		}
		
	}
}
