package com.uniovi.notaneitor.pageobjects;
import com.uniovi.notaneitor.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PO_PrivateView extends PO_NavView {
    static public void fillFormAddMark(WebDriver driver, int userOrder, String descriptionp, String scorep)
    {
        //Esperamos 5 segundo a que carge el DOM porque en algunos equipos falla
        SeleniumUtils.waitSeconds(driver, 5);
        //Seleccionamos el alumnos userOrder
        new Select(driver.findElement(By.id("user"))).selectByIndex(userOrder);
        //Rellenemos el campo de descripción
        WebElement description = driver.findElement(By.name("description"));
        description.clear();
        description.sendKeys(descriptionp);
        WebElement score = driver.findElement(By.name("score"));
        score.click();
        score.clear();
        score.sendKeys(scorep);
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    static public void doLogin(WebDriver driver,String dni, String password) {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver,dni,password);
        PO_View.checkElementBy(driver, "text", dni);
    }

    static public void doLogout(WebDriver driver) {
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }

    static public void doCheckMarkList(WebDriver driver,String type,String text,int expected) {
        List<WebElement> marksList = SeleniumUtils.waitLoadElementsBy(driver, type, text,
                PO_View.getTimeout());
        Assertions.assertEquals(expected, marksList.size());
    }

    static public void doCheckViewMarkDetail(WebDriver driver,String type,String expected) {
        List<WebElement> result = PO_View.checkElementBy(driver, type, expected);
        Assertions.assertEquals(expected, result.get(0).getText());
    }

    static public void doClickAddMark(WebDriver driver) {
        //Pinchamos en la opción de menú de Notas: //li[contains(@id, 'marks-menu')]/a
        //List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//li[contains(@id, 'marksmenu')]/a");
        List<WebElement> elements = PO_View.checkElementBy(driver, "free",
                "//*[@id='myNavbar']/ul[1]/li[2]");
        elements.get(0).click();

        //Esperamos a que aparezca la opción de añadir nota: //a[contains(@href, 'mark/add')]
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'mark/add')]");
        //Pinchamos en agregar Nota.
        elements.get(0).click();
    }

    public static void CheckMarkByDescription(WebDriver driver, String checkText,int LastPage) {
        PO_PrivateView.goToPageX(driver,LastPage);
        //Comprobamos que aparece la nota en la página
        List<WebElement>elements = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, elements.get(0).getText());
    }
    public static void goToNoteList(WebDriver driver) {
        // Pinchamos en la opción de menú de Notas
        // List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//li[contains(@id, 'marksmenu')]/a");
        List<WebElement> elements = PO_View.checkElementBy(driver, "free", "//*[@id='myNavbar']/ul[1]/li[2]");
        elements.get(0).click();

        // Pinchamos en la opción de lista de notas.
        elements = PO_View.checkElementBy(driver, "free", "//a[contains(@href, 'mark/list')]");
        elements.get(0).click();
    }
    public static void goToPageX(WebDriver driver,int Page) {
        //Esperamos a que se muestren los enlaces de paginación de la lista de notas
        List<WebElement>elements = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        //Nos vamos a la página
        elements.get(Page).click();
    }

    public static void doDeleteMarkByDescription(WebDriver driver, String description, int page) {
        PO_PrivateView.goToPageX(driver,page);
        List<WebElement>elements = PO_View.checkElementBy(driver, "free", "//td[contains(text(),'"+description+"')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
        elements.get(0).click();
    }
}