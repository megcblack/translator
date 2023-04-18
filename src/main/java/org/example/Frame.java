package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;
import com.formdev.flatlaf.*;  //intellijthemes.FlatCarbonIJTheme;
import javax.swing.UIManager;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
//import com.formdev.flatlaf.intellijthemes.FlatXcodeDarkIJTheme;


public class Frame implements ActionListener {
    private JFrame frame;
    private JTextArea inputArea;
    private String text;
    private JTextArea outputArea;
    JButton button;
    private JComboBox transToLang;

    private JComboBox transFromLang;
  //  private String translateTo = "";
  //  private String translateFrom = "";

    private String[] langCode = {"af", "am", "ar", "an", "hy", "av", "ae", "az", "eu", "be", "bn", "bs", "bg", "my", "ca", "ch", "ce", "ny", "zh",
            "zh-tw", "zh-cn", "zh-hk", "zh-sg", "zh-mo", "cv", "kw", "co", "cr", "hr", "cs", "da", "nl", "en", "eo", "et", "fi",
            "fr", "ff", "gl", "ka", "de", "el", "gu", "ht", "ha", "he", "hz", "hi", "ho", "hu", "is", "io", "ig", "id", "ga", "it", "ja", "jv", "kn", "kr",
            "kk", "km", "ki", "rw", "kv", "kg", "ko", "ku", "kj", "ky", "lo", "la", "lv", "li","lt", "lu", "lb", "mk", "mg", "ms", "ml", "mt", "mr",
            "mh", "mn", "mi", "nv", "ng", "ne", "nd", "se", "no", "nb", "nn", "ii", "oj", "cu", "or", "pa","ps", "fa", "pl", "pt", "pi", "ro", "ru",
            "sm", "sc", "gd", "sr", "sn", "sd", "si", "sk", "sl", "so", "nr", "st", "es", "su", "sw", "sv", "tl", "ty", "tg", "ta", "tt", "te", "th",
            "tr", "tk", "uk", "ur", "ug", "uz", "vi", "wa", "cy", "fy", "xh", "yi", "yo" };
    private String[] languages = {"Afrikaans", "Amharic", "Arabic", "Aragonese", "Armenian", "Avaric", "Avestan", "Azerbaijani", "Basque", "Belarusian",
            "Bengali", "Bosnian", "Bulgarian", "Burmese", "Catalan", "Chamorro", "Chechen", "Chichewa", "Chinese", "Chinese Taiwan", "Chinese PRC", "Chinese Hong Kong",
            "Chinese Singapore", "Chinese Macau", "Chuvash", "Cornish", "Corsican", "Cree", "Croatian", "Czech", "Danish", "Dutch", "English", "Esperanto", "Estonian",
            "Finnish", "French", "Fula", "Galician", "Georgian", "German", "Greek", "Gujarati", "Haitian", "Hausa", "Hebrew", "Herero", "Hindi", "Hiri Motu", "Hungarian",
            "Icelandic", "Ido", "Igbo", "Indonesian", "Irish", "Italian", "Japanese", "Javanese", "Kannada", "Kanuri", "Kazakh", "Khmer", "Kikuyu", "Kinyarwanda",
            "Komi", "Kongo", "Korean", "Kurdish", "Kwanyama", "Kyrgyz", "Lao", "Latin", "Latvian", "Limburgish", "Lithuanian", "Luba-Katanga", "Luxembourgish", "Macedonian",
            "Malagasy", "Malay", "Malayalam", "Maltese", "Marathi", "Marshallese", "Mongolian", "Māori", "Navajo", "Ndonga", "Nepali", "Northern Ndebele", "Northern Sami",
            "Norwegian", "Norwegian Bokmål", "Norwegian Nynorsk", "Nuosu", "Ojibwe", "Old Church Slavonic", "Oriya", "Panjabi", "Pashto", "Persian", "Polish", "Portuguese",
            "Pāli", "Romanian", "Russian", "Samoan", "Sardinian", "Scottish Gaelic", "Serbian", "Shona", "Sindhi", "Sinhala", "Slovak", "Slovene", "Somali", "Southern Ndebele",
            "Southern Sotho", "Spanish", "Sundanese", "Swahili", "Swedish", "Tagalog", "Tahitian", "Tajik", "Tamil", "Tatar", "Telugu", "Thai", "Turkish", "Turkmen",
            "Ukrainian", "Urdu", "Uyghur", "Uzbek", "Vietnamese", "Walloon", "Welsh", "Western Frisian", "Xhosa", "Yiddish", "Yoruba"};

    public Frame() {

        try {
            UIManager.setLookAndFeel(new FlatXcodeDarkIJTheme());

        } catch( Exception ex ) {
            System.err.println( "Failed to initialize theme.");
        }


        frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(400, 400);

        transToLang = new JComboBox(languages);
        transToLang.setBounds(5, 149, 120, 25);

        transFromLang = new JComboBox(languages);
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

      //  JScrollPane scroll = new JScrollPane(inputArea);

     //   frame.add(scroll);

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
            String to = getLangCode((String) transToLang.getSelectedItem());
            String from  = getLangCode((String) transFromLang.getSelectedItem());

            try {
                Object obj = getTranslation(text, to, from);
                System.out.println(obj);
                outputArea.setText((String) obj);
            } catch (Exception ex) {
                outputArea.setText("ERROR");
                try {
                    googleIt();
                } catch (URISyntaxException exc) {
                    throw new RuntimeException(exc);
                } catch (IOException exc) {
                    throw new RuntimeException(exc);
                }
                throw new RuntimeException(ex);

            }
        }

    }

    private static void googleIt() throws URISyntaxException, IOException {
        Desktop desktop = Desktop.getDesktop();

            desktop.browse(new URI("https://translate.google.com"));

    }

    private Object getTranslation(String text, String translateTo, String translateFrom) throws Exception {
        //String to = "es";
        //String from  = "en";
        String body = "from=" + translateFrom + "&to=" + translateTo + "&text=" + text;
        HttpResponse<JsonNode> response = null;
        response = Unirest.post("https://translo.p.rapidapi.com/api/v3/translate")
                .header("content-type", "application/x-www-form-urlencoded")
                .header("X-RapidAPI-Key", "1f30b371b4msh69066cd1523a8ffp1165aajsn20382e10548f")
                .header("X-RapidAPI-Host", "translo.p.rapidapi.com")
                .body(body)
                .asJson();
        JSONObject obj = response.getBody().getObject();
        return obj.get("translated_text");
    }

    private String getLangCode(String language) {
        int index = -1;
        for (int i = 0; i < langCode.length; i++) {
            if (languages[i].equals(language)) {
                index = i;
            }
        }
        return langCode[index];
    }






}
