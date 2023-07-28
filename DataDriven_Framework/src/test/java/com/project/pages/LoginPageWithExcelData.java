package com.project.pages;

import com.project.component.GenericMethods;
import com.project.utils.FakerClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;

public class LoginPageWithExcelData extends GenericMethods {

    public LoginPageWithExcelData(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "(//a[text()='Create an Account'])[1]")
    protected WebElement createAnAccountLink;

    @FindBy(id = "firstname")
    protected WebElement firstName;

    @FindBy(id = "lastname")
    protected WebElement lastName;

    @FindBy(id = "email_address")
    protected WebElement email;

    @FindBy(id = "password")
    protected WebElement password;

    @FindBy(id = "password-confirmation")
    protected WebElement confirmPassword;

    @FindBy(xpath = "//button/*[text()='Create an Account']")
    protected WebElement createAnAccountButton;

    @FindBy(xpath = "//h1/*[text()='My Account']")
    protected WebElement positiveLoginIdentifier;

    public void loginFunctionality(Map<Object, Object> map) {
        PageFactory.initElements(getDriver(), this);
        mainPageIdentifier();
        clicksOnTheCreateAnAccountLink();
        enterAccountCreationDetails(map);
        clickCreateAnAccountButton("Create An Account Button");
        myAccountPageIdentifier();
    }

    public void mainPageIdentifier() {
        pageIdentifier(createAnAccountLink, "Main Page");
    }

    public void clicksOnTheCreateAnAccountLink() {
        createAnAccountLink.click();
        pageIdentifier(firstName, "Login Page");
    }

    public void enterAccountCreationDetails(Map<Object, Object> map) {
        String firstName = getExcelValue(map, "FirstName");
        String lastName = getExcelValue(map, "LastName");
        String email = getExcelValue(map, "Email");
        String password = getExcelValue(map, "Password");
        String confirmPassword = getExcelValue(map, "ConfirmPassword");

        typeInto(this.firstName, "First Name", firstName);
        typeInto(this.lastName, "Last Name", lastName);

        if (email.equalsIgnoreCase("random")) {
            typeInto(this.email, "Email", FakerClass.randomEmail());
        } else {
            typeInto(this.email, "Email", email);
        }

        typeInto(this.password, "Password", password);
        typeInto(this.confirmPassword, "Confirm Password", confirmPassword);
    }

    public void clickCreateAnAccountButton(String elementName) {
        clickElement(createAnAccountButton, elementName);
    }

    public void myAccountPageIdentifier() {
        pageIdentifier(positiveLoginIdentifier, "My Account Page");
    }
}