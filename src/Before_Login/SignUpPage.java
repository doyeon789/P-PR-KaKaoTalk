package Before_Login;

import Before_Login.Function.RoundedLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class SignUpPage extends JFrame {
    JPanel signUpPagePanel = new JPanel();
    JLabel backgroundLabel = new JLabel();

    //
    int firstPageObjectSize = 1;
    JLabel titleTextLabel = new JLabel("<html>카카오계정으로 사용할<br>이메일을 입력해 주세요.</html>");//
    Color backgroundLabelColor = new Color(224, 224, 224);
    JLabel progressBar = new JLabel();//
    static JTextField emailText = new JTextField("");//
    JLabel emailFieldBar = new JLabel();//
    JLabel warningEmailTextLabel = new JLabel();//

    static JTextField numberText = new JTextField("");//
    JLabel numberTextBar = new JLabel();//
    JLabel warningNumberTextLabel = new JLabel();//

    JLabel requestLabel = new JLabel();//
    boolean ClickedRequestLabel = false;
    JLabel explainLabel = new JLabel("<html>⠐ 입력한 이메일로 인증번호가 발송됩니다.<br>⠐ 인증메일을 받을 수 있도록 자주 쓰는 이메일을 입력해 주세요.</html>");//

    RoundedLabel nextButton = new RoundedLabel("다음", 11);//
    Color nextButtonColor = new Color(240, 240, 240);
    Color nextButtonColor_Done = new Color(66,54,48);

    JLabel failureRequestNumberLabel = new JLabel("<html><u>인증메일을 받지 못하셨나요?</u></html>");

    Object [] firstPage = new Object[4];
    int mouseX, mouseY;
    int screenSizeX = 750;
    int screenSizeY = 750;

    public static boolean running = false;  // 쓰레드 실행 여부
    public static Thread thread;

    public static boolean running2 = false;  // 쓰레드 실행 여부
    public static Thread thread2;

    static String emailSendNum = "";

    JLabel titleTextLabel2 = new JLabel("<html>카카오계정 로그인에 사용할<br>비밀번호를 등록해 주세요.</html>");
    JLabel progressBar2 = new JLabel();
    JLabel textKakaoAccount2 = new JLabel("카카오계정");
    JLabel currentKakaoAccount2 = new JLabel("kimdongyoung@gmail.com");
    JLabel password2 = new JLabel("비밀번호");

    static JPasswordField passwordText2 = new JPasswordField("");//
    JLabel passwordFieldBar2 = new JLabel();//
    JLabel warningPasswordTextLabel2 = new JLabel();//

    static JPasswordField passwordReInputText2 = new JPasswordField("");//
    JLabel passwordReInputFieldBar2 = new JLabel();//
    JLabel warningPasswordReInputTextLabel2 = new JLabel();//

    JLabel explainLabel2 = new JLabel("<html>⠐ 비밀번호는 8~32자리의 영문 대소문자, 숫자, 특수문자를 조합하여 설정해 주세요.<br>⠐ 다른 사이트에서 사용하는 것과 동일하거나 쉬운 비밀번호는 사용하지 마세요.<br>⠐ 안전한 계정 사용을 위해 비밀번호는 주기적으로 변경해 주세요.</html>");

    RoundedLabel nextButton2 = new RoundedLabel("다음", 11);//
    Color nextButtonColor2 = new Color(240, 240, 240);
    Color nextButtonColor_Done2 = new Color(66,54,48);

    public SignUpPage() {

        this.setSize(750, 750);
        this.setLocation(0,0);
        this.setLayout(null);
        this.setLocationRelativeTo((Component) null);
        this.setUndecorated(true);
        this.setFocusable(true);
        this.setEnabled(true);
        this.requestFocusInWindow();
        this.setBackground(Color.white);
        this.setShape(new RoundRectangle2D.Double(0, 0, screenSizeX, screenSizeY, 15, 15));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 마우스 클릭 위치 저장

                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        // 마우스 드래그 이벤트 등록
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // 프레임의 새로운 위치 설정
                int x = e.getXOnScreen() - mouseX;
                int y = e.getYOnScreen() - mouseY;
                SignUpPage.this.setLocation(x, y);
            }
        });

        signUpPagePanel.setSize(750, 750);
        signUpPagePanel.setLocation(0,0);
        signUpPagePanel.setLayout(null);
        signUpPagePanel.setBackground(Color.white);
        this.add(signUpPagePanel);

        backgroundLabel.setOpaque(false);
        backgroundLabel.setSize(582, 649);
        backgroundLabel.setLocation(85, 52);
        backgroundLabel.setLayout(null);
        backgroundLabel.setBackground(Color.white);
        backgroundLabel.setBorder(new LineBorder(backgroundLabelColor, 1));
        signUpPagePanel.add(backgroundLabel);

        progressBar.setOpaque(false);
        progressBar.setSize(70, 17);
        progressBar.setLocation(145, 101);
        progressBar.setLayout(null);
        Main.setImage(progressBar, "image/Before_Login_img/signUpPageImage/progressImage1.png", 70, 17);
        signUpPagePanel.add(progressBar);

        titleTextLabel.setFont(Main.kakaoFont);
        Font titleTextLabelCurrentFont = titleTextLabel.getFont();
        Font titleTextLabelNewFont = titleTextLabelCurrentFont.deriveFont(22f);
        titleTextLabel.setOpaque(false);
        titleTextLabel.setSize(250, 60);
        titleTextLabel.setLocation(145, 143);
        titleTextLabel.setFont(titleTextLabelNewFont);
        signUpPagePanel.add(titleTextLabel);

        Font emailTextCurrentFont = emailText.getFont();
        Font emailTextNewFont = emailTextCurrentFont.deriveFont(18f);
        emailText.setOpaque(false);
        emailText.setSize(250, 60);
        emailText.setLocation(145, 250);
        emailText.setFont(emailTextNewFont);
        emailText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        signUpPagePanel.add(emailText);

        emailFieldBar.setOpaque(false);
        emailFieldBar.setSize(451, 2);
        emailFieldBar.setLocation(145, 302);
        emailFieldBar.setLayout(null);
        emailFieldBar.setBorder(new LineBorder(Color.black, 2));
        signUpPagePanel.add(emailFieldBar);

        Font numberTextCurrentFont = numberText.getFont();
        Font numberTextNewFont = numberTextCurrentFont.deriveFont(18f);
        numberText.setOpaque(false);
        numberText.setSize(250, 60);
        numberText.setLocation(145, 308);
        numberText.setFont(numberTextNewFont);
        numberText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        signUpPagePanel.add(numberText);
        numberText.setVisible(false);

        numberTextBar.setOpaque(false);
        numberTextBar.setSize(451, 2);
        numberTextBar.setLocation(145, 360);
        numberTextBar.setLayout(null);
        numberTextBar.setBorder(new LineBorder(Color.black, 2));
        signUpPagePanel.add(numberTextBar);
        numberTextBar.setVisible(false);

        requestLabel.setOpaque(false);
        requestLabel.setSize(94, 35);
        requestLabel.setLocation(509, 263);
        requestLabel.setLayout(null);
        Main.setImage(requestLabel, "image/Before_Login_img/signUpPageImage/requestImage.png", 94, 35);
        signUpPagePanel.add(requestLabel);
        requestLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                naverMailSend();

                emailText.setForeground(Color.LIGHT_GRAY);
                emailFieldBar.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));

                explainLabel.setVisible(false);
                numberText.setVisible(true);
                numberTextBar.setVisible(true);
                requestLabel.setVisible(false);
                failureRequestNumberLabel.setVisible(true);
                repaint();

                if (!running) {
                    running = true;
                    thread = new Thread(() -> {
                        while (running) {
                            if(numberText.getText().equals(emailSendNum)){
                                nextButton.setForeground(Color.white);
                                nextButton.setBackground(nextButtonColor_Done);
                            }
                            else{
                                nextButton.setForeground(Color.BLACK);
                                nextButton.setBackground(nextButtonColor);
                            }

                        }
                    });
                    thread.start();
                }
            }
        });

        Font explainLabelCurrentFont = explainLabel.getFont();
        Font explainLabelNewFont = explainLabelCurrentFont.deriveFont(11f);
        explainLabel.setOpaque(false);
        explainLabel.setFont(explainLabelNewFont);
        explainLabel.setForeground(Color.gray);
        explainLabel.setSize(354, 40);
        explainLabel.setLocation(145, 356);
        explainLabel.setLayout(null);
        signUpPagePanel.add(explainLabel);

        nextButton.setOpaque(true);
        nextButton.setSize(460, 49);
        nextButton.setLocation(143, 602);
        nextButton.setLayout(null);
        nextButton.setBackground(nextButtonColor);
        signUpPagePanel.add(nextButton);
        nextButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(numberText.getText().equals(emailSendNum)){
                    titleTextLabel.setVisible(false);
                    progressBar.setVisible(false);
                    emailText.setVisible(false);
                    emailFieldBar.setVisible(false);
                    warningEmailTextLabel.setVisible(false);
                    numberText.setVisible(false);
                    numberTextBar.setVisible(false);
                    warningNumberTextLabel.setVisible(false);
                    requestLabel.setVisible(false);
                    explainLabel.setVisible(false);
                    nextButton.setVisible(false);
                    failureRequestNumberLabel.setVisible(false);

                    currentKakaoAccount2.setText(emailText.getText());
                    titleTextLabel2.setVisible(true);
                    progressBar2.setVisible(true);
                    textKakaoAccount2.setVisible(true);
                    currentKakaoAccount2.setVisible(true);
                    password2.setVisible(true);
                    passwordText2.setVisible(true);
                    passwordFieldBar2.setVisible(true);
                    passwordReInputText2.setVisible(true);
                    passwordReInputFieldBar2.setVisible(true);
                    explainLabel2.setVisible(true);
                }
                repaint();
            }
        });

        Font failureRequestNumberLabelCurrentFont = failureRequestNumberLabel.getFont();
        Font failureRequestNumberLabelNewFont = failureRequestNumberLabelCurrentFont.deriveFont(13f);
        failureRequestNumberLabel.setOpaque(false);
        failureRequestNumberLabel.setFont(failureRequestNumberLabelNewFont);
        failureRequestNumberLabel.setForeground(Color.black);
        failureRequestNumberLabel.setSize(354, 40);
        failureRequestNumberLabel.setLocation(145, 406);
        failureRequestNumberLabel.setLayout(null);
        signUpPagePanel.add(failureRequestNumberLabel);
        failureRequestNumberLabel.setVisible(false);
        failureRequestNumberLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                TransparentGrayFrame transparentGrayFrame = new TransparentGrayFrame();
                FailureRequestNumber failureRequestNumber = new FailureRequestNumber(transparentGrayFrame);

                repaint();
            }
        });

        titleTextLabel2.setFont(Main.kakaoFont);
        Font titleTextLabel2CurrentFont = titleTextLabel2.getFont();
        Font titleTextLabel2NewFont = titleTextLabel2CurrentFont.deriveFont(22f);
        titleTextLabel2.setOpaque(false);
        titleTextLabel2.setSize(300, 60);
        titleTextLabel2.setLocation(145, 143);
        titleTextLabel2.setFont(titleTextLabel2NewFont);
        signUpPagePanel.add(titleTextLabel2);
        titleTextLabel2.setVisible(false);

        progressBar2.setOpaque(false);
        progressBar2.setSize(70, 17);
        progressBar2.setLocation(145, 101);
        progressBar2.setLayout(null);
        Main.setImage(progressBar2, "image/Before_Login_img/signUpPageImage/progressImage2.png", 70, 17);
        signUpPagePanel.add(progressBar2);
        progressBar2.setVisible(false);

        Font textKakaoAccount2CurrentFont = textKakaoAccount2.getFont();
        Font textKakaoAccount2NewFont = textKakaoAccount2CurrentFont.deriveFont(11f);
        textKakaoAccount2.setOpaque(false);
        textKakaoAccount2.setFont(textKakaoAccount2NewFont);
        textKakaoAccount2.setForeground(Color.gray);
        textKakaoAccount2.setSize(354, 40);
        textKakaoAccount2.setLocation(145, 216);
        textKakaoAccount2.setLayout(null);
        signUpPagePanel.add(textKakaoAccount2);
        textKakaoAccount2.setVisible(false);

        Font currentKakaoAccount2CurrentFont = currentKakaoAccount2.getFont();
        Font currentKakaoAccount2NewFont = currentKakaoAccount2CurrentFont.deriveFont(16f);
        currentKakaoAccount2.setOpaque(false);
        currentKakaoAccount2.setFont(currentKakaoAccount2NewFont);
        currentKakaoAccount2.setForeground(Color.black);
        currentKakaoAccount2.setSize(354, 40);
        currentKakaoAccount2.setLocation(145, 245);
        currentKakaoAccount2.setLayout(null);
        signUpPagePanel.add(currentKakaoAccount2);
        currentKakaoAccount2.setVisible(false);

        Font password2CurrentFont = password2.getFont();
        Font password2tNewFont = password2CurrentFont.deriveFont(11f);
        password2.setOpaque(false);
        password2.setFont(password2tNewFont);
        password2.setForeground(Color.gray);
        password2.setSize(354, 40);
        password2.setLocation(145, 316);
        password2.setLayout(null);
        signUpPagePanel.add(password2);
        password2.setVisible(false);

        Font passwordText2CurrentFont = passwordText2.getFont();
        Font passwordText2NewFont = passwordText2CurrentFont.deriveFont(18f);
        passwordText2.setOpaque(false);
        passwordText2.setSize(250, 60);
        passwordText2.setLocation(145, 340);
        passwordText2.setFont(passwordText2NewFont);
        passwordText2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        signUpPagePanel.add(passwordText2);
        passwordText2.setVisible(false);


        passwordFieldBar2.setOpaque(false);
        passwordFieldBar2.setSize(451, 2);
        passwordFieldBar2.setLocation(145, 392);
        passwordFieldBar2.setLayout(null);
        passwordFieldBar2.setBorder(new LineBorder(Color.black, 2));
        signUpPagePanel.add(passwordFieldBar2);
        passwordFieldBar2.setVisible(false);

        Font passwordReInputText2CurrentFont = passwordReInputText2.getFont();
        Font passwordReInputText2NewFont = passwordReInputText2CurrentFont.deriveFont(18f);
        passwordReInputText2.setOpaque(false);
        passwordReInputText2.setSize(250, 60);
        passwordReInputText2.setLocation(145, 420);
        passwordReInputText2.setFont(passwordReInputText2NewFont);
        passwordReInputText2.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        signUpPagePanel.add(passwordReInputText2);
        passwordReInputText2.setVisible(false);


        passwordReInputFieldBar2.setOpaque(false);
        passwordReInputFieldBar2.setSize(451, 2);
        passwordReInputFieldBar2.setLocation(145, 472);
        passwordReInputFieldBar2.setLayout(null);
        passwordReInputFieldBar2.setBorder(new LineBorder(Color.black, 2));
        signUpPagePanel.add(passwordReInputFieldBar2);
        passwordReInputFieldBar2.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
        passwordReInputFieldBar2.setVisible(false);

        Font explainLabel2CurrentFont = explainLabel2.getFont();
        Font explainLabel2NewFont = explainLabel2CurrentFont.deriveFont(11f);
        explainLabel2.setOpaque(false);
        explainLabel2.setFont(explainLabel2NewFont);
        explainLabel2.setForeground(Color.gray);
        explainLabel2.setSize(454, 80);
        explainLabel2.setLocation(141, 476);
        explainLabel2.setLayout(null);
        signUpPagePanel.add(explainLabel2);
        explainLabel2.setVisible(false);

        nextButton2.setOpaque(true);
        nextButton2.setSize(460, 49);
        nextButton2.setLocation(143, 602);
        nextButton2.setLayout(null);
        nextButton2.setBackground(nextButtonColor);
        signUpPagePanel.add(nextButton2);
        nextButton2.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(String.valueOf(passwordText2.getPassword()).equals(String.valueOf(passwordReInputText2.getPassword()))) {
                    String filePath = "DB/" + emailText.getText() + ".txt"; // 생성할 파일의 경로

                    String Get_PW = new String(passwordText2.getPassword());

                    try (FileWriter writer = new FileWriter(filePath)) {
                        writer.write(Get_PW);
                    } catch (IOException er) {
                        System.err.println("파일 작성 중 오류가 발생했습니다: " + er.getMessage());
                    }

                    thread.stop();
                    thread2.stop();

                    dispose();
                }
                repaint();
            }
        });
        passwordReInputText2.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                running2 = !running2;
                thread2 = new Thread(() -> {
                    while (running2) {
                        if(String.valueOf(passwordText2.getPassword()).equals(String.valueOf(passwordReInputText2.getPassword()))) {
                            nextButton2.setForeground(Color.white);
                            nextButton2.setBackground(nextButtonColor_Done2);
                        }
                        else{
                            nextButton2.setForeground(Color.black);
                            nextButton2.setBackground(nextButtonColor2);
                        }
                    }
                });
                thread2.start();
            }
        });

        this.setVisible(true);
        this.repaint();
    }

    public static void naverMailSend() {
        String host = "smtp.naver.com";
        String user = "...";
        String password = "...";

        // SMTP 서버 정보를 설정한다.
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtps.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS 사용 설정
        props.put("mail.smtps.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.auth", "true");

        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); // TLS 버전 설정

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailText.getText()));

            // 메일 제목
            message.setSubject("카카오계정 가입 인증번호");

            // 메일 내용
            emailSendNum = numberGen(8, 1);
            String emailContent = "" + "아래 인증번호를 확인하여 이메일 인증을 완료해 주세요.\n\n----------------------------------------------------------------------\n\n        카카오계정  " + emailText.getText() + "\n        인증번호     " + emailSendNum + "\n\n----------------------------------------------------------------------";
            message.setText(emailContent);

            // 메일 전송
            Transport.send(message);
            System.out.println("Success Message Send");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public static String numberGen(int len, int dupCd) {

        Random rand = new Random();
        String numStr = ""; // 난수가 저장될 변수

        for (int i = 0; i < len; i++) {
            String ran = Integer.toString(rand.nextInt(10));

            if (dupCd == 1) {
                numStr += ran;
            } else if (dupCd == 2) {
                if (!numStr.contains(ran)) {
                    numStr += ran;
                } else {
                    i -= 1;
                }
            }
        }

        return numStr;
    }
}
class FailureRequestNumber extends JFrame {
    JLabel mainText = new JLabel("인증메일을 받지 못하셨나요?");
    JLabel subText = new JLabel("<html>이메일이 정확한지 확인해 주세요.<br>메일 서비스에 따라 인증번호 발송이 늦어질 수 있습니<br>다.</html>");
    Font mainTextFont = new Font("감탄로드돋움체 Bold TTF 보통", Font.BOLD, 18);

    Color anotherEmailButtonColor = new Color(240,240,240);

    public FailureRequestNumber(JFrame back) {
        RoundedLabel ResendButton = new RoundedLabel("인증번호 재발송", 11);
        RoundedLabel anotherEmailButton = new RoundedLabel("다른 이메일로 인증", 11);

        this.setSize(385, 330);
        this.setLocation(0,0);
        this.setLayout(null);
        this.setLocationRelativeTo((Component) null);
        this.setUndecorated(true);
        this.setEnabled(true);
        this.requestFocusInWindow();
        this.getContentPane().setBackground(Color.white);

        JLabel exitButton = new JLabel();
        Main.setImage(exitButton, "image/Before_Login_img/signUpPageImage/exitButton.png", 29, 38);
        exitButton.setOpaque(false);
        exitButton.setSize(45, 45);
        exitButton.setLocation(340, 6);
        exitButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                FailureRequestNumber.this.setVisible(false);
                back.setVisible(false);
            }
        });

        mainText.setOpaque(false);
        mainText.setSize(280, 60);
        mainText.setLocation(30, 32);
        mainText.setFont(mainTextFont);
        this.add(mainText);

        Font subTextCurrentFont = subText.getFont();
        Font subTextNewFont = subTextCurrentFont.deriveFont(12.8f);
        subText.setOpaque(false);
        subText.setSize(330, 60);
        subText.setLocation(27, 88);
        subText.setFont(subTextNewFont);
        subText.setForeground(Color.GRAY);
        subText.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.add(subText);

        ResendButton.setOpaque(true);
        ResendButton.setSize(316, 41);
        ResendButton.setLocation(34, 209);
        ResendButton.setLayout(null);
        //nextButton.setShape(new RoundRectangle2D.Double(0, 0, screenSizeX, screenSizeY, 15, 15));
        ResendButton.setBackground(Main.kakaoTalkMainColor);
        this.add(ResendButton);


        anotherEmailButton.setOpaque(true);
        anotherEmailButton.setSize(316, 41);
        anotherEmailButton.setLocation(34, 259);
        anotherEmailButton.setLayout(null);
        //nextButton.setShape(new RoundRectangle2D.Double(0, 0, screenSizeX, screenSizeY, 15, 15));
        anotherEmailButton.setBackground(anotherEmailButtonColor);
        this.add(anotherEmailButton);



        this.add(exitButton);
        this.setVisible(true);
    }
}

class TransparentGrayFrame extends JFrame {
    public TransparentGrayFrame() {

        this.setSize(380, 330);
        this.setLocation(0,0);
        this.setLayout(null);
        this.setLocationRelativeTo((Component) null);
        this.setUndecorated(true);
        this.setEnabled(true);
        this.requestFocusInWindow();
        this.getContentPane().setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 창의 크기 설정
        this.setSize(2000, 900);

        // 창의 배경색 설정
        this.getContentPane().setBackground(Color.GRAY);

        // 창의 불투명도 설정 (0.0: 완전 투명, 1.0: 완전 불투명)
        this.setOpacity(0.6f); // 70% 불투명

        // 창을 화면 중앙에 배치
        this.setLocationRelativeTo(null);

        this.setFocusableWindowState(false);
        // 창을 표시
        this.setVisible(true);
    }
}





