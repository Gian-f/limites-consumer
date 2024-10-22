package br.com.coffeeandit.events;

import br.com.coffeeandit.model.Transaction;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class TransactionEvent {

    @Incoming("transacao")
    public void processarTransacao(final Transaction transaction) {
        Log.info(transaction);
    }
}
