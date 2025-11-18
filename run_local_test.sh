#!/bin/bash
./gradlew build
java -jar NATT.jar -nc https://gitlab.com/ap5vs/test-config/web-crawler-config/-/raw/main/test-config-5.yaml?ref_type=heads
