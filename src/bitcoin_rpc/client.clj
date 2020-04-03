(ns bitcoin-rpc.client
  (:require [clj-http.client :as client]
            [cheshire.parse :refer [*use-bigdecimals?*]]
            [cheshire.core :refer [parse-string]]))


(defprotocol IClient
  (perform [this method params]))


(defrecord Client
  [url user pass]

  IClient
  (perform [_ method params]
    (let [response
          (client/request
            {:url              url
             :method           :post
             :basic-auth       (when (and user pass) [user pass])
             :form-params      {:jsonrpc "1" :method method :params params}
             :content-type     :json
             :throw-exceptions false})

          body
          (try (binding [*use-bigdecimals?* true]
                 (parse-string (:body response) true))
               (catch Throwable _ nil))]

      (cond
        (some? (:error body))
        (throw (ex-info (:message (:error body)) {:code (:code (:error body))}))

        (some? (:result body))
        (:result body)

        :else
        (throw (ex-info (str "clj-http: status " (:status response)) response))))))
