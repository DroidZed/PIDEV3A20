/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.views;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import net.glxn.qrgen.QRCode;
import tn.nebulagaming.models.JeuVideo;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.Rating;
import tn.nebulagaming.services.ServiceJeuVideo;
import tn.nebulagaming.services.ServicePublication;
import tn.nebulagaming.utils.GlobalConfig;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class VideoGameController implements Initializable {

    private Statement ste;
    private Connection con;
    int ratingJeu = 0;

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

    ServiceJeuVideo sjv = new ServiceJeuVideo();
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
    private TableColumn<JeuVideo, Integer> Likes;
    @FXML
    private Button btnback;

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

    public void Aff() {

	try {
	    con = GlobalConfig.getInstance().getCONNECTION();
	    ste = con.createStatement();
	    data.clear();

	    ResultSet rs = ste.executeQuery("select * from tbl_videogame");
	    while (rs.next()) {
		JeuVideo f = new JeuVideo(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getDouble(6));

		File file = new File(rs.getString(4));
		Image image = new Image(file.toURI().toString());

		ImageView imageView = new ImageView(image);
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

    public void RechercheAV() {
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

		if (evenement.getNameVg().toLowerCase().indexOf(lowerCaseFilter) != -1) {
		    return true; // Filter matches first name.
		} else {
		    return false; // Does not match.
		}
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
	if (affiche.getText().isEmpty() || nomtxt.getText().isEmpty()) {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setContentText("Verifiez les champs!");
	    alert.show();
	} else {
	    File f = new File(affiche.getText());
	    System.out.println(f.getName());
	    System.out.println(nomtxt.getText());
	    JeuVideo jv = new JeuVideo(nomtxt.getText(), "E:\\9raya\\Nebula-Gaming\\desktop\\Images" + f.getName());
	    jv.setRating(stars.getRating());

	    sjv.ajouter(jv);

	    Files.copy(Paths.get(affiche.getText()), Paths.get("E:\\9raya\\Nebula-Gaming\\desktop\\Images" + f.getName()), REPLACE_EXISTING);
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

	ObservableList<JeuVideo> allJV, SingleJV;
	allJV = tableVideoGame.getItems();
	SingleJV = tableVideoGame.getSelectionModel().getSelectedItems();
	JeuVideo A = SingleJV.get(0);
	sjv.supprimer(A);
	SingleJV.forEach(allJV::remove);
	Aff();
	RechercheAV();
    }

    @FXML
    private void clickedColumn(MouseEvent event) {
	tableVideoGame.setItems(data);

	ObservableList<JeuVideo> allJV, SingleJV;
	allJV = tableVideoGame.getItems();
	SingleJV = tableVideoGame.getSelectionModel().getSelectedItems();
	JeuVideo A = SingleJV.get(0);

	nomtxt.setText(A.getNameVg());
	affiche.setText(A.getImageVg());
	idlabel.setText(String.valueOf(A.getId()));
	stars.setRating(A.getRating());
    }

    @FXML
    private void ModifierJeu(ActionEvent event) {
	if (affiche.getText().isEmpty() || nomtxt.getText().isEmpty()) {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setContentText("Verifiez les champs!");
	    alert.show();
	} else {
	    try {
		File f = new File(affiche.getText());
		JeuVideo jv = new JeuVideo(Integer.valueOf(idlabel.getText()), nomtxt.getText(), "E:\\9raya\\Nebula-Gaming\\desktop\\Images" + f.getName());
		jv.setRating(stars.getRating());
		sjv.modifier(jv);
		Files.copy(Paths.get(affiche.getText()), Paths.get("E:\\9raya\\Nebula-Gaming\\desktop\\Images" + f.getName()), REPLACE_EXISTING);
		Aff();
		RechercheAV();
	    } catch (IOException ex) {
		Logger.getLogger(VideoGameController.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}

    }

    @FXML
    private void DonnerJaime(ActionEvent event) throws SQLException {

	tableVideoGame.setItems(data);

	ObservableList<JeuVideo> allJV, SingleJV;
	allJV = tableVideoGame.getItems();
	SingleJV = tableVideoGame.getSelectionModel().getSelectedItems();
	JeuVideo A = SingleJV.get(0);

	A.setLikes(A.getLikes() + 1);

	sjv.modifier(A);
	Aff();
	RechercheAV();

    }

    @FXML
    private void ExportPDF(ActionEvent event) throws SQLException {

	ServiceJeuVideo sjv = new ServiceJeuVideo();

	ObservableList<JeuVideo> list = sjv.getGamesList();
	try {
	    OutputStream file = new FileOutputStream(new File("E:\\9raya\\Nebula-Gaming\\desktop\\Images\\exportfront.pdf"));
	    com.itextpdf.text.Document document = new com.itextpdf.text.Document();
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
		    .onAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
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

	Node root = this.tableVideoGame;

	if (job != null) {
	    job.showPrintDialog(root.getScene().getWindow()); // Window must be your main Stage
	    Printer printer = job.getPrinter();
	    PageLayout pageLayout = printer.createPageLayout(Paper.A3, PageOrientation.PORTRAIT, Printer.MarginType.HARDWARE_MINIMUM);
	    boolean success = job.printPage(pageLayout, root);
	    if (success) {
		job.endJob();
	    }
	}

	Notifications notificationBuilder = Notifications.create()
		.title("NOTIFICATION FORUM").text("TABLE IMPRIMée")
		.graphic(null).hideAfter(javafx.util.Duration.seconds(5))
		.position(Pos.BASELINE_RIGHT)
		.onAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent event) {
			System.out.println("clicked ON");
		    }
		}
		);
	notificationBuilder.darkStyle();
	notificationBuilder.show();
    }

    @FXML
    private void genererQR(ActionEvent event) {

	ServiceJeuVideo sjv = new ServiceJeuVideo();

	if (tableVideoGame.getSelectionModel().getSelectedItem() != null) {
	    JeuVideo jv = new JeuVideo();
	    jv.setNameVg(sjv.liste().get(tableVideoGame.getSelectionModel().getSelectedIndex()).getNameVg());

	    try {

		String projectPath = System.getProperty("user.dir").replace("\\", "/");
		String contenue = "Nom Jeu: " + nomtxt.getText();
		ByteArrayOutputStream out = QRCode.from(contenue).to(net.glxn.qrgen.image.ImageType.JPG).stream();
		File f = new File(projectPath + "\\src\\qr\\" + nomtxt.getText() + ".jpg");
		FileOutputStream fos = new FileOutputStream(f); //creation du fichier de sortie
		fos.write(out.toByteArray()); //ecrire le fichier du sortie converter
		fos.flush(); // creation final

	    } catch (IOException ea) {
		System.out.println(ea);
	    }
	} else {
	    Alert alert = new Alert(Alert.AlertType.WARNING);
	    alert.setContentText("Choose a row !");
	    alert.show();
	}

	Notifications notificationBuilder = Notifications.create()
		.title("NOTIFICATION FORUM").text("QR CODE GENERé")
		.graphic(null).hideAfter(javafx.util.Duration.seconds(5))
		.position(Pos.BASELINE_RIGHT)
		.onAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent event) {
			System.out.println("clicked ON");
		    }
		}
		);
	notificationBuilder.darkStyle();
	notificationBuilder.show();
    }

    public void Stat() throws SQLException {

	ServicePublication sp = new ServicePublication();
	XYChart.Series<String, Number> series = new XYChart.Series<>();
	series.setName("Répartition des Publications");
	con = GlobalConfig.getInstance().getCONNECTION();
	ste = con.createStatement();
	ResultSet res = ste.executeQuery("select * from tbl_videogame");
	while (res.next()) {
	    series.getData().add(new XYChart.Data<>(res.getString(3), sp.calculer(res.getInt(1))));
	}
	barChart.getData().addAll(series);

    }

    @FXML
    private void goBack(ActionEvent event) throws IOException {
	
	FXMLLoader loader = new FXMLLoader(getClass().getResource("./NewsFeed.fxml"));
	Parent root = loader.load();
	Stage window = (Stage) btnback.getScene().getWindow();
	window.setScene(new Scene(root, 1920, 1080));
    
    }
}
