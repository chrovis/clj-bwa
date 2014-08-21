package clj_bwa.jna;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class MemOption extends Structure {

    public int matchScore = 1;
    public int mismatchPenalty = 4;

    public int oDel = 6;
    public int eDel = 1;

    public int oIns = 6;
    public int eIns = 1;

    public int penUnpaired = 17;

    public int penClip5 = 5;
    public int penClip3 = 5;

    public int bandWidth = 100;

    public int zdrop = 100;

    public int t = 30;

    public int flag = 0;

    public int minSeedLen = 19;

    public int minChainWeight = 0;

    public int maxChainExtend = 1 << 30;

    public float splitFactor = 1.5F;

    public int splitWidth = 10;

    public int maxOcc = 500;

    public int maxChainGap = 10000;

    public int nThreads = 1;

    public int chunkSize = 10000000;

    public float maskLevel = 0.5F;

    public float dropRatio = 0.5F;

    public float xaDropRatio = 0.8F;

    public float maskLevelRedun = 0.95F;

    public float mapqCoefLen = 50F;

    public int maxIns = 10000;

    public int maxMatesw = 50;

    public int maxHits = 5;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[] {
                "matchScore",
                "mismatchPenalty",
                "oDel", "eDel",
                "oIns", "eIns",
                "penUnpaired",
                "penClip5", "penClip3",
                "bandWidth",
                "zdrop",
                "t",
                "flag",
                "minSeedLen",
                "minChainWeight",
                "maxChainExtend",
                "splitFactor",
                "splitWidth",
                "maxOcc",
                "maxChainGap",
                "nThreads",
                "chunkSize",
                "maskLevel",
                "dropRatio",
                "xaDropRatio",
                "maskLevelRedun",
                "mapqCoefLen",
                "maxIns",
                "maxMatesw",
                "maxHits"
            });
    }
}
