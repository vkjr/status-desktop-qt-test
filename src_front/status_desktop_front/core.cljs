(ns status-desktop-front.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            ;status-im.ui.screens.subs
            ;status-desktop-front.storage
            ;status-desktop-front.ui.screens.events
            [status-desktop-front.ui.screens.views :as views]
            [status-desktop-front.react-native-web :as react]))

(defn app-root []
      (fn []
          [views/main]
          ;[react/text "Logs asdf asdf"]
          ))

(defn mount-root []
      (.registerComponent react/app-registry "StatusIm" #(reagent/reactify-component app-root)))

(defn init []
      (mount-root)
      (re-frame/dispatch-sync [:initialize-app]))

(defn log [message]
  (re-frame/dispatch [:log-message message]))
