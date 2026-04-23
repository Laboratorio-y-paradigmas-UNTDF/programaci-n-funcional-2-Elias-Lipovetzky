(ns tp04.ej15
  "Ejercicio 15 — Lazy sequences (5 pts). Trazabilidad: F-30")

;; Los primeros n pares positivos (2, 4, 6...).
(defn primeros-n-pares [n]
  (take n
    ((fn pares [x]
       (lazy-seq
         (cons x (pares (+ x 2)))))
     2)))

;; Secuencia infinita de Fibonacci. DEBE ser lazy.
(defn fibonacci []
  ((fn fib [a b]
     (lazy-seq
       (cons a (fib b (+ a b)))))
   0 1))

;; Toma elementos mientras sean menores que umbral.
(defn tomar-mientras-menor [coll umbral]
  (lazy-seq
    (if (and (seq coll) (< (first coll) umbral))
        (cons (first coll) (tomar-mientras-menor (rest coll) umbral))
        nil)))
