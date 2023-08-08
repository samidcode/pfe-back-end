package ma.payment.service;

import ma.payment.bean.Payment;
import ma.payment.dao.PaymentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
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
}
