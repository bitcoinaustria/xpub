import org.bitcoinj.core.Address;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.params.MainNetParams;

public class XPubTool {

    public static final String SAMPLE = "xpub6BkJWUzGLCNpraMs78UmJtU388Z6SkTABhmeRxrew8mYpWCf7ah4pVLWwtSR8KpESsiknB5CNqSYGSmLzRBUYY7qhSXnfeCjJU2hpLhghwr";

    /*
    sample seed:
    cancel upper word cushion ball ostrich agent soon right rigid all floor riot labor inmate
    sample xPub
    xpub6BkJWUzGLCNpraMs78UmJtU388Z6SkTABhmeRxrew8mYpWCf7ah4pVLWwtSR8KpESsiknB5CNqSYGSmLzRBUYY7qhSXnfeCjJU2hpLhghwr

    m/44'/0'/0'/0/0	15DCfrEYAx2fq4xw54FTCdZwyztPEm1wFH
    m/44'/0'/0'/0/1	1DWxD3zb31GuBi8v3z19VPRMu7C9f7FqVs

     */
    public static void main(String[] args) {
        String xPub = SAMPLE;
        generateAddresses(xPub);
    }

    private static void generateAddresses(String xPub) {
        DeterministicKey pubKey = DeterministicKey.deserializeB58(xPub, MainNetParams.get());
        DeterministicKey receiveAddressesPub = derivePub(pubKey, 0);
        for (int i = 0; i < 1000; i++) {
            DeterministicKey paymentAddr = derivePub(receiveAddressesPub, i);
            Address addr = LegacyAddress.fromPubKeyHash(MainNetParams.get(),paymentAddr.getPubKeyHash());
            System.out.println("address \t" + i + "\t" + addr);
        }
    }

    private static DeterministicKey derivePub(DeterministicKey pubKey, int index) {
        return HDKeyDerivation.deriveChildKey(pubKey, new ChildNumber(index, false));
    }
}
