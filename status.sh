#!/bin/bash
# Script to install the hello java AWS lambda API
set -feuo pipefail

bucket="cf-store-software"
account="software"
stack="hello"
mode="UPDATE"

echo check the stack status
aws --profile "${account}-admin" cloudformation describe-stacks --stack-name "${stack}" --output table
 