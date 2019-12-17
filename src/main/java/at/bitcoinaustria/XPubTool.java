package at.bitcoinaustria;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.params.MainNetParams;

import java.util.ArrayList;
import java.util.List;

public class XPubTool {

    public static final String SAMPLE = "xpub6BkJWUzGLCNpraMs78UmJtU388Z6SkTABhmeRxrew8mYpWCf7ah4pVLWwtSR8KpESsiknB5CNqSYGSmLzRBUYY7qhSXnfeCjJU2hpLhghwr";
    public static final int RECEIVE_PATH = 0;

    /*
    sample seed:
    cancel upper word cushion ball ostrich agent soon right rigid all floor riot labor inmate
    sample xPub
    xpub6BkJWUzGLCNpraMs78UmJtU388Z6SkTABhmeRxrew8mYpWCf7ah4pVLWwtSR8KpESsiknB5CNqSYGSmLzRBUYY7qhSXnfeCjJU2hpLhghwr

    m/44'/0'/0'/0/0	15DCfrEYAx2fq4xw54FTCdZwyztPEm1wFH
    m/44'/0'/0'/0/1	1DWxD3zb31GuBi8v3z19VPRMu7C9f7FqVs

     */
    public static void main(String[] args) {
        List<Address> addresses = generateReceiveAddresses(SAMPLE, 0, 1000);
        for (int i = 0; i < addresses.size(); i++) {
            Address address = addresses.get(i);
            System.out.println("address \t" + i + "\t" + address);
        }
    }

    public static List<Address> generateReceiveAddresses(String xPub, int offset, int count) {
        List<Address> generated = new ArrayList<>();
        DeterministicKey pubKey = DeterministicKey.deserializeB58(xPub, MainNetParams.get());
        DeterministicKey receiveAddressesPub = derivePub(pubKey, RECEIVE_PATH);
        for (int i = offset; i < count; i++) {
            Address addr = getAddress(receiveAddressesPub, i);
            generated.add(addr);
        }
        return generated;
    }


    private static Address getAddress(DeterministicKey receiveAddressesPub, int i) {
        DeterministicKey paymentAddr = derivePub(receiveAddressesPub, i);
        return LegacyAddress.fromPubKeyHash(MainNetParams.get(),paymentAddr.getPubKeyHash());
    }

    private static DeterministicKey derivePub(DeterministicKey pubKey, int index) {
        return HDKeyDerivation.deriveChildKey(pubKey, new ChildNumber(index, false));
    }
}
