dir:
	@mkdir -p build

build_first: dir
	@javac -d build Sample1.java

build_second: dir
	@javac -d build Sample2.java

build_third: dir
	@javac -d build Sample3.java

all: build_first build_second build_third

first: build_first
	@java -cp build Task11.JavaSamples.Sample1

second: build_second
	@java -cp build Task11.JavaSamples.Sample2

third: build_third
	@java -cp build Task11.JavaSamples.Sample3
