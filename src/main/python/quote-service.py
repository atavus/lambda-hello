import logging, json

logger = logging.getLogger()
logger.setLevel(logging.INFO)

def handleRequest(request, context):
    logger.info('Received {}'.format(request))
    message = 'Hello {}! Here is a quote: Lambda is quite good.'.format(request['who'])
    response = { 'message' : message }
    logger.info('Response {}'.format(response))
    return response 
    