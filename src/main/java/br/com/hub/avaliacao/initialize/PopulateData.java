package br.com.hub.avaliacao.initialize;

import br.com.hub.avaliacao.entity.CreditCard;
import br.com.hub.avaliacao.entity.ResultTransaction;
import br.com.hub.avaliacao.entity.Transaction;
import br.com.hub.avaliacao.repository.CreditCardRepository;
import br.com.hub.avaliacao.repository.ResultTransactionRepository;
import br.com.hub.avaliacao.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;


@Slf4j
@Component
public class PopulateData implements CommandLineRunner {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ResultTransactionRepository resultTransactionRepository;

    @Override
    public void run(String... args) throws Exception {

        transactionRepository.deleteAll().subscribe();
        resultTransactionRepository.deleteAll().subscribe();

        creditCardRepository.deleteAll()
                .thenMany(
                        Flux.just("5223025221438234", "5384050025777947")
                                .map(numero -> new CreditCard(
                                        null,
                                        numero,
                                        1000.00,
                                        new ArrayList<>()
                                ))
                                .flatMap(creditCardRepository::save))
                .subscribe(System.out::println);

        log.info("Cartões iniciais criados com sucesso.");
    }
}
