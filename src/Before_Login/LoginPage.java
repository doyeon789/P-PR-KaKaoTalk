package Before_Login;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.LineBorder;

import Before_Login.Function.ExitButton;
import Before_Login.Function.MinimizeButton;
import Before_Login.Function.UserDataClass;
import Home_Do.Home_Page_Do;
import Home_Sim.Home_Page_Sim;

@SuppressWarnings("serial")
public class LoginPage extends JLayeredPane {
	
	JLabel loginLogo = new JLabel();
	JLabel bar1 = new JLabel();
	JLabel bar2 = new JLabel();
	JLabel bar3 = new JLabel();
	JLabel bar4 = new JLabel();
	
	ExitButton exitButton = new ExitButton(Main.mainFrame, true, 11);
	MinimizeButton minimizeButton = new MinimizeButton();
	
	JTextField email = new JTextField("");
	JLabel emailBackgroundText = new JLabel("카카오계정 (이메일 또는 전화번호)");
	JPasswordField password = new JPasswordField("");
	JLabel passwordBackgroundText = new JLabel("비밀번호");
	JLabel emailPwBackground = new JLabel();
	
	public static Color outOfFocusGrayColor = new Color(228, 206, 0);
	static Color barTextFieldGrayColor = new Color(245, 245, 245);
	static Color textFieldGrayColor = new Color(235, 235, 235);
	static Color loginText = new Color(173, 173, 173);
	JLabel visibleEyeLabel = new JLabel();
	
	public static boolean focusChecking = true;
	
	int passwordCount = 0;
	
	JLabel findAccount = new JLabel("카카오계정 찾기");
	JLabel signUp = new JLabel("회원가입");
	JLabel resettingPw = new JLabel("비밀번호 재설정");
	Color otherColorMouseExited = new Color(117,105,0);
	
	JLabel loginButton = new JLabel("로그인", SwingConstants.CENTER);
	Color loginButtonMouseExited = new Color(66, 54, 48);
	Color loginButtonMouseEntered = new Color(89, 73, 65);
	
	boolean loginButtonChecking = false;
	boolean visibleChecking = false;

	public static JLabel X_minus_ON;
	
	public LoginPage() {
		loginLogo.setOpaque(false);
		loginLogo.setSize(135, 115);
		loginLogo.setLocation(126, 90);
		Main.setImage(loginLogo, "image/Before_Login_img/loginPageImage/loginKakaoTalkLogo.png", 135, 115);
		loginLogo.setVisible(true);
		this.add(loginLogo);

		bar1.setOpaque(false);
		bar1.setSize(1, 11);
		bar1.setLocation(328, 9);
		bar1.setBorder(new LineBorder(outOfFocusGrayColor, 2));
		this.add(bar1);

		exitButton.setOpaque(false);
		exitButton.setSize(15, 15);
		exitButton.setLocation(9, 6);
		this.add(exitButton);

		minimizeButton.setOpaque(false);
		minimizeButton.setSize(15, 15);
		minimizeButton.setLocation(29, 6);
		this.add(minimizeButton);

		X_minus_ON = new JLabel();
		X_minus_ON.setOpaque(false);
		X_minus_ON.setBounds(5, 5, 51, 18);
		ImageIcon X_minus_ONI = new ImageIcon("image/Before_Login_img/COM/X.png");
		Image X_minus_ON_img = X_minus_ONI.getImage();
		Image X_minus_ON_logo = X_minus_ON_img.getScaledInstance(51, 18, Image.SCALE_SMOOTH);
		X_minus_ON.setIcon(new ImageIcon(X_minus_ON_logo));
		X_minus_ON.setVisible(false);
		this.add(X_minus_ON);

		JLabel X_minus = new JLabel();
		X_minus.setOpaque(false);
		X_minus.setBounds(0, 0, 70, 33);
		ImageIcon X_minusI = new ImageIcon("image/Before_Login_img/COM/Xminus.PNG");
		Image X_minus_img = X_minusI.getImage();
		Image X_minus_logo = X_minus_img.getScaledInstance(70, 33, Image.SCALE_SMOOTH);
		X_minus.setIcon(new ImageIcon(X_minus_logo));
		this.add(X_minus);


		Main.mainFrame.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent evt) {
				focusChecking = true;
				exitButton.setForeground(Color.gray);
				minimizeButton.setForeground(Color.gray);
				repaint();
				revalidate();
			}
			
			public void focusLost(FocusEvent evt) {
				focusChecking = false;
				exitButton.setForeground(outOfFocusGrayColor);
				minimizeButton.setForeground(outOfFocusGrayColor);

				repaint();
				revalidate();
			}
		});

		email.setOpaque(false);
		email.setSize(202, 37);
		email.setLocation(85, 221);
		email.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		email.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				emailBackgroundText.setVisible(false);
			}
		});
		email.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e){
				String str = email.getText();
				if(str.isEmpty()){
					emailBackgroundText.setVisible(true);
				}
			}
		});
		this.add(email, Integer.valueOf(2));

		emailBackgroundText.setFont(Main.kakaoFont);
		Font emailBackgroundTextCurrentFont = emailBackgroundText.getFont();
		Font emailBackgroundTextNewFont = emailBackgroundTextCurrentFont.deriveFont(12f);
		emailBackgroundText.setOpaque(true);
		emailBackgroundText.setSize(202, 37);
		emailBackgroundText.setLocation(85, 221);
		emailBackgroundText.setBackground(Color.white);
		emailBackgroundText.setForeground(Color.LIGHT_GRAY);
		emailBackgroundText.setFont(emailBackgroundTextNewFont);
		emailBackgroundText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		this.add(emailBackgroundText, Integer.valueOf(1));
		
		password.setOpaque(false);
		password.setSize(202, 37);
		password.setLocation(85, 258);
		password.setEchoChar('●');
		password.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) { // pressed typed 차이점
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					passwordCount--;
				} else {
					passwordCount++;
				}
				
				System.out.println(passwordCount);
				if (passwordCount >= 1) {
					visibleEyeLabel.setVisible(true);
				} else {
					visibleEyeLabel.setVisible(false);
				}
				if (passwordCount >= 4) {
					loginButton.setBackground(loginButtonMouseExited);
					loginButtonChecking = true;
					
					loginButton.setForeground(Color.white);
					loginButton.setBorder(new LineBorder(Color.black, 1));
				} else {
					loginButton.setBackground(barTextFieldGrayColor);
					loginButtonChecking = false;
					
					loginButton.setForeground(loginText);
					loginButton.setBorder(new LineBorder(textFieldGrayColor, 1));
				}
				repaint();
			}
		});
		password.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				passwordBackgroundText.setVisible(false);
			}
		});
		password.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e){
				char[] strC = password.getPassword();
				String str = new String(strC);
				if(str.isEmpty()){
					passwordBackgroundText.setVisible(true);
				}
			}
		});
		this.add(password, Integer.valueOf(2));
		
		passwordBackgroundText.setFont(Main.kakaoFont);
		Font passwordBackgroundTextCurrentFont = passwordBackgroundText.getFont();
		Font passwordBackgroundTextNewFont = passwordBackgroundTextCurrentFont.deriveFont(12f);
		passwordBackgroundText.setOpaque(false);
		passwordBackgroundText.setSize(202, 37);
		passwordBackgroundText.setLocation(85, 258);
		passwordBackgroundText.setBackground(Color.white);
		passwordBackgroundText.setForeground(Color.LIGHT_GRAY);
		passwordBackgroundText.setFont(passwordBackgroundTextNewFont);
		passwordBackgroundText.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		this.add(passwordBackgroundText, Integer.valueOf(1));
		
		emailPwBackground.setOpaque(true);
		emailPwBackground.setSize(242, 76);
		emailPwBackground.setLocation(75, 220);
		emailPwBackground.setBackground(Color.white);
		emailPwBackground.setBorder(new LineBorder(textFieldGrayColor, 1));
		this.add(emailPwBackground, Integer.valueOf(0));
		
		bar2.setOpaque(true);
		bar2.setSize(237, 1);
		bar2.setLocation(77, 260);
		bar2.setBorder(new LineBorder(barTextFieldGrayColor, 1));
		this.add(bar2, Integer.valueOf(2));
		
		loginButton.setOpaque(true);
		loginButton.setSize(242, 39);
		loginButton.setLocation(75, 302);
		loginButton.setFont(Main.kakaoFont);
		loginButton.setForeground(loginText);
		loginButton.setBackground(barTextFieldGrayColor);
		loginButton.setBorder(new LineBorder(textFieldGrayColor, 1));
		loginButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent me) {
				if (loginButtonChecking) {
					loginButton.setBackground(loginButtonMouseEntered);
				}
				
				repaint();
			}
			
			public void mouseExited(MouseEvent me) {
				if (loginButtonChecking) {
					loginButton.setBackground(loginButtonMouseExited);
				}
				
				repaint();
			}
			//startlogin
			public void mousePressed(MouseEvent e) {

				String filePath = "DB/" + email.getText() + ".txt";
				// File 객체 생성
				// File 객체 생성
				File file = new File(filePath);

				String Get_PW = String.valueOf(password.getPassword());

				boolean isID;
				boolean isPW;

				// Check if the file exists
				if (file.exists()) {
					isID = true;
				} else {
					isID = false;
				}

				// Compare hashed password with the stored hash
				try {
					String fileContent = Files.readAllLines(Paths.get(filePath)).get(0);
					if (Get_PW.equals(fileContent)) {
						isPW = true;
					} else {
						isPW = false;
					}
				} catch (IOException er) {
					System.out.println("파일을 읽는 도중 오류 발생: " + er.getMessage());
					isPW = false;
				} catch (IndexOutOfBoundsException er) {
					System.out.println("파일이 비어 있거나 예상보다 내용이 적습니다.");
					isPW = false;
				}

				if (isID && isPW) {
					System.out.println("로그인 성공");
					if(email.getText().equals("ldyeon789@naver.com")){
						new Home_Page_Do();
					}
					if(email.getText().equals("Sim123@naver.com")){
						new Home_Page_Sim();
					}
				} else {
					System.out.println("로그인 실패");
				}
			}
		});
		
		this.add(loginButton, Integer.valueOf(0));
		
		visibleEyeLabel.setOpaque(true);
		visibleEyeLabel.setSize(21, 17);
		visibleEyeLabel.setLocation(288, 269);
		visibleEyeLabel.setVisible(false);
		this.add(visibleEyeLabel, Integer.valueOf(2));
		Main.setImage(visibleEyeLabel, "image/Before_Login_img/loginPageImage/visibleTrueGray.png", 21, 17);
		visibleEyeLabel.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent me) {
				if (visibleChecking) {
					Main.setImage(visibleEyeLabel, "image/Before_Login_img/loginPageImage/visibleFalseBlack.png", 21, 17);
				} else {
					Main.setImage(visibleEyeLabel, "image/Before_Login_img/loginPageImage/visibleTrueBlack.png", 21, 17);
				}

				repaint();
			}
			
			public void mouseExited(MouseEvent me) {
				if (visibleChecking) {
					Main.setImage(visibleEyeLabel, "image/Before_Login_img/loginPageImage/visibleFalseGray.png", 21, 17);
				} else {
					Main.setImage(visibleEyeLabel, "image/Before_Login_img/loginPageImage/visibleTrueGray.png", 21, 17);
				}
				
				repaint();
			}
		});
		visibleEyeLabel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
				if(visibleChecking) {
					password.setEchoChar('●');
					Main.setImage(visibleEyeLabel, "image/Before_Login_img/loginPageImage/visibleTrueBlack.png", 21, 17);
				}
				else {
					password.setEchoChar((char) 0);
					Main.setImage(visibleEyeLabel, "image/Before_Login_img/loginPageImage/visibleFalseBlack.png", 21, 17);
				}
				
				
				
				visibleChecking = !visibleChecking;
				repaint();
			}
		});
		
		findAccount.setFont(Main.kakaoFont);
		Font findAccountCurrentFont = findAccount.getFont();
		Font findAccountNewFont = findAccountCurrentFont.deriveFont(12f);
		findAccount.setOpaque(false);
		findAccount.setSize(91, 20);
		findAccount.setLocation(61, 581);
		findAccount.setFont(findAccountNewFont);
		findAccount.setForeground(otherColorMouseExited);
		repaint();
		findAccount.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent me) {
				findAccount.setForeground(Color.darkGray);
				
				repaint();
			}

			public void mouseExited(MouseEvent me) {
				findAccount.setForeground(otherColorMouseExited);
				
				repaint();
			}
		});
		this.add(findAccount);
		
		
		bar3.setOpaque(false);
		bar3.setSize(1, 11);
		bar3.setLocation(159, 586);
		bar3.setBorder(new LineBorder(outOfFocusGrayColor, 2));
		this.add(bar3);
		
		
		signUp.setFont(Main.kakaoFont);
		Font signUpCurrentFont = signUp.getFont();
		Font signUpNewFont = signUpCurrentFont.deriveFont(12f);
		signUp.setOpaque(false);
		signUp.setSize(50, 20);
		signUp.setLocation(167, 581);
		signUp.setFont(signUpNewFont);
		signUp.setForeground(otherColorMouseExited);
		repaint();
		signUp.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent me) {
				signUp.setForeground(Color.darkGray);
				
				repaint();
			}
			
			public void mouseExited(MouseEvent me) {
				signUp.setForeground(otherColorMouseExited);
				
				repaint();
			}
		});
		signUp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				new SignUpPage();
			}
		});
		
		this.add(signUp);
		
		bar4.setOpaque(false);
		bar4.setSize(1, 11);
		bar4.setLocation(224, 586);
		bar4.setBorder(new LineBorder(outOfFocusGrayColor, 2));
		this.add(bar4);
		
		
		resettingPw.setFont(Main.kakaoFont);
		Font resettingPwCurrentFont = resettingPw.getFont();
		Font resettingPwNewFont = resettingPwCurrentFont.deriveFont(12f);
		resettingPw.setOpaque(false);
		resettingPw.setSize(90, 20);
		resettingPw.setLocation(233, 581);
		resettingPw.setFont(resettingPwNewFont);
		resettingPw.setForeground(otherColorMouseExited);
		repaint();
		resettingPw.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent me) {
				resettingPw.setForeground(Color.darkGray);
				
				repaint();
			}
			
			public void mouseExited(MouseEvent me) {
				resettingPw.setForeground(otherColorMouseExited);
				
				repaint();
			}
		});
		this.add(resettingPw);
		
	}

	public static void getIdPw(String str) {
		String tempIdPw = "";
		System.out.println(UserDataClass.userStr);
		for (int i = 0; i < str.length(); ++i) {
			tempIdPw += str.charAt(i);
			System.out.println(tempIdPw);
			System.out.println(UserDataClass.userId + " " + UserDataClass.userPw + "fkekfklelfe");
			if (str.charAt(i) == '/') {
				UserDataClass.userId = tempIdPw;
				tempIdPw = "";
				continue;
			} else if (str.charAt(i) == '$') {
				UserDataClass.userPw = tempIdPw;

				tempIdPw = "";
				System.out.println(UserDataClass.userId + " " + UserDataClass.userPw);
				UserDataClass.userIdPw.put(UserDataClass.userId, UserDataClass.userPw);
				continue;
			}

		}
	}

	public boolean hashmapChecking(HashMap<String, String> userIdPwTemp, String target, String mainId) {
	    String idTemp = "";

	    // mainId에 해당하는 키를 찾아서 비교
	    if (userIdPwTemp.containsKey(mainId)) {
	        idTemp = filterOutId(mainId); // mainId를 필터링
	        System.out.println(idTemp + "/////" + mainId);
	        System.out.println(userIdPwTemp.get(mainId).toString() + "/////" + target + "$");

	        String a_1 = userIdPwTemp.get(mainId).toString();
	        String a_2 = (target + "$").toString();
	        int count = 0;
	        
	        for (int i = 0; i < a_1.length(); ++i) {
	            if ((a_1.charAt(i) == a_2.charAt(i)) || (a_1.charAt(i) == 63 || a_2.charAt(i) == 65533)) {
	            } else {
	                ++count; 
	            }
	        }

	        if (count == 0) {
	            return true;
	        }
	    }
	    return false;
	}

	public String filterOutId(String hashMapId) {
		String resultId = "";

		for (int i = 0; i < hashMapId.length(); ++i) {
			if (hashMapId.charAt(i) != '/') {
				resultId += hashMapId.charAt(i);
			} else {
				break;
			}
		}
		System.out.println(resultId + "###$#$#$#$# 222");

		return resultId;
	}
}
