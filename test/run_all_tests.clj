(ns run-all-tests 
  (:require [clojure.test :refer :all]
            [babashka.pods :as pods]))

;; Cargar el pod una sola vez
(pods/load-pod "./result/bin/babashka-pod-docker.exe")

;; Requerir los archivos de prueba
(require '[test-basic])
(require '[test-images])
(require '[test-utils])

;; Ejecutar todas las pruebas
(run-tests 'test-basic 'test-images 'test-utils)

;; Mensaje al terminar
(println "Todas las pruebas se ejecutaron correctamente.")
