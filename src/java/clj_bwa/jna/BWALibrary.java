package clj_bwa.jna;

import com.sun.jna.Library;

public interface BWALibrary extends Library {

    int libbwa_index(String db, String prefix, int algo, int is64);

    int libbwa_aln(String db, String read, String out, AlnOption opt);

    int libbwa_samse(String db, String sai, String read, String out, SamseOption opt);

    int libbwa_sampe(String db, String sai1, String sai2,
                     String read1, String read2, String out, SampeOption opt);

    int libbwa_sw(String db, String read, String mate, String out, SwOption opt);

    int libbwa_mem(String db, String read, String mate, String out, MemOption opt);

    int libbwa_fastmap(String db, String read, String out, FastmapOption opt);

    int libbwa_fa2pac(String db, String prefix, int forOnly);

    int libbwa_pac2bwt(String pac, String out, int useIs);

    int libbwa_bwtgen(String pac, String out);

    int libbwa_bwtupdate(String bwt);

    int libbwa_bwt2sa(String bwt, String out, int saIntv);
}
