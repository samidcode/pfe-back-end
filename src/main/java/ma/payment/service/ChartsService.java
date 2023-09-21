package ma.payment.service;

import ma.payment.bean.Payment;

import ma.payment.dao.PaymentDao;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ChartsService {


    // Your method to fetch payments for a specific date range
    private final PaymentDao paymentDao;

    @Autowired
    public ChartsService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public List<Payment> getPaymentsByDateRange(Date startDate, Date endDate) {
        // Call the instance method on your repository to fetch payments by date range
        return paymentDao.findByDateDeCreationBetween(startDate, endDate);
    }

    public Map<String, Object> prepareChartData() {
        Map<String, Object> chartData = new HashMap<>();
        Map<String, Object> overviewData = new HashMap<>();
        Map<String, Object> thisWeekData = new HashMap<>();
        Map<String, Object> lastWeekData = new HashMap<>();
        Map<String, Object> seriesData = new HashMap<>();

        // Calculate start and end dates for "this week" and "last week"
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 1=Sunday, 2=Monday, ...
        int daysToAddThisWeek = 8 - currentDayOfWeek; // Days remaining in the current week



        cal.add(Calendar.DAY_OF_YEAR, daysToAddThisWeek); // Move to the end of the current week
        Date thisWeekEndDate = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, -7); // Move back one week
        Date lastWeekStartDate = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, -1); // Move to the end of last week
        Date lastWeekEndDate = cal.getTime();
        cal.setTime(thisWeekEndDate);
        cal.add(Calendar.DAY_OF_YEAR, -6); // Move back 6 days to get the start of "this week"
        Date thisWeekStartDate = cal.getTime();
        // Fetch payment data for "this week" and "last week"
        List<Payment> thisWeekPayments = getPaymentsByDateRange(thisWeekStartDate, thisWeekEndDate);
        List<Payment> lastWeekPayments = getPaymentsByDateRange(lastWeekStartDate, lastWeekEndDate);

        // Calculate daily statistics for "this week" and "last week"
        Map<String, Object> thisWeekStats = calculateDailyStatistics(thisWeekPayments);
        Map<String, Object> lastWeekStats = calculateDailyStatistics(lastWeekPayments);

        // Prepare data for ApexCharts
        thisWeekData.put("new-payment", thisWeekStats.get("new-payment"));
        thisWeekData.put("montant-totale", thisWeekStats.get("montant-totale"));
        lastWeekData.put("new-payment", lastWeekStats.get("new-payment"));
        lastWeekData.put("montant-totale", lastWeekStats.get("montant-totale"));

        seriesData.put("this-week", prepareSeriesData(thisWeekStats, "this week"));
        seriesData.put("last-week", prepareSeriesData(lastWeekStats, "last week"));

        overviewData.put("this-week", thisWeekData);
        overviewData.put("last-week", lastWeekData);

        chartData.put("overview", overviewData);
        chartData.put("labels", prepareLabels());
        chartData.put("series", seriesData);

        return chartData;
    }

    private Map<String, Object> calculateDailyStatistics(List<Payment> payments) {
        Map<String, Object> stats = new HashMap<>();
        int newPaymentCount = payments.size();
        int totalMontant = 0;

        for (Payment payment : payments) {
            totalMontant += payment.getMontant();
        }

        stats.put("new-payment", newPaymentCount);
        stats.put("montant-totale", totalMontant);

        return stats;
    }

    private List<String> prepareLabels() {
        List<String> labels = new ArrayList<>();
        // Add labels for each day of the week (e.g., Mon, Tue, Wed, ...)
        // You can customize this based on your requirements
        labels.add("lun");
        labels.add("mar");
        labels.add("mer");
        labels.add("jeu");
        labels.add("ven");
        labels.add("sam");
        labels.add("dim");
        return labels;
    }

    private List<Map<String, Object>> prepareSeriesData(Map<String, Object> stats, String name) {

        List<Integer> dailyPaymentCounts = fetchDailyPaymentCountsFromDatabase();

        List<Map<String, Object>> seriesData = new ArrayList<>();
        // Prepare data for "New payment" and "montant-totale"
        Map<String, Object> newPaymentData = new HashMap<>();
        newPaymentData.put("name", "New payment");
        newPaymentData.put("type", "line");
        newPaymentData.put("data",dailyPaymentCounts);
        seriesData.add(newPaymentData);

        List<Integer> dailyMontantTotaleData = fetchDailyMontantTotaleDataFromDatabase();

        Map<String, Object> montantTotaleData = new HashMap<>();
        montantTotaleData.put("name", "montant-totale");
        montantTotaleData.put("type", "column");
        montantTotaleData.put("data",dailyMontantTotaleData);
        seriesData.add(montantTotaleData);

        return seriesData;
    }


    private List<Integer> fetchDailyMontantTotaleDataFromDatabase() {
        List<Integer> dailyMontantTotaleData = new ArrayList<>();

        // Calculate start and end dates for the current week
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        // Move to the beginning of the week (Monday)
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date weekStartDate = cal.getTime();

        // Move to the end of the week (Sunday)
        cal.add(Calendar.DAY_OF_YEAR, 6);
        Date weekEndDate = cal.getTime();

        // Fetch payments for the current week
        List<Payment> payments = getPaymentsByDateRange(weekStartDate, weekEndDate);

        // Initialize dailyMontantTotaleData with zeros for each day of the week (Monday to Sunday)
        for (int i = 0; i < 7; i++) {
            dailyMontantTotaleData.add(0);
        }

        // Populate dailyMontantTotaleData with actual data
        for (Payment payment : payments) {
            cal.setTime(payment.getDateDeCreation());
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 1=Sunday, 2=Monday, ...
            int montantTotale = dailyMontantTotaleData.get(dayOfWeek - 2) + payment.getMontant();
            dailyMontantTotaleData.set(dayOfWeek - 2, montantTotale);
        }

        return dailyMontantTotaleData;
    }

    private List<Integer> fetchDailyPaymentCountsFromDatabase() {
        List<Integer> dailyPaymentCounts = new ArrayList<>();

        // Calculate start and end dates for the current week
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        // Move to the beginning of the week (Monday)
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date weekStartDate = cal.getTime();

        // Move to the end of the week (Sunday)
        cal.add(Calendar.DAY_OF_YEAR, 6);
        Date weekEndDate = cal.getTime();

        // Fetch payments for the current week
        List<Payment> payments = getPaymentsByDateRange(weekStartDate, weekEndDate);

        // Initialize dailyPaymentCounts with zeros for each day of the week (Monday to Sunday)
        for (int i = 0; i < 7; i++) {
            dailyPaymentCounts.add(0);
        }

        // Populate dailyPaymentCounts with the count of payments
        for (Payment payment : payments) {
            cal.setTime(payment.getDateDeCreation());
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 1=Sunday, 2=Monday, ...
            int count = dailyPaymentCounts.get(dayOfWeek - 2) + 1;
            dailyPaymentCounts.set(dayOfWeek - 2, count);
        }

        return dailyPaymentCounts;
    }





    public Map<String, Map<String, Integer>> getPaymentStatistics() {
        Map<String, Map<String, Integer>> result = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date yesterday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date twoDaysAgo = calendar.getTime();

        // Fetch payment data for "today," "yesterday," and "2 days ago"
        Optional<Integer> elevePayentToday = paymentDao.countPaymentsByDate(currentDate);
        Optional<Integer> sizeOfPaymentToday = paymentDao.sumSizeOfPaymentsByDate(currentDate);
        Optional<Integer> sumInscriptionToday = paymentDao.sumInscription(currentDate);

        Optional<Integer> elevePayentYestrday = paymentDao.countPaymentsByDate(yesterday);
        Optional<Integer> sizeOfPaymentYestrday = paymentDao.sumSizeOfPaymentsByDate(yesterday);
        Optional<Integer> sumInscriptionYestrday = paymentDao.sumInscription(yesterday);

        Optional<Integer> elevePayent2DayAgo = paymentDao.countPaymentsByDate(twoDaysAgo);
        Optional<Integer> sizeOfPayment2DayAgo = paymentDao.sumSizeOfPaymentsByDate(twoDaysAgo);
        Optional<Integer> sumInscription2DayAgo = paymentDao.sumInscription(twoDaysAgo);

        // Build the JSON response
        Map<String, Integer> todayStats = new HashMap<>();
        todayStats.put("elevePayentToday", elevePayentToday.orElse(0));
        todayStats.put("sizeOfPaymentToday", sizeOfPaymentToday.orElse(0));
        todayStats.put("totaleOfInscriptionToday", sumInscriptionToday.orElse(0));

        Map<String, Integer> yesterdayStats = new HashMap<>();
        yesterdayStats.put("elevePayentYestrday", elevePayentYestrday.orElse(0));
        yesterdayStats.put("sizeOfPaymentYestrday", sizeOfPaymentYestrday.orElse(0));
        yesterdayStats.put("totaleOfInscriptionYestrday", sumInscriptionYestrday.orElse(0));

        Map<String, Integer> twoDaysAgoStats = new HashMap<>();
        twoDaysAgoStats.put("elevePayentDayAgo", elevePayent2DayAgo.orElse(0));
        twoDaysAgoStats.put("sizeOfPaymentDayAgo", sizeOfPayment2DayAgo.orElse(0));
        twoDaysAgoStats.put("totaleOfInscriptionDayAgo", sumInscription2DayAgo.orElse(0));

        result.put("today", todayStats);
        result.put("yesterday", yesterdayStats);
        result.put("DayAgo", twoDaysAgoStats);

        return result;
    }

}
