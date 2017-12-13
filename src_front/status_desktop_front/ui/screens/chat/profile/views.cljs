(ns status-desktop-front.ui.screens.chat.profile.views
  (:require [status-desktop-front.react-native-web :as react]
            [status-im.ui.screens.profile.styles :as styles]
            [re-frame.core :as re-frame]
            [status-im.utils.utils :refer [hash-tag?]]
            [clojure.string :as string]
            [cljs.nodejs :as nodejs])
  (:require-macros [status-im.utils.views :as views]))

;TODO
;(def Electron (nodejs/require "electron"))
;(def clipboard (.-clipboard Electron))

(defn profile-badge [{:keys [name last-online] :as contact}]
  [react/view {:style styles/profile-badge}
   [react/view {:style styles/profile-badge-name-container}
    [react/text {:style           styles/profile-name-text
                 :number-of-lines 1}
     name]]])

(defn tag-view [tag]
  [react/text {:style {:color "#7099e6"}
               :font  :medium}
   (str tag " ")])

(defn colorize-status-hashtags [status]
  (for [[i status] (map-indexed vector (string/split status #" "))]
    (if (hash-tag? status)
      ^{:key (str "item-" i)}
      [tag-view status]
      ^{:key (str "item-" i)}
      (str status " "))))

(defn profile-status [status & [edit?]]
  [react/view {:style styles/profile-status-container}
   (if (or (nil? status) (string/blank? status))
     [react/touchable-highlight ;{:on-press #(dispatch [:my-profile/edit-profile :edit-status])}
      [react/view
       [react/text {:style styles/add-a-status}
        "Add status"]]]
     [react/scroll-view
      [react/touchable-highlight ;{:on-press (when edit? #(dispatch [:my-profile/edit-profile :edit-status]))}
       [react/view
        [react/text {:style styles/profile-status-text}
         (colorize-status-hashtags status)]]]])])


(defn profile-info-item [{:keys [label value options text-mode empty-value? accessibility-label]}]
  [react/touchable-highlight                                ;{:on-press #(.writeText clipboard value)}
   [react/view {:style styles/profile-setting-item}

     [react/view {:style (styles/profile-info-text-container options)}
      [react/text {:style styles/profile-setting-title}
       label]
      [react/view {:style styles/profile-setting-spacing}]
      [react/text {:style               (if empty-value?
                                          styles/profile-setting-text-empty
                                          styles/profile-setting-text)
                   :number-of-lines     1
                   :ellipsizeMode       text-mode
                   :accessibility-label accessibility-label}
       value]]]])


(defn profile-info-address-item [{:keys [address] :as contact}]
  [profile-info-item
   {:label               "Address";(label :t/address)
    :value               address}])

(defn profile-info-public-key-item [public-key contact]
  [profile-info-item
   {:label               "Public Key";(label :t/public-key)
    :value               public-key}])

(defn my-profile-info [{:keys [public-key status phone] :as contact}]
  [react/view
   [profile-info-address-item contact]
   [profile-info-public-key-item public-key contact]])

(views/defview profile []
  (views/letsubs [{:keys [status public-key] :as current-account} [:get-current-account]]
    [react/view {:style (merge styles/profile {:background-color :white})}
     [react/scroll-view
      [react/view {:style styles/profile-form}
       [profile-badge current-account]
       [profile-status status true]]
      [react/view {:style styles/profile-info-container}
       [my-profile-info current-account]]]]))