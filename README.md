# hw5
step homework 0623

### Mysolver_all.java  
頂点の順列を全通り試して、一番距離が短くなるような経路を採用するプログラムです。  
(input_0.csv, input_1.csvで使いました。)

実行方法

    javac Common.java Mysolver_all.java  
    java Mysolver_all input_0.csv solution_yours_0.csv
  
### Mysolver_divide.java
頂点が５つ以下の塊に分けてそれぞれの塊で Mysolver_all.java を行うプログラムです。  
input_3.csv以降がうまく行っていないので改善中です。
  
実行方法

    javac Common.java Mysolver_all.java Mysolver_divide.java
    java Mysolver_divide input_0.csv solution_yours_0.csv
  
### mysolver_divide.py  
頂点が３つ以下の塊に分けて分割倒置法で経路を求めた後、2optを使って交差をなくしました。  
 (input_2.csv以降で使いました。)
   
 実行方法
 
    python mysolver_divide.py input_0.csv solution_yours_0.csv

 
