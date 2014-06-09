#!/bin/bash

# Stratio Meta
#
# Copyright (c) 2014, Stratio, All rights reserved.
#
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 3.0 of the License, or (at your option) any later version.
#
# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public
#  License along with this library.
#
# Stratio Meta CCM Test
# 
# PRE: Git, pyYaml module for python, Ant
pgrep -f ssandr
if [ $? -eq 0 ]; then
CURRENT_DIR=$(pwd)
COMPLETE_DIR="`locate meta-ssandreta/pom.xml`"
DIR_NAME="`dirname $COMPLETE_DIR`"
cd $DIR_NAME
nohup mvn exec:java -Dexec.mainClass="com.stratio.ssandreta.Main" &
cd ${CURRENT_DIR}
fi
