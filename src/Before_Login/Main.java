package Before_Login;

import Before_Login.Function.UserDataClass;
import Home_Sim.Home_Page_Sim;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
    public static JFrame mainFrame = new JFrame();
    LoginPage loginPage = new LoginPage();
    boolean loginCheck = false;

    private static int mouseX, mouseY;


    static Color kakaoTalkMainColor = new Color(254, 229, 0);
    static Font kakaoFont = new Font("맑은 고딕 굵게", Font.CENTER_BASELINE, 13);

    int screenSizeX = 388;
    int screenSizeY = 638;


    public Main() {

        Image icon = Toolkit.getDefaultToolkit().getImage("image/Before_Login_img/mainImage/kakaoTalkLogo.png");

        mainFrame.setIconImage(icon); // 타이틀바 이미지 바꾸기
        mainFrame.setTitle("KakaoTalk");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(null);
        mainFrame.setSize(screenSizeX, screenSizeY);
        mainFrame.setLocationRelativeTo((Component) null);
        mainFrame.setUndecorated(true);
        mainFrame.setFocusable(true);
        mainFrame.setEnabled(true);
        mainFrame.requestFocusInWindow();
        mainFrame.setShape(new RoundRectangle2D.Double(0, 0, screenSizeX, screenSizeY, 15, 15));
        // mainFrame.getContentPane().setBackground(kakaoTalkMainColor);//jframe은
        // getContentPane을 써야함

        mainFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        mainFrame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen() - mouseX;
                int y = e.getYOnScreen() - mouseY;
                mainFrame.setLocation(x, y);
            }
        });
        mainFrame.setVisible(true);


        loginPage.setOpaque(true);
        loginPage.setLayout(null);
        loginPage.setLocation(0, 0);
        loginPage.setSize(screenSizeX, screenSizeY);
        loginPage.setBackground(kakaoTalkMainColor);
        mainFrame.add(loginPage);
    }
    static void setImage(JLabel player2, String str, int x, int y) {
        File f = new File(str);
        if (!f.exists()) {
            System.out.println("File not found: " + f.getAbsolutePath());
        }
        ImageIcon icon = new ImageIcon(f.getAbsolutePath());
        icon.setImage(icon.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH));

        player2.setIcon(icon);
        player2.revalidate();
        player2.repaint();
        mainFrame.repaint();
        mainFrame.revalidate();
    }

    public static void main(String[] args) throws IOException {
        String filePath = "DataFile/IdPw";
        File file = new File(filePath); // File객체 생성

        if (!file.exists()) {
            System.out.println("파일이 존재하지 않습니다: " + file.getAbsolutePath());
        } else {
            System.out.println("파일을 찾았습니다.");
        }
        FileReader file_reader = null;

        file_reader = new FileReader(file);

        int userCh = 0;

        while ((userCh = file_reader.read()) != -1) {
            UserDataClass.userStr += (char) userCh;
        }
        UserDataClass.getIdPw(UserDataClass.userStr);

        file_reader.close();

        new Main();
    }
}
