package com.frameworkium.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A custom element to help when working with HTML tables.
 */
public class Table implements WrapsElement {

    private final WebElement wrappedElement;

    public Table(WebElement wrappedElement) {
        this.wrappedElement = wrappedElement;
    }

    @Override
    public WebElement getWrappedElement() {
        return wrappedElement;
    }

    public boolean isDisplayed() {
        return getWrappedElement().isDisplayed();
    }

    /**
     * Returns table heading elements (contained in "th" tags).
     *
     * @return List with table heading elements.
     */
    public List<WebElement> getHeadings() {
        return getWrappedElement().findElements(By.xpath(".//th"));
    }

    public List getHeadingsAsString() {
        return getHeadings().stream().map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns table cell elements grouped by rows.
     *
     * @return List where each item is a table row.
     */
    public List<List<WebElement>> getRows() {
        List<WebElement> rowElements =
                getWrappedElement().findElements(By.xpath(".//tr"));
        List<List<WebElement>> rows = rowElements.stream()
                .map(rowElement -> rowElement.findElements(By.xpath(".//td")))
                .collect(Collectors.toList());
        return rows;
    }

    public List<List<String>> getRowsAsString() {
        List<List<String>> rows = new ArrayList<>();
        for (List<WebElement> row : getRows()) {
            List<String> currentRow =
                    row.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            rows.add(currentRow);
        }
        return rows;
    }

    /**
     * Returns table cell elements grouped by columns.
     *
     * @return List where each item is a table column.
     */
    public List<List<WebElement>> getColumns() {
        List<List<WebElement>> columns = new ArrayList<>();
        List<List<WebElement>> rows = getRows();

        if (rows.isEmpty()) {
            return columns;
        }

        int columnsNumber = rows.get(0).size();
        for (int i = 0; i < columnsNumber; i++) {
            List<WebElement> column = new ArrayList<>();
            for (List<WebElement> row : rows) {
                column.add(row.get(i));
            }
            columns.add(column);
        }

        return columns;
    }

    public List<String> getColumnAsString(String colHeader) {
        int a = getHeadingsAsString().indexOf(colHeader);

        List<String> columnContents = new ArrayList<>();
        for (int i = 0; i < getRows().size(); i++) {
            if (getRowsAsString().get(i).size() != 0) {
                columnContents.add(getRowsAsString().get(i).get(a));
            }
        }

        return columnContents;
    }

    /**
     * Returns table cell element at i-th row and j-th column.
     *
     * @param i Row number
     * @param j Column number
     * @return Cell element at i-th row and j-th column.
     */
    public WebElement getCellAt(int i, int j) {
        return getRows().get(i).get(j);
    }

    /**
     * Returns list of maps where keys are passed headings and values are table row elements.
     *
     * @param headings List containing strings to be used as table headings.
     */
    public List<Map<String, WebElement>> getRowsMappedToHeadings(List<String> headings) {
        List<Map<String, WebElement>> rowsMappedToHeadings = new ArrayList<>();
        List<List<WebElement>> rows = getRows();

        if (rows.isEmpty()) {
            return rowsMappedToHeadings;
        }

        for (List<WebElement> row : rows) {
            if (row.size() != headings.size()) {
                throw new IllegalStateException(
                        "Heading count is not equal to number of cells in row");
            }

            Map<String, WebElement> rowToHeadingsMap = new HashMap<>();
            int cellNumber = 0;
            for (String heading : headings) {
                rowToHeadingsMap.put(heading, row.get(cellNumber));
                cellNumber++;
            }
            rowsMappedToHeadings.add(rowToHeadingsMap);
        }

        return rowsMappedToHeadings;
    }
}
