package parserpart2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserPart2 {
	public static void main(String[] args) throws IOException {
		String tentativeURL = "https://www.bellevuecollege.edu/classes/";
		String temp = "";
		String storage = "";
		String quarter = "";
		String year = "";
		String program = "";
		boolean invalid = false;//true;
		temp = "winter";
		Scanner sc = new Scanner(System.in);
		while (invalid){
			System.out.println("Enter quarter:");
			temp = sc.nextLine();
			invalid = !StringValidator.onlyLetters(temp);
			if (invalid)
				System.out.println("Error: must have letters only.");
		}
		quarter = temp;
		tentativeURL += temp;
		System.out.println(temp);
		//deletestorage += temp;
		//invalid = true;
		temp = "2017";
		while (invalid){
			System.out.println("Enter year:");
			temp = sc.nextLine();
			invalid = !StringValidator.onlyNumbers(temp);
			if (invalid)
				System.out.println("Error: must have numbers only.");
		}
		year = temp;
		tentativeURL += temp;
		//deletestorage += temp;
		System.out.println(temp);
		//invalid = true;
		char temp2 = 'C';
		while (invalid){
			System.out.println("Enter the initial for the program:");
			temp = sc.nextLine();
			invalid = !StringValidator.lengthCheck(temp, 1)||!StringValidator.onlyLetters(temp);
			if (invalid)
				System.out.println("Error: must be a single letter only.");
		}
		if (temp.charAt(0) >= 'a' && temp.charAt(0) <= 'z')
			temp = temp.toUpperCase();
		//temp2 = temp;
		System.out.println(temp2);
		System.out.println();
	
		URL url = new URL(tentativeURL);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String input = "";
		String text = "";
		while ((input=in.readLine())!=null){
			text += input + '\n';
		}
		//System.out.println(text);
		//System.out.println(text.indexOf("<h2 class=\"subject-letter\">" + temp2));
		//System.out.println((char)(temp2 + 1));
		//System.out.println(text.indexOf("<h2 class=\"subject-letter\">" + (char)(temp2 + 1)));
		//System.out.println(text.length());
		if (temp2 == 'z' || temp2 == 'Z')
			text = text.substring(text.indexOf("<h2 class=\"subject-letter\">" + temp2),
					text.indexOf("Edit Subject Information"));
		else
			text = text.substring(text.indexOf("<h2 class=\"subject-letter\">" + temp2), 
					text.indexOf("<h2 class=\"subject-letter\">" + (char)(temp2 + 1)));
		//System.out.println(text);
		Pattern p = Pattern.compile("<a href=\"/classes/.+/.+\">(.+)</a>([^\n]+)");
		Matcher m = p.matcher(text);
		System.out.println("Programs:");
		while (m.find()){//for every match found
        	System.out.print(m.group(1));
        	System.out.println(m.group(2));
        }
		temp = "computer science";
		invalid = true;
		while (invalid){
			storage = "";
			System.out.println();
			System.out.println("Enter the program's name:");
			//sc.reset();
			//temp = sc.nextLine();
			storage = StringValidator.capitalizeWords(temp);
			//System.out.println(temp);
			//System.out.println(storage);
			invalid = !StringValidator.onlyLetters(storage);
			if (invalid){
				System.out.println("Error: only enter program name. ie. for Accounting (ACCT) just type 'accounting'");	
			} else {
				p = Pattern.compile(storage + ".*?</a>\\s[(](\\w+)[,)]");
				m = p.matcher(text);
				invalid = !m.find();
				if (invalid){
					System.out.println("selected program not found, please type it again.");
				}
			}
		}
		program = storage;
		//System.out.println(m.group(1));
		tentativeURL += "/" + m.group(1);
		url = new URL(tentativeURL);
		in = new BufferedReader(new InputStreamReader(url.openStream()));
		input = "";
		text = "";
		while ((input=in.readLine())!=null){
			text += input + '\n';
		}
		text = text.substring(0,text.indexOf("<div id=\"classes-footer\""));
		//System.out.println(text);
		invalid = true;
		while (invalid){
			System.out.println();
			System.out.println("Enter the course ID:");
			//sc.reset();
			temp = "CS 351";
			//temp = sc.nextLine();
			storage = temp.toUpperCase();
			if (text.indexOf(storage) >= 0){
				temp = text.substring(text.indexOf(storage));
				if (temp.indexOf("<h2 class=\"classHeading\">") >= 0)
					temp = temp.substring(0, temp.indexOf("<h2 class=\"classHeading\">") + 30);
				//System.out.println(temp);
				//System.out.println(storage);
				String cTitle = "";
				p = Pattern.compile(storage + "</span> <span class=\"courseTitle\">(.*)</span>");
				m = p.matcher(temp);
				if (m.find()){//if found, then there is valid entry and program will complete.
					cTitle = m.group(1);
					//System.out.println(cTitle);
					p = Pattern.compile("Item number: </span>(.+)</span>[^;]+?a href=.+>(.+)</a>");
					m = p.matcher(temp);
					//System.out.println("entering m.find()");
					//invalid = false;//GET RID OF THIS LATER, JUST TO STOP CONSOLE SPAM
					while (m.find()){
						if (invalid){
							System.out.println();
							System.out.println(program + " Courses in " + quarter + " " + year);
							System.out.println("====================================================================");
						}
						System.out.println("Course Code: " + storage);
						System.out.println("Item Number: " + m.group(1));
						System.out.println("Course Title: " + cTitle);
						System.out.println("Instructor: " + m.group(2));
						System.out.print("Class Times: ");//days + times + room
						text = temp.substring(temp.indexOf(m.group(1)));
						if (text.indexOf("<!-- SEARCHTERM:  -->") >= 0)
							text = text.substring(0, text.indexOf("<!-- SEARCHTERM:  -->"));
						else
							if (text.indexOf("<h2 class=\"classHeading\">") >= 0)
								text = text.substring(0, text.indexOf("<h2 class=\"classHeading\">"));
						Pattern p2 = Pattern.compile("<span class=\"days\">\\s*<abbr title=\"(.+)\">[^;]+?at </span>(.+)\\s[^;]+?in </span>(.+)\\s|<span class=\"days online\">(.+)</span>");//NEED THIS STILL
						Matcher m2 = p2.matcher(text);//reusing text since its not being used anymore?
						int n = 0;
						while (m2.find()){
							if (m2.group(4) != null)
								System.out.println(m2.group(4));
							else {
								if (n > 0)
									System.out.print("             ");
								System.out.println(m2.group(1) + " at " + m2.group(2) + " in " + m2.group(3));
								n++;
							}
						}
						System.out.println("====================================================================");
						invalid = false;
					}
				} else {
					System.out.println("selected course not found, please type it again.");
				}
			} else {
				System.out.println("selected course not found, please type it again.");
			}
		}
		
		
		sc.close();
	}
}


