(defproject clj-bwa "0.1.0-SNAPSHOT"
  :description "BWA wrapper for Clojure"
  :url "https://github.com/chrovis/clj-bwa"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [net.java.dev.jna/jna "4.2.1"]
                 [camel-snake-kebab "0.3.2"]]
  :plugins [[lein-midje "3.2"]]
  :profiles {:dev {:dependencies [[midje "1.8.3"]
                                  [me.raynes/fs "1.4.6"]]
                   :global-vars {*warn-on-reflection* true}}}
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"])
