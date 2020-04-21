package com.ihason.dtp.easytrans.demos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TxLogRepository extends JpaRepository<TxLog, Long> {
}
