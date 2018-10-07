import logging, json
logger = logging.getLogger()
logger.setLevel(logging.INFO)

def handleRequest(event, context):
    logger.info('Received {}'.format(event))
    body = event['body']
    request = json.loads(body)
    message = 'Hello {}!'.format(request['who'])
    response = { 'message' : message }
    return { 'statusCode': 200 , 'body': json.dumps(response) } 
    