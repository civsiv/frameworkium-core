package com.heroku.theinternet.pages.web;

import java.util.List;

import com.frameworkium.elements.Table;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.frameworkium.pages.internal.BasePage;
import com.frameworkium.pages.internal.Visible;

public class SortableDataTablesPage extends BasePage<SortableDataTablesPage> {

    @Visible
    @FindBy(css = "div.example h3")
    private WebElement heading;

    @Visible
    @FindBy(css = "table#table1")
    private WebElement table1;

    @Visible
    @FindBy(css = "table#table2")
    private WebElement table2;

    public List<String> getTable1ColumnContents(String colHeader) {
        return table(table1).getColumnAsString(colHeader);
    }
    
    public List<String> getTable2ColumnContents(String colHeader) {
        return table(table2).getColumnAsString(colHeader);
    }
    
    public SortableDataTablesPage sortTable2ByColumnName(String colHeader) {
        sortTableByColumnName(table(table2), colHeader);
        return this;
    }
    
    private void sortTableByColumnName(Table table, String colHeader) {
        int index = table.getHeadingsAsString().indexOf(colHeader);
        table.getHeadings().get(index).click();
    }

    private Table table(WebElement tableWebElement) {
        return new Table(tableWebElement);
    }
}
