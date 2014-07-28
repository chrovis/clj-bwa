package clj_bwa.jna;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class FastmapOption extends Structure {

    public int printSeq = 0;

    public int minIwidth = 20;

    public int minLen = 17;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[] {
                "printSeq", "minIwidth", "minLen"
            });
    }
}
