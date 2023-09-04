package com.fortuna.fabricktest.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fortuna.fabricktest.data.entity.TransactionEntity;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {
	public List<TransactionEntity> findTransactionByDescription(String descr);
}
