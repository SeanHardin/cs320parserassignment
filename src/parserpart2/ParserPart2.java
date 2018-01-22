// ParserPart2.java
// Sean Hardin
//1/22/18
// parses BC class page for programs and class information as prompted by user
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
		String tentativeURL = "https://www.bellevuecollege.edu/classes/";//many while loops used
		String temp = "";//so initializing variables I use out here
		String storage = "";
		String quarter = "";
		String year = "";
		String program = "";
		BufferedReader in;
		URL url;
		String text = "";
		String input = "";
		boolean invalid = true;//used to move through while loops
		Scanner sc = new Scanner(System.in);//take user input
		while (invalid){
			System.out.println("Enter quarter:");//ask for quarter
			temp = sc.nextLine();
			invalid = !StringValidator.onlyLetters(temp);
			if (invalid)
				System.out.println("Error: must have letters only.");//print error if invalid input
		}
		quarter = temp;//store for later
		tentativeURL += temp;//add to the url for parsing
		invalid = true;
		while (invalid){
			System.out.println("Enter year:");//ask for year
			temp = sc.nextLine();
			invalid = !StringValidator.onlyNumbers(temp);
			if (invalid)
				System.out.println("Error: must have numbers only.");
		}
		year = temp;//store for later
		tentativeURL += temp;//appends to url
		invalid = true;
		char temp2 = 'A';//initialize character
		while (invalid){//loop to account for unused character being selected
			while (invalid){
				System.out.println("Enter the initial for the program:");//ask for program initial
				temp = sc.nextLine();
				invalid = !StringValidator.lengthCheck(temp, 1) || !StringValidator.onlyLetters(temp);
				if (invalid)//if more than character, or non letter then give error
					System.out.println("Error: must be a single letter only.");
			}
			if (temp.charAt(0) >= 'a' && temp.charAt(0) <= 'z')//if lowercase
				temp = temp.toUpperCase();//capitalizes it.
			temp2 = temp.charAt(0);//stores as character
			System.out.println();
			url = new URL(tentativeURL);//initialize parser to take webpage info
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			text = "";
			while ((input=in.readLine()) != null){//store all html into string
				text += input + '\n';
			}
			invalid = text.indexOf("class=\"subject-letter\">" + temp2) < 0;
			if (invalid)//if section for given letter isn't found
				System.out.println("selected letter not found. please try again.");
		}//close while loop, character selection successful
		text = text.substring(text.indexOf("class=\"subject-letter\">" + temp2));//shorten string to parse
		if (text.indexOf("<h2 class=\"subject-letter\">") < 0)//if no other letter after
			text = text.substring(0, text.indexOf("Edit Subject Information"));
		else//if letter after, just more shortening to make regex easier
			text = text.substring(0, text.indexOf("<h2 class=\"subject-letter\">"));
		Pattern p = Pattern.compile("<a href=\"/classes/.+/.+\">(.+)</a>([^\n]+)");
		Matcher m = p.matcher(text);//matches program names and the acronyms in parenthesis
		System.out.println("Programs:");
		while (m.find()){//for every match found
			System.out.print(m.group(1));
			System.out.println(m.group(2));
		}
		invalid = true;
		while (invalid){
			storage = "";
			System.out.println();
			System.out.println("Enter the program's name:");
			temp = sc.nextLine();
			//storage = StringValidator.capitalizeWords(temp);
			//used to make it easy on myself, but doesn't work on cases with non capitalized words like Translation and Interpretation
			storage = temp;//since the above line got commented out
			invalid = false;//!StringValidator.onlyLetters(storage);
			if (invalid){
				System.out.println("Error: only enter program name. ie. for Accounting (ACCT) just type 'accounting'");	
			} else {
				p = Pattern.compile(storage + ".*?</a>\\s[(](\\w+)[,)]");//finds program acronym for url
				m = p.matcher(text);
				invalid = !m.find();
				if (invalid){
					System.out.println("selected program not found, please type it again.");
				}
			}
		}
		program = storage;//store for later
		tentativeURL += "/" + m.group(1);//change url for selected program
		url = new URL(tentativeURL);
		in = new BufferedReader(new InputStreamReader(url.openStream()));
		input = "";
		text = "";
		while ((input=in.readLine())!=null){//take new html lines
			text += input + '\n';
		}
		text = text.substring(0, text.indexOf("<div id=\"classes-footer\""));//shorten string again
		invalid = true;
		while (invalid){
			System.out.println();
			System.out.println("Enter the course ID:");
			temp = sc.nextLine();//not worried about ampersands.
			storage = temp.toUpperCase();//since case sensitive
			if (text.indexOf(storage) >= 0){//if class found
				temp = text.substring(text.indexOf(storage));//moving string to temp in case match not found
				if (temp.indexOf("<h2 class=\"classHeading\">") >= 0)
					temp = temp.substring(0, temp.indexOf("<h2 class=\"classHeading\">") + 30);//shortening
				String cTitle = "";
				p = Pattern.compile(storage + "</span> <span class=\"courseTitle\">(.*)</span>");
				m = p.matcher(temp);//finds course title
				if (m.find()){//if found, then there is valid entry and program will complete.
					cTitle = m.group(1);//store for later
					p = Pattern.compile("Item number: </span>(.+)</span>[^;]+?a href=.+>(.+)</a>");
					m = p.matcher(temp);//find item number and teacher... dont use teacher anymore but not deleting in case it breaks something
					while (m.find()){//for every offering found
						if (invalid){
							System.out.println();//formatting
							System.out.println(program + " Courses in " + quarter + " " + year);//prints based on user input
							System.out.println("====================================================================");
						}
						System.out.println("Course Code: " + storage);//first piece
						System.out.println("Item Number: " + m.group(1));//found item number
						System.out.println("Course Title: " + cTitle);//from previous parser
						System.out.print("Instructor(s): ");//in case of multiple
						text = temp.substring(temp.indexOf(m.group(1)));//by this point the regex went through so no longer need to keep text string
						text = text.substring(0, text.indexOf("Meets:"));//shorten text to include only teacher data
						Pattern p2 = Pattern.compile("a href=.+>(.+)</a>");//find all teacher names
						Matcher m2 = p2.matcher(text);
						int n = 0;//for formatting
						while (m2.find()){
							if (n > 0)
								System.out.print("               ");//only print with 2+ teachers
							System.out.println(m2.group(1));//prints teacher name
							n++;//increment
						}
						System.out.print("Class Times: ");//in case multiple times
						text = temp.substring(temp.indexOf(m.group(1)));//reusing text again, cuts off at item number
						if (text.indexOf("<!-- SEARCHTERM:  -->") >= 0)//if this is found, cuts it off
							text = text.substring(0, text.indexOf("<!-- SEARCHTERM:  -->"));
						else if (text.indexOf("<h2 class=\"classHeading\">") >= 0)//if this is found cuts it off
								text = text.substring(0, text.indexOf("<h2 class=\"classHeading\">"));
						p2 = Pattern.compile("<span class=\"days\">\\s*<abbr title=\"(.+)\">[^;]+?at </span>(.+)\\s[^;]+?in </span>(.+)\\s|"
						+ "<span class=\"days online\">(.+)</span>");//finds days, times and room for each item
						m2 = p2.matcher(text);
						n = 0;
						while (m2.find()){
							if (m2.group(4) != null)//if class is online
								System.out.println(m2.group(4));
							else {
								if (n > 0)
									System.out.print("             ");
								if (m2.group(1).equals("Arranged"))//if class is arranged
									System.out.println(m2.group(1));
								else//normal classes print all 3
									System.out.println(m2.group(1) + " at " + m2.group(2) + " in " + m2.group(3));
								n++;
							}
						}
						System.out.println("====================================================================");
						invalid = false;
					}
				} else {//pattern couldn't find course
					System.out.println("selected course not found, please type it again.");
				}
			} else {//if class not found
				System.out.println("selected course not found, please type it again.");
			}
		}
		System.out.println("successful");
		sc.close();
	}
}


