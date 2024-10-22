package br.com.coffeeandit.business;

import br.com.coffeeandit.model.Transaction;
import br.com.coffeeandit.repository.LimiteRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@ApplicationScoped
public class LimiteDiarioBusiness {


    @Inject
    LimiteRepository limiteRepository;


    @Transactional
    public Transaction limiteDiario(final Transaction transactionDTO) {

        limiteRepository.buscarLimiteDiario(
                        transactionDTO.getAgencia(),
                        transactionDTO.getConta())
                .subscribe().with(limiteDiario -> {


                    if (Objects.isNull(limiteDiario)) {
                        limiteDiario = limiteRepository.inserirLimiteDiario(transactionDTO.getAgencia(),
                                transactionDTO.getConta());
                    }

                    Log.info("Limite diario da conta " + limiteDiario.getValor());

                    if (limiteDiario.getValor().compareTo(transactionDTO.getValor()) < 0) {

                        transactionDTO.suspeitaFraude();
                        Log.info("Transação excede valor diario " + transactionDTO);


                    } else if (transactionDTO.getValor().compareTo(BigDecimal.valueOf(10000L)) > 0) {

                        transactionDTO.analiseHumana();
                        Log.info("Transação está em Análise Humana " + transactionDTO);


                    } else {
                        transactionDTO.analisada();
                        Log.info("Transação analisada " + transactionDTO);
                        limiteDiario.setValor(limiteDiario.getValor().subtract(transactionDTO.getValor()));
                        Log.info("Limite diario da conta " + limiteDiario.getValor());

                    }
                });


        return transactionDTO;


    }
}