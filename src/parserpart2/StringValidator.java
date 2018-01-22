// StringValidator.java
// Sean Hardin
//1/22/18
// a class to make some checks on strings.

package parserpart2;
public class StringValidator {
	
	public static boolean isNotEmpty(String s){//string has some elements
		return s.length() > 0;
	}
	
	public static boolean onlyLetters(String s){//string has only letters and spaces
		for (int i = 0; i < s.length(); i++){
			if (((s.charAt(i) < 'a' || s.charAt(i) > 'z') && (s.charAt(i) < 'A' || s.charAt(i) > 'Z')) && s.charAt(i) != ' '){
				return false;
			}
		}
			return isNotEmpty(s);
	}
	
	public static boolean onlyNumbers(String s){//string only has number characters
		for (int i = 0; i < s.length(); i++){
			if (s.charAt(i) < '0' || s.charAt(i) > '9')
				return false;
		}
		return isNotEmpty(s);
	}
	
	public static boolean noNumbers(String s){//string has no number characters
		for (int i = 0; i < s.length(); i++){
			if (s.charAt(i) >= '0' && s.charAt(i) <= '9')
				return false;
		}
		return isNotEmpty(s);
	}
	
	public static boolean lengthCheck(String s, int n){//checks if string matches given length
		return s.length() == n;
	}
	
	public static String capitalizeWords(String s){//capitalize all space separated words in string
		s.trim();
		int n = s.indexOf(' ');
		String temp = "";
		s = s.substring(0,1).toUpperCase() + s.substring(1);
		while (s.indexOf(' ') >= 0){
			if (s.indexOf(' ') != 0){
				temp += s.substring(0,s.indexOf(' ') + 1);
				String temp2 = "";
				if (s.length() >= s.indexOf(' ') + 2)
					temp2 = s.substring(s.indexOf(' ') + 2);
				s = s.substring(s.indexOf(' ') + 1, s.indexOf(' ') + 2).toUpperCase() + 
					temp2;
			} else {
				s = s.substring(1);
			}
		}
		temp += s;
		return temp;
	}

}
