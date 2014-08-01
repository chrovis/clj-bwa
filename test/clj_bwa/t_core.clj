(ns clj-bwa.t-core
  "Tests for clj-bwa.core."
  (:require [midje.sweet :refer :all]
            [clojure.java.io :as io]
            [me.raynes.fs :as fs]
            [clj-bwa.core :as bwa])
  (:import [clj_bwa.jna MemOption]))

(defmacro with-out-file
  [f & body]
  `(binding [*out* (clojure.java.io/writer ~f)]
     ~@body))

(def temp-dir (.getPath (io/file (System/getProperty "java.io.tmpdir") "clj-bwa-test")))

(defn prepare-cache!
  []
  (.mkdir (io/file temp-dir)))

(defn clean-cache!
  []
  (let [dir (io/file temp-dir)]
    (when (.exists dir)
      (doseq [f (seq (.list dir))]
        (.delete (io/file (str temp-dir "/" f))))
      (.delete dir))))

(def temp-out (str temp-dir "/out"))

;; Test resources
;; --------------

(def test-fa-file "test-resources/test.fa")
(def test-db-files ["test-resources/test.fa.amb"
                    "test-resources/test.fa.ann"
                    "test-resources/test.fa.bwt"
                    "test-resources/test.fa.pac"
                    "test-resources/test.fa.sa"])
(def test-sai-file "test-resources/test.sai")
(def test-fq-file "test-resources/test.fq")

(def temp-fa-file (str temp-dir "/test.fa"))

;; index test
;; ----------

(with-state-changes [(before :facts (do (prepare-cache!)
                                        (fs/copy test-fa-file
                                                 temp-fa-file)))
                     (after :facts (clean-cache!))]
  (fact "about index"
    (with-out-file temp-out
      (bwa/index temp-fa-file (str temp-dir "/test1.fa") :auto false)) => anything
    (with-out-file temp-out
      (bwa/index temp-fa-file (str temp-dir "/test2.fa") :bwtsw false)) => anything
    (with-out-file temp-out
      (bwa/index temp-fa-file (str temp-dir "/test3.fa") :is false)) => anything
    (with-out-file temp-out
      (bwa/index temp-fa-file (str temp-dir "/test4.fa") :auto true)) => anything))

;; aln test
;; --------

(with-state-changes [(before :facts (do (prepare-cache!)
                                        (doseq [f test-db-files]
                                          (fs/copy f (str temp-dir "/" (fs/base-name f))))
                                        (fs/copy test-fq-file (str temp-dir "/test.fq"))))
                     (after :facts (clean-cache!))]
  (fact "about aln"
    (with-out-file temp-out
      (bwa/aln (str temp-dir "/test.fa")
               (str temp-dir "/test.fq")
               (str temp-dir "/out.sai")
               (bwa/aln-option))) => anything))

;; samse test
;; ----------

(with-state-changes [(before :facts (do (prepare-cache!)
                                        (doseq [f test-db-files]
                                          (fs/copy f (str temp-dir "/" (fs/base-name f))))
                                        (fs/copy test-sai-file (str temp-dir "/test.sai"))
                                        (fs/copy test-fq-file (str temp-dir "/test.fq"))))
                     (after :facts (clean-cache!))]
  (fact "about samse"
    (with-out-file temp-out
      (bwa/samse (str temp-dir "/test.fa")
                 (str temp-dir "/test.sai")
                 (str temp-dir "/test.fq")
                 (str temp-dir "/out.sam")
                 (bwa/samse-option))) => anything))

;; sampe test
;; ----------

(with-state-changes [(before :facts (do (prepare-cache!)
                                        (doseq [f test-db-files]
                                          (fs/copy f (str temp-dir "/" (fs/base-name f))))
                                        (fs/copy test-sai-file (str temp-dir "/test.sai"))
                                        (fs/copy test-fq-file (str temp-dir "/test.fq"))))
                     (after :facts (clean-cache!))]
  (fact "about sampe"
    (with-out-file temp-out
      (bwa/sampe (str temp-dir "/test.fa")
                 (str temp-dir "/test.sai") (str temp-dir "/test.sai")
                 (str temp-dir "/test.fq") (str temp-dir "/test.fq")
                 (str temp-dir "/out.sam")
                 (bwa/sampe-option))) => anything))

;; bwasw test
;; ----------

(with-state-changes [(before :facts (do (prepare-cache!)
                                        (doseq [f test-db-files]
                                          (fs/copy f (str temp-dir "/" (fs/base-name f))))
                                        (fs/copy test-fq-file (str temp-dir "/test.fq"))))
                     (after :facts (clean-cache!))]
  (fact "about bwasw"
    (with-out-file temp-out
      (bwa/sw (str temp-dir "/test.fa")
              (str temp-dir "/test.fq")
              nil
              (str temp-dir "/out.sam")
              (bwa/sw-option))) => anything))

;; mem test
;; ----------

(fact "about mem-option"
  (type (bwa/mem-option)) => MemOption
  (type (bwa/mem-option nil)) => MemOption
  (type (bwa/mem-option {})) => MemOption
  (type (bwa/mem-option {:a 0})) => MemOption)

(with-state-changes [(before :facts (do (prepare-cache!)
                                        (doseq [f test-db-files]
                                          (fs/copy f (str temp-dir "/" (fs/base-name f))))
                                        (fs/copy test-fq-file (str temp-dir "/test.fq"))))
                     (after :facts (clean-cache!))]
  (fact "about mem"
    (with-out-file temp-out
      (bwa/mem (str temp-dir "/test.fa")
               (str temp-dir "/test.fq")
               nil
               (str temp-dir "/out.sam")
               (bwa/mem-option))) => anything))
