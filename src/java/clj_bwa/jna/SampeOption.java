package clj_bwa.jna;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class SampeOption extends Structure {

    public int maxIsize = 500;
    public int forceIsize = 0;

    public int maxOcc = 100000;

    public int nMulti = 3;
    public int NMulti = 10;

    public int type = Common.BWA_PET_STD; // TODO
    public int isSw = 1;
    public int isPreload = 0;

    public double apPrior = 1e-5;

    public String rgLine = null;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[] {
                "maxIsize", "forceIsize",
                "maxOcc",
                "nMulti", "NMulti",
                "type", "isSw", "isPreload",
                "apPrior",
                "rgLine"
            });
    }
}
