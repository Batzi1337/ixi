package org.iota.ixi;

import org.iota.ict.eee.Environment;
import org.iota.ict.ixi.Ixi;
import org.iota.ict.ixi.IxiModule;
import org.iota.ict.model.transaction.Transaction;
import org.iota.ict.model.transaction.TransactionBuilder;
import org.iota.ict.network.gossip.GossipEvent;
import org.iota.ict.network.gossip.GossipListener;

/**
 * This is an example IXI module. Use it as template to implement your own
 * module. Run Main.main() to test it. Do not move this class into a different
 * package. If you rename it, update your module.json, Use this constructor only
 * for initialization because it blocks Ict. Instead, use run() as main thread.
 * https://github.com/iotaledger/ixi
 */
public class Module extends IxiModule {

    public Module(Ixi ixi) {
        super(ixi);
        GossipListener listener = new GossipListener.Implementation() {

            @Override
            public void onReceive(GossipEvent effect) {
                String message = (effect.isOwnTransaction() ? "SUBMITTED >>> " : "RECEIVED  <<< ")
                        + effect.getTransaction().decodedSignatureFragments();
                System.out.println(message);
            }
        };
        ixi.addListener(listener);
    }

    public void run() {
        // submit a new transaction
        TransactionBuilder builder = new TransactionBuilder();
        builder.asciiMessage("Hello World!");
        Transaction toSubmit = builder.build();
        ixi.submit(toSubmit);
    }
}

/**
 * A custom gossip listener which prints every message submitted or received.
 */
class CutomGossipListener implements GossipListener {
    @Override
    public void onReceive(GossipEvent effect) {
        String message = (effect.isOwnTransaction() ? "SUBMITTED >>> " : "RECEIVED  <<< ")
                + effect.getTransaction().decodedSignatureFragments();
        System.out.println(message);
    }

    @Override
    public Environment getEnvironment() {
        return null;
    }
}