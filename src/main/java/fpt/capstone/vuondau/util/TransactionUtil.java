package fpt.capstone.vuondau.util;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Transaction;
import fpt.capstone.vuondau.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionUtil {
    private final TransactionRepository transactionRepository;

    public TransactionUtil(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public boolean isClassPaid(Account student, Class clazz) {
        Transaction transaction = transactionRepository.findByPaymentClassAndAccount(clazz, student);
        if (transaction != null && transaction.getSuccess()) return true;
        return false;
    }
}
