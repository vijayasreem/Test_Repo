```python
import time
from datetime import datetime, timedelta
from credit_bureau_service import get_credit_score_and_history
from loan_criteria import calculate_loan_offer
from notification_service import notify_applicant
from data_storage import store_credit_data_securely

class CreditCheckSystem:
    def __init__(self, applicant_id):
        self.applicant_id = applicant_id
        self.credit_score = None
        self.credit_history = None
        self.loan_offer = None

    def perform_credit_check(self):
        self.credit_score, self.credit_history = get_credit_score_and_history(self.applicant_id)
        store_credit_data_securely(self.applicant_id, self.credit_score, self.credit_history)

    def pre_qualify(self):
        self.loan_offer = calculate_loan_offer(self.credit_score)

    def notify_applicant(self):
        notify_applicant(self.applicant_id, self.loan_offer)

    def process_application(self):
        start_time = datetime.now()
        self.perform_credit_check()
        self.pre_qualify()
        self.notify_applicant()
        end_time = datetime.now()
        assert end_time - start_time < timedelta(hours=24), "Process exceeded time frame"

# Example usage
applicant_id = "12345"
credit_check_system = CreditCheckSystem(applicant_id)
credit_check_system.process_application()
```
