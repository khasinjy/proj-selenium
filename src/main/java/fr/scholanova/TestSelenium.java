package fr.scholanova;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Khasinjy
 * Classe pour tester selenium avec chrome
 */
public class TestSelenium {

    public static WebDriver WEB_DRIVER_CHROME;

    public static void main(String[] args) {

        // Indiquer au système où trouver le pilote Chrome
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\Khasinjy\\Documents\\DAI\\CoursQualité\\chromedriver_win32\\chromedriver.exe");

        // Ouvre le navigateur Chrome
        WEB_DRIVER_CHROME = new ChromeDriver();

        WEB_DRIVER_CHROME.get("http://www.google.com");

        WEB_DRIVER_CHROME.get("http://scholanova-group.org/?page_id=3981");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Fermeture du navigateur et de WebDriver
        WEB_DRIVER_CHROME.close();
        WEB_DRIVER_CHROME.quit();
    }
}
