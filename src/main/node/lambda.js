exports.handleRequest = function(event, context, callback) {
	const request = JSON.parse(event.body);
	const who = request.who;
	console.log(`Received ${who}`);
	callback(null,{statusCode: 200, body: JSON.stringify({message: `Hello ${who}!`})});
}