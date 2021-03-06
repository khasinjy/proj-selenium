package fr.scholanova;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for tutoselenium
 */
public class Page123SeleniumTest01
{
    public static WebDriver WEB_DRIVER_CHROME;

    /**
     * Initialise le webdriver, l’URL à tester et le timer implicite de 30 s.
     * Il faut lancer au préalable le serveur tomcat avec webapps/tutoselenium.war
     */
    @BeforeClass //s'execute avant tous les tests
    public static void setUp(){
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\Khasinjy\\Documents\\DAI\\CoursQualité\\chromedriver_win32\\chromedriver.exe");
        WEB_DRIVER_CHROME = new ChromeDriver();
        WEB_DRIVER_CHROME.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WEB_DRIVER_CHROME.get("http://127.0.0.1:8080/tutoselenium/");
    }

    @Test
    public void testNavigation()
    {
        testEntete();
        testPageUne();
        testPageDeux();
        testPageTrois();
    }

    /**
     * Vérification de l'en-tête
     */
    public void testEntete()
    {
        WebElement entete = WEB_DRIVER_CHROME.findElement(By.tagName("h1"));
        String s_entete = entete.getText();
        assertEquals("L'en-tete n'est pas celui attendu", "En tête", s_entete);

        WebElement drapeau_anglais = WEB_DRIVER_CHROME.findElement(By.id("headerForm:english_button"));
        drapeau_anglais.click();
        WebElement entete_anglais = WEB_DRIVER_CHROME.findElement(By.tagName("h1"));
        s_entete = entete_anglais.getText();
        assertEquals("La traduction en anglais n'a pas fonctionnée.", "Header", s_entete);

        WebElement drapeau_français = WEB_DRIVER_CHROME.findElement(By.id("headerForm:french_button"));
        drapeau_français.click();
        WebElement entete_francais = WEB_DRIVER_CHROME.findElement(By.tagName("h1"));
        s_entete = entete_francais.getText();
        assertEquals("La traduction en français n'a pas fonctionnée.", "En tête", s_entete);
    }

    /**
     * Vérification de la page une
     */
    public void testPageUne(){
        WebElement input = WEB_DRIVER_CHROME.findElement(By.id("contentForm:pageText"));
        input.sendKeys("6");
        WebElement submit = WEB_DRIVER_CHROME.findElement(By.id("contentForm:nextPage"));
        submit.click();
        WebElement error_message = WEB_DRIVER_CHROME.findElement(By.id("contentForm:pageError"));
        String s_error_message = error_message.getText();
        assertEquals("Le message d'erreur n'est pas affiché.", "Vous devez entrer une valeur entre un et trois.", s_error_message);

        String color_error_message = error_message.getCssValue("color");
        assertEquals("Le message d'erreur n'est pas en rouge.", "rgba(255, 0, 0, 1)", color_error_message);

        input = WEB_DRIVER_CHROME.findElement(By.id("contentForm:pageText"));
        input.sendKeys(Keys.BACK_SPACE + "2");
        WEB_DRIVER_CHROME.findElement(By.id("contentForm:nextPage")).click();
        testTitrePage("deux");
    }

    /**
     * Vérification de la page deux
     */
    public void testPageDeux() {
        allerPage(2);
        List<WebElement> buttons = WEB_DRIVER_CHROME.findElements(By.tagName("button"));
        assertEquals("Il n'y a pas trois boutons sur la page deux.", 3, buttons.size());

        //teste la navigation vers la page suivante
        buttons.get(2).click();
        testTitrePage("trois");
    }

    /**
     * Vérification de la page trois
     */
    public void testPageTrois() {
        allerPage(3);
        List<WebElement> choix_page = WEB_DRIVER_CHROME.findElement(By.id("contentForm:pageList")).findElements(By.tagName("li"));
        assertEquals("Il n'y a pas trois choix dans la liste de pages.", 3, choix_page.size());

        //teste la navigation vers la page suivante
        choix_page.get(0).click();
        WEB_DRIVER_CHROME.findElement(By.id("contentForm:nextPageButton")).click();
        testTitrePage("une");

    }

    /**
     * Permet d'aller à une page précise du site
     * @param numPage
     */
    public void allerPage(int numPage){
        WEB_DRIVER_CHROME.get("http://127.0.0.1:8080/tutoselenium/faces/page" + numPage + ".xhtml");
    }

    /**
     * Vérification du titre de la page
     */
    public void testTitrePage(String numPage){
        String title = WEB_DRIVER_CHROME.findElement(By.id("contentForm:pageTitle")).getText();
        assertEquals("La page attendu n'est pas la page" + numPage +  ".", "Page " + numPage, title);
    }

    /**
     * Quitte le navigateur
     */
    @AfterClass //s'execute après tous les tests
    public static void tearDown() {
        WEB_DRIVER_CHROME.close();
        WEB_DRIVER_CHROME.quit();
    }
}
