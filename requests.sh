#!/usr/bin/env bash

for ((i=0;i<=1000;i++)); do
  curl -s "http://localhost:8080/owl.png" > /dev/null;
done