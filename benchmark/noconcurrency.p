# output as png image
set terminal png

# save file to "out.png"
set output "benchmark/noconcurrency.png"

# graph title
set title "10000 requests, no concurrency, photo"

# nicer aspect ratio for image size
set size 1,0.7

# y-axis grid
set grid y

# x-axis label
set xlabel "request"

# y-axis label
set ylabel "response time (ms)"

# plot data from "out.dat" using column 9 with smooth sbezier lines
plot "benchmark/noconcurrency.tsv" using 9 smooth sbezier with lines title 'response time'