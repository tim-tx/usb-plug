(ns usb-plug.core
    (:require [scad-clj.scad :refer [write-scad]]
              [scad-clj.model :exclude [use import] :refer :all]))

(def usb-holder-size [5.7 10.0 13.1])   ; this is too small for an interference fit
(def cap-thickness 2)
(def cap-overhang-x cap-thickness)
(def cap-overhang-z cap-thickness)

(def usb-holder-cap
  (translate [0 (+ (/ (second usb-holder-size) 2) (/ cap-thickness 2)) 0]
   (cube
    (+ (first usb-holder-size) (* cap-overhang-x 2))  ; x
    cap-thickness                                     ; y
    (+ (last usb-holder-size) (* cap-overhang-z 2)))))

(def usb-holder-block
  (apply cube usb-holder-size))

(def usb-holder-cutout nil)
  
(def usb-holder-body
  (difference
   usb-holder-block
   usb-holder-cutout))

(def usb-holder
  (translate [0 0 (+ (/ (second usb-holder-size) 2) cap-thickness)]
             (rotate [(/ Math/PI -2) 0 0]
                     (union
                      usb-holder-body
                      usb-holder-cap))))

(def model usb-holder)

(defn -main [& dum]
  (spit (str "things/usb-plug.scad") (write-scad model))
  (System/exit 1))


