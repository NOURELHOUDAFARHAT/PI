package org.example.lastoflast;

import entities.User;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class ExporterPdf {

    public static void exportToPDF(String title, List<User> users, String filePath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Set the font and font size
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                // Add title
                contentStream.beginText();
                contentStream.newLineAtOffset(20, 700);
                contentStream.showText(title);
                contentStream.endText();

                // Add User details
                int yOffset = 650;
                int yOffsetIncrement = 150;
                for (User user : users) {
                    StringBuilder userDetails = new StringBuilder();
                    userDetails.append("Nom: ").append(user.getNom()).append(" | ");
                    userDetails.append("Prénom: ").append(user.getPrenom()).append(" | ");
                    userDetails.append("Email: ").append(user.getEmail());
                    userDetails.append("Sexe: ").append(user.getSexe());
                    userDetails.append("Adresse: ").append(user.getAdresse());


                    contentStream.beginText();
                    contentStream.newLineAtOffset(20, yOffset);
                    contentStream.showText(userDetails.toString());
                    contentStream.newLine();
                    contentStream.endText();

                    yOffset -= yOffsetIncrement; // Adjust this value based on your layout
                }
            }

            // Save the document to the specified file path
            document.save(filePath);
        }
    }
}
