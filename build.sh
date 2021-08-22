#!/usr/bin/env bash

set -euxo pipefail

cd "$(dirname "$0")"

rm -f *.class *.jar

javac Paint.java
jar cvef Paint Paint.jar *.class
