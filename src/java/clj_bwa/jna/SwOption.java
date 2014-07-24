package clj_bwa.jna;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class SwOption extends Structure {

    public int skipSw = 0;
    public int cpyCmt = 0;
    public int hardClip = 0;

    public int a = 1;
    public int b = 3;
    public int q = 5;
    public int r = 2;
    public int t = 30;
    public int qr = q + r;
    public int bw = 50;
    public int maxIns = 20000;
    public int maxChainGap = 10000;

    public int z = 1;
    public int is = 3;
    public int tSeeds = 5;
    public int multi2nd;

    public float maskLevel = 0.5F;
    public float coef = 5.5F;

    public int nThreads = 1;
    public int chunkSize = 10000000;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[] {
                "skipSw", "cpyCmt", "hardClip",
                "a", "b", "q", "r", "t", "qr", "bw", "maxIns", "maxChainGap",
                "z", "is", "tSeeds", "multi2nd",
                "maskLevel", "coef",
                "nThreads", "chunkSize"
            });
    }
}
