package nadrabank.controller;

import nadrabank.domain.Asset;
import nadrabank.domain.Lot;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.util.DateFormatConverter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Excel implements Serializable {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
    public Excel() {
    }
    public static SimpleDateFormat getSdf() {
        return sdf;
    }

    public static String loadCreditsByList(List<Lot> lotList, List<Asset> assetList) throws IOException {
        Set<Date> bidDateSet = new TreeSet<>();
        Set<String> exNamesSet = new TreeSet<>();
        Set<Long> lotNumsSet = new TreeSet<>();

        for(Lot lot: lotList){
            if(lot.getBid().getBidDate()!=null)
            bidDateSet.add(lot.getBid().getBidDate());
            exNamesSet.add(lot.getBid().getExchange().getCompanyName());
            lotNumsSet.add(lot.getId());
        }

        String bidDates="";
        for(Date date: bidDateSet){
            bidDates +=", "+sdf.format(date);
        }

        String exNames =exNamesSet.toString();
        String lotNums =lotNumsSet.toString();

     //   File file = new File("alex_nb_web\\src\\main\\resources\\Shablon.xls");
       // System.out.println(file.getAbsolutePath());
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("C:\\projectFiles\\Shablon.xls"));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int shiftCount = assetList.size() + 6;

        sheet.getRow(1).getCell(0).setCellValue("Інформація про активи ПАТ «КБ «НАДРА», що пропонуються на продаж на аукціоні "
                +(bidDates.equals("") ? "" : bidDates.substring(2))+" р. на електронному торговому майданчику "+exNames.substring(1, exNames.length()-1));

        //задаем формат дати
        String excelFormatter = DateFormatConverter.convert(Locale.ENGLISH , "yyyy-MM-dd");
        CellStyle cellStyle = wb.createCellStyle();
        DataFormat poiFormat = wb.createDataFormat();
        cellStyle.setDataFormat(poiFormat.getFormat(excelFormatter));
        //end
        sheet.shiftRows(7, 7, assetList.size() - 1);
        int numRow = 6;
        for (Asset asset : assetList) {
            HSSFRow row = sheet.createRow(numRow);
            numRow++;
            int j = 0;
            while (j < 14) {
                row.createCell(j);
                j++;
            }
            row.getCell(0).setCellValue(asset.getId());
            row.getCell(1).setCellValue(asset.getLot().getId());
            row.getCell(2).setCellValue(asset.getAsset_name());
            row.getCell(3).setCellValue(asset.getInn());
            row.getCell(4).setCellValue(String.valueOf(asset.getRv()));
            row.getCell(5).setCellValue(asset.getEksplDate());
            row.getCell(5).setCellStyle(cellStyle);
            row.getCell(6).setCellValue(String.valueOf(asset.getOriginalPrice()));
            row.getCell(7).setCellValue(String.valueOf(asset.getZb()));
            if(asset.getLot().getBidStage()!=null){
                row.getCell(8).setCellValue(asset.getLot().getBidStage()==null | asset.getLot().getBidStage().equals("Перші торги") ? "1" : asset.getLot().getBidStage().equals("Другі торги") ? "2" : "3");
            }
            row.getCell(9).setCellValue(0);
            if(asset.getRvNoPdv()!=null)
            {row.getCell(10).setCellValue(String.valueOf(asset.getRvNoPdv()));}
            if(asset.getRv()!=null)
            {row.getCell(11).setCellValue(String.valueOf(asset.getRv()));}
            row.getCell(12).setCellValue(asset.getLot().getCountOfParticipants());
            if(asset.getFactPrice()!=null)
            {row.getCell(13).setCellValue(String.valueOf(asset.getFactPrice()));}
        }
        HSSFRow sumRow = sheet.getRow(6+assetList.size());
        sumRow.getCell(4).setCellFormula("SUM(E7:E" + shiftCount + ")");
        sumRow.getCell(6).setCellFormula("SUM(G7:G" + shiftCount + ")");
        sumRow.getCell(7).setCellFormula("SUM(H7:H" + shiftCount + ")");
        //sumRow.getCell(8).setCellFormula("SUM(I7:I" + shiftCount + ")");
        sumRow.getCell(10).setCellFormula("SUM(K7:K" + shiftCount + ")");
        sumRow.getCell(11).setCellFormula("SUM(L7:L" + shiftCount + ")");
        sumRow.getCell(12).setCellFormula("SUM(M7:M" + shiftCount + ")");
        sumRow.getCell(13).setCellFormula("SUM(N7:N" + shiftCount + ")");

        String fileName = "C:\\projectFiles\\"+(bidDates.equals("") ? "" : bidDates.substring(2)) + " Lot N"+lotNums.substring(1, lotNums.length()-1)+ ".xls";;
        OutputStream fileOut = new FileOutputStream(fileName);

        wb.write(fileOut);
        fileOut.close();
        return fileName;
    }
}