package encryptionApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class EncryptionAlgorithm {
	
	private String message;
	private String key;
	private Hashtable<String, Integer> map;
	private List<String> alphanum;
	private List<String> specialChars;
	private int keyNum;
	
	public EncryptionAlgorithm(String message, String key) {
		this.message = message.toLowerCase();
		this.key = key.toLowerCase();
		map = new Hashtable<String, Integer>(41);
		keyNum = 0;
		
		String a = "a,?,0,f,5,g,6,',h,7,i,8,j,9,k,d,l,m,n,.,o,p,:,e,q,r,s,;,t,u,v,w,b,1,c,4,x,y,z";
		alphanum = new ArrayList<String>(Arrays.asList(a.split(",")));
		
		String b = "!,@,#,$,%,^,&,*, ";
		specialChars = new ArrayList<String>(Arrays.asList(b.split(",")));
		
		String s1;
		Integer counter = 1;
		for (int i = 0; i < alphanum.size(); i++) {
			s1 = new String(alphanum.get(i));
			map.put(s1, counter);
			counter++;
		}
				
		for (int j = 0; j < this.key.length(); j++) {	
			keyNum += map.get((new Character(this.key.charAt(j))).toString());
		}
		
		if (keyNum == map.size()) {
			keyNum++;
		}
	}
	
	public String Encrypt() {
		String encryptedMessage = "";
		String currentChar;
		int currentCharIndex;
		int encryptedCharIndex;
		String encryptedChar;
		
		for (int i = 0; i < this.message.length(); i++) {
			currentChar = (new Character(this.message.charAt(i))).toString();
			if (currentChar.equals(" ")) {
				encryptedChar = randomSpecialChar();
			}
			else if (alphanum.contains(currentChar)) {
				if (i % 2 == 0) {
					currentCharIndex = alphanum.indexOf(currentChar);
				} else {
					currentCharIndex = alphanum.indexOf(currentChar) - 1;
				}
				if (currentCharIndex + keyNum < alphanum.size()) {
					encryptedCharIndex = currentCharIndex + keyNum;
				} else {
					encryptedCharIndex = currentCharIndex + keyNum - alphanum.size();
					while (encryptedCharIndex >= alphanum.size()) {
						encryptedCharIndex -= alphanum.size();
					}
				}
				encryptedChar = (String) alphanum.get(encryptedCharIndex);
			} else {
				encryptedChar = currentChar;
			}
			encryptedMessage += encryptedChar;
		}
		return encryptedMessage;
	}
	
	public String Decrypt() {
		String decryptedMessage = "";
		String currentChar;
		int currentCharIndex;
		int decryptedCharIndex;
		String decryptedChar;
		
		for (int i = 0; i < this.message.length(); i++) {
			currentChar = (new Character(this.message.charAt(i))).toString();
			
			if (specialChars.contains(currentChar)) {
				decryptedChar = " ";
			}
			else if (alphanum.contains(currentChar)) {
				if (i % 2 == 0) {
					currentCharIndex = alphanum.indexOf(currentChar);
				} else {
					currentCharIndex = alphanum.indexOf(currentChar) + 1;
				}
				if (currentCharIndex - keyNum >= 0) {
					decryptedCharIndex = currentCharIndex - keyNum;
				} else {
					decryptedCharIndex = currentCharIndex - keyNum;
					while (decryptedCharIndex < 0) {
						decryptedCharIndex += alphanum.size();
					}
				}
				decryptedChar = (String) alphanum.get(decryptedCharIndex);
			} else {
				decryptedChar = currentChar;
			}
			decryptedMessage += decryptedChar;
		}
		return decryptedMessage;
	}
	
	////////////////////////////
	////////HelperMethods///////
	////////////////////////////
	
	private String randomSpecialChar() {
		Random rand = new Random();
		Integer n = rand.nextInt(specialChars.size());
		String randomSpecialChar = "";
		
		for (Integer i = 0; i < specialChars.size(); i++) {
			if (i.equals(n)) {
				randomSpecialChar = specialChars.get(i);
			}
		}
		return randomSpecialChar;
	}
	
}
