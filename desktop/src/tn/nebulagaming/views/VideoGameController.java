/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;
import javax.imageio.ImageIO;
import models.JeuVideo;
import models.publication;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.Rating;
import service.serviceJeuVideo;
import service.servicePublication; 
import utils.jdbc;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class VideoGameController implements Initializable {

    private Statement ste;
    private Connection con;
    int ratingJeu=0;

    @FXML
    private TextField nomtxt;
    @FXML
    private Button btnajouter;
    @FXML
    private TableView<JeuVideo> tableVideoGame;
    @FXML
    private TableColumn<JeuVideo, Integer> idt;
    @FXML
    private TableColumn<JeuVideo, String> nomt;
    @FXML
    private TableColumn<JeuVideo, ImageView> imaget;
    
    private final ObservableList<JeuVideo> data = FXCollections.observableArrayList();
    @FXML
    private TextField affiche;
    @FXML
    private Button uploadbutton;
    
    serviceJeuVideo sjv= new serviceJeuVideo();
    @FXML
    private TextField recherche;
    @FXML
    private Label idlabel;
    @FXML
    private TableColumn<JeuVideo, Float> NoteVG;
    @FXML
    private Button btnLIKE;
    @FXML
    private Button btnMODIF;
    @FXML
    private Button PDFbtn;
    @FXML
    private Button PRINTbtn;
    @FXML
    private Button QRbtn;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private Rating stars;
    @FXML
    private ImageView fond;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Aff();
        RechercheAV();
        idlabel.setDisable(false);
    } 
    
    public void Aff(){
        
        try {
            con = jdbc.getInstance().getCnx();
            ste = con.createStatement();
            data.clear();
            
            ResultSet rs = ste.executeQuery("select * from tbl_videogame");
            while(rs.next())
            {
                JeuVideo f= new JeuVideo(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getDouble(6));
                
                File file = new File(rs.getString(4));
                Image image = new Image(file.toURI().toString());
                
                ImageView imageView =new ImageView(image);
                imageView.setImage(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                
                f.setImg(imageView);
                data.add(f);
                
            }

            idt.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomt.setCellValueFactory(new PropertyValueFactory<>("nameVg"));
            imaget.setCellValueFactory(new PropertyValueFactory<>("img"));
            NoteVG.setCellValueFactory(new PropertyValueFactory<>("rating"));
            

            tableVideoGame.setItems(data);
        } catch (SQLException ex) {
            Logger.getLogger(VideoGameController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
         
        public void RechercheAV(){
                // Wrap the ObservableList in a FilteredList (initially display all data).
                FilteredList<JeuVideo> filteredData = new FilteredList<>(data, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(evenement -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (evenement.getNameVg().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				}    else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<JeuVideo> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tableVideoGame.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tableVideoGame.setItems(sortedData);
    }
      

    @FXML
    private void Ajouter(ActionEvent event) throws IOException, SQLException {
        if (affiche.getText().isEmpty() || nomtxt.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Verifiez les champs!");
            alert.show();
        }
        else
        {
            File f = new File(affiche.getText());
            System.out.println(f.getName());
            System.out.println(nomtxt.getText());
            JeuVideo jv = new JeuVideo(nomtxt.getText(),"C:\\Users\\dell\\Desktop\\PI\\Nouveau dossier\\Forum"+f.getName());
            jv.setRating(stars.getRating());
            
            sjv.ajouter(jv);
            
            Files.copy(Paths.get(affiche.getText()),Paths.get("C:\\Users\\dell\\Desktop\\PI\\Nouveau dossier\\Forum"+f.getName()),REPLACE_EXISTING);
            Aff();
            RechercheAV();
        }
    }
        

    @FXML
    private void Uploadfile(ActionEvent event) {
               
        FileChooser fc = new FileChooser();
        String path = fc.showOpenDialog(uploadbutton.getScene().getWindow()).getPath();
        affiche.setText(path);
    }

    @FXML
    private void Delete(ActionEvent event) throws SQLException {
             tableVideoGame.setItems(data);

             ObservableList<JeuVideo> allJV,SingleJV ;
             allJV=tableVideoGame.getItems();
             SingleJV=tableVideoGame.getSelectionModel().getSelectedItems();
             JeuVideo A = SingleJV.get(0);
             sjv.supprimer(A);
             SingleJV.forEach(allJV::remove);
             Aff();
             RechercheAV();
    }

    @FXML
    private void clickedColumn(MouseEvent event) {
                     tableVideoGame.setItems(data);

             ObservableList<JeuVideo> allJV,SingleJV ;
             allJV=tableVideoGame.getItems();
             SingleJV=tableVideoGame.getSelectionModel().getSelectedItems();
             JeuVideo A = SingleJV.get(0);
        
        nomtxt.setText(A.getNameVg());
        affiche.setText(A.getImageVg());
        idlabel.setText(String.valueOf(A.getId()));
        stars.setRating(A.getRating());
    }

    @FXML
    private void ModifierJeu(ActionEvent event) throws IOException {
        if (affiche.getText().isEmpty() || nomtxt.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Verifiez les champs!");
            alert.show();
        }
        else{
        try {
            File f = new File(affiche.getText());
            JeuVideo jv = new JeuVideo(Integer.valueOf(idlabel.getText()),nomtxt.getText(),"C:\\Users\\dell\\Desktop\\PI\\Nouveau dossier\\Forum"+f.getName());
            jv.setRating(stars.getRating());
            sjv.modifier(jv);
            
            Files.copy(Paths.get(affiche.getText()),Paths.get("C:\\Users\\dell\\Desktop\\PI\\Nouveau dossier\\Forum"+f.getName()),REPLACE_EXISTING);
            Aff();
            RechercheAV();
        } catch (SQLException ex) {
            Logger.getLogger(VideoGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
       
    }

    @FXML
    private void DonnerJaime(ActionEvent event) throws SQLException {
            
        tableVideoGame.setItems(data);

        ObservableList<JeuVideo> allJV,SingleJV ;
        allJV=tableVideoGame.getItems();
        SingleJV=tableVideoGame.getSelectionModel().getSelectedItems();
        JeuVideo A = SingleJV.get(0);
             
        A.setRating(A.getRating()+1);
             
        sjv.modifier(A);
        Aff();
        RechercheAV();       
        
    }


    @FXML
    private void ExportPDF(ActionEvent event) throws SQLException {
        
          serviceJeuVideo sjv = new serviceJeuVideo();
              
         ObservableList<JeuVideo> list = sjv.getGamesList();
        try {
            OutputStream file = new FileOutputStream(new File("D:\\pdf\\exportfront.pdf"));
            com.itextpdf.text.Document document = new  com.itextpdf.text.Document();
            PdfWriter.getInstance(document, file);
            document.open();

            com.itextpdf.text.Font font = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Paragraph pdfTitle = new Paragraph("Video Games Names List", font);
            pdfTitle.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);

            document.add(pdfTitle);
            document.add(new Chunk("\n"));
            PdfPTable table = new PdfPTable(1);
            table.setHeaderRows(1);

                        
            list.forEach((_item) -> {
                table.addCell(_item.getNameVg());
            });

            document.add(table);

            Notifications notificationBuilder = Notifications.create()
                            .title("NOTIFICATION FORUM").text("PDF EXPORTé")
                            .graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                            .position(Pos.BASELINE_RIGHT)
                            .onAction(new EventHandler<ActionEvent>(){
                                public void handle(ActionEvent event)
                                {
                                    System.out.println("clicked ON");
                                }
                            }
);
                    notificationBuilder.darkStyle();
                    notificationBuilder.show();
            document.close();

            file.close();

        } catch (DocumentException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Cannot export data!");
            alert.show();
        }   
    }

    @FXML
    private void PrintGames(ActionEvent event) {
        
        PrinterJob job = PrinterJob.createPrinterJob();
       
        Node root= this.tableVideoGame;
        
        if(job != null)
        {
            job.showPrintDialog(root.getScene().getWindow()); // Window must be your main Stage
            Printer printer = job.getPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.A3, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
            boolean success = job.printPage(pageLayout, root);
            if(success)
                {
                    job.endJob();
                }
        }
        
        Notifications notificationBuilder = Notifications.create()
                            .title("NOTIFICATION FORUM").text("TABLE IMPRIMée")
                            .graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                            .position(Pos.BASELINE_RIGHT)
                            .onAction(new EventHandler<ActionEvent>(){
                                public void handle(ActionEvent event)
                                {
                                    System.out.println("clicked ON");
                                }
                            }
);
                    notificationBuilder.darkStyle();
                    notificationBuilder.show();
    }

    @FXML
    private void genererQR(ActionEvent event) {
        
        serviceJeuVideo sjv = new serviceJeuVideo();

                if (tableVideoGame.getSelectionModel().getSelectedItem() != null) {
            JeuVideo jv = new JeuVideo();
            jv.setNameVg(sjv.liste().get(tableVideoGame.getSelectionModel().getSelectedIndex()).getNameVg());
            
            Hashtable hints = new Hashtable();
            hints.put(com.google.zxing.EncodeHintType.ERROR_CORRECTION, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel.H);
            com.google.zxing.qrcode.QRCodeWriter writer = new com.google.zxing.qrcode.QRCodeWriter();
            com.google.zxing.common.BitMatrix bitMatrix = null;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                // Create a qr code with the url as content and a size of 250x250 px
                bitMatrix = writer.encode("The Game's name= "+jv.getNameVg(), BarcodeFormat.QR_CODE, 250, 250, hints);
                MatrixToImageConfig config = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);
                // Load QR image
                BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                // Load logo image
                File file = new File("D:\\QR.png");
                BufferedImage logoImage = ImageIO.read(file);
                // Calculate the delta height and width between QR code and logo
                int deltaHeight = qrImage.getHeight() - logoImage.getHeight();
                int deltaWidth = qrImage.getWidth() - logoImage.getWidth();
                // Initialize combined image
                BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = (Graphics2D) combined.getGraphics();
                // Write QR code to new image at position 0/0
                g.drawImage(qrImage, 0, 0, null);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                // Write logo into combine image at position (deltaWidth / 2) and
                // (deltaHeight / 2). Background: Left/Right and Top/Bottom must be
                // the same space for the logo to be centered
                g.drawImage(logoImage, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);
                // Write combined image as PNG to OutputStream
                ImageIO.write(combined, "png", new File("C:\\Users\\dell\\Desktop\\PI\\Nouveau dossier\\QR\\QR.png"));
                //System.out.println("done");
            } catch (Exception ea) {
                System.out.println(ea);
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Choose a row !");
            alert.show();
        }
                
                Notifications notificationBuilder = Notifications.create()
                            .title("NOTIFICATION FORUM").text("QR CODE GENERé")
                            .graphic(null).hideAfter(javafx.util.Duration.seconds(5))
                            .position(Pos.BASELINE_RIGHT)
                            .onAction(new EventHandler<ActionEvent>(){
                                public void handle(ActionEvent event)
                                {
                                    System.out.println("clicked ON");
                                }
                            }
);
                    notificationBuilder.darkStyle();
                    notificationBuilder.show();
    }
    
    public void Stat() throws SQLException{
        
        servicePublication sp = new servicePublication();
                XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Répartition des Publications");
            con = jdbc.getInstance().getCnx();
            ste = con.createStatement();
            ResultSet res = ste.executeQuery("select * from tbl_videogame");
            while(res.next()){
            series.getData().add(new XYChart.Data<>(res.getString(3), sp.calculer(res.getInt(1))));
            }        
        barChart.getData().addAll(series);

    }
}
    


    

   
       
    
        
    



