//============================================================================
// Name        : ParserPart1
// Author      : Sean Hardin
// Date        : 1/14/18
// Description : Assignment 1 for cs320, parses a given .java file for variables.
// intellij would not let me run anything with the errors in the file so i moved to eclipse.
//============================================================================
package cs320parserassignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parserPart1 {

	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("A.java");
        Scanner scanner = new Scanner(f);//to read the file
        String input = "";
        String text = "";
        while (scanner.hasNextLine()){//until end of file
            input = scanner.nextLine();
            text += input + '\n';
        }
        //System.out.println(text);
        //Pattern p = Pattern.compile("([^';]+;)");//to figure out why regexes were failing me.
        Pattern p = Pattern.compile("(\\w+)\\s(\\w+)();|(\\w+)\\s(\\w+)\\s=\\s'?([^';]*)'?;");
        	//takes 2 words followed by either a semicolon or " = ...;"
        Matcher m = p.matcher(text);
        while (m.find()){//for every match found
        	int n;//use an or in regex, use this to switch which groups are used
        	if (m.group(1)==null)//if right side of or, then use 4,5,6
        		n = 4;
        	else//if left side, use 1,2,3
        		n = 1;
        	System.out.println("Type: " + m.group(n));//generalized prints to display values
            System.out.println("Variable name: " + m.group(n+1));
            if (m.group(n+2).isEmpty())//if no value assigned, print null.
            	System.out.println("Value: null");
            else
            	System.out.println("Value: " + m.group(n+2));
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        }
        scanner.close();//eclipse yelled at me for not closing it.
	}

}
