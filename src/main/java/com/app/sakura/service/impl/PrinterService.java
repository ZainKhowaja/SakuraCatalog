package com.app.sakura.service.impl;

import com.app.sakura.model.PrintProductDetails;
import com.app.sakura.util.FileUtil;
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
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class PrinterService {

    private static final String HTML_FILE = "sample.html";
    private static final String PDF_FILE = "sample.pdf";
    private static final String HTML_TEXT = "<html><head>    <style>        body {            font-family: Arial, Helvetica, sans-serif;        }        table {            border: 1px solid black;            float:left;            margin-left: 5px;            position:relative        }        td{            font-size:12px;        }        .value {            padding:0px 0px 0px 0px;            font-weight: bold;        }        .key{            width:40%;        }        .ref-head-value {            padding:0px 0px 0px 70px;        }        .man {            padding:0px 0px 0px 70px;            font-weight: bold;        }        .ref {            font-weight: bold;        }        img{            border: 1px solid black;            position:absolute;            width:54%;            top: 270px;            margin-left: 5px;        }        #over img {            margin-left: 5px;            margin-right:auto;            display: block;        }    </style></head><body><h2>FUJI FILTER PRODUCTS CATALOGUE</h2><table width=\"55%\">    <tr>        <td class=\"key\">Fuji No:</td>        <td class=\"value\">{fujiNo}</td>    </tr>    <tr>        <td class=\"key\">Filter:</td>        <td class=\"value\">{filter}</td>    </tr>    <tr>        <td class=\"key\">Brand:</td>        <td class=\"value\">{brand}</td>    </tr>    <tr>        <td class=\"key\">Type Details:</td>        <td class=\"value\">{type}</td>    </tr>    <tr>        <td class=\"key\">Height Diameter:</td>        <td class=\"value\">{height}</td>    </tr>    <tr>        <td class=\"key\">Outer Diameter:</td>        <td class=\"value\">{outer}</td>    </tr>    <tr>        <td class=\"key\">Inner Diameter:</td>        <td class=\"value\">{inner}</td>    </tr>    <tr>        <td class=\"key\">Packaging Per Carton:</td>        <td class=\"value\">{pack}</td>    </tr>    <tr>        <td class=\"key\">Thread:</td>        <td class=\"value\">{thread}</td>    </tr>   <tr>        <td class=\"key\">Inner Diameter Secondary:</td>        <td class=\"value\">{innerSec}</td>    </tr>   <tr>        <td class=\"key\">Outer Diameter Secondary:</td>        <td class=\"value\">{outerSec}</td>    </tr>    <tr>        <td class=\"key\">Note:</td>        <td class=\"value\">{note}</td>    </tr></table><table width=\"44%\">    <tr>        <td class=\"key\">Reference #</td>        <td class=\"ref-head-value\">Manufacturer</td>    </tr>    </ref></table><div><img src=\"{imagePath}\"/></div></body></html>\n";

    public void printProductDetails(PrintProductDetails printProductDetails) throws IOException, PrinterException, URISyntaxException {
        String htmlText = fillDataInHtml(getStaticHtml(), printProductDetails);
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
    private String getStaticHtml(){
        return HTML_TEXT;
    }
    private String getHtmlText() throws FileNotFoundException, URISyntaxException {
        String htmlText = "";

        Scanner scanner = new Scanner(new File(getClass().getResource(HTML_FILE).toURI()));
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
                .replaceFirst("\\{outerSec}",pro.getOuterSecD())
                .replaceFirst("\\{innerSec}",pro.getInnerSecD())
                .replaceFirst("\\{note}",pro.getNote())
                .replaceFirst("\\{imagePath}",getImagePath(pro.getImageName()));
        System.out.println(htmlText);
        return htmlText;
    }

    private String getImagePath(String imageName) {
        File file = new File(imageName);
        System.out.println( "image/" + file.getName());
        return "image/" + file.getName();
    }

    public static void main(String[] args) throws IOException, PrinterException, URISyntaxException {
        String htmlText = "";

        Scanner scanner = new Scanner(new File(HTML_FILE));
        while(scanner.hasNext()){
            htmlText += scanner.nextLine();
        }
        System.out.println(htmlText);
//        PrintProductDetails printProductDetails = PrintProductDetails.PrintProductDetailsBuilder.newBuilder()
//                .setFujiNo("FC-1503")
//                .setFilter("Fuel Filter")
//                .setManufacturer("DONALDSON")
//                .setTypeDetail("Full Flow Spin-On High Efficency")
//                .setHeight("126 (mm)")
//                .setInnerD("90 (inch)")
//                .setOuterD("n/a")
//                .setThread("3/4\"X16")
//                .setPackagingPerCaton("20 PCS")
//                .setNote("Checking")
//                .setImageName("image/test.jpg")
//                .addReference("BF19","BALDWIN")
//                .addReference("BF7602","BALDWIN")
//                .addReference("P502155","BALDWIN")
//                .addReference("P55234","BALDWIN")
//                .addReference("BF12","BALDWIN")
//                .addReference("BF13","BALDWIN")
//                .addReference("1-132499974-0","ISUZU")
//                .addReference("1-132419974-0","ISUZU")
//                .addReference("BF19-10","BALDWIN")
//                .addReference("BF7604","BALDWIN")
//                .addReference("P502115","BALDWIN")
//                .addReference("P552356","BALDWIN")
//                .addReference("BFF-21043","BALDWIN")
//                .addReference("BF-210432","BALDWIN")
//                .addReference("1-132499974-0","ISUZU")
//                .addReference("1-132499973-0","ISUZU")
//                .addReference("1-1322499974-0","ISUZU")
//                .addReference("1-1321419974-0","ISUZU")
//                .addReference("BF139-10","BALDWIN")
//                .addReference("BF74604","BALDWIN")
//                .addReference("P5025115","BALDWIN")
//                .addReference("P5523656","BALDWIN")
//                .addReference("BFF-216043","BALDWIN")
//                .addReference("BF-2104632","BALDWIN")
//                .addReference("1-1324939974-0","ISUZU")
//                .addReference("1-1324199973-0","FLEETGUARD")
//                .build();
//        new PrinterService().printProductDetails(printProductDetails);
        System.out.println("done");
    }
}
