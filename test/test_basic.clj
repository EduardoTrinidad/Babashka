(ns test-basic
  (:require [clojure.test :refer :all]
            [babashka.pods :as pods]))

;; Cargar el pod
(pods/load-pod "./result/bin/babashka-pod-docker.exe")
(require '[docker.tools :as docker])

(println "🧪 Ejecutando prueba: parse-image-name con tag...")

(deftest test-parse-image-name
  (testing "parse-image-name with tag"
    (is (= {:path "alpine" :tag "latest"}
           (docker/parse-image-name "alpine:latest")))))

(run-tests)

(println "✅ Prueba ejecutada correctamente.")
