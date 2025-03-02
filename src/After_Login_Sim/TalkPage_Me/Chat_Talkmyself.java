package After_Login_Sim.TalkPage_Me;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static Home_Sim.Home_Page_Sim.Current_Chat;
import static Home_Sim.Home_Page_Sim.Current_Time;

public class Chat_Talkmyself {

    static JPanel chat_scroll = new JPanel();
    static int chat_cnt = 0;
    static int time_cnt = 0;
    static JLabel STR;
    static ArrayList<LocalTime> messageTimes = new ArrayList<LocalTime>();
    static ArrayList<String> messageMinutes = new ArrayList<String>();
    static boolean isTextFirst = false;

    // Chat 메서드 내에서 STR 초기화
    public static void Chat(String chat_str, JPanel Talk_Panel_me, String O_time) {
        Current_Chat = chat_str;

        chat_cnt++;
        time_cnt++;

        Talk_Panel_me.revalidate();
        Talk_Panel_me.repaint();

        // chat_scroll 패널 생성 및 설정
        chat_scroll.setBounds(0,85,379,430);
        chat_scroll.setOpaque(false);
        chat_scroll.setLayout(new BoxLayout(chat_scroll, BoxLayout.Y_AXIS));
        chat_scroll.revalidate();
        chat_scroll.repaint();

        // 스크롤 설정
        JScrollPane scrollPane = new JScrollPane(chat_scroll);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 85, 379, 430);
        scrollPane.setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setOpaque(false); // 스크롤바를 투명하게
        verticalBar.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(86, 97, 108);
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(86, 97, 108));

                int arc = Math.min(thumbBounds.width, thumbBounds.height); // 둥글기 설정
                int thumbWidth = 8; // 스크롤 핸들의 너비
                int x = thumbBounds.x + (thumbBounds.width - thumbWidth) / 2; // 가운데 정렬
                g2.fillRoundRect(x, thumbBounds.y, thumbWidth, thumbBounds.height, arc, arc);

                g2.dispose();
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 0)); // 트랙을 투명하게 설정
                g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                g2.dispose();
            }

            // 위 아래 화살표 버튼을 끄는 방법
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = new JButton(); // 기본 버튼을 대신 빈 버튼으로
                button.setPreferredSize(new Dimension(0, 0)); // 버튼 크기를 0으로 설정
                button.setEnabled(false); // 버튼 비활성화
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = new JButton(); // 기본 버튼을 대신 빈 버튼으로
                button.setPreferredSize(new Dimension(0, 0)); // 버튼 크기를 0으로 설정
                button.setEnabled(false); // 버튼 비활성화
                return button;
            }
        });


        JPanel messagePanel = new JPanel();
        messagePanel.setName("messagePanel"+time_cnt);
        messagePanel.setOpaque(false);
        messagePanel.setPreferredSize(new Dimension(379,31));
        messagePanel.setLayout(new FlowLayout(FlowLayout.RIGHT,2,7));

        JPanel gluedPanel = new JPanel();
        gluedPanel.setName("gluedPanel"+time_cnt);
        gluedPanel.setLayout(new BorderLayout());
        gluedPanel.add(messagePanel, BorderLayout.CENTER);
        gluedPanel.setMaximumSize(new Dimension(350, 31)); // 패널 크기 고정
        gluedPanel.setOpaque(false);

        // 시간 입력
        DateTimeFormatter originalFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter targetFormat = DateTimeFormatter.ofPattern("a h:mm");
        LocalTime localTime = LocalTime.parse(O_time, originalFormat);
        String time = localTime.format(targetFormat);
        JLabel time_label = new JLabel(time);
        time_label.setFont(new Font("Arial",Font.PLAIN,10));
        time_label.setForeground(Color.darkGray);
        time_label.setPreferredSize(new Dimension(time_label.getPreferredSize().width+8, time_label.getPreferredSize().height));

        Current_Time = targetFormat;

        LocalTime time_now = LocalTime.now(); // 현재 시간
        String minutes_time = time_now.format(DateTimeFormatter.ofPattern("HH:mm"));


        messageTimes.add(time_now);
        messageMinutes.add(minutes_time);
        messagePanel.add(time_label);
        time_label.setName("time_label"+time_cnt);

        if(!isTextFirst){
            isTextFirst = true;

        }
        else{
            if(messageMinutes.get(messageMinutes.size()-1).equals(messageMinutes.get(messageMinutes.size()-2))){
                if(messageTimes.get(messageMinutes.size()-1).isAfter(messageTimes.get(messageTimes.size()-2))){
                    JPanel G_Panel = findPanelByName(chat_scroll,"gluedPanel"+(time_cnt-1));
                    if(G_Panel != null){
                        JPanel M_Panel = findPanelByName(G_Panel, "messagePanel"+(time_cnt-1));
                        if (M_Panel != null) {
                            JLabel T_Label = (JLabel) findComponentByName(M_Panel, "time_label"+(time_cnt-1));
                            if (T_Label != null) {
                                T_Label.setText(" "); // 텍스트 변경
                            }
                        }
                    }
                }
            }
        }

        // 메시지 입력
        STR = new JLabel(chat_str);
        JLabel messageLabel = Make_Background_and_Chat(chat_str);
        messagePanel.add(messageLabel);
        chat_scroll.add(gluedPanel);

        scrollPane.getViewport().setViewPosition(new Point(0, 31*chat_cnt));

        Talk_Panel_me.add(scrollPane, 0);
        Talk_Panel_me.revalidate();
        Talk_Panel_me.repaint();

    }

    private static JLabel Make_Background_and_Chat(String chat_str) {

        JLabel Chat_K = new JLabel();
        Chat_K.setOpaque(false);
        Chat_K.setLayout(null); // null 레이아웃 유지
        Chat_K.setPreferredSize(new Dimension(STR.getPreferredSize().width + 25, 40)); // 적절한 크기 설정

        JLabel Yellow = new JLabel();
        Yellow.setOpaque(true);
        Yellow.setBackground(new Color(254, 225, 10));
        Yellow.setBounds(10, 7, STR.getPreferredSize().width, 18);

        STR = new JLabel(chat_str);
        STR.setBounds(10, 4, STR.getPreferredSize().width, 16);

        JLabel LEFT_UP = new JLabel();
        LEFT_UP.setOpaque(false);
        LEFT_UP.setBounds(0, 0, 10, 8);
        ImageIcon LEFT_UPI = new ImageIcon("image/TalkPage/Me/chat/left/leftup.png");
        Image LEFT_UP_img = LEFT_UPI.getImage();
        Image LEFT_UP_logo = LEFT_UP_img.getScaledInstance(10, 8, Image.SCALE_SMOOTH);
        LEFT_UP.setIcon(new ImageIcon(LEFT_UP_logo));

        JLabel LEFT_DOWN = new JLabel();
        LEFT_DOWN.setOpaque(false);
        LEFT_DOWN.setBounds(0, 16, 10, 8);
        ImageIcon LEFT_DOWNI = new ImageIcon("image/TalkPage/Me/chat/left/leftdown.png");
        Image LEFT_DOWN_img = LEFT_DOWNI.getImage();
        Image LEFT_DOWN_logo = LEFT_DOWN_img.getScaledInstance(10, 8, Image.SCALE_SMOOTH);
        LEFT_DOWN.setIcon(new ImageIcon(LEFT_DOWN_logo));

        JLabel RIGHT_DOWN = new JLabel();
        RIGHT_DOWN.setOpaque(false);
        RIGHT_DOWN.setBounds(10 + STR.getPreferredSize().width, 17, 16, 7);
        ImageIcon RIGHT_DOWNI = new ImageIcon("image/TalkPage/Me/chat/right/rightdown.png");
        Image RIGHT_DOWN_img = RIGHT_DOWNI.getImage();
        Image RIGHT_DOWN_logo = RIGHT_DOWN_img.getScaledInstance(16, 7, Image.SCALE_SMOOTH);
        RIGHT_DOWN.setIcon(new ImageIcon(RIGHT_DOWN_logo));

        ImageIcon LEFT_I = new ImageIcon("image/TalkPage/Me/chat/left/left.png");
        JLabel LEFT = new JLabel(LEFT_I);
        LEFT.setBounds(0, 8, 10, 10);

        JLabel RIGHT_UP = new JLabel();
        RIGHT_UP.setOpaque(false);
        RIGHT_UP.setBounds(10 + STR.getPreferredSize().width, 0, 16, 8);
        ImageIcon RIGHT_UPI = new ImageIcon("image/TalkPage/Me/chat/right/rightup1.png");
        Image RIGHT_UP_img = RIGHT_UPI.getImage();
        Image RIGHT_UP_logo = RIGHT_UP_img.getScaledInstance(16, 8, Image.SCALE_SMOOTH);
        RIGHT_UP.setIcon(new ImageIcon(RIGHT_UP_logo));

        JLabel RIGHT = new JLabel();
        RIGHT.setOpaque(false);
        ImageIcon RIGHTI = new ImageIcon("image/TalkPage/Me/chat/right/right1.png");
        Image RIGHT_img = RIGHTI.getImage();
        Image RIGHT_logo = RIGHT_img.getScaledInstance(16, 13, Image.SCALE_SMOOTH);
        RIGHT.setIcon(new ImageIcon(RIGHT_logo));
        RIGHT.setBounds(10 + STR.getPreferredSize().width, 8, 16, 13);

        JLabel UP = new JLabel();
        UP.setOpaque(false);
        ImageIcon UPI = new ImageIcon("image/TalkPage/Me/chat/up_down/up.png");
        Image UP_img = UPI.getImage();
        Image UP_logo = UP_img.getScaledInstance(STR.getPreferredSize().width, 13, Image.SCALE_SMOOTH);
        UP.setIcon(new ImageIcon(UP_logo));
        UP.setBounds(10, 0, STR.getPreferredSize().width, 13);

        // 구성 요소 추가
        Chat_K.add(STR);
        Chat_K.add(UP);
        Chat_K.add(LEFT);
        Chat_K.add(RIGHT);
        Chat_K.add(RIGHT_DOWN);
        Chat_K.add(RIGHT_UP);
        Chat_K.add(LEFT_DOWN);
        Chat_K.add(LEFT_UP);
        Chat_K.add(Yellow);

        // Layout 확인용 Border
        Chat_K.setBorder(BorderFactory.createEmptyBorder());

        return Chat_K;
    }
    // 특정 이름을 가진 컴포넌트를 찾는 메서드
    private static JComponent findComponentByName(JPanel panel, String name) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JComponent) {
                JComponent jComp = (JComponent) comp;
                if (name.equals(jComp.getName())) {
                    return jComp;
                }
            }
        }
        return null;
    }
    // 특정 이름을 가진 패널을 찾는 메서드
    private static JPanel findPanelByName(JPanel panel, String name) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel jPanel = (JPanel) comp;
                if (name.equals(jPanel.getName())) {
                    return jPanel;
                }
            }
        }
        return null;
    }
}
