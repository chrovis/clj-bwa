package clj_bwa.jna;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class SamseOption extends Structure {

    public int nOcc = 3;

    public String rgLine = null;

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[] {
                "nOcc", "rgLine"
            });
    }
}
