package fpt.capstone.vuondau.util;

import fpt.capstone.vuondau.entity.Account;
import fpt.capstone.vuondau.entity.Class;
import fpt.capstone.vuondau.entity.Transaction;
import fpt.capstone.vuondau.repository.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionUtil {
    private final TransactionRepository transactionRepository;

    public TransactionUtil(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public boolean isClassPaid(Account student, Class clazz) {
        List<Transaction> transactions = transactionRepository.findByPaymentClassAndAccount(clazz, student);
        long count = transactions.stream()
                .filter(transaction -> transaction.getSuccess() != null)
                .filter(Transaction::getSuccess).count();
        if (count==1) return true;
        return false;
    }
}
