package org.example;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Translator {
    private final String[] languages = {"Afrikaans", "Amharic", "Arabic", "Aragonese", "Armenian", "Avaric", "Avestan", "Azerbaijani", "Basque", "Belarusian",
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
    private final String[] langCode = {"af", "am", "ar", "an", "hy", "av", "ae", "az", "eu", "be", "bn", "bs", "bg", "my", "ca", "ch", "ce", "ny", "zh",
            "zh-tw", "zh-cn", "zh-hk", "zh-sg", "zh-mo", "cv", "kw", "co", "cr", "hr", "cs", "da", "nl", "en", "eo", "et", "fi",
            "fr", "ff", "gl", "ka", "de", "el", "gu", "ht", "ha", "he", "hz", "hi", "ho", "hu", "is", "io", "ig", "id", "ga", "it", "ja", "jv", "kn", "kr",
            "kk", "km", "ki", "rw", "kv", "kg", "ko", "ku", "kj", "ky", "lo", "la", "lv", "li","lt", "lu", "lb", "mk", "mg", "ms", "ml", "mt", "mr",
            "mh", "mn", "mi", "nv", "ng", "ne", "nd", "se", "no", "nb", "nn", "ii", "oj", "cu", "or", "pa","ps", "fa", "pl", "pt", "pi", "ro", "ru",
            "sm", "sc", "gd", "sr", "sn", "sd", "si", "sk", "sl", "so", "nr", "st", "es", "su", "sw", "sv", "tl", "ty", "tg", "ta", "tt", "te", "th",
            "tr", "tk", "uk", "ur", "ug", "uz", "vi", "wa", "cy", "fy", "xh", "yi", "yo" };

    public String[] getLanguages() {
        return languages;
    }
    public Object getTranslation(String text, String translateTo, String translateFrom) throws Exception {
        translateTo = getLangCode(translateTo);
        translateFrom = getLangCode(translateFrom);
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

    public static void googleIt() throws URISyntaxException, IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(new URI("https://translate.google.com"));
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
