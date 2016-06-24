package nadrabank.controller;

import nadrabank.domain.*;
import nadrabank.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes({"userId", "lotRid", "exRid", "lotsIdToPrint"})
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
    private static final List<String> bidStatusList = Arrays.asList("Перші торги", "Другі торги", "Треті торги");
    private static final List<String> bidResultList = Arrays.asList("", "Торги відбулись", "Торги не відбулись");
    private static final List<String> fondDecisionsList = Arrays.asList("", "Відправлено до ФГВФО", "ВД ФГВФО", "Комітет ФГВФО");
    //private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss", Locale.ENGLISH);
    private static final SimpleDateFormat sdfshort = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    private boolean isAuth(HttpSession session) {
        Locale.setDefault(Locale.ENGLISH);
        return session.getAttribute("userId") != null;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD})
    private String main(HttpSession session) {
        Locale.setDefault(Locale.ENGLISH);
        if (!isAuth(session)) {
            return "LogIN";
        }
        else {
            return "Menu";
        }
    }

    @RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.HEAD})
    private String index(HttpSession session) {
        if (!isAuth(session)) {
            return "LogIN";
        }
        else {
            return "Menu";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private @ResponseBody String logCheck(@RequestParam("login") String login,
                    @RequestParam("password") String password,
                    Model uID) {
        if (userService.isExist(login, password)) {
            uID.addAttribute("userId", login);
            return "1";
        }
        else {
            return "0";
        }
    }

    @RequestMapping(value = "/lotList", method = RequestMethod.GET)
    private @ResponseBody List<Lot> getLots() {
        return lotService.getLots();
    }

    @RequestMapping(value = "/exchanges", method = RequestMethod.GET)
    private @ResponseBody List<Exchange> jsonGetExchanges() {
        return exchangeService.getAllExchanges();
    }

    @RequestMapping(value = "/setRlot", method = RequestMethod.GET)
    private @ResponseBody String toLotRedactor(@RequestParam("lotID") String lotId, Model model) {
        model.addAttribute("lotRid", lotId);
        return "1";
    }

    @RequestMapping(value = "/setRex", method = RequestMethod.GET)
    private @ResponseBody String setRex(@RequestParam("exId") String exId, Model model) {
        model.addAttribute("exRid", exId);
        return "1";
    }

    @RequestMapping(value = "/lotDel", method = RequestMethod.POST)
    private @ResponseBody String deleteLot(@RequestParam("lotID") String lotId) {
        boolean isitDel = lotService.delLot(Long.parseLong(lotId));
        if (isitDel)
            return "1";
        else
            return "0";
    }

    @RequestMapping(value = "/setLotSold", method = RequestMethod.POST)
    private @ResponseBody String setLotSold(@RequestParam("lotID") String lotId) {
        Lot lot =lotService.getLot(Long.parseLong(lotId));
        lot.setItSold(true);
        boolean isitUpdated = lotService.updateLot(lot);
        if (isitUpdated)
            return "1";
        else
            return "0";
    }

    @RequestMapping(value = "/statusChanger", method = RequestMethod.POST)
    private @ResponseBody String changeStatus
            (@RequestParam("lotID") String lotId, @RequestParam("status") String status) {
        Lot lot = lotService.getLot(Long.parseLong(lotId));
        lot.setWorkStage(status);
        lotService.updateLot(lot);
        return "1";
    }

    @RequestMapping(value = "/regions", method = RequestMethod.POST)
    private @ResponseBody List<String> getAllRegs() {
        List<String> regList;
        regList = creditService.getRegions();
        return regList;
    }

    @RequestMapping(value = "/crType", method = RequestMethod.POST)
    private @ResponseBody List<String> getAllTypes() {
        List<String> typesList;
        typesList = creditService.getTypes();
        return typesList;
    }

    @RequestMapping(value = "/getCurs", method = RequestMethod.POST)
    private @ResponseBody List<String> getAllCurr() {
        List<String> currList;
        currList = creditService.getCurrencys();
        return currList;
    }

    @RequestMapping(value = "/countSumByLot", method = RequestMethod.POST)
    private @ResponseBody List<String> countSumByLot(@RequestParam("lotId") String idLot) {
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
    private @ResponseBody BigDecimal paymentsSum(@RequestParam("lotId") String idLot) {
        return lotService.paymentsSumByLot(lotService.getLot(Long.parseLong(idLot)));
    }

    @RequestMapping(value = "/paymentsByLot", method = RequestMethod.POST)
    private @ResponseBody List<Pay> paymentsByLot(@RequestParam("lotId") String idLot) {
        Lot lot = lotService.getLot(Long.parseLong(idLot));
        return lotService.paymentsByLot(lot);
    }

    @RequestMapping(value = "/addPayToLot", method = RequestMethod.POST)
    private @ResponseBody String addPayToLot(@RequestParam("lotId") String idLot, @RequestParam("payDate") String payDate, @RequestParam("pay") BigDecimal pay) {

        Lot lot = lotService.getLot(Long.parseLong(idLot));
        Date date;
        try {
            date = sdfshort.parse(payDate);
        }
        catch (ParseException e) {
            return "0";
        }
        Pay payment = new Pay(lot, date, pay);

        if(payService.createPay(payment)>0L) return "1";
        else return "0";
    }

    @RequestMapping(value = "/setLotsToPrint", method = RequestMethod.GET)
    private @ResponseBody String setLotsToPrint(@RequestParam("lotsId") String lotsId, Model model) {
        model.addAttribute("lotsIdToPrint", lotsId);
            return "1";
        }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletResponse response, HttpSession session) throws IOException {
        String lotsIdToPrint = (String) session.getAttribute("lotsIdToPrint");
        List<Lot> lotList = new ArrayList<>();
        List<Asset> assetList = new ArrayList<>();

        String[] idMass = lotsIdToPrint.split(",");
        for (String id : idMass) {
            Lot lot = lotService.getLot(Long.parseLong(id));
            lotList.add(lot);
            assetList.addAll(lotService.getAssetsByLot(lot));
        }

        String filePath = Excel.loadCreditsByList(lotList, assetList);

        File file = new File(filePath);
        InputStream is = new FileInputStream(file);

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\""+ file.getName() + "\"");

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
    private @ResponseBody String changeLotParams
            (@RequestParam("lotId") String lotId,
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
             @RequestParam("countOfParticipants") int countOfParticipants
             ) {
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

        List<Asset> assets = lotService.getAssetsByLot(lot);

        if (factLotPrice != null && !factLotPrice.equals(new BigDecimal(0.00))) {
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
                assetService.updateAsset(asset);
            }
        }
        if (isSold.equals("1")) {
            lot.setActSignedDate(new Date());
            lot.setItSold(true);
            for (Asset asset : assets) {
                asset.setSold(true);
                assetService.updateAsset(asset);
            }
        }
        boolean isitChanged = lotService.updateLot(lot);
        if (isitChanged) return "1";
        else return "0";
    }

    @RequestMapping(value = "/reBidByLot", method = RequestMethod.POST)
    private @ResponseBody String reBidByLot(@RequestParam("lotId") String lotId){
        Lot lot = lotService.getLot(Long.parseLong(lotId));
        if(lot.getFirstStartPrice()==null){
            lot.setFirstStartPrice(lot.getStartPrice());
        }
        lot.setBid(null);
        lot.setFactPrice(null);
        lot.setLotNum(null);
        lot.setWorkStage("Новий лот");
        lot.setStatus(null);
        if(lot.getBidStage().equals(bidStatusList.get(0))){
            lot.setBidStage(bidStatusList.get(1));
        }
        else if(lot.getBidStage().equals(bidStatusList.get(1))){
            lot.setBidStage(bidStatusList.get(2));
        }
        lotService.updateLot(lot);
        return "1";
    }

    @RequestMapping(value="/changeBidParams", method = RequestMethod.POST)
    private @ResponseBody String changeBidParams(@RequestParam("bidId") String bidId,
                                                 @RequestParam("bidDate") String bidDate,
                                                 @RequestParam("exId") String exId,
                                                 @RequestParam("newNP") String newNP,
                                                 @RequestParam("newND1") String newND1,
                                                 @RequestParam("newND2") String newND2,
                                                 @RequestParam("newRED") String newRED){
        Date bDate=null, ND1=null, ND2=null, RED=null;
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

    @RequestMapping(value="/deleteBid", method = RequestMethod.POST)
    private @ResponseBody String deleteBid(@RequestParam("idBid") String bidId){
        Bid bid = bidService.getBid(Long.parseLong(bidId));
        List<Lot> lotList=lotService.getLotsByBid(bid);
        for(Lot lot: lotList){
            lot.setBid(null);
            lotService.updateLot(lot);
        }
        bidService.delete(bid);
        return "1";
    }

    @RequestMapping(value = "/lotForm", method = RequestMethod.GET)
    private String formLot(HttpSession session, Model model) {
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            model.addAttribute("exList", exchangeService.getAllExchanges());
            return "Creator";
        }
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
        }
        else {
            model.addAttribute("bidList", bidService.getAllBids());
            model.addAttribute("exchangeList", exchangeService.getAllExchanges());
           /* model.addAttribute("bidStatusList", bidStatusList);*/
            return "BidMenu";
        }
    }

    @RequestMapping(value = "/assets", method = RequestMethod.GET)
    private String assets(HttpSession session, Model model) {
        if (!isAuth(session)) {
            return "LogIN";
        }
        else {
            model.addAttribute("assetList", assetService.getAssetsByPortion(0));
            model.addAttribute("fondDecisionsList", fondDecisionsList);
            model.addAttribute("allBidDates", assetService.getAllBidDates());
            model.addAttribute("bidResultList", bidResultList);
            model.addAttribute("workStages", statusList);
            model.addAttribute("exchangeList", assetService.getExchanges());
            return "Assets";
        }
    }

    @RequestMapping(value = "/lotCreator", method = RequestMethod.GET)
    private String singleFormLot(HttpSession session) {
        if (!isAuth(session)) {
            return "LogIN";
        } else {
            return "LotCreator";
        }
    }
    @RequestMapping(value = "/lotCreator1", method = RequestMethod.GET)
    private String singleFormLot1(HttpSession session, Model m) {
        if (!isAuth(session)) {
            return "LogIN";
        }
        else {
            List<Asset> assetList = new ArrayList<>();
            String [] idMass = (String[]) session.getAttribute("assetsListToLot");
            for(String id: idMass){
                assetList.add(assetService.getAsset(Long.parseLong(id)));
            }
            m.addAttribute("assetList", assetList);
            return "LotCreator";
        }
    }

    @RequestMapping(value = "/createLotByCheckedAssets", method = RequestMethod.POST)
    private @ResponseBody String createLotByAssets(@RequestParam("idList") String idList, HttpSession session) {
        String [] idMass = idList.split(",");
        if (!isAuth(session)) {
            return "LogIN";
        }
        else {
            session.setAttribute("assetsListToLot", idMass);
            return "1";
        }
    }

    @RequestMapping(value = "/lotRedactor", method = RequestMethod.GET)
    private String LotRedactor(HttpSession session, Model model)
    {
        String lotId = (String) session.getAttribute("lotRid");
        String userName = (String) session.getAttribute("userId");
        Lot lot = lotService.getLot(Long.parseLong(lotId));
        model.addAttribute("bidStatusList", bidStatusList);
        model.addAttribute("statusList", statusList);
        model.addAttribute("lott", lot);
        model.addAttribute("user", userName);
        model.addAttribute("bidResultList", bidResultList);
        model.addAttribute("allBidsList", bidService.getAllBids());
        return "LotRedaction";
    }

    @RequestMapping(value = "/exLots", method = RequestMethod.GET)
    private String exRedactor(HttpSession session, Model model) {
        String exId = (String) session.getAttribute("exRid");
        Exchange exchange = exchangeService.getExchange(Long.parseLong(exId));
        List<Bid> bidList =bidService.getBidsByExchange(exchange);

        List<Lot> lotList =new ArrayList<>();
        for(Bid bid: bidList){
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
    private @ResponseBody List<Asset> getAssetsByInNum(@RequestParam("inn") String inn)
    {
        return assetService.getAssetsByInNum(inn);
    }

    @RequestMapping(value = "/sumById", method = RequestMethod.POST)
    private @ResponseBody String sumById(@RequestParam("idMass") String ids) {
        Formatter f = new Formatter();
        Double sum = 0.;
        String[] idm;
        try {
            idm = ids.substring(1).split(",");
        } catch (IndexOutOfBoundsException e) {
            return "0";
        }
        for (String id : idm) {
            sum += creditService.getCredit(Long.parseLong(id)).getCreditPrice();
        }
        return f.format("%,.0f", sum).toString();
    }

    @RequestMapping(value = "/sumByInvs", method = RequestMethod.POST)
    private @ResponseBody String sumByInvs(@RequestParam("idMass") String ids) {
        BigDecimal sum = new BigDecimal(0.00) ;
        String[] idm;
        try {
            idm = ids.substring(1).split(",");
        }
        catch (IndexOutOfBoundsException e) {
            return "0";
        }
        for (String id : idm) {
            sum = sum.add(assetService.getAsset(Long.parseLong(id)).getRv());
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
        if(asset.getLot()==null){
            return list;
        }
        else {
            Lot lot = asset.getLot();
            BigDecimal coeff = asset.getRv().divide(lotService.lotSum(lot), 10, BigDecimal.ROUND_HALF_UP);
            System.out.println(lotService.lotSum(lot)+" "+asset.getRv()+" "+coeff);
            BigDecimal paySumByAsset = lotService.paymentsSumByLot(lot).multiply(coeff).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal factPriceByAsset = lot.getFactPrice().multiply(coeff).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal residualToPay = factPriceByAsset.subtract(paySumByAsset);
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
    private @ResponseBody List<String> countSumLotsByExchange(@RequestParam("exId") String exId) {
        Exchange exchange = exchangeService.getExchange(Long.parseLong(exId));
        List<Lot> lotsList = lotService.getLotsByExchange(exchange);
        List<String> list = new ArrayList<>();
        BigDecimal lotRV =new BigDecimal(0.00);
        for(Lot lot: lotsList){
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

    @RequestMapping(value = "/delAssetFromLot", method = RequestMethod.POST)
    private @ResponseBody String delCrFromLot(@RequestParam("crdId") String crdId) {
        Asset asset = assetService.getAsset(Long.parseLong(crdId));
        asset.setLot(null);
        boolean isitUpdated = assetService.updateAsset(asset);
        if (isitUpdated)
            return "1";
        else
            return "0";
    }
    @RequestMapping(value = "/changeFondDec", method = RequestMethod.POST)
    private @ResponseBody String changeFondDec(@RequestParam("idList") String idList,
                                               @RequestParam("fondDecDate") String fondDecDate,
                                               @RequestParam("fondDec") String fondDec,
                                               @RequestParam("decNum") String decNum){
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
            assetService.updateAsset(asset);
        }
        return "1";
    }

    @RequestMapping(value = "/updateCreditsInLot", method = RequestMethod.POST)
    private @ResponseBody String updateCreditsInLot(@RequestParam("newPricesId") String newPricesId, @RequestParam("newPrice") String newPrices,
                                                    @RequestParam("factPricesId") String factPricesId, @RequestParam("factPrice") String factPrices,
                                                    @RequestParam("soldId") String soldId ) {
        if(!newPricesId.equals("")) {
        String [] newPricesIdMass = newPricesId.split(",");
        String [] newPricesMass = newPrices.split(",");
            for (int i = 0; i < newPricesIdMass.length; i++) {
                Credit credit = creditService.getCredit(Long.parseLong(newPricesIdMass[i]));
                credit.setDiscountPrice(Double.parseDouble(newPricesMass[i]));
                creditService.updateCredit(credit);

            }
        }

        if(!factPricesId.equals("")) {
        String [] factPricesIdMass = factPricesId.split(",");
        String [] factPricesMass = factPrices.split(",");
            for (int i = 0; i < factPricesIdMass.length; i++) {
                Credit credit = creditService.getCredit(Long.parseLong(factPricesIdMass[i]));
                credit.setFactPrice(Double.parseDouble(factPricesMass[i]));
                creditService.updateCredit(credit);

            }
        }

        if(!soldId.equals("")){
        String [] soldIdMass = soldId.split(",");
        for(String sId : soldIdMass){
            Credit credit = creditService.getCredit(Long.parseLong(sId));
            credit.setIsSold(true);
            creditService.updateCredit(credit);

        }
        }

        return "1";
    }

    @RequestMapping(value = "/selectedParam", method = RequestMethod.POST)
    private @ResponseBody List<String> getParam(@RequestParam("types") String types,
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
    private @ResponseBody String createLot(HttpSession session,
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

        Lot newlot = new Lot(comment, user, new Date());
        lotService.createLot(newlot);
        for (String id : idm) {
            Asset asset = assetService.getAsset(Long.parseLong(id));
            if(asset.getLot()==null)asset.setLot(newlot);
            assetService.updateAsset(asset);
        }
        return "1";
    }

    @RequestMapping(value = "/createBid", method=RequestMethod.GET)
    private @ResponseBody String createBid( @RequestParam("exId") String exId,
                                            @RequestParam("bidDate") String bidD,
                                            @RequestParam("newspaper") String newspaper,
                                            @RequestParam("newsDate1") String newsD1,
                                            @RequestParam("newsDate2") String newsD2,
                                            @RequestParam("registrEnd") String regEnd) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd" , Locale.ENGLISH);
        Date bidDate, newsDate1, newsDate2, registrEnd;
        try {
            bidDate = bidD.equals("") ? null : format.parse(bidD);
            newsDate1 = newsD1.equals("") ? null : format.parse(newsD1);
            newsDate2 = newsD2.equals("") ? null : format.parse(newsD2);
            registrEnd = regEnd.equals("") ? null : format.parse(regEnd);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return "0";
        }
        Exchange exchange = exchangeService.getExchange(Long.parseLong(exId));
        Bid bid = new Bid(bidDate, exchange, newspaper, newsDate1, newsDate2, registrEnd );
        bidService.createBid(bid);
        return "1";
    }

    @RequestMapping(value = "/create", method = {RequestMethod.GET, RequestMethod.HEAD})
    private void creator() {
        System.out.println(assetService.getExchanges());
        /*User empl1 = new User("Київ", "Омельченко Олександр Олександрович", "mer", "a44n73", "admin");
        User empl2 = new User("Київ", "Опанасенко Вячеслав Володимирович", "slava", "111", "manager");
        User empl3 = new User("Київ", "Шніткова Світлана Василівна", "sv", "222", "user");

        Client client1 = new Client("1023456789", "DolgOff", "м.Київ, вул. Січових Стрільців, 17", "02165 м.Київ, вул. Лисківська, 50", "Петров Віктор Михайлович", "14056", "dolg@gmail.com");
        Client client2 = new Client("1123456789", "Bezdolgov", "м.Київ, вул. Лисківська, 50", "02165 м.Київ, вул. Січових Стрільців, 10", "Сидоров Віктор Михайлович", "20-453", "dolg@ukr.net");
        Client client3 = new Client("2123456789", "Collector", "м.Київ, вул. Січових Стрільців, 15", "02165 м.Київ, вул. Січових Стрільців, 10", "Іванов Віктор Михайлович", "20-441", "coll@gmail.com");
        Client client4 = new Client("3123456789", "UKG", "м.Київ, вул. Ломоносова, 11б", "02165 м.Київ, вул. Січових Стрільців, 10", "Ярош Дмитро Михайлович", "20-551", "ukg@gmail.com");
        Exchange exchange1 = new Exchange("53456789", "Біржа1", "м.Київ, вул. Хрещатик, 17", "02145 м.Київ, вул. Хрещатик, 50", "Петров Віктор Михайлович", "14056", "bir1@gmail.com");
        Exchange exchange2 = new Exchange("51236789", "Біржа2", "м.Київ, вул. Липська, 50", "02115 м.Київ, вул. Нова, 10", "Сидоров Віктор Михайлович", "20-453", "bir2@ukr.net");
        Exchange exchange3 = new Exchange("51234589", "Біржа3", "м.Київ, вул. Нова, 15", "02100 м.Київ, вул. Липська, 10", "Іванов Віктор Михайлович", "20-441", "bir3@gmail.com");
        Exchange exchange4 = new Exchange("51234567", "Біржа4", "м.Київ, вул. Якась, 11б", "02365 м.Київ, вул. Якась, 10", "Ярош Дмитро Михайлович", "20-551", "bir4@gmail.com");
        userService.createUser(empl1);
        userService.createUser(empl2);
        userService.createUser(empl3);
        exchangeService.createExchange(exchange1);
        exchangeService.createExchange(exchange2);
        exchangeService.createExchange(exchange3);
        exchangeService.createExchange(exchange4);
        Bid bid1 = new Bid();
        Bid bid2 = new Bid();
        Bid bid3 = new Bid();
        bidService.createBid(bid1);
        bidService.createBid(bid2);
        bidService.createBid(bid3);*/
    }

}