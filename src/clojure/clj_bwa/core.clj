(ns clj-bwa.core
  (:require [clj-bwa.util :refer [boolean->int]]
            [clj-bwa.exception :refer [libbwa-error->exception]])
  (:import com.sun.jna.Native
           [clj_bwa.jna BWALibrary AlnOption SamseOption SampeOption SwOption
                        MemOption FastmapOption]))

(def ^BWALibrary bwalib (Native/loadLibrary "bwa" BWALibrary))

;; index
;; -----

(defn- algo->enum
  "Converts algorithm keyword into libbwa's `libbwa_index_algo` enum. Note that
  `LIBBWA_INDEX_ALGO_DIV` (1) algorithm cannot be used."
  [algo]
  (case algo
    :auto 0   ; LIBBWA_INDEX_ALGO_AUTO
    :bwtsw 2  ; LIBBWA_INDEX_ALGO_BWTSW
    :is 3     ; LIBBWA_INDEX_ALGO_IS
    0))

(defn index
  [db prefix algo is64]
  (let [algo* (algo->enum algo)
        is64* (boolean->int is64)]
   (let [n (.libbwa_index bwalib db prefix algo* is64*)]
     (libbwa-error->exception n))))

;; aln
;; ----

(defn ^AlnOption aln-option
  ([] (AlnOption.))
  ([optmap]
     (let [opt (AlnOption.)]
       (if-let [v (:s-mm optmap)] (set! (.sMm opt) v))
       (if-let [v (:s-gapo optmap)] (set! (.sGapo opt) v))
       (if-let [v (:s-gape optmap)] (set! (.sGape opt) v))
       (if-let [v (:mode optmap)] (set! (.mode opt) v))
       (if-let [v (:indel-end-skip optmap)] (set! (.indelEndSkip opt) v))
       (if-let [v (:max-del-occ optmap)] (set! (.maxDelOcc opt) v))
       (if-let [v (:max-entries optmap)] (set! (.maxEntries opt) v))
       (if-let [v (:fnr optmap)] (set! (.fnr opt) v))
       (if-let [v (:max-diff optmap)] (set! (.maxDiff opt) v))
       (if-let [v (:max-gapo optmap)] (set! (.maxGapo opt) v))
       (if-let [v (:max-gape optmap)] (set! (.maxGape opt) v))
       (if-let [v (:max-seed-diff optmap)] (set! (.maxSeedDiff opt) v))
       (if-let [v (:seed-len optmap)] (set! (.seedLen opt) v))
       (if-let [v (:n-threads optmap)] (set! (.nThreads opt) v))
       (if-let [v (:max-top2 optmap)] (set! (.maxTop2 opt) v))
       (if-let [v (:trim-qual optmap)] (set! (.trimQual opt) v))
       opt)))

(defn aln
  [db read out opt]
  (let [n (.libbwa_aln bwalib db read out opt)]
    (libbwa-error->exception n)))

;; samse
;; -----

(defn ^SamseOption samse-option
  ([] (SamseOption.))
  ([optmap]
     (let [opt (SamseOption.)]
       (if-let [v (:n-occ optmap)] (set! (.nOcc opt) v))
       (if-let [v (:rg-line optmap)] (set! (.rgLine opt) v))
       opt)))

(defn samse
  [db sai read out opt]
  (let [n (.libbwa_samse bwalib db sai read out opt)]
    (libbwa-error->exception n)))

;; sampe
;; -----

(defn ^SampeOption sampe-option
  ([] (SampeOption.))
  ([optmap]
     (let [opt (SampeOption.)]
       (if-let [v (:max-isize optmap)] (set! (.maxIsize opt) v))
       (if-let [v (:force-isize optmap)] (set! (.forceIsize opt) v))
       (if-let [v (:max-occ optmap)] (set! (.maxOcc opt) v))
       (if-let [v (:n-multi optmap)] (set! (.nMulti opt) v))
       (if-let [v (:N-multi optmap)] (set! (.NMulti opt) v))
       (if-let [v (:type optmap)] (set! (.type opt) v))
       (if-let [v (:is-sw optmap)] (set! (.isSw opt) v))
       (if-let [v (:is-preload optmap)] (set! (.isPreload opt) v))
       (if-let [v (:apPrior optmap)] (set! (.apPrior opt) v))
       (if-let [v (:rg-line optmap)] (set! (.rgLine opt) v))
       opt)))

(defn sampe
  [db sai1 sai2 read1 read2 out opt]
  (let [n (.libbwa_sampe bwalib db sai1 sai2 read1 read2 out opt)]
    (libbwa-error->exception n)))

;; bwasw
;; -----

(defn ^SwOption sw-option
  ([] (SwOption.))
  ([optmap]
     (let [opt (SwOption.)]
       (if-let [v (:skip-sw optmap)] (set! (.skipSw opt) v))
       (if-let [v (:cpy-cmt optmap)] (set! (.cpyCmt opt) v))
       (if-let [v (:hard-clip optmap)] (set! (.hardClip opt) v))
       (if-let [v (:a optmap)] (set! (.a opt) v))
       (if-let [v (:b optmap)] (set! (.b opt) v))
       (if-let [v (:q optmap)] (set! (.q opt) v))
       (if-let [v (:r optmap)] (set! (.r opt) v))
       (if-let [v (:t optmap)] (set! (.t opt) v))
       (if-let [v (:qr optmap)] (set! (.qr opt) v))
       (if-let [v (:bw optmap)] (set! (.bw opt) v))
       (if-let [v (:max-ins optmap)] (set! (.maxIns opt) v))
       (if-let [v (:max-chain-gap optmap)] (set! (.maxChainGap opt) v))
       (if-let [v (:z optmap)] (set! (.z opt) v))
       (if-let [v (:is optmap)] (set! (.is opt) v))
       (if-let [v (:t-seeds optmap)] (set! (.tSeeds opt) v))
       (if-let [v (:multi-2nd optmap)] (set! (.multi2nd opt) v))
       (if-let [v (:mask-level optmap)] (set! (.maskLevel opt) v))
       (if-let [v (:coef optmap)] (set! (.coef opt) v))
       (if-let [v (:n-threads optmap)] (set! (.nThreads opt) v))
       (if-let [v (:chunk-size optmap)] (set! (.chunkSize opt) v))
       opt)))

(defn sw
  [db read mate out opt]
  (let [n (.libbwa_sw bwalib db read mate out opt)]
    (libbwa-error->exception n)))

;; mem
;; ----

(defn ^MemOption mem-option
  ([] (MemOption.))
  ([optmap]
     (let [opt (MemOption.)]
       (if-let [v (:match-score optmap)] (set! (.matchScore opt) v))
       (if-let [v (:mismatch-penalty optmap)] (set! (.mismatchPenalty opt) v))
       (if-let [v (:o-del optmap)] (set! (.oDel opt) v))
       (if-let [v (:e-del optmap)] (set! (.eDel opt) v))
       (if-let [v (:pen-unpaired optmap)] (set! (.penUnpaired opt) v))
       (if-let [v (:pen-clip5 optmap)] (set! (.penClip5 opt) v))
       (if-let [v (:pen-clip3 optmap)] (set! (.penClip3 opt) v))
       (if-let [v (:bandWidth optmap)] (set! (.bandWidth opt) v))
       (if-let [v (:zdrop optmap)] (set! (.zdrop opt) v))
       (if-let [v (:t optmap)] (set! (.t opt) v))
       (if-let [v (:flag optmap)] (set! (.flag opt) v))
       (if-let [v (:min-seed-len optmap)] (set! (.minSeedLen opt) v))
       (if-let [v (:min-chain-weight optmap)] (set! (.minChainWeight opt) v))
       (if-let [v (:max-chain-extend optmap)] (set! (.maxChainExtend opt) v))
       (if-let [v (:split-factor optmap)] (set! (.splitFactor opt) v))
       (if-let [v (:split-width optmap)] (set! (.splitWidth opt) v))
       (if-let [v (:max-occ optmap)] (set! (.maxOcc opt) v))
       (if-let [v (:max-chain-gap optmap)] (set! (.maxChainGap opt) v))
       (if-let [v (:n-threads optmap)] (set! (.nThreads opt) v))
       (if-let [v (:chunk-size optmap)] (set! (.chunkSize opt) v))
       (if-let [v (:mask-level optmap)] (set! (.maskLevel opt) v))
       (if-let [v (:drop-ratio optmap)] (set! (.dropRatio opt) v))
       (if-let [v (:xa-drop-ratio optmap)] (set! (.xaDropRatio opt) v))
       (if-let [v (:mask-level-redun optmap)] (set! (.maskLevelRedun opt) v))
       (if-let [v (:mapq-coef-len optmap)] (set! (.mapqCoefLen opt) v))
       (if-let [v (:max-ins optmap)] (set! (.maxIns opt) v))
       (if-let [v (:max-matesw optmap)] (set! (.maxMatesw opt) v))
       (if-let [v (:max-hits optmap)] (set! (.maxHits opt) v))
       opt)))

(defn mem
  [db read mate out opt]
  (let [n (.libbwa_mem bwalib db read mate out opt)]
    (if-let [e (libbwa-error->exception n)]
      (throw e))))

;; fastmap
;; -------

(defn fastmap-option
  ([] (FastmapOption.))
  ([optmap]
     (let [opt (FastmapOption.)]
       (if-let [v (:print-seq optmap)] (set! (.printSeq opt) v))
       (if-let [v (:min-iwidth optmap)] (set! (.minIwidth opt) v))
       (if-let [v (:min-len optmap)] (set! (.minLen opt) v))
       opt)))

(defn fastmap
  [db read out opt]
  (let [n (.libbwa_fastmap bwalib db read out opt)]
    (if-let [e (libbwa-error->exception n)]
      (throw e))))

;; fa2pac
;; ------

(defn fa2pac
  [db prefix for-only]
  (let [n (.libbwa_fa2pac bwalib db prefix (boolean->int for-only))]
    (if-let [e (libbwa-error->exception n)]
      (throw e))))

;; pac2bwt
;; -------

(defn pac2bwt
  [pac out]
  (let [n (.libbwa_pac2bwt bwalib pac out (boolean->int true))]
    (if-let [e (libbwa-error->exception n)]
      (throw e))))

;; bwtgen
;; ----------

(defn bwtgen
  [pac out]
  (let [n (.libbwa_bwtgen bwalib pac out)]
    (if-let [e (libbwa-error->exception n)]
      (throw e))))

;; bwtupdate
;; ---------

(defn bwtupdate
  [bwt]
  (let [n (.libbwa_bwtupdate bwalib bwt)]
    (if-let [e (libbwa-error->exception n)]
      (throw e))))

;; bwt2sa
;; ------

(defn bwt2sa
  [bwt out sa-intv]
  (let [n (.libbwa_bwt2sa bwalib bwt out sa-intv)]
    (if-let [e (libbwa-error->exception n)]
      (throw e))))
