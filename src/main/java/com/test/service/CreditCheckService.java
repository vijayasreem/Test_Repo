
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class CreditCheckService {

    private final CreditBureauService creditBureauService;
    private final LoanCriteriaService loanCriteriaService;
    private final NotificationService notificationService;
    private final DataStorageService dataStorageService;
    private final Duration timeFrame = Duration.ofHours(24);

    @Autowired
    public CreditCheckService(CreditBureauService creditBureauService, LoanCriteriaService loanCriteriaService,
                              NotificationService notificationService, DataStorageService dataStorageService) {
        this.creditBureauService = creditBureauService;
        this.loanCriteriaService = loanCriteriaService;
        this.notificationService = notificationService;
        this.dataStorageService = dataStorageService;
    }

    public void preQualifyApplicant(String applicantId) {
        LocalDateTime startTime = LocalDateTime.now();
        CreditData creditData = performCreditCheck(applicantId);
        LoanOffer loanOffer = loanCriteriaService.calculateLoanOffer(creditData.getScore());
        notificationService.notifyApplicant(applicantId, loanOffer);
        LocalDateTime endTime = LocalDateTime.now();
        if (Duration.between(startTime, endTime).compareTo(timeFrame) > 0) {
            throw new RuntimeException("Pre-qualification process exceeded time frame");
        }
    }

    private CreditData performCreditCheck(String applicantId) {
        CreditData creditData = creditBureauService.getCreditScoreAndHistory(applicantId);
        dataStorageService.storeCreditDataSecurely(applicantId, creditData);
        return creditData;
    }
}
