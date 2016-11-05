package nadrabank.controller;

import nadrabank.domain.*;
import nadrabank.service.*;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.util.DateFormatConverter;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes({"userId", "lotRid", "exRid", "objIdToDownload", "docName", "docType", "reportPath", "assetPortionNum"})
public class AssetController {

    @Autowired
    private CreditService creditService;
    @Autowired
    private LotService lotService;
    @Autowired
    private UserService userService;
    @Autowired
    private ExchangeService exchangeService;
    @Autowired
    private BidService bidService;
    @Autowired
    private AssetService assetService;
    @Autowired
    private PayService payService;

    private static final List<String> statusList = Arrays.asList("Новий лот", "Опубліковано", "Оформлення угоди", "Угода укладена");
    private static final List<String> bidStatusList = Arrays.asList("Перші торги", "Другі торги", "Треті торги", "Четверті торги", "П'яті торги");
    private static final List<String> bidResultList = Arrays.asList("", "Торги відбулись", "Торги не відбулись");
    private static final List<String> fondDecisionsList = Arrays.asList("", "Відправлено до ФГВФО", "Повторно відправлено до ФГВФО", "ВД ФГВФО", "Комітет ФГВФО");
    private static final SimpleDateFormat sdfshort = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private static final SimpleDateFormat sdfpoints = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
    private static final SimpleDateFormat yearMonthFormat = new SimpleDateFormat("MM/yyyy", Locale.ENGLISH);
    private static final String documentsPath = "C:\\SCAN\\DocumentsByLots\\";
    private static final String bidDocumentsPath = "C:\\SCAN\\DocumentsByBid\\";

    private boolean isAuth(HttpSession session) {
        Locale.setDefault(Locale.ENGLISH);
        return session.getAttribute("userId") != null;
    }

    private BigDecimal getCoefficient(BigDecimal divident, BigDecimal divisor) {
        return divident.divide(divisor, 10, BigDecimal.ROUND_HALF_UP);
    }

    private String makeDodatok(List<Asset> assetList, List<Credit> creditList, String startDate, String endDate) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("C:\\projectFiles\\Table prodaj.xls"));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        //int shiftCount = assetList.size() + 6;

        sheet.getRow(1).getCell(3).setCellValue("Звіт щодо реалізації активів Банку ПАТ \"КБ \"НАДРА\" за період з " + startDate + " по " + endDate);

        //задаем формат даты
        String excelFormatter = DateFormatConverter.convert(Locale.ENGLISH, "yyyy-MM-dd");
        CellStyle cellStyle = wb.createCellStyle();
        CellStyle numStyle = wb.createCellStyle();

        numStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0.00"));

        DataFormat poiFormat = wb.createDataFormat();
        cellStyle.setDataFormat(poiFormat.getFormat(excelFormatter));
        //end
        sheet.shiftRows(8, 8, assetList.size()+creditList.size() - 1);
        int numRow = 7;
        int i = 0;
        for (Asset asset : assetList) {
            HSSFRow row = sheet.createRow(numRow);
            i++;
            numRow++;
            int j = 0;
            while (j < 65) {
                row.createCell(j);
                j++;
            }
            Lot lot = asset.getLot();
            Bid bid = lot.getBid();
            BigDecimal coeffRV = getCoefficient(asset.getRv(), lotService.lotSum(lot));// asset.getRv().divide(lotService.lotSum(lot), 10, BigDecimal.ROUND_HALF_UP);
            //
            row.getCell(0).setCellValue(i);
            row.getCell(1).setCellValue(380764);
            if (asset.getFondDecisionDate() != null) {
                row.getCell(2).setCellValue(asset.getFondDecisionDate());
                row.getCell(2).setCellStyle(cellStyle);
            }
            if (asset.getLot() != null) {
                row.getCell(3).setCellValue(asset.getLot().getLotNum());
            }
            row.getCell(4).setCellValue("AU");
            row.getCell(5).setCellValue(asset.getAssetGroupCode());
            row.getCell(6).setCellValue(asset.getInn());
            row.getCell(7).setCellValue(asset.getAsset_name());
            row.getCell(8).setCellValue(asset.getAsset_descr());
            if (asset.getEksplDate() != null) {
                row.getCell(9).setCellValue(asset.getEksplDate());
                row.getCell(9).setCellStyle(cellStyle);
            }
            row.getCell(10).setCellValue(asset.getOriginalPrice().doubleValue());
            if(asset.getZb()!=null)
            row.getCell(11).setCellValue(asset.getZb().doubleValue());
            row.getCell(12).setCellValue(asset.getRv().doubleValue());
            if (lot.getFirstStartPrice() != null)
                row.getCell(13).setCellValue(lot.getFirstStartPrice().multiply(coeffRV).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());// Початкова ціна реалізації активу, з ПДВ, грн.
            row.getCell(43).setCellValue(bid.getExchange().getCompanyName());
            row.getCell(44).setCellValue(bid.getExchange().getInn());
            row.getCell(45).setCellValue(bid.getBidDate());
            row.getCell(45).setCellStyle(cellStyle);
            row.getCell(46).setCellValue(asset.getLot().getCountOfParticipants());
            row.getCell(48).setCellValue(asset.getLot().getBidStage());

            BigDecimal lotStartPrice = lot.getStartPrice();
            BigDecimal lotFirstStartPrice = lot.getFirstStartPrice();

            if (lot.getFirstStartPrice() != null && lot.getStartPrice() != null)
                row.getCell(49).setCellValue((1 - (lotStartPrice.divide(lotFirstStartPrice, 4, BigDecimal.ROUND_HALF_UP)).doubleValue()) * 100);//Зниження початкової ціни реалізації активу

            if (lot.getStartPrice() != null) {
                BigDecimal assetStartPrive = lot.getStartPrice().multiply(coeffRV).setScale(10, BigDecimal.ROUND_HALF_UP);
                row.getCell(50).setCellValue(assetStartPrive.divide(new BigDecimal(6), 4).multiply(new BigDecimal(5)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()); //Початкова ціна реалізації активу на актуальном аукціоні без ПДВ, грн.
                row.getCell(50).setCellStyle(numStyle);
            }
            if (lot.getStartPrice() != null)
                row.getCell(51).setCellValue(lot.getStartPrice().multiply(coeffRV).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()); //Початкова ціна реалізації активу на актуальном аукціоні з ПДВ, грн.
            if (lot.getActSignedDate() != null) {
                row.getCell(52).setCellValue(lot.getActSignedDate());
                row.getCell(52).setCellStyle(cellStyle);
            }
            if (asset.getFactPrice() != null)
                row.getCell(53).setCellValue(asset.getFactPrice().doubleValue());
            if (payService.getLastDateByBid(lot.getId()) != null) {
                row.getCell(54).setCellValue(payService.getLastDateByBid(lot.getId()));
                row.getCell(54).setCellStyle(cellStyle);
            }
            if (payService.getLastDateByCustomer(lot.getId()) != null) {
                row.getCell(55).setCellValue(payService.getLastDateByCustomer(lot.getId()));
                row.getCell(55).setCellStyle(cellStyle);
            }
            if (asset.getPaysCustomer() != null) {
                row.getCell(56).setCellValue(asset.getPaysCustomer().doubleValue());
                row.getCell(56).setCellStyle(numStyle);
            }
            if (asset.getPaysBid() != null) {
                row.getCell(57).setCellValue(asset.getPaysBid().doubleValue());
                row.getCell(57).setCellStyle(numStyle);
            }
        }
        for (Credit credit : creditList) {
            HSSFRow row = sheet.createRow(numRow);
            i++;
            numRow++;
            int j = 0;
            while (j < 65) {
                row.createCell(j);
                j++;
            }
            Lot lot = lotService.getLot(credit.getLot());
            Bid bid = lot.getBid();
            BigDecimal coeffRV = getCoefficient(credit.getRv(), lotService.lotSum(lot));// asset.getRv().divide(lotService.lotSum(lot), 10, BigDecimal.ROUND_HALF_UP);

            row.getCell(0).setCellValue(i);
            row.getCell(1).setCellValue(380764);
            if (credit.getFondDecisionDate() != null) {
                row.getCell(2).setCellValue(credit.getFondDecisionDate());
                row.getCell(2).setCellStyle(cellStyle);
            }
            if (credit.getLot() != null) {
                row.getCell(3).setCellValue(lot.getLotNum());
            }
            row.getCell(4).setCellValue("AU");
            row.getCell(5).setCellValue(credit.getAssetGroupCode());
            row.getCell(14).setCellValue(credit.getFio());
            row.getCell(15).setCellValue(credit.getInn());
            row.getCell(16).setCellValue(credit.getContractNum());
            if (credit.getContractStart() != null) {
                row.getCell(17).setCellValue(credit.getContractStart());
                row.getCell(17).setCellStyle(cellStyle);
            }
            if (credit.getContractStart() != null) {
                row.getCell(18).setCellValue(credit.getContractEnd());
                row.getCell(18).setCellStyle(cellStyle);
            }

            row.getCell(19).setCellValue(credit.getCurr());
            row.getCell(20).setCellValue(credit.getRv().doubleValue());
            if (lot.getFirstStartPrice() != null)
            row.getCell(21).setCellValue(lot.getFirstStartPrice().multiply(coeffRV).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            row.getCell(22).setCellValue(credit.getGageVid());
            row.getCell(25).setCellValue(credit.getZb().doubleValue());

            row.getCell(43).setCellValue(bid.getExchange().getCompanyName());
            row.getCell(44).setCellValue(bid.getExchange().getInn());
            row.getCell(45).setCellValue(bid.getBidDate());
            row.getCell(45).setCellStyle(cellStyle);

            row.getCell(46).setCellValue(lot.getCountOfParticipants());
            row.getCell(48).setCellValue(lot.getBidStage());

            BigDecimal lotStartPrice = lot.getStartPrice();
            BigDecimal lotFirstStartPrice = lot.getFirstStartPrice();

            if (lot.getFirstStartPrice() != null && lot.getStartPrice() != null)
                row.getCell(49).setCellValue((1 - (lotStartPrice.divide(lotFirstStartPrice, 4, BigDecimal.ROUND_HALF_UP)).doubleValue()) * 100);//Зниження початкової ціни реалізації активу

            if (lot.getStartPrice() != null) {
                row.getCell(50).setCellValue(lot.getStartPrice().multiply(coeffRV).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()); //Початкова ціна реалізації активу на актуальном аукціоні без ПДВ, грн.
                row.getCell(51).setCellValue(lot.getStartPrice().multiply(coeffRV).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            }
            if (lot.getActSignedDate() != null) {
                row.getCell(52).setCellValue(lot.getActSignedDate());
                row.getCell(52).setCellStyle(cellStyle);
            }
            if (credit.getFactPrice() != null)
                row.getCell(53).setCellValue(credit.getFactPrice().doubleValue());
            if (payService.getLastDateByBid(lot.getId()) != null) {
                row.getCell(54).setCellValue(payService.getLastDateByBid(lot.getId()));
                row.getCell(54).setCellStyle(cellStyle);
            }
            if (payService.getLastDateByCustomer(lot.getId()) != null) {
                row.getCell(55).setCellValue(payService.getLastDateByCustomer(lot.getId()));
                row.getCell(55).setCellStyle(cellStyle);
            }
            if (credit.getPaysCustomer() != null) {
                row.getCell(56).setCellValue(credit.getPaysCustomer().doubleValue());
                row.getCell(56).setCellStyle(numStyle);
            }
            if (credit.getPaysBid() != null) {
                row.getCell(57).setCellValue(credit.getPaysBid().doubleValue());
                row.getCell(57).setCellStyle(numStyle);
            }
        }

        int tableEnd = 7 + assetList.size()+creditList.size();
        HSSFRow sumRow = sheet.getRow(tableEnd);
        sumRow.getCell(10).setCellFormula("SUM(K8:K" + tableEnd + ")");
        sumRow.getCell(11).setCellFormula("SUM(L8:L" + tableEnd + ")");
        sumRow.getCell(12).setCellFormula("SUM(M8:M" + tableEnd + ")");
        sumRow.getCell(13).setCellFormula("SUM(N8:N" + tableEnd + ")");
        sumRow.getCell(50).setCellFormula("SUM(AY8:AY" + tableEnd + ")");
        sumRow.getCell(51).setCellFormula("SUM(AZ8:AZ" + tableEnd + ")");
        sumRow.getCell(53).setCellFormula("SUM(BB8:BB" + tableEnd + ")");
        sumRow.getCell(56).setCellFormula("SUM(BE8:BE" + tableEnd + ")");
        sumRow.getCell(57).setCellFormula("SUM(BF8:BF" + tableEnd + ")");

        String fileName = "C:\\projectFiles\\" + ("Table prodaj " + startDate + " по " + endDate + ".xls");
        OutputStream fileOut = new FileOutputStream(fileName);

        wb.write(fileOut);
        fileOut.close();
        return fileName;
    }

    public String makeOgoloshennya(Long bidId) throws IOException {

        Bid bid = bidService.getBid(bidId);
        List<Lot> lotsByBidList = lotService.getLotsByBid(bid);
        List<Asset> assetList = bidService.getAssetsByBid(bid);
        Set<String> decisionsSet = new TreeSet<>();
        for (Asset as : assetList) {
            decisionsSet.add(as.getDecisionNumber() + " від " + sdfpoints.format(as.getFondDecisionDate()));
        }

        InputStream fs = new FileInputStream("C:\\\\projectFiles\\\\Dodatok 2.docx");

        XWPFDocument docx = new XWPFDocument(fs);
        List<XWPFTable> tableList = docx.getTables();

        String lotNums = "";
        for (Lot lot : lotsByBidList) {
            lotNums += "№ " + lot.getLotNum() + ", ";
        }
        lotNums = lotNums.substring(0, lotNums.length() - 2);

        for (XWPFParagraph p : docx.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains("nlots")) {
                        text = text.replace("nlots", lotNums);
                        r.setText(text, 0);
                    } else if (text != null && text.contains("exchange")) {
                        text = text.replace("exchange", bid.getExchange().getCompanyName());
                        r.setText(text, 0);
                    } else if (text != null && text.contains("webSite") && bid.getExchange().getEmail() != null) {
                        text = text.replace("webSite", bid.getExchange().getEmail());
                        r.setText(text, 0);
                    }
                }
            }
        }
        //Заполнение таблиц
        XWPFTable tab1 = tableList.get(0);
        XWPFTable tab2 = tableList.get(1);
        XWPFTable tab3 = tableList.get(2);

        //Таблица 1 заполнение
        if (bid.getExchange().getReq() != null) {
            tab1.getRow(7).getCell(1).setText(bid.getExchange().getReq());
        }
        for (int i = 0; i < lotsByBidList.size(); i++) {
            String assetName = "";
            String assetDesc = "";
            Lot lot = lotsByBidList.get(i);

            List<Asset> notTMCList = lotService.getNotTMCAssetsByLot(lot);
            List<Asset> TMCList = lotService.getTMCAssetsByLot(lot);
            for (Asset asset : notTMCList) {
                assetName += asset.getAsset_name() + " ";
                assetDesc += asset.getAsset_descr() + " ";
            }
            if (TMCList.size() > 0) {
                assetDesc += " +" + TMCList.size() + " од. ТМЦ";
            }
            XWPFTableRow row = tab1.getRow(i + 1);
            row.getCell(0).setText(lot.getLotNum());
            row.getCell(1).setText(assetName);
            row.getCell(2).setText(assetDesc);
            row.getCell(3).setText(String.valueOf(lot.getStartPrice()));

            if ((lotsByBidList.size() - i) > 1)
                tab1.createRow();

            //частичное заполнение табл2
            if (lot.getBidStage().equals("Перші торги")) {
                tab2.getRow(0).getCell(1).setText(lot.getLotNum() + " - Вперше; ");
                tab3.getRow(0).getCell(1).setText(lot.getLotNum() + " - Вперше; ");
            } else {
                tab2.getRow(0).getCell(1).setText(lot.getLotNum() + " - Повторно;  ");
                tab3.getRow(0).getCell(1).setText(lot.getLotNum() + " - Повторно; ");
            }
            //частичное заполнение табл3
            tab3.getRow(1).getCell(1).setText(assetDesc + "; ");
        }

        //Таблица 2 заполнение
        for (String st : decisionsSet) {
            // XWPFTableRow tab2row2 = tab2.getRow(1);
            tab2.getRow(1).getCell(1).setText("Рішення № " + st + "; ");
        }
        tab2.getRow(2).getCell(1).setText(bid.getExchange().getCompanyName() + ", " + bid.getExchange().getPostAddress() + ", працює щоденно крім вихідних з 09.00 до 17.00, www.aukzion.com.ua");

        tab2.getRow(12).getCell(1).setText(String.valueOf(sdfpoints.format(bid.getBidDate())) + " року");
        tab2.getRow(14).getCell(1).setText(String.valueOf(bid.getExchange().getEmail()));

        if (bid.getRegistrEndDate() != null) {
            tab2.getRow(16).getCell(1).setText(String.valueOf("до 17 год. 00 хв. " + sdfpoints.format(bid.getRegistrEndDate())) + " року");
            tab2.getRow(17).getCell(1).setText(String.valueOf("до 17 год. 00 хв. " + sdfpoints.format(bid.getRegistrEndDate())) + " року; ");
            tab2.getRow(17).getCell(1).setText(String.valueOf("до 17 год. 00 хв. " + sdfpoints.format(bid.getRegistrEndDate())) + " року");
        }

        //Таблица 3 заполнение
        tab3.getRow(3).getCell(1).setText(String.valueOf(sdfpoints.format(bid.getBidDate())) + " р.");

        String fileName = "C:\\projectFiles\\Dodatok 2 (" + String.valueOf(sdfshort.format(bid.getBidDate())) + ").docx";
        OutputStream fileOut = new FileOutputStream(fileName);

        docx.write(fileOut);
        fileOut.close();
        return fileName;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    private String main(HttpSession session) {
        Locale.setDefault(Locale.ENGLISH);
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            return "Menu";
        }
    }

    @RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.HEAD})
    private String index(HttpSession session) {
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            return "Menu";
        }
    }

    @RequestMapping(value = "/reports", method = {RequestMethod.GET, RequestMethod.HEAD})
    private String reports(HttpSession session) {
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            return "Reports";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private @ResponseBody
    String logCheck(@RequestParam("login") String login,
                    @RequestParam("password") String password,
                    Model uID) {
        if (userService.isExist(login, password)) {
            uID.addAttribute("userId", login);
            return "1";
        } else {
            return "0";
        }
    }

    @RequestMapping(value = "/lotList", method = RequestMethod.GET)
    private
    @ResponseBody
    List<Lot> getLots() {
        return lotService.getLots();
    }

    @RequestMapping(value = "/exchanges", method = RequestMethod.GET)
    private
    @ResponseBody
    List<Exchange> jsonGetExchanges() {
        return exchangeService.getAllExchanges();
    }

    @RequestMapping(value = "/setRlot", method = RequestMethod.GET)
    private
    @ResponseBody
    String toLotRedactor(@RequestParam("lotID") String lotId, Model model) {
        model.addAttribute("lotRid", lotId);
        return "1";
    }

    @RequestMapping(value = "/setRex", method = RequestMethod.GET)
    private
    @ResponseBody
    String setRex(@RequestParam("exId") String exId, Model model) {
        model.addAttribute("exRid", exId);
        return "1";
    }

    @RequestMapping(value = "/lotDel", method = RequestMethod.POST)
    private
    @ResponseBody
    String deleteLot(@RequestParam("lotID") String lotId) {
        boolean isitDel = lotService.delLot(Long.parseLong(lotId));
        if (isitDel)
            return "1";
        else
            return "0";
    }

    @RequestMapping(value = "/setLotSold", method = RequestMethod.POST)
    private
    @ResponseBody
    String setLotSold(HttpSession session, @RequestParam("lotID") String lotId) {
        String login = (String) session.getAttribute("userId");
        Lot lot = lotService.getLot(Long.parseLong(lotId));
        lot.setItSold(true);
        boolean isitUpdated = lotService.updateLot(login, lot);
        if (isitUpdated)
            return "1";
        else
            return "0";
    }

    @RequestMapping(value = "/statusChanger", method = RequestMethod.POST)
    private
    @ResponseBody
    String changeStatus
            (HttpSession session, @RequestParam("lotID") String lotId, @RequestParam("status") String status) {
        String login = (String) session.getAttribute("userId");
        Lot lot = lotService.getLot(Long.parseLong(lotId));
        lot.setWorkStage(status);
        lotService.updateLot(login, lot);
        return "1";
    }

    @RequestMapping(value = "/regions", method = RequestMethod.POST)
    private
    @ResponseBody
    List<String> getAllRegs() {
        List<String> regList;
        regList = creditService.getRegions();
        return regList;
    }

    @RequestMapping(value = "/crType", method = RequestMethod.POST)
    private
    @ResponseBody
    List<String> getAllTypes() {
        List<String> typesList;
        typesList = creditService.getTypes();
        return typesList;
    }

    @RequestMapping(value = "/getCurs", method = RequestMethod.POST)
    private
    @ResponseBody
    List<String> getAllCurr() {
        List<String> currList;
        currList = creditService.getCurrencys();
        return currList;
    }

    @RequestMapping(value = "/countSumByLot", method = RequestMethod.POST)
    private @ResponseBody
    List<String> countSumByLot(@RequestParam("lotId") String idLot) {
        List<String> countSumList = new ArrayList<>();
        Long id = Long.parseLong(idLot);
        Lot lot = lotService.getLot(id);
        Long count = lotService.lotCount(lot);
        BigDecimal sum = lotService.lotSum(lot);
        countSumList.add(count.toString());
        countSumList.add(sum.toString());
        return countSumList;
    }

    @RequestMapping(value = "/paymentsSumByLot", method = RequestMethod.POST)
    private
    @ResponseBody
    BigDecimal paymentsSum(@RequestParam("lotId") String idLot) {
        return lotService.paymentsSumByLot(lotService.getLot(Long.parseLong(idLot)));
    }

    @RequestMapping(value = "/paymentsByLot", method = RequestMethod.POST)
    private
    @ResponseBody
    List<Pay> paymentsByLot(@RequestParam("lotId") String idLot) {
        Lot lot = lotService.getLot(Long.parseLong(idLot));
        return lotService.paymentsByLot(lot);
    }

    @RequestMapping(value = "/addPayToLot", method = RequestMethod.POST)
    private @ResponseBody String addPayToLot(@RequestParam("lotId") String idLot,
                                             @RequestParam("payDate") String payDate,
                                             @RequestParam("pay") BigDecimal pay,
                                             @RequestParam("paySource") String paySource) {
        Date date;
        try {
            date = sdfshort.parse(payDate);
        } catch (ParseException e) {
            return "0";
        }
        Lot lot = lotService.getLot(Long.parseLong(idLot));
        if(lot.getLotType()==1) {
            List<Asset> assetsByLot = lotService.getAssetsByLot(Long.parseLong(idLot));
            BigDecimal lotFactPrice = lot.getFactPrice();

            BigDecimal assetsTotalPays = new BigDecimal(0.00);

            for (int i = 0; i < assetsByLot.size(); i++) {

                Asset asset = assetsByLot.get(i);

                BigDecimal coeff = getCoefficient(asset.getFactPrice(), lotFactPrice);
                // asset.getFactPrice().divide(lotFactPrice, 10, BigDecimal.ROUND_HALF_UP);

                BigDecimal payByAsset = pay.multiply(coeff).setScale(2, BigDecimal.ROUND_HALF_UP);
                asset.setPaysBid(asset.getPaysBid().add(payByAsset));
                asset.setBidPayDate(date);

                assetsTotalPays = assetsTotalPays.add(payByAsset);
                if (i == assetsByLot.size() - 1) {
                    asset.setPaysBid(asset.getPaysBid().add(pay.subtract(assetsTotalPays)));
                }
                assetService.updateAsset(asset);
            }
            Pay payment = new Pay(lot, date, pay, paySource);
            if (payService.createPay(payment) > 0L) return "1";
            else return "0";
        }
        if(lot.getLotType()==0) {
            List<Credit> creditsByLot = lotService.getCRDTSByLot(lot);
            BigDecimal lotFactPrice = lot.getFactPrice();

            BigDecimal assetsTotalPays = new BigDecimal(0.00);

            for (int i = 0; i < creditsByLot.size(); i++) {

                Credit credit = creditsByLot.get(i);

                BigDecimal coeff = getCoefficient(credit.getFactPrice(), lotFactPrice);
                // asset.getFactPrice().divide(lotFactPrice, 10, BigDecimal.ROUND_HALF_UP);

                BigDecimal payByAsset = pay.multiply(coeff).setScale(2, BigDecimal.ROUND_HALF_UP);
                credit.setPaysBid(credit.getPaysBid().add(payByAsset));
                credit.setBidPayDate(date);

                assetsTotalPays = assetsTotalPays.add(payByAsset);
                if (i == creditsByLot.size() - 1) {
                    credit.setPaysBid(credit.getPaysBid().add(pay.subtract(assetsTotalPays)));
                }
                creditService.updateCredit(credit);
            }
            Pay payment = new Pay(lot, date, pay, paySource);
            if (payService.createPay(payment) > 0L) return "1";
            else return "0";
        }
        else return "1";
    }

    @RequestMapping(value = "/setLotToPrint", method = RequestMethod.GET)
    private
    @ResponseBody
    String setLotsToPrint(@RequestParam("lotId") String lotId, Model model) {
        model.addAttribute("objIdToDownload", lotId);
        return "1";
    }

    @RequestMapping(value = "/setDocToDownload", method = RequestMethod.GET)
    private
    @ResponseBody
    String setDocToDownload(@RequestParam("objType") String objType, @RequestParam("objId") String objId, @RequestParam("docName") String docName, Model model) {
        model.addAttribute("objIdToDownload", objId);
        model.addAttribute("docName", docName);
        model.addAttribute("docType", objType);
        return "1";
    }

    @RequestMapping(value = "/setReportPath", method = RequestMethod.GET)
    private @ResponseBody String setReportPath(@RequestParam("reportNum") String reportNum,
                         @RequestParam("startDate") String start,
                         @RequestParam("endDate") String end,
                         Model model) {

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = sdfshort.parse(start);
        } catch (ParseException e) {
            System.out.println("Невірний формат дати або дату не введено");
        }
        try {
            endDate = sdfshort.parse(end);
        } catch (ParseException e) {
            System.out.println("Невірний формат дати або дату не введено");
        }
        List<Asset> assetList = assetService.findAllSuccessBids(startDate, endDate);
        List<Credit> crList = creditService.getCredits_SuccessBids(startDate, endDate);

        String reportPath = "";
        if (reportNum.equals("1")) {
            try {
                reportPath = makeDodatok(assetList, crList, start, end);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (reportNum.equals("2")) {
            reportPath = "C:\\projectFiles\\Dodatok 2_14.xls";
        }

        model.addAttribute("reportPath", reportPath);

        return "1";
    }

    @RequestMapping(value = "/getFileNames", method = RequestMethod.POST)
    private
    @ResponseBody
    List<String> getFileNames(@RequestParam("objId") String objId,
                              @RequestParam("objType") String objType) {

        List<String> fileList = new ArrayList<>();
        File[] fList;
        File F = null;

        if (objType.equals("lot"))
            F = new File(documentsPath + objId);
        if (objType.equals("bid"))
            F = new File(bidDocumentsPath + objId);

        fList = F.listFiles();

        for (File aFList : fList) {
            if (aFList.isFile())
                fileList.add(aFList.getName());
        }
        return fileList;
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    private
    @ResponseBody
    String uploadLotFile(@RequestParam("file") MultipartFile file,
                         @RequestParam("objId") String objId,
                         @RequestParam("objType") String objType,
                         HttpServletResponse response,
                         HttpServletRequest request) {
        response.setCharacterEncoding("UTF-8");
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("oooooopppps");
        }
        String name = null;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                name = file.getOriginalFilename();
                System.out.println(name);

                String arg = URLEncoder.encode(name, "UTF-8");
                String fineOne = URLDecoder.decode(arg, "UTF-8");
                System.out.println(arg);
                System.out.println(fineOne);

                String rootPath = null;
                if (objType.equals("lot"))
                    rootPath = documentsPath;
                if (objType.equals("bid"))
                    rootPath = bidDocumentsPath;

                File dir = new File(rootPath + File.separator + objId);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File uploadedFile = new File(dir.getAbsolutePath() + File.separator + name);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(bytes);
                stream.flush();
                stream.close();

                return "File " + name + " zavantajeno";

            } catch (Exception e) {
                return "Download error " + name + " => " + e.getMessage();
            }
        } else {
            return "Error. File not choosen.";
        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletResponse response, HttpSession session) throws IOException {
        String objIdToDownload = (String) session.getAttribute("objIdToDownload");
        List<Lot> lotList = new ArrayList<>();
        List<Asset> assetList = new ArrayList<>();

        String[] idMass = objIdToDownload.split(",");
        for (String id : idMass) {
            Lot lot = lotService.getLot(Long.parseLong(id));
            lotList.add(lot);
            assetList.addAll(lotService.getAssetsByLot(lot));
        }
        String filePath = Excel.loadCreditsByList(lotList, assetList);

        File file = new File(filePath);
        InputStream is = new FileInputStream(file);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.flush();
        is.close();
        os.close();
        file.delete();
    }

    @RequestMapping(value = "/reportDownload", method = RequestMethod.GET)
    public void reportDownload(HttpServletResponse response, HttpSession session) throws IOException {
        String reportPath = (String) session.getAttribute("reportPath");
        File file = new File(reportPath);
        InputStream is = new FileInputStream(file);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.flush();
        is.close();
        os.close();
        file.delete();
    }

    @RequestMapping(value = "/downloadDocument", method = RequestMethod.GET)
    public void downloadDocument(HttpServletResponse response, HttpSession session) throws IOException {
        String objId = (String) session.getAttribute("objIdToDownload");
        String docName = (String) session.getAttribute("docName");
        String docType = (String) session.getAttribute("docType");
        File file = null;

        if (docType.equals("lot"))
            file = new File(documentsPath + objId + File.separator + docName);
        if (docType.equals("bid"))
            file = new File(bidDocumentsPath + objId + File.separator + docName);

        InputStream is = new FileInputStream(file);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.flush();
        is.close();
        os.close();
    }

    @RequestMapping(value = "/downloadOgolosh", method = RequestMethod.GET)
    public void downloadOgoloshennya(HttpServletResponse response, HttpSession session) throws IOException {
        String objId = (String) session.getAttribute("objIdToDownload");

        File file = null;
        String docName = makeOgoloshennya(Long.parseLong(objId));
        file = new File(docName);

        InputStream is = new FileInputStream(file);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        OutputStream os = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.flush();
        is.close();
        os.close();
        file.delete();
    }

    @RequestMapping(value = "/changeLotParams", method = RequestMethod.POST)
    private @ResponseBody
    String changeLotParams
            (HttpSession session,
             @RequestParam("lotId") String lotId,
             @RequestParam("lotNum") String lotNum,
             @RequestParam("workStage") String status,
             @RequestParam("comment") String comment,
             @RequestParam("bidStage") String bidStage,
             @RequestParam("resultStatus") String resultStatus,
             @RequestParam("customer") String customer,
             @RequestParam("startPrice") BigDecimal startPrice,
             @RequestParam("factPrice") BigDecimal factLotPrice,
             @RequestParam("isSold") String isSold,
             @RequestParam("selectedBidId") Long selectedBidId,
             @RequestParam("countOfParticipants") int countOfParticipants) {
        String login = (String) session.getAttribute("userId");
        Lot lot = lotService.getLot(Long.parseLong(lotId));
        lot.setLotNum(lotNum);
        lot.setWorkStage(status);
        lot.setComment(comment);
        lot.setBidStage(bidStage);
        lot.setStatus(resultStatus);
        lot.setCustomerName(customer);
        lot.setStartPrice(startPrice);
        lot.setFactPrice(factLotPrice);
        lot.setCountOfParticipants(countOfParticipants);
        if (selectedBidId == 0L) {
            lot.setBid(null);
        } else lot.setBid(bidService.getBid(selectedBidId));

       /* List <Asset> assets = lotService.getAssetsByLot(lot);

        if (factLotPrice == null) {
            for (Asset asset : assets) {
                asset.setFactPrice(null);
                assetService.updateAsset(login, asset);
            }
        }
        else if (!factLotPrice.equals(new BigDecimal(0.00))) {
            BigDecimal lotSum = lotService.lotSum(lot);
            BigDecimal assetsTotalFact = new BigDecimal(0.00);

            for (int i = 0; i < assets.size(); i++) {
                Asset asset = assets.get(i);
                BigDecimal factPrice;
                if (i == assets.size() - 1) {
                    factPrice = factLotPrice.subtract(assetsTotalFact);
                } else {
                    factPrice = (asset.getRv().divide(lotSum, 10, BigDecimal.ROUND_HALF_UP)).multiply(factLotPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                    assetsTotalFact = assetsTotalFact.add(factPrice);
                }
                asset.setFactPrice(factPrice);
                assetService.updateAsset(login, asset);
            }
        }
        if (isSold.equals("1")) {
            lot.setActSignedDate(new Date());
            lot.setItSold(true);
            for (Asset asset : assets) {
                asset.setSold(true);
                assetService.updateAsset(login, asset);
            }
        }*/
        if(lot.getLotType()==1)
            setFactPriceFromLotToAssets(lot, factLotPrice, isSold, login);
        if(lot.getLotType()==0)
            setFactPriceFromLotToCredits(lot, factLotPrice, isSold, login);

        boolean isitChanged = lotService.updateLot(login, lot);
        if (isitChanged) return "1";
        else return "0";
    }
    public void setFactPriceFromLotToCredits(Lot lot, BigDecimal factLotPrice, String isSold, String login){
        List <Credit> credits = lotService.getCRDTSByLot(lot);

        if (factLotPrice == null) {
            for (Credit credit : credits) {
                credit.setFactPrice(null);
                creditService.updateCredit(login, credit);
            }
        }
        else if (!factLotPrice.equals(new BigDecimal(0.00))) {
            BigDecimal lotSum = lotService.lotSum(lot);
            BigDecimal creditsTotalFact = new BigDecimal(0.00);

            for (int i = 0; i < credits.size(); i++) {
                Credit credit = credits.get(i);
                BigDecimal factPrice;
                if (i == credits.size() - 1) {
                    factPrice = factLotPrice.subtract(creditsTotalFact);
                } else {
                    factPrice = (credit.getRv().divide(lotSum, 10, BigDecimal.ROUND_HALF_UP)).multiply(factLotPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                    creditsTotalFact = creditsTotalFact.add(factPrice);
                }
                credit.setFactPrice(factPrice);
                creditService.updateCredit(login, credit);
            }
        }
        if (isSold.equals("1")) {
            lot.setActSignedDate(new Date());
            lot.setItSold(true);
            for (Credit credit : credits) {
                credit.setSold(true);
                creditService.updateCredit(login, credit);
            }
        }
    }
    public void setFactPriceFromLotToAssets(Lot lot, BigDecimal factLotPrice, String isSold, String login){
        List <Asset> assets = lotService.getAssetsByLot(lot);

        if (factLotPrice == null) {
            for (Asset asset : assets) {
                asset.setFactPrice(null);
                assetService.updateAsset(login, asset);
            }
        }
        else if (!factLotPrice.equals(new BigDecimal(0.00))) {
            BigDecimal lotSum = lotService.lotSum(lot);
            BigDecimal assetsTotalFact = new BigDecimal(0.00);

            for (int i = 0; i < assets.size(); i++) {
                Asset asset = assets.get(i);
                BigDecimal factPrice;
                if (i == assets.size() - 1) {
                    factPrice = factLotPrice.subtract(assetsTotalFact);
                } else {
                    factPrice = (asset.getRv().divide(lotSum, 10, BigDecimal.ROUND_HALF_UP)).multiply(factLotPrice).setScale(2, BigDecimal.ROUND_HALF_UP);
                    assetsTotalFact = assetsTotalFact.add(factPrice);
                }
                asset.setFactPrice(factPrice);
                assetService.updateAsset(login, asset);
            }
        }
        if (isSold.equals("1")) {
            lot.setActSignedDate(new Date());
            lot.setItSold(true);
            for (Asset asset : assets) {
                asset.setSold(true);
                assetService.updateAsset(login, asset);
            }
        }
    }

    @RequestMapping(value = "/reBidByLot", method = RequestMethod.POST)
    private
    @ResponseBody
    String reBidByLot(HttpSession session, @RequestParam("lotId") String lotId) {
        String login = (String) session.getAttribute("userId");
        Lot lot = lotService.getLot(Long.parseLong(lotId));
        if (lot.getFirstStartPrice() == null) {
            lot.setFirstStartPrice(lot.getStartPrice());
        }
        lot.setBid(null);
        lot.setFactPrice(null);
        lot.setLotNum(null);
        lot.setWorkStage("Новий лот");
        lot.setStatus(null);
        if (lot.getBidStage().equals(bidStatusList.get(0))) {
            lot.setBidStage(bidStatusList.get(1));
        } else if (lot.getBidStage().equals(bidStatusList.get(1))) {
            lot.setBidStage(bidStatusList.get(2));
        } else if (lot.getBidStage().equals(bidStatusList.get(2))) {
            lot.setBidStage(bidStatusList.get(3));
        } else if (lot.getBidStage().equals(bidStatusList.get(3))) {
            lot.setBidStage(bidStatusList.get(4));
        }
        lotService.updateLot(login, lot);
        return "1";
    }

    @RequestMapping(value = "/changeBidParams", method = RequestMethod.POST)
    private
    @ResponseBody
    String changeBidParams(@RequestParam("bidId") String bidId,
                           @RequestParam("bidDate") String bidDate,
                           @RequestParam("exId") String exId,
                           @RequestParam("newNP") String newNP,
                           @RequestParam("newND1") String newND1,
                           @RequestParam("newND2") String newND2,
                           @RequestParam("newRED") String newRED) {
        Date bDate = null, ND1 = null, ND2 = null, RED = null;
        Bid bid = bidService.getBid(Long.parseLong(bidId));
        Exchange exchange = exchangeService.getExchange(Long.parseLong(exId));
        try {
            bDate = sdfshort.parse(bidDate);
        } catch (ParseException e) {
            System.out.println("Неверный формат даты");
        }
        try {
            ND1 = sdfshort.parse(newND1);
        } catch (ParseException e) {
            System.out.println("Неверный формат даты");
        }
        try {
            ND2 = sdfshort.parse(newND2);
        } catch (ParseException e) {
            System.out.println("Неверный формат даты");
        }
        try {
            RED = sdfshort.parse(newRED);
        } catch (ParseException e) {
            System.out.println("Неверный формат даты");
        }
        bid.setExchange(exchange);
        bid.setBidDate(bDate);
        bid.setNewspaper(newNP);
        bid.setNews1Date(ND1);
        bid.setNews2Date(ND2);
        bid.setRegistrEndDate(RED);
        bidService.updateBid(bid);
        return "1";
    }

    @RequestMapping(value = "/deleteBid", method = RequestMethod.POST)
    private
    @ResponseBody
    String deleteBid(HttpSession session, @RequestParam("idBid") String bidId) {
        String login = (String) session.getAttribute("userId");
        Bid bid = bidService.getBid(Long.parseLong(bidId));
        List<Lot> lotList = lotService.getLotsByBid(bid);
        for (Lot lot : lotList) {
            lot.setBid(null);
            lotService.updateLot(login, lot);
        }
        bidService.delete(bid);
        return "1";
    }

    @RequestMapping(value = "/lotMenu", method = RequestMethod.GET)
    private String lotMenu(HttpSession session, Model model) {
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            List<Lot> lotList = lotService.getLots();
            model.addAttribute("lotList", lotList);
            return "LotMenu";
        }
    }

    @RequestMapping(value = "/exMenu", method = RequestMethod.GET)
    private String exMenu(HttpSession session, Model model) {
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            model.addAttribute("exchangesList", exchangeService.getAllExchanges());
            return "ExMenu";
        }
    }

    @RequestMapping(value = "/bidMenu", method = RequestMethod.GET)
    private String bidMenu(HttpSession session, Model model) {
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            model.addAttribute("bidList", bidService.getAllBids());
            model.addAttribute("exchangeList", exchangeService.getAllExchanges());
           /* model.addAttribute("bidStatusList", bidStatusList);*/
            return "BidMenu";
        }
    }

    @RequestMapping(value = "/setAssetPortionNum", method = RequestMethod.POST)
    private @ResponseBody
    String setAssetPortionNum(HttpSession session, @RequestParam("portion") String portion, Model model) {
        model.addAttribute("assetPortionNum", portion);
        return "1";
    }

    @RequestMapping(value = "/assets", method = RequestMethod.GET)
    private String assets(HttpSession session, Model model) {
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            int portionNum;
            try {
                String p = (String) session.getAttribute("assetPortionNum");
                portionNum = Integer.parseInt(p);
            } catch (Exception e) {
                portionNum = 0;
            }
            model.addAttribute("assetPortion", portionNum + 1);
            model.addAttribute("assetList", assetService.getAssetsByPortion(portionNum));
            model.addAttribute("fondDecisionsList", fondDecisionsList);
            model.addAttribute("allBidDates", assetService.getAllBidDates());
            model.addAttribute("bidResultList", bidResultList);
            model.addAttribute("workStages", statusList);
            model.addAttribute("exchangeList", assetService.getExchanges());
            model.addAttribute("decisionNumbers", assetService.getDecisionNumbers());
            model.addAttribute("allLotId", lotService.getLotsId());
            return "Assets";
        }
    }

    @RequestMapping(value = "/credits", method = RequestMethod.GET)
    private String credits(HttpSession session, Model model) {
        if (!isAuth(session)) {
            return "LogIN";
        } else
            model.addAttribute("totalCountOfCredits", creditService.getTotalCountOfCredits());

        return "Credits";
    }

    @RequestMapping(value = "/lotCreator", method = RequestMethod.GET)
    private String singleFormLot(HttpSession session, Model m) {
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            List<Asset> assetList = new ArrayList<>();
            m.addAttribute("assetList", assetList);
            return "LotCreator";
        }
    }

    @RequestMapping(value = "/lotCreator1", method = RequestMethod.GET)
    private String singleFormLot1(HttpSession session, Model m) {
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            List<Asset> assetList = new ArrayList<>();
            String[] idMass = (String[]) session.getAttribute("assetsListToLot");
            for (String id : idMass) {
                assetList.add(assetService.getAsset(Long.parseLong(id)));
            }
            m.addAttribute("assetList", assetList);
            return "LotCreator";
        }
    }

    @RequestMapping(value = "/lotCreditsCreator", method = RequestMethod.GET)
    private String lotCreditsCreator(HttpSession session, Model m) {
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            List<Credit> creditList = new ArrayList<>();
            m.addAttribute("creditList", creditList);
            return "LotCreditsCreator";
        }
    }

    @RequestMapping(value = "/lotCreditsCreator1", method = RequestMethod.GET)
    private String lotCreditsCreator1(HttpSession session, Model m) {
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            List<Credit> creditList = new ArrayList<>();
            String[] idMass = (String[]) session.getAttribute("creditsListToLot");
            for (String id : idMass) {
                Credit cr = creditService.getCredit(Long.parseLong(id));
                if(cr.getLot()==null&&cr.getFondDecisionDate()!=null)
                creditList.add(cr);
            }

            m.addAttribute("creditList", creditList);
            return "LotCreditsCreator";
        }
    }

    @RequestMapping(value = "/createLotByCheckedAssets", method = RequestMethod.POST)
    private @ResponseBody String createLotByAssets(@RequestParam("idList") String idList, HttpSession session) {
        String[] idMass = idList.split(",");
            session.setAttribute("assetsListToLot", idMass);
            return "1";
    }

    @RequestMapping(value = "/createLotByCheckedCredits", method = RequestMethod.POST)
    private @ResponseBody String createLotByCheckedCredits(@RequestParam("idList") String idList, HttpSession session) {
        String[] idMass = idList.split(",");
        session.setAttribute("creditsListToLot", idMass);
        return "1";
    }

    @RequestMapping(value = "/lotRedactor", method = RequestMethod.GET)
    private String LotRedactor(HttpSession session, Model model) {
        String lotId = (String) session.getAttribute("lotRid");
        String userName = (String) session.getAttribute("userId");
        Lot lot = lotService.getLot(Long.parseLong(lotId));
        model.addAttribute("bidStatusList", bidStatusList);
        model.addAttribute("statusList", statusList);
        model.addAttribute("lott", lot);
        model.addAttribute("user", userName);
        model.addAttribute("bidResultList", bidResultList);
        model.addAttribute("allBidsList", bidService.getAllBids());

        List<Long> bidIdList = lotService.getBidsIdByLot(Long.parseLong(lotId));

        Set<Bid> historyBids = new TreeSet<>();

        for (long id : bidIdList) {
            Bid bid = bidService.getBid(id);
            if (lot.getBid() == null || lot.getBid().getId() != id) {
                historyBids.add(bid);
            }
        }
        model.addAttribute("bidsHistoryList", historyBids);
        return "LotRedaction";

    }

    @RequestMapping(value = "/exLots", method = RequestMethod.GET)
    private String exRedactor(HttpSession session, Model model) {
        String exId = (String) session.getAttribute("exRid");
        Exchange exchange = exchangeService.getExchange(Long.parseLong(exId));
        List<Bid> bidList = bidService.getBidsByExchange(exchange);

        List<Lot> lotList = new ArrayList<>();
        for (Bid bid : bidList) {
            lotList.addAll(lotService.getLotsByBid(bid));
        }
        model.addAttribute("exchange", exchange);
        model.addAttribute("lotList", lotList);
        return "ExLots";
    }

    @RequestMapping(value = "/creditsByClient", method = RequestMethod.POST)
    private @ResponseBody List<Credit> getCreditsByEx(
            @RequestParam("inn") String inn,
            @RequestParam("idBars") Long idBars) {
        return creditService.getCreditsByClient(inn, idBars);
    }

    @RequestMapping(value = "/objectsByInNum", method = RequestMethod.POST)
    private @ResponseBody List<Asset> getAssetsByInNum(@RequestParam("inn") String inn) {
        return assetService.getAssetsByInNum(inn);
    }

    @RequestMapping(value = "/sumById", method = RequestMethod.POST)
    private @ResponseBody String sumById(@RequestParam("idMass") String ids) {
        Formatter f = new Formatter();
        BigDecimal sum = new BigDecimal(0);
        String[] idm;
        try {
            idm = ids.substring(1).split(",");
        } catch (IndexOutOfBoundsException e) {
            return "0";
        }
        for (String id : idm) {
            sum = sum.multiply(creditService.getCredit(Long.parseLong(id)).getCreditPrice());
        }
        return f.format("%,.0f", sum).toString();
    }

    @RequestMapping(value = "/sumByInvs", method = RequestMethod.POST)
    private @ResponseBody String sumByInvs(@RequestParam("idMass") String ids) {
        BigDecimal sum = new BigDecimal(0.00);
        String[] idm;
        try {
            idm = ids.substring(1).split(",");
        } catch (IndexOutOfBoundsException e) {
            return "0";
        }
        for (String id : idm) {
            sum = sum.add(assetService.getAsset(Long.parseLong(id)).getRv());
        }
        return sum.toString();
    }

    @RequestMapping(value = "/sumByIDBars", method = RequestMethod.POST)
    private @ResponseBody String sumByIDBars (@RequestParam("idMass") String ids) {
        BigDecimal sum = new BigDecimal(0.00);
        String[] idm;
        try {
            idm = ids.substring(1).split(",");
        } catch (IndexOutOfBoundsException e) {
            return "0";
        }
        for (String id : idm) {
            System.out.println("id = "+id);
            sum = sum.add(creditService.getCredit(Long.parseLong(id)).getRv());
            System.out.println("sum = "+sum);
        }
        return sum.toString();
    }

    @RequestMapping(value = "/lotsByBid", method = RequestMethod.POST)
    private @ResponseBody List<Lot> lotsByBid(@RequestParam("bidId") String bidId) {
        Bid bid = bidService.getBid(Long.parseLong(bidId));
        return bidService.lotsByBid(bid);
    }

    @RequestMapping(value = "/getPaySum_Residual", method = RequestMethod.GET)
    private @ResponseBody List<BigDecimal> getPaySumResidual(@RequestParam("id") String id) {
        List<BigDecimal> list = new ArrayList<>();
        Asset asset = assetService.getAsset(Long.parseLong(id));
        if (asset.getLot() == null) {
            return list;
        } else {
            Lot lot = asset.getLot();
            BigDecimal coeff = getCoefficient(asset.getFactPrice(), lot.getFactPrice());// asset.getFactPrice().divide(lot.getFactPrice(), 10, BigDecimal.ROUND_HALF_UP);
            BigDecimal paySumByAsset = lotService.paymentsSumByLot(lot).multiply(coeff).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal residualToPay = asset.getFactPrice().subtract(paySumByAsset);
            list.add(paySumByAsset);
            list.add(residualToPay);
            return list;
        }
    }

    @RequestMapping(value = "/getTotalCountOfObjects", method = RequestMethod.GET)
    private @ResponseBody Long getTotalCountOfObjects() {
        return assetService.getTotalCountOfAssets();
    }

    @RequestMapping(value = "/objectsByPortions", method = RequestMethod.POST)
    private @ResponseBody List<Asset> objectsByPortions(@RequestParam("num") String portionNumber) {
        return assetService.getAssetsByPortion(Integer.parseInt(portionNumber));
    }

    @RequestMapping(value = "/countCreditsByFilter", method = RequestMethod.POST)
    private @ResponseBody Long countCreditsByFilter(@RequestParam("num") int portionNumber,
                                                         @RequestParam("isSold") int isSold,
                                                         @RequestParam("isInLot") int isInLot,
                                                         @RequestParam("clientType") int clientType,
                                                         @RequestParam("isNbu") int isNbu,
                                                         @RequestParam("isFondDec") int isFondDec) {
        return creditService.countOfFilteredCredits(isSold, isInLot, clientType, isNbu, isFondDec);
    }

    @RequestMapping(value = "/creditsByPortions", method = RequestMethod.POST)
    private @ResponseBody List<String> creditsByPortions(@RequestParam("num") int portionNumber,
                                                         @RequestParam("isSold") int isSold,
                                                         @RequestParam("isInLot") int isInLot,
                                                         @RequestParam("clientType") int clientType,
                                                         @RequestParam("isNbu") int isNbu,
                                                         @RequestParam("isFondDec") int isFondDec) {
      //  List<Credit> crList = creditService.getCreditsByPortion(portionNumber);
        List<Credit> crList = creditService.getCreditsByPortion(portionNumber, isSold, isInLot, clientType, isNbu, isFondDec);
        List<String> rezList = new ArrayList<>();
        for (Credit cr : crList) {
            String lotId = "";

            String bidDate="";
            String exchangeName="";

            String nbuPledge = "Ні";
            if (cr.getNbuPladge())
                nbuPledge = "Так";
            String factPrice = "";
            if (cr.getFactPrice() != null)
                factPrice = String.valueOf(cr.getFactPrice());
            String bidStage = "";
            String bidResult = "";
            String payStatus = "";
            String paySum = "";
            String residualToPay = "";
            String customerName = "";
            String workStage = "";
            String fondDecisionDate = "";
            String acceptedPrice="";

            if (cr.getFondDecisionDate() != null)
                fondDecisionDate = String.valueOf(sdfpoints.format(cr.getFondDecisionDate()));
            if(cr.getAcceptPrice()!=null)
                acceptedPrice=String.valueOf(cr.getAcceptPrice());
            String actSignedDate = "";
            if (cr.getLot() != null) {
                lotId = String.valueOf(cr.getLot());
                Lot lot = lotService.getLot(cr.getLot());

                if(lot.getBid()!=null){
                    bidDate=String.valueOf(sdfpoints.format(lot.getBid().getBidDate()));
                    exchangeName=lot.getBid().getExchange().getCompanyName();
                }
                bidStage = lot.getBidStage();
                bidResult = lot.getStatus();
                if (lotService.paymentsSumByLot(lot) != null) {
                    BigDecimal paysSum = lotService.paymentsSumByLot(lot);
                    paySum = String.valueOf(paysSum);
                    if (lot.getFactPrice().compareTo(paysSum) < 0)
                        payStatus = "100% сплата";
                    else if (!paysSum.equals(new BigDecimal(0)))
                        payStatus = "Часткова оплата";
                    residualToPay = String.valueOf(lot.getFactPrice().subtract(paysSum));
                }
                customerName = lot.getCustomerName();
                workStage = lot.getWorkStage();
                if (lot.getActSignedDate() != null)
                actSignedDate = sdfpoints.format(lot.getActSignedDate());
            }
            String planSaleDate="";
            if(cr.getPlanSaleDate()!=null)
                planSaleDate=yearMonthFormat.format(cr.getPlanSaleDate());

            rezList.add(lotId
                    + "||" + cr.getId()
                    + "||" + cr.getInn()
                    + "||" + cr.getContractNum()
                    + "||" + bidDate
                    + "||" + exchangeName
                    + "||" + cr.getClientType()
                    + "||" + cr.getFio()
                    + "||" + cr.getProduct()
                    + "||" + nbuPledge
                    + "||" + cr.getRegion()
                    + "||" + cr.getCurr()
                    + "||" + cr.getZb()
                    + "||" + cr.getDpd()
                    + "||" + cr.getRv()
                    + "||" + bidStage
                    + "||" + factPrice
                    + "||" + bidResult
                    + "||" + payStatus
                    + "||" + paySum
                    + "||" + residualToPay
                    + "||" + customerName
                    + "||" + workStage
                    + "||" + fondDecisionDate
                    + "||" + cr.getFondDecision()
                    + "||" + cr.getDecisionNumber()
                    + "||" + acceptedPrice
                    + "||" + actSignedDate
                    + "||" + planSaleDate
            );
        }
        return rezList;
    }

    @RequestMapping(value = "/countSumLotsByBid", method = RequestMethod.POST)
    private @ResponseBody List<String> countSumLotsByBid(@RequestParam("bidId") String bidId) {
        Long id = Long.parseLong(bidId);
        Bid bid = bidService.getBid(id);
        List<String> list = new ArrayList<>();
        Long count = bidService.countOfLots(bid);
        BigDecimal sum = bidService.sumByBid(bid);
        list.add(count.toString());
        list.add(sum.toString());
        return list;
    }

    @RequestMapping(value = "/countSumLotsByExchange", method = RequestMethod.POST)
    private @ResponseBody
    List<String> countSumLotsByExchange(@RequestParam("exId") String exId) {
        Exchange exchange = exchangeService.getExchange(Long.parseLong(exId));
        List<Lot> lotsList = lotService.getLotsByExchange(exchange);
        List<String> list = new ArrayList<>();
        BigDecimal lotRV = new BigDecimal(0.00);
        for (Lot lot : lotsList) {
            lotRV = lotRV.add(lotService.lotSum(lot));
        }
        list.add(String.valueOf(lotsList.size()));
        list.add(String.valueOf(lotRV));
        return list;
    }

    @RequestMapping(value = "/countBidsByExchange", method = RequestMethod.GET)
    private @ResponseBody int countBidsByExchange(@RequestParam("exId") String exId) {
        Exchange exchange = exchangeService.getExchange(Long.parseLong(exId));
        return bidService.getBidsByExchange(exchange).size();
    }

    @RequestMapping(value = "/selectedCRD", method = RequestMethod.POST)
    private @ResponseBody List<String> selectCrd(
            @RequestParam("types") String types,
            @RequestParam("regions") String regs,
            @RequestParam("curs") String curs,
            @RequestParam("dpdmin") int dpdmin,
            @RequestParam("dpdmax") int dpdmax,
            @RequestParam("zbmin") double zbmin,
            @RequestParam("zbmax") double zbmax) {
        String[] typesMass = types.split(",");
        String[] regsMass = regs.split(",");
        String[] curMass = curs.split(",");
        List<Credit> crList = creditService.selectCredits(typesMass, regsMass, curMass, dpdmin, dpdmax, zbmin, zbmax);
        List<String> rezList = new ArrayList<>();
        rezList.add("ID" + '|' + "ІНН" + '|' + "Номер договору" + '|' + "ФІО" + '|' + "Регіон" + '|' + "Код типу активу" + '|'
                + "Код групи активу" + '|' + "Тип клієнта" + '|' + "Дата видачі" + '|'
                + "Дата закінчення" + '|' + "Валюта" + '|' + "Продукт" + '|'
                + "Загальний борг, грн." + '|' + "dpd" + '|' + "Вартість об'єкту, грн.");

        for (Credit cr : crList) {
            rezList.add(cr.getId() + cr.toShotString());
        }
        return rezList;
    }

    @RequestMapping(value = "/selectAssetsbyLot", method = RequestMethod.POST)
    private @ResponseBody List<Asset> selectAssetsbyLot(@RequestParam("lotId") String lotId) {
        return lotService.getAssetsByLot(Long.parseLong(lotId));
    }

    @RequestMapping(value = "/selectCreditsLot", method = RequestMethod.POST)
    private @ResponseBody List<Credit> selectCreditsLot(@RequestParam("lotId") String lotId) {
        return creditService.getCrditsByLotId(Long.parseLong(lotId));
    }

    @RequestMapping(value = "/delObjectFromLot", method = RequestMethod.POST)
    private @ResponseBody String delObjectFromLot(HttpSession session,
                                                  @RequestParam("objId") Long objId,
                                                  @RequestParam("lotId") Long lotId) {
        Lot lot = lotService.getLot(lotId);
        String login = (String) session.getAttribute("userId");
        boolean isitUpdated = true;
        if (lot.getLotType() == 0) {
            Credit credit = creditService.getCredit(objId);
            credit.setLot(null);
            isitUpdated = creditService.updateCredit(login, credit);
        } else if (lot.getLotType() == 1) {
            Asset asset = assetService.getAsset(objId);
            asset.setLot(null);
            isitUpdated = assetService.updateAsset(login, asset);
        }
        if (isitUpdated)
            return "1";
        else
            return "0";
    }

    @RequestMapping(value = "/setAcceptedPrice", method = RequestMethod.POST)
    private @ResponseBody
    String setAcceptedPrice(HttpSession session, @RequestParam("assetId") String assetId, @RequestParam("acceptPrice") BigDecimal acceptPrice) {
        String login = (String) session.getAttribute("userId");
        Asset asset = assetService.getAsset(Long.parseLong(assetId));
        asset.setAcceptPrice(acceptPrice);
        assetService.updateAsset(login, asset);
        return "1";
    }

    @RequestMapping(value = "/changeFondDec", method = RequestMethod.POST)
    private @ResponseBody
    String changeFondDec(HttpSession session, @RequestParam("idList") String idList,
                         @RequestParam("fondDecDate") String fondDecDate,
                         @RequestParam("fondDec") String fondDec,
                         @RequestParam("decNum") String decNum
                         // @RequestParam("acceptedPrice") BigDecimal acceptedPrice
    ) {
        String login = (String) session.getAttribute("userId");
        String[] idMass = idList.split(",");
        Date date = null;
        try {
            date = sdfshort.parse(fondDecDate);
        } catch (ParseException e) {
            System.out.println("Халепа!");
        }
        for (String id : idMass) {
            Asset asset = assetService.getAsset(Long.parseLong(id));
            asset.setFondDecisionDate(date);
            asset.setFondDecision(fondDec);
            asset.setDecisionNumber(decNum);
            // asset.setAcceptPrice(acceptedPrice);
            assetService.updateAsset(login, asset);
        }
        return "1";
    }

    @RequestMapping(value = "/setPlanSaleDate", method = RequestMethod.GET)
    private @ResponseBody void setPlanSaleDate(@RequestParam("objID") Long objId, @RequestParam("objType") int objType, @RequestParam("planDate") String stringDate ){
        Date planDat=new Date();
        try {
            planDat = yearMonthFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(planDat);
        if (objType==0){
            Credit credit =creditService.getCredit(objId);
            credit.setPlanSaleDate(planDat);
            creditService.updateCredit(credit);
        }
        else if (objType==1){
            Asset asset =assetService.getAsset(objId);
            asset.setPlanSaleDate(planDat);
            assetService.updateAsset(asset);
        }
    }

    @RequestMapping(value = "/updateCreditsInLot", method = RequestMethod.POST)
    private
    @ResponseBody
    String updateCreditsInLot(@RequestParam("newPricesId") String newPricesId, @RequestParam("newPrice") String newPrices,
                              @RequestParam("factPricesId") String factPricesId, @RequestParam("factPrice") String factPrices,
                              @RequestParam("soldId") String soldId) {
        if (!newPricesId.equals("")) {
            String[] newPricesIdMass = newPricesId.split(",");
            String[] newPricesMass = newPrices.split(",");
            for (int i = 0; i < newPricesIdMass.length; i++) {
                Credit credit = creditService.getCredit(Long.parseLong(newPricesIdMass[i]));
                credit.setDiscountPrice(BigDecimal.valueOf(Double.valueOf(newPricesMass[i])));
                creditService.updateCredit(credit);

            }
        }

        if (!factPricesId.equals("")) {
            String[] factPricesIdMass = factPricesId.split(",");
            String[] factPricesMass = factPrices.split(",");
            for (int i = 0; i < factPricesIdMass.length; i++) {
                Credit credit = creditService.getCredit(Long.parseLong(factPricesIdMass[i]));
                credit.setFactPrice(BigDecimal.valueOf(Double.parseDouble(factPricesMass[i])));
                creditService.updateCredit(credit);

            }
        }

        if (!soldId.equals("")) {
            String[] soldIdMass = soldId.split(",");
            for (String sId : soldIdMass) {
                Credit credit = creditService.getCredit(Long.parseLong(sId));
                credit.setSold(true);
                creditService.updateCredit(credit);

            }
        }

        return "1";
    }

    @RequestMapping(value = "/selectedParam", method = RequestMethod.POST)
    private
    @ResponseBody
    List<String> getParam(@RequestParam("types") String types,
                          @RequestParam("regions") String regs,
                          @RequestParam("curs") String curs,
                          @RequestParam("dpdmin") int dpdmin,
                          @RequestParam("dpdmax") int dpdmax,
                          @RequestParam("zbmin") double zbmin,
                          @RequestParam("zbmax") double zbmax) {
        String[] typesMass = types.split(",");
        String[] regsMass = regs.split(",");
        String[] curMass = curs.split(",");
        List<String> paramList = creditService.getCreditsResults(typesMass, regsMass, curMass, dpdmin, dpdmax, zbmin, zbmax);
        return paramList;
    }

    @RequestMapping(value = "/createSLot", method = RequestMethod.POST)
    private @ResponseBody String createLot(HttpSession session, Model model,
                     @RequestParam("idMass") String ids,
                     @RequestParam("comment") String comment) {
        String[] idm;
        try {
            idm = ids.substring(1).split(",");
        } catch (IndexOutOfBoundsException e) {
            return "0";
        }
        String userLogin = (String) session.getAttribute("userId");
        User user = userService.getByLogin(userLogin);
        BigDecimal startPrice = new BigDecimal(0);
        Lot newlot = new Lot("" + comment, user, new Date(), 1);
        Long lotRid = lotService.createLot(userLogin, newlot);
        for (String id : idm) {
            Asset asset = assetService.getAsset(Long.parseLong(id));
            if (asset.getAcceptPrice() != null)
                startPrice = startPrice.add(asset.getAcceptPrice());
            else
                startPrice = startPrice.add(asset.getRv());
            if (asset.getLot() == null) asset.setLot(newlot);
            assetService.updateAsset(userLogin, asset);
        }
        newlot.setStartPrice(startPrice);
        newlot.setFirstStartPrice(startPrice);
        lotService.updateLot(newlot);

        model.addAttribute("lotRid", lotRid.toString());
        return "1";
    }

    @RequestMapping(value = "/createCreditLot", method = RequestMethod.POST)
    private @ResponseBody String createCreditLot(HttpSession session, Model model,
                     @RequestParam("idMass") String ids,
                     @RequestParam("comment") String comment) {
        String[] idm;
        try {
            idm = ids.substring(1).split(",");
        } catch (IndexOutOfBoundsException e) {
            return "0";
        }
        String userLogin = (String) session.getAttribute("userId");
        User user = userService.getByLogin(userLogin);
        BigDecimal startPrice = new BigDecimal(0);
        Lot newlot = new Lot("" + comment, user, new Date(), 0);
        Long lotRid = lotService.createLot(userLogin, newlot);
        for (String id : idm) {
            Credit crdt = creditService.getCredit(Long.parseLong(id));
            if (crdt.getAcceptPrice() != null)
                startPrice = startPrice.add(crdt.getAcceptPrice());
            else
                startPrice = startPrice.add(crdt.getRv());
            if (crdt.getLot() == null) crdt.setLot(lotRid);
            creditService.updateCredit(crdt);
        }
        newlot.setStartPrice(startPrice);
        newlot.setFirstStartPrice(startPrice);
        lotService.updateLot(newlot);

        model.addAttribute("lotRid", lotRid.toString());
        return "1";
    }

    @RequestMapping(value = "/createBid", method = RequestMethod.GET)
    private
    @ResponseBody
    String createBid(@RequestParam("exId") String exId,
                     @RequestParam("bidDate") String bidD,
                     @RequestParam("newspaper") String newspaper,
                     @RequestParam("newsDate1") String newsD1,
                     @RequestParam("newsDate2") String newsD2,
                     @RequestParam("registrEnd") String regEnd) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date bidDate, newsDate1, newsDate2, registrEnd;
        try {
            bidDate = bidD.equals("") ? null : format.parse(bidD);
            newsDate1 = newsD1.equals("") ? null : format.parse(newsD1);
            newsDate2 = newsD2.equals("") ? null : format.parse(newsD2);
            registrEnd = regEnd.equals("") ? null : format.parse(regEnd);
        } catch (ParseException e) {
            e.printStackTrace();
            return "0";
        }
        Exchange exchange = exchangeService.getExchange(Long.parseLong(exId));
        Bid bid = new Bid(bidDate, exchange, newspaper, newsDate1, newsDate2, registrEnd);
        bidService.createBid(bid);
        return "1";
    }

}