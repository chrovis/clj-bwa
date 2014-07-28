(ns clj-bwa.t-core
  (:require [midje.sweet :refer :all]
            [clojure.java.io :as io]
            [me.raynes.fs :as fs]
            [clj-bwa.core :as bwa]))

(defmacro with-out-file
  [f & body]
  `(binding [*out* (clojure.java.io/writer ~f)]
     ~@body))

(def temp-dir (.getPath (io/file (System/getProperty "java.io.tmpdir") "cljam-test")))

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
(def test-fa-file "test-resources/test.fa")
(def temp-fa-file (str temp-dir "/test.fa"))

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
      (bwa/index temp-fa-file (str temp-dir "/test4.fa") :auto true)) => anything
      ))
