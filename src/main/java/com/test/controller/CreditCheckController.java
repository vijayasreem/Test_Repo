
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreditCheckController {

    private final CreditCheckService creditCheckService;

    @Autowired
    public CreditCheckController(CreditCheckService creditCheckService) {
        this.creditCheckService = creditCheckService;
    }

    @GetMapping("/prequalify/{applicantId}")
    public String preQualifyApplicant(@PathVariable String applicantId) {
        try {
            creditCheckService.preQualifyApplicant(applicantId);
            return "Pre-qualification process completed successfully.";
        } catch (RuntimeException e) {
            return "Error: " + e.getMessage();
        }
    }
}
