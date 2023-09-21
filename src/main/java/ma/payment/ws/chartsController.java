package ma.payment.ws;

import ma.payment.dto.ChartsDTO;
import ma.payment.service.ChartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/charts")
public class chartsController {

    @Autowired
    private ChartsService chartsService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getCharts() {
        Map<String, Object> charts = chartsService.prepareChartData();
        return ResponseEntity.ok(charts);
    }
    @GetMapping("/getstatistics")
    public ResponseEntity< Map<String, Map<String, Integer>>> getPaymentStatistics() {
        Map<String, Map<String, Integer>> paymentStatistics = chartsService.getPaymentStatistics();
        return ResponseEntity.ok(paymentStatistics);
    }

}
