package com.akilisoft.tech.resto.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.akilisoft.tech.resto.Model.ProductImage;
import com.akilisoft.tech.resto.Model.ProductModel;
import com.akilisoft.tech.resto.R;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Copyright (c) 2020, NIKISS. All Rights Reserved.
 * <p>
 * Save to the extent permitted by law, you may not use, copy, modify, distribute or
 * create derivative works of this material or any part of it without the prior
 * written consent of  OUEDRAOGO ISSOUF NIKISS.email:ouedraogo.nikiss@gmail.com
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the software.
 * Created on 06,janvier,2020
 */
public class PdfUtils {

    private String path;
    private File dir;
    private File file;
    private PdfPCell cell;
    private Image bgImage;
    private Context context;
    private List<ProductImage> produits = new ArrayList<>();
    private int montantTotal= 0;
    private String dateReport;
    //use to set background color
    BaseColor myColor = WebColors.getRGBColor("#9E9E9E");
    BaseColor myColor1 = WebColors.getRGBColor("#757575");

    public PdfUtils(Context context, List<ProductImage> produits, int montantTotal, String dateReport) {
        this.context = context;
        this.produits = produits;
        this.montantTotal = montantTotal;
        this.dateReport = dateReport;

        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Resto/PDF Files";
        dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("dir creted");
        }
    }

    public void createPDF() throws FileNotFoundException, DocumentException {

        //create document file
        Document doc = new Document();
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        try {

            Log.e("PDFCreator", "PDF Path: " + path);
            //SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
            //file = new File(dir, "Waari PDF" + sdf.format(Calendar.getInstance().getTime()) + ".pdf");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = dateReport.replace("-", "");
            file = new File(dir, "facture" + fileName + ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter writer = PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();
//create table
            PdfPTable pt = new PdfPTable(3);
            pt.setWidthPercentage(100);
            float[] fl = new float[]{20, 45, 35};
            pt.setWidths(fl);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);

            //set drawable in cell
            Drawable myImage = context.getResources().getDrawable(R.drawable.food);
            Bitmap bitmap = ((BitmapDrawable) myImage).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();
            try {
                bgImage = Image.getInstance(bitmapdata);
                bgImage.setAbsolutePosition(330f, 642f);
                cell.addElement(bgImage);
                pt.addCell(cell);
                cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                cell.addElement(new Paragraph("Bienvenue dans Food"));

                cell.addElement(new Paragraph(""));
                cell.addElement(new Paragraph(""));
                pt.addCell(cell);
                cell = new PdfPCell(new Paragraph(""));
                cell.setBorder(Rectangle.NO_BORDER);
                pt.addCell(cell);

                PdfPTable pTable = new PdfPTable(1);
                pTable.setWidthPercentage(100);
                cell = new PdfPCell();
                cell.setColspan(1);
                cell.addElement(pt);
                pTable.addCell(cell);
                PdfPTable table = new PdfPTable(5);

                float[] columnWidth = new float[]{6, 30, 30, 40, 30};
                table.setWidths(columnWidth);


                cell = new PdfPCell();


                cell.setBackgroundColor(myColor);
                cell.setColspan(5);
                cell.addElement(pTable);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(" "));
                cell.setColspan(5);
                table.addCell(cell);
                cell = new PdfPCell();
                cell.setColspan(5);

                cell.setBackgroundColor(myColor1);

                cell = new PdfPCell(new Phrase("#"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Libelle"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Prix"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Qte"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Montant"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);

                //table.setHeaderRows(3);
                cell = new PdfPCell();
                cell.setColspan(5);


                int j=1;
                Double tt=0D;
                Double pvt=0D;
                String montantTotal;

                for (ProductImage produit:produits) {

                    table.addCell(String.valueOf(j));

                    table.addCell(produit.getProductName());//Header 1 row
                    table.addCell(produit.getDealerPrice());//"Header 2 row " +
                    table.addCell(""+produit.getProductQuantity());

                    table.addCell("" + produit.getTotalCash());//"Header 4 row " +

                }

                PdfPTable ftable = new PdfPTable(5);
                ftable.setWidthPercentage(100);
                float[] columnWidthaa = new float[]{30, 10, 30, 40, 10};
                ftable.setWidths(columnWidthaa);
                cell = new PdfPCell();
                cell.setColspan(5);
                cell.setBackgroundColor(myColor1);

                montantTotal = format.format(this.montantTotal);
                cell = new PdfPCell(new Phrase("Total: "+montantTotal+" FCFA"));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(myColor1);
                ftable.addCell(cell);
                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(myColor1);
                ftable.addCell(cell);
                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(myColor1);
                ftable.addCell(cell);
                montantTotal = format.format(pvt);
                cell = new PdfPCell(new Phrase(montantTotal+" FCFA"));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(myColor1);
                ftable.addCell(cell);
                cell = new PdfPCell(new Phrase(""));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setBackgroundColor(myColor1);
                ftable.addCell(cell);
                cell = new PdfPCell(new Paragraph("Date: "+dateReport));
                cell.setColspan(5);
                ftable.addCell(cell);
                cell = new PdfPCell();
                cell.setColspan(5);
                cell.addElement(ftable);
                table.addCell(cell);
                doc.add(table);
                Toast.makeText(context, "PDF creer", Toast.LENGTH_LONG).show();
            } catch (DocumentException de) {
                Log.e("PDFCreator", "DocumentException:" + de);
            } catch (IOException e) {
                Log.e("PDFCreator", "ioException:" + e);
            } finally {
                doc.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
