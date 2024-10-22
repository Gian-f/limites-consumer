package br.com.coffeeandit.repository;

import br.com.coffeeandit.entity.Limite;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;

@ApplicationScoped
public class LimiteRepository {

    @ConfigProperty(name = "limite.valorTotal")
    BigDecimal valorTotal;

    public Uni<Limite> buscarLimiteDiario(final Long codigoAgencia, final Long codigoConta) {
        return buscarLimitePorData(codigoAgencia, codigoConta, LocalDate.now());
    }

    public Limite inserirLimiteDiario(final Long agencia, Long conta) {
        var limiteDiario = new Limite();
        limiteDiario.setAgencia(agencia);
        limiteDiario.setConta(conta);
        limiteDiario.setValor(valorTotal);
        limiteDiario.setData(LocalDate.now());
        Panache.withTransaction(limiteDiario::persist)
                .replaceWith(limiteDiario)
                .ifNoItem()
                .after(Duration.ofSeconds(5L))
                .fail()
                .onFailure()
                .transform(IllegalArgumentException::new);

        return limiteDiario;
    }

    public Uni<Limite> buscarLimitePorData(final Long codigoAgencia, final Long codigoConta, final LocalDate data) {
        var params = new HashMap<String, Object>();
        params.put("agencia", codigoAgencia);
        params.put("conta", codigoConta);
        params.put("data", data);

        try {
            return Limite.find("agencia= :agencia and conta= :conta and data=:data", params).firstResult();
        } catch (NoResultException e) {
            return Uni.createFrom().item(inserirLimiteDiario(codigoAgencia, codigoConta));
        }
    }
}
