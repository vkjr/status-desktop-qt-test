(ns status-desktop-front.init
  (:require [status-desktop-front.core :as core]))

(enable-console-print!)
(core/init)

;
;
;(defn ^:export run
;  []
;  (core/init))
;
;(defn ^:export log
;  [message]
;(core/log message))
