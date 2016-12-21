# output as png image
set terminal png

# save file to "benchmark.png"
set output "benchmark/after/benchmark.png"

# graph title
set title "Compare: 1000 requests, 20 concurrent, 100MB file"

# aspect ratio for image size
set size 1,1

# enable grid on y-axis
set grid y

# x-axis label
set xlabel "Request"

# y-axis label
set ylabel "Response Time (ms)"

# plot data from bench1.tsv,bench2.tsv and bench3.tsv using column 10 with smooth sbezier lines
plot "benchmark/after/bench1.tsv" using 10 smooth sbezier with lines title "Benchmark 1:", \
"benchmark/after/bench2.tsv" using 10 smooth sbezier with lines title "Benchmark 2:", \
"benchmark/after/bench3.tsv" using 10 smooth sbezier with lines title "Benchmark 3:"