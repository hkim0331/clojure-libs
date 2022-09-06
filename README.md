# clojure-libs

Private clojure libraries.
Step by step reading/learning
[clojure.org/guides/deps_and_cli](https://clojure.org/guides/deps_and_cli).

* how to use local repos?
* how to use git repos?
* how to write tests?

## test
(copied from `deps_and_cli`.)

add aliases to deps.edn:

```
 :aliases
 {:test {:extra-paths ["test"]
         :extra-deps {io.github.cognitect-labs/test-runner
                      {:git/url "https://github.com/cognitect-labs/test-runner.git"
                       :sha "9e35c979860c75555adaff7600070c60004a0f44"}}
         :main-opts ["-m" "cognitect.test-runner"]
         :exec-fn cognitect.test-runner.api/test}}}
```

then,

    % clj -X:test
