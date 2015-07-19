package com.heroku.theinternet.pages.web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.PageFactory;
import com.frameworkium.pages.internal.Visible;

public class WelcomePage extends BasePage<WelcomePage> {

    @Visible
    @FindBy(linkText = "Basic Auth")
    private WebElement basicAuthLink;

    @Visible
    @FindBy(linkText = "Checkboxes")
    private WebElement checkboxesLink;

    @Visible
    @FindBy(linkText = "Drag and Drop")
    private WebElement dragAndDropLink;

    @Visible
    @FindBy(linkText = "Dropdown")
    private WebElement dropdownLink;

    @Visible
    @FindBy(linkText = "Dynamic Loading")
    private WebElement dynamicLoadingLink;

    @Visible
    @FindBy(linkText = "File Download")
    private WebElement fileDownloadLink;

    @Visible
    @FindBy(linkText = "File Upload")
    private WebElement fileUploadLink;

    @Visible
    @FindBy(linkText = "Form Authentication")
    private WebElement formAuthenticationLink;

    @Visible
    @FindBy(linkText = "Hovers")
    private WebElement hoversLink;

    @Visible
    @FindBy(linkText = "Frames")
    private WebElement framesLink;

    @Visible
    @FindBy(linkText = "JQuery UI Menus")
    private WebElement jqueryUILink;

    @Visible
    @FindBy(linkText = "JavaScript Alerts")
    private WebElement javascriptAlertsLink;

    @Visible
    @FindBy(linkText = "Key Presses")
    private WebElement keyPressesLink;

    @Visible
    @FindBy(linkText = "Secure File Download")
    private WebElement secureFileDownloadLink;

    @Visible
    @FindBy(linkText = "Sortable Data Tables")
    private WebElement sortableDataTablesLink;

    public static WelcomePage open() {
        return PageFactory.newInstance(
                WelcomePage.class, "http://the-internet.herokuapp.com");
    }

    public BasicAuthSuccessPage clickBasicAuth(String username, String password) {
        // For this sort of authentication, Selenium cannot handle the dialog
        // box that appears if you click the link.
        // Instead, we can provide the username and password in the URL:
        return PageFactory.newInstance(BasicAuthSuccessPage.class,
                "http://" + username + ":" + password + "@"
                + basicAuthLink.getAttribute("href").split("http://")[1]);
    }

    public CheckboxesPage clickCheckboxesLink() {
        checkboxesLink.click();
        return PageFactory.newInstance(CheckboxesPage.class);
    }

    public DragAndDropPage clickDragAndDropLink() {
        dragAndDropLink.click();
        return PageFactory.newInstance(DragAndDropPage.class);
    }

    public DropdownPage clickDropdownLink() {
        dropdownLink.click();
        return PageFactory.newInstance(DropdownPage.class);
    }

    public DynamicLoadingPage clickDynamicLoading() {
        dynamicLoadingLink.click();
        return PageFactory.newInstance(DynamicLoadingPage.class);
    }

    public FileDownloadPage clickFileDownloadLink() {
        fileDownloadLink.click();
        return PageFactory.newInstance(FileDownloadPage.class);
    }

    public FileUploadPage clickFileUploadLink() {
        fileUploadLink.click();
        return PageFactory.newInstance(FileUploadPage.class);
    }

    public FormAuthenticationPage clickFormAuthenticationLink() {
        formAuthenticationLink.click();
        return PageFactory.newInstance(FormAuthenticationPage.class);
    }

    public FramesPage clickFramesLink() {
        framesLink.click();
        return PageFactory.newInstance(FramesPage.class);
    }

    public HoversPage clickHoversLink() {
        hoversLink.click();
        return PageFactory.newInstance(HoversPage.class);
    }

    public JQueryUIMenuPage clickJQueryUILink() {
        jqueryUILink.click();
        return PageFactory.newInstance(JQueryUIMenuPage.class);
    }

    public JavaScriptAlertsPage clickjavascriptAlertsLink() {
        javascriptAlertsLink.click();
        return PageFactory.newInstance(JavaScriptAlertsPage.class);
    }

    public KeyPressesPage clickKeyPressesLink() {
        keyPressesLink.click();
        return PageFactory.newInstance(KeyPressesPage.class);
    }

    public SecureFileDownloadPage clickSecureFileDownloadsLink(
            String username, String password) {
        // For this sort of authentication, Selenium cannot handle the dialog
        // box that appears if you click the link.
        // Instead, we can provide the username and password in the URL:
        String url = "http://" + username + ":" + password + "@"
                + secureFileDownloadLink.getAttribute("href").split("http://")[1];
        return PageFactory.newInstance(SecureFileDownloadPage.class, url);
    }

    public SortableDataTablesPage clickSortableDataTablesLink() {
        sortableDataTablesLink.click();
        return PageFactory.newInstance(SortableDataTablesPage.class);
    }
}
