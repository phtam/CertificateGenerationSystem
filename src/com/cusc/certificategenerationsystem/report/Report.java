/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cusc.certificategenerationsystem.report;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author PC_HP
 */
public class Report {
    public static void ExportExcel(JTable tbl) {

    JFileChooser save = new JFileChooser();
    save.setDialogTitle("Save as");
    save.setFileFilter(new FileNameExtensionFilter("xlsx", "xls", "xlsm"));
    int choose = save.showSaveDialog(null);

    if(choose == JFileChooser.APPROVE_OPTION) {
        XSSFWorkbook export = new XSSFWorkbook();
        XSSFSheet sheet1 = export.createSheet("new file");
        try{
            TableModel tableModel = tbl.getModel();

            for(int i=0; i<tableModel.getRowCount(); i++) {
                XSSFRow newRow = sheet1.createRow(i);
                for(int j=0; j<tableModel.getColumnCount(); j++) {
                    XSSFCell newCell = newRow.createCell((short) j);
                    if(i==tableModel.getRowCount()){
                        XSSFCellStyle style = export.createCellStyle();
                        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                        style.setBorderBottom(BorderStyle.THIN);
                        style.setBorderTop(BorderStyle.THIN);
                        style.setBorderLeft(BorderStyle.THIN);
                        style.setBorderRight(BorderStyle.THIN);
                        newCell.setCellStyle(style);
                        newCell.setCellValue(tableModel.getColumnName(j));
                    } else {
                        XSSFCellStyle style = export.createCellStyle();
                        style.setBorderBottom(BorderStyle.THIN);
                        style.setBorderTop(BorderStyle.THIN);
                        style.setBorderLeft(BorderStyle.THIN);
                        style.setBorderRight(BorderStyle.THIN);
                        newCell.setCellStyle(style);
                    newCell.setCellValue(tableModel.getValueAt(i, j).toString());
                    }
                }
            }

            FileOutputStream otp = new FileOutputStream(save.getSelectedFile()+".xlsx");
            BufferedOutputStream bos = new BufferedOutputStream(otp);
            export.write(bos);
            bos.close();
            otp.close();

            JOptionPane.showMessageDialog(null, "Create successful report!");
        }catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Create failure report!");
        }
    }
}
    
    
}
