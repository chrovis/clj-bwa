(ns clj-bwa.core
  (:require [clojure.template :refer [do-template]]
            [camel-snake-kebab.core :refer :all]
            [clj-bwa.util :refer [boolean->int]]
            [clj-bwa.exception :refer [libbwa-error->exception]])
  (:import com.sun.jna.Native
           [clj_bwa.jna BWALibrary AlnOption SamseOption SampeOption SwOption
                        MemOption FastmapOption]))

(def ^BWALibrary bwalib (Native/loadLibrary "bwa" BWALibrary))

(defmacro ^:private set-field!
  [opt optmap key]
  (let [f (symbol (->camelCase (name key)))]
    `(if-let [v# (get ~optmap ~key)]
       (set! (. ~opt ~f) v#))))

(defmacro ^:private set-fields!
  [opt optmap keys]
  `(do-template [key]
                (set-field! ~opt ~optmap key)
                ~@keys))

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
       (set-fields! opt optmap [:s-mm :s-gapo :s-gape :mode :indel-end-skip
                                :max-del-occ :max-entries :fnr :max-diff
                                :max-gapo :max-gape :max-seed-diff :seed-len
                                :n-threads :max-top2 :trim-qual])
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
       (set-fields! opt optmap [:n-occ :rg-line])
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
       (set-fields! opt optmap [:max-isize :force-isize :max-occ :n-multi
                                :N-multi :type :is-sw :is-preload :apPrior
                                :rg-line])
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
       (set-fields! opt optmap [:skip-sw :cpy-cmt :hard-clip :a :b :q :r :t
                                :qr :bw :max-ins :max-chain-gap :z :is :t-seeds
                                :multi-2nd :mask-level :coef :n-threads
                                :chunk-size])
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
       (set-fields! opt optmap [:match-score :mismatch-penalty :o-del :e-del
                                :o-ins :e-ins :pen-unpaired :pen-clip5
                                :pen-clip3 :band-width :zdrop :t :flag
                                :min-seed-len :min-chain-weight
                                :max-chain-extend :split-factor :split-width
                                :max-occ :max-chain-gap :n-threads :chunk-size
                                :mask-level :drop-ratio :xa-drop-ratio
                                :mask-level-redun :mapq-coef-len :max-ins
                                :max-matesw :max-hits])
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
       (set-fields! opt optmap [:print-seq :min-iwidth :min-len])
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
