package com.tvoyagryvnia.bean.reports.budget.xls;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

public class XlsStyle {
    HSSFWorkbook workbook;

    public XlsStyle(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public HSSFCellStyle getHeaderStyle() {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();

        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());

        setBorder(style);

        return style;
    }

    public HSSFCellStyle getBodyStile() {
        HSSFCellStyle style = workbook.createCellStyle();
        setBorder(style);

        return style;
    }

    public HSSFCellStyle getFormattedBodyStile(String format) {

        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat(format));
        setBorder(style);

        return style;
    }

    public HSSFCellStyle getBoldCell() {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();

        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
        setBorder(style);

        return style;
    }

    private void setBorder(HSSFCellStyle style) {
        style.setBorderBottom(CellStyle.BORDER_HAIR);
        style.setBorderTop(CellStyle.BORDER_HAIR);
        style.setBorderLeft(CellStyle.BORDER_HAIR);
        style.setBorderRight(CellStyle.BORDER_HAIR);
    }

}