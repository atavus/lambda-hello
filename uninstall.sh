#!/bin/bash
# Uninstall script for lambda-hello example
set -feuo pipefail

echo delete the stack
aws --profile "software-admin" cloudformation delete-stack \
  		--stack-name "lambda-hello" \
  		--output table

echo delete the bucket
aws --profile "software-admin" cloudformation delete-stack \
  		--stack-name "lambda-hello-bootstrap" \
  		--output table
