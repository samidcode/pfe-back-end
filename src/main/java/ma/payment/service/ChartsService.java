package ma.payment.service;

import ma.payment.bean.Payment;
import ma.payment.charts.ChartSeries;
import ma.payment.charts.Overview;
import ma.payment.charts.WeekInfo;
import ma.payment.dao.PaymentDao;
import ma.payment.dto.ChartsDTO;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChartsService {

    private final PaymentDao PaymentDao;


    public ChartsService(ma.payment.dao.PaymentDao paymentDao) {
        PaymentDao = paymentDao;
    }

    public ChartsDTO getCharts() {
        ChartsDTO chartsDTO = new ChartsDTO();
        chartsDTO.setOverview(createOverview());
        chartsDTO.setLabels(Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"));
        chartsDTO.setSeries(createSeries());
        return chartsDTO;
    }

    private Overview createOverview() {
        Overview overview = new Overview();
        WeekInfo thisWeek = new WeekInfo();
        WeekInfo lastWeek = new WeekInfo();

        // Fetch data from the database and populate the WeekInfo objects
        LocalDate currentDate = LocalDate.now();
        LocalDate lastWeekStart = currentDate.minusWeeks(1).with(DayOfWeek.MONDAY);
        LocalDate lastWeekEnd = currentDate.minusWeeks(1).with(DayOfWeek.SUNDAY);

        List<Payment> thisWeekPayments = PaymentDao.findByDateBetween(
                currentDate.with(DayOfWeek.MONDAY).toString(),
                currentDate.with(DayOfWeek.SUNDAY).toString()
        );

        List<Payment> lastWeekPayments = PaymentDao.findByDateBetween(
                lastWeekStart.toString(),
                lastWeekEnd.toString()
        );

        thisWeek.setNewPayment(thisWeekPayments.size());
        thisWeek.setMontantTotale(thisWeekPayments.stream().mapToInt(Payment::getMontant).sum());
        // You need to calculate 'totale-payant-eleve' based on your business logic

        lastWeek.setNewPayment(lastWeekPayments.size());
        lastWeek.setMontantTotale(lastWeekPayments.stream().mapToInt(Payment::getMontant).sum());
        // You need to calculate 'totale-payant-eleve' based on your business logic

        overview.setThisWeek(thisWeek);
        overview.setLastWeek(lastWeek);

        return overview;
    }


    private Map<String, List<ChartSeries>> createSeries() {
        Map<String, List<ChartSeries>> series = new HashMap<>();

        List<ChartSeries> thisWeekSeries = new ArrayList<>();
        List<ChartSeries> lastWeekSeries = new ArrayList<>();

        // Fetch and populate the data from the database for the series
        LocalDate currentDate = LocalDate.now();
        LocalDate lastWeekStart = currentDate.minusWeeks(1).with(DayOfWeek.MONDAY);
        LocalDate lastWeekEnd = currentDate.minusWeeks(1).with(DayOfWeek.SUNDAY);

        List<Object[]> thisWeekData = PaymentDao.findPaymentDataByDateDeCreationRange(
                Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).plusDays(6).toInstant())
        );

        List<Object[]> lastWeekData = PaymentDao.findPaymentDataByDateDeCreationRange(
                Date.from(lastWeekStart.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(lastWeekEnd.atStartOfDay(ZoneId.systemDefault()).plusDays(1).toInstant())
        );

        // Create ChartSeries objects and populate them with data
        ChartSeries newPaymentSeriesThisWeek = new ChartSeries();
        newPaymentSeriesThisWeek.setName("New payment");
        newPaymentSeriesThisWeek.setType("line");
        newPaymentSeriesThisWeek.setData(thisWeekData.stream().map(data -> (Integer) data[1]).collect(Collectors.toList()));

        ChartSeries montantTotaleSeriesThisWeek = new ChartSeries();
        montantTotaleSeriesThisWeek.setName("montant-totale");
        montantTotaleSeriesThisWeek.setType("column");
        montantTotaleSeriesThisWeek.setData(thisWeekData.stream().map(data -> (Integer) data[2]).collect(Collectors.toList()));

        thisWeekSeries.add(newPaymentSeriesThisWeek);
        thisWeekSeries.add(montantTotaleSeriesThisWeek);

        // Similar processing for 'last-week' series using lastWeekData

        series.put("this-week", thisWeekSeries);
        series.put("last-week", lastWeekSeries);

        return series;
    }


}
