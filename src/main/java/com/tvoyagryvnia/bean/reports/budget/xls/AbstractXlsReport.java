package com.tvoyagryvnia.bean.reports.budget.xls;

import com.tvoyagryvnia.bean.budget.FullBudgetBean;
import com.tvoyagryvnia.util.Transliterator;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;

public abstract class AbstractXlsReport extends AbstractExcelView {

    protected XlsStyle xlsStyle;
    protected Map<String, Object> model;
    protected int rowIndex;

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        this.model = model;
        this.xlsStyle = new XlsStyle(workbook);
        String name = ((FullBudgetBean) model.get("budget")).getName() + " " + getWorkbookName();
        name = Transliterator.transliterate(name).replaceAll(" ", "_");
        this.rowIndex = 0;
        response.setHeader("Content-disposition", String.format("attachment; filename=%s.xls", name));

        HSSFSheet sheet = workbook.createSheet(getSheetName());
        setHeader(sheet);
        setRows(sheet);

        for (int colNum = 0; colNum < sheet.getRow(0).getPhysicalNumberOfCells(); colNum++) {
            sheet.autoSizeColumn(colNum);
        }
    }

    protected void setHeader(HSSFSheet sheet) {
        HSSFRow header = sheet.createRow(rowIndex++);
        HSSFCellStyle style = xlsStyle.getHeaderStyle();
        int cellIndex = 0;

        for (String title : getHeaderTitles()) {
            header.createCell(cellIndex++).setCellValue(title);
        }

        setRowStyle(header, style);
    }

    /**
     * Set style for current row
     *
     * @param row   - current row
     * @param style - style to use
     */
    protected void setRowStyle(HSSFRow row, HSSFCellStyle style) {
        Iterator<Cell> iter = row.cellIterator();
        while (iter.hasNext()) {
            Cell cell = iter.next();
            cell.setCellStyle(style);
        }
    }

    /**
     * Created an empty row with body style
     *
     * @param sheet - current list
     */
    protected void createEmptyRow(HSSFSheet sheet, int columnNumber) {
        HSSFRow row = sheet.createRow(rowIndex++);
        HSSFCellStyle style = xlsStyle.getBodyStile();

        for (int i = 0; i < columnNumber; i++) {
            row.createCell(i, Cell.CELL_TYPE_BLANK);
        }

        setRowStyle(row, style);
    }

    protected abstract String getWorkbookName();

    protected abstract String getSheetName();

    protected abstract String[] getHeaderTitles();

    protected abstract void setRows(HSSFSheet sheet);
}
