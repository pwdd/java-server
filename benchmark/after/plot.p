# output as png image
set terminal png

# save file to "out.png"
set output "benchmark/after/out.png"

# graph title
set title "1000 requests, 20 concurrent, 100MB file"

# nicer aspect ratio for image size
set size 1,0.7

# y-axis grid
set grid y

# x-axis label
set xlabel "request"

# y-axis label
set ylabel "response time (ms)"

# plot data from "out.dat" using column 9 with smooth sbezier lines
plot "benchmark/after/out.tsv" using 9 smooth sbezier with lines title 'response time'