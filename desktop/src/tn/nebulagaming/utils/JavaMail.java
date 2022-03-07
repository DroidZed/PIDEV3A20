/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.utils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import tn.nebulagaming.config.MailConfig;

/**
 *
 * @author Aymen Dhahri
 */
public class JavaMail {

    private static HashMap<String, String> CONF;
    private static Properties PROPS;
    private static Session SESSION;

    public JavaMail() {

	CONF = MailConfig.getInstance().getConfig();
	PROPS = new Properties();

	PROPS.setProperty("mail.smtp.auth", "true");
	PROPS.setProperty("mail.smtp.starttls.enable", "true");
	PROPS.setProperty("mail.smtp.host", CONF.get("HOST"));
	PROPS.setProperty("mail.smtp.port", CONF.get("PORT"));
	PROPS.setProperty("mail.default-encoding", "UTF-8");

	SESSION = Session.getDefaultInstance(PROPS, new Authenticator() {

	    @Override
	    protected PasswordAuthentication getPasswordAuthentication() {

		return new PasswordAuthentication(CONF.get("EMAIL"), CONF.get("PWD"));
	    }
	});
    }

    public void sendEmailWithAttach(String subject, String to, String body, File f) {

	System.out.println("Preparing to send email...");

	try {

	    MimeMessage attch = prepareMailWithAttach(to, subject, body, f);

	    Transport.send(attch);

	    System.out.println("Message sent successfully !!");

	} catch (MessagingException ex) {
	    System.out.println(ex.getMessage());
	}
    }

    public void sendTextMail(String subject, String to, String body) {

	System.out.println("Preparing to send email...");

	try {

	    MimeMessage textMail = createTextMessage(to, subject, body);

	    Transport.send(textMail);

	    System.out.println("Message sent successfully !!");

	} catch (MessagingException ex) {
	    System.out.println(ex.getMessage());
	}

    }

    private MimeMessage prepareMailWithAttach(String to, String subject, String body, File f) throws AddressException, MessagingException {

	MimeMessage message = prepareMessage(to, subject);

	BodyPart messageBodyPart = new MimeBodyPart();

	Multipart multipart = new MimeMultipart();

	messageBodyPart.setText(body);

	multipart.addBodyPart(messageBodyPart);

	messageBodyPart = new MimeBodyPart();

	DataSource source = new FileDataSource(f.getAbsolutePath());

	System.out.println(f.getAbsolutePath());

	messageBodyPart.setDataHandler(new DataHandler(source));

	messageBodyPart.setFileName(f.getName());

	multipart.addBodyPart(messageBodyPart);

	message.setContent(multipart);

	return message;
    }

    private MimeMessage createTextMessage(String to, String subject, String body) throws MessagingException {

	MimeMessage message = prepareMessage(to, subject);

	message.setSentDate(new Date());

	message.setText(body);

	return message;
    }

    private MimeMessage prepareMessage(String to, String subject) throws AddressException, MessagingException {

	MimeMessage message = new MimeMessage(SESSION);

	message.setFrom(new InternetAddress(CONF.get("EMAIL")));

	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

	message.setSubject(subject);

	return message;
    }
}
