package BFLDIDchange;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import methodsDIDChange.BFLCentral;
import methodsDIDChange.BFLCollection;
import methodsDIDChange.BFLCollectionService;
import methodsDIDChange.BFLCreditCard;
import methodsDIDChange.BFLInsurance;
import methodsDIDChange.BFLNPL;
import methodsDIDChange.BFLPLCS;
import methodsDIDChange.BFLRural;
import methodsDIDChange.BFLSalpl;
import methodsDIDChange.BFLSecurities;

public class MainClassDIDCHange {

	public static void main(String[] args) throws Exception {

		ChromeOptions options = new ChromeOptions();

		options.addArguments("use-fake-ui-for-media-stream");
		options.addArguments("--disable-notifications");
		options.addArguments("--remote-allow-origins=*");
		WebDriver driver1 = MainClassDIDCHange.initializeDriver(options,
				"https://bfl-insurance.slashrtc.in/index.php/");
		Thread.sleep(500);
		WebDriver driver2 = MainClassDIDCHange.initializeDriver(options,
				"https://bfl-salpl.slashrtc.in/index.php/login");
		Thread.sleep(500);
		WebDriver driver3 = MainClassDIDCHange.initializeDriver(options, "https://bfl-rural.slashrtc.in/index.php/");
		Thread.sleep(500);
		WebDriver driver4 = MainClassDIDCHange.initializeDriver(options, "https://bfl-central.slashrtc.in/index.php/");
		Thread.sleep(500);
		WebDriver driver5 = MainClassDIDCHange.initializeDriver(options, "https://bfl-plcs.slashrtc.in/index.php");
		Thread.sleep(500);
		WebDriver driver6 = MainClassDIDCHange.initializeDriver(options,
				"https://bfl-creditcard.slashrtc.in/index.php/");
		Thread.sleep(500);
		WebDriver driver7 = MainClassDIDCHange.initializeDriver(options,
				"https://bfl-collection.slashrtc.in/index.php/login");
		Thread.sleep(500);
		WebDriver driver8 = MainClassDIDCHange.initializeDriver(options, "https://bfl-npl.slashrtc.in/index.php/login");
		Thread.sleep(500);
		WebDriver driver9 = MainClassDIDCHange.initializeDriver(options,
				"https://bfl-collection.slashrtc.in/index.php/login");
		Thread.sleep(500);
		BFLInsurance.insuranceScript(driver1);
		Thread.sleep(1000);
		BFLSalpl.SALPLScript(driver2);
		Thread.sleep(1000);
		BFLRural.ruralScript(driver3);
		Thread.sleep(1000);
		BFLCentral.centralScript(driver4);
		Thread.sleep(1000);
		BFLPLCS.PLCSScript(driver5);
		Thread.sleep(1000);
		BFLCreditCard.creditCardScript(driver6);
		Thread.sleep(1000);
		BFLCollectionService.collectionServiceScript(driver7);
		Thread.sleep(1000);
		BFLNPL.NPLScript(driver8);
		Thread.sleep(1000);
		BFLCollection.collectionScript(driver9);
		Thread.sleep(1000);

	}

	public static WebDriver initializeDriver(ChromeOptions options, String url) {
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get(url);
		return driver;
	}

}
