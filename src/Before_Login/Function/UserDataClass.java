package Before_Login.Function;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class UserDataClass {

	public static String userStr = "";
	public static String userId = "";
	public static String userPw = "";
	
	static String filePath = "DataFile/IdPw";
	static String encryptionKey = "happyprogrammer!";
	public static HashMap<String, String> userIdPw = new HashMap<String, String>();
	
	public UserDataClass(String user) throws IOException {
		
		File file = new File(filePath); // File객체 생성
		if (!file.exists()) { // 파일이 존재하지 않으면
			file.createNewFile(); // 신규생성
		}

		FileWriter fw = new FileWriter(file);
		
		BufferedWriter writer = new BufferedWriter(fw);

		getIdPw(user);
		writer.write(user);
		writer.close();
	}
	public UserDataClass(String user, String target) throws IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		
		Cipher cipher = Cipher.getInstance("AES");

		SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey.getBytes(), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

		byte[] encryptBytes = cipher.doFinal(target.getBytes(StandardCharsets.UTF_8));
		String newTarget = new String(encryptBytes);
		user += newTarget + ".";

		File file = new File(filePath); // File객체 생성
		if (!file.exists()) { // 파일이 존재하지 않으면
			file.createNewFile(); // 신규생성
		}

		FileWriter fw = new FileWriter(file);
		
		BufferedWriter writer = new BufferedWriter(fw);

		getIdPw(user);
		writer.write(user);
		writer.close();
	}
	public static void getIdPw(String userStr) throws IOException {
		String userId = "";
		String userPw = "";
		String tempIdPw = "";

		for (int i = 0; i < userStr.length(); ++i) {
			tempIdPw += userStr.charAt(i);
			
			if (userStr.charAt(i) == '/') {
				userId = tempIdPw;
				tempIdPw = "";
				continue;
			}else if (userStr.charAt(i) == '$') {
				userPw = tempIdPw;
				tempIdPw = "";
				
				userIdPw.put(userId, userPw);
				continue;
			}

			
		}
	}
}