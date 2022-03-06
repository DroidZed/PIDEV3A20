/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.ArrayList;
import jdbc.jdbc;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author hp
 */
public class Pdf {
        private Connection con;
        private Statement ste;
    public Pdf()  {
        con = jdbc.getInstance().getCnx();
          
    
}
    public void add(String file, String titleOffer, String descOffer, String typeoffer, String startDTM, String endDTM, String salaryOffer, String regionOffer, String addressOffer, String domain) throws FileNotFoundException, SQLException, DocumentException{
        
                Document my_pdf_report = new Document();
                PdfWriter.getInstance(my_pdf_report, new FileOutputStream(file));
                my_pdf_report.open();            
                //we have four columns in our table
                PdfPTable my_report_table = new PdfPTable(2);
                //create a cell object
                PdfPCell table_cell;
                                
                              
                                table_cell=new PdfPCell(new Phrase("Title"));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase(titleOffer));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase("Description"));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase(descOffer));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase("Type"));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase(typeoffer));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase("Date Debut"));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase(startDTM));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase("Date Fin"));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase(endDTM));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase("Salaire"));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase(salaryOffer));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase("Adresse"));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase(addressOffer));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase("Region"));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase(regionOffer));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase("Domaine"));
                                my_report_table.addCell(table_cell);
                                table_cell=new PdfPCell(new Phrase(domain));
                                my_report_table.addCell(table_cell);
                               
                                
                /* Attach report table to PDF */
                my_pdf_report.add(my_report_table);                       
                my_pdf_report.close();
                
               /* Close all DB related objects */

        
    }
     
}
