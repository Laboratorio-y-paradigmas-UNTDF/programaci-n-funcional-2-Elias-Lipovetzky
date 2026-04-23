(ns tp04.ej18
  "Ejercicio 18 — Integrador Clojure (7 pts). Trazabilidad: F-33")

;; {:ok true :value orden} si activa y total > 100. Error si no.
(defn clasificar-orden [orden]
  (let [{:keys [activa? total]} orden]
    (cond
      (not activa?) {:ok false :error "orden inactiva"}
      (<= total 100) {:ok false :error "monto insuficiente"}
      :else {:ok true :value orden})))

;; Retorna nueva orden con total reducido por porcentaje.
(defn aplicar-descuento [porcentaje orden]
  (assoc orden :total (* (:total orden) (- 1 (/ porcentaje 100)))))

;; Pipeline: clasificar → separar → descuento 10% → sumar.
;; Retorna {:aprobadas [...] :rechazadas [...] :total-final N}
(defn procesar-ordenes [ordenes]
  (let [clasificadas (map clasificar-orden ordenes)
        aprobadas (filter :ok clasificadas)
        rechazadas (remove :ok clasificadas)
        
        aprobadas-con-descuento (map #(aplicar-descuento 10 (:value %)) aprobadas)]
    
    {:aprobadas aprobadas-con-descuento
     :rechazadas rechazadas
     :total-final (reduce + 0 (map :total aprobadas-con-descuento))}))
