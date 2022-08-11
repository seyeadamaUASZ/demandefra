package sn.gainde2000.pi.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.gainde2000.pi.core.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
