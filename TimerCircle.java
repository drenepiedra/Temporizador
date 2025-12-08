import javax.swing.*;
import java.awt.*;

public class TimerCircle {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TimerCircleFrame frame = new TimerCircleFrame();
            frame.setVisible(true);
        });
    }
}

class TimerCircleFrame extends JFrame {
    private final TimerPanel panel;
    private final JTextField inputSeconds;
    private final JButton startButton;
    private final JButton pauseButton;
    private final JButton resetButton;

    public TimerCircleFrame() {
        setTitle("Temporizador de concentración");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 320));
        setLocationRelativeTo(null);

        // Default: ventana siempre encima activada. Añadimos un checkbox para controlarlo.
        setAlwaysOnTop(true);

        panel = new TimerPanel();
        add(panel, BorderLayout.CENTER);

        // Controls responsive usando GridBagLayout
        JPanel controls = new JPanel(new GridBagLayout());
        controls.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; c.weightx = 0;
        controls.add(new JLabel("Minutos:"), c);

        inputSeconds = new JTextField("25");
        c.gridx = 1; c.gridy = 0; c.weightx = 1.0;
        controls.add(inputSeconds, c);

        JCheckBox alwaysOnTopCheckbox = new JCheckBox("Siempre encima", true);
        alwaysOnTopCheckbox.addActionListener(e -> setAlwaysOnTop(alwaysOnTopCheckbox.isSelected()));
        c.gridx = 0; c.gridy = 1; c.gridwidth = 2; c.weightx = 0;
        controls.add(alwaysOnTopCheckbox, c);

        // Buttons row
        JPanel btns = new JPanel(new GridLayout(1, 3, 6, 0));
        startButton = new JButton("Iniciar");
        pauseButton = new JButton("Pausar");
        resetButton = new JButton("Reiniciar");
        btns.add(startButton);
        btns.add(pauseButton);
        btns.add(resetButton);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 2; c.weightx = 0;
        controls.add(btns, c);

        add(controls, BorderLayout.SOUTH);

        // Action listeners
        startButton.addActionListener(e -> {
            try {
                int m = Integer.parseInt(inputSeconds.getText().trim());
                if (m <= 0) throw new NumberFormatException();
                panel.startTimer(m * 60L * 1000L);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Introduce minutos enteros (ej. 25 para 25 minutos).", "Entrada inválida", JOptionPane.ERROR_MESSAGE);
            }
        });

        pauseButton.addActionListener(e -> panel.togglePause());
        resetButton.addActionListener(e -> panel.reset());

        pack();
    }
}

class TimerPanel extends JPanel {
    private long totalMillis = 25L * 60L * 1000L; // default 25 min
    private long remainingMillis = totalMillis;
    private final javax.swing.Timer swingTimer;
    private boolean running = false;
    private boolean paused = false;

    public TimerPanel() {
        setPreferredSize(new Dimension(400, 400));
        swingTimer = new javax.swing.Timer(100, e -> {
            if (running && !paused) {
                remainingMillis -= 100;
                if (remainingMillis <= 0) {
                    remainingMillis = 0;
                    running = false;
                    ((javax.swing.Timer) e.getSource()).stop();
                    Toolkit.getDefaultToolkit().beep();
                }
                repaint();
            }
        });
    }

    public void startTimer(long totalMillis) {
        this.totalMillis = totalMillis;
        this.remainingMillis = totalMillis;
        this.running = true;
        this.paused = false;
        if (!swingTimer.isRunning()) swingTimer.start();
        repaint();
    }

    public void togglePause() {
        if (!running) return;
        paused = !paused;
        repaint();
    }

    public void reset() {
        running = false;
        paused = false;
        remainingMillis = totalMillis;
        if (swingTimer.isRunning()) swingTimer.stop();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(getWidth(), getHeight()) - 40;
        int x = (getWidth() - size) / 2;
        int y = (getHeight() - size) / 2;

        // Background circle (unfilled)
        g2.setColor(new Color(240, 240, 240));
        g2.fillOval(x, y, size, size);

        // Red arc representing remaining time
        double fraction = (totalMillis <= 0) ? 0.0 : (double) remainingMillis / (double) totalMillis;
        int angle = (int) Math.round(fraction * 360.0);
        g2.setColor(new Color(200, 20, 20));
        g2.fillArc(x, y, size, size, 90, -angle); // start at 12 o'clock, clockwise

        // Optional inner circle to create a ring effect (looks nicer)
        int padding = Math.max(8, size / 6);
        g2.setColor(getBackground());
        g2.fillOval(x + padding, y + padding, size - padding * 2, size - padding * 2);

        // Time text in center
        g2.setColor(Color.BLACK);
        String timeText = formatTime(remainingMillis);
        float fontSize = Math.max(18f, size / 8f);
        Font font = getFont().deriveFont(Font.BOLD, fontSize);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        int tx = getWidth() / 2 - fm.stringWidth(timeText) / 2;
        int ty = getHeight() / 2 + fm.getAscent() / 2 - fm.getDescent() / 2;
        g2.drawString(timeText, tx, ty);

        // Paused status
        if (paused) {
            g2.setFont(getFont().deriveFont(Font.PLAIN, 14f));
            String p = "Pausado";
            FontMetrics fm2 = g2.getFontMetrics();
            g2.drawString(p, getWidth() / 2 - fm2.stringWidth(p) / 2, ty + fm2.getHeight() + 6);
        }

        g2.dispose();
    }

    private String formatTime(long ms) {
        long totalSec = Math.max(0L, ms / 1000L);
        long hours = totalSec / 3600L;
        long rem = totalSec % 3600L;
        long min = rem / 60L;
        long sec = rem % 60L;
        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, min, sec);
        } else {
            return String.format("%02d:%02d", min, sec);
        }
    }
}
