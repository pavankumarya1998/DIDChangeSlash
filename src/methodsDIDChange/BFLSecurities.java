package methodsDIDChange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BFLSecurities {
	private static List<String> excelFilePaths = new ArrayList<>(); // List to store paths of Excel files
	private static List<String> gatewayNames = new ArrayList<>(); // List to store Gateways
	private static List<Row> rows = new ArrayList<>(); // To store rows from the current Excel file
	private static int currentIndex = 0;
	private static Workbook workbook;

	public static void securitiesScript(WebDriver driver) throws InterruptedException, IOException {

		excelFilePaths.add("C:\\Users\\pavan\\Desktop\\BFLDIDChange\\BFLNPL\\BFL-NPL-TataLEGALPMO.xlsx");
		gatewayNames.add("TataRuralNPL01");

		try {
			driver.findElement(By.name("username")).clear();
			driver.findElement(By.name("username")).sendKeys("slashadmin");
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys("Slash@123");
			driver.findElement(By.id("loginProcess")).click();
			// if you want the console output in the Excel sheet then remove comment for
			// below line
//			saveConsoleOutputToFile("C:\\Users\\pavan\\Desktop\\BFLDIDChange\\BFLNPL\\Consoleoutput\\NPL.txt");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.urlContains("dashboard"));
			driver.findElement(By.xpath("//a[@id='tabAdvanced']/p")).click();
			driver.findElement(By.linkText("Voice Gateways")).click();
			driver.findElement(By.linkText("Config")).click();
			driver.get("https://bflsecurities.slashrtc.in/index.php/site/viewGateWayDetail");

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
			System.out.println("Console output redirected to: " + filePath);
		} catch (IOException e) {
			System.err.println("Error while saving console output: " + e.getMessage());
		}
	}
}
