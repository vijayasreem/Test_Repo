```python
import time
from datetime import datetime, timedelta
from credit_bureau_service import get_credit_score_and_history
from loan_criteria import calculate_loan_offer
from notification_service import notify_applicant
from data_storage import store_credit_data_securely

class CreditCheckSystem:
    def __init__(self, max_processing_time_hours=24):
        self.max_processing_time = timedelta(hours=max_processing_time_hours)

    def perform_credit_check(self, applicant_id):
        start_time = datetime.now()
        
        # Retrieve credit score and history
        credit_data = get_credit_score_and_history(applicant_id)
        if not credit_data:
            return False
        
        # Calculate loan offer based on credit score
        loan_offer = calculate_loan_offer(credit_data['score'])
        
        # Check if processing is within the allowed time frame
        if datetime.now() - start_time > self.max_processing_time:
            return False
        
        # Notify applicant of pre-qualification status
        notify_applicant(applicant_id, loan_offer)
        
        # Securely store credit check data
        store_credit_data_securely(applicant_id, credit_data)
        
        return True

# Example usage
credit_check_system = CreditCheckSystem()
applicant_id = '12345'
credit_check_system.perform_credit_check(applicant_id)
```
