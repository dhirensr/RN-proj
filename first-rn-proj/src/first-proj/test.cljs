(ns first-proj.test
  (:require  [cljs.test :as t :include-macros true]
             [reagent.core :as r :refer [atom]]))


(defonce env (.-env js/process))
(defonce Expo (js/require "expo"))
(defonce secure-store (.-SecureStore Expo))
(defonce ReactNative (js/require "react-native"))
(defonce ReactNavigation (js/require "react-navigation"))
(def app-registry (.-AppRegistry ReactNative))
(defonce NativeBase (js/require "native-base"))
(defonce VectorIcon (js/require "@expo/vector-icons"))

(defonce comm-icon (r/adapt-react-class (.-MaterialCommunityIcons VectorIcon)))
(defonce mat-icon (r/adapt-react-class (.-MaterialIcons VectorIcon)))

;;;;;; Get componnets from NativeBase
(defn nb-comp [name]
  (-> NativeBase
      (aget name)
      r/adapt-react-class))

(defonce container (nb-comp "Container"))
(defonce header (nb-comp "Header"))
(defonce content (nb-comp "Content"))
(defonce input (nb-comp "Input"))
(defonce item (nb-comp "Item"))
(defn rn-comp [name] (-> ReactNative
                         (aget name)
                         r/adapt-react-class))

(defonce text (rn-comp "Text"))
(defonce view (rn-comp "View"))
(defonce image (rn-comp "Image"))
(defonce touchable-highlight (rn-comp "TouchableOpacity"))
(defonce flat-list (rn-comp "FlatList"))
(defonce text-input (rn-comp "TextInput"))
(defonce Alert (.-Alert ReactNative))
(defn alert [title] (.alert Alert title))

(defn clj->json [data] (str (.stringify js/JSON (clj->js data)) "\n"))
(defn json->clj [line] (js->clj (.parse js/JSON line) :keywordize-keys true))
(defn print-msg [& msg]
  (when (= (.-NODE_ENV env) "development")
    (println (apply str msg))))



(def FirstComp (container
                (header
                 (content
                  (item {:rounded true}
                        (input {:placeholder "Rounded Textbox"}))))))

(defn app-root []
  (fn []
    ;;(delete-from-store "tasks")
    [:> FirstComp {}]))

(defn init []
  (.registerComponent app-registry "main" #(r/reactify-component app-root)))
