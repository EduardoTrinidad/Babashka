(ns run-all-tests
  (:require [clojure.test :refer :all]
            [babashka.pods :as pods]))

;; Detecta si estás en Windows o Linux
(def os-name (System/getProperty "os.name"))
(def pod-path (if (.startsWith os-name "Windows")
                "./result/bin/babashka-pod-docker.exe"
                "./result/bin/babashka-pod-docker"))

(pods/load-pod pod-path)

(require '[test-basic])
(require '[test-images])
(require '[test-utils])

(run-tests 'test-basic 'test-images 'test-utils)
(println "Todas las pruebas se ejecutaron correctamente.")
