(ns clj-bwa.exception
  (:import java.io.IOException))

(defn libbwa-error->exception
  [n]
  (case n
    0 nil
    ;; General errors
    10 (IllegalArgumentException. "invalid arguments")
    11 (IllegalArgumentException. "invalid option")
    12 (IOException.)
    ;; BWA errors
    20 (Exception. "index error")
    21 (Exception. "unmatched SAI")
    22 (Exception. "not implementation")
    (Exception.)))
