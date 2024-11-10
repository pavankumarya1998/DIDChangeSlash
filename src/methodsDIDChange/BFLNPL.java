package methodsDIDChange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BFLNPL {
	private static List<String> excelFilePaths = new ArrayList<>(); // List to store paths of Excel files
	private static List<String> gatewayNames = new ArrayList<>(); // List to store Gateways
	private static List<Row> rows = new ArrayList<>(); // To store rows from the current Excel file
	private static int currentIndex = 0;
	private static Workbook workbook;
	private static String instanceURL = "https://bfl-npl.slashrtc.in";
	public static String TataRuralNPL01 = "C:\\Users\\pavan\\Desktop\\BFLDIDChange\\BFLNPL\\BFL-NPL-TataRuralNPL01.xlsx";
	public static String TataNPLVINAYAK = "C:\\Users\\pavan\\Desktop\\BFLDIDChange\\BFLNPL\\BFL-NPL-TataNPLVINAYAK.xlsx";
	public static String TataNPLQUICKSERVICES = "C:\\Users\\pavan\\Desktop\\BFLDIDChange\\BFLNPL\\BFL-NPL-TataNPLQUICKSERVICES.xlsx";
	public static String TataNPLPMO = "C:\\Users\\pavan\\Desktop\\BFLDIDChange\\BFLNPL\\BFL-NPL-TataNPLPMO.xlsx";
	public static String TataNPLNSB = "C:\\Users\\pavan\\Desktop\\BFLDIDChange\\BFLNPL\\BFL-NPL-TataNPLNSB.xlsx";
	public static String TataNPLMPower = "C:\\Users\\pavan\\Desktop\\BFLDIDChange\\BFLNPL\\BFL-NPL-TataNPLMPower.xlsx";
	public static String TataNPLIMPETUS = "C:\\Users\\pavan\\Desktop\\BFLDIDChange\\BFLNPL\\BFL-NPL-TataNPLIMPETUS.xlsx";
	public static String TataNPLGLOBAL = "C:\\Users\\pavan\\Desktop\\BFLDIDChange\\BFLNPL\\BFL-NPL-TataNPLGLOBAL.xlsx";
	public static String TataNPLALLISWELL = "C:\\Users\\pavan\\Desktop\\BFLDIDChange\\BFLNPL\\BFL-NPL-TataNPLALLISWELL.xlsx";
	public static String TataNPLAKSOL = "C:\\Users\\pavan\\Desktop\\BFLDIDChange\\BFLNPL\\BFL-NPL-TataNPLAKSOL.xlsx";
	public static String TataLEGALPMO = "C:\\Users\\pavan\\Desktop\\BFLDIDChange\\BFLNPL\\BFL-NPL-TataLEGALPMO.xlsx";
	private static String outputfile = "C:\\Users\\pavan\\Desktop\\BFLDIDChange\\BFLNPL\\ConsoleOutput\\ConsoleOutput.txt";

	public static void NPLScript(WebDriver driver) throws InterruptedException, IOException {

		excelFilePaths.add(TataRuralNPL01);
		excelFilePaths.add(TataNPLVINAYAK);
		excelFilePaths.add(TataNPLQUICKSERVICES);
		excelFilePaths.add(TataNPLPMO);
		excelFilePaths.add(TataNPLNSB);
		excelFilePaths.add(TataNPLMPower);
		excelFilePaths.add(TataNPLIMPETUS);
		excelFilePaths.add(TataNPLGLOBAL);
		excelFilePaths.add(TataNPLALLISWELL);
		excelFilePaths.add(TataNPLAKSOL);
		excelFilePaths.add(TataLEGALPMO);

		gatewayNames.add("TataRuralNPL01");
		gatewayNames.add("TataNPLVINAYAK");
		gatewayNames.add("TataNPLQUICKSERVICES");
		gatewayNames.add("TataNPLPMO");
		gatewayNames.add("TataNPLNSB");
		gatewayNames.add("TataNPLMPower");
		gatewayNames.add("TataNPLIMPETUS");
		gatewayNames.add("TataNPLGLOBAL");
		gatewayNames.add("TataNPLALLISWELL");
		gatewayNames.add("TataNPLAKSOL");
		gatewayNames.add("TataLEGALPMO");

		try {
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("pavanAdmin");
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys("Pavan^1998");
			driver.findElement(By.id("loginProcess")).click();
			saveConsoleOutputToFile(outputfile);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.urlContains("dashboard"));
			driver.findElement(By.xpath("//a[@id='tabAdvanced']/p")).click();
			driver.findElement(By.linkText("Voice Gateways")).click();
			driver.findElement(By.linkText("Config")).click();
			driver.get(instanceURL + "/index.php/site/viewGateWayDetail");

			for (int i = 0; i < excelFilePaths.size(); i++) {
				String excelFilePath = excelFilePaths.get(i);
				String gatewayName = gatewayNames.get(i);

				loadDIDFromExcel(excelFilePath);

				Thread.sleep(500);
				performGatewayUpdate(driver, wait, gatewayName, excelFilePath);

				rows.clear(); // Clear the rows after processing each Excel file
				currentIndex = 0; // Reset the index for the next Excel file
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			driver.quit();
			BFLNPL.Emailsend();
		}
	}

	// Method to load numbers from an Excel file and store the rows
	private static void loadDIDFromExcel(String filePath) throws IOException {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(new File(filePath));
			workbook = new XSSFWorkbook(fileInputStream);
			Sheet sheet = workbook.getSheetAt(0);

			for (Row row : sheet) {
				Cell cell = row.getCell(0); // Get the first column (index 0)

				// Check if the cell contains text (number stored as a string)
				if (cell != null && cell.getCellType() == CellType.STRING) {
					rows.add(row); // Add row to the list
				}
			}
			Collections.shuffle(rows); // Shuffle the rows to ensure random order

		} catch (IOException e) {
			System.out.println("Error reading Excel file: " + e.getMessage());
			throw e;
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}
		}
	}

	// Method to update each gateway with caller ID from the Excel file

	private static void performGatewayUpdate(WebDriver driver, WebDriverWait wait, String gatewayName,
			String excelFilePath) throws IOException, InterruptedException {

		// Search for the gateway
		WebElement searchInput = driver.findElement(By.xpath("//input[@type='search']"));
		searchInput.clear();
		Thread.sleep(1000);
		searchInput.sendKeys(gatewayName); // Use the gateway name dynamically

		// Click on the edit button
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[@class='btn btn-primary btn-xs editGateway']")));
		driver.findElement(By.xpath("//button[@class='btn btn-primary btn-xs editGateway']")).click();

		// Wait for the edit page to load
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editGatewayCallerId")));

		// Update the gateway with a DID number from Excel
		if (currentIndex < rows.size()) {
			Row currentRow = rows.get(currentIndex);
			Cell numberCell = currentRow.getCell(0);

			// Check if the cell contains text (number stored as a string)
			String callerId;
			if (numberCell.getCellType() == CellType.STRING) {
				callerId = numberCell.getStringCellValue(); // Get the phone number as text
			} else if (numberCell.getCellType() == CellType.NUMERIC) {
				// If by chance, it's stored as a numeric value
				callerId = new BigDecimal(numberCell.getNumericCellValue()).toString();
			} else {
				throw new IllegalArgumentException("Unsupported cell type in phone number column!");
			}

			String currentUrl = driver.getCurrentUrl();
			Thread.sleep(500);
			System.out.println(
					"Picked Caller ID: " + callerId + " for Gateway: " + gatewayName + " for Instance: " + currentUrl);

			WebElement callerIdField = driver.findElement(By.id("editGatewayCallerId"));
			callerIdField.clear();
			callerIdField.sendKeys(callerId);

			// Log the current date and time
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
			String formattedDateTime = now.format(formatter);

			// Write the current date and time to the Excel file in the second column
			Cell statusCell = currentRow.createCell(1, CellType.STRING);
			statusCell.setCellValue(formattedDateTime);

			// Write the changes back to the Excel file
			updateExcelFile(excelFilePath);

			currentIndex++;
		} else {
			System.out.println("No more numbers available for " + gatewayName);
		}

		driver.findElement(By.id("editGateWayDetail")).click();
	}

	private static void updateExcelFile(String filePath) throws IOException {
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(new File(filePath));
			workbook.write(fileOutputStream);
			System.out.println("Excel file updated successfully!");
		} catch (IOException e) {
			System.out.println("Error updating Excel file: " + e.getMessage());
			throw e;
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.close();
			}
		}
	}

	public static void saveConsoleOutputToFile(String filePath) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream(filePath));
			System.setOut(out);
		} catch (IOException e) {
			System.err.println("Error while saving console output: " + e.getMessage());
		}
	}

	// Auto Email Sent
	public static void Emailsend() throws FileNotFoundException {

		final String fromEmail = "notify@slashrtc.com";
		final String username = "smtp37389287";
		final String password = "WA6mJCnkbY";
		final String host = "master.sendclean.net";
		final String port = "587";
		final boolean secure = false;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateTime = sdf.format(new Date());

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", String.valueOf(secure)); // Using 'false' or 'true' for secure
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			// Set multiple recipients in "To"
			String[] toEmails = { "pavan.y@slashrtc.com", "mohammad.abbas@slashrtc.com", };
			for (String email : toEmails) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			}
			// Set multiple recipients in "CC"
			String[] ccEmails = { "deepesh.bhoir@slashrtc.com", "amir.shaikh@slashrtc.com" };
			for (String email : ccEmails) {
				message.addRecipient(Message.RecipientType.CC, new InternetAddress(email));
			}

			String subject = "DID Change for " + instanceURL + " " + currentDateTime;
			message.setSubject(subject);

			Multipart multipart = new MimeMultipart();
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(
					"Dear Team, \nI hope this message finds you well.. \nWe wanted to inform you that the DID for your "
							+ "'" + instanceURL + "'"
							+ " has been changed.\nPlease find the details in the attached file for your reference.\n\nShould you have any questions or need further clarification, feel free to reach out.\nHave a Great Day !.\n\nBest regards,\nSlashRTC Software Services Pvt Ltd");

			multipart.addBodyPart(messageBodyPart);

			String[] xmlFiles = { TataRuralNPL01, TataNPLVINAYAK, TataNPLQUICKSERVICES, TataNPLPMO, TataNPLNSB,
					TataNPLMPower, TataNPLIMPETUS, TataNPLGLOBAL, TataNPLALLISWELL, TataNPLAKSOL, TataLEGALPMO };

			// Attach each XML file manually
			for (String xmlFilePath : xmlFiles) {
				MimeBodyPart attachmentPart = new MimeBodyPart();
				DataSource source = new FileDataSource(new File(xmlFilePath));
				attachmentPart.setDataHandler(new DataHandler(source));
				attachmentPart.setFileName(new File(xmlFilePath).getName()); // Extract the file name from the path
				multipart.addBodyPart(attachmentPart);
			}

			// Attach 1 DOC file

			MimeBodyPart docAttachment = new MimeBodyPart();
			String docFilePath = outputfile;
			DataSource docSource = new FileDataSource(new File(docFilePath));
			docAttachment.setDataHandler(new DataHandler(docSource));
			docAttachment.setFileName("Document.docx"); // Set the DOC file name
			multipart.addBodyPart(docAttachment);
			message.setContent(multipart); // <-- THIS LINE IS IMPORTANT
			Transport.send(message);
			System.out.println("Email sent successfully with attachment");

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
