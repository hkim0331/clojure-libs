# libs

private clojure miscellaneous libraries.

* how to write tests?
* how to use local repos?
* how to use git repos?


## test

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



