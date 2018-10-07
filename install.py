import boto3, sys, subprocess, time

def validate_template(profile,stack):
    template = open('src/main/cloudformation/{0}.yaml'.format(stack)).read()
    session = boto3.Session(profile_name=profile)
    client = session.client('cloudformation')
    result = client.validate_template(TemplateBody=template)
    status = result['ResponseMetadata']['HTTPStatusCode']
    if status != 200:
        sys.exit(0)
        
def build_java_lambda():
    subprocess.call(["mvn","clean","package"])
    
def build_python_lambda():
    p = subprocess.Popen(["zip","-r9","../../../target/lambda-hello-1.0.0.py.zip","."],cwd='src/main/python')
    rc = p.wait()
    if rc != 0:
        sys.exit(0)
        
def build_node_lambda():
    p = subprocess.Popen(["zip","-r9","../../../target/lambda-hello-1.0.0.js.zip","."],cwd='src/main/node')
    rc = p.wait()
    if rc != 0:
        sys.exit(0)
        
def s3_copy_java_file(profile):
    session = boto3.Session(profile_name=profile)
    client = session.client('s3')
    client.upload_file('./target/lambda-hello-1.0.0-SNAPSHOT.jar', bucket, 'java/hello-lambda-1.0.0.jar')    

def s3_copy_python_file(profile):
    session = boto3.Session(profile_name=profile)
    client = session.client('s3')
    client.upload_file('./target/lambda-hello-1.0.0.py.zip', bucket, 'python/hello-lambda-1.0.0.zip')    

def s3_copy_node_file(profile):
    session = boto3.Session(profile_name=profile)
    client = session.client('s3')
    client.upload_file('./target/lambda-hello-1.0.0.js.zip', bucket, 'node/hello-lambda-1.0.0.zip')
    
def apply_stack(profile,stack,bucket):
    session = boto3.Session(profile_name=profile)
    client = session.client('cloudformation')
    try:
        result = client.describe_stacks(StackName=stack)
        status = result['Stacks'][0]['StackStatus']
        print(status)
        try:
            template = open('src/main/cloudformation/{0}.yaml'.format(stack)).read()
            client.update_stack(StackName=stack, TemplateBody=template, Capabilities=['CAPABILITY_NAMED_IAM'], Parameters=[{'ParameterKey':'BucketName','ParameterValue':bucket}])
        except:
            print("Nothing to do")            
    except:
        template = open('src/main/cloudformation/{0}.yaml'.format(stack)).read()
        client.create_stack(StackName=stack, TemplateBody=template, Capabilities=['CAPABILITY_NAMED_IAM'], Parameters=[{'ParameterKey':'BucketName','ParameterValue':bucket}])
        
    result = client.describe_stacks(StackName=stack)
    status = result['Stacks'][0]['StackStatus']
    print(status)
    while "_FAILED" not in status and "_COMPLETE" not in status:
        time.sleep(5)
        result = client.describe_stacks(StackName=stack)
        status = result['Stacks'][0]['StackStatus']
        print(status)    
    if "Outputs" in result['Stacks'][0]:
        for output in result['Stacks'][0]['Outputs']:
            if output['OutputKey'] == 'APIURL':
                uri = output['OutputValue']
                print(uri)
                with open("api.url","w") as file:
                    file.write(uri)
                    
if __name__ == "__main__":
    
    bucket = open('bucket.name').read().splitlines()[0]
    profile = open('profile.name').read().splitlines()[0]

    validate_template(profile,'lambda-hello-bootstrap')
    apply_stack(profile,'lambda-hello-bootstrap',bucket)
    
    build_java_lambda()
    build_python_lambda()
    build_node_lambda()
    
    s3_copy_java_file(profile)
    s3_copy_python_file(profile)
    s3_copy_node_file(profile)
    
    validate_template(profile,'lambda-hello')
    apply_stack(profile,'lambda-hello',bucket)
