package com.app.sakura.service.impl;

import com.app.sakura.model.PrintProductDetails;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.stereotype.Service;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class PrinterService {

    private static final String HTML_FILE = "sample.html";
    private static final String PDF_FILE = "sample.pdf";

    public void printProductDetails(PrintProductDetails printProductDetails) throws IOException, PrinterException {
        String htmlText = fillDataInHtml(getHtmlText(), printProductDetails);
        htmlText = fillReferenceInHtml(htmlText,printProductDetails.getReference());
        createPDF(htmlText);
        printPDF();
    }

    private void printPDF() throws IOException, PrinterException {
        PDDocument pdDocument = PDDocument.load(new File("sample.pdf"));
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(pdDocument));
        job.print();
    }

    private boolean createPDF(String html) throws IOException {

        PdfWriter pdfWriter = new PdfWriter(PDF_FILE);
        PdfDocument pdfDoc = new PdfDocument(pdfWriter);
        pdfDoc.setDefaultPageSize(PageSize.A4);
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(html,
                pdfDoc, converterProperties);
        return true;
    }

    private String getHtmlText() throws FileNotFoundException {
        String htmlText = "";
        Scanner scanner = new Scanner(new File(HTML_FILE));
        while(scanner.hasNext()){
            htmlText += scanner.nextLine();
        }
        return htmlText;
    }

    private String fillReferenceInHtml(String htmlText , HashMap<String,String> ref){
        String row = "<tr><td class=\"ref\">%s</td><td class=\"man\">%s</td></tr>";
        String rows = "";
        String firstPart = htmlText.split("</ref>")[0];
        String secondPart = htmlText.split("</ref>")[1];

        for (Map.Entry<String, String> entry : ref.entrySet()) {
            String reference = entry.getKey();
            String brand = entry.getValue();
            rows += String.format(row,reference,brand);
        }

        return firstPart.concat(rows).concat(secondPart);
    }
    private String fillDataInHtml(String htmlText, PrintProductDetails pro){
        htmlText = htmlText
                .replaceFirst("\\{fujiNo}",pro.getFujiNo())
                .replaceFirst("\\{filter}",pro.getFilter())
                .replaceFirst("\\{brand}",pro.getManufacturer())
                .replaceFirst("\\{type}",pro.getTypeDetail())
                .replaceFirst("\\{height}",pro.getHeight())
                .replaceFirst("\\{filter}",pro.getFilter())
                .replaceFirst("\\{outer}",pro.getOuterD())
                .replaceFirst("\\{inner}",pro.getInnerD())
                .replaceFirst("\\{pack}",pro.getPackagingPerCaton())
                .replaceFirst("\\{thread}",pro.getThread())
                .replaceFirst("\\{note}",pro.getNote())
                .replaceFirst("\\{imagePath}",pro.getImageName());
        System.out.println(htmlText);
        return htmlText;
    }

    public static void main(String[] args) throws IOException, PrinterException {
        PrintProductDetails printProductDetails = PrintProductDetails.PrintProductDetailsBuilder.newBuilder()
                .setFujiNo("FC-1503")
                .setFilter("Fuel Filter")
                .setManufacturer("DONALDSON")
                .setTypeDetail("Full Flow Spin-On High Efficency")
                .setHeight("126 (mm)")
                .setInnerD("90 (inch)")
                .setOuterD("n/a")
                .setThread("3/4\"X16")
                .setPackagingPerCaton("20 PCS")
                .setNote("Checking")
                .setImageName("image/test.jpg")
                .addReference("BF19","BALDWIN")
                .addReference("BF7602","BALDWIN")
                .addReference("P502155","BALDWIN")
                .addReference("P55234","BALDWIN")
                .addReference("BF12","BALDWIN")
                .addReference("BF13","BALDWIN")
                .addReference("1-132499974-0","ISUZU")
                .addReference("1-132419974-0","ISUZU")
                .addReference("BF19-10","BALDWIN")
                .addReference("BF7604","BALDWIN")
                .addReference("P502115","BALDWIN")
                .addReference("P552356","BALDWIN")
                .addReference("BFF-21043","BALDWIN")
                .addReference("BF-210432","BALDWIN")
                .addReference("1-132499974-0","ISUZU")
                .addReference("1-132499973-0","ISUZU")
                .addReference("1-1322499974-0","ISUZU")
                .addReference("1-1321419974-0","ISUZU")
                .addReference("BF139-10","BALDWIN")
                .addReference("BF74604","BALDWIN")
                .addReference("P5025115","BALDWIN")
                .addReference("P5523656","BALDWIN")
                .addReference("BFF-216043","BALDWIN")
                .addReference("BF-2104632","BALDWIN")
                .addReference("1-1324939974-0","ISUZU")
                .addReference("1-1324199973-0","FLEETGUARD")
                .build();
        new PrinterService().printProductDetails(printProductDetails);
        System.out.println("done");
    }
}
