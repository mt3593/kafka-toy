(ns kafka-toy.acceptance
  (:require [kafka-toy.test-common :refer :all]
            [clj-http.client :as http]
            [environ.core :refer [env]]
            [midje.sweet :refer :all])
  (:import [java.util UUID]))

(defn gen-uid
  []
  (str (UUID/randomUUID)))

(fact-group
 :acceptance

 (fact "Ping resource returns 200 HTTP response"
       (let [response (http/get (url+ "/ping")  {:throw-exceptions false})]
         response => (contains {:status 200})))

 (fact "Healthcheck resource returns 200 HTTP response"
       (let [response (http/get (url+ "/healthcheck") {:throw-exceptions false})
             body (json-body response)]
         response => (contains {:status 200})
         body => (contains {:name "kafka-toy"
                            :success true
                            :version truthy})))

 (fact "create a topic on kafka!!"
       (let [{:keys [status]} (http/post (url+ (str "/topic/" gen-uid)) {:throw-exceptions false})]
         status => 200))

 (fact "list all messages on kafka"
       (let [message (gen-uid)
             _ (http/post (url+ "/topic/test") {:body message})
             {:keys [status body]} (http/get (url+ "/topic/test") {:throw-exceptions false
                                                                   :as :json})]
         status => 200
         body => (contains {:messages (contains message)}))))
