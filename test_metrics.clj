(ns test-metrics
  (:require [clojure.test :refer :all]
            [babashka.pods :as pods]
            [babashka.http-server :as http]))

(pods/load-pod "./result/bin/babashka-pod-docker.exe")

(require '[test-basic])
(require '[test-images])
(require '[test-utils])

(defonce test-result (atom nil))

(defn run-all []
  (let [result (run-tests 'test-basic 'test-images 'test-utils)]
    (reset! test-result result)
    (println "Pruebas ejecutadas.")
    result))

(defn prometheus-metrics []
  (let [result @test-result
        passed (get-in result [:pass] 0)
        failed (get-in result [:fail] 0)
        errors (get-in result [:error] 0)
        total  (+ passed failed errors)]
    (str
     "# HELP clojure_test_total Total de pruebas ejecutadas\n"
     "# TYPE clojure_test_total counter\n"
     (format "clojure_test_total %d\n" total)
     "# HELP clojure_test_failures Número de fallos\n"
     "# TYPE clojure_test_failures counter\n"
     (format "clojure_test_failures %d\n" failed)
     "# HELP clojure_test_errors Número de errores\n"
     "# TYPE clojure_test_errors counter\n"
     (format "clojure_test_errors %d\n" errors)
     "# HELP clojure_test_passes Número de pruebas exitosas\n"
     "# TYPE clojure_test_passes counter\n"
     (format "clojure_test_passes %d\n" passed))))

(defn -main []
  (run-all)
  (println "📊 Servidor de métricas en http://localhost:9876/metrics")
  (http/start-server {:port 9876
                      :handler (fn [_req]
                                 {:status 200
                                  :headers {"Content-Type" "text/plain"}
                                  :body (prometheus-metrics)})}))

(-main)
