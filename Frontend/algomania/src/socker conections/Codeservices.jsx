const WebSocket = require('ws');

let socket = null;
let uuid = '';

function runCode(lang, code, input, setOutput, setLoading) {
    setLoading(true); // Set loading state to true when starting WebSocket connection

    // Ensure there's only one WebSocket connection
    if (!socket || socket.readyState !== WebSocket.OPEN) {
        socket = new WebSocket('ws://localhost:8080/ws');

        socket.on('open', () => {
            console.log('Connected to the WebSocket server.');

            // Request UUID from the server once connected
            socket.send(JSON.stringify({ action: 'get_uuid' }));
        });

        socket.on('message', (data) => {
            const message = data.toString('utf8');
            console.log('Message from server:', message);
            handleTextMessage(message, input, code, lang, setOutput);
        });

        socket.on('close', () => {
            setLoading(false); // Set loading state to false on WebSocket close
            console.log('Disconnected from the WebSocket server.');
        });

        socket.on('error', (error) => {
            setLoading(false); // Set loading state to false on WebSocket error
            console.error('WebSocket error:', error);
        });
    } else {
        console.log('WebSocket is already connected.');
    }
}

function handleTextMessage(message, input, code, lang, setOutput) {
    console.log('Received message from server:', message);

    if (message.startsWith('Your UUID is: ')) {
        const newUuid = message.substring('Your UUID is: '.length);
        setUUID(newUuid);
        sendCodeRequest(input, code, lang);
    } else {
        try {
            const codeResponse = JSON.parse(message);

            if (codeResponse.hasOwnProperty('isError') && codeResponse.hasOwnProperty('response')) {
                console.log('Parsed CodeResponse:', codeResponse);
                handleCodeResponse(codeResponse, setOutput);
            } else {
                console.log('Received unrecognized JSON-like data:', codeResponse);
                // Handle other types of responses if needed
            }
        } catch (error) {
            console.log('Message is not JSON-like. Handling as plain text:', message);
            handlePlainText(message);
        }
    }
}

function handleCodeResponse(codeResponse, setOutput) {
    console.log('Handling CodeResponse:', codeResponse);
    // Example: Update state with output from code execution
    setOutput(codeResponse.response);
}

function handlePlainText(message) {
    console.log('Handling plain text message:', message);
    // Handle plain text messages as needed
}

function sendCodeRequest(input, code, lang) {
    const message = {
        uuid: uuid,
        code: code,
        input_data: input,
        lang: lang
    };

    const jsonString = JSON.stringify(message);

    if (socket && socket.readyState === WebSocket.OPEN) {
        socket.send(jsonString);
        console.log('Message sent to server:', message);
    } else {
        console.error('WebSocket connection is not open. Message not sent.');
    }
}

function setUUID(newUuid) {
    uuid = newUuid.replace(/\s/g, ''); // Remove spaces from newUuid
    console.log('UUID set:', uuid);
}

module.exports = {
    runCode
};
