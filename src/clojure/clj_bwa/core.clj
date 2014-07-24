(ns clj-bwa.core
  (:require [clj-bwa.util :refer [boolean->int]]
            [clj-bwa.exception :refer [libbwa-error->exception]])
  (:import com.sun.jna.Native
           [clj_bwa.jna BWALibrary AlnOption SamseOption SampeOption]))

(def ^BWALibrary bwalib (Native/loadLibrary "bwa" BWALibrary))

;; index
;; -----

(defn- algo->enum
  [algo]
  (case algo
    :auto 0   ; LIBBWA_INDEX_ALGO_AUTO
    :div 1    ; LIBBWA_INDEX_ALGO_DIV
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

(defn sampe-option
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
