package ma.payment.service;


import ma.payment.bean.Classe;
import ma.payment.bean.Payment;
import ma.payment.dao.PaymentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PaymentService {
    private final PaymentDao paymentDao;
    @Autowired
    public PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public List<Payment> getAllPayments() {
        return paymentDao.findAll();
    }

    public Optional<Payment> getPaymentById(int id) {
        return paymentDao.findById(id);
    }

    public Payment savePayment(Payment payment) {
        return paymentDao.save(payment);
    }

    public void deletePayment(int id) {
        paymentDao.deleteById(id);
    }

    public List<Payment> getPaymentsForEleve(Integer eleveId) {
        return paymentDao.findByEleveId(eleveId);
    }


    public boolean hasPaidForLastMonth(Integer eleveId) {

        ZonedDateTime serverDateTime = ZonedDateTime.now();

        String monthName = serverDateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.FRANCE);
        return paymentDao.existsByEleveIdAndMoisP(eleveId,monthName);


    }
    public Page<Payment> paymentPagination(int page , int size) {

        Pageable pageable = PageRequest.of(page, size);
        return paymentDao.findAll(pageable);

    }

    public Optional<Payment>  getPaymentByMonth(String moisP, String yearP, Integer eleveId, String objet){

        return paymentDao.findByMoisPAndYearPAndEleve_IdAndObjet(moisP, yearP, eleveId, objet);


    }

    public List<Payment> searchPayment(String keyword) {

        return paymentDao.searchPaymentsByKeyword(keyword);


    }
}
