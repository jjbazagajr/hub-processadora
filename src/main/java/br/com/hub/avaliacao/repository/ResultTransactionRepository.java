package br.com.hub.avaliacao.repository;

import br.com.hub.avaliacao.entity.ResultTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultTransactionRepository extends ReactiveMongoRepository<ResultTransaction, String> {
}
