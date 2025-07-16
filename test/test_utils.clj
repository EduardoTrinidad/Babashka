(ns test-utils
  (:require [clojure.test :refer :all]
            [babashka.pods :as pods]))

;; Cargar el pod
(pods/load-pod "./result/bin/babashka-pod-docker.exe")
(require '[docker.tools :as docker])

;; Función auxiliar que agrega :tag "latest" si falta
(defn normalize-image-name [image-str]
  (let [{:keys [path tag]} (docker/parse-image-name image-str)]
    {:path path
     :tag  (or tag "latest")}))

(deftest test-parse-image-name
  (testing "parse-image-name without tag"
    (is (= {:path "ubuntu"}
           (docker/parse-image-name "ubuntu")))))

(deftest test-normalize-image-name
  (testing "normalize-image-name completes missing tag"
    (is (= {:path "ubuntu" :tag "latest"}
           (normalize-image-name "ubuntu"))))
  (testing "normalize-image-name keeps existing tag"
    (is (= {:path "nginx" :tag "1.25"}
           (normalize-image-name "nginx:1.25")))))

(deftest test-parse-shellwords
  (testing "parse-shellwords splits command"
    (is (= ["echo" "hello" "world"]
           (docker/parse-shellwords "echo hello world")))))

(run-tests)
(println "Pruebas ejecutadas correctamente en test-utils")
