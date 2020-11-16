package br.com.hub.avaliacao.service;

import br.com.hub.avaliacao.mapper.CreditCardMapper;
import br.com.hub.avaliacao.entity.CreditCard;
import br.com.hub.avaliacao.repository.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private CreditCardMapper creditCardMapper;

    @Override
    public Flux<CreditCard> findAll() {
        return creditCardRepository.findAll().delayElements(Duration.ofMillis(500));
    }
}
