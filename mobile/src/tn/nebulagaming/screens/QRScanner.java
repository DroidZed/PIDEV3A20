/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.screens;

import com.codename1.ext.codescan.CodeScanner;
import com.codename1.ext.codescan.ScanResult;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author ASUS
 */
public class QRScanner extends BaseForm {

    public QRScanner(Resources res) {
	super("QR Code Scanner", BoxLayout.y());

	super.addSideMenu(res);

	Button btnScan = new Button("Scan Qr");

	Label lb1 = new Label("Click the button to scan your code.");

	Container cnt = new Container(BoxLayout.y());

	ImageViewer igV = new ImageViewer();

	btnScan.addActionListener(e -> {

	    CodeScanner.getInstance().scanQRCode(new ScanResult() {

		@Override
		public void scanCompleted(String contents, String formatName, byte[] rawBytes) {
		    //barCode.setText("Bar: " + contents);
		    cnt.addComponent(new Label(contents));
		    cnt.addComponent(new ImageViewer(Image.createImage(rawBytes, 0, rawBytes.length)));
		    cnt.revalidate();
		}

		@Override
		public void scanCanceled() {
		    System.out.println("cancelled");
		}

		@Override
		public void scanError(int errorCode, String message) {
		    System.out.println("err " + message);
		}
	    });
	});

	

	addAll(lb1,btnScan, cnt, igV);
    }

}
