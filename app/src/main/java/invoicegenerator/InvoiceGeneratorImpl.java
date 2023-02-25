package invoicegenerator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InvoiceGeneratorImpl implements InvoiceGenerator {

    public PDPage addPage(PDDocument pdDocument){
        PDPage pdPage = new PDPage();
        pdDocument.addPage(pdPage);
        return pdPage;
    }

    public void addTextToLocation(PDPageContentStream contentStream,int tx,int ty,PDFont font_name,int font_size, String text) throws IOException {
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.beginText();
        contentStream.setFont(font_name, font_size);
        contentStream.newLineAtOffset(tx, ty);
        contentStream.showText(text);
        contentStream.endText();
    }

    public void addMixedFontTextToLocation(PDPageContentStream contentStream, int tx, int ty, int font_size, List<TextFontPair> textFontPairs) throws IOException {
        contentStream.setNonStrokingColor(Color.BLACK);
        contentStream.beginText();
        contentStream.newLineAtOffset(tx, ty);
        for(TextFontPair textFontPair: textFontPairs){
            contentStream.setFont(textFontPair.getFont(), font_size);
            contentStream.showText(textFontPair.getText());
        }
        contentStream.endText();
    }

    public void addImageAtLocation(PDDocument pdDocument, PDPageContentStream contentStream,int tx,int ty,int width, int height, String imageName) throws IOException {
        PDImageXObject pdImage = PDImageXObject.createFromByteArray(pdDocument,ClassLoader.getSystemResourceAsStream(imageName).readAllBytes(),imageName);
        contentStream.drawImage(pdImage,tx,ty,width,height);
    }

    public PDFont loadFont(PDDocument pdDocument, String filename) throws IOException{
        return PDType0Font.load(pdDocument, ClassLoader.getSystemResourceAsStream(filename));
    }

    private void add_line(PDPageContentStream contentStream, int tx, int ty, float width, float height, float r, float g, float b) throws IOException{
        contentStream.setNonStrokingColor(r, g, b);
        contentStream.addRect(tx,ty,width,height);
        contentStream.fill();
    }

    public void addContent(PDDocument pdDocument, PDPage pdPage, String donorName, String donationAmount){
        float pageHeight = pdPage.getMediaBox().getHeight();
        float pageWidth = pdPage.getMediaBox().getWidth();
        Date today=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try(PDPageContentStream contentStream=new PDPageContentStream(pdDocument,pdPage)){
            PDFont verdanaBold = loadFont(pdDocument,"VerdanaBold.ttf");
            PDFont verdana = loadFont(pdDocument,"Verdana.ttf");
            PDFont arialBold = loadFont(pdDocument,"ArialBold.ttf");
            PDFont arial = loadFont(pdDocument,"Arial.ttf");
            PDFont calibri = loadFont(pdDocument,"Calibri.ttf");
            PDFont calibriBold = loadFont(pdDocument,"CalibriBold.ttf");
            addTextToLocation(contentStream,(int)(pageWidth*.22), (int)(pageHeight*.89),
                    PDType1Font.TIMES_ROMAN,  18,
                    "International Society for Krishna Consciousness");

            addTextToLocation(contentStream,(int)(pageWidth*.244), (int)(pageHeight*.87),
                    PDType1Font.TIMES_BOLD,  10,
                    "Founder- Acarya:  His Divine Grace A. C. Bhaktivedanta Swami Prabhupada");

            addTextToLocation(contentStream,(int)(pageWidth*.425), (int)(pageHeight*.854),
                    PDType1Font.HELVETICA_BOLD,  12,
                    "ISKCON Hillsboro");

            addImageAtLocation(pdDocument, contentStream,
                    (int)(pageWidth*.125), (int)(pageHeight*.80),
                    (int)34.798*3,(int)18.034*3,
                    "image002.jpg");


            addTextToLocation(contentStream,(int)(pageWidth*.325), (int)(pageHeight*.84),
                    verdanaBold,  10, //TODO: move st up
                    "612 N 1st Ave, Hillsboro, OR 97124");

            addTextToLocation(contentStream,(int)(pageWidth*.293), (int)(pageHeight*.825),
                    PDType1Font.TIMES_BOLD,  10,
                    "Ph:");
            addTextToLocation(contentStream,(int)(pageWidth*.321), (int)(pageHeight*.825),
                    verdana,  10,
                    "503-567-7363 ");
            addTextToLocation(contentStream,(int)(pageWidth*.45), (int)(pageHeight*.825),
                    arialBold,  10,
                    "Federal Tax ID/ EIN #");
            addTextToLocation(contentStream,(int)(pageWidth*.617), (int)(pageHeight*.825),
                    arial,  10,
                    "is 20-0149018");

            addTextToLocation(contentStream,(int)(pageWidth*.39), (int)(pageHeight*.81),
                    arialBold,  10,
                    "Web:");
            addTextToLocation(contentStream,(int)(pageWidth*.434), (int)(pageHeight*.81),
                    verdana,  10,
                    "www.iskconportland.com");

            addTextToLocation(contentStream,(int)(pageWidth*.39), (int)(pageHeight*.795),
                    arialBold,  10,
                    "Email:");
            addTextToLocation(contentStream,(int)(pageWidth*.442), (int)(pageHeight*.795),
                    verdana,  10,
                    "info@iskconportland.com");

            addImageAtLocation(pdDocument, contentStream,
                    (int)(pageWidth*.78), (int)(pageHeight*.80),
                    (int)(25.4*1.5),(int)(33*1.5),
                    "image001.png"); //TODO: move to correct position relative to other fields

            add_line(contentStream,
                    (int)(pageWidth*.15), (int)(pageHeight*.775),
                    (int)(pageWidth*.7), 2,
                    .65f,.65f,.65f
            );

            addTextToLocation(contentStream,(int)(pageWidth*.755), (int)(pageHeight*.757),
                    calibri,  12,
                    dateFormat.format(today)
            );

            addTextToLocation(contentStream,(int)(pageWidth*.30), (int)(pageHeight*.728),
                    calibriBold,  12,
                    "Receipt for Donation to ISKCON HILLSBORO"
            );

            add_line(contentStream,
                    (int)(pageWidth*.30), (int)(pageHeight*.725),
                    pageWidth*.3525f, 1.0f,
                    0.05f,0.05f,0.05f
            );

            int contentStart = (int)(pageWidth*.15);

            addMixedFontTextToLocation(contentStream, contentStart, (int)(pageHeight*.685),
                    12,
                    List.of(
                            new TextFontPair("Dear ",calibri),
                            new TextFontPair(donorName, calibriBold),
                            new TextFontPair(",", calibri)
                    )
            );

            addTextToLocation(contentStream, contentStart, (int)(pageHeight*.65),
                    calibri,  12,
                    "ISKCON Hillsboro ISKCON Hillsboro is very grateful for your kind support and generous"
            );

            addMixedFontTextToLocation(contentStream, contentStart, (int)(pageHeight*.63),
                    12,
                    List.of(
                            new TextFontPair("donation for an amount of ",calibri),
                            new TextFontPair(donationAmount, calibriBold),
                            new TextFontPair(" in 2022.",calibri)

                    )
            );


            addTextToLocation(contentStream, contentStart, (int)(pageHeight*.59),
                    calibri,  12,
                    "ISKCON Hillsboro is a 501(c)(3) - non-profit, tax exempt organization with Federal Tax ID"
            );
            addTextToLocation(contentStream, contentStart, (int)(pageHeight*.57),
                    calibri,  12,
                    "of 20-0149018. You may, therefore, use this receipt for tax deduction purposes. There"
            );
            addTextToLocation(contentStream, contentStart, (int)(pageHeight*.55),
                    calibri,  12,
                    "were no services or gifts provided to you for this donation."
            );
            addTextToLocation(contentStream, contentStart, (int)(pageHeight*.50),
                    calibri,  12,
                    "Sincerely yours,"
            );
            addTextToLocation(contentStream, contentStart, (int)(pageHeight*.47),
                    calibri,  12,
                    "Treasurer - ISKCON Hillsboro"
            );

        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void generateInvoice(String donorName, String donationAmount) {

        String filename = donorName.replaceAll("\s","_")+".pdf";

        File outputFile=new File(filename);

        try(PDDocument pdfDocument = new PDDocument();
            FileOutputStream fos=new FileOutputStream(outputFile)) {

            PDPage pdPage= addPage(pdfDocument);
            addContent(pdfDocument,pdPage, donorName, donationAmount);
            pdfDocument.save(fos);

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
