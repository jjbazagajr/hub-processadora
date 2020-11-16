package br.com.hub.avaliacao.service;

import br.com.hub.avaliacao.dto.WithDrawDTO;
import br.com.hub.avaliacao.entity.CreditCard;
import br.com.hub.avaliacao.entity.ResultTransaction;
import br.com.hub.avaliacao.entity.ResultTransactionEnum;
import br.com.hub.avaliacao.entity.Transaction;
import br.com.hub.avaliacao.repository.CreditCardRepository;
import br.com.hub.avaliacao.repository.ResultTransactionRepository;
import br.com.hub.avaliacao.repository.TransactionRepository;
import br.com.hub.avaliacao.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class WithDrawServiceImpl implements WithDrawService {

    @Autowired
    private ResultTransactionRepository resultTransactionRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public ResultTransaction sacar(WithDrawDTO withDraw) {
        CreditCard creditCard = creditCardRepository.findByCardNumber(withDraw.getCardNumber()).block();
        ResultTransactionEnum resultado = ResultTransactionEnum.APROVADA;
        ResultTransaction resultTransaction = new ResultTransaction();
        resultTransaction.setCode(resultado.getCode());
        try {
            if (creditCard == null) {
                resultTransaction.setCode(ResultTransactionEnum.CONTA_INVALIDA.getCode());

            } else if (withDraw.getAmount() > creditCard.getAvailableAmount()) {
                resultTransaction.setCode(ResultTransactionEnum.SALDO_INSUFICIENTE.getCode());
            }
            resultTransaction.setAction(withDraw.getAction());
            if (resultTransaction.getCode().equals(ResultTransactionEnum.APROVADA.getCode())) {
                resultTransaction.setAuthorizationCode(NumberUtil.getRandomNumberString());
            }
        } catch (Exception e) {
            resultTransaction.setCode((ResultTransactionEnum.ERRO_PROCESSAMENTO.getCode()));
        }
        salvaTransacao(withDraw, resultTransaction);
        atualizaCartao(creditCard, resultTransaction);
        resultTransactionRepository.save(resultTransaction).subscribe();
        return resultTransaction;
    }

    @Override
    public void salvaTransacao(WithDrawDTO withDraw, ResultTransaction resultTransaction) {
        if (resultTransaction.getCode().equals(ResultTransactionEnum.APROVADA.getCode())) {
            Transaction transaction = new Transaction(
                    UUID.randomUUID().toString(),
                    new Date(),
                    withDraw.getAmount(),
                    null
            );
            transactionRepository.save(transaction).subscribe();
            resultTransaction.setTransaction(transaction);
        }
    }

    @Override
    public void atualizaCartao(CreditCard creditCard, ResultTransaction resultTransaction) {
        if (creditCard != null) {
            if (resultTransaction.getTransaction() != null) {
                creditCard.getTransactions().addAll(Arrays.asList(resultTransaction.getTransaction()));
                Double valorFinal = creditCard.getAvailableAmount() - resultTransaction.getTransaction().getAmount();
                creditCard.setAvailableAmount(valorFinal);
            }
            creditCardRepository.save(creditCard).subscribe();
        }
    }


}
