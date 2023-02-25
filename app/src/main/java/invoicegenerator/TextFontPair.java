package invoicegenerator;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class TextFontPair {
    String text;
    PDFont font;

    public TextFontPair(String text, PDFont font) {
        this.text = text;
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public PDFont getFont() {
        return font;
    }

}
