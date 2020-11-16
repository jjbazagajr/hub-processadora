package br.com.hub.avaliacao.service;

import br.com.hub.avaliacao.dto.WithDrawDTO;
import br.com.hub.avaliacao.entity.CreditCard;
import br.com.hub.avaliacao.entity.ResultTransaction;

public interface WithDrawService {
    ResultTransaction sacar(WithDrawDTO withDraw);
    void salvaTransacao(WithDrawDTO withDraw, ResultTransaction resultTransaction);
    void atualizaCartao(CreditCard creditCard, ResultTransaction resultTransaction);
}
