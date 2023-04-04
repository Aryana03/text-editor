import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class textEditor extends JFrame implements ActionListener, appOptions {
    JTextArea text_area = new JTextArea();
    JScrollPane scroll_pane = new JScrollPane(this.text_area);
    JSpinner font_size_spinner = new JSpinner();
    JButton font_color_button = new JButton("Color");
    JLabel size_label = new JLabel("Size: ");
    JLabel font_label = new JLabel("Font: ");
    JComboBox font_box;
    JMenu menu = new JMenu("File");
    JMenuBar menuBar = new JMenuBar();
    JMenuItem openItem = new JMenuItem("Open");
    JMenuItem saveItem = new JMenuItem("Save");
    JMenuItem exitItem = new JMenuItem("Exit");

    public textEditor() {
        this.setDefaultCloseOperation(3);
        this.setTitle("Text Editor");
        this.setSize(420, 420);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);

        text_area.setLineWrap(true);
        text_area.setWrapStyleWord(true);
        text_area.setFont(new Font("Century", 0, 16));

        scroll_pane.setPreferredSize(new Dimension(370, 370));
        scroll_pane.setBackground(Color.black);
        scroll_pane.setVerticalScrollBarPolicy(22);

        font_size_spinner.setPreferredSize(new Dimension(35, 25));
        font_size_spinner.setValue(20);
        font_size_spinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                textEditor.this.text_area.setFont(new Font(textEditor.this.text_area.getFont().getFamily(), 0, (Integer)textEditor.this.font_size_spinner.getValue()));
            }
        });

        font_color_button.addActionListener(this);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        font_box = new JComboBox(fonts);
        font_box.addActionListener(this);
        font_box.setSelectedItem("Century");
        font_box.setPreferredSize(new Dimension(200, 28));

        // Menu bar portion //
        menuBar.add(openItem);
        menuBar.add(saveItem);
        menuBar.add(exitItem);

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        // Menu bar portion //

        this.setJMenuBar(menuBar);
        List<Component> componentList = Arrays.asList(font_label, font_box, font_color_button,
                                                        size_label, font_size_spinner, scroll_pane);
        for(Component component : componentList)
        {
            this.add(component);
        }

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.font_color_button) {
            JColorChooser color_chooser = new JColorChooser();
            Color color = JColorChooser.showDialog((Component)null, "Choose a color", Color.black);
            this.text_area.setForeground(color);
        }

        if (e.getSource() == this.font_box) {
            this.text_area.setFont(new Font((String)this.font_box.getSelectedItem(), 0, this.text_area.getFont().getSize()));
        }

        if(e.getSource() == saveItem){
            save();
        }

        if(e.getSource() == openItem) {
            open();
        }

        if(e.getSource() == exitItem){
            exit();
        }

    }

    @Override
    public void open() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("Text files", "txt");
        fileChooser.setFileFilter(fileNameExtensionFilter);

        int response = fileChooser.showOpenDialog(null);

        if(response == JFileChooser.APPROVE_OPTION)
        {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            Scanner fileIn = null;

            try {
                fileIn = new Scanner(file);
                if(file.isFile())
                {
                    while(fileIn.hasNextLine())
                    {
                        String line = fileIn.nextLine()+"\n";
                        text_area.append(line);
                    }
                }
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            finally {
                assert fileIn != null;
                fileIn.close();
            }
        }
    }

    @Override
    public void save() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));

        int response = fileChooser.showSaveDialog(null);

        if(response == JFileChooser.APPROVE_OPTION)
        {
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            PrintWriter fileOut = null;
            try {
                fileOut = new PrintWriter(file);
                fileOut.println(text_area.getText());
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            finally {
                fileOut.close();
            }
        }

    }

    @Override
    public void exit() {
        System.exit(0);

    }
}