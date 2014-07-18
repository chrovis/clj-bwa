(ns clj-bwa.core
  (:require [clj-bwa.util :refer [boolean->int]]
            [clj-bwa.exception :refer [libbwa-error->exception]])
  (:import com.sun.jna.Native))

(gen-interface
 :name clj-bwa.core.BWALibrary
 :extends [com.sun.jna.Library]
 :methods [[libbwa_index [String String int int] int]])

(def bwalib (Native/loadLibrary "bwa" clj-bwa.core.BWALibrary))

(defn- algo->enum
  [algo]
  (condp = algo
    :auto 0   ; LIBBWA_INDEX_ALGO_AUTO
    :div 1    ; LIBBWA_INDEX_ALGO_DIV
    :bwtsw 2  ; LIBBWA_INDEX_ALGO_BWTSW
    :is 3     ; LIBBWA_INDEX_ALGO_IS
    ))

(defn index
  [db prefix algo is64]
  (let [algo* (algo->enum algo)
        is64* (boolean->int is64)]
   (let [n (.libbwa_index bwalib db prefix algo* is64*)]
     (libbwa-error->exception n))))
