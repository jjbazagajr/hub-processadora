package br.com.hub.avaliacao.service;

import br.com.hub.avaliacao.entity.CreditCard;
import reactor.core.publisher.Flux;

public interface CreditCardService {

    Flux<CreditCard> findAll();

}
