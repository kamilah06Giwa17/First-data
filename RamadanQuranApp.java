// package GIWA_IFEDOLAPO_EU230102-3709_JAVA_PROJECT


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class RamadanQuranApp extends JFrame {
    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);
    Map<Integer, Boolean> fastingProgress = new HashMap<>();
    String userName = "";
    JLabel greetLabel;

    String[] quranVerses = {
        "It is the fire of Allah (eternally) fueled (104:6)",
        "Guide us to a straight path (1:6)",
        "Or enjoin righteousness (96:12)",
        "For you is your religion and for me is my religion (109:6)",
        "And have you shown him the two ways (90:10)",
        "And We raised him to a high station (19:57)",
        "So your Lord poured upon them a scourge of punishment (89:13)",
        "Does he not know that Allah sees? (96:14)",
        "And his mother and his father (80:35)",
        "And those who are in their testimonies upright (70:33)",
        "And indeed, the wicked will be in Hellfire (82:14)",
        "Indeed I am to you a trustworthy messenger (26:143)",
        "Say, \"I seek refuge in the Lord of daybreak\" (113:1)",
        "And remember the name of your Lord and devote yourself to Him with [complete] devotion. (73:8)"
    };

    public RamadanQuranApp() {
        setTitle("Ramadan & Quran App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color green = new Color(0, 128, 0);
        Color white = Color.WHITE;

        
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(white);

        JLabel namePrompt = new JLabel("Enter your name:");
        namePrompt.setFont(new Font("Serif", Font.BOLD, 24));
        namePrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        namePrompt.setForeground(green);

        JTextField nameField = new JTextField(20);
        nameField.setMaximumSize(new Dimension(300, 40));
        nameField.setFont(new Font("Serif", Font.PLAIN, 20));

        JButton continueButton = new JButton("Continue");
        continueButton.setBackground(green);
        continueButton.setForeground(white);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setFont(new Font("Serif", Font.BOLD, 18));
        continueButton.addActionListener(e -> {
            userName = nameField.getText().trim();
            greetLabel.setText("Salam " + userName + ", what would you like to do?");
            cardLayout.show(mainPanel, "Home");
        });

        welcomePanel.add(Box.createVerticalStrut(100));
        welcomePanel.add(namePrompt);
        welcomePanel.add(Box.createVerticalStrut(80));
        welcomePanel.add(nameField);
        welcomePanel.add(Box.createVerticalStrut(80));
        welcomePanel.add(continueButton);

        
        JPanel homePanel = new JPanel();
        homePanel.setBackground(white);
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
        greetLabel = new JLabel("", SwingConstants.CENTER);
        greetLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        greetLabel.setFont(new Font("Serif", Font.BOLD, 48));
        greetLabel.setForeground(green);

        JButton ramadanButton = new JButton("Track Ramadan");
        JButton verseButton = new JButton("Generate Quran Verse");
        for (JButton btn : new JButton[]{ramadanButton, verseButton}) {
            btn.setBackground(green);
            btn.setForeground(white);
            btn.setFont(new Font("Serif", Font.BOLD, 18));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setFocusPainted(false);
            btn.setMaximumSize(new Dimension(300, 40));
        }

        homePanel.add(Box.createVerticalStrut(80));
        homePanel.add(greetLabel);
        homePanel.add(Box.createVerticalStrut(100));
        homePanel.add(ramadanButton);
        homePanel.add(Box.createVerticalStrut(80));
        homePanel.add(verseButton);

        
        JPanel trackerPanel = new JPanel(new BorderLayout());
        trackerPanel.setBackground(white);

        JLabel trackerTitle = new JLabel("Ramadan Fasting Tracker", SwingConstants.CENTER);
        trackerTitle.setFont(new Font("Serif", Font.BOLD, 24));
        trackerTitle.setForeground(green);
        trackerPanel.add(trackerTitle, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel(new GridLayout(6, 5, 10, 10));
        gridPanel.setBackground(white);

        Map<Integer, JButton> dayButtons = new HashMap<>();

        for (int i = 1; i <= 30; i++) {
            JButton dayButton = new JButton("Day " + i);
            styleDayButton(dayButton, false, green, white);
            final int day = i;

            fastingProgress.put(day, false);
            dayButton.addActionListener(e -> {
                boolean currentlyFasted = fastingProgress.get(day);
                fastingProgress.put(day, !currentlyFasted);
                styleDayButton(dayButton, !currentlyFasted, green, white);
            });

            dayButtons.put(day, dayButton);
            gridPanel.add(dayButton);
        }

        trackerPanel.add(gridPanel, BorderLayout.CENTER);

        JButton backFromTracker = new JButton("Back");
        backFromTracker.setBackground(green);
        backFromTracker.setForeground(white);
        backFromTracker.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        trackerPanel.add(backFromTracker, BorderLayout.SOUTH);

      
        loadProgressFromFile(dayButtons);

        
        JPanel versePanel = new JPanel(new BorderLayout());
        versePanel.setBackground(white);

        JLabel verseTitle = new JLabel("Quran Verse Generator", SwingConstants.CENTER);
        verseTitle.setFont(new Font("Serif", Font.BOLD, 24));
        verseTitle.setForeground(green);
        versePanel.add(verseTitle, BorderLayout.NORTH);

        JTextArea verseBox = new JTextArea();
        verseBox.setFont(new Font("Serif", Font.PLAIN, 48));
        verseBox.setWrapStyleWord(true);
        verseBox.setLineWrap(true);
        verseBox.setEditable(false);
        verseBox.setBackground(new Color(240, 255, 240));
        verseBox.setForeground(Color.BLACK);
        verseBox.setMargin(new Insets(20, 20, 20, 20));
        verseBox.setBorder(BorderFactory.createLineBorder(green, 2));

        JScrollPane scrollPane = new JScrollPane(verseBox);
        versePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(white);

        JButton generateVerse = new JButton("Generate Verse");
        generateVerse.setBackground(green);
        generateVerse.setForeground(white);
        generateVerse.setFont(new Font("Serif", Font.BOLD, 18));
        generateVerse.addActionListener(e -> {
            Random rand = new Random();
            verseBox.setText(quranVerses[rand.nextInt(quranVerses.length)]);
        });

        JButton backFromVerse = new JButton("Back");
        backFromVerse.setBackground(green);
        backFromVerse.setForeground(white);
        backFromVerse.addActionListener(e -> cardLayout.show(mainPanel, "Home"));

        bottomPanel.add(generateVerse);
        bottomPanel.add(backFromVerse);
        versePanel.add(bottomPanel, BorderLayout.SOUTH);

       
        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(homePanel, "Home");
        mainPanel.add(trackerPanel, "Tracker");
        mainPanel.add(versePanel, "Verse");

        ramadanButton.addActionListener(e -> cardLayout.show(mainPanel, "Tracker"));
        verseButton.addActionListener(e -> cardLayout.show(mainPanel, "Verse"));

        add(mainPanel);
        cardLayout.show(mainPanel, "Welcome");
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                saveProgressToFile();
            }
        });
    }

    private void styleDayButton(JButton button, boolean fasted, Color green, Color white) {
        if (fasted) {
            button.setBackground(green);
            button.setForeground(white);
        } else {
            button.setBackground(white);
            button.setForeground(green);
        }
        button.setFont(new Font("Serif", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(green, 2));
        button.setOpaque(true);
    }

    private void saveProgressToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("progress.txt"))) {
            writer.println("name=" + userName);
            for (Map.Entry<Integer, Boolean> entry : fastingProgress.entrySet()) {
                writer.println(entry.getKey() + "=" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadProgressFromFile(Map<Integer, JButton> dayButtons) {
        File file = new File("progress.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("name=")) {
                    userName = line.substring(5);
                    if (greetLabel != null) {
                        greetLabel.setText("Salam " + userName + ", what would you like to do?");
                    }
                } else if (line.contains("=")) {
                    String[] parts = line.split("=");
                    int day = Integer.parseInt(parts[0]);
                    boolean fasted = Boolean.parseBoolean(parts[1]);
                    fastingProgress.put(day, fasted);

                    if (dayButtons != null && dayButtons.containsKey(day    )) {
                        styleDayButton(dayButtons.get(day), fasted, new Color(0, 128, 0), Color.WHITE);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RamadanQuranApp::new);
    }
}
