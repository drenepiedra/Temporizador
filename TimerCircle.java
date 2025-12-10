import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TimerCircle {
    public static void main(String[] args) {
        // Configurar tema oscuro para diálogos
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.put("OptionPane.background", new Color(0x15, 0x00, 0x3B));
            UIManager.put("Panel.background", new Color(0x15, 0x00, 0x3B));
            UIManager.put("OptionPane.messageForeground", Color.WHITE);
            UIManager.put("Button.background", new Color(0x56, 0x00, 0xA7));
            UIManager.put("Button.foreground", Color.WHITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
    private final JCheckBox shutdownCheckbox;

    public TimerCircleFrame() {
        setTitle("Temporizador de concentración");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(300, 380));
        setLocationRelativeTo(null);

        setAlwaysOnTop(true);

        panel = new TimerPanel();
        add(panel, BorderLayout.CENTER);

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

        shutdownCheckbox = new JCheckBox("Apagar PC al finalizar");
        c.gridx = 0; c.gridy = 2; c.gridwidth = 2; c.weightx = 0;
        controls.add(shutdownCheckbox, c);

        JPanel btns = new JPanel(new GridLayout(1, 3, 6, 0));
        startButton = new JButton("Iniciar");
        pauseButton = new JButton("Pausar");
        resetButton = new JButton("Reiniciar");
        btns.add(startButton);
        btns.add(pauseButton);
        btns.add(resetButton);

        c.gridx = 0; c.gridy = 3; c.gridwidth = 2; c.weightx = 0;
        controls.add(btns, c);

        // Configurar colores de la interfaz
        Color backgroundColor = new Color(0x15, 0x00, 0x3B);
        Color buttonColor = new Color(0x56, 0x00, 0xA7);
        Color buttonHoverColor = new Color(0x46, 0x00, 0x87);

        // Fondo general
        getContentPane().setBackground(backgroundColor);

        // Fondo de controles
        controls.setBackground(backgroundColor);
        btns.setBackground(backgroundColor);

        // Personalizar botones
        customizeButton(startButton, buttonColor, buttonHoverColor);
        customizeButton(pauseButton, buttonColor, buttonHoverColor);
        customizeButton(resetButton, buttonColor, buttonHoverColor);

        // Configurar colores de otros componentes
        configureComponentColors(controls);

        // Configurar panel del timer
        panel.setBackground(backgroundColor);

        add(controls, BorderLayout.SOUTH);

        startButton.addActionListener(e -> {
            try {
                int m = Integer.parseInt(inputSeconds.getText().trim());
                if (m <= 0) throw new NumberFormatException();
                
                if (shutdownCheckbox.isSelected()) {
                    int confirm = JOptionPane.showConfirmDialog(this,
                        "⚠️ ADVERTENCIA ⚠️\n\n" +
                        "La PC se APAGARÁ AUTOMÁTICAMENTE en " + m + " minutos.\n" +
                        "No habrá más advertencias.\n" +
                        "Guarda todo tu trabajo antes de iniciar.\n\n" +
                        "¿Continuar?",
                        "Confirmar apagado automático",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                    
                    if (confirm == JOptionPane.YES_OPTION) {
                        panel.startTimer(m * 60L * 1000L, true);
                    } else {
                        shutdownCheckbox.setSelected(false);
                        panel.startTimer(m * 60L * 1000L, false);
                    }
                } else {
                    panel.startTimer(m * 60L * 1000L, false);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Introduce minutos enteros (ej. 25 para 25 minutos).", 
                    "Entrada inválida", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        pauseButton.addActionListener(e -> panel.togglePause());
        resetButton.addActionListener(e -> {
            panel.reset();
            shutdownCheckbox.setSelected(false);
        });

        pack();
    }

    private void customizeButton(JButton button, Color bgColor, Color hoverColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(15, bgColor));
        
        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

    private void configureComponentColors(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JLabel) {
                comp.setForeground(Color.WHITE);
                comp.setFont(comp.getFont().deriveFont(Font.BOLD));
            } else if (comp instanceof JCheckBox) {
                JCheckBox cb = (JCheckBox) comp;
                cb.setForeground(Color.WHITE);
                cb.setBackground(new Color(0x15, 0x00, 0x3B));
                cb.setOpaque(false);
                cb.setFocusPainted(false);
            } else if (comp instanceof JTextField) {
                comp.setBackground(new Color(0x25, 0x10, 0x5B));
                comp.setForeground(Color.WHITE);
                ((JTextField)comp).setCaretColor(Color.WHITE);
                ((JTextField)comp).setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0x56, 0x00, 0xA7), 1),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
                ));
            } else if (comp instanceof Container) {
                configureComponentColors((Container)comp);
            }
        }
    }

    // Clase interna para bordes redondeados
    class RoundedBorder implements javax.swing.border.Border {
        private int radius;
        private Color color;
        
        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }
        
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }
        
        public boolean isBorderOpaque() {
            return true;
        }
        
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }
}

class TimerPanel extends JPanel {
    private long totalMillis = 25L * 60L * 1000L;
    private long remainingMillis = totalMillis;
    private final javax.swing.Timer swingTimer;
    private boolean running = false;
    private boolean paused = false;
    private boolean shutdownOnFinish = false;

    public TimerPanel() {
        setPreferredSize(new Dimension(300, 180));
        setBackground(new Color(0x15, 0x00, 0x3B));
        
        swingTimer = new javax.swing.Timer(100, e -> {
            if (running && !paused) {
                remainingMillis -= 100;
                if (remainingMillis <= 0) {
                    remainingMillis = 0;
                    running = false;
                
                    Toolkit.getDefaultToolkit().beep();
                    
                    // Apagar inmediatamente sin mostrar nada
                    if (shutdownOnFinish) {
                        shutdownComputerSilently();
                    } else {
                        // Solo beep si no va a apagar
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
                repaint();
            }
        });
    }

    public void startTimer(long totalMillis, boolean shutdownOnFinish) {
        this.totalMillis = totalMillis;
        this.remainingMillis = totalMillis;
        this.running = true;
        this.paused = false;
        this.shutdownOnFinish = shutdownOnFinish;
        
        if (!swingTimer.isRunning()) {
            swingTimer.start();
        }
        repaint();
    }

    private void shutdownComputerSilently() {
        String os = System.getProperty("os.name").toLowerCase();
        String shutdownCommand;
        
        if (os.contains("win")) {
            // Windows - apagar inmediatamente sin diálogos
            shutdownCommand = "shutdown -s -f -t 0";
        } else if (os.contains("mac") || os.contains("darwin")) {
            // macOS - apagar inmediatamente
            shutdownCommand = "sudo shutdown -h now";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            // Linux/Unix - apagar inmediatamente
            shutdownCommand = "sudo shutdown -h now";
        } else {
            // Si hay error, no mostrar nada, solo terminar
            System.exit(0);
            return;
        }
        
        try {
            if (os.contains("win")) {
                // Para Windows, ejecutar directamente
                Runtime.getRuntime().exec(shutdownCommand);
            } else {
                // Para sistemas Unix/Linux/macOS
                String[] cmd = {"/bin/bash", "-c", shutdownCommand};
                Runtime.getRuntime().exec(cmd);
            }
            
            // Cerrar la aplicación inmediatamente sin mostrar nada
            System.exit(0);
                
        } catch (IOException ex) {
            // Si hay error, simplemente cerrar la aplicación sin mostrar nada
            System.exit(0);
        }
    }

    public void togglePause() {
        if (!running) return;
        paused = !paused;
        repaint();
    }

    public void reset() {
        running = false;
        paused = false;
        shutdownOnFinish = false;
        remainingMillis = totalMillis;
        if (swingTimer.isRunning()) {
            swingTimer.stop();
        }
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

        // Fondo del círculo
        g2.setColor(new Color(0x25, 0x10, 0x5B));
        g2.fillOval(x, y, size, size);

        // Arco del temporizador
        double fraction = (totalMillis <= 0) ? 0.0 : (double) remainingMillis / (double) totalMillis;
        int angle = (int) Math.round(fraction * 360.0);
        
        if (shutdownOnFinish && running) {
            g2.setColor(new Color(0xFF, 0x00, 0x40)); // Rojo brillante para apagado
        } else {
            g2.setColor(new Color(0x13, 0x04, 0x4C)); // Morado oscuro #13044C para normal
        }
        
        g2.fillArc(x, y, size, size, 90, -angle);

        // Círculo interior
        int padding = Math.max(8, size / 6);
        g2.setColor(new Color(0x15, 0x00, 0x3B)); // Fondo #15003B
        g2.fillOval(x + padding, y + padding, size - padding * 2, size - padding * 2);

        // Texto del tiempo
        g2.setColor(Color.WHITE);
        String timeText = formatTime(remainingMillis);
        float fontSize = Math.max(18f, size / 8f);
        Font font = getFont().deriveFont(Font.BOLD, fontSize);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        int tx = getWidth() / 2 - fm.stringWidth(timeText) / 2;
        int ty = getHeight() / 2 + fm.getAscent() / 2 - fm.getDescent() / 2;
        g2.drawString(timeText, tx, ty);

        // Texto de pausado
        if (paused) {
            g2.setFont(getFont().deriveFont(Font.PLAIN, 14f));
            String p = "Pausado";
            FontMetrics fm2 = g2.getFontMetrics();
            g2.setColor(Color.WHITE);
            g2.drawString(p, getWidth() / 2 - fm2.stringWidth(p) / 2, ty + fm2.getHeight() + 6);
        }
        
        // Indicador de apagado automático
        if (shutdownOnFinish && running) {
            g2.setColor(new Color(0xFF, 0x60, 0x60));
            g2.setFont(getFont().deriveFont(Font.BOLD, 12f));
            String warningText = "⚠ APAGADO AUTOMÁTICO ACTIVADO ⚠";
            FontMetrics fm3 = g2.getFontMetrics();
            int textY = y + size + fm3.getHeight() + 2;
            
            // Fondo del texto de advertencia
            g2.setColor(new Color(0x25, 0x00, 0x4B, 200));
            int textWidth = fm3.stringWidth(warningText);
            g2.fillRect(getWidth() / 2 - textWidth / 2 - 5, textY - fm3.getAscent() + 3, 
                       textWidth + 10, fm3.getHeight());
            
            // Texto de advertencia
            g2.setColor(new Color(0xFF, 0x80, 0x80));
            g2.drawString(warningText, getWidth() / 2 - textWidth / 2, textY);
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