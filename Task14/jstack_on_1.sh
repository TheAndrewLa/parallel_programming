# /usr/bin/bash

java -cp build Task14.Sample1 & PID="$!" && sleep 1
jstack -l $PID > logs/sample_1.txt
