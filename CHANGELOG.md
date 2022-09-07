# clojure-libs

## Unreleased
- Java interface is one of my weak points.
### Bug
 ```
(let [e (hj/now-in-epoch)
      e2 (hj/datetime->epoch (hj/epoch->datetime e)
     (- e e2))
```


## [0.2.1] - 2022-09-07
### Changed
- move `sort` into `misc`.
- instead of epoch, use second for function names. for example,
  `now-in-epoch` was renamed as `now-in-second`.

## [0.2.0] - 2022-09-06
### Added
- create `javatime`. learning use of java libraries from clojure.

## 0.1.0 - 2022-09-06
- initialize repository

[0.2.1]:https://github.com/hkim0331/clojure-libs/compare/0.2.1...0.2.0
[0.2.0]: https://github.com/hkim0331/clojure-libs/compare/0.1.0...0.2.0
[0.1.1]: https://github.com/hkim0331/clojure-libs/compare/0.1.0...0.1.1
