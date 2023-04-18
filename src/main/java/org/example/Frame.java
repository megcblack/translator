package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.io.IOException;
import java.net.URISyntaxException;
import com.formdev.flatlaf.intellijthemes.FlatXcodeDarkIJTheme;


public class Frame implements ActionListener {
    private final JFrame frame;
    private JTextArea inputArea;
    private String text;
    private JTextArea outputArea;
    JButton button;
    private JComboBox transToLang;

    private JComboBox transFromLang;

    private Translator translator;

    public Frame() {

        try {
            UIManager.setLookAndFeel(new FlatXcodeDarkIJTheme());

        } catch( Exception ex ) {
            System.err.println( "Failed to initialize theme.");
        }

        translator = new Translator();
        frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(400, 400);

        transToLang = new JComboBox(translator.getLanguages());
        transToLang.setBounds(5, 149, 120, 25);

        transFromLang = new JComboBox(translator.getLanguages());
        transFromLang.setBounds(5, 10, 120, 25);

        button = new JButton("Translate");
        button.setBounds(275, 10, 120, 25);
        button.addActionListener(this);

        outputArea = new JTextArea();
        outputArea.setBounds(5, 180, 390, 100);
        outputArea.setEditable(false);
        Border border2 = BorderFactory.createTitledBorder("Translation: ");
        outputArea.setBorder(border2);

        inputArea = new JTextArea();
        inputArea.setBounds(5, 40, 390, 100);
        Border border1 =  BorderFactory.createTitledBorder("Enter Text: ");
        inputArea.setBorder(border1);

        frame.add(inputArea);
        frame.add(transFromLang);
        frame.add(outputArea);
        frame.add(transToLang);
        frame.add(button);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    //text updates when enter is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            text = inputArea.getText();
            System.out.println(text);
            String to = (String) transToLang.getSelectedItem();
            String from  = (String) transFromLang.getSelectedItem();

            try {
                Object obj = translator.getTranslation(text, to, from);
                System.out.println(obj);
                outputArea.setText((String) obj);
            } catch (Exception ex) {
                outputArea.setText("ERROR");
                try {
                    translator.googleIt();
                } catch (URISyntaxException exc) {
                    throw new RuntimeException(exc);
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
                throw new RuntimeException(ex);

            }
        }

    }
}
