import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JFrame
{

    private Board theBoard;
    private JLabel playLabel;
    //private JButton enterButton;
    private JLabel enterLabel;
    private Timer timer;
    private JLabel settingLabel;

    public Menu(Board theBoard) {
        this.theBoard = theBoard;
        theBoard.setVisible(false);

        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.BLACK);

        // add play button
        addPlayButton();

        addSettingButton();

        // add the title image
        addTitleImage();

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // function to add stars
        addStars(g);
    }

    private void addStars(Graphics g) {
        // set the number and the size of the star
        int numStars = 100;
        int MaxStarSize = 4;

        for (int i = 0; i < numStars; i++) {
            // the random position
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            int size = (int) (Math.random() * MaxStarSize) + 1;

            g.setColor(Color.WHITE);

            // draw the star(dot)
            g.fillOval(x, y, size, size);
        }
    }

    private void addPlayButton() {
        playLabel = new JLabel("Play");
        playLabel.setForeground(Color.WHITE);
        playLabel.setFont(new Font("Algerian", Font.BOLD, 40));
        playLabel.setBounds(400, 400, 200, 80);
        playLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // add the mouse hover event
        playLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e) {
                // when the mouse hover, make the size
                startAnimation(playLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // when the mouse exit the range reset the front
                playLabel.setFont(new Font("Algerian", Font.BOLD, 40));
                stopAnimation();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                //into login screen
                showLoginScreen();
                dispose();
            }
        });

        add(playLabel);
    }

    private void addSettingButton() {
        settingLabel = new JLabel("Setting");
        settingLabel.setForeground(Color.WHITE);
        settingLabel.setFont(new Font("Algerian", Font.BOLD, 40));
        settingLabel.setBounds(380, 480, 250, 80);
        settingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        settingLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e) {
                startAnimation(settingLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                settingLabel.setFont(new Font("Algerian", Font.BOLD, 40));
                stopAnimation();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                //into setting screen
                showSettingScreen();
            }
        });

        add(settingLabel);
    }

    private void startAnimation(JLabel label) {
        // if the animation started then stop the front one
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        int targetSize = 60;
        int initSize = label.getFont().getSize();

        //each step
        int step = 2;
        if (targetSize < initSize) {
            step -= step;
        }

        int finalStep = step;
        timer = new Timer(10, new ActionListener()
        {
            int currentSize = initSize;

            @Override
            public void actionPerformed(ActionEvent e) {
                currentSize += finalStep;
                if ((finalStep > 0 && currentSize >= targetSize) || (finalStep < 0 && currentSize <= targetSize)) {
                    // after the animation finished
                    label.setFont(new Font("Algerian", Font.BOLD, targetSize));
                    timer.stop();
                } else {
                    // update the size of the front
                    label.setFont(new Font("Algerian", Font.BOLD, currentSize));
                }
            }
        });

        timer.start();
    }

    private void stopAnimation() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    private void addTitleImage() {
        // shoot the asteroids
        ImageIcon titleIcon = new ImageIcon("src/title.png");

        JLabel titleLabel = new JLabel(titleIcon);
        titleLabel.setBounds(220, 100, 560, 356);
        add(titleLabel);
    }

    public void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(1000, 800);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(null);
        loginFrame.getContentPane().setBackground(Color.BLACK);

        JLabel starsLabel = new JLabel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                addStars(g);
            }
        };
        starsLabel.setBounds(0, 0, 1000, 800);
        loginFrame.add(starsLabel);

        // 上半部分，Username输入框
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Algerian", Font.BOLD, 20));
        usernameLabel.setBounds(400, 300, 200, 20);
        loginFrame.add(usernameLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(400, 320, 200, 30);
        loginFrame.add(usernameField);

        //password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Algerian", Font.BOLD, 20));
        passwordLabel.setBounds(400, 370, 200, 20);
        loginFrame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(400, 390, 200, 30);
        loginFrame.add(passwordField);

        //enter
        enterLabel = new JLabel("Enter");
        enterLabel.setForeground(Color.WHITE);
        enterLabel.setFont(new Font("Algerian", Font.BOLD, 40));
        enterLabel.setBounds(400, 450, 200, 200);
        enterLabel.setHorizontalAlignment(SwingConstants.CENTER);

        enterLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e) {
                startAnimation(enterLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                enterLabel.setFont(new Font("Algerian", Font.BOLD, 40));
                stopAnimation();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                theBoard.setVisible(true);
                loginFrame.dispose(); // 登录成功后关闭登录窗口
            }
        });

        loginFrame.add(enterLabel);
        loginFrame.setVisible(true);
    }

    private void showSettingScreen() {
        this.setVisible(false);

        JFrame settingFrame = new JFrame("Setting");
        settingFrame.setSize(1000, 800);
        settingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingFrame.setLocationRelativeTo(null);
        settingFrame.setLayout(null);
        settingFrame.getContentPane().setBackground(Color.BLACK);

        JLabel starsLabel = new JLabel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                addStars(g);
            }
        };
        starsLabel.setBounds(0, 0, 1000, 800);
        settingFrame.add(starsLabel);

        // 图标和图片
        ImageIcon speakerIcon = new ImageIcon("src/speaker.png");
        ImageIcon muteIcon = new ImageIcon("src/mute.png");
        JLabel speakerLabel = new JLabel(speakerIcon);
        speakerLabel.setBounds(400, 200, 200, 200);

        speakerLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e) {
                //startAnimation(speakerLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //stopAnimation();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (Board.getMuted()) {
                    speakerLabel.setIcon(speakerIcon);
                    Board.setMuted(false);
                    System.out.println("not muted");
                } else {
                    speakerLabel.setIcon(muteIcon);
                    Board.setMuted(true);
                    System.out.println("muted");
                }
            }
        });

        settingFrame.add(speakerLabel);
        if (!Board.getMuted())
            speakerLabel.setIcon(speakerIcon);
        else
            speakerLabel.setIcon(muteIcon);

        // Exit
        JLabel exitLabel = new JLabel("Exit");
        exitLabel.setForeground(Color.WHITE);
        exitLabel.setFont(new Font("Algerian", Font.BOLD, 40));
        exitLabel.setBounds(400, 500, 200, 80);
        exitLabel.setHorizontalAlignment(SwingConstants.CENTER);

        exitLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseEntered(MouseEvent e) {
                startAnimation(exitLabel);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                stopAnimation();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(true);
                settingFrame.dispose();
            }
        });

        settingFrame.add(exitLabel);

        settingFrame.setVisible(true);
    }
}