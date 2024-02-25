package com.uniovi.notaneitor.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_LoginView extends PO_NavView{
    static public void fillLoginForm(WebDriver driver, String dni,String password) {
        WebElement username = driver.findElement(By.name("username"));
        username.click();
        username.clear();
        username.sendKeys(dni);
        WebElement name = driver.findElement(By.name("password"));
        name.click();
        name.clear();
        name.sendKeys(password);

        //Pulsar el boton de Alta.
        By boton = By.className("btn");
        driver.findElement(boton).click();
    }

    public static void clickLogout(WebDriver driver) {
        By boton = By.xpath("/html/body/nav/div/ul[2]/li[2]/a/span");
        driver.findElement(boton).click();
    }
}
