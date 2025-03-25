```python
import time
from datetime import datetime, timedelta
from credit_bureau import CreditBureauService
from notification_service import NotificationService
from data_storage import SecureDataStorage

class LoanPreQualificationSystem:
    def __init__(self, credit_service, notification_service, data_storage):
        self.credit_service = credit_service
        self.notification_service = notification_service
        self.data_storage = data_storage
        self.lending_criteria = {
            'min_score': 600,
            'max_loan_amount': 50000,
            'interest_rate_range': (3.5, 7.5)
        }
        self.response_time_limit = timedelta(hours=24)

    def perform_credit_check(self, applicant_id):
        credit_data = self.credit_service.get_credit_data(applicant_id)
        self.data_storage.store_securely(applicant_id, credit_data)
        return credit_data

    def pre_qualify_applicant(self, applicant_id):
        credit_data = self.perform_credit_check(applicant_id)
        score = credit_data['score']
        if score >= self.lending_criteria['min_score']:
            loan_amount = min(score * 100, self.lending_criteria['max_loan_amount'])
            interest_rate = self.calculate_interest_rate(score)
            status = 'Pre-qualified'
        else:
            loan_amount = 0
            interest_rate = 0
            status = 'Not qualified'
        
        self.notify_applicant(applicant_id, status, loan_amount, interest_rate)

    def calculate_interest_rate(self, score):
        min_rate, max_rate = self.lending_criteria['interest_rate_range']
        return min_rate + (max_rate - min_rate) * (700 - score) / 100

    def notify_applicant(self, applicant_id, status, loan_amount, interest_rate):
        message = {
            'status': status,
            'loan_amount': loan_amount,
            'interest_rate': interest_rate
        }
        self.notification_service.send(applicant_id, message)

    def process_applicant(self, applicant_id):
        start_time = datetime.now()
        self.pre_qualify_applicant(applicant_id)
        end_time = datetime.now()
        if end_time - start_time > self.response_time_limit:
            raise Exception("Pre-qualification process exceeded time limit")

# Example usage
credit_service = CreditBureauService()
notification_service = NotificationService()
data_storage = SecureDataStorage()

loan_system = LoanPreQualificationSystem(credit_service, notification_service, data_storage)
loan_system.process_applicant('applicant_123')
```