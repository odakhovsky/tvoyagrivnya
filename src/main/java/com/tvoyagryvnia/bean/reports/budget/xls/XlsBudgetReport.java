package com.tvoyagryvnia.bean.reports.budget.xls;

import com.tvoyagryvnia.bean.budget.BudgetLineBean;
import com.tvoyagryvnia.bean.budget.FullBudgetBean;
import com.tvoyagryvnia.bean.budget.FullBudgetLineBean;
import com.tvoyagryvnia.util.NumberFormatter;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

public class XlsBudgetReport extends AbstractXlsReport {

    @Override
    protected String getWorkbookName() {
        return "XlsBudgetReport";
    }

    @Override
    protected String getSheetName() {
        return "XlsBudgetReport";
    }

    @Override
    protected String[] getHeaderTitles() {
        return new String[]{"Категорія", "По факту", "Заплановано", "Відхилення"};
    }

    @Override
    protected void setRows(HSSFSheet sheet) {
        FullBudgetBean budget = (FullBudgetBean) model.get("budget");
        sheet.setColumnWidth(2, 1800);  //manually setting the column width

        setExcelHeader(sheet);

        int rownum = 1;
        Row row = sheet.createRow(rownum++);

        //fill incomes
        rownum = fillRow(sheet, budget.getIncomes(), rownum, row);

        //fill total incomes
        row = sheet.createRow(rownum++);
        fillTotal(row, "Підсумок доходів", budget.getIncomesTotal().getFact(), budget.getIncomesTotal().getBudget(), budget.getIncomesTotal().getDiff());

        //fill spendings
        rownum = fillRow(sheet, budget.getSpending(), rownum, row);
        //fill total incomes
        row = sheet.createRow(rownum++);
        fillTotal(row, "Підсумок витрат", budget.getSpendingTotal().getFact(), budget.getSpendingTotal().getBudget(), budget.getSpendingTotal().getDiff());

        //fill grandTotal
        row = sheet.createRow(rownum++);
        fillTotal(row, "Залишок", budget.getGrandTotal().getFact(), budget.getGrandTotal().getBudget(), budget.getSpendingTotal().getDiff());
    }

    private void fillTotal(Row row, String s, float fact, float budget2, float diff) {


        row.createCell(0).setCellValue(s);
        row.createCell(1).setCellValue(fact);
        row.createCell(2).setCellValue(NumberFormatter.cutFloat(budget2, 2));
        row.createCell(3).setCellValue(diff);
    }

    private int fillRow(HSSFSheet sheet, List<FullBudgetLineBean> lines, int rownum, Row row) {
        for (FullBudgetLineBean line : lines) {
            row = sheet.createRow(rownum++);
            fillTotal(row, line.getCategory(), line.getFactMoney(), line.getLine().getMoney(), line.getDiff());
        }
        return rownum;
    }

    public void setExcelHeader(HSSFSheet excelSheet) {
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue("Категорія");
        excelHeader.createCell(1).setCellValue("По факту");
        excelHeader.createCell(2).setCellValue("Заплановано");
        excelHeader.createCell(3).setCellValue("Відхилення");
    }
}
