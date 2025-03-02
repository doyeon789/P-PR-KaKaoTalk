package Home_Sim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

import After_Login_Sim.TalkPage_Do.TalkPage_Do;
import After_Login_Sim.TalkPage_Me.TalkPage_Me;

public class Home_Page_Sim {
    static JFrame frame_Home = new JFrame("");

    public static boolean isTalk_myself_Open = false;
    public static boolean isTalk_Do_Open = false;

    public static String Current_Chat = "";
    public static DateTimeFormatter Current_Time;

    public Home_Page_Sim() {
        frame_Home.setSize(398, 639);
        frame_Home.setLocation(900, 285);
        frame_Home.setUndecorated(true);
        frame_Home.setBackground(new Color(0, 0, 0, 0));
        frame_Home.setResizable(false);

        JPanel Home_panel = new JPanel();
        Home_panel.setLayout(null);
        Home_panel.setOpaque(false);
        Home_panel.setBounds(0, 0, 398, 639);

        JPanel Chatting_Panel = new JPanel();
        Chatting_Panel.setLayout(null);
        Chatting_Panel.setOpaque(false);
        Chatting_Panel.setBounds(0, 0, 398, 639);

        JPanel Friends_Panel = new JPanel();
        Friends_Panel.setLayout(null);
        Friends_Panel.setOpaque(false);
        Friends_Panel.setBounds(0, 0, 398, 639);

        Friends_Page(Friends_Panel);

        final Point[] mouseClickPoint = {null};

        JLabel Move = new JLabel();
        Move.setOpaque(false);
        Move.setBounds(65, 0, 332, 50);
        Move.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseClickPoint[0] = e.getPoint();
            }
        });
        Move.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (mouseClickPoint[0] != null) {
                    Point current = frame_Home.getLocation();
                    frame_Home.setLocation(
                            current.x + e.getX() - mouseClickPoint[0].x,
                            current.y + e.getY() - mouseClickPoint[0].y
                    );
                }
            }
        });

        JLabel X_button = new JLabel();
        X_button.setOpaque(false);
        X_button.setBounds(5, 3, 56, 21);
        ImageIcon X_buttonI = new ImageIcon("image/After_Login_img/Sim/COM/X.png");
        Image X_button_img = X_buttonI.getImage();
        Image X_button_logo = X_button_img.getScaledInstance(56, 21, Image.SCALE_SMOOTH);
        X_button.setIcon(new ImageIcon(X_button_logo));
        X_button.setVisible(false);

        JLabel X_button_Box = new JLabel();
        X_button_Box.setBounds(10, 5, 18, 18);
        X_button_Box.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                X_button.setVisible(false);
                frame_Home.dispose();
            }

            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                X_button.setVisible(true);
                Home_panel.revalidate();
                Home_panel.repaint();
                X_button_Box.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                X_button.setVisible(false);
                Home_panel.revalidate();
                Home_panel.repaint();
                X_button_Box.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        JLabel Chatting = new JLabel();
        Chatting.setBounds(20,118,25,25);
        Chatting.setOpaque(false);
        Chatting.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                Chatting_Panel.setVisible(true);
                Friends_Panel.setVisible(false);
                Chatting_Page(Chatting_Panel);
            }
        });

        JLabel Friends = new JLabel();
        Friends.setBounds(20,62,25,25);
        Friends.setOpaque(false);
        Friends.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                Chatting_Panel.setVisible(false);
                Friends_Panel.setVisible(true);
                Friends_Page(Friends_Panel);
            }
        });

        // ESC 키 설정
        Home_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "closeWindow");
        Home_panel.getActionMap().put("closeWindow", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                X_button.setVisible(false);
                frame_Home.dispose();
            }
        });

        Home_panel.add(Friends);
        Home_panel.add(Chatting);

        Home_panel.add(X_button);
        Home_panel.add(X_button_Box);
        Home_panel.add(Move);

        Home_panel.add(Friends_Panel);
        Home_panel.add(Chatting_Panel);

        frame_Home.add(Home_panel);
        frame_Home.setVisible(true);
    }
    private static void Friends_Page(JPanel Friends_Panel){
        JLabel Background = new JLabel();
        Background.setOpaque(false);
        Background.setBounds(0, 0, 398, 639);
        ImageIcon BackgroundI = new ImageIcon("image/After_Login_img/Sim/Home/Home.PNG");
        Image Background_img = BackgroundI.getImage();
        Image Background_logo = Background_img.getScaledInstance(398, 639, Image.SCALE_SMOOTH);
        Background.setIcon(new ImageIcon(Background_logo));

        JLabel Me = new JLabel();
        Me.setBounds(65, 56, 333, 70);
        Me.setBackground(new Color(0, 0, 0, 0));
        Me.setOpaque(true);
        Me.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!isTalk_myself_Open) {
                    isTalk_myself_Open = true;
                    TalkPage_Me.TalkPage();
                }
            }

            public void mouseEntered(MouseEvent e) {
                Me.setBackground(new Color(0, 0, 0, 20));
                Friends_Panel.revalidate();
                Friends_Panel.repaint();
            }

            public void mouseExited(MouseEvent e) {
                Me.setBackground(new Color(0, 0, 0, 0));
                Friends_Panel.revalidate();
                Friends_Panel.repaint();
            }
        });

        JLabel Sim = new JLabel();
        Sim.setBounds(65, 260, 333, 60);
        Sim.setBackground(new Color(0, 0, 0, 0));
        Sim.setOpaque(true);
        Sim.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!isTalk_Do_Open) {
                    isTalk_Do_Open = true;
                    try {
                        TalkPage_Do.TalkPage();}
                    catch (IOException er){
                        er.printStackTrace();
                    }
                }
            }

            public void mouseEntered(MouseEvent e) {
                Sim.setBackground(new Color(0, 0, 0, 20));
                Friends_Panel.revalidate();
                Friends_Panel.repaint();
            }

            public void mouseExited(MouseEvent e) {
                Sim.setBackground(new Color(0, 0, 0, 0));
                Friends_Panel.revalidate();
                Friends_Panel.repaint();
            }
        });

        JLabel My_Profile = new JLabel();
        My_Profile.setBounds(85, 63, 56, 56);
        My_Profile.setOpaque(false);
        My_Profile.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                My_Profile_Frame();
            }
        });

        Friends_Panel.add(Sim);
        Friends_Panel.add(Me);
        Friends_Panel.add(My_Profile, 0);

        Friends_Panel.add(Background);
    }
    private static void Chatting_Page(JPanel Chatting_Panel){

        JLabel Background = new JLabel();
        Background.setOpaque(false);
        Background.setBounds(0, 0, 398, 639);
        ImageIcon BackgroundI = new ImageIcon("image/After_Login_img/Sim/Home/Home_F.PNG");
        Image Background_img = BackgroundI.getImage();
        Image Background_logo = Background_img.getScaledInstance(398, 639, Image.SCALE_SMOOTH);
        Background.setIcon(new ImageIcon(Background_logo));

        JLabel Me = new JLabel();
        Me.setBounds(65, 125, 333, 60);
        Me.setBackground(new Color(0, 0, 0, 0));
        Me.setOpaque(true);
        Me.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!isTalk_myself_Open) {
                    isTalk_myself_Open = true;
                    TalkPage_Me.TalkPage();
                }
            }

            public void mouseEntered(MouseEvent e) {
                Me.setBackground(new Color(0, 0, 0, 20));
                Chatting_Panel.revalidate();
                Chatting_Panel.repaint();
            }

            public void mouseExited(MouseEvent e) {
                Me.setBackground(new Color(0, 0, 0, 0));
                Chatting_Panel.revalidate();
                Chatting_Panel.repaint();
            }
        });

        JLabel Sim = new JLabel();
        Sim.setBounds(65,65,333,60);
        Sim.setBackground(new Color(0,0,0,0));
        Sim.setOpaque(true);
        Sim.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e){
                if (!isTalk_Do_Open) {
                    isTalk_Do_Open = true;
                    try {
                        TalkPage_Do.TalkPage();
                    }
                    catch (IOException er){
                        er.printStackTrace();
                    }
                }
            }
            public void mouseEntered(MouseEvent e){
                Sim.setBackground(new Color(0,0,0,20));
                Chatting_Panel.revalidate();
                Chatting_Panel.repaint();
            }
            public void mouseExited(MouseEvent e){
                Sim.setBackground(new Color(0,0,0,0));
                Chatting_Panel.revalidate();
                Chatting_Panel.repaint();
            }
        });

        Chatting_Panel.add(Sim);
        Chatting_Panel.add(Me);
        Chatting_Panel.add(Background);
    }

    public static void My_Profile_Frame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Point location = frame_Home.getLocation();

        JFrame frame_my_profile = new JFrame("");
        frame_my_profile.setSize(313, 600);
        frame_my_profile.setLocation(500, 100);
        frame_my_profile.setLocation(location.x - 226, location.y - 80);
        frame_my_profile.setUndecorated(true);
        frame_my_profile.setBackground(new Color(0, 0, 0, 0));
        frame_my_profile.setResizable(false);

        JPanel profile_panel = new JPanel();
        profile_panel.setLayout(null);
        profile_panel.setOpaque(false);
        profile_panel.setBounds(0, 0, 312, 600);

        JLabel MyProfile_B = new JLabel();
        MyProfile_B.setOpaque(false);
        MyProfile_B.setBounds(0, 0, 312, 600);
        ImageIcon MyProfile_BI = new ImageIcon("image/After_Login_img/Sim/Home/MyProfile/MyProfile.png");
        Image MyProfile_Bimg = MyProfile_BI.getImage();
        Image MyProfile_B_logo = MyProfile_Bimg.getScaledInstance(312, 600, Image.SCALE_SMOOTH);
        MyProfile_B.setIcon(new ImageIcon(MyProfile_B_logo));

        frame_my_profile.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                frame_my_profile.dispose();// 창 닫기
            }
        });

        JLabel TalkMyself = new JLabel();
        TalkMyself.setOpaque(false);
        TalkMyself.setBounds(73, 519, 56, 48);
        TalkMyself.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                frame_my_profile.dispose();// 창 닫기
                if (!isTalk_myself_Open) {
                    isTalk_myself_Open = true;
                    TalkPage_Me.TalkPage();
                }
            }
        });

        // ESC 키 설정
        profile_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "closeWindow");
        profile_panel.getActionMap().put("closeWindow", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_my_profile.dispose();
            }
        });

        profile_panel.add(TalkMyself);
        profile_panel.add(MyProfile_B);

        frame_my_profile.add(profile_panel);
        frame_my_profile.setVisible(true);
    }

    // Setter 메서드
    public static void setTalk_myself_Open(boolean home_PageOpen) {
        isTalk_myself_Open = home_PageOpen;
    }
}