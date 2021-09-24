(ns example.core
  (:require
   [next.jdbc :as jdbc])
  (:gen-class))

(defn -main
  [& _args]
  (try
    (let [connection (jdbc/get-connection "jdbc:h2:mem:./h2-db")]
      (println (jdbc/execute! connection [(str "create table user" (rand-nth ["a" "b" "c" "d" "e" "f" "g" "h" "i" "j" "k"]) " (id serial primary key, name text not null)")]))
      (println (jdbc/execute! connection ["show tables"])))
    (catch Exception err
      (println err)
      (System/exit 1))))
