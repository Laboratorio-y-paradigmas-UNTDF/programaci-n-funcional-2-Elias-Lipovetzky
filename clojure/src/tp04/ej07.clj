(ns tp04.ej07
  "Ejercicio 7 — Partial en Clojure (5 pts). Trazabilidad: F-15"
  (:require [clojure.string :as str]))

;; Retorna {:status :ok :value value} si no vacío, {:status :error :error "FIELD es obligatorio"}.
(defn required-field [field-name value]
  (if (str/blank? value)
    {:status :error
     :error (str field-name " es obligatorio")}
    {:status :ok
     :value value}))

(def doble
  (fn[x](* x 2)))

(def triple
  (fn[x](* x 3)))

(def validate-name
  (fn [value] (required-field "nombre" value)))

(def validate-email
  (fn [value] (required-field "email" value)))
