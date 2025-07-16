(ns test-images
  (:require [clojure.test :refer :all]
            [babashka.pods :as pods]))

(pods/load-pod "./result/bin/babashka-pod-docker.exe")
(require '[docker.tools :as docker])

(deftest test-parse-no-tag
  (testing "parse-image-name without tag"
    (is (= {:path "ubuntu"}
           (docker/parse-image-name "ubuntu")))))

(deftest test-parse-custom-tag
  (testing "parse-image-name with custom tag"
    (is (= {:path "nginx" :tag "1.25"}
           (docker/parse-image-name "nginx:1.25")))))

(run-tests)

(println "Pruebas de imagenes ejecutadas correctamente.")
