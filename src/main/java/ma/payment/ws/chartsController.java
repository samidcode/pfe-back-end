package ma.payment.ws;

import ma.payment.dto.ChartsDTO;
import ma.payment.service.ChartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/charts")
public class chartsController {

    @Autowired
    private ChartsService chartsService;

    @GetMapping
    public ResponseEntity<ChartsDTO> getGitHubIssues() {
        ChartsDTO charts = chartsService.getCharts();
        return ResponseEntity.ok(charts);
    }

}
