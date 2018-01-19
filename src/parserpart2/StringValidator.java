package parserpart2;

public class StringValidator {
	
	public static boolean isNotEmpty(String s){
		return s.length() > 0;
	}
	
	public static boolean onlyLetters(String s){
		for (int i = 0; i < s.length(); i++){
			if (((s.charAt(i)<'a'||s.charAt(i)>'z')&&(s.charAt(i)<'A'||s.charAt(i)>'Z')) && s.charAt(i) != ' '){
				System.out.println(s.charAt(i) + " " + i);
				System.out.println((int)'a' + " " + (int)'e');
				return false;
			}
		}
			return isNotEmpty(s);
	}
	
	public static boolean onlyNumbers(String s){
		for (int i = 0; i < s.length(); i++){
			if (s.charAt(i)<'0'||s.charAt(i)>'9')
				return false;
		}
		return isNotEmpty(s);
	}
	
	public static boolean noNumbers(String s){
		for (int i = 0; i < s.length(); i++){
			if (s.charAt(i)>='0'&&s.charAt(i)<='9')
				return false;
		}
		return isNotEmpty(s);
	}
	
	public static boolean lengthCheck(String s, int n){
		return s.length() == n;
	}
	
	public static String capitalizeWords(String s){
		s.trim();
		int n = s.indexOf(' ');
		String temp = "";
		s = s.substring(0,1).toUpperCase() + s.substring(1);
		while (s.indexOf(' ') >= 0){
			if (s.indexOf(' ') != 0){
				//System.out.println(s.indexOf(' '));
				temp += s.substring(0,s.indexOf(' ') + 1);
				String temp2 = "";
				if (s.length() >= s.indexOf(' ') + 2)
					temp2 = s.substring(s.indexOf(' ') + 2);
				s = s.substring(s.indexOf(' ') + 1, s.indexOf(' ')+2).toUpperCase() + 
					temp2;
			} else {
				s = s.substring(1);
			}
		}
		temp += s;
		return temp;
	}

}
