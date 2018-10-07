import logging, json, boto3

logger = logging.getLogger()
logger.setLevel(logging.INFO)

lambda_client = boto3.client('lambda')

def handleRequest(request, context):
    logger.info('Received {}'.format(request))
    body = request['body']
    question = json.loads(body)
    logger.info('Question {}'.format(question))
    try:
        result = lambda_client.invoke(FunctionName='QuoteServiceLambda', InvocationType='RequestResponse', Payload=json.dumps(question))
        if result['StatusCode'] == 200:
            answer = result['Payload'].read().decode("utf-8") 
            logger.info('Answer {}'.format(answer))
            response = { 'statusCode': 200 , 'body': answer }
            logger.info('Response {}'.format(response))
            return response
        else:
            e = result['Payload'].read().decode("utf-8") 
            response = { 'statusCode': 503 , 'exception': e }
            logger.info('Response {}'.format(response))
            return response
    except Exception as e:
        response = { 'statusCode': 503 , 'exception': str(e) }
        logger.info('Response {}'.format(response))
        return response
