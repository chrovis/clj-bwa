(defproject clj-bwa "0.1.0-SNAPSHOT"
  :description "BWA wrapper for Clojure"
  :url "https://github.com/chrovis/clj-bwa"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [net.java.dev.jna/jna "4.1.0"]]
  :plugins [[lein-midje "3.1.3"]]
  :profiles {:dev {:dependencies [[midje "1.6.3"]]
                   :global-vars {*warn-on-reflection* true}}})
