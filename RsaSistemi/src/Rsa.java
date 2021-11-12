import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;


public class Rsa {

    private static BigInteger lastPrime = new BigInteger("150000000073150000000073150000000073150000000073150000000073");
    private BigInteger p = new BigInteger("47");
    private BigInteger q = new BigInteger("71");
    private BigInteger n = new BigInteger("0");
    private BigInteger e = new BigInteger("0");
    private BigInteger d = new BigInteger("0");
    private BigInteger phi = new BigInteger("0");
    public String keyFriend;

    {lastPrime = new BigInteger(lastPrime.bitLength(), new Random());}

    public Rsa() {
        p = newPrime(lastPrime);
        q = newPrime(lastPrime);
        n = p.multiply(q);
        e = coprimes();
        phi = phi();
        d = e.modInverse(phi);
        System.out.println("e: " + e + " n: " + n);
    }

    public BigInteger findD() {
        BigInteger k = BigInteger.TEN.pow(e.toString().length());
        BigInteger phi = phi();
        while (true) {
            if (k.multiply(e).mod(phi).equals(BigInteger.ONE))
                return k;
            k = k.add(BigInteger.ONE);
        }
    }

    public BigInteger phi() {
        return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
    }

    /*
     * public BigInteger mcm(BigInteger a, BigInteger b){ if
     * (b.equals(BigInteger.ZERO)) return a; return mcm(b,a.mod(b)); }
     */

    public BigInteger coprimes() {
        BigInteger prm = q;
        return newPrime(prm);
    }

    public BigInteger newPrime(BigInteger prime) {
        BigInteger a = new BigInteger("2");
        if(prime.mod(BigInteger.TWO).equals(BigInteger.ZERO))
        prime = prime.add(BigInteger.ONE);
        do {
            prime = prime.add(BigInteger.TWO);
        } while (!a.modPow(prime.subtract(BigInteger.ONE), prime).equals(BigInteger.ONE));
        lastPrime = prime;
        return prime;
    }

    public String getD() {
        return d.toString();
    }

    public String getN() {
        return n.toString();
    }

    public String encrypt(String ss) throws UnsupportedEncodingException {
        String[] supOne = keyFriend.split("/");
        BigInteger publicExp = new BigInteger(supOne[0]);
        BigInteger publicN = new BigInteger(supOne[1]); 
        BigInteger plainText, cypher = new BigInteger("0");
        boolean lengthPrime = false;
        ss = ss.replaceAll("<3", new StringBuilder().appendCodePoint(0x1F497).toString());
        byte[] erbite = ss.getBytes("UTF-8");
        byte[] toSend = new byte[erbite.length+1];
        toSend[0] = 0;
        for(int i = 0; i<erbite.length; i++) 
            toSend[i+1] = erbite[i];
        
        plainText = new BigInteger(toSend); // <n
        /*
        if (plainText.min(n).equals(plainText)){
            plainText = plainText.modPow(e, n); // still <n
            return plainText.toString();
        }
        */
        ss = new String(plainText.toByteArray());
        int l = ss.length();
        //System.out.println(ss.length());
        //System.out.println(ss);
        int part = 1, divisor, cont = 0;
        if(BigInteger.TWO.modPow(new BigInteger(String.valueOf(l-1)), new BigInteger(String.valueOf(ss.length()))).equals(BigInteger.ONE)){
            lengthPrime = true;
            l = l+1;
        }

        for(int i = 2; i <= l/2; i++) {
            if(l % i == 0){
                part = i;
                cont++;
            }
        }
        
        int everyN = l/part;
        //System.out.println("l: " + l + " part: " + part + " everyN: " + everyN);
        cont = 0;
        String cypherText = "";
        String supp = "";
        for(char c : ss.toCharArray()){
            supp = supp + c;
            cont++;
            if(cont == everyN){
                plainText = new BigInteger(supp.getBytes("UTF-8"));
                cypherText = cypherText + "//" + plainText.modPow(publicExp, publicN).toString();
                supp = "";
                cont = 0;
            }
        }
        if(lengthPrime){
            plainText = new BigInteger(supp.getBytes("UTF-8"));
            cypherText = cypherText + "//" + plainText.modPow(publicExp, publicN).toString();
        }
        return cypherText;
    }

    public String decrypt(String msg) {
        msg = msg.substring(2);
        //System.out.println(msg);
        String decrypted = "";
        String part = "";
        String[] b = msg.split("//");
        
        for(String s : msg.split("//")){
            //System.out.println(s);
            BigInteger supp = new BigInteger(s);
            supp = supp.modPow(d,n);
            byte[] erbite = supp.toByteArray();
            decrypted = decrypted + new String(erbite);
        }
        return decrypted;

    }

    public String getKey() {
        return new String("pk"+ e.toString() + "/" + n.toString());
    }

    public void setFriendKey(String s){
        keyFriend = s;
    }

}
