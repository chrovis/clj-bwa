package clj_bwa.jna;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class AlnOption extends Structure {

    public int sMm = 3;
    public int sGapo = 11;
    public int sGape = 4;

    public int mode = Common.BWA_MODE_GAPE | Common.BWA_MODE_COMPREAD;

    public int indelEndSkip = 5;
    public int maxDelOcc = 10;
    public int maxEntries = 2000000;

    public float fnr = 0.04F;

    public int maxDiff = -1;
    public int maxGapo = 1;
    public int maxGape = 6;

    public int maxSeedDiff = 2;
    public int seedLen = 32;

    public int nThreads = 1;

    public int maxTop2 = 30;

    public int trimQual = 0;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[] {
                "sMm", "sGapo", "sGape",
                "mode",
                "indelEndSkip", "maxDelOcc", "maxEntries",
                "fnr",
                "maxDiff", "maxGapo", "maxGape",
                "maxSeedDiff", "seedLen",
                "nThreads",
                "maxTop2",
                "trimQual"
            });
    }
}
