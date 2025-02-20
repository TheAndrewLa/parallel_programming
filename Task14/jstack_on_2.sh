# /usr/bin/bash 

java -cp build Task14.Sample2 & PID="$!" && sleep 1
jstack -l $PID > logs/sample_2.txt
