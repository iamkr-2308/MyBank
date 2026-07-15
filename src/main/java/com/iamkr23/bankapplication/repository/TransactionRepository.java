//package com.iamkr23.bankapplication.repository;
//
//import com.iamkr23.bankapplication.entity.Account;
//import com.iamkr23.bankapplication.entity.Transaction;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//
//    List<Transaction> findByAccountOrderByTxnDateDesc(Account account);
//}
//

package com.iamkr23.bankapplication.repository;

import com.iamkr23.bankapplication.entity.Account;
import com.iamkr23.bankapplication.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByAccountOrderByTxnDateDesc(Account account, Pageable pageable);
}