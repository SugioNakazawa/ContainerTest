#!/bin/bash

SCRIPT_DIR=$(cd $(dirname $0); pwd)

java -cp ${SCRIPT_DIR}/../ContainerTest-1.0.jar com.hoge.HelloWorld
