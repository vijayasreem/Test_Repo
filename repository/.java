```python
import time
from datetime import datetime, timedelta
from credit_bureau_service import get_credit_score_and_history
from loan_criteria import calculate_loan_offer
from notification_service import notify_applicant
from data_storage import store_credit_data_securely

class CreditCheckSystem:
    def __init__(self):
        self.time_frame = timedelta(hours=24)

    def perform_credit_check(self, applicant_id):
        credit_data = get_credit_score_and_history(applicant_id)
        store_credit_data_securely(applicant_id, credit_data)
        return credit_data

    def pre_qualify_applicant(self, applicant_id):
        start_time = datetime.now()
        credit_data = self.perform_credit_check(applicant_id)
        loan_offer = calculate_loan_offer(credit_data['score'])
        notify_applicant(applicant_id, loan_offer)
        end_time = datetime.now()
        if end_time - start_time > self.time_frame:
            raise Exception("Pre-qualification process exceeded time frame")

# Example usage
credit_system = CreditCheckSystem()
credit_system.pre_qualify_applicant('applicant_123')
```
