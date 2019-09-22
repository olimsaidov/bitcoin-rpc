(ns bitcoin-rpc.client
  (:require [clj-http.client :as client]
            [cheshire.core :refer [parse-string]]))


(defprotocol IClient
  (perform [this method params]))


(defrecord Client
  [url user pass]

  IClient
  (perform [_ method params]
    (let [response
          (client/request
            {:url          url
             :as           :json
             :method       :post
             :content-type :json
             :basic-auth   (when (and user pass) [user pass])
             :form-params  {:jsonrpc "1" :method method :params params}})]

      (if (empty? (:error (:body response)))
        (:result (:body response))
        (throw (ex-info "Perform failed" (::error (:body response))))))))
