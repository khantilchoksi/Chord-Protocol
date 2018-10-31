.PHONY: do_script

setup: 
    sh setup.sh

setup_preq: setup

target: sh setup.sh 