```python
import time
from datetime import datetime, timedelta
from credit_bureau_service import CreditBureauService
from notification_service import NotificationService
from data_storage_service import DataStorageService

class LoanPreQualificationSystem:
    def __init__(self):
        self.credit_service = CreditBureauService()
        self.notification_service = NotificationService()
        self.data_storage_service = DataStorageService()
        self.lending_criteria = {
            'min_score': 600,
            'max_loan_amount': 50000,
            'interest_rate_range': (3.5, 7.5)
        }
        self.time_frame = timedelta(hours=24)

    def perform_credit_check(self, applicant_id):
        credit_data = self.credit_service.get_credit_data(applicant_id)
        self.data_storage_service.store_securely(applicant_id, credit_data)
        return credit_data

    def pre_qualify_applicant(self, applicant_id):
        start_time = datetime.now()
        credit_data = self.perform_credit_check(applicant_id)
        credit_score = credit_data['score']

        if credit_score >= self.lending_criteria['min_score']:
            loan_amount = min(credit_score * 100, self.lending_criteria['max_loan_amount'])
            interest_rate = self.calculate_interest_rate(credit_score)
            status = 'Pre-qualified'
        else:
            loan_amount = 0
            interest_rate = 0
            status = 'Not qualified'

        self.notify_applicant(applicant_id, status, loan_amount, interest_rate)
        end_time = datetime.now()
        assert end_time - start_time <= self.time_frame

    def calculate_interest_rate(self, credit_score):
        min_rate, max_rate = self.lending_criteria['interest_rate_range']
        return min_rate + (max_rate - min_rate) * (700 - credit_score) / 100

    def notify_applicant(self, applicant_id, status, loan_amount, interest_rate):
        message = {
            'status': status,
            'loan_amount': loan_amount,
            'interest_rate': interest_rate
        }
        self.notification_service.send(applicant_id, message)

# Example usage
system = LoanPreQualificationSystem()
system.pre_qualify_applicant('applicant_123')
```
