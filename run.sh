#!/bin/sh
set -eu
cd "$(dirname "$0")"
mkdir -p build
find src -name '*.java' | sort > build/sources.txt
javac --release 17 -encoding UTF-8 -Xlint:all -Werror -d build @build/sources.txt
case "${1:-demo}" in
  demo) java -cp build rideready.Main ;;
  test) java -cp build rideready.PatternTests ;;
  *) echo "Usage: sh run.sh [demo|test]" >&2; exit 2 ;;
esac
