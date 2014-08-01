clj-bwa
=======

clj-bwa is Burrows-Wheeler Aligner (BWA) wrapper for Clojure.

Requirements
------------

- [libbwa][libbwa]

clj-bwa uses libbwa native library via Java Native Access (JNA).
Install libbwa to system or locate it on your `java.library.path`.

Usage
-----

Add the following dependency to your project.clj.

```clojure
[clj-bwa "0.1.0-SNAPSHOT"]
```

clj-bwa wraps BWA sub-commands for calling its features from Clojure codes.

```clojure
(require '[clj-bwa.core :as bwa])

;; Index database sequences in the FASTA format.
(bwa/index "path/to/reference.fa" "path/to/reference.fa" :auto false)

;; Align 70bp-1Mbp query sequences with the BWA-MEM algorithm.
(let [opt (bwa/mem-option)]
  (bwa/mem "path/to/reference.fa" "path/to/read.fq" nil "path/to/out.sam" opt)
```

## Development

### Test

To run all tests,

```bash
$ lein midje
```

### Generating document

cljam uses [Marginalia][marginalia] for generating documents.

```bash
$ lein marg -m
```

generates HTML documents in `docs` directory.

License
-------

Copyright 2014 [Xcoo, Inc.][xcoo]

Licensed under the [Apache License, Version 2.0][apache-license-2.0].

[libbwa]: https://github.com/chrovis/libbwa
[marginalia]: http://gdeer81.github.io/marginalia/
[xcoo]: http://www.xcoo.jp
[apache-license-2.0]: http://www.apache.org/licenses/LICENSE-2.0.html
