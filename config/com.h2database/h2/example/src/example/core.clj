(ns example.core
  (:require
   [next.jdbc :as jdbc])
  (:gen-class))

(defn -main
  [& _args]
  (try
    (let [connection (jdbc/get-connection "jdbc:h2:./h2-db")]
      (jdbc/execute!
       connection
       [(str "create table user" (rand-nth ["a" "b" "c" "d" "e" "f" "g" "h" "i" "j" "k"]) " (id serial primary key, name text not null)")]))
    (catch Exception err
      (println err))))
